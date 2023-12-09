package me.margiux.miniutils.setting;

import me.margiux.miniutils.gui.widget.Field;
import me.margiux.miniutils.gui.widget.Widget;

import java.util.function.Predicate;

public class FieldSetting extends Setting<String> {
    public Predicate<String> predicate = null;

    public FieldSetting(String name, String description, String data) {
        super(name, description, data);
    }

    public FieldSetting(String name, String description) {
        super(name, description);
    }

    public void setData(Integer data) {
        setData(String.valueOf(data));
    }

    public void setData(Double data) {
        setData(String.valueOf(data));
    }

    public void setData(Long data) {
        setData(String.valueOf(data));
    }

    public void setData(Float data) {
        setData(String.valueOf(data));
    }

    public Integer getIntegerData() {
        try {
            return Integer.parseInt(getData());
        } catch (NumberFormatException ignored) {
        }
        return 0;
    }

    public Double getDoubleData() {
        try {
            return Double.parseDouble(getData());
        } catch (NumberFormatException ignored) {
        }
        return 0d;
    }

    public Float getFloatData() {
        try {
            return Float.parseFloat(getData());
        } catch (NumberFormatException ignored) {
        }
        return 0f;
    }

    public Long getLongData() {
        try {
            return Long.parseLong(getData());
        } catch (NumberFormatException ignored) {
        }
        return 0L;
    }

    public Widget makeWidget(int width, int height) {
        Field field = new Field(width, height, name, description, this);
        if (predicate != null) field.setTextPredicate(predicate);
        return field;
    }

    public Widget makeWidget() {
        return makeWidget(Widget.DEFAULT_WIDTH, Widget.DEFAULT_HEIGHT - 5);
    }
}
