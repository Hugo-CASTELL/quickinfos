package dev.quickinfos.infos;

import dev.quickinfos.Singleton;
import dev.quickinfos.exceptions.CannotRenderInfoException;
import dev.quickinfos.trackers.DeathCoordinatesTracker;
import dev.quickinfos.utils.StaticUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.NotNull;

public class DeathCoordinates extends Info {

    @Override
    public String getHumanReadableName() {
        return "Death coordinates";
    }

    @Override
    public String render(@NotNull MinecraftClient client) {
        if (client.player == null)
            throw new CannotRenderInfoException(this);

        BlockPos pos = ((DeathCoordinatesTracker) Singleton.TRACKERS.get(DeathCoordinatesTracker.class.getName())).getLastDeathPos();
        return pos != null ? String.format("Died at %d / %d / %d", pos.getX(), pos.getY(), pos.getZ()) :
                             StaticUtils.NONE_INFO_CALCULATED;
    }

}
