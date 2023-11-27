package me.margiux.miniutils;

import me.margiux.miniutils.event.EventManager;
import me.margiux.miniutils.event.TickEvent;
import me.margiux.miniutils.gui.ClickGuiScreen;
import me.margiux.miniutils.gui.MiniutilsScreen;
import me.margiux.miniutils.gui.widget.Enum;
import me.margiux.miniutils.gui.MiniutilsGui;
import me.margiux.miniutils.module.ModuleManager;
import me.margiux.miniutils.task.TaskManager;
import me.margiux.miniutils.utils.Mutable;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

public class Main implements ModInitializer {
    public static Main instance;
    public static MiniutilsGui gui;
    public Mutable<CheatMode> STATUS;

    public MinecraftClient getClient() {
        return MinecraftClient.getInstance();
    }

    @Override
    public void onInitialize() {
        instance = this;
        this.STATUS = new Mutable<>(CheatMode.ENABLED);
        gui = new MiniutilsGui();
        EventManager.addStaticListener(MainListener.class);
        EventManager.addStaticListener(TaskManager.class);
        ClientTickEvents.END_CLIENT_TICK.register((client) -> EventManager.fireEvent(new TickEvent()));
        //MiniutilsGui.instance.root.add(new Enum<>("MiniUtils mode", Main.instance.STATUS, this::changeStatus), 0, 460, 120, 15);
    }

    public void openScreen() {
        MinecraftClient.getInstance().setScreen(new ClickGuiScreen(Text.literal("Miniutils")));
    }

    @SuppressWarnings("unused")
    public void panic() {
        STATUS.setValue(CheatMode.PANIC);
        ModuleManager.disable();
    }

    public void enable() {
        STATUS.setValue(CheatMode.ENABLED);
        ModuleManager.enableDisabled();
    }

    public void disable() {
        STATUS.setValue(CheatMode.DISABLED);
        ModuleManager.disable();
    }

    public void changeStatus(CheatMode status) {
        if (status == CheatMode.DISABLED) disable();
        if (status == CheatMode.ENABLED) enable();
        if (status == CheatMode.PANIC) panic();
    }
}
