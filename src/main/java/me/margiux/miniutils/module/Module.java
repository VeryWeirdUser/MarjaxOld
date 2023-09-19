package me.margiux.miniutils.module;

import me.margiux.miniutils.Mode;
import me.margiux.miniutils.gui.widget.Enum;
import me.margiux.miniutils.mutable.MutableExtended;
import net.minecraft.client.MinecraftClient;
import me.margiux.miniutils.Main;

public class Module {
    public final String name;
    public final String description;
    protected MutableExtended<Mode> mode = new MutableExtended<>(Mode.DISABLED);
    protected final Enum<Mode> toggleButton;
    public boolean disabledByMain = false;

    public MinecraftClient getClient() {
        return Main.instance.getClient();
    }

    public Module(String name, String description, Mode defaultMode) {
        this(name, description);
        this.mode.setValue(defaultMode);
    }

    public Module(String name, String description) {
        this.name = name;
        this.description = description;
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

    }

    public void onDisable() {

    }

    public void initGui() {

    }

    public boolean isEnabled() {
        return mode.getValue() == Mode.ENABLED;
    }
}
