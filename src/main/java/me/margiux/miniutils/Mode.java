package me.margiux.miniutils;

import me.margiux.miniutils.utils.Enum;

public enum Mode implements Enum<Mode> {
    ENABLED("§aEnabled"),
    DISABLED("§cDisabled"),
    FORCE_DISABLED("§7Disabled", true);

    final boolean displayOnly;
    final String name;

    Mode(String name, boolean displayOnly) {
        this.name = name;
        this.displayOnly = displayOnly;
    }

    Mode(String name) {
        this(name, false);
    }

    @Override
    public Mode[] getEnumValues() {
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