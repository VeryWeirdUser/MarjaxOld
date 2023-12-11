package me.margiux.miniutils.gui.widget;

public class Window extends Widget {
    public boolean expanded = false;
    public Window(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    @SuppressWarnings("unused")
    public Window(int x, int y, int width, int height, String name, String description) {
        super(x, y, width, height, name, description);
    }

    @SuppressWarnings("unused")
    public Window(int width, int height, String name, String description) {
        super(width, height, name, description);
    }

    public int calculateHeight() {
        return height;
    }
}