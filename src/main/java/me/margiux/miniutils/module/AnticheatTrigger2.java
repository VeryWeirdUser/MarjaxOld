package me.margiux.miniutils.module;

import me.margiux.miniutils.event.ModuleEventHandler;
import me.margiux.miniutils.event.TickEvent;

public class AnticheatTrigger2 extends Module {
    boolean jumped = false;
    public AnticheatTrigger2(String name, String description, Category category, int activationKey) {
        super(name, description, category, activationKey);
    }

    @ModuleEventHandler
    public void onTick(TickEvent event) {
        getClient().options.sprintKey.setPressed(true);
        getClient().options.forwardKey.setPressed(true);
        getClient().options.jumpKey.setPressed(jumped = !jumped);
    }
}
