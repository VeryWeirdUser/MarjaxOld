package me.margiux.miniutils.mutable;

import java.util.function.Consumer;

public class MutableFloatExtended extends org.apache.commons.lang3.mutable.MutableFloat {
    protected Consumer<Float> valueChangedListener;

    public MutableFloatExtended(float value) {
        super(value);
    }

    public MutableFloatExtended(Number value) {
        super(value);
    }

    public MutableFloatExtended(String value) {
        super(value);
    }

    @Override
    public void setValue(float value) {
        super.setValue(value);
        this.onValueChanged();
    }

    @Override
    public void setValue(Number value) {
        super.setValue(value);
        this.onValueChanged();
    }

    public void setOnValueChanged(Consumer<Float> task) {
        valueChangedListener = task;
    }

    public void onValueChanged() {
        if (valueChangedListener != null) valueChangedListener.accept(getValue());
    }
}