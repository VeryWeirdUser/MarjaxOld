package me.margiux.miniutils.setting;

public class FloatSetting extends FieldSetting<Float> {
    public FloatSetting(String name, String description, Float data) {
        super(name, description, data);
    }

    public FloatSetting(String name, String description) {
        super(name, description);
    }

    @Override
    public void parseAndSetValue(String value) {
        try {
            this.setData(Float.parseFloat(value));
        } catch (NumberFormatException ignored) {
        }
    }
}
