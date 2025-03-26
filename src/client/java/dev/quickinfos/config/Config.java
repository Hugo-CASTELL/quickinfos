package dev.quickinfos.config;

import dev.quickinfos.enums.Positions;

import java.util.LinkedHashMap;
import java.util.Map;

public class Config {
    private final Map<String, Boolean> enabledModules = new LinkedHashMap<>();
    private Positions position;

    public boolean isValid(){
        return position != null && !enabledModules.isEmpty();
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

    public Positions getPosition() {
        return this.position;
    }

    public void clearPosition() {
        this.position = null;
    }

    public void setPosition(Positions position) {
        this.position = position;
    }
}
