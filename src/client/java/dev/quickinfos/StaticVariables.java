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

public class StaticVariables {
    public static final Identifier QUICKINFOS_LAYER = Identifier.of("quickinfos");
    public static final HashMap<String, Info> INFOS = new HashMap<>();
    public static final HashMap<String, Tracker> TRACKERS = new HashMap<>();
    public static final ArrayList<Info> SELECTED_INFOS = new ArrayList<>();
    public static Config config;

    private StaticVariables() {}

    public static void useDefaultConfig(){
        SELECTED_INFOS.add(StaticVariables.INFOS.get(Coordinates.class.getName()));
        SELECTED_INFOS.add(StaticVariables.INFOS.get(CurrentBiome.class.getName()));
        SELECTED_INFOS.add(StaticVariables.INFOS.get(FacingDirection.class.getName()));
    }

    public static void useUserConfig(){
        for(String infoSaved : StaticVariables.config.getEnabledModules()) {
            Info info = StaticVariables.INFOS.get(infoSaved);
            if(info != null){
                StaticVariables.SELECTED_INFOS.add(info);
            }
        }
    }
}
