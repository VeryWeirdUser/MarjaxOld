package me.margiux.miniutils.gui;

import me.margiux.miniutils.Main;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

public class ClickGuiScreen extends MiniutilsScreen {
    public ClickGuiScreen(Text title) {
        super(title);
    }

    @Override
    protected void init() {
        super.init();
        rootWidget = Main.gui;
        addDrawableChild(rootWidget);
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        renderBackground(matrices);
        super.render(matrices, mouseX, mouseY, delta);
    }
}
