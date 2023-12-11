package me.margiux.miniutils.module;

import me.margiux.miniutils.Mode;
import me.margiux.miniutils.event.EventHandler;
import me.margiux.miniutils.event.ModuleKeyEvent;
import me.margiux.miniutils.event.Listener;
import me.margiux.miniutils.setting.EnumSetting;
import me.margiux.miniutils.setting.Setting;
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
    protected final EnumSetting<Mode> mode = new EnumSetting<>("Mode", "", Mode.DISABLED);
    public boolean disabledByMain = false;
    public final int activationKey;

    public MinecraftClient getClient() {
        return Main.instance.getClient();
    }
    public EnumSetting<Mode> getModeSetting() {
        return mode;
    }

    public Module(String name, String description, Category category, int activationKey, Mode defaultMode) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.activationKey = activationKey;
        this.mode.setData(defaultMode);
    }

    public Module(String name, String description, Category category, int activationKey) {
        this(name, description, category, activationKey, Mode.DISABLED);
    }

    public void changeMode(Mode mode) {
        Mode oldMode = this.mode.getData();
        if (mode == oldMode) return;
        this.mode.setData(mode);
        if (this.mode.getData() == Mode.ENABLED) onEnable();
        else if (oldMode == Mode.ENABLED) onDisable();
    }

    public void toggle() {
        if (this.mode.getData() != Mode.FORCE_DISABLED) changeMode(mode.getData().getNext());
    }

    public void onEnable() {
        HudUtil.setSubTitle("§7" + name + ": §aEnabled");
    }

    public void onDisable() {
        HudUtil.setSubTitle("§7" + name + ": §cDisabled");
    }

    public boolean isEnabled() {
        return mode.getData() == Mode.ENABLED;
    }
    public Mode getMode() {
        return mode.getData();
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

    public String getColorizedName() {
        String c = "§7";
        if (getMode() == Mode.ENABLED) c = "§a";
        else if (getMode() == Mode.DISABLED) c = "§c";
        return c + this.name;
    }
}
