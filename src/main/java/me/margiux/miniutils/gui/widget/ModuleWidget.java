package me.margiux.miniutils.gui.widget;

import me.margiux.miniutils.Mode;
import me.margiux.miniutils.gui.Gradient;
import me.margiux.miniutils.module.Module;
import me.margiux.miniutils.setting.EnumSetting;
import me.margiux.miniutils.utils.RenderUtils;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;

public class ModuleWidget extends Enum<Mode> {
    final HackWindow parent;
    public ModuleWidget(Module module, int x, int y, int width, String name, String description, EnumSetting<Mode> setting, HackWindow parent) {
        super(x, y, width, 15, name, description, setting, (s, button) -> {
            if (button == 0) {
                module.toggle();
            } else if (button == 1) {
                parent.expanded = !parent.expanded;
            }
        });
        this.parent = parent;
        displayInSingleLine = true;
        displayNameSupplier = () -> module.name;
    }

    @Override
    public int calculateHeight() {
        return height;
    }

    @Override
    public void renderBackground(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        switch (parent.module.getMode()) {
            case ENABLED -> {
                strokeColor = new Gradient(0xFF87f2b2 - 0x00333333, 0xFF8BA8E0 - 0x00333333);
                widgetColor = new Gradient(0xFF87f2b2, 0xFF8BA8E0);
            }
            case DISABLED -> {
                strokeColor = new Gradient(0xFF87f2b2 - 0x00556666, 0xFF8BA8E0 - 0x00556666);
                widgetColor = new Gradient(0xFF87f2b2 - 0x00335555, 0xFF8BA8E0 - 0x00335555);
            }
            default -> {
                strokeColor = new Gradient(0xFF87f2b2 - 0x00557777, 0xFF8BA8E0 - 0x00557777);
                widgetColor = new Gradient(0xFF87f2b2 - 0x00445555, 0xFF8BA8E0 - 0x00447777);
            }
        }
        super.renderBackground(matrices, mouseX, mouseY, delta);
    }

    @Override
    public void renderText(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        RenderUtils.drawText(matrices, displayName, this.x + 4, this.y + 4, 0xFFFFFF | MathHelper.ceil(this.alpha * 255.0f) << 24);
        RenderUtils.drawText(matrices, parent.getChildren().size() > 1 ? parent.expanded ? "-" : "+" : "", this.x + this.width - 10, this.y + 4, 0xDDDDDD | MathHelper.ceil(this.alpha * 255.0f) << 24);
    }
}
