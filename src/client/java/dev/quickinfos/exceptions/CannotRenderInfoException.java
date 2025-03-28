package dev.quickinfos.exceptions;

import dev.quickinfos.infos.Info;

public class CannotRenderInfoException extends RuntimeException {
    public CannotRenderInfoException(Info info) {
      this(info.getHumanReadableName() + " could not be rendered");
    }

    public CannotRenderInfoException(String message) {
        super(message);
    }
}
