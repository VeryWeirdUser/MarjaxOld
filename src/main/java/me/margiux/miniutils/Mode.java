package me.margiux.miniutils;

import java.util.ArrayList;
import java.util.List;

public class Mode extends Enum<Mode> {
    protected static final List<Mode> enumList = new ArrayList<>();
    public static final Mode ENABLED = new Mode("§aEnabled", 0, false);
    public static final Mode DISABLED = new Mode("§cDisabled", 1, false);
    public static final Mode FORCE_DISABLED = new Mode("§7Disabled", 2, true);

    public Mode(String name, int ordinal, boolean displayOnly) {
        super(name, ordinal, displayOnly);
        enumList.add(this);
    }

    @Override
    public Mode next() {
        for (Mode mode : enumList) {
            if (mode.ordinal <= this.ordinal) continue;
            if (mode.isDisplayOnly()) continue;
            return mode;
        }
        return enumList.get(0);
    }
}
