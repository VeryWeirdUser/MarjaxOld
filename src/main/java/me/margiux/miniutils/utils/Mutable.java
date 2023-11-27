package me.margiux.miniutils.utils;

public class Mutable<T> extends org.apache.commons.lang3.mutable.MutableObject<T> {

    public Mutable() {
        super();
    }

    public Mutable(T value) {
        super(value);
    }
}
