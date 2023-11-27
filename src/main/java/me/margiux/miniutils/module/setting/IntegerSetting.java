package me.margiux.miniutils.module.setting;

public class IntegerSetting extends FieldSetting<Integer> {
    public IntegerSetting(String name, String description, Integer data) {
        super(name, description, data);
    }

    public IntegerSetting(String name, String description) {
        super(name, description);
    }

    @Override
    public void parseAndSetValue(String value) {
        try {
            this.setData(Integer.parseInt(value));
        } catch (NumberFormatException ignored) {
        }
    }
}
