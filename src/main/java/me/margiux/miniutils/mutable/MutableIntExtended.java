package me.margiux.miniutils.mutable;

import java.util.function.Consumer;

public class MutableIntExtended extends org.apache.commons.lang3.mutable.MutableInt {
    protected Consumer<Integer> valueChangedListener;

    public MutableIntExtended(int value) {
        super(value);
    }

    public MutableIntExtended(Number value) {
        super(value);
    }

    public MutableIntExtended(String value) {
        super(value);
    }

    @Override
    public void setValue(int value) {
        super.setValue(value);
        this.onValueChanged();
    }

    @Override
    public void setValue(Number value) {
        super.setValue(value);
        this.onValueChanged();
    }

    public void setOnValueChanged(Consumer<Integer> task) {
        valueChangedListener = task;
    }

    public void onValueChanged() {
        if (valueChangedListener != null) valueChangedListener.accept(getValue());
    }
}