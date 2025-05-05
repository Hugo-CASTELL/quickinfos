package dev.quickinfo.exceptions;

import dev.quickinfo.trackers.Tracker;

public class CannotTriggerTrackerException extends RuntimeException {
    public CannotTriggerTrackerException(Tracker tracker) {
      this(tracker.getClass().getName() + " could not be triggered");
    }

    public CannotTriggerTrackerException(String message) {
        super(message);
    }
}
