package me.margiux.miniutils.module.player;

import me.margiux.miniutils.gui.MiniutilsScreen;
import me.margiux.miniutils.module.Category;
import me.margiux.miniutils.module.Module;
import me.margiux.miniutils.task.RepeatTask;
import me.margiux.miniutils.task.TaskManager;
import net.minecraft.screen.slot.SlotActionType;

public class DropAll extends Module {
    public final RepeatTask task = new RepeatTask(task -> drop(), 10);

    public DropAll(String name, String description, Category category, int activationKey) {
        super(name, description, category, activationKey);
    }

    @Override
    protected void onEnable() {
        super.onEnable();
        TaskManager.addTask(task);
    }

    @Override
    protected void onDisable() {
        super.onDisable();
        TaskManager.removeTask(task);
    }

    public void drop() {
        if (MC.player == null) return;
        if (MC.interactionManager == null) return;
        if (MC.player.getInventory().isEmpty()) return;
        if (MC.currentScreen instanceof MiniutilsScreen) return;
        for (int i = 0; i < MC.player.getInventory().size(); i++) {
            MC.interactionManager.clickSlot(MC.player.playerScreenHandler.syncId, i, 1, SlotActionType.THROW, MC.player);
        }
    }
}