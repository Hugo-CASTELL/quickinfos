package dev.quickinfo.infos;

import dev.quickinfo.exceptions.CannotRenderInfoException;
import net.minecraft.client.MinecraftClient;
import org.jetbrains.annotations.NotNull;

public abstract class Info {
    private boolean isOn;

    public boolean isOn() { return isOn; }
    public void setOn(boolean on) { isOn = on; }

    public abstract String getHumanReadableName();
    public abstract String render(@NotNull MinecraftClient client) throws CannotRenderInfoException;
}
