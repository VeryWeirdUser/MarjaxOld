package me.margiux.miniutils.utils;

import com.mojang.blaze3d.systems.RenderSystem;
import me.margiux.miniutils.Main;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.OrderedText;
import net.minecraft.util.math.Matrix4f;

public interface DrawUtils {
    static void verticalGradient(MatrixStack matrices, int left, int top, int right, int bottom, int startColor, int endColor) {
        Matrix4f matrix = matrices.peek().getPositionMatrix();
        BufferBuilder bufferBuilder = Tessellator.getInstance().getBuffer();
        setupRender();
        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR);
        bufferBuilder.vertex(matrix, left, top, 0.0F).color(startColor).next();
        bufferBuilder.vertex(matrix, left, bottom, 0.0F).color(endColor).next();
        bufferBuilder.vertex(matrix, right, bottom, 0.0F).color(endColor).next();
        bufferBuilder.vertex(matrix, right, top, 0.0F).color(startColor).next();
        BufferRenderer.drawWithShader(bufferBuilder.end());
        resetRender();
    }

    static void horizontalGradient(MatrixStack matrices, int left, int top, int rigth, int bottom, int startColor, int endColor) {
        Matrix4f matrix = matrices.peek().getPositionMatrix();
        BufferBuilder bufferBuilder = Tessellator.getInstance().getBuffer();
        setupRender();
        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR);
        bufferBuilder.vertex(matrix, left, top, 0.0F).color(startColor).next();
        bufferBuilder.vertex(matrix, left, bottom, 0.0F).color(startColor).next();
        bufferBuilder.vertex(matrix, rigth, bottom, 0.0F).color(endColor).next();
        bufferBuilder.vertex(matrix, rigth, top, 0.0F).color(endColor).next();
        BufferRenderer.drawWithShader(bufferBuilder.end());
        resetRender();
    }

    static void fill(MatrixStack matrices, int left, int top, int right, int bottom, int startColor) {
        DrawableHelper.fill(matrices, left, top, right, bottom, startColor);
    }

    static int drawCenteredText(MatrixStack matrices, String text, int centerX, int y, int color) {
        return drawCenteredText(matrices, Main.instance.getClient().textRenderer, text, centerX, y, color);
    }

    static int drawCenteredText(MatrixStack matrices, TextRenderer textRenderer, String text, int centerX, int y, int color) {
        return textRenderer.drawWithShadow(matrices, text, (float) (centerX - textRenderer.getWidth(text) / 2), (float) y, color);
    }

    static int drawText(MatrixStack matrices, String text, float x, float y, int color) {
        return drawText(matrices, Main.instance.getClient().textRenderer, text, x, y, color);
    }

    static int drawText(MatrixStack matrices, TextRenderer textRenderer, String text, float x, float y, int color) {
        return textRenderer.draw(matrices, text, x, y, color);
    }

    static int drawTextWithShadow(MatrixStack matrices, String text, float x, float y, int color) {
        return drawTextWithShadow(matrices, Main.instance.getClient().textRenderer, text, x, y, color);
    }

    static int drawTextWithShadow(MatrixStack matrices, TextRenderer textRenderer, String text, float x, float y, int color) {
        return textRenderer.drawWithShadow(matrices, text, x, y, color);
    }

    static int drawTextWithShadow(MatrixStack matrices, OrderedText text, float x, float y, int color) {
        return drawTextWithShadow(matrices, Main.instance.getClient().textRenderer, text, x, y, color);
    }

    static int drawTextWithShadow(MatrixStack matrices, TextRenderer textRenderer, OrderedText text, float x, float y, int color) {
        return textRenderer.drawWithShadow(matrices, text, x, y, color);
    }

    static void setupRender() {
        RenderSystem.enableBlend();
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
    }

    static void resetRender() {
        RenderSystem.disableBlend();
    }
}
