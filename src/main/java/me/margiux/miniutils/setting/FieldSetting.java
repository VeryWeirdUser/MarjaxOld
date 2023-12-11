package me.margiux.miniutils.setting;

import me.margiux.miniutils.gui.widget.Field;
import me.margiux.miniutils.gui.widget.Widget;
import org.jetbrains.annotations.Nullable;

import java.util.function.Predicate;

public class FieldSetting extends Setting<String> {
    @Nullable
    public final Predicate<String> predicate;

    public FieldSetting(String name, String description, String data, @Nullable Predicate<String> predicate) {
        super(name, description, data);
        this.predicate = predicate;
    }

    public FieldSetting(String name, String description, @Nullable Predicate<String> predicate) {
        this(name, description, "", predicate);
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

    public Widget makeWidget(int width) {
        Field field = new Field(width, name, description, this);
        if (predicate != null) field.setTextPredicate(predicate);
        return field;
    }

    public Widget makeWidget() {
        return makeWidget(Widget.DEFAULT_WIDTH);
    }
}
