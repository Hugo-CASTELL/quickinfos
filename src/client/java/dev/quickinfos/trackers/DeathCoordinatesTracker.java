package dev.quickinfos.trackers;

import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.NotNull;

public class DeathCoordinatesTracker implements Tracker {

    private BlockPos lastDeathPos = null;

    public BlockPos getLastDeathPos() {
        return lastDeathPos;
    }

    @Override
    public boolean shouldTrigger(@NotNull MinecraftClient client) {
        if (client.player == null) return false;
        return client.player.getHealth() == 0;
    }

    @Override
    public void trigger(@NotNull MinecraftClient client) {
        if (client.player == null) return;
        lastDeathPos = new BlockPos(client.player.getBlockPos());
    }
}
