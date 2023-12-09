package me.margiux.miniutils.mixin;

import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(MinecraftClient.class)
public interface MinecraftClientAccessor {
    @Mutable
    @Accessor
    void setGameVersion(String version);

    @Mutable
    @Accessor
    void setVersionType(String value);
}
