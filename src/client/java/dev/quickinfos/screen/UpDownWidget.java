package dev.quickinfos.screen;

import dev.quickinfos.infos.Info;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class UpDownWidget {
    private final Info info;

    private final ButtonWidget up;
    private final ButtonWidget center;
    private final ButtonWidget down;

    public UpDownWidget(Info info, int x, int y, int width, int height, QuickInfosScreen screen) {
        this.info = info;

        this.up = ButtonWidget.builder(Text.of("↑"), button -> screen.moveUp(this))
                  .dimensions(x, y, width/4, height)
                  .build();

        this.center = ButtonWidget.builder(Text.of(info.getHumanReadableName()), button -> {})
                      .dimensions(x+width/4, y, 2*width/4, height)
                      .build();

        this.down = ButtonWidget.builder(Text.of("↓"), button -> screen.moveDown(this))
                    .dimensions(x+3*width/4, y, width/4, height)
                    .build();
    }

    public Info getInfo() {
        return info;
    }

    public ButtonWidget getUp() {
        return up;
    }

    public ButtonWidget getCenter() {
        return center;
    }

    public ButtonWidget getDown() {
        return down;
    }
}
