package me.margiux.miniutils.gui;

import io.github.cottonmc.cotton.gui.widget.WGridPanel;
import io.github.cottonmc.cotton.gui.widget.WScrollPanel;
import me.margiux.miniutils.module.AuctionSeller;
import me.margiux.miniutils.mutable.MutableObjectExtended;

import java.util.function.Function;

public class List<T extends java.util.List<R>, R> extends WScrollPanel implements Widget{
    public Function<T, WGridPanel> converter;
    public WGridPanel root;
    protected MutableObjectExtended<T> list;
    public List(MutableObjectExtended<T> list, WGridPanel panel, Function<T, WGridPanel> converter) {
        super(panel);
        this.root = panel;
        this.list = list;
        this.converter = converter;

    }

    public void listToPanel() {
        root = converter.apply(this.list.getValue());
    }
}