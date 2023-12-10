package me.margiux.miniutils.gui;

import me.margiux.miniutils.Main;
import me.margiux.miniutils.gui.widget.Widget;
import net.minecraft.SharedConstants;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;

public class MiniutilsScreen extends Screen {
    @Nullable
    public Widget focusedWidget;
    public Widget rootWidget;

    public MiniutilsScreen(Text title) {
        super(title);
        rootWidget = Main.gui;
    }

    public void setFocusedWidget(@Nullable Widget focusedWidget) {
        if (this.focusedWidget != null) this.focusedWidget.setFocused(false);
        this.focusedWidget = focusedWidget;
        if (focusedWidget != null) focusedWidget.setFocused(true);
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        super.render(matrices, mouseX, mouseY, delta);
        rootWidget.render(matrices, mouseX, mouseY, delta);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (rootWidget.isValidClickButton(button)) {
            Widget widget = rootWidget;
            while (!widget.children.isEmpty()) {
                Widget oldWidget = widget;
                for (Widget child : widget.children) {
                    if (child.inBounds(mouseX, mouseY)) {
                        widget = child;
                    }
                }
                if (oldWidget == widget) break;
            }
            widget.mouseClicked(mouseX, mouseY, button);
            setFocusedWidget(widget);
        }
        return false;
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        if (rootWidget.isValidClickButton(button) && focusedWidget != null)
            return focusedWidget.mouseReleased(mouseX, mouseY, button);
        return false;
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        if (rootWidget.isValidClickButton(button) && focusedWidget != null)
            return focusedWidget.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
        return false;
    }

    @Override
    public boolean charTyped(char chr, int modifiers) {
        if (focusedWidget != null && SharedConstants.isValidChar(chr)) return focusedWidget.charTyped(chr, modifiers);
        return false;
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        super.keyPressed(keyCode, scanCode, modifiers);
        if (focusedWidget != null) return focusedWidget.keyPressed(keyCode, scanCode, modifiers);
        return false;
    }

    @Override
    public void close() {
        setFocusedWidget(null);
        super.close();
    }
}
