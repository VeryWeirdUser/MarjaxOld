package me.margiux.miniutils.gui.widget;

import me.margiux.miniutils.setting.EnumSetting;
import net.minecraft.client.util.math.MatrixStack;
import org.jetbrains.annotations.Nullable;

public class Enum<T extends me.margiux.miniutils.Enum<T>> extends Button {
    public EnumSetting<T> setting;
    public PressAction<T> onPress;

    public Enum(int x, int y, int width, int height, String name, String description, EnumSetting<T> setting, @Nullable PressAction<T> handler) {
        super(x, y, width, height, name, description, null);
        this.onPress = handler;
        this.setting = setting;
        refreshDisplayName();
    }

    public Enum(int width, int height, String name, String description, EnumSetting<T> setting, @Nullable PressAction<T> handler) {
        this(0, 0, width, height, name, description, setting, handler);
    }

    @SuppressWarnings("unused")
    public Enum(String name, String description, EnumSetting<T> setting, @Nullable PressAction<T> handler) {
        this(DEFAULT_WIDTH, DEFAULT_HEIGHT, name, description, setting, handler);
    }

    @Override
    public void onClick(double mouseX, double mouseY, int button) {
        if (onPress != null) onPress.onPress(this, button);
        else {
            setting.setData(setting.getData().getNext());
            refreshDisplayName();
        }
    }

    @FunctionalInterface
    public interface PressAction<T extends me.margiux.miniutils.Enum<T>> {
        void onPress(Enum<T> enumWidget, int button);
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        refreshDisplayName();
        super.render(matrices, mouseX, mouseY, delta);
    }

    @Override
    public void refreshDisplayName() {
        if (displayNameSupplier != null) displayName = displayNameSupplier.get();
        else displayName = name + ": " + setting.getData().getName();
    }
}
