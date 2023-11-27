package me.margiux.miniutils.gui;

import me.margiux.miniutils.gui.widget.Widget;

public class Window extends Widget {
    public Window(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    public Window(int x, int y, int width, int height, String name, String description) {
        super(x, y, width, height, name, description);
    }

    public Window(int width, int height, String name, String description) {
        super(width, height, name, description);
    }
}