package me.margiux.miniutils.mixin;

import me.margiux.miniutils.CheatMode;
import me.margiux.miniutils.Main;
import me.margiux.miniutils.module.ModuleManager;
import net.minecraft.client.Keyboard;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Keyboard.class)
public class KeyboardMixin {
    @Inject(method = "onKey", at = @At("HEAD"), cancellable = true)
    private void onKey(long window, int key, int scancode, int action, int modifiers, CallbackInfo ci) {
        if (Main.instance.STATUS.getValue() != CheatMode.PANIC && action == 1) {
            if (handleKey(key, modifiers)) ci.cancel();
        }
    }

    @Unique
    public boolean handleKey(int key, int modifiers) {
        if (modifiers == 6 && key == GLFW.GLFW_KEY_KP_DECIMAL && Main.instance.STATUS.getValue() != CheatMode.PANIC) {
            Main.instance.openScreen();
            return true;
        }
        if (Main.instance.STATUS.getValue() == CheatMode.ENABLED) {
            if (ModuleManager.elytraHunter.hasTargets()) {
                if (key == GLFW.GLFW_KEY_RIGHT) {
                    ModuleManager.elytraHunter.selectNext();
                    return true;
                }
                if (key == GLFW.GLFW_KEY_LEFT) {
                    ModuleManager.elytraHunter.selectPrevious();
                    return true;
                }
                if (key == GLFW.GLFW_KEY_UP) {
                    ModuleManager.elytraHunter.requestAimlock();
                    return true;
                }
                if (key == GLFW.GLFW_KEY_DOWN) {
                    ModuleManager.elytraHunter.abortAimlock();
                    return true;
                }
            }
            if (modifiers == 7 && key == GLFW.GLFW_KEY_I) {
                ModuleManager.truesight.toggle();
                return true;
            }
            if (modifiers == 7 && key == GLFW.GLFW_KEY_C) {
                ModuleManager.chorusFarmer.toggle();
                return true;
            }
        }
        return false;
    }
}
