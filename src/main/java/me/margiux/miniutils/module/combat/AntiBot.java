package me.margiux.miniutils.module.combat;

import me.margiux.miniutils.Mode;
import me.margiux.miniutils.event.EntityAddedEvent;
import me.margiux.miniutils.event.EntityRemovedEvent;
import me.margiux.miniutils.event.ModuleEventHandler;
import me.margiux.miniutils.event.TickEvent;
import me.margiux.miniutils.module.Category;
import me.margiux.miniutils.module.Module;
import me.margiux.miniutils.utils.HudUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;
import java.util.List;

public class AntiBot extends Module {
    static class Player {
        public PlayerEntity player;
        public double averageDistance;
        public double minDistance;
        public double maxDistance;
        public double distance;
        public int ticksBehindPlayer;
        public int ticksExactlyBehindPlayer;
        public int ticks;
        public Vec3d initialPosition = null;
    }

    private final List<Player> possibleBots = new ArrayList<>();

    public AntiBot(String name, String description, Category category, int activationKey, Mode defaultMode) {
        super(name, description, category, activationKey, defaultMode);
    }

    @ModuleEventHandler
    public void onTick(TickEvent e) {
        if (getClient().player == null) return;
        for (int i = 0; i < possibleBots.size(); i++) {
            Player p = possibleBots.get(i);
            PlayerEntity player = p.player;
            p.distance = player.squaredDistanceTo(getClient().player);
            if (p.ticks == 0) {
                if (p.averageDistance == 0) p.averageDistance = p.distance;
                if (p.minDistance == 0) p.minDistance = p.distance;
                if (p.maxDistance == 0) p.maxDistance = p.distance;
            } else if (p.ticks > 25) {
                possibleBots.remove(p);
                --i;
                return;
            }
            p.ticks++;
            if (p.minDistance > p.distance) p.minDistance = p.distance;
            if (p.maxDistance < p.distance) p.maxDistance = p.distance;
            p.averageDistance = (p.averageDistance * (p.ticks - 1) + p.distance) / p.ticks;

            double posX = player.getX() - getClient().player.getX();
            double posZ = player.getZ() - getClient().player.getZ();

            float newYaw = (float) Math.toDegrees(Math.atan2(posZ, posX)) - 90F;
            if (newYaw < 0) newYaw = -newYaw;
            float playerYaw = (getClient().player.prevYaw < 0) ? -getClient().player.prevYaw : getClient().player.prevYaw;

            if (newYaw - playerYaw > -140 && newYaw - playerYaw < 140) p.ticksBehindPlayer++;
            if (newYaw - playerYaw > -15 && newYaw - playerYaw < 15) p.ticksExactlyBehindPlayer++;

            if (p.ticksBehindPlayer > 10 && p.minDistance > 0.1 && p.maxDistance < 5) {
                p.player.remove(Entity.RemovalReason.KILLED);
                HudUtil.setActionbar("Player removed: " + p.player.getDisplayName().getString());
                return;
            }

            if (p.ticksExactlyBehindPlayer > 10 && p.minDistance > 0.1 && p.maxDistance < 5) {
                p.player.remove(Entity.RemovalReason.KILLED);
                HudUtil.setActionbar("Player removed: " + p.player.getDisplayName().getString());
                return;
            }

            if (p.ticksBehindPlayer >= 20) {
                if (p.initialPosition == p.player.getPos()) return;
                double maxDiff = p.maxDistance - p.averageDistance;
                double minDiff = p.averageDistance - p.minDistance;
                double averageDiff = (maxDiff + minDiff) / 2;
                if (averageDiff - p.averageDistance > -2 && p.averageDistance - averageDiff < 2) {
                    p.player.remove(Entity.RemovalReason.KILLED);
                    HudUtil.setActionbar("Player removed: " + p.player.getDisplayName().getString());
                }
            }
        }
    }

    @ModuleEventHandler
    public void onEntityAdded(EntityAddedEvent e) {
        if (getClient().player == null) return;
        if (e.getEntity() instanceof PlayerEntity p) {
            if (p == getClient().player) return;
            if (p.distanceTo(getClient().player) > 10) return;
            Player player = new Player();
            player.player = p;
            player.initialPosition = p.getPos();
            possibleBots.add(player);
        }
    }

    @ModuleEventHandler
    public void onEntityRemoved(EntityRemovedEvent e) {
        if (e.getEntity() instanceof PlayerEntity p) {
            possibleBots.removeIf((player -> player.player == p));
        }
    }
}
