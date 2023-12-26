package me.margiux.miniutils.module.player;

import me.margiux.miniutils.module.Category;
import me.margiux.miniutils.module.Module;
import me.margiux.miniutils.task.DelayedRepeatTask;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.screen.slot.SlotActionType;

public class DropAll extends Module {
    public DelayedRepeatTask task = new DelayedRepeatTask(task1 -> drop(), 10, 20);

    public DropAll(String name, String description, Category category, int activationKey) {
        super(name, description, category, activationKey);
    }

    public void drop() {
        if (getClient().player == null) return;
        if (getClient().interactionManager == null) return;
        if (getClient().player.getInventory().isEmpty()) return;
        getClient().setScreen(new InventoryScreen(getClient().player));
        for (int i = 0; i < getClient().player.getInventory().size(); i++) {
            if (getClient().currentScreen != null) {
                getClient().interactionManager.clickSlot(((InventoryScreen) getClient().currentScreen).getScreenHandler().syncId, i, 1, SlotActionType.THROW, getClient().player);
            }
        }
        getClient().setScreen(null);
    }
}