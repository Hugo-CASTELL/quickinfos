package dev.quickinfo.utils;

import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;

public class KeyUtils {

    public static KeyBinding registerToggleInfo(int keyCode){
        return registerKeyBindingInMinecraftControls("Toggle Key", keyCode);
    }

    public static KeyBinding registerShowMenu(int keyCode){
        return registerKeyBindingInMinecraftControls("Show Menu Key", keyCode);
    }

    public static KeyBinding registerKeyBindingInMinecraftControls(String translationKey, int keyCode){
        try {
            return KeyBindingHelper.registerKeyBinding(new KeyBinding(
                    translationKey,
                    InputUtil.Type.KEYSYM,
                    keyCode,
                    StaticUtils.QUICKINFO_CATEGORY_CONTROLS
            ));
        } catch (Throwable e) {
            return KeyBindingHelper.registerKeyBinding(new KeyBinding(
                    translationKey,
                    InputUtil.Type.KEYSYM,
                    DefaultConfigUtils.NONE_KEY_IF_ERROR,
                    StaticUtils.QUICKINFO_CATEGORY_CONTROLS
            ));
        }
    }
}
