package dev.quickinfos.config;

import dev.quickinfos.enums.Positions;
import org.lwjgl.glfw.GLFW;

import java.util.LinkedHashMap;
import java.util.Map;

public class Config {
    private final Map<String, Boolean> enabledModules = new LinkedHashMap<>();
    private Positions position;
    private int toggleKeyCode;
    private int showMenuKeyCode;
    private boolean show;

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

    public void clearToggleKeyCode() {
        this.toggleKeyCode = GLFW.GLFW_KEY_K;
    }

    public int getToggleKeyCode() {
        return toggleKeyCode;
    }

    public void setToggleKeyCode(int toggleKeyCode) {
        this.toggleKeyCode = toggleKeyCode;
    }

    public void clearShow() {
        this.show = true;
    }

    public boolean getShow() {
        return show;
    }

    public void setShow(boolean show) {
        this.show = show;
    }

    public void clearShowMenuKeyCode() {
        this.showMenuKeyCode = GLFW.GLFW_KEY_M;
    }

    public int getShowMenuKeyCode() {
        return showMenuKeyCode;
    }

    public void setShowMenuKeyCode(int showMenuKeyCode) {
        this.showMenuKeyCode = showMenuKeyCode;
    }
}
