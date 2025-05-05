package dev.quickinfo.trackers;

import dev.quickinfo.exceptions.CannotCheckTriggerConditionTrackerException;
import dev.quickinfo.exceptions.CannotTriggerTrackerException;
import net.minecraft.client.MinecraftClient;
import org.jetbrains.annotations.NotNull;

public interface Tracker {
    boolean shouldTrigger(@NotNull MinecraftClient client) throws CannotCheckTriggerConditionTrackerException;
    void trigger(@NotNull MinecraftClient client) throws CannotTriggerTrackerException;
}
