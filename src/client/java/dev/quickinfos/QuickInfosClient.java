package dev.quickinfos;

import dev.quickinfos.config.ConfigManager;
import dev.quickinfos.exceptions.CannotCheckTriggerConditionTrackerException;
import dev.quickinfos.exceptions.CannotRenderInfoException;
import dev.quickinfos.exceptions.CannotTriggerTrackerException;
import dev.quickinfos.infos.*;
import dev.quickinfos.trackers.DeathCoordinatesTracker;
import dev.quickinfos.trackers.Tracker;
import dev.quickinfos.utils.ExitUtils;
import dev.quickinfos.utils.ScreenUtils;
import dev.quickinfos.utils.StaticUtils;
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
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class QuickInfosClient implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		this.onInitializeLoadStatic();
		this.onInitializeLoadConfig();
		this.onInitializeRegisterEvents();
	}

	private void onInitializeLoadStatic() {
		for (Tracker tracker : new Tracker[] {new DeathCoordinatesTracker()}) {
			try {
				Singleton.TRACKERS.put(tracker.getClass().getName(), tracker);
			} catch (Throwable e) {
				System.err.println("Failed to load a tracker at start: " + e.getMessage());
			}
		}

		for (Info info : new Info[] {new Coordinates(), new DeathCoordinates(), new TargetedBlock(), new TargetedBlockCoordinates(), new CurrentBiome(), new FacingDirection(), new FPS()}) {
			try {
				Singleton.INFOS_INSTANCES.put(info.getClass().getName(), info);
			} catch (Throwable e) {
				System.err.println("Failed to load info at start: " + e.getMessage());
			}
		}
	}

	private void onInitializeLoadConfig() {
		try{
			Singleton.config = ConfigManager.loadConfig();
			if(Singleton.config.isValid()){
				Singleton.useUserConfig();
				return;
			}
		}
		catch (Throwable e) {
			System.out.println("Failed to load config: " + e.getMessage());
		}

		Singleton.useDefaultConfig();
		Singleton.useDefaultOrderedInfos();
	}

	private void onInitializeRegisterEvents() {
		// #-----------------#
		// # Attach Trackers #
		// #-----------------#
		ClientTickEvents.START_CLIENT_TICK.register((client) -> {
			for(Tracker tracker : Singleton.TRACKERS.values()) {
				try {
					if (tracker.shouldTrigger(client)) {
						tracker.trigger(client);
					}
				}
				catch (CannotCheckTriggerConditionTrackerException | CannotTriggerTrackerException e){
					// Cancel other trackers if one fails as they all depend on client
					return;
				}
            }
		});

		// #--------------------#
		// # Infos on crosshair #
		// #--------------------#
		HudLayerRegistrationCallback.EVENT.register(
				layeredDrawer -> layeredDrawer.attachLayerBefore(
						IdentifiedLayer.CROSSHAIR,
						Singleton.QUICKINFOS_LAYER,
						QuickInfosClient::onCrosshairRender));

		// #-------------#
		// # Keybindings #
		// #-------------#
		ClientTickEvents.END_CLIENT_TICK.register((client) -> {
			while (Singleton.TOGGLE_INFO_KEY.wasPressed()){
				Singleton.SHOW = !Singleton.SHOW;
			}
			while (Singleton.SHOW_MENU_KEY.wasPressed()){
				ScreenUtils.openScreen(client);
			}
		});

		// #-------------#
		// # /quickinfos #
		// #-------------#
		ClientCommandRegistrationCallback.EVENT.register(
				((commandDispatcher, registryAccess) ->
						commandDispatcher.register(ClientCommandManager.literal(StaticUtils.COMMAND_LITERAL).executes(
								commandContext -> {
									try{
										ScreenUtils.openScreen(commandContext.getSource().getClient());
										return ExitUtils.PROPER_EXIT;
									}catch (Throwable e){
										commandContext.getSource().sendError(Text.of(e.toString()));
										return ExitUtils.ERROR_IN_OPENED_SCREEN_EXIT;
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
			Singleton.ORDERED_INFOS.isEmpty() ||
			client.player == null ||
			client.world == null ||
			!Singleton.SHOW) {
			return;
		}

		// Split the selected infos into separate lines
		String[] rawLines;
		try {
			rawLines = Singleton.ORDERED_INFOS.stream().map(info -> info.isOn() ? info.render(client) : "").toArray(String[]::new);
		}
		catch (CannotRenderInfoException e){
			// Abort if one info cannot be rendered as they all depend on client
			return;
		}

		ArrayList<String> lines = new ArrayList<>();
		for(String line : rawLines) {
			if(line != null && !line.isEmpty()) lines.add(line) ;
		}

		renderLines(client, lines, drawContext);
	}

	private static void renderLines(@NotNull MinecraftClient client, @NotNull ArrayList<String> lines, @NotNull DrawContext drawContext) {
		// Calculate the screen width and set a y_margin
		int screenWidth = client.getWindow().getScaledWidth();
		int y_margin = 2;
		int y = y_margin;

		// For each line, calculate its width and draw it right aligned
		for (String line : lines) {
			int textWidth = client.textRenderer.getWidth(line);
			int bottom_top = lines.size() * (client.textRenderer.fontHeight + y_margin);
			int x = 2;
			switch (Singleton.POSITION){
				case TOP_RIGHT:
					x = screenWidth - textWidth - y_margin;
					drawContext.drawText(client.textRenderer, line, x, y, Colors.WHITE, false);
					y += client.textRenderer.fontHeight;
					break;
				case BOTTOM_RIGHT:
					x = screenWidth - textWidth - y_margin;
					drawContext.drawText(client.textRenderer, line, x, client.getWindow().getScaledHeight() - bottom_top + y, Colors.WHITE, false);
					y += client.textRenderer.fontHeight;
					break;
				case TOP_LEFT:
					drawContext.drawText(client.textRenderer, line, x, y, Colors.WHITE, false);
					y += client.textRenderer.fontHeight;
					break;
				case BOTTOM_LEFT:
					drawContext.drawText(client.textRenderer, line, x, client.getWindow().getScaledHeight() - bottom_top + y, Colors.WHITE, false);
					y += client.textRenderer.fontHeight;
					break;
			}
		}
	}
}