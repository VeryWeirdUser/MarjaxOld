package me.margiux.miniutils.setting;

public class DoubleSetting extends FieldSetting<Double> {
    public DoubleSetting(String name, String description, Double data) {
        super(name, description, data);
    }

    public DoubleSetting(String name, String description) {
        super(name, description);
    }

    @Override
    public void parseAndSetValue(String value) {
        try {
            this.setData(Double.parseDouble(value));
        } catch (NumberFormatException ignored) {
        }
    }
}
