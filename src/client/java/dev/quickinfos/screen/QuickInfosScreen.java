package dev.quickinfos.screen;

import dev.quickinfos.StaticVariables;
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
        createToggleButton();
        createPositionButton();
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
            ConfigManager.saveConfig(StaticVariables.SHOW, StaticVariables.TOGGLE_KEY.getDefaultKey().getCode(), StaticVariables.POSITION, StaticVariables.ORDERED_INFOS, StaticVariables.config);
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
        int y = 100;
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

    private void createPositionButton(){
        ButtonWidget posButton = ButtonWidget.builder(Text.of(buildMessage(StaticVariables.POSITION)), button -> {
                    switch (StaticVariables.POSITION){
                        case TOP_RIGHT -> StaticVariables.POSITION = Positions.BOTTOM_RIGHT;
                        case BOTTOM_RIGHT -> StaticVariables.POSITION = Positions.BOTTOM_LEFT;
                        case BOTTOM_LEFT -> StaticVariables.POSITION = Positions.TOP_LEFT;
                        case TOP_LEFT -> StaticVariables.POSITION = Positions.TOP_RIGHT;
                    }
                    button.setMessage(Text.of(buildMessage(StaticVariables.POSITION)));
                })
                .dimensions(60, 40, 160, 20)
                .build();
        this.addDrawableChild(posButton);
    }

    private void createToggleButton(){
        KeybindWidget toggleButton = new KeybindWidget(StaticVariables.TOGGLE_KEY, 40, 60, 160, 20, Text.of("Toggle QuickInfos"), button -> {boolean pass = true;});
        this.addDrawableChild(toggleButton);
    }
}
