package me.margiux.miniutils.event;

import net.minecraft.client.gui.screen.Screen;
import org.apache.commons.lang3.mutable.MutableObject;

public class PreOpenScreenEvent extends Event {
    @SuppressWarnings("unused")
    public MutableObject<Screen> getScreen() {
        return screen;
    }

    protected final MutableObject<Screen> screen;

    public PreOpenScreenEvent(MutableObject<Screen> screen) {
        this.screen = screen;
    }
}
