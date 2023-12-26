package me.margiux.miniutils.mixin;

import me.margiux.miniutils.event.EntityAddedEvent;
import me.margiux.miniutils.event.EntityRemovedEvent;
import me.margiux.miniutils.event.EventManager;
import me.margiux.miniutils.module.ModuleManager;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.util.profiler.Profiler;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.MutableWorldProperties;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.entity.EntityLookup;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Supplier;

@Mixin(ClientWorld.class)
public abstract class ClientWorldMixin extends World {
    @Final
    @Shadow
    private ClientWorld.Properties clientWorldProperties;

    @Shadow
    protected abstract EntityLookup<Entity> getEntityLookup();

    protected ClientWorldMixin(MutableWorldProperties properties, RegistryKey<World> registryRef, RegistryEntry<DimensionType> dimension, Supplier<Profiler> profiler, boolean isClient, boolean debugWorld, long seed, int maxChainedNeighborUpdates) {
        super(properties, registryRef, dimension, profiler, isClient, debugWorld, seed, maxChainedNeighborUpdates);
    }

    @Inject(at = @At("HEAD"), method = "setTimeOfDay", cancellable = true)
    public void setTimeOfDay(long timeOfDay, CallbackInfo ci) {
        if (ModuleManager.clientTime.isEnabled()) {
            clientWorldProperties.setTimeOfDay(ModuleManager.clientTime.timeSetting.getData().time);
            ci.cancel();
        }
    }

    @Inject(at = @At("HEAD"), method = "addEntityPrivate", cancellable = true)
    public void addEntityPrivate(int id, Entity entity, CallbackInfo ci) {
        EntityAddedEvent event = new EntityAddedEvent(id, entity, this);
        EventManager.fireEvent(event);
        if (event.isCanceled()) ci.cancel();
    }

    @Inject(at = @At("HEAD"), method = "removeEntity", cancellable = true)
    public void removeEntity(int entityId, Entity.RemovalReason removalReason, CallbackInfo ci) {
        if (getEntityLookup().get(entityId) == null) return;
        EntityRemovedEvent event = new EntityRemovedEvent(entityId, getEntityLookup().get(entityId), removalReason, this);
        EventManager.fireEvent(event);
        if (event.isCanceled()) ci.cancel();
    }
}
