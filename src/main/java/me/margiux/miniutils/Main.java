package me.margiux.miniutils;

import me.margiux.miniutils.event.EventManager;
import me.margiux.miniutils.event.TickEvent;
import me.margiux.miniutils.gui.ClickGuiScreen;
import me.margiux.miniutils.gui.MiniutilsGui;
import me.margiux.miniutils.module.ModuleManager;
import me.margiux.miniutils.setting.EnumSetting;
import me.margiux.miniutils.task.TaskManager;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

public class Main implements ModInitializer {
    public static Main instance;
    public static MiniutilsGui gui;
    public final EnumSetting<CheatMode> status = new EnumSetting<>("Cheat status", "", CheatMode.ENABLED);

    public static final MinecraftClient MC = MinecraftClient.getInstance();

    public static boolean isClientInGame() {
        return MC.player != null && MC.world != null;
    }

    @Override
    public void onInitialize() {
        instance = this;
        gui = new MiniutilsGui();
        EventManager.addStaticListener(MainListener.class);
        EventManager.addStaticListener(TaskManager.class);
        ClientTickEvents.END_CLIENT_TICK.register((client) -> EventManager.fireEvent(new TickEvent()));
    }

    public void openScreen() {
        MinecraftClient.getInstance().setScreen(new ClickGuiScreen(Text.literal("Miniutils")));
    }

    @SuppressWarnings("unused")
    public void panic() {
        status.setData(CheatMode.PANIC);
        ModuleManager.disable();
    }

    public void enable() {
        status.setData(CheatMode.ENABLED);
        ModuleManager.enableDisabled();
    }

    public void disable() {
        status.setData(CheatMode.DISABLED);
        ModuleManager.disable();
    }

    public void changeStatus(CheatMode status) {
        if (status == CheatMode.DISABLED) disable();
        if (status == CheatMode.ENABLED) enable();
        if (status == CheatMode.PANIC) panic();
    }
}
