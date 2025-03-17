package dev.quickinfos.screen;

import dev.quickinfos.StaticVariables;
import dev.quickinfos.config.ConfigManager;
import dev.quickinfos.infos.Info;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

import java.util.ArrayList;

public class QuickInfosScreen extends Screen {
    public ArrayList<UpDownWidget>  upDownWidgets = new ArrayList<>();

    public QuickInfosScreen(Text title) {
        super(title);
    }

    @Override
    public void init() {
        int y = 40;
        for (Info info : StaticVariables.INFOS.values()) {
            ButtonWidget buttonWidget = ButtonWidget.builder(Text.of(buildMessage(info)), (btn) -> {
                if(isInfoActivated(info)){
                    StaticVariables.SELECTED_INFOS.remove(info);
                }else{
                    StaticVariables.SELECTED_INFOS.add(info);
                }
                btn.setMessage(Text.of(buildMessage(info)));
                refreshUpDownList();
            }).dimensions(40, y, 180, 20).build();
            y+=25;

            // Register the button widget.
            this.addDrawableChild(buttonWidget);
        }
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
            ConfigManager.saveConfig(StaticVariables.SELECTED_INFOS, StaticVariables.config);
        }finally {
            super.close();
        }
    }

    private boolean isInfoActivated(Info info){
        boolean isOn = false;
        for(Info selectedInfo : StaticVariables.SELECTED_INFOS){
            if (info.getClass() == selectedInfo.getClass()) {
                isOn = true;
                break;
            }
        }
        return isOn;
    }

    private String buildMessage(Info info){
        return String.format("%s : %s", info.getHumanReadableName(), isInfoActivated(info) ? "ON" : "OFF");
    }

    public void moveUp(UpDownWidget upDownWidget) {
        int index = StaticVariables.SELECTED_INFOS.indexOf(upDownWidget.getInfo());
        if(index > 0) {
            StaticVariables.SELECTED_INFOS.remove(index);
            StaticVariables.SELECTED_INFOS.add(index-1, upDownWidget.getInfo());
        }
        refreshUpDownList();
    }

    public void moveDown(UpDownWidget upDownWidget) {
        int index = StaticVariables.SELECTED_INFOS.indexOf(upDownWidget.getInfo());
        if(index != -1 && index < StaticVariables.SELECTED_INFOS.size()-1) {
            StaticVariables.SELECTED_INFOS.remove(index);
            StaticVariables.SELECTED_INFOS.add(index+1, upDownWidget.getInfo());
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
        for(Info selectedInfo : StaticVariables.SELECTED_INFOS){
            upDownWidgets.add(new UpDownWidget(selectedInfo, 300, y, 200, 20, this));
            y+= 22;
        }
        for(UpDownWidget widget : upDownWidgets){
            this.addDrawableChild(widget.getUp());
            this.addDrawableChild(widget.getCenter());
            this.addDrawableChild(widget.getDown());
        }
    }
}
