package dev.quickinfos.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
            System.err.println("Failed to load quickinfos config: " + e.getMessage());
            return new Config();
        }
    }

    public static void saveConfig(ArrayList<Info> infos, Config config) {
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
