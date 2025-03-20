package dev.quickinfos.infos;

import net.minecraft.client.MinecraftClient;
import org.jetbrains.annotations.NotNull;

public abstract class Info {
    private boolean isOn;

    public boolean isOn() { return isOn; }
    public void setOn(boolean on) { isOn = on; }

    public abstract String getHumanReadableName();
    public abstract String toHUDScreen(@NotNull MinecraftClient client);
}
