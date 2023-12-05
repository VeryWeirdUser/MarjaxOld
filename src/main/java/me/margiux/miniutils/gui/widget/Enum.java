package me.margiux.miniutils.gui.widget;

import me.margiux.miniutils.setting.EnumSetting;
import org.jetbrains.annotations.Nullable;

public class Enum<T extends me.margiux.miniutils.Enum<T>> extends Button {
    public EnumSetting<T> setting;
    public PressAction<T> onPress;

    public Enum(int x, int y, int width, int height, String name, String description, EnumSetting<T> setting, @Nullable PressAction<T> handler) {
        super(x, y, width, height, name, description, null);
        this.onPress = handler;
        this.name = name + ": " + setting.getData().getName();
        this.setting = setting;
    }

    public Enum(int width, int height, String name, String description, EnumSetting<T> setting, @Nullable PressAction<T> handler) {
        super(width, height, name, description, null);
        this.onPress = handler;
        this.name = name + ": " + setting.getData().getName();
        this.setting = setting;
    }

    @Override
    public void onClick(double mouseX, double mouseY, int button) {
        onPress.onPress(this, button);
    }

    @FunctionalInterface
    public interface PressAction<T extends me.margiux.miniutils.Enum<T>> {
        void onPress(Enum<T> enumWidget, int button);
    }
}
