package me.margiux.miniutils.module.setting;

public abstract class FieldSetting<T> extends Setting<T> {
    public FieldSetting(String name, String description, T data) {
        super(name, description, data);
    }

    public FieldSetting(String name, String description) {
        super(name, description);
    }
    public String getStringValue() {
        return String.valueOf(getData());
    }

    public abstract void parseAndSetValue(String value);
}
