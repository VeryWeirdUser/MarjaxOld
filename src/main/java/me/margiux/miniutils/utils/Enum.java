package me.margiux.miniutils.utils;

public interface Enum<T extends Enum<T>> {
    T[] getEnumValues();
    String getName();
    default T getNext() {
        int thisIndex = 0;
        for (int i = 0; i < getEnumValues().length; i++) {
            if (getEnumValues()[i] == this) {
                thisIndex = i;
                break;
            }
        }
        for (int i = thisIndex; i < getEnumValues().length; i++) {
            if (i != thisIndex && !getEnumValues()[i].isDisplayOnly()) return getEnumValues()[i];
        }
        return getEnumValues()[0];
    }
    boolean isDisplayOnly();
}
