package dev.quickinfos.infos;

import net.minecraft.client.MinecraftClient;
import org.jetbrains.annotations.NotNull;

public class FPS extends Info {

    @Override
    public String getHumanReadableName() {
        return "FPS";
    }

    @Override
    public String render(@NotNull MinecraftClient client) {
        return String.format("%d FPS", client.getCurrentFps());
    }

}
