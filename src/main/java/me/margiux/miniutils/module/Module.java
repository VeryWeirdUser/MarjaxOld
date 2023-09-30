package me.margiux.miniutils.module;

import me.margiux.miniutils.Mode;
import me.margiux.miniutils.event.EventHandler;
import me.margiux.miniutils.event.ModuleKeyEvent;
import me.margiux.miniutils.event.Listener;
import me.margiux.miniutils.gui.widget.Enum;
import me.margiux.miniutils.utils.Mutable;
import me.margiux.miniutils.utils.HudUtil;
import net.minecraft.client.MinecraftClient;
import me.margiux.miniutils.Main;

public class Module implements Listener {
    public final String name;
    public final String description;
    protected Mutable<Mode> mode = new Mutable<>(Mode.DISABLED);
    protected final Enum<Mode> toggleButton;
    public boolean disabledByMain = false;
    public final int activationKey;

    public MinecraftClient getClient() {
        return Main.instance.getClient();
    }

    public Module(String name, String description, int activationKey, Mode defaultMode) {
        this(name, description, activationKey);
        this.mode.setValue(defaultMode);
    }

    public Module(String name, String description, int activationKey) {
        this.name = name;
        this.description = description;
        this.activationKey = activationKey;
        this.toggleButton = new Enum<>(this.name, this.description, mode, this::changeMode);
    }

    public void changeMode(Mode mode) {
        Mode oldMode = this.mode.getValue();
        this.mode.setValue(mode, true);
        if (this.mode.getValue() == Mode.ENABLED) onEnable();
        else if (oldMode == Mode.ENABLED && mode != oldMode) onDisable();
    }

    public void toggle() {
        changeMode(mode.getValue().next());
    }

    public void onEnable() {
        HudUtil.setSubTitle("§7" + name + ": §aEnabled");
    }

    public void onDisable() {
        HudUtil.setSubTitle("§7" + name + ": §cDisabled");
    }

    public void initGui() {

    }

    public boolean isEnabled() {
        return mode.getValue() == Mode.ENABLED;
    }

    @EventHandler
    public void onModuleKey(ModuleKeyEvent event) {
        if (event.getKey() == activationKey) {
            toggle();
            event.setCanceled();
        }
    }
}
