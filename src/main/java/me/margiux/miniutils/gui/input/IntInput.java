package me.margiux.miniutils.gui.input;

import me.margiux.miniutils.mutable.MutableIntExtended;
import org.apache.commons.lang3.mutable.MutableInt;

import java.util.Objects;

public class IntInput extends InputWidget<Integer> {
    public IntInput(String name, String description, MutableIntExtended input) {
        super(name, description, input.getValue());
        this.setText(input.getValue().toString());
        this.setChangedListener((newInput) -> {
            if (!Objects.equals(newInput, "")) input.setValue(Integer.valueOf(newInput));
        });
    }

    @Override
    public String getDefaultValue() {
        return this.defaultValue.toString();
    }
}
