package me.margiux.miniutils.module.misc;

import me.margiux.miniutils.event.ModuleEventHandler;
import me.margiux.miniutils.event.TickEvent;
import me.margiux.miniutils.module.Category;
import me.margiux.miniutils.module.Module;
public class AnticheatTrigger extends Module {
    boolean jumped = false;
    int tick;
    public AnticheatTrigger(String name, String description, Category category, int activationKey) {
        super(name, description, category, activationKey);
    }

    @ModuleEventHandler
    public void onTick(TickEvent event) {
        if (++tick == 21) tick = 0;
        if (MC.player != null) MC.player.setSprinting(true);
        MC.options.sprintKey.setPressed(true);
        MC.options.sneakKey.setPressed(true);
        MC.options.forwardKey.setPressed(true);
        MC.options.rightKey.setPressed(tick > 5 && tick < 10);
        MC.options.leftKey.setPressed(tick > 15 && tick < 20);
        MC.options.jumpKey.setPressed(jumped = !jumped);
    }

    @Override
    protected void onDisable() {
        super.onDisable();
        tick = 0;
        if (MC.player != null) MC.player.setSprinting(false);
        MC.options.sprintKey.setPressed(false);
        MC.options.sneakKey.setPressed(false);
        MC.options.forwardKey.setPressed(false);
        MC.options.rightKey.setPressed(false);
        MC.options.leftKey.setPressed(false);
        MC.options.jumpKey.setPressed(jumped = false);
    }
}
