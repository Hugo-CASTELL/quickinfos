package dev.quickinfos.config;

import java.util.LinkedHashMap;
import java.util.Map;

public class Config {
    private final Map<String, Boolean> enabledModules = new LinkedHashMap<>();

    public boolean isEmpty(){
        return enabledModules.isEmpty();
    }

    public Map<String, Boolean> getEnabledModules() {
        return enabledModules;
    }

    public void addEnabledModule(String enabledModule, boolean isOn) {
        this.enabledModules.put(enabledModule, isOn);
    }

    public void clearEnabledModules() {
        this.enabledModules.clear();
    }
}
