package me.margiux.miniutils.gui.widget;

import me.margiux.miniutils.Mode;
import me.margiux.miniutils.module.Module;
import me.margiux.miniutils.setting.EnumSetting;
import me.margiux.miniutils.setting.FieldSetting;
import me.margiux.miniutils.setting.Setting;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;

import java.util.List;

public class HackWindow extends Window {
    public boolean expanded = false;
    public Module module;

    public HackWindow(int x, int y, Module module) {
        super(x, y, 100, 25);
        this.module = module;

        String c = "§7";
        if (module.getMode() == Mode.ENABLED) c = "§a";
        else if (module.getMode() == Mode.DISABLED) c = "§c";
        Button moduleButton = new Button(x, y, 100, 25, c + module.name, "", (b, mx, my, b1) -> {
            if (b1 == 0) {
                module.toggle();
                String color = "§7";
                if (module.getMode() == Mode.ENABLED) color = "§a";
                else if (module.getMode() == Mode.DISABLED) color = "§c";
                b.name = color + module.name;
            } else if (b1 == 1) {
                expanded = !expanded;
                this.height = calculateHeight();
            }
        });
        addChild(moduleButton);

        List<Setting<?>> settings = module.moduleSettings;

        for (Setting<?> s : settings) {
            if (s instanceof FieldSetting<?> setting) {
                addChild(new Field(100, 25, "", "", setting));
            } else if (s instanceof EnumSetting<?> setting) {
                addChild(new Enum<>(100, 25, setting.getName(), setting.getDescription(), setting, null));
            }
        }
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        if (!this.visible) {
            return;
        }
        this.hovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
        MinecraftClient minecraftClient = MinecraftClient.getInstance();
        int y = this.y;
        if (expanded) {
            for (Widget window : children) {
                window.x = this.x;
                window.y = y;
                y += window.getHeight() + 1;
            }
        } else {
            children.get(0).x = this.x;
            children.get(0).y = y;
        }

        this.renderBackground(matrices, minecraftClient, mouseX, mouseY);

        String color = "§7";
        if (module.getMode() == Mode.ENABLED) color = "§a";
        else if (module.getMode() == Mode.DISABLED) color = "§c";
        children.get(0).name = color + module.name;
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
                height += window.getHeight() + 1;
            }
        } else {
            height += children.get(0).getHeight();
        }
        return height;
    }
}
