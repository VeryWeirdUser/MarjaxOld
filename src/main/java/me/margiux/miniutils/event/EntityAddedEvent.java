package me.margiux.miniutils.event;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public class EntityAddedEvent extends Event implements Cancelable {
    protected final int entityId;
    protected final Entity entity;
    protected final World world;

    public EntityAddedEvent(int id, Entity entity, World world) {
        this.entityId = id;
        this.entity = entity;
        this.world = world;
    }

    public int getEntityId() {
        return entityId;
    }

    public Entity getEntity() {
        return entity;
    }

    public World getWorld() {
        return world;
    }
}
