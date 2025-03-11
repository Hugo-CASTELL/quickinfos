package dev.quickinfos.infos;

import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.NotNull;

public class Coordinates implements Info {

    @Override
    public String toHUDScreen(@NotNull MinecraftClient client) {
        if (client.player == null) {
            return "Unknown player position";
        }

        Vec3d pos = client.player.getPos();

        return String.format("%.1f / %.1f / %.1f", pos.getX(), pos.getY(), pos.getZ());
    }

}
