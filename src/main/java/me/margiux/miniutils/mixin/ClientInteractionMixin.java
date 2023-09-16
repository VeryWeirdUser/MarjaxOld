package me.margiux.miniutils.mixin;

import me.margiux.miniutils.Main;
import net.minecraft.block.ChorusPlantBlock;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.item.AxeItem;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientPlayerInteractionManager.class)
public abstract class ClientInteractionMixin {

    @Inject(method = "breakBlock", at = @At(value = "RETURN"))
    public void breakBlock(BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        if (Main.instance.getClient().world.getBlockState(pos).getBlock() instanceof ChorusPlantBlock)
        {
            if (Main.instance.getClient().player.getMainHandStack().getItem() instanceof AxeItem && Main.instance.getClient().player.getOffHandStack().getItem() == Items.CHORUS_FLOWER) {
            }
        }
    }
}
