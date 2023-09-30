package me.margiux.miniutils.gui.widget;

import io.github.cottonmc.cotton.gui.widget.WPlainPanel;
import io.github.cottonmc.cotton.gui.widget.WScrollPanel;
import me.margiux.miniutils.gui.MiniutilsGui;
import me.margiux.miniutils.gui.PanelWithAlignment;
import me.margiux.miniutils.utils.Mutable;

import java.util.function.Function;

public class List<T extends java.util.List<R>, R> extends WScrollPanel implements Widget {
    protected int height = 20;
    public Function<Mutable<T>, PanelWithAlignment> converter;
    public WPlainPanel root;
    public PanelWithAlignment main;
    protected Mutable<T> list;

    public List(Mutable<T> list, WPlainPanel panel, Function<Mutable<T>, PanelWithAlignment> converter) {
        super(panel);
        this.root = panel;
        this.main = new PanelWithAlignment();
        this.root.add(this.main, 0, 0);
        this.list = list;
        this.converter = converter;
        listToPanel();
        list.setOnValueChanged((newValue) -> listToPanel());
    }

    public void listToPanel() {
        root.remove(main);
        main = converter.apply(this.list);
        root.add(main, 0, 0);
        if (root.getHeight() > main.getHeight() && root.getHeight() > this.height) root.setSize(this.getWidth(), main.getHeight());
        layout();
        MiniutilsGui.instance.validate();
    }

    public void setHeight(int height) {
        this.height = height;
    }
}