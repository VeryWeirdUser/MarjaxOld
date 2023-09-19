package me.margiux.miniutils.gui;

import io.github.cottonmc.cotton.gui.widget.WPlainPanel;
import io.github.cottonmc.cotton.gui.widget.WGridPanel;
import io.github.cottonmc.cotton.gui.widget.WWidget;
import io.github.cottonmc.cotton.gui.widget.data.Insets;
import me.margiux.miniutils.Mode;
import me.margiux.miniutils.gui.widget.Enum;

public class Panel extends WPlainPanel {
    public Panel(int width, int height) {
        this.setSize(width, height);
    }

    public Panel() {
    }

    @Override
    protected void expandToFit(WWidget w, Insets insets) {
        super.expandToFit(w, insets);
    }
}
