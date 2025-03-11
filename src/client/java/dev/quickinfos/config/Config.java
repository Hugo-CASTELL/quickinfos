package dev.quickinfos.config;

import java.util.ArrayList;

public class Config {
    private final ArrayList<String> enabledModules = new ArrayList<>();

    public boolean isEmpty(){
        return enabledModules.isEmpty();
    }

    public ArrayList<String> getEnabledModules() {
        return enabledModules;
    }

    public void addEnabledModule(String enabledModule) {
        this.enabledModules.add(enabledModule);
    }

    public void clearEnabledModules() {
        this.enabledModules.clear();
    }
}
