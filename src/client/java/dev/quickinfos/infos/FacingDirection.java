package dev.quickinfos.infos;

import dev.quickinfos.exceptions.CannotRenderInfoException;
import dev.quickinfos.utils.StaticUtils;
import net.minecraft.client.MinecraftClient;
import org.jetbrains.annotations.NotNull;

public class FacingDirection extends Info {

    @Override
    public String getHumanReadableName() {
        return "Facing direction";
    }

    @Override
    public String render(@NotNull MinecraftClient client) {
        if (client.player == null)
            throw new CannotRenderInfoException(this);

        float yaw = client.player.getYaw();

        // Normalize the yaw to a value between 0 and 360
        float normalizedYaw = (yaw % 360 + 360) % 360;

        // Determine the cardinal direction based on the yaw
        return getDirection(normalizedYaw);
    }

    public static String getDirection(double normalizedYaw) {
        int sector = (int) ((normalizedYaw + 22.5) / 45) % 8;

        return switch (sector) {
            case 0 -> "South";
            case 1 -> "South-West";
            case 2 -> "West";
            case 3 -> "North-West";
            case 4 -> "North";
            case 5 -> "North-East";
            case 6 -> "East";
            case 7 -> "South-East";
            default -> StaticUtils.NONE_INFO_CALCULATED;
        };
    }

}
