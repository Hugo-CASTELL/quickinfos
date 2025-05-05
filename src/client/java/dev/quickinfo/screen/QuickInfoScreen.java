package dev.quickinfo.screen;

import dev.quickinfo.Singleton;
import dev.quickinfo.config.ConfigManager;
import dev.quickinfo.enums.Positions;
import dev.quickinfo.infos.Info;
import dev.quickinfo.utils.ScreenUtils;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

import java.util.ArrayList;

public class QuickInfoScreen extends Screen {
    public ArrayList<UpDownWidget>  upDownWidgets = new ArrayList<>();

    public static final Dimension TITLE_DIMENSION = new Dimension(40, 10);
    public static final Dimension SUBTITLE_DIMENSION = new Dimension(40, TITLE_DIMENSION.getY() + 10);
    public static final Dimension SHOW_BUTTON_DIMENSION = new Dimension(SUBTITLE_DIMENSION.getX(), SUBTITLE_DIMENSION.getY() + 15, 160, 20);
    public static final Dimension POSITION_BUTTON_DIMENSION = new Dimension(SHOW_BUTTON_DIMENSION.getX() + SHOW_BUTTON_DIMENSION.getWidth() + 10, SHOW_BUTTON_DIMENSION.getY(), SHOW_BUTTON_DIMENSION.getWidth() - 10, SHOW_BUTTON_DIMENSION.getHeight());
    public static final Dimension INFO_LIST_DIMENSION = new Dimension(SHOW_BUTTON_DIMENSION.getX(), POSITION_BUTTON_DIMENSION.getY() + 25, 320, SHOW_BUTTON_DIMENSION.getHeight());
    public static final int INFO_LIST_MARGIN = 20;

    public QuickInfoScreen(Text title) {
        super(title);
    }

    @Override
    public void init() {
        createShowButton();
        createPositionButton();
        refreshUpDownList();
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        context.drawText(this.textRenderer, "Quick infos menu (This screen will changed in the next mod version)", TITLE_DIMENSION.getX(), TITLE_DIMENSION.getY(), 0xFFFFFFFF, true);
        context.drawText(this.textRenderer, "Checkout Minecraft default controls menu for keybindings", SUBTITLE_DIMENSION.getX(), SUBTITLE_DIMENSION.getY(), 0xFFFFFFFF, true);
    }

    @Override
    public void close() {
        try {
            ConfigManager.saveConfig(Singleton.SHOW, Singleton.SHOW_MENU_KEY.getDefaultKey().getCode(), Singleton.TOGGLE_INFO_KEY.getDefaultKey().getCode(), Singleton.POSITION, Singleton.ORDERED_INFOS, Singleton.config);
        } finally {
            super.close();
        }
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
        int y = QuickInfoScreen.INFO_LIST_DIMENSION.getY();
        upDownWidgets.forEach(widget -> widget.getWidgets().forEach(this::remove));
        upDownWidgets.clear();
        for(Info orderedInfo : Singleton.ORDERED_INFOS){
            upDownWidgets.add(new UpDownWidget(orderedInfo, QuickInfoScreen.INFO_LIST_DIMENSION.getX(), y, QuickInfoScreen.INFO_LIST_DIMENSION.getWidth(), QuickInfoScreen.INFO_LIST_DIMENSION.getHeight(),this));
            y+= QuickInfoScreen.INFO_LIST_MARGIN;
        }
        upDownWidgets.forEach(widget -> widget.getWidgets().forEach(this::addDrawableChild));
    }

    private void createPositionButton() {
        this.addDrawableChild(ScreenUtils.createButton(Singleton.POSITION, () -> {
            switch (Singleton.POSITION){
                case TOP_RIGHT -> Singleton.POSITION = Positions.BOTTOM_RIGHT;
                case BOTTOM_RIGHT -> Singleton.POSITION = Positions.BOTTOM_LEFT;
                case BOTTOM_LEFT -> Singleton.POSITION = Positions.TOP_LEFT;
                case TOP_LEFT -> Singleton.POSITION = Positions.TOP_RIGHT;
            }
        }, QuickInfoScreen.POSITION_BUTTON_DIMENSION.getX(), QuickInfoScreen.POSITION_BUTTON_DIMENSION.getY(), QuickInfoScreen.POSITION_BUTTON_DIMENSION.getWidth(), QuickInfoScreen.POSITION_BUTTON_DIMENSION.getHeight()));
    }

    private void createShowButton(){
        this.addDrawableChild(ScreenUtils.createButton("Show/Hide", () -> Singleton.SHOW = !Singleton.SHOW, QuickInfoScreen.SHOW_BUTTON_DIMENSION.getX(), QuickInfoScreen.SHOW_BUTTON_DIMENSION.getY(), QuickInfoScreen.SHOW_BUTTON_DIMENSION.getWidth(), QuickInfoScreen.SHOW_BUTTON_DIMENSION.getHeight()));
    }

}
