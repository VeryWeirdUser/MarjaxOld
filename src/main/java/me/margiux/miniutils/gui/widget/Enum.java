package me.margiux.miniutils.gui.widget;

import io.github.cottonmc.cotton.gui.widget.WButton;
import me.margiux.miniutils.module.setting.EnumSetting;
import me.margiux.miniutils.utils.Mutable;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class Enum<T extends me.margiux.miniutils.Enum<T>> extends Button {
    public EnumSetting<T> setting;

    public Enum(int x, int y, int width, int height, String name, String description, EnumSetting<T> setting) {
        super(x, y, width, height, name, description, (b, mx, my, b1) -> {
            setting.setData(setting.getData().next());
            b.name = name + ": " + setting.getData().getName();
        });
        this.name = name + ": " + setting.getData().getName();
        this.setting = setting;
    }

    public Enum(int width, int height, String name, String description, EnumSetting<T> setting) {
        super(width, height, name, description, (b, mx, my, b1) -> {
            setting.setData(setting.getData().next());
            b.name = name + ": " + setting.getData().getName();
        });
        this.name = name + ": " + setting.getData().getName();
        this.setting = setting;
    }
}
