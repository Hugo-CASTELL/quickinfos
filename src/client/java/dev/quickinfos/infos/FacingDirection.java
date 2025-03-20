package dev.quickinfos.infos;

import net.minecraft.client.MinecraftClient;
import org.jetbrains.annotations.NotNull;

public class FacingDirection extends Info {

    @Override
    public String getHumanReadableName() {
        return "Facing direction";
    }

    @Override
    public String toHUDScreen(@NotNull MinecraftClient client) {
        if (client.player == null) {
            return "Unknown player direction";
        }

        float yaw = client.player.getYaw();

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
