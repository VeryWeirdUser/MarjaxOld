package me.margiux.miniutils.event;

public class ModuleKeyEvent extends KeyEvent implements Cancelable {
    public ModuleKeyEvent(int key, int modifiers, int action) {
        super(key, modifiers, action);
    }
}
