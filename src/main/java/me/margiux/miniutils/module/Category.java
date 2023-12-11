package me.margiux.miniutils.module;

public enum Category {
    COMBAT("Combat"),
    VISUAL("Visual"),
    PLAYER("Player"),
    MISC("Miscellaneous"),
    WORLD("World");
    public final String name;
    Category(String name) {
        this.name = name;
    }
}
