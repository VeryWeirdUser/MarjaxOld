package me.margiux.miniutils.mixin;

import net.minecraft.client.gui.screen.ingame.HandledScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(HandledScreen.class)
public interface HandledScreenAccessor {
    @Accessor int getBackgroundWidth();
    @Accessor(value = "x") int getX();
    @Accessor(value = "y") int getY();
}