package me.margiux.miniutils.event;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public class EntityRemovedEvent extends Event implements Cancelable {
    protected final int entityId;
    protected final Entity entity;
    protected final World world;
    protected final Entity.RemovalReason removalReason;

    public EntityRemovedEvent(int entityId, Entity entity, Entity.RemovalReason removalReason, World world) {
        this.entityId = entityId;
        this.entity = entity;
        this.removalReason = removalReason;
        this.world = world;
    }

    public Entity getEntity() {
        return entity;
    }

    public int getEntityId() {
        return entityId;
    }

    public Entity.RemovalReason getRemovalReason() {
        return removalReason;
    }

    public World getWorld() {
        return world;
    }
}
