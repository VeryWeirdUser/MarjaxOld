package me.margiux.miniutils.setting;

public class BooleanSetting extends Setting<BooleanSetting> {
    public BooleanSetting(String name, String description, BooleanSetting data) {
        super(name, description, data);
    }

    public BooleanSetting(String name, String description) {
        super(name, description);
    }
}
