package dev.quickinfos.utils;

import dev.quickinfos.screen.QuickInfosScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import org.jetbrains.annotations.NotNull;

public class ScreenUtils {
    public static void openScreen(@NotNull MinecraftClient client){
        client.send(() -> client.setScreen(new QuickInfosScreen(Text.empty())));
    }
}
