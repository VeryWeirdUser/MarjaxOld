package me.margiux.miniutils.module;

import io.github.cottonmc.cotton.gui.widget.WPlainPanel;
import me.margiux.miniutils.event.EventManager;
import me.margiux.miniutils.event.OpenScreenEvent;
import me.margiux.miniutils.gui.widget.Button;
import me.margiux.miniutils.gui.widget.Input;
import me.margiux.miniutils.gui.MiniutilsGui;
import me.margiux.miniutils.mutable.MutableExtended;
import me.margiux.miniutils.task.RepeatTask;
import me.margiux.miniutils.task.TaskManager;
import net.minecraft.client.gui.screen.Screen;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AuctionSeller extends Module implements OnTick {
    @Override
    public void tick() {
    }

    public record Item(String itemName, long price, int quantity, int damage) {
    }

    //public MutableExtended<List<Item>> sellItemList = new MutableExtended<>(new ArrayList<>());
    //public me.margiux.miniutils.gui.widget.List<List<Item>, Item> itemList;

    public MutableExtended<String> comInput = new MutableExtended<>("/ah sell");
    public Input<String> comField = new Input<>("*Command to sell the item", comInput, null, (e) -> comInput.setValue(e, true));
    public MutableExtended<String> idInput = new MutableExtended<>("");
    public Input<String> idField = new Input<>("*ID of the item", idInput, null, (e) -> idInput.setValue(e, true));
    public MutableExtended<Long> priceInput = new MutableExtended<>(0L);
    public Input<Long> priceField = new Input<>("*Price of the item", priceInput, Input.LONG_FILTER, (e) -> priceInput.setValue(Long.valueOf(e), true));
    public MutableExtended<Integer> quantityInput = new MutableExtended<>(0);
    public Input<Integer> quantityField = new Input<>("Quantity of the item", quantityInput, Input.INT_FILTER, (e) -> quantityInput.setValue(Integer.valueOf(e), true));
    public MutableExtended<Integer> damageInput = new MutableExtended<>(0);
    public Input<Integer> damageField = new Input<>("Damage of the item", damageInput, Input.INT_FILTER, (e) -> damageInput.setValue(Integer.valueOf(e), true));

    public Button resetButton = new Button("Reset", "Reset", (b) -> {
        idInput.setValue("", true);
        priceInput.setValue(0L, true);
        quantityInput.setValue(0, true);
        damageInput.setValue(0, true);
    });


    public AuctionSeller(String name, String description) {
        super(name, description);
    }

    /*
    public PanelWithAlignment listToPanel(MutableExtended<List<Item>> list) {
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
        MiniutilsGui.instance.main.add(idField);
        MiniutilsGui.instance.main.add(priceField);
        MiniutilsGui.instance.main.add(quantityField);
        MiniutilsGui.instance.main.add(damageField);
        MiniutilsGui.instance.main.add(resetButton);

        EventManager.addListener(OpenScreenEvent.class, event -> {

        });

        TaskManager.addTask(new RepeatTask(() -> {
            if (getClient().world != null) {
                getClient().interactionManager.clickSlot();
                getClient().player.getInventory().getSlotWithStack()
            }
        }, 100));

        //MiniutilsGui.instance.main.add(itemList, 100);
    }
}
