package me.margiux.miniutils.module.player;

import me.margiux.miniutils.event.ModuleEventHandler;
import me.margiux.miniutils.event.TickEvent;
import me.margiux.miniutils.module.Category;
import me.margiux.miniutils.module.Module;
import me.margiux.miniutils.utils.Input;
import net.minecraft.client.gui.screen.ingame.HandledScreen;

public class GuiMove extends Module {
    public GuiMove(String name, String description, Category category, int activationKey) {
        super(name, description, category, activationKey);
    }

    @ModuleEventHandler
    public void onTick(TickEvent event) {
        if (MC.currentScreen instanceof HandledScreen<?>) {
            MC.options.forwardKey.setPressed(Input.isPressed(MC.options.forwardKey.getDefaultKey().getCode()));
            MC.options.backKey.setPressed(Input.isPressed(MC.options.backKey.getDefaultKey().getCode()));
            MC.options.rightKey.setPressed(Input.isPressed(MC.options.rightKey.getDefaultKey().getCode()));
            MC.options.leftKey.setPressed(Input.isPressed(MC.options.leftKey.getDefaultKey().getCode()));
            MC.options.jumpKey.setPressed(Input.isPressed(MC.options.jumpKey.getDefaultKey().getCode()));
            MC.options.sprintKey.setPressed(Input.isPressed(MC.options.sprintKey.getDefaultKey().getCode()));
        }
    }
}