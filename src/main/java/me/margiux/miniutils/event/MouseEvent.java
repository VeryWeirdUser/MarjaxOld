package me.margiux.miniutils.event;

public class MouseEvent extends Event {
    protected final int button;
    protected final int action;
    protected final int modifiers;
    protected final double x;
    protected final double y;
    protected final boolean doubleClick;

    public MouseEvent(int button, int action, int modifiers, double x, double y, boolean doubleClick) {
        this.button = button;
        this.action = action;
        this.modifiers = modifiers;
        this.x = x;
        this.y = y;
        this.doubleClick = doubleClick;
    }

    public int getButton() {
        return button;
    }

    public int getAction() {
        return action;
    }

    public int getModifiers() {
        return modifiers;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public boolean clicked() {
        return action == 1;
    }

    public boolean held() {
        return action == 2;
    }

    public boolean released() {
        return action == 0;
    }

    public boolean isDoubleClick() {
        return doubleClick;
    }
}
