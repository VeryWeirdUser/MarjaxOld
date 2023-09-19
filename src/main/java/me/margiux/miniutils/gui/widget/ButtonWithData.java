package me.margiux.miniutils.gui.widget;

import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class ButtonWithData <T> extends Button implements Widget {
    public T data;

    public ButtonWithData(String name, String description, @Nullable Consumer<ButtonWithData<T>> consumer, T data) {
        super(name, description, null);
        this.data = data;
        this.setConsumerWithData(consumer);
    }

    public ButtonWithData<T> setConsumerWithData(Consumer<ButtonWithData<T>> consumer) {
        this.setOnClick(() -> consumer.accept(this));
        return this;
    }
}