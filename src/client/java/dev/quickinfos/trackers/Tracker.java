package dev.quickinfos.trackers;

import net.minecraft.client.MinecraftClient;
import org.jetbrains.annotations.NotNull;

public interface Tracker {
    boolean shouldTrigger(@NotNull MinecraftClient client);
    void trigger(@NotNull MinecraftClient client);
}
