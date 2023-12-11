package me.margiux.miniutils.gui.widget;

import com.mojang.blaze3d.systems.RenderSystem;
import me.margiux.miniutils.gui.Color;
import me.margiux.miniutils.gui.Gradient;
import me.margiux.miniutils.utils.RenderUtils;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;

public class Button extends Widget {
    protected Color strokeColor = new Gradient(0xFF87f2b2 - 0x00333333, 0xFF8BA8E0 - 0x00333333);
    public final PressAction onPress;

    public Button(int x, int y, int width, int height, String name, String description, PressAction onPress) {
        super(x, y, width, height, name, description);
        this.onPress = onPress;
        this.widgetColor = new Gradient(0xFF87f2b2, 0xFF8BA8E0);
    }

    public Button(int width, int height, String name, String description, PressAction onPress) {
        this(0, 0, width, height, name, description, onPress);
    }

    @SuppressWarnings("unused")
    public Button(String name, String description, PressAction onPress) {
        this(DEFAULT_WIDTH, DEFAULT_HEIGHT, name, description, onPress);
    }

    public void renderBackground(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        RenderUtils.setupRender();
        RenderSystem.enableDepthTest();
        RenderUtils.draw(matrices, this.x, this.y, this.x + this.width, this.y + this.height, strokeColor);
        RenderUtils.draw(matrices, this.x + 2, this.y + 2, this.x + this.width - 2, this.y + this.height - 2, widgetColor);
        RenderUtils.resetRender();
    }

    @Override
    public void renderText(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        RenderUtils.drawCenteredText(matrices, displayName, this.x + this.width / 2, this.y + (this.height - 8) / 2, 0xFFFFFF | MathHelper.ceil(this.alpha * 255.0f) << 24);
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        if (!this.visible) return;
        this.hovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;

        renderBackground(matrices, mouseX, mouseY, delta);
        renderText(matrices, mouseX, mouseY, delta);
    }

    @Override
    public void onClick(double mouseX, double mouseY, int button) {
        if (onPress != null) onPress.onPress(this, mouseX, mouseY, button);
    }

    @FunctionalInterface
    public interface PressAction {
        void onPress(Button button, double mouseX, double mouseY, int mouseButton);
    }
}
