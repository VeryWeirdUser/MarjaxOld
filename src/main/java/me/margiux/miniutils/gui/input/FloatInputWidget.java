package me.margiux.miniutils.gui.input;

import me.margiux.miniutils.mutable.MutableFloatExtended;

import java.util.Objects;

public class FloatInputWidget extends InputWidget<Float> {
    public FloatInputWidget(String name, String description, MutableFloatExtended input) {
        super(name, description, input.getValue());
        this.setText(input.getValue().toString());
        this.setChangedListener((newInput) -> {
            if (!Objects.equals(newInput, "")) input.setValue(Float.valueOf(newInput));
        });
        input.setOnValueChanged((newValue) -> {

        });
    }

    @Override
    public String getDefaultValue() {
        return this.defaultValue.toString();
    }
}
