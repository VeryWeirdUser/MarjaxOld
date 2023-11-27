package me.margiux.miniutils.gui.widget;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;

import java.util.function.Consumer;

public class Button extends Widget {
    public PressAction onPress;
    public Button(int x, int y, int width, int height, String name, String description, PressAction onPress) {
        super(x, y, width, height, name, description);
        this.onPress = onPress;
    }

    public Button(int width, int height, String name, String description, PressAction onPress) {
        super(width, height, name, description);
        this.onPress = onPress;
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        if (!this.visible) {
            return;
        }
        this.hovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
        MinecraftClient minecraftClient = MinecraftClient.getInstance();
        TextRenderer textRenderer = minecraftClient.textRenderer;
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, WIDGETS_TEXTURE);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, this.alpha);
        int i = this.getYImage(this.isHovered());
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableDepthTest();
        DrawableHelper.fill(matrices, this.x, this.y, this.x + this.width, this.y + this.height, 0x55092D49);
        DrawableHelper.fill(matrices, this.x + 2, this.y + 2, this.x + this.width - 2, this.y + this.height - 2, 0x550887E7);

        this.renderBackground(matrices, minecraftClient, mouseX, mouseY);
        int j = this.active ? 0xFFFFFF : 0xA0A0A0;
        ClickableWidget.drawCenteredText(matrices, textRenderer, name, this.x + this.width / 2, this.y + (this.height - 8) / 2, j | MathHelper.ceil(this.alpha * 255.0f) << 24);
    }

    @Override
    public void onClick(double mouseX, double mouseY, int button) {
        onPress.onPress(this, mouseX, mouseY, button);
    }

    @FunctionalInterface
    public interface PressAction {
        void onPress(Button button, double mouseX, double mouseY, int mouseButton);
    }
}
