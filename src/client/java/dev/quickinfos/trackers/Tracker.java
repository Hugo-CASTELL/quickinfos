package dev.quickinfos.trackers;

import dev.quickinfos.exceptions.CannotCheckTriggerConditionTrackerException;
import dev.quickinfos.exceptions.CannotTriggerTrackerException;
import net.minecraft.client.MinecraftClient;
import org.jetbrains.annotations.NotNull;

public interface Tracker {
    boolean shouldTrigger(@NotNull MinecraftClient client) throws CannotCheckTriggerConditionTrackerException;
    void trigger(@NotNull MinecraftClient client) throws CannotTriggerTrackerException;
}
