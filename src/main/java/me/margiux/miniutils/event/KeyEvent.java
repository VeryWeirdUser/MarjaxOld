package me.margiux.miniutils.event;

public class KeyEvent extends Event implements Cancelable {
    public int getKey() {
        return key;
    }

    public int getModifiers() {
        return modifiers;
    }

    public int getAction() {
        return action;
    }

    protected final int key;
    protected final int modifiers;
    protected final int action;

    public KeyEvent(int key, int modifiers, int action) {
        this.key = key;
        this.modifiers = modifiers;
        this.action = action;
    }
}
