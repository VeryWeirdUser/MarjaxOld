package me.margiux.miniutils;

public abstract class Enum<T> {
    public final String name;
    public final int ordinal;
    public final boolean displayOnly;
    public Enum(String name, int ordinal, boolean displayOnly) {
        this.name = name;
        this.ordinal = ordinal;
        this.displayOnly = displayOnly;
    }

    public String getName() {
        return name;
    }

    public int getOrdinal() {
        return ordinal;
    }

    public boolean isDisplayOnly() {
        return displayOnly;
    }

    public abstract T next();
}
