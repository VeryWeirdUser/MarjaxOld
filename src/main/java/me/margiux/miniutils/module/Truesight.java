package me.margiux.miniutils.module;

import me.margiux.miniutils.gui.MiniutilsGui;
import me.margiux.miniutils.utils.HudUtil;

public class Truesight extends Module {
    public Truesight(String name, String description) {
        super(name, description);
    }

    @Override
    public void onEnable() {
        HudUtil.setSubTitle("§7Truesight: §aEnabled");
    }

    @Override
    public void onDisable() {
        HudUtil.setSubTitle("§7Truesight: §4Disabled");
    }

    @Override
    public void initGui() {
        MiniutilsGui.instance.main.add(toggleButton);
    }
}
