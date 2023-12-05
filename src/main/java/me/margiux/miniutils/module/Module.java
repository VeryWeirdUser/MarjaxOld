package me.margiux.miniutils.module;

import me.margiux.miniutils.Mode;
import me.margiux.miniutils.event.EventHandler;
import me.margiux.miniutils.event.ModuleKeyEvent;
import me.margiux.miniutils.event.Listener;
import me.margiux.miniutils.setting.Setting;
import me.margiux.miniutils.utils.Mutable;
import me.margiux.miniutils.utils.HudUtil;
import net.minecraft.client.MinecraftClient;
import me.margiux.miniutils.Main;

import java.util.ArrayList;
import java.util.List;

public class Module implements Listener {
    public final String name;
    public final String description;
    public final Category category;
    public final List<Setting<?>> moduleSettings = new ArrayList<>();
    protected final Mutable<Mode> mode = new Mutable<>(Mode.DISABLED);
    public boolean disabledByMain = false;
    public final int activationKey;

    public MinecraftClient getClient() {
        return Main.instance.getClient();
    }

    public Module(String name, String description, Category category, int activationKey, Mode defaultMode) {
        this(name, description, category, activationKey);
        this.mode.setValue(defaultMode);
    }

    public Module(String name, String description, Category category, int activationKey) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.activationKey = activationKey;
    }

    public void changeMode(Mode mode) {
        Mode oldMode = this.mode.getValue();
        if (mode == oldMode) return;
        this.mode.setValue(mode);
        if (this.mode.getValue() == Mode.ENABLED) onEnable();
        else if (oldMode == Mode.ENABLED) onDisable();
    }

    public void toggle() {
        if (this.mode.getValue() != Mode.FORCE_DISABLED) changeMode(mode.getValue().getNext());
    }

    public void onEnable() {
        HudUtil.setSubTitle("§7" + name + ": §aEnabled");
    }

    public void onDisable() {
        HudUtil.setSubTitle("§7" + name + ": §cDisabled");
    }

    public boolean isEnabled() {
        return mode.getValue() == Mode.ENABLED;
    }
    public Mode getMode() {
        return mode.getValue();
    }

    @EventHandler
    public void onModuleKey(ModuleKeyEvent event) {
        if (event.getKey() == activationKey) {
            toggle();
            event.setCanceled();
        }
    }

    public void addSetting(Setting<?> setting) {
        this.moduleSettings.add(setting);
    }
}
