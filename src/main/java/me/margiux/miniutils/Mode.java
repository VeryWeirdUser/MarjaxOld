package me.margiux.miniutils;

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
    public String getName() {
        return name;
    }

    @Override
    public boolean isDisplayOnly() {
        return displayOnly;
    }

    @Override
    public Mode getNext() {
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