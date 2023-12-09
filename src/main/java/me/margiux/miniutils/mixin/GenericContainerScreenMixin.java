package me.margiux.miniutils.mixin;

import me.margiux.miniutils.event.EventManager;
import me.margiux.miniutils.event.OpenScreenEvent;
import me.margiux.miniutils.event.PreOpenScreenEvent;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import org.apache.commons.lang3.mutable.MutableObject;
import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.client.gui.screen.ingame.GenericContainerScreen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.ingame.ScreenHandlerProvider;
import net.minecraft.screen.GenericContainerScreenHandler;

@Mixin(GenericContainerScreen.class)
public abstract class GenericContainerScreenMixin extends HandledScreen<GenericContainerScreenHandler> implements ScreenHandlerProvider<GenericContainerScreenHandler> {
    public GenericContainerScreenMixin(GenericContainerScreenHandler container, PlayerInventory playerInventory, Text name) {
        super(container, playerInventory, name);
        MutableObject<Screen> screen = new MutableObject<>(this);
        PreOpenScreenEvent event = new PreOpenScreenEvent(screen);
        EventManager.fireEvent(event);
    }

    @Override
    protected void init() {
        super.init();
        OpenScreenEvent event = new OpenScreenEvent(this);
        EventManager.fireEvent(event);
    }
}