package dev.quickinfos.infos;

import net.minecraft.client.MinecraftClient;
import org.jetbrains.annotations.NotNull;

public interface Info {
    String toHUDScreen(@NotNull MinecraftClient client);
}
