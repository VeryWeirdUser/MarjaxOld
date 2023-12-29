package me.margiux.miniutils.mixin;

import me.margiux.miniutils.event.EventManager;
import me.margiux.miniutils.event.MouseEvent;
import net.minecraft.client.Mouse;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Mouse.class)
public abstract class MouseMixin {
    @Unique
    public long lastClick;
    @Unique
    public long lastButton;

    @Unique
    public boolean doubleClicked;

    @Shadow
    public abstract double getX();

    @Shadow
    public abstract double getY();

    @Inject(method = "onMouseButton", at = @At("HEAD"))
    private void onMouseButton(long window, int button, int action, int mods, CallbackInfo ci) {
        boolean doubleClick = false;
        if (doubleClicked) doubleClicked = false;
        else {
            doubleClicked = doubleClick = System.currentTimeMillis() - lastClick <= 250L && lastButton == button;
            lastClick = System.currentTimeMillis();
            lastButton = button;
        }
        EventManager.fireEvent(new MouseEvent(button, action, mods, getX(), getY(), doubleClick));
    }
}
