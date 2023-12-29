package me.margiux.miniutils.event;

public class KeyEvent extends Event implements Cancelable {
    protected final int key;
    protected final int modifiers;
    protected final int action;

    public KeyEvent(int key, int modifiers, int action) {
        this.key = key;
        this.modifiers = modifiers;
        this.action = action;
    }

    public int getKey() {
        return key;
    }

    public int getModifiers() {
        return modifiers;
    }

    public int getAction() {
        return action;
    }

    public boolean pressed() {
        return action == 1;
    }

    public boolean held() {
        return action == 2;
    }

    public boolean released() {
        return action == 0;
    }
}
