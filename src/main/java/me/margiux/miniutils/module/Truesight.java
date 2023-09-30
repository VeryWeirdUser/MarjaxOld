package me.margiux.miniutils.module;

import me.margiux.miniutils.gui.MiniutilsGui;
import me.margiux.miniutils.utils.HudUtil;

public class Truesight extends Module {
    public Truesight(String name, String description, int activationKey) {
        super(name, description, activationKey);
    }

    @Override
    public void initGui() {
        MiniutilsGui.instance.main.add(toggleButton);
    }
}
