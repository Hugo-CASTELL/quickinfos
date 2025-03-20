package dev.quickinfos.screen;

import dev.quickinfos.StaticVariables;
import dev.quickinfos.config.ConfigManager;
import dev.quickinfos.infos.Info;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

import java.util.ArrayList;

public class QuickInfosScreen extends Screen {
    public ArrayList<UpDownWidget>  upDownWidgets = new ArrayList<>();

    public QuickInfosScreen(Text title) {
        super(title);
    }

    @Override
    public void init() {
        refreshUpDownList();
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        context.drawText(this.textRenderer, "Quick infos menu", 40, 40 - this.textRenderer.fontHeight - 10, 0xFFFFFFFF, true);
    }

    @Override
    public void close() {
        try {
            ConfigManager.saveConfig(StaticVariables.ORDERED_INFOS, StaticVariables.config);
        } finally {
            super.close();
        }
    }

    public String buildMessage(Info info){
        return String.format("%s : %s", info.getHumanReadableName(), info.isOn() ? "ON" : "OFF");
    }

    public void onActivate(UpDownWidget upDownWidget) {
        Info info = upDownWidget.getInfo();
        info.setOn(!info.isOn());
        refreshUpDownList();
    }

    public void onMoveUp(UpDownWidget upDownWidget) {
        move(upDownWidget, true);
    }

    public void onMoveDown(UpDownWidget upDownWidget) {
        move(upDownWidget, false);
    }

    private void move(UpDownWidget upDownWidget, boolean up) {
        int index = StaticVariables.ORDERED_INFOS.indexOf(upDownWidget.getInfo());
        if(up ? index > 0 :
                index != -1 && index < StaticVariables.ORDERED_INFOS.size()-1) {
            StaticVariables.ORDERED_INFOS.remove(index);
            StaticVariables.ORDERED_INFOS.add(index + (up ? -1 : +1), upDownWidget.getInfo());
        }
        refreshUpDownList();
    }

    private void refreshUpDownList(){
        if(!upDownWidgets.isEmpty()) {
            for(UpDownWidget widget : upDownWidgets){
                this.remove(widget.getUp());
                this.remove(widget.getCenter());
                this.remove(widget.getDown());
            }
            upDownWidgets.clear();
        }
        int y = 40;
        for(Info orderedInfo : StaticVariables.ORDERED_INFOS){
            upDownWidgets.add(new UpDownWidget(orderedInfo, 40, y, 320, 20, this));
            y+= 22;
        }
        for(UpDownWidget widget : upDownWidgets){
            this.addDrawableChild(widget.getUp());
            this.addDrawableChild(widget.getCenter());
            this.addDrawableChild(widget.getDown());
        }
    }
}
