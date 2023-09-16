package me.margiux.miniutils.gui;

import io.github.cottonmc.cotton.gui.widget.WGridPanel;
import io.github.cottonmc.cotton.gui.widget.WScrollPanel;
import io.github.cottonmc.cotton.gui.widget.WWidget;
import me.margiux.miniutils.Linkable;

public class List<T extends java.util.List<R>, R extends Linkable<>> extends WScrollPanel implements Widget{
    public WGridPanel root = new WGridPanel();
    protected T list;
    public List(T list, WGridPanel panel) {
        super(panel);
        this.root = panel;
        this.list = list;
    }

    public void listToPanel() {
        for (R e : list) {
            e.
        }
    }
}