package me.margiux.miniutils.module;

public enum Category {
    COMBAT("Combat"),
    VISUAL("Visual"),
    MISC("Miscellaneous"),
    WORLD("World");
    final String name;
    Category(String name) {
        this.name = name;
    }
}
