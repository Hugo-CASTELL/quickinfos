package dev.quickinfos;

import dev.quickinfos.config.Config;
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
}
