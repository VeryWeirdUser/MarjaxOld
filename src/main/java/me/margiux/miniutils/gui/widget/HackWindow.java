package me.margiux.miniutils.gui.widget;

import me.margiux.miniutils.Mode;
import me.margiux.miniutils.module.Module;
import me.margiux.miniutils.setting.BooleanSetting;
import me.margiux.miniutils.setting.EnumSetting;
import me.margiux.miniutils.setting.FieldSetting;
import me.margiux.miniutils.setting.Setting;
import net.minecraft.client.util.math.MatrixStack;

import java.util.List;

public class HackWindow extends Window {
    public boolean expanded = false;
    public final Module module;

    public HackWindow(int x, int y, Module module) {
        super(x, y, DEFAULT_WIDTH, DEFAULT_HEIGHT);
        this.module = module;

        Enum<Mode> moduleButton = new Enum<>(x, y, Widget.DEFAULT_WIDTH, Widget.DEFAULT_HEIGHT, module.getColorizedName(), "", module.getModeSetting(), (setting, button) -> {
            if (button == 0) {
                module.toggle();
                setting.displayName = module.getColorizedName();
            } else if (button == 1) {
                expanded = !expanded;
            }
        });
        moduleButton.displayNameSupplier = module::getColorizedName;
        moduleButton.displayInSingleLine = true;

        addChild(moduleButton);

        List<Setting<?>> settings = module.moduleSettings;

        for (Setting<?> s : settings) {
            if (s instanceof FieldSetting setting) {
                addChild(setting.makeWidget());
            } else if (s instanceof EnumSetting<?> setting) {
                addChild(setting.makeWidget());
            } else if (s instanceof BooleanSetting setting) {
                addChild(setting.makeWidget());
            }
        }
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        if (!this.visible) return;
        this.hovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
        int y = this.y;
        this.height = calculateHeight();
        if (expanded) {
            for (Widget window : children) {
                window.x = this.x + ((children.indexOf(window) == 0) ? 0 : 2);
                window.setWidth(width - ((children.indexOf(window) == 0) ? 0 : 4));
                window.y = y;
                y += window.getHeight() + 1;
            }
        } else {
            children.get(0).x = this.x;
            children.get(0).y = y;
        }

        String color = "§7";
        if (module.getMode() == Mode.ENABLED) color = "§a";
        else if (module.getMode() == Mode.DISABLED) color = "§c";
        children.get(0).displayName = color + module.name;
        if (expanded) {
            for (Widget window : children) {
                window.render(matrices, mouseX, mouseY, delta);
            }
        } else {
            children.get(0).render(matrices, mouseX, mouseY, delta);
        }
    }

    public int calculateHeight() {
        int height = 0;
        if (expanded) {
            for (Widget window : children) {
                height += window.getHeight() + ((children.size() > 1) ? 1 : 0);
            }
        } else {
            height += children.get(0).getHeight();
        }
        return height;
    }
}
