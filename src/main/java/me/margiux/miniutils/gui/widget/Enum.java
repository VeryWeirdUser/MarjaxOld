package me.margiux.miniutils.gui.widget;

import com.mojang.blaze3d.systems.RenderSystem;
import me.margiux.miniutils.Main;
import me.margiux.miniutils.setting.EnumSetting;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import org.jetbrains.annotations.Nullable;

public class Enum<T extends me.margiux.miniutils.utils.Enum<T>> extends Button {
    public final EnumSetting<T> setting;
    public final PressAction<T> onPress;
    public boolean displayInSingleLine = false;

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
    public interface PressAction<T extends me.margiux.miniutils.utils.Enum<T>> {
        void onPress(Enum<T> enumWidget, int button);
    }

    @Override
    public void renderBackground(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        if (displayInSingleLine) {
            super.renderBackground(matrices, mouseX, mouseY, delta);
            return;
        }
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, this.alpha);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableDepthTest();
        DrawableHelper.fill(matrices, this.x, this.y + 10, this.x + this.width, this.y + this.height, 0x55092D49);
        DrawableHelper.fill(matrices, this.x + 2, this.y + 12, this.x + this.width - 2, this.y + this.height - 2, 0x550887E7);
    }

    @Override
    public void renderText(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        if (displayInSingleLine) {
            super.renderText(matrices, mouseX, mouseY, delta);
            return;
        }
        TextRenderer textRenderer = Main.instance.getClient().textRenderer;
        textRenderer.draw(matrices, name + ":", this.x + 3, this. y + 2, 0xFFFFFFFF);
        textRenderer.draw(matrices, setting.getData().getName(), this.x + 3, this. y + 16, 0xFFFFFFFF);
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        refreshDisplayName();
        if (displayInSingleLine) setHeight(DEFAULT_HEIGHT);
        else setHeight(30);
        super.render(matrices, mouseX, mouseY, delta);
    }

    @Override
    public void refreshDisplayName() {
        if (displayNameSupplier != null) displayName = displayNameSupplier.get();
        else displayName = name + ": " + setting.getData().getName();
    }
}
