package me.margiux.miniutils.mixin;

import me.margiux.miniutils.CheatMode;
import me.margiux.miniutils.Main;
import me.margiux.miniutils.event.EventManager;
import me.margiux.miniutils.event.KeyEvent;
import me.margiux.miniutils.event.ModuleKeyEvent;
import net.minecraft.client.Keyboard;
import net.minecraft.client.gui.screen.ChatScreen;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Keyboard.class)
public abstract class KeyboardMixin {
    @Inject(method = "onKey", at = @At("HEAD"), cancellable = true)
    private void onKey(long window, int key, int scancode, int action, int modifiers, CallbackInfo ci) {
        if (!(Main.instance.getClient().currentScreen instanceof ChatScreen)) {
            EventManager.fireEvent(new KeyEvent(key, modifiers, action));
            if (action == 1 && modifiers == 7 && Main.instance.STATUS.getValue() == CheatMode.ENABLED) {
                ModuleKeyEvent moduleKeyEvent = new ModuleKeyEvent(key, modifiers, action);
                EventManager.fireEvent(moduleKeyEvent);
                if (moduleKeyEvent.isCanceled()) ci.cancel();
            }
        }
    }
}