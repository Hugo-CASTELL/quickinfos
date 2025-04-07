package dev.quickinfos.utils;

import dev.quickinfos.enums.Positions;
import dev.quickinfos.infos.Coordinates;
import dev.quickinfos.infos.CurrentBiome;
import dev.quickinfos.infos.FacingDirection;
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
