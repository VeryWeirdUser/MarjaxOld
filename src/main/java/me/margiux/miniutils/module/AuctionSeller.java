package me.margiux.miniutils.module;

import io.github.cottonmc.cotton.gui.widget.WGridPanel;
import me.margiux.miniutils.gui.MiniutilsGui;
import me.margiux.miniutils.mutable.MutableObjectExtended;

import java.util.ArrayList;
import java.util.List;

public class AuctionSeller extends Module {
    public record Item(String itemName, int price) {
    }
    public MutableObjectExtended<List<Item>> sellItemList = new MutableObjectExtended<>(new ArrayList<>());
    public me.margiux.miniutils.gui.List<List<Item>, Item> itemList;


    public AuctionSeller(String name, String description) {
        super(name, description);
        this.itemList = new me.margiux.miniutils.gui.List<>(sellItemList, new WGridPanel(5), (this::listToPanel));
    }

    public WGridPanel listToPanel(List<Item> list) {
        return null;
    }

    @Override
    public void initGui() {
        MiniutilsGui.instance.add(toggleButton);
        MiniutilsGui.instance.add(itemList, 25, 5);
    }
}
