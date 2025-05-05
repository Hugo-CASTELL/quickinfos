package dev.quickinfo.utils;

import dev.quickinfo.enums.Positions;
import dev.quickinfo.infos.Info;
import dev.quickinfo.screen.QuickInfoScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import org.jetbrains.annotations.NotNull;

public class ScreenUtils {
    public static void openScreen(@NotNull MinecraftClient client){
        client.send(() -> client.setScreen(new QuickInfoScreen(Text.empty())));
    }

    public static String buildMessage(Info info){
        return String.format("%s : %s", info.getHumanReadableName(), info.isOn() ? "ON" : "OFF");
    }

    public static String buildMessage(Positions position){
        return String.format("Info position : %s", position.toString().replace("_", " "));
    }

    public static String buildMessage(Object object){
        if(object instanceof String string) {
            return string;
        } else if(object instanceof Info info){
            return buildMessage(info);
        } else if(object instanceof Positions position){
            return buildMessage(position);
        }
        return "Unknown type for buildMessage";
    }

    public static ButtonWidget createButton(Object object, Runnable onClick, int x, int y, int width, int height){
        return ButtonWidget.builder(Text.of(ScreenUtils.buildMessage(object)), button -> {
                    onClick.run();
                    button.setMessage(Text.of(buildMessage(object)));
                })
                .dimensions(x, y, width, height)
                .build();
    }
}
