package me.margiux.miniutils.setting;

import me.margiux.miniutils.utils.Enum;
import me.margiux.miniutils.gui.widget.Widget;

public class EnumSetting<T extends Enum<T>> extends Setting<T> {
    public EnumSetting(String name, String description, T data) {
        super(name, description, data);
    }

    public EnumSetting(String name, String description) {
        super(name, description);
    }

    public Widget makeWidget(int width, int height) {
        return new me.margiux.miniutils.gui.widget.Enum<>(width, height, name, description, this, null);
    }

    public Widget makeWidget() {
        return makeWidget(Widget.DEFAULT_WIDTH, Widget.DEFAULT_HEIGHT);
    }
}
