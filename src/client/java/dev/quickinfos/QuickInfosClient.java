package dev.quickinfos;

import org.reflections.Reflections;
import dev.quickinfos.config.ConfigManager;
import dev.quickinfos.infos.*;
import dev.quickinfos.screen.QuickInfosScreen;
import dev.quickinfos.trackers.Tracker;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudLayerRegistrationCallback;
import net.fabricmc.fabric.api.client.rendering.v1.IdentifiedLayer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.text.Text;
import net.minecraft.util.Colors;

public class QuickInfosClient implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		this.onInitializeLoadStatic();
		this.onInitializeLoadConfig();
		this.onInitializeRegisterEvents();
	}

	private void onInitializeLoadStatic() {
		Reflections trackersReflections = new Reflections("dev.quickinfos.trackers");
		for (Class<? extends Tracker> trackerClass : trackersReflections.getSubTypesOf(Tracker.class)) {
			try {
				StaticVariables.TRACKERS.put(trackerClass.getName(), trackerClass.getDeclaredConstructor().newInstance());
			} catch (Throwable e) {
				System.err.println("Failed to load a tracker at start: " + e.getMessage());
			}
		}

		Reflections infoReflections = new Reflections("dev.quickinfos.infos");
		for (Class<? extends Info> infoClass : infoReflections.getSubTypesOf(Info.class)) {
			try {
				StaticVariables.INFOS.put(infoClass.getName(), infoClass.getDeclaredConstructor().newInstance());
			} catch (Throwable e) {
				System.err.println("Failed to load info at start: " + e.getMessage());
			}
		}
	}

	private void onInitializeLoadConfig() {
		StaticVariables.config = ConfigManager.loadConfig();
		if(!StaticVariables.config.isEmpty()){
			StaticVariables.useUserConfig();
		}
		else {
			StaticVariables.useDefaultConfig();
		}
	}

	private void onInitializeRegisterEvents() {
		// #-----------------#
		// # Attach Trackers #
		// #-----------------#
		ClientTickEvents.END_CLIENT_TICK.register((client) -> {
			if(client == null){
				return;
			}

			for(Tracker tracker : StaticVariables.TRACKERS.values()) {
				if(tracker.shouldTrigger(client))	{
					tracker.trigger(client);
				}
			}
		});

		// #--------------------#
		// # Infos on crosshair #
		// #--------------------#
		HudLayerRegistrationCallback.EVENT.register(
				layeredDrawer -> layeredDrawer.attachLayerBefore(
						IdentifiedLayer.CROSSHAIR,
						StaticVariables.QUICKINFOS_LAYER,
						QuickInfosClient::onCrosshairRender));

		// #-------------#
		// # /quickinfos #
		// #-------------#
		ClientCommandRegistrationCallback.EVENT.register(
				((commandDispatcher, registryAccess) ->
						commandDispatcher.register(ClientCommandManager.literal("quickinfos").executes(
								commandContext -> {
									try{
										MinecraftClient client = commandContext.getSource().getClient();
										client.send(() -> client.setScreen(new QuickInfosScreen(Text.empty())));
										return 0;
									}catch (Throwable e){
										commandContext.getSource().sendError(Text.of(e.toString()));
										return 1;
									}
								}
						))
				)
		);
	}

	private static void onCrosshairRender(DrawContext drawContext, RenderTickCounter tickCounter) {
		MinecraftClient client = MinecraftClient.getInstance();

		if( // Abort conditions
			client == null ||
			client.options.hudHidden ||
			client.getDebugHud().shouldShowDebugHud() ||
			StaticVariables.SELECTED_INFOS.isEmpty() ||
			client.player == null ||
			client.world == null) {
			return;
		}

		// Split the selected infos into separate lines
		String[] lines = StaticVariables.SELECTED_INFOS.stream().map(info -> {
			try {
				return info.toHUDScreen(client);
			} catch (Throwable e){
				return "";
			}
		}).toArray(String[]::new);

		// Calculate the screen width and set a margin
		int screenWidth = client.getWindow().getScaledWidth();
		int margin = 2;
		int y = margin;

		// For each line, calculate its width and draw it right aligned
		for (String line : lines) {
			if(line != null &&  !line.isEmpty()) {
				int textWidth = client.textRenderer.getWidth(line);
				int x = screenWidth - textWidth - margin;
				drawContext.drawText(client.textRenderer, line, x, y, Colors.WHITE, false);
				y += client.textRenderer.fontHeight;
			}
		}
	}
}