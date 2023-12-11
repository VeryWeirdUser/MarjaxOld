package me.margiux.miniutils.module.player;

import me.margiux.miniutils.event.ModuleEventHandler;
import me.margiux.miniutils.event.TickEvent;
import me.margiux.miniutils.module.Category;
import me.margiux.miniutils.module.Module;
import me.margiux.miniutils.utils.Input;

public class GuiMove extends Module {
    public GuiMove(String name, String description, Category category, int activationKey) {
        super(name, description, category, activationKey);
    }

    @ModuleEventHandler
    public void onTick(TickEvent event) {
        getClient().options.forwardKey.setPressed(Input.isPressed(getClient().options.forwardKey.getDefaultKey().getCode()));
        getClient().options.backKey.setPressed(Input.isPressed(getClient().options.backKey.getDefaultKey().getCode()));
        getClient().options.rightKey.setPressed(Input.isPressed(getClient().options.rightKey.getDefaultKey().getCode()));
        getClient().options.leftKey.setPressed(Input.isPressed(getClient().options.leftKey.getDefaultKey().getCode()));
        getClient().options.jumpKey.setPressed(Input.isPressed(getClient().options.jumpKey.getDefaultKey().getCode()));
        getClient().options.sprintKey.setPressed(Input.isPressed(getClient().options.sprintKey.getDefaultKey().getCode()));
    }
}