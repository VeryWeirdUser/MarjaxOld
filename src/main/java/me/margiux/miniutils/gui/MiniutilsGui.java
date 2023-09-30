package me.margiux.miniutils.gui;

import io.github.cottonmc.cotton.gui.client.BackgroundPainter;
import io.github.cottonmc.cotton.gui.client.LightweightGuiDescription;
import io.github.cottonmc.cotton.gui.widget.*;
import io.github.cottonmc.cotton.gui.widget.data.Insets;

public class MiniutilsGui extends LightweightGuiDescription {
    public static MiniutilsGui instance;
    public WPlainPanel root;
    public PanelWithAlignment main;

    public MiniutilsGui() {
        root = new WPlainPanel();
        main = new PanelWithAlignment(500, 450);
        setRootPanel(root);

        root.setSize(510, 490);
        root.setInsets(Insets.ROOT_PANEL);

        main.setSize(500, 450);

        root.add(main, 0, 0);
        root.validate(this);

        root.setBackgroundPainter(BackgroundPainter.SLOT);
        root.getBackgroundPainter();
    }
    public MiniutilsScreen getScreen() {
        return new MiniutilsScreen(instance);
    }

    public void validate() {
        root.validate(this);
    }
}
