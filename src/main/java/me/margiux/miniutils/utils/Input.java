package me.margiux.miniutils.utils;

import java.util.ArrayList;
import java.util.List;

public class Input {
    public static final List<Integer> pressedKeys = new ArrayList<>();

    public static void setPressed(Integer key, boolean value) {
        if (!pressedKeys.contains(key) && value) pressedKeys.add(key);
        else if (pressedKeys.contains(key) && !value) pressedKeys.remove(key);
    }

    public static boolean isPressed(Integer key) {
        return pressedKeys.contains(key);
    }
}
