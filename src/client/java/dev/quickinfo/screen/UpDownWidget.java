package dev.quickinfo.screen;

import dev.quickinfo.infos.Info;
import dev.quickinfo.utils.ScreenUtils;
import net.minecraft.client.gui.widget.ButtonWidget;

import java.util.LinkedList;

public class UpDownWidget {
    private final Info info;

    private final ButtonWidget up;
    private final ButtonWidget center;
    private final ButtonWidget down;

    public UpDownWidget(Info info, int x, int y, int width, int height, QuickInfoScreen screen) {
        this.info = info;
        int square = height;

        this.up = ScreenUtils.createButton("↑", () -> screen.onMoveUp(this), x, y, square, square);
        this.center = ScreenUtils.createButton(info, () -> screen.onActivate(this),x+width/8, y, 3*width/4, height);
        this.down = ScreenUtils.createButton("↓", () -> screen.onMoveDown(this), x+width-height, y, square, square);
    }

    public Info getInfo() {
        return info;
    }

    public LinkedList<ButtonWidget> getWidgets() {
        return new LinkedList<>() { { add(up); add(center); add(down); } };
    }
}
