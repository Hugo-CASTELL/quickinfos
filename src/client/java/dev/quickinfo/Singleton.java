package dev.quickinfo;

import dev.quickinfo.config.Config;
import dev.quickinfo.enums.Positions;
import dev.quickinfo.infos.Info;
import dev.quickinfo.trackers.Tracker;
import dev.quickinfo.utils.DefaultConfigUtils;
import dev.quickinfo.utils.KeyUtils;
import dev.quickinfo.utils.StaticUtils;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Singleton {
    public static final Identifier QUICKINFO_LAYER = Identifier.of(StaticUtils.LAYER_ID);
    public static final HashMap<String, Info> INFOS_INSTANCES = new HashMap<>();
    public static final HashMap<String, Tracker> TRACKERS = new HashMap<>();
    public static final ArrayList<Info> ORDERED_INFOS = new ArrayList<>();
    public static Positions POSITION;
    public static KeyBinding TOGGLE_INFO_KEY;
    public static KeyBinding SHOW_MENU_KEY;
    public static boolean SHOW;
    public static Config config;

    private Singleton() {}

    public static void useDefaultConfig(){
        for (String info : DefaultConfigUtils.DEFAULT_INFOS) {
            INFOS_INSTANCES.get(info).setOn(true);
        }

        TOGGLE_INFO_KEY = KeyUtils.registerToggleInfo(DefaultConfigUtils.TOGGLE_INFO_KEYCODE);
        SHOW_MENU_KEY = KeyUtils.registerShowMenu(DefaultConfigUtils.SHOW_MENU_KEYCODE);

        POSITION = DefaultConfigUtils.POSITION;
        SHOW = DefaultConfigUtils.SHOW;
    }

    public static void useDefaultOrderedInfos() {
        ORDERED_INFOS.addAll(INFOS_INSTANCES.values());
    }

    public static void useUserConfig(){
        for (Map.Entry<String, Boolean> entry : config.getEnabledModules().entrySet()) {
            Info info = INFOS_INSTANCES.getOrDefault(entry.getKey(), null);
            if(info != null){
                info.setOn(entry.getValue());
                ORDERED_INFOS.add(info);
            }
        }

        // For new infos in mod updates
        for (Info info : INFOS_INSTANCES.values()) {
            if(!ORDERED_INFOS.contains(info)){
                ORDERED_INFOS.add(info);
                info.setOn(false);
            }
        }

        TOGGLE_INFO_KEY = KeyUtils.registerToggleInfo(config.getToggleKeyCode());
        SHOW_MENU_KEY = KeyUtils.registerShowMenu(config.getShowMenuKeyCode());

        POSITION = config.getPosition();
        SHOW = config.getShow();
    }
}
