package dev.quickinfos.infos;

import dev.quickinfos.exceptions.CannotRenderInfoException;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.NotNull;

public class Coordinates extends Info {

    @Override
    public String getHumanReadableName() {
        return "Coordinates";
    }

    @Override
    public String render(@NotNull MinecraftClient client) {
        if (client.player == null)
            throw new CannotRenderInfoException(this);

        Vec3d pos = client.player.getPos();
        return String.format("%.1f / %.1f / %.1f", pos.getX(), pos.getY(), pos.getZ());
    }

}
