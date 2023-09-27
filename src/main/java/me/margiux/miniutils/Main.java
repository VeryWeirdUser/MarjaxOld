package me.margiux.miniutils;

import me.margiux.miniutils.event.EventManager;
import me.margiux.miniutils.event.TickEvent;
import me.margiux.miniutils.gui.widget.Enum;
import me.margiux.miniutils.gui.MiniutilsGui;
import me.margiux.miniutils.module.ModuleManager;
import me.margiux.miniutils.utils.Mutable;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main implements ModInitializer {
    public static Main instance;

    public Mutable<CheatMode> STATUS;
    public final Logger LOGGER = LoggerFactory.getLogger("Miniutils");

    public MinecraftClient getClient() {
        return MinecraftClient.getInstance();
    }

    @Override
    public void onInitialize() {
        instance = this;
        this.STATUS = new Mutable<>(CheatMode.ENABLED);
        MiniutilsGui.instance = new MiniutilsGui();
        ModuleManager.initGuiElements();
        MiniutilsGui.instance.root.add(new Enum<>("MiniUtils mode", "MiniUtils mode", Main.instance.STATUS, this::statusChange),
                0, 460, 120, 15);
        ClientTickEvents.END_CLIENT_TICK.register((client) -> {
            EventManager.fireEvent(new TickEvent());
        });
    }

    public void openScreen() {
        MinecraftClient.getInstance().setScreen(MiniutilsGui.instance.getScreen());
    }

    public void emergentDisable() {
        STATUS.setValue(CheatMode.PANIC);
    }

    public void enable() {
        STATUS.setValue(CheatMode.ENABLED);
    }

    public void disable() {
        STATUS.setValue(CheatMode.DISABLED);
        ModuleManager.disable();
    }

    public void statusChange(CheatMode status) {
        if (status == CheatMode.DISABLED) disable();
        if (status == CheatMode.ENABLED) {
            ModuleManager.enableDisabled();
        }
    }
}
