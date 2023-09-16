package me.margiux.miniutils.module;

import me.margiux.miniutils.gui.MiniutilsGui;
import me.margiux.miniutils.mutable.MutableObjectExtended;

import java.util.List;

public class AuctionSeller extends Module {
    public record Item(String itemName, int price) {

    }
    public MutableObjectExtended<List<Item>> sellItemList;
    public me.margiux.miniutils.gui.List<List<Item>, Item> itemList;


    public AuctionSeller(String name, String description) {
        super(name, description);
    }

    public void tick() {

    }

    @Override
    public void initGui() {
        MiniutilsGui.instance.add(toggleButton);
        MiniutilsGui.instance.add(l, );
    }
}
