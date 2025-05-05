package dev.quickinfo.exceptions;

import dev.quickinfo.infos.Info;

public class CannotRenderInfoException extends RuntimeException {
    public CannotRenderInfoException(Info info) {
      this(info.getHumanReadableName() + " could not be rendered");
    }

    public CannotRenderInfoException(String message) {
        super(message);
    }
}
