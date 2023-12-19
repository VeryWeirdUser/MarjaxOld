package me.margiux.miniutils.mixin;

import me.margiux.miniutils.module.ModuleManager;
import net.minecraft.client.world.ClientWorld;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientWorld.class)
public class ClientWorldMixin {
    @Final
    @Shadow
    private ClientWorld.Properties clientWorldProperties;
    @Inject(at = @At("HEAD"), method = "setTimeOfDay", cancellable = true)
    public void setTimeOfDay(long timeOfDay, CallbackInfo ci) {
        if (ModuleManager.clientTime.isEnabled()) {
            clientWorldProperties.setTimeOfDay(ModuleManager.clientTime.timeSetting.getData().time);
            ci.cancel();
        }
    }
}
