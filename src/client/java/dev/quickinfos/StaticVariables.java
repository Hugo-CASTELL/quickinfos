package dev.quickinfos;

import dev.quickinfos.config.Config;
import dev.quickinfos.enums.Positions;
import dev.quickinfos.infos.Coordinates;
import dev.quickinfos.infos.CurrentBiome;
import dev.quickinfos.infos.FacingDirection;
import dev.quickinfos.infos.Info;
import dev.quickinfos.trackers.Tracker;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.util.Identifier;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class StaticVariables {
    public static final Identifier QUICKINFOS_LAYER = Identifier.of("quickinfos");
    public static final HashMap<String, Info> INFOS_INSTANCES = new HashMap<>();
    public static final HashMap<String, Tracker> TRACKERS = new HashMap<>();
    public static final ArrayList<Info> ORDERED_INFOS = new ArrayList<>();
    public static Positions POSITION;
    public static KeyBinding TOGGLE_KEY;
    public static boolean SHOW;
    public static Config config;

    private StaticVariables() {}

    public static void useDefaultOrderedInfos() {
        ORDERED_INFOS.addAll(INFOS_INSTANCES.values());
    }

    public static void useDefaultConfig(){
        INFOS_INSTANCES.get(Coordinates.class.getName()).setOn(true);
        INFOS_INSTANCES.get(CurrentBiome.class.getName()).setOn(true);
        INFOS_INSTANCES.get(FacingDirection.class.getName()).setOn(true);

        POSITION = Positions.TOP_RIGHT;
        SHOW = true;
        int defaultKeyCode = GLFW.GLFW_KEY_K;
        TOGGLE_KEY = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.quickinfos.toggle", // Translation key for the keybind
                InputUtil.Type.KEYSYM,
                defaultKeyCode,
                "category.quickinfos.main" // Category for the controls menu
        ));
    }

    public static void useUserConfig(){
        POSITION = config.getPosition();
        SHOW = config.getShow();
        TOGGLE_KEY = new KeyBinding(InputUtil.Type.KEYSYM.createFromCode(config.getToggleKeyCode()).getTranslationKey(), config.getToggleKeyCode(), "");

        for (Map.Entry<String, Boolean> entry : config.getEnabledModules().entrySet()) {
            Info info = INFOS_INSTANCES.getOrDefault(entry.getKey(), null);
            if(info != null){
                info.setOn(entry.getValue());
                ORDERED_INFOS.add(info);
            }
        }

        for (Info info : INFOS_INSTANCES.values()) {
            if(!ORDERED_INFOS.contains(info)){
                System.out.println(info.getHumanReadableName() + " not found in config, adding it to the list ----------------------");
                ORDERED_INFOS.add(info);
                info.setOn(false);
            }
        }
    }
}
