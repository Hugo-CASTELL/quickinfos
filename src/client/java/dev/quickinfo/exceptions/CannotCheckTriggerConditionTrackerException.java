package dev.quickinfo.exceptions;

import dev.quickinfo.trackers.Tracker;

public class CannotCheckTriggerConditionTrackerException extends RuntimeException {
    public CannotCheckTriggerConditionTrackerException(Tracker tracker) {
      this(tracker.getClass().getName() + " could not verify trigger condition");
    }

    public CannotCheckTriggerConditionTrackerException(String message) {
        super(message);
    }
}
