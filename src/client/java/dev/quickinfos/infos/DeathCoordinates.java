package dev.quickinfos.infos;

import dev.quickinfos.StaticVariables;
import dev.quickinfos.trackers.DeathCoordinatesTracker;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.NotNull;

public class DeathCoordinates extends Info {

    @Override
    public String getHumanReadableName() {
        return "Death coordinates";
    }

    @Override
    public String toHUDScreen(@NotNull MinecraftClient client) {
        if (client.player == null) {
            return "Unknown player position";
        }

        BlockPos pos = ((DeathCoordinatesTracker)StaticVariables.TRACKERS.get(DeathCoordinatesTracker.class.getName())).getLastDeathPos();

        return String.format("Died at %d / %d / %d", pos.getX(), pos.getY(), pos.getZ());
    }

}
