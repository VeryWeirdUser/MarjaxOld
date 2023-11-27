package me.margiux.miniutils.module;

import me.margiux.miniutils.Mode;
import me.margiux.miniutils.event.ChatReceiveMessageEvent;
import me.margiux.miniutils.event.EventHandler;
import me.margiux.miniutils.event.ModuleEventHandler;
import me.margiux.miniutils.event.TickEvent;
import me.margiux.miniutils.gui.MiniutilsScreen;
import me.margiux.miniutils.module.setting.IntegerSetting;
import me.margiux.miniutils.module.setting.LongSetting;
import me.margiux.miniutils.module.setting.StringSetting;
import me.margiux.miniutils.task.*;
import net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen;
import net.minecraft.client.gui.screen.ingame.GenericContainerScreen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.util.Identifier;

public final class AutoSell extends Module {
    public final StringSetting comInput = new StringSetting("*Command", "", "ah sell");
    public final StringSetting idInput = new StringSetting("*ID", "ID of the item", "potion");
    public final LongSetting priceInput = new LongSetting("*Price", "Price of the item", 449999L);
    public final IntegerSetting quantityInput = new IntegerSetting("Quantity", "Quantity of the item", 1);
    public final StringSetting actionOnFailTriggerInput = new StringSetting("Resell trigger", "Message that will trigger the action", "Освободите хранилище");
    public boolean canRun = true;

    public final Task sellTask = new RepeatTask((task) -> {
        if (getClient().world == null || getClient().player == null || getClient().player.getInventory() == null || getClient().interactionManager == null)
            return;
        int slot = -1;
        for (ItemStack stack : getClient().player.getInventory().main) {
            if (itemMatches(stack)) {
                slot = getClient().player.getInventory().getSlotWithStack(stack);
                if (slot < 9) slot += 36;
                break;
            }
        }
        if (slot == -1) return;
        int finalSlot = slot;
        TaskManager.addTask(new DelayedRepeatTask((task2) -> {
            if (finalSlot == getClient().player.getInventory().selectedSlot + 36) {
                task2.setTaskCompleted();
            } else {
                getClient().setScreen(new InventoryScreen(getClient().player));
                if (getClient().currentScreen instanceof InventoryScreen screen && !(getClient().currentScreen instanceof CreativeInventoryScreen)) {
                    getClient().interactionManager.clickSlot(screen.getScreenHandler().syncId, finalSlot, 0, SlotActionType.PICKUP, getClient().player);
                    task2.setTaskCompleted();
                }
            }
        }, 4, 5).setPredicate((b) -> mode.getValue() == Mode.ENABLED && !(getClient().currentScreen instanceof MiniutilsScreen))).setOnCompleteTask(new DelayedRepeatTask((task2) -> {
            if (finalSlot == getClient().player.getInventory().selectedSlot + 36) {
                task2.setTaskCompleted();
                return;
            }
            if (getClient().currentScreen instanceof InventoryScreen screen) {
                getClient().interactionManager.clickSlot(screen.getScreenHandler().syncId, getClient().player.getInventory().selectedSlot + 36, 0, SlotActionType.PICKUP, getClient().player);
                task2.setTaskCompleted();
            }
        }, 4, 5).setPredicate((b) -> mode.getValue() == Mode.ENABLED && !(getClient().currentScreen instanceof MiniutilsScreen))).setOnCompleteTask(
                new DelayTask((task2) -> {
                    getClient().setScreen(null);
                    getClient().player.sendCommand(comInput.getData() + " " + priceInput.getData().toString());
                }, 10).setPredicate((b) -> mode.getValue() == Mode.ENABLED && !(getClient().currentScreen instanceof MiniutilsScreen))
        );
    }, 30).setPredicate((b) -> mode.getValue() == Mode.ENABLED && comInput.getData() != null && idInput.getData() != null && priceInput.getData() != null && !(getClient().currentScreen instanceof MiniutilsScreen) && canRun);

    public boolean itemMatches(ItemStack stack) {
        if (stack.getCount() != quantityInput.getData()) return false;
        return stack.getRegistryEntry().matchesId(new Identifier("minecraft", idInput.getData()));
    }

    @ModuleEventHandler
    public void onMessageReceived(ChatReceiveMessageEvent e) {
        if (e.message.getString().contains(actionOnFailTriggerInput.getData())) {
            canRun = false;
            TaskManager.addTask(new DelayTask((task -> {
                if (getClient().player != null) getClient().player.sendCommand("ah");
            }), 2)).setOnCompleteTask(new DelayTask((task -> {
                if (getClient().player != null && getClient().currentScreen instanceof GenericContainerScreen screen && getClient().interactionManager != null) {
                    if (screen.getScreenHandler().getSlot(46).getStack() == ItemStack.EMPTY) {
                        task.setTaskCompleted();
                    } else
                        getClient().interactionManager.clickSlot(screen.getScreenHandler().syncId, 46, 0, SlotActionType.PICKUP, getClient().player);
                }
            }), 5)).setOnCompleteTask(new DelayedRepeatTask(task -> {
                if (getClient().player != null && getClient().currentScreen instanceof GenericContainerScreen screen && getClient().interactionManager != null)
                    getClient().interactionManager.clickSlot(screen.getScreenHandler().syncId, 0, 0, SlotActionType.PICKUP, getClient().player);
            }, 3, 3, 12)).setOnCompleteTask(new DelayTask((task -> {
                getClient().setScreen(null);
                canRun = true;
            }), 5));
        }
    }


    public AutoSell(String name, String description, Category category, int activationKey) {
        super(name, description, category, activationKey);
        addSetting(comInput);
        addSetting(idInput);
        addSetting(priceInput);
        addSetting(quantityInput);
        addSetting(actionOnFailTriggerInput);
    }

    @Override
    public void onEnable() {
        super.onEnable();

        TaskManager.addTask(sellTask);
    }

    @Override
    public void onDisable() {
        super.onDisable();

        TaskManager.removeTask(sellTask);
    }
}
