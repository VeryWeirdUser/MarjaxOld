package me.margiux.miniutils.mutable;

import java.util.function.Consumer;

public class MutableExtended<T> extends org.apache.commons.lang3.mutable.MutableObject<T> {
    protected Consumer<T> valueChangedListener;

    public MutableExtended() {
        super();
    }

    public MutableExtended(T value) {
        super(value);
    }

    public void setValue(T value, boolean notify) {
        super.setValue(value);
        if (notify) this.onValueChanged();
    }

    public void setOnValueChanged(Consumer<T> task) {
        valueChangedListener = task;
    }

    public void onValueChanged() {
        if (valueChangedListener != null) valueChangedListener.accept(getValue());
    }
}
