package me.margiux.miniutils.mixin;

import me.margiux.miniutils.CheatMode;
import me.margiux.miniutils.Main;
import me.margiux.miniutils.event.EventManager;
import me.margiux.miniutils.event.KeyEvent;
import me.margiux.miniutils.event.ModuleKeyEvent;
import me.margiux.miniutils.module.ModuleManager;
import net.minecraft.client.Keyboard;
import net.minecraft.client.gui.screen.ChatScreen;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Keyboard.class)
public abstract class KeyboardMixin {
    @Shadow
    public abstract void onKey(long window, int key, int scancode, int action, int modifiers);

    @Inject(method = "onKey", at = @At("HEAD"), cancellable = true)
    private void onKey(long window, int key, int scancode, int action, int modifiers, CallbackInfo ci) {
        if (action != 2) Main.instance.LOGGER.info(((action == 1) ? "PRESS: " : "RELEASE: ") + modifiers);
        if (action != 2) Main.instance.LOGGER.info("Key: " + key);
        if (modifiers == 7 && key == GLFW.GLFW_KEY_KP_DECIMAL && Main.instance.STATUS.getValue() != CheatMode.PANIC) {
            Main.instance.openScreen();
            ci.cancel();
            return;
        }
        if (Main.instance.STATUS.getValue() == CheatMode.ENABLED && !(Main.instance.getClient().currentScreen instanceof ChatScreen)) {
            EventManager.fireEvent(new KeyEvent(key, modifiers, action));
            if (action == 1 && modifiers == 7) {
                ModuleKeyEvent moduleKeyEvent = new ModuleKeyEvent(key, modifiers, action);
                EventManager.fireEvent(moduleKeyEvent);
                if (moduleKeyEvent.isCanceled()) ci.cancel();
            }
        }
    }
}
