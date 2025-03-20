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

        this.up = ButtonWidget.builder(Text.of("↑"), button -> screen.onMoveUp(this))
                  .dimensions(x, y, height, height)
                  .build();

        this.center = ButtonWidget.builder(Text.of(screen.buildMessage(info)), button -> {
                    screen.onActivate(this);
                    button.setMessage(Text.of(screen.buildMessage(info)));
                })
                      .dimensions(x+width/8, y, 3*width/4, height)
                      .build();

        this.down = ButtonWidget.builder(Text.of("↓"), button -> screen.onMoveDown(this))
                    .dimensions(x+width-height, y, height, height)
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
