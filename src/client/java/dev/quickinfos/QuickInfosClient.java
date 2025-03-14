package dev.quickinfos;

import dev.quickinfos.config.Config;
import dev.quickinfos.config.ConfigManager;
import dev.quickinfos.infos.Coordinates;
import dev.quickinfos.infos.CurrentBiome;
import dev.quickinfos.infos.FacingDirection;
import dev.quickinfos.infos.Info;
import dev.quickinfos.screen.QuickInfosScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.rendering.v1.HudLayerRegistrationCallback;
import net.fabricmc.fabric.api.client.rendering.v1.IdentifiedLayer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.text.Text;
import net.minecraft.util.Colors;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.HashMap;

public class QuickInfosClient implements ClientModInitializer {
	// Custom Identifier for the mod in the HUD
	public static final Identifier QUICKINFOS_LAYER = Identifier.of("quickinfos");
	public static final HashMap<String, Info> INFOS = new HashMap<>();
	public static final ArrayList<Info> SELECTED_INFOS = new ArrayList<>();
	public static Config config;

	@Override
	public void onInitializeClient() {
		// This entrypoint is suitable for setting up client-specific logic, such as rendering.

		INFOS.put(Coordinates.class.getName(), new Coordinates());
		INFOS.put(CurrentBiome.class.getName(), new CurrentBiome());
		INFOS.put(FacingDirection.class.getName(), new FacingDirection());

		config = ConfigManager.loadConfig();

		if(!config.isEmpty()){
			// Saved config
			for(String infoSaved : config.getEnabledModules()) {
				Info info = INFOS.get(infoSaved);
				if(info != null){
					SELECTED_INFOS.add(info);
				}
			}
		}
		else {
			// Default config
			SELECTED_INFOS.add(INFOS.get(Coordinates.class.getName()));
			SELECTED_INFOS.add(INFOS.get(CurrentBiome.class.getName()));
			SELECTED_INFOS.add(INFOS.get(FacingDirection.class.getName()));
		}

		// Attach Quickinfos layer on top of the crosshair layer
		HudLayerRegistrationCallback.EVENT.register(
				layeredDrawer -> layeredDrawer.attachLayerBefore(
						IdentifiedLayer.CROSSHAIR,
						QUICKINFOS_LAYER,
						QuickInfosClient::render));

		// Attach screen to a command
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

	private static void render(DrawContext drawContext, RenderTickCounter tickCounter) {
		MinecraftClient client = MinecraftClient.getInstance();

		// Conditions of stopping
		boolean screenRelatedAbort =
			client.options.hudHidden ||
			client.getDebugHud().shouldShowDebugHud();

		// Mod related
		boolean modRelatedAbord =
			SELECTED_INFOS.isEmpty();

		// Checking if every dependency is working
		boolean errorsRelatedAbort =
			client.player == null ||
			client.world == null;

		if (screenRelatedAbort || modRelatedAbord || errorsRelatedAbort) {
			return;
		}

		// Calculate the screen width and set a margin
		int screenWidth = client.getWindow().getScaledWidth();
		int margin = 2;

		// Split the selected infos into separate lines
		String[] lines = SELECTED_INFOS.stream().map(info -> info.toHUDScreen(client)).toArray(String[]::new);
		int y = margin;

		// For each line, calculate its width and draw it right aligned
		for (String line : lines) {
			int textWidth = client.textRenderer.getWidth(line);
			int x = screenWidth - textWidth - margin;
			drawContext.drawText(client.textRenderer, line, x, y, Colors.WHITE, false);
			y += client.textRenderer.fontHeight;
		}
	}
}