package me.margiux.miniutils.gui;

import io.github.cottonmc.cotton.gui.client.LightweightGuiDescription;
import io.github.cottonmc.cotton.gui.widget.*;
import io.github.cottonmc.cotton.gui.widget.data.Insets;
import me.margiux.miniutils.gui.widget.Button;
import me.margiux.miniutils.module.Category;
import me.margiux.miniutils.module.Module;
import me.margiux.miniutils.module.ModuleManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MiniutilsGui extends Window {
    public MiniutilsGui() {
        super(5, 5, 500, 500);

        int x = 0;

        Map<Category, List<Module>> moduleMap = new HashMap<>();

        for (Module mod : ModuleManager.modules) {
            moduleMap.computeIfAbsent(mod.category, (k) -> new ArrayList<>()).add(mod);
        }

        for (Category c : Category.values()) {
            HackListWindow window;
            List<HackWindow> hackWindows = new ArrayList<>();
            for (Module module : moduleMap.get(c)) {
                hackWindows.add(new HackWindow(0, 0, module));
            }
            window = new HackListWindow(x, 0, 100, 300, c, hackWindows);
            x += 110;
            addChild(window);
        }
    }
}
