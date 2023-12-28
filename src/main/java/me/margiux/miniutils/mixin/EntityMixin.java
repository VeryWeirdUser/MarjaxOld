package me.margiux.miniutils.mixin;

import me.margiux.miniutils.module.ModuleManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public class EntityMixin {
    @Inject(method = "isInvisibleTo", at = @At("HEAD"), cancellable = true)
    public void isInvisibleToPlayer(PlayerEntity player, CallbackInfoReturnable<Boolean> ci) {
        if (ModuleManager.truesight.isEnabled()) {
            ci.setReturnValue(false);
        }
    }
}
