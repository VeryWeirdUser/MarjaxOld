package me.margiux.miniutils.mixin;

import net.minecraft.client.gui.screen.ingame.GenericContainerScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(GenericContainerScreen.class)
public interface GenericContainerScreenAccessor {
    @Accessor
    int getRows();
}