package me.margiux.miniutils.gui.widget;

import io.github.cottonmc.cotton.gui.widget.WTextField;
import me.margiux.miniutils.mutable.MutableExtended;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class Input<T> extends WTextField implements Widget {
    private boolean textChanged = false;
    public final static Predicate<String> INT_FILTER = (e) -> {
        if (Objects.equals(e, "")) return true;
        try {
            Integer.valueOf(e);
        } catch (NumberFormatException exception) {
            return false;
        }
        return true;
    };
    public final static Predicate<String> LONG_FILTER = (e) -> {
        if (Objects.equals(e, "")) return true;
        try {
            Long.valueOf(e);
        } catch (NumberFormatException exception) {
            return false;
        }
        return true;
    };
    public final static Predicate<String> FLOAT_FILTER = (e) -> {
        if (Objects.equals(e, "")) return true;
        try {
            Float.valueOf(e);
        } catch (NumberFormatException exception) {
            return false;
        }
        return true;
    };
    public final String description;
    public final MutableExtended<T> input;

    public Input(String description, MutableExtended<T> input,
                 @Nullable Predicate<String> predicate, @Nullable Consumer<String> consumer) {
        this.setMaxLength(64);
        this.description = description;
        this.input = input;
        this.setTextPredicate(predicate);
        this.setChangedListener((e) -> {
            if (textChanged) {
                textChanged = false;
                return;
            }
            if (consumer != null)
                if (!(Objects.equals(e, "") && (predicate == INT_FILTER || predicate == FLOAT_FILTER || predicate == LONG_FILTER)))
                    consumer.accept(e);
        });
        this.input.setOnValueChanged((value) -> {
            textChanged = true;
            this.setText(String.valueOf(input));
        });
        this.setSuggestion(Text.literal(this.description));
    }

    @Override
    public void onCharTyped(char ch) {
        if (this.getCursor() > getText().length()) this.setCursorPos(this.getText().length() - 1);
        if (this.getCursor() < 0) this.setCursorPos(0);
        super.onCharTyped(ch);
    }

    @Override
    public void onKeyPressed(int ch, int key, int modifiers) {
        if (this.getCursor() > getText().length()) this.setCursorPos(this.getText().length() - 1);
        if (this.getCursor() < 0) this.setCursorPos(0);
        super.onKeyPressed(ch, key, modifiers);
    }
}