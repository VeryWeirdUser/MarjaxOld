package me.margiux.miniutils;

import java.util.ArrayList;
import java.util.List;

public class CheatMode extends Enum<CheatMode> {
    public static List<CheatMode> enumList = new ArrayList<>();
    public static final CheatMode ENABLED = new CheatMode("§aEnabled", 0, false);
    public static final CheatMode DISABLED = new CheatMode("§cDisabled", 1, false);
    public static final CheatMode PANIC = new CheatMode("§c&lPANIC", 2, true);

    public CheatMode(String name, int ordinal, boolean displayOnly) {
        super(name, ordinal, displayOnly);
        enumList.add(this);
    }

    @Override
    public CheatMode next() {
        for (CheatMode mode : enumList) {
            if (mode.ordinal <= this.ordinal) continue;
            if (mode.isDisplayOnly()) continue;
            return mode;
        }
        return enumList.get(0);
    }
}
