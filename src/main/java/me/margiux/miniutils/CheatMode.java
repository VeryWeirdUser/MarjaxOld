package me.margiux.miniutils;

import me.margiux.miniutils.utils.Enum;

public enum CheatMode implements Enum<CheatMode> {
    ENABLED("§aEnabled"),
    DISABLED("§cDisabled"),
    PANIC("§c§lPANIC", true);

    final String name;
    final boolean displayOnly;

    CheatMode(String name, boolean displayOnly) {
        this.name = name;
        this.displayOnly = displayOnly;
    }

    CheatMode(String name) {
        this(name, false);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public CheatMode getNext() {
        int thisIndex = 0;
        for (int i = 0; i < values().length; i++) {
            if (values()[i] == this) {
                thisIndex = i;
                break;
            }
        }
        for (int i = thisIndex; i < values().length; i++) {
            if (!values()[i].displayOnly && i != thisIndex) return values()[i];
        }
        return values()[0];
    }
}
