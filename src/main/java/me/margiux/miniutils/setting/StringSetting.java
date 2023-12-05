package me.margiux.miniutils.setting;

public class StringSetting extends FieldSetting<String> {
    public StringSetting(String name, String description, String data) {
        super(name, description, data);
    }

    public StringSetting(String name, String description) {
        super(name, description);
    }

    @Override
    public void parseAndSetValue(String value) {
        setData(value);
    }
}
