package me.margiux.miniutils.utils;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Matrix4f;

public interface DrawUtils {
    default void verticalGradient(MatrixStack matrices, float left, float top, float right, float bottom, int startColor, int endColor) {
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

    default void horizontalGradient(MatrixStack matrices, float left, float top, float rigth, float bottom, int startColor, int endColor) {
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

    default void setupRender() {
        RenderSystem.enableBlend();
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
    }

    default void resetRender() {
        RenderSystem.disableBlend();
    }
}
