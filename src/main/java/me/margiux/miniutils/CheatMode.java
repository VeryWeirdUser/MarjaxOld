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
    public CheatMode[] getEnumValues() {
        return values();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean isDisplayOnly() {
        return displayOnly;
    }
}
