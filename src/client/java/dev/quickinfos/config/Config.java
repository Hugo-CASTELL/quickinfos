package dev.quickinfos.config;

import dev.quickinfos.enums.Positions;
import org.lwjgl.glfw.GLFW;

import java.util.LinkedHashMap;
import java.util.Map;

public class Config {

    //#region Fields

    private final Map<String, Boolean> enabledModules = new LinkedHashMap<>();
    private Positions position;
    private int toggleKeyCode;
    private int showMenuKeyCode;
    private boolean show;

    //#endregion Fields

    //#region Getters and setters

    public Map<String, Boolean> getEnabledModules() {
        return enabledModules;
    }

    public void addEnabledModule(String enabledModule, boolean isOn) {
        this.enabledModules.put(enabledModule, isOn);
    }

    public Positions getPosition() {
        return this.position;
    }

    public void setPosition(Positions position) {
        this.position = position;
    }

    public int getToggleKeyCode() {
        return toggleKeyCode;
    }

    public void setToggleKeyCode(int toggleKeyCode) {
        this.toggleKeyCode = toggleKeyCode;
    }

    public boolean getShow() {
        return show;
    }

    public void setShow(boolean show) {
        this.show = show;
    }

    public int getShowMenuKeyCode() {
        return showMenuKeyCode;
    }

    public void setShowMenuKeyCode(int showMenuKeyCode) {
        this.showMenuKeyCode = showMenuKeyCode;
    }

    //#endregion Getters and setters

    //#region Methods

    public boolean isValid() {
        return position != null && !enabledModules.isEmpty();
    }

    public void clearEnabledModules() {
        this.enabledModules.clear();
    }

    public void clearPosition() {
        this.position = null;
    }

    public void clearToggleKeyCode() {
        this.toggleKeyCode = GLFW.GLFW_KEY_K;
    }

    public void clearShow() {
        this.show = true;
    }

    public void clearShowMenuKeyCode() {
        this.showMenuKeyCode = GLFW.GLFW_KEY_M;
    }

    //#endregion Methods

}
