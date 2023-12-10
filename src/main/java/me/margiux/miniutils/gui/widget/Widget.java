package me.margiux.miniutils.gui.widget;

import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.Selectable;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.util.math.MatrixStack;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class Widget implements Drawable, Element, Selectable {
    public final static int DEFAULT_WIDTH = 100;
    public final static int DEFAULT_HEIGHT = 25;
    protected double clickX;
    protected double clickY;
    protected double relativeClickX;
    protected double relativeClickY;
    protected double draggedX;
    protected double draggedY;
    protected int width;
    protected int height;
    protected int x;
    protected int y;
    protected boolean hovered;
    public boolean active = true;
    public boolean visible = true;
    protected float alpha = 1.0F;
    private boolean focused;
    @Nullable
    protected Widget parent;
    public final List<Widget> children = new ArrayList<>();
    public final String name;
    public final String description;
    public String displayName;
    public Supplier<String> displayNameSupplier;

    public Widget(int x, int y, int width, int height, String name, String description) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.name = this.displayName = name;
        this.description = description;
    }

    public Widget(int x, int y, int width, int height) {
        this(x, y, width, height, "", "");
    }

    public Widget(int width, int height, String name, String description) {
        this(0, 0, width, height, name, description);
    }

    public void addChild(Widget child) {
        this.children.add(child);
        child.setParent(this);
    }

    @SuppressWarnings("unused")
    public void removeChild(Widget child) {
        this.children.remove(child);
        child.setParent(null);
    }

    public void setParent(@Nullable Widget parent) {
        this.parent = parent;
    }

    @SuppressWarnings("unused")
    @Nullable
    public Widget getParent() {
        return this.parent;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.hovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;

        renderBackground(matrices, mouseX, mouseY, delta);
        renderText(matrices, mouseX, mouseY, delta);
        for (Widget child : children) {
            child.render(matrices, mouseX, mouseY, delta);
        }
    }

    public void renderText(MatrixStack matrices, int mouseX, int mouseY, float delta) {

    }

    public void renderBackground(MatrixStack matrices, int mouseX, int mouseY, float delta) {

    }

    public boolean inBounds(double mouseX, double mouseY) {
        return this.active && this.visible && mouseX >= (double) this.x && mouseY >= (double) this.y && mouseX < (double) (this.x + this.width) && mouseY < (double) (this.y + this.height);
    }

    public boolean isValidClickButton(int button) {
        return button == 0 || button == 1;
    }

    public boolean isRightClickButton(int button) {
        return button == 0;
    }

    public boolean isLeftClickButton(int button) {
        return button == 1;
    }


    public void refreshDisplayName() {
        if (displayNameSupplier != null) displayName = displayNameSupplier.get();
        else displayName = name;
    }

    public Selectable.SelectionType getType() {
        if (this.focused) {
            return SelectionType.FOCUSED;
        } else {
            return this.hovered ? SelectionType.HOVERED : SelectionType.NONE;
        }
    }

    @Override
    public void appendNarrations(NarrationMessageBuilder builder) {

    }

    @Override
    public boolean isNarratable() {
        return false;
    }

    @Override
    public void mouseMoved(double mouseX, double mouseY) {
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (inBounds(mouseX, mouseY) && isValidClickButton(button)) {
            clickX = mouseX;
            clickY = mouseY;
            relativeClickX = mouseX - x;
            relativeClickY = mouseY - y;
            onClick(mouseX, mouseY, button);
            return true;
        }
        return false;
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        clickX = 0;
        clickY = 0;
        draggedX = 0;
        draggedY = 0;
        relativeClickX = 0;
        relativeClickY = 0;
        return false;
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        if (this.isValidClickButton(button)) {
            draggedX = mouseX - relativeClickX;
            draggedY = mouseY - relativeClickY;
            this.onDrag(mouseX, mouseY, deltaX, deltaY);
            return true;
        }
        return false;
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double amount) {
        return false;
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        return false;
    }

    @Override
    public boolean keyReleased(int keyCode, int scanCode, int modifiers) {
        return false;
    }

    @Override
    public boolean charTyped(char chr, int modifiers) {
        return false;
    }

    @Override
    public boolean changeFocus(boolean lookForwards) {
        return false;
    }

    @Override
    public boolean isMouseOver(double mouseX, double mouseY) {
        return false;
    }

    public void onClick(double mouseX, double mouseY, int button) {
    }

    public void onDrag(double mouseX, double mouseY, double deltaX, double deltaY) {
    }

    public void setFocused(boolean focused) {
        this.focused = focused;
    }

    public boolean isFocused() {
        return this.focused;
    }
}
