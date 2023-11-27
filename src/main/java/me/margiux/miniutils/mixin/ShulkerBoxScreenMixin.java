package me.margiux.miniutils.mixin;

import me.margiux.miniutils.event.EventManager;
import me.margiux.miniutils.event.OpenScreenEvent;
import me.margiux.miniutils.event.PreOpenScreenEvent;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.ingame.ShulkerBoxScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ShulkerBoxScreenHandler;
import net.minecraft.text.Text;
import org.apache.commons.lang3.mutable.MutableObject;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ShulkerBoxScreen.class)
public abstract class ShulkerBoxScreenMixin extends HandledScreen<ShulkerBoxScreenHandler> {
    public ShulkerBoxScreenMixin(ShulkerBoxScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
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