package dev.quickinfos.screen;

import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

public class KeybindWidget extends ButtonWidget {
    private final KeyBinding keyBinding;
    private boolean isListening;

    public KeybindWidget(KeyBinding keyBinding, int x, int y, int width, int height, Text message, PressAction onPress) {
        super(x, y, width, height, message, onPress, DEFAULT_NARRATION_SUPPLIER);
        this.keyBinding = keyBinding;
    }

    @Override
    public void onPress() {
        super.onPress();
        this.isListening = true;
        this.updateMessage();
    }

    private void updateMessage() {
        if (this.isListening) {
            this.setMessage(Text.of("Press a key..."));
        } else {
            this.setMessage(Text.of(keyBinding.getBoundKeyLocalizedText().getString()));
        }
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if(this.isListening) {
            if(keyCode != GLFW.GLFW_KEY_ESCAPE){
                this.keyBinding.setBoundKey(InputUtil.Type.KEYSYM.createFromCode(keyCode));
            }
            this.isListening = false;
            this.updateMessage();
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public void onRelease(double mouseX, double mouseY) {
        super.onRelease(mouseX, mouseY);
        if (this.isListening && !this.isMouseOver(mouseX, mouseY)) {
            // Cancel if clicked outside while listening
            this.isListening = false;
            this.updateMessage();
        }
    }
}
