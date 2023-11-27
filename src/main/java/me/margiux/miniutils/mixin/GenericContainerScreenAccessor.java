package me.margiux.miniutils.mixin;

import me.margiux.miniutils.event.EventManager;
import me.margiux.miniutils.event.OpenScreenEvent;
import me.margiux.miniutils.event.PreOpenScreenEvent;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.GenericContainerScreen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.ingame.ScreenHandlerProvider;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.text.Text;
import org.apache.commons.lang3.mutable.MutableObject;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(GenericContainerScreen.class)
public interface GenericContainerScreenAccessor {
    @Accessor
    int getRows();
}