package me.margiux.miniutils.module.setting;

import me.margiux.miniutils.Enum;

public class EnumSetting<T extends Enum<?>> extends Setting<T> {
    public EnumSetting(String name, String description, T data) {
        super(name, description, data);
    }

    public EnumSetting(String name, String description) {
        super(name, description);
    }
}
