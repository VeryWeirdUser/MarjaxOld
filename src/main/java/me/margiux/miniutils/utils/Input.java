package me.margiux.miniutils.utils;

import java.util.ArrayList;
import java.util.List;

public class Input {
    public static final List<Integer> pressedKeys = new ArrayList<>();

    public static void setPressed(int key, boolean value) {
        if (value) {
            if (pressedKeys.contains(key)) return;
            else pressedKeys.add(key);
        }
        else {
            if (!pressedKeys.contains(key)) return;
            else pressedKeys.remove(key);
        }
    }

    public static boolean isPressed(int key) {
        return pressedKeys.contains(key);
    }
}
