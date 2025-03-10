package dev.quickinfos;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudLayerRegistrationCallback;
import net.fabricmc.fabric.api.client.rendering.v1.IdentifiedLayer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Colors;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.biome.Biome;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class QuickInfosClient implements ClientModInitializer {
	// Custom Identifier for the mod in the HUD
	public static final Identifier QUICKINFOS_LAYER = Identifier.of("quickinfos");

	@Override
	public void onInitializeClient() {
		// This entrypoint is suitable for setting up client-specific logic, such as rendering.

		// Attach Quickinfos layer on top of the crosshair layer
		HudLayerRegistrationCallback.EVENT.register(
				layeredDrawer -> layeredDrawer.attachLayerBefore(
						IdentifiedLayer.CROSSHAIR,
						QUICKINFOS_LAYER,
						QuickInfosClient::render));
	}

	private static void render(DrawContext drawContext, RenderTickCounter tickCounter) {
		MinecraftClient client = MinecraftClient.getInstance();

		// Conditions of stopping
		boolean screenRelatedAbort =
			client.options.hudHidden ||
			client.getDebugHud().shouldShowDebugHud();

		// Checking if every dependency is working
		boolean errorsRelatedAbort =
			client.player == null ||
			client.world == null;

		if (screenRelatedAbort || errorsRelatedAbort) {
			return;
		}

		// Calculate the screen width and set a margin
		int screenWidth = client.getWindow().getScaledWidth();
		int margin = 2;

		// Split the info into separate lines
		String[] lines = new String[]{
			formatPos(client.player.getPos()),
			getBiomeName(client),
			getDirection(client.player.getYaw())
		};
		int y = margin;

		// For each line, calculate its width and draw it right aligned
		for (String line : lines) {
			int textWidth = client.textRenderer.getWidth(line);
			int x = screenWidth - textWidth - margin;
			drawContext.drawText(client.textRenderer, line, x, y, Colors.WHITE, false);
			y += client.textRenderer.fontHeight;
		}
	}

	private static String formatPos(Vec3d pos) {
		return String.format("%.1f / %.1f / %.1f", pos.getX(), pos.getY(), pos.getZ());
	}

	public static String getBiomeName(@NotNull MinecraftClient client) {
        BlockPos playerPos = client.player.getBlockPos();
		Optional<RegistryKey<Biome>> biome = client.world.getBiome(playerPos).getKey();
        return biome.map(biomeRegistryKey -> biomeRegistryKey.getValue().toString()).orElse("unknown");
	}

	public static String getDirection(float yaw) {
		// Normalize the yaw to a value between 0 and 360
		float normalizedYaw = (yaw % 360 + 360) % 360;

		// Determine the cardinal direction based on the yaw
		if (normalizedYaw >= 337.5 || normalizedYaw < 22.5) {
			return "South";
		} else if (normalizedYaw >= 22.5 && normalizedYaw < 67.5) {
			return "South-West";
		} else if (normalizedYaw >= 67.5 && normalizedYaw < 112.5) {
			return "West";
		} else if (normalizedYaw >= 112.5 && normalizedYaw < 157.5) {
			return "North-West";
		} else if (normalizedYaw >= 157.5 && normalizedYaw < 202.5) {
			return "North";
		} else if (normalizedYaw >= 202.5 && normalizedYaw < 247.5) {
			return "North-East";
		} else if (normalizedYaw >= 247.5 && normalizedYaw < 292.5) {
			return "East";
		} else if (normalizedYaw >= 292.5 && normalizedYaw < 337.5) {
			return "South-East";
		} else {
			return "Unknown";
		}
	}
}