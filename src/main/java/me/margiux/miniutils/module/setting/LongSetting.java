package me.margiux.miniutils.module.setting;

public class LongSetting extends FieldSetting<Long> {
    public LongSetting(String name, String description, Long data) {
        super(name, description, data);
    }

    public LongSetting(String name, String description) {
        super(name, description);
    }

    @Override
    public void parseAndSetValue(String value) {
        try {
            this.setData(Long.parseLong(value));
        } catch (NumberFormatException ignored) {
        }
    }
}
