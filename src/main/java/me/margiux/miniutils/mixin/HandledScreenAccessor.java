package me.margiux.miniutils.mixin;

import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.Selectable;
import net.minecraft.client.gui.screen.ingame.GenericContainerScreen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(HandledScreen.class)
public interface HandledScreenAccessor {
    @Accessor int getBackgroundWidth();
    @Accessor int getBackgroundHeight();
    @Accessor(value = "x") int getX();
    @Accessor(value = "y") int getY();
}