package me.margiux.miniutils.gui;

import io.github.cottonmc.cotton.gui.GuiDescription;
import io.github.cottonmc.cotton.gui.widget.WWidget;

public class PanelWithAlignment extends Panel {
    int pointerX = 0;
    int pointerY = 0;
    public enum Alignment {
        Vertical, Both
    }

    public Alignment alignment = Alignment.Both;

    public void setAlignment(Alignment alignment) {
        this.alignment = alignment;
    }

    public PanelWithAlignment(int width, int height) {
        super(width, height);
    }

    public PanelWithAlignment() {
        super();
    }

    public void add(WWidget e) {
        add(e, pointerX, pointerY);
        pointerY += 5 + e.getHeight();
        if (pointerY + 5 > this.parent.getHeight() && alignment == Alignment.Both) {
            pointerY = 0;
            pointerX += 125;
        }
    }

    public void add(WWidget e, int height) {
        add(e, pointerX, pointerY, 120, height);
        pointerY += 5 + e.getHeight();
        if (pointerY + 5 > this.parent.getHeight() && alignment == Alignment.Both) {
            pointerY = 0;
            pointerX += 125;
        }
    }

    @Override
    public void add(WWidget e, int x, int y) {
        add(e, x, y, 120, 25);
    }

    public void add(WWidget e, int x, int y, int width, int height) {
        super.add(e, x, y, width, height);
    }

    @Override
    public void validate(GuiDescription c) {
        pointerX = 0;
        pointerY = 0;
        children.forEach((child) -> {
            child.setLocation(pointerX, pointerY);
            pointerY += 5 + child.getHeight();
            if (pointerY + 5 > this.parent.getHeight() && alignment == Alignment.Both) {
                pointerY = 0;
                pointerX += 125;
            }
        });
        super.validate(c);
    }
}
