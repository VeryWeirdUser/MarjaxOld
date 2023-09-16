package me.margiux.miniutils.gui.input;

import org.apache.commons.lang3.mutable.MutableObject;

import java.util.Objects;

public class StringInput extends InputWidget<String> {
    public StringInput(String name, String description, MutableObject<String> input) {
        super(name, description, input.getValue());
        this.setText(input.getValue());
        this.setChangedListener((newInput) -> {
            if (!Objects.equals(newInput, "")) input.setValue(newInput);
        });
    }

    @Override
    public String getDefaultValue() {
        return this.defaultValue;
    }
}
