package me.margiux.miniutils.event;

import net.minecraft.client.gui.screen.Screen;

public class OpenScreenEvent extends Event {
    protected final Screen screen;

    @SuppressWarnings("unused")
    public Screen getScreen() {
        return screen;
    }
    public OpenScreenEvent(Screen screen) {
        this.screen = screen;
    }
}
