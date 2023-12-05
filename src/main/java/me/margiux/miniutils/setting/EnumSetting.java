package me.margiux.miniutils.setting;

import me.margiux.miniutils.Enum;

public class EnumSetting<T extends Enum<T>> extends Setting<T> {
    public EnumSetting(String name, String description, T data) {
        super(name, description, data);
    }

    public EnumSetting(String name, String description) {
        super(name, description);
    }
}
