package me.margiux.miniutils.module;

public enum Category {
    COMBAT("Combat"),
    VISUAL("Visual"),
    MISC("Miscellaneous");
    final String name;
    Category(String name) {
        this.name = name;
    }
}
