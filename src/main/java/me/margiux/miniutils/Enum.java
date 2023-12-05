package me.margiux.miniutils;

public interface Enum<T> {
    String getName();
    boolean isDisplayOnly();
    T getNext();
}
