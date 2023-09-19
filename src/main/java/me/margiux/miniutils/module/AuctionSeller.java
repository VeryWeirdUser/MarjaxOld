package me.margiux.miniutils.module;

import io.github.cottonmc.cotton.gui.widget.WGridPanel;
import io.github.cottonmc.cotton.gui.widget.WPlainPanel;
import me.margiux.miniutils.gui.PanelWithAlignment;
import me.margiux.miniutils.gui.widget.Button;
import me.margiux.miniutils.gui.widget.ButtonWithData;
import me.margiux.miniutils.gui.widget.Input;
import me.margiux.miniutils.gui.MiniutilsGui;
import me.margiux.miniutils.mutable.MutableExtended;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AuctionSeller extends Module {
    public record Item(String itemName, long price, int quantity, int damage) {
    }

    public MutableExtended<List<Item>> sellItemList = new MutableExtended<>(new ArrayList<>());
    public me.margiux.miniutils.gui.widget.List<List<Item>, Item> itemList;

    public MutableExtended<String> idInput = new MutableExtended<>("");
    public Input<String> idField = new Input<>("*ID of the item", idInput, null, (e) -> idInput.setValue(e, true));
    public MutableExtended<Long> priceInput = new MutableExtended<>(0L);
    public Input<Long> priceField = new Input<>("*Price of the item", priceInput, Input.LONG_FILTER, (e) -> priceInput.setValue(Long.valueOf(e), true));
    public MutableExtended<Integer> quantityInput = new MutableExtended<>(0);
    public Input<Integer> quantityField = new Input<>("Quantity of the item", quantityInput, Input.INT_FILTER, (e) -> quantityInput.setValue(Integer.valueOf(e), true));
    public MutableExtended<Integer> damageInput = new MutableExtended<>(0);
    public Input<Integer> damageField = new Input<>("Damage of the item", damageInput, Input.INT_FILTER, (e) -> damageInput.setValue(Integer.valueOf(e), true));

    public Button addButton = new Button("Add", "Add item to the list", (b) -> {
        String id = idInput.getValue();
        long price = priceInput.getValue();
        int quantity = quantityInput.getValue();
        int damage = damageInput.getValue();
        if (Objects.equals(id, "") || price == 0L) return;
        idInput.setValue("", true);
        priceInput.setValue(0L, true);
        quantityInput.setValue(0, true);
        damageInput.setValue(0, true);
        Item item = new Item(id, price, (quantity == 0) ? 1 : quantity, (damage == 0) ? 1 : damage);
        sellItemList.getValue().add(item);
        sellItemList.onValueChanged();
    });


    public AuctionSeller(String name, String description) {
        super(name, description);
        this.itemList = new me.margiux.miniutils.gui.widget.List<>(sellItemList, new WPlainPanel(), (this::listToPanel));
        this.itemList.setHeight(20);
    }

    public PanelWithAlignment listToPanel(MutableExtended<List<Item>> list) {
        PanelWithAlignment p = new PanelWithAlignment();
        p.setAlignment(PanelWithAlignment.Alignment.Vertical);
        int pointerY = 0;
        for (Item item : list.getValue()) {
            ButtonWithData<Item> button = new ButtonWithData<>(item.itemName + " | " + item.price, "", (b) -> {
                list.getValue().remove(b.data);
                list.onValueChanged();
                MiniutilsGui.instance.validate();
            }, item);
            p.add(button, 0, pointerY, 110, 20);
            pointerY += button.getHeight() / 5 + 1;
        }
        return p;
    }

    @Override
    public void initGui() {
        MiniutilsGui.instance.main.add(toggleButton);
        MiniutilsGui.instance.main.add(idField);
        MiniutilsGui.instance.main.add(priceField);
        MiniutilsGui.instance.main.add(quantityField);
        MiniutilsGui.instance.main.add(damageField);
        MiniutilsGui.instance.main.add(addButton);

        MiniutilsGui.instance.main.add(itemList, 100);
    }
}
