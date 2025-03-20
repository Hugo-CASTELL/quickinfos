package dev.quickinfos;

import dev.quickinfos.config.Config;
import dev.quickinfos.infos.Coordinates;
import dev.quickinfos.infos.CurrentBiome;
import dev.quickinfos.infos.FacingDirection;
import dev.quickinfos.infos.Info;
import dev.quickinfos.trackers.Tracker;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class StaticVariables {
    public static final Identifier QUICKINFOS_LAYER = Identifier.of("quickinfos");
    public static final HashMap<String, Info> INFOS_INSTANCES = new HashMap<>();
    public static final HashMap<String, Tracker> TRACKERS = new HashMap<>();
    public static final ArrayList<Info> ORDERED_INFOS = new ArrayList<>();
    public static Config config;

    private StaticVariables() {}

    public static void useDefaultOrderedInfos() {
        ORDERED_INFOS.addAll(INFOS_INSTANCES.values());
    }

    public static void useDefaultConfig(){
        INFOS_INSTANCES.get(Coordinates.class.getName()).setOn(true);
        INFOS_INSTANCES.get(CurrentBiome.class.getName()).setOn(true);
        INFOS_INSTANCES.get(FacingDirection.class.getName()).setOn(true);
    }

    public static void useUserConfig(){
        for (Map.Entry<String, Boolean> entry : config.getEnabledModules().entrySet()) {
            Info info = INFOS_INSTANCES.getOrDefault(entry.getKey(), null);
            if(info != null){
                info.setOn(entry.getValue());
                ORDERED_INFOS.add(info);
            }
        }
    }
}
