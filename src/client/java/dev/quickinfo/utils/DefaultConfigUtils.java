package dev.quickinfo.utils;

import dev.quickinfo.enums.Positions;
import dev.quickinfo.infos.Coordinates;
import dev.quickinfo.infos.CurrentBiome;
import dev.quickinfo.infos.FacingDirection;
import org.lwjgl.glfw.GLFW;

public class DefaultConfigUtils {
    public static Positions POSITION = Positions.TOP_RIGHT;
    public static boolean SHOW = true;

    public static int SHOW_MENU_KEYCODE = GLFW.GLFW_KEY_M;
    public static int TOGGLE_INFO_KEYCODE = GLFW.GLFW_KEY_K;
    public static int NONE_KEY_IF_ERROR = GLFW.GLFW_KEY_UNKNOWN;

    public static String[] DEFAULT_INFOS = new String[]{
            Coordinates.class.getName(),
            CurrentBiome.class.getName(),
            FacingDirection.class.getName()
    };
}
