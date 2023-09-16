package me.margiux.miniutils.gui;

import io.github.cottonmc.cotton.gui.client.LightweightGuiDescription;
import io.github.cottonmc.cotton.gui.widget.*;
import io.github.cottonmc.cotton.gui.widget.data.Insets;

public class MiniutilsGui extends LightweightGuiDescription {
    public static MiniutilsGui instance;
    public WPlainPanel root;
    public WGridPanel main;
    int pointerX = 0;
    int pointerY = 0;

    public MiniutilsGui() {
        root = new WPlainPanel();
        main = new WGridPanel(5);
        setRootPanel(root);

        root.setSize(510, 490);
        root.setInsets(Insets.ROOT_PANEL);

        main.setSize(500, 450);

        root.add(main, 0, 0);
        root.validate(this);
    }

    public <T extends WWidget> void add(T e) {
        add(e, pointerX, pointerY, main);
        pointerY += 5;
        if (pointerY + 5 > 450 / 5) {
            pointerY = 0;
            pointerX += 30;
        }
    }

    public <T extends WWidget> void add(T e, int width, int height) {
        add(e, pointerX, pointerY, width, height, main);
        pointerY += 1 + height / 5;
        if (pointerY + 5 > 450 / 5) {
            pointerY = 0;
            pointerX += 30;
        }
    }

    public <T extends WWidget, R extends WPanel> void add(T e, int x, int y, R panel) {
        add(e, x, y, 25, 5, panel);
    }

    public <T extends WWidget, R extends WPanel> void add(T e, int x, int y, int width, int height, R panel) {
        if (panel instanceof WGridPanel p) p.add(e, x, y, width, height);
        if (panel instanceof WPlainPanel p) p.add(e, x, y, width, height);
        panel.validate(this);
    }

    public MiniutilsScreen getScreen() {
        return new MiniutilsScreen(instance);
    }
}
