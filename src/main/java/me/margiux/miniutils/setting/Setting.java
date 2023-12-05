package me.margiux.miniutils.setting;

import me.margiux.miniutils.utils.Mutable;

public class Setting<T> {
    protected String name;
    protected String description;
    protected Mutable<T> data;

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
