package dev.quickinfos.exceptions;

import dev.quickinfos.trackers.Tracker;

public class CannotTriggerTrackerException extends RuntimeException {
    public CannotTriggerTrackerException(Tracker tracker) {
      this(tracker.getClass().getName() + " could not be triggered");
    }

    public CannotTriggerTrackerException(String message) {
        super(message);
    }
}
