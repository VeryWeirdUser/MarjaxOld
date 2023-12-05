package me.margiux.miniutils.gui.widget;

import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class Widget extends ClickableWidget {
    @Nullable
    protected Widget parent;
    public List<Widget> children = new ArrayList<>();
    public String name;
    public String description;

    public Widget(int x, int y, int width, int height, String name, String description) {
        super(x, y, width, height, Text.literal(name));
        this.name = name;
        this.description = description;
    }

    public Widget(int x, int y, int width, int height) {
        this(x, y, width, height, "", "");
    }

    public Widget(int width, int height, String name, String description) {
        this(0, 0, width, height, name, description);
    }

    public void setFocused(boolean focused) {
        super.setFocused(focused);
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void addChild(Widget child) {
        this.children.add(child);
        child.setParent(this);
    }

    public void removeChild(Widget child) {
        this.children.remove(child);
        child.setParent(null);
    }

    @Override
    public void appendNarrations(NarrationMessageBuilder builder) {

    }

    @Override
    public void mouseMoved(double mouseX, double mouseY) {
        super.mouseMoved(mouseX, mouseY);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double amount) {
        return super.mouseScrolled(mouseX, mouseY, amount);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean keyReleased(int keyCode, int scanCode, int modifiers) {
        return super.keyReleased(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean charTyped(char chr, int modifiers) {
        return super.charTyped(chr, modifiers);
    }

    public void setParent(@Nullable Widget parent) {
        this.parent = parent;
    }

    @Nullable
    public Widget getParent() {
        return this.parent;
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        for (Widget child : children) {
            child.render(matrices, mouseX, mouseY, delta);
        }
    }

    public void onClick(double mouseX, double mouseY, int button) {

    }

    public boolean inBounds(double mouseX, double mouseY) {
        return clicked(mouseX, mouseY);
    }

    public boolean isValidClickButton(int button) {
        return button == 0 || button == 1;
    }

    public void onDrag(double mouseX, double mouseY, double deltaX, double deltaY) {
    }
}
