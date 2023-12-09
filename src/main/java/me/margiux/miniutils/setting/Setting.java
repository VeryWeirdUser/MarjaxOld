package me.margiux.miniutils.setting;

import me.margiux.miniutils.utils.Mutable;

public abstract class Setting<T> {
    protected final String name;
    protected final String description;
    protected final Mutable<T> data;

    public Setting(String name, String description, T data) {
        this.name = name;
        this.description = description;
        this.data = new Mutable<>(data);
    }

    public Setting(String name, String description) {
        this(name, description, null);
    }

    public T getData() {
        return data.getValue();
    }

    public void setData(T data) {
        this.data.setValue(data);
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
