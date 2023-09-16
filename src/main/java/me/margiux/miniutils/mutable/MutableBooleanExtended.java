package me.margiux.miniutils.mutable;

import java.util.function.Consumer;

public class MutableBooleanExtended extends org.apache.commons.lang3.mutable.MutableBoolean {
    protected Consumer<Boolean> valueChangedListener;

    @Override
    public void setValue(Boolean value) {
        super.setValue(value);
        this.onValueChanged();
    }

    @Override
    public void setValue(boolean value) {
        super.setValue(value);
        this.onValueChanged();
    }

    public void setOnValueChanged(Consumer<Boolean> task) {
        valueChangedListener = task;
    }

    public void onValueChanged() {
        if (valueChangedListener != null) valueChangedListener.accept(getValue());
    }
}
