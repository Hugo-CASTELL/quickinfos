package dev.quickinfos.screen;

import dev.quickinfos.Singleton;
import dev.quickinfos.config.ConfigManager;
import dev.quickinfos.enums.Positions;
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
        createPositionButton();
        refreshUpDownList();
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        context.drawText(this.textRenderer, "Quick infos menu", 40, 40 - this.textRenderer.fontHeight - 10, 0xFFFFFFFF, true);
        context.drawText(this.textRenderer, "Checkout Minecraft default controls menu for keybindings", 40, 60 - this.textRenderer.fontHeight - 10, 0xFFFFFFFF, true);
    }

    @Override
    public void close() {
        try {
            ConfigManager.saveConfig(Singleton.SHOW, Singleton.SHOW_MENU_KEY.getDefaultKey().getCode(), Singleton.TOGGLE_INFO_KEY.getDefaultKey().getCode(), Singleton.POSITION, Singleton.ORDERED_INFOS, Singleton.config);
        } finally {
            super.close();
        }
    }

    public String buildMessage(Info info){
        return String.format("%s : %s", info.getHumanReadableName(), info.isOn() ? "ON" : "OFF");
    }

    public String buildMessage(Positions position){
        return String.format("Info position : %s", position.toString().replace("_", " "));
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
        int index = Singleton.ORDERED_INFOS.indexOf(upDownWidget.getInfo());
        if(up ? index > 0 :
                index != -1 && index < Singleton.ORDERED_INFOS.size()-1) {
            Singleton.ORDERED_INFOS.remove(index);
            Singleton.ORDERED_INFOS.add(index + (up ? -1 : +1), upDownWidget.getInfo());
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
        int y = 100;
        for(Info orderedInfo : Singleton.ORDERED_INFOS){
            upDownWidgets.add(new UpDownWidget(orderedInfo, 40, y, 320, 20, this));
            y+= 22;
        }
        for(UpDownWidget widget : upDownWidgets){
            this.addDrawableChild(widget.getUp());
            this.addDrawableChild(widget.getCenter());
            this.addDrawableChild(widget.getDown());
        }
    }

    private void createPositionButton(){
        ButtonWidget posButton = ButtonWidget.builder(Text.of(buildMessage(Singleton.POSITION)), button -> {
                    switch (Singleton.POSITION){
                        case TOP_RIGHT -> Singleton.POSITION = Positions.BOTTOM_RIGHT;
                        case BOTTOM_RIGHT -> Singleton.POSITION = Positions.BOTTOM_LEFT;
                        case BOTTOM_LEFT -> Singleton.POSITION = Positions.TOP_LEFT;
                        case TOP_LEFT -> Singleton.POSITION = Positions.TOP_RIGHT;
                    }
                    button.setMessage(Text.of(buildMessage(Singleton.POSITION)));
                })
                .dimensions(40, 65, 160, 20)
                .build();
        this.addDrawableChild(posButton);
    }

}
