package me.margiux.miniutils.module;

import me.margiux.miniutils.Mode;
import me.margiux.miniutils.gui.MiniutilsScreen;
import me.margiux.miniutils.gui.widget.Button;
import me.margiux.miniutils.gui.widget.Input;
import me.margiux.miniutils.gui.MiniutilsGui;
import me.margiux.miniutils.utils.Mutable;
import me.margiux.miniutils.task.*;
import net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.util.Identifier;

public class AutoSell extends Module {
    public record Item(String itemName, long price, int quantity, int damage) {
    }

    public Mutable<String> comInput = new Mutable<>("ah sell");
    public Input<String> comField = new Input<>("*Command to sell the item", comInput, null, (e) -> comInput.setValue(e, true));
    public Mutable<String> idInput = new Mutable<>("");
    public Input<String> idField = new Input<>("*ID of the item", idInput, null, (e) -> idInput.setValue(e, true));
    public Mutable<Long> priceInput = new Mutable<>(0L);
    public Input<Long> priceField = new Input<>("*Price of the item", priceInput, Input.LONG_FILTER, (e) -> priceInput.setValue(Long.valueOf(e), true), false);
    public Mutable<Integer> quantityInput = new Mutable<>(0);
    public Input<Integer> quantityField = new Input<>("Quantity of the item", quantityInput, Input.INT_FILTER, (e) -> quantityInput.setValue(Integer.valueOf(e), true), false);
    public Mutable<Integer> damageInput = new Mutable<>(0);
    public Input<Integer> damageField = new Input<>("Damage of the item", damageInput, Input.INT_FILTER, (e) -> damageInput.setValue(Integer.valueOf(e), true), false);

    public Button resetButton = new Button("Reset", "Reset", (b) -> {
        idInput.setValue("", true);
        priceInput.setValue(0L, true);
        quantityInput.setValue(0, true);
        damageInput.setValue(0, true);
    });

    public boolean itemMatches(ItemStack stack) {
        if (stack.getCount() != quantityInput.getValue() && quantityField.notEmpty()) return false;
        if (stack.getDamage() != damageInput.getValue() && damageField.notEmpty()) return false;
        return stack.getRegistryEntry().matchesId(new Identifier("minecraft", idInput.getValue()));
    }

    public AutoSell(String name, String description, int activationKey) {
        super(name, description, activationKey);
        TaskManager.addTask(new RepeatTask((task) -> {
            if (getClient().world != null) {
                int slot = -1;
                for (ItemStack stack : getClient().player.getInventory().main) {
                    if (itemMatches(stack)) {
                        slot = getClient().player.getInventory().getSlotWithStack(stack);
                        if (slot < 9) slot += 36;
                        break;
                    }
                }
                if (slot != -1) {
                    if (slot != getClient().player.getInventory().selectedSlot + 36) getClient().setScreen(new InventoryScreen(getClient().player));
                    int finalSlot = slot;

                    TaskManager.addTask(new DelayedRepeatTask((task2) -> {
                        if (finalSlot == getClient().player.getInventory().selectedSlot + 36) {
                            task2.setTaskCompleted();
                            return;
                        }
                        if (getClient().currentScreen instanceof InventoryScreen screen && !(getClient().currentScreen instanceof CreativeInventoryScreen)) {
                            getClient().interactionManager.clickSlot(screen.getScreenHandler().syncId, finalSlot, 0, SlotActionType.PICKUP, getClient().player);
                            task2.setTaskCompleted();
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
                                getClient().player.sendCommand(comInput.getValue() + " " + priceInput.getValue().toString());
                            }, 10).setPredicate((b) -> mode.getValue() == Mode.ENABLED && !(getClient().currentScreen instanceof MiniutilsScreen))
                    );
                }
            }
        }, 30).setPredicate((b) -> mode.getValue() == Mode.ENABLED && comField.notEmpty() && idField.notEmpty() && priceField.notEmpty() && !(getClient().currentScreen instanceof MiniutilsScreen)));
    }

    /*
    public PanelWithAlignment listToPanel(Mutable<List<Item>> list) {
        PanelWithAlignment p = new PanelWithAlignment();
        p.setAlignment(PanelWithAlignment.Alignment.Vertical);
        for (Item item : list.getValue()) {
            ButtonWithData<Item> button = new ButtonWithData<>(item.itemName + " | " + item.price, "", (b) -> {
                list.getValue().remove(b.data);
                list.onValueChanged();
                MiniutilsGui.instance.validate();
            }, item);
            p.add(button, 110);
        }
        return p;
    }*/

    @Override
    public void initGui() {
        MiniutilsGui.instance.main.add(toggleButton);
        MiniutilsGui.instance.main.add(comField);
        MiniutilsGui.instance.main.add(idField);
        MiniutilsGui.instance.main.add(priceField);
        MiniutilsGui.instance.main.add(quantityField);
        MiniutilsGui.instance.main.add(damageField);
        MiniutilsGui.instance.main.add(resetButton);

        //MiniutilsGui.instance.main.add(itemList, 100);
    }
}
