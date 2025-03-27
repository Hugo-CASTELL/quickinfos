package dev.quickinfos.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dev.quickinfos.enums.Positions;
import dev.quickinfos.infos.Info;
import net.fabricmc.loader.api.FabricLoader;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class ConfigManager {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path CONFIG_FILE = FabricLoader.getInstance().getConfigDir().resolve("quickinfos_mod.json");

    public static Config loadConfig() {
        try {
            if (!Files.exists(CONFIG_FILE)) {
                return new Config(); // Return default config
            }

            return GSON.fromJson(Files.newBufferedReader(CONFIG_FILE), Config.class);
        } catch (Throwable e) {
            Config cleanConfig = new Config();
            saveConfig(cleanConfig);
            return cleanConfig;
        }
    }

    public static void saveConfig(boolean show, int toggleKeyCode, Positions position, ArrayList<Info> infos, Config config) {
        config.clearPosition();
        config.setPosition(position);

        config.setShow(show);
        config.setToggleKeyCode(toggleKeyCode);

        config.clearEnabledModules();
        for (Info info : infos) {
            config.addEnabledModule(info.getClass().getName(), info.isOn());
        }

        saveConfig(config);
    }

    public static void saveConfig(Config config) {
        try {
            Files.deleteIfExists(CONFIG_FILE);
            Files.writeString(CONFIG_FILE, GSON.toJson(config));
        } catch (Throwable e) {
            System.err.println("Failed to save config: " + e.getMessage());
        }
    }
}
