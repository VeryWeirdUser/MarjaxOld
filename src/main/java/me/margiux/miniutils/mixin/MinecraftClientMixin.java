package me.margiux.miniutils.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.RaycastContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public abstract class MinecraftClientMixin {
    @Shadow
    public HitResult crosshairTarget;
    @Shadow
    public ClientPlayerEntity player;
    @Shadow
    public ClientWorld world;

    @Inject(method = "doItemUse", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/client/network/ClientPlayerEntity;getStackInHand(Lnet/minecraft/util/Hand;)Lnet/minecraft/item/ItemStack;"))
    public void switchCrosshairTarget(CallbackInfo ci) {
        if (MinecraftClient.getInstance().cameraEntity != null) {
            BlockHitResult result;
            if ((result = world.raycast(new RaycastContext(player.getEyePos(), player.getEyePos().add(MinecraftClient.getInstance().cameraEntity.getRotationVec(0).multiply(MinecraftClient.getInstance().interactionManager.getReachDistance())), RaycastContext.ShapeType.OUTLINE, RaycastContext.FluidHandling.NONE, player))) != null) {
                if (world.getBlockState(result.getBlockPos()).hasBlockEntity()) {
                    MinecraftClient.getInstance().interactionManager.interactBlock(player, Hand.MAIN_HAND, result);
                }
            }
        }
    }
}
