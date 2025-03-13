package dev.quickinfos.screen;

import dev.quickinfos.QuickInfosClient;
import dev.quickinfos.config.ConfigManager;
import dev.quickinfos.infos.Info;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class QuickInfosScreen extends Screen {
    public QuickInfosScreen(Text title) {
        super(title);
    }

    private boolean isInfoActivated(Info info){
        boolean isOn = false;
        for(Info selectedInfo : QuickInfosClient.SELECTED_INFOS){
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

    @Override
    public void init() {
        int y = 40;
        for (Info info : QuickInfosClient.INFOS.values()) {
            ButtonWidget buttonWidget = ButtonWidget.builder(Text.of(buildMessage(info)), (btn) -> {
                if(isInfoActivated(info)){
                    QuickInfosClient.SELECTED_INFOS.remove(info);
                }else{
                    QuickInfosClient.SELECTED_INFOS.add(info);
                }
                btn.setMessage(Text.of(buildMessage(info)));
            }).dimensions(40, y, 180, 20).build();
            y+=25;

            // Register the button widget.
            this.addDrawableChild(buttonWidget);
        }
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        context.drawText(this.textRenderer, "Quick infos menu", 40, 40 - this.textRenderer.fontHeight - 10, 0xFFFFFFFF, true);
    }

    @Override
    public void close() {
        try {
            ConfigManager.saveConfig(QuickInfosClient.SELECTED_INFOS, QuickInfosClient.config);
        }finally {
            super.close();
        }
    }
}
