package me.margiux.miniutils.mutable;

import java.util.function.Consumer;

public class MutableObjectExtended<T> extends org.apache.commons.lang3.mutable.MutableObject<T> {
    protected Consumer<T> valueChangedListener;

    public MutableObjectExtended() {
        super();
    }

    public MutableObjectExtended(T value) {
        super(value);
    }

    @Override
    public void setValue(T value) {
        super.setValue(value);
        this.onValueChanged();
    }

    public void setOnValueChanged(Consumer<T> task) {
        valueChangedListener = task;
    }

    public void onValueChanged() {
        if (valueChangedListener != null) valueChangedListener.accept(getValue());
    }
}
