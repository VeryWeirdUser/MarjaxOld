package me.margiux.miniutils.utils;

import me.margiux.miniutils.Main;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;

import java.util.ArrayList;
import java.util.List;

public class PlayerUtils {

    public static List<PlayerEntity> getPlayersInRange(PlayerEntity player) {
        if (Main.getClient().player != null) return getPlayersInRange(player, (Main.getClient().interactionManager == null) ? 4.5 : Main.getClient().interactionManager.getReachDistance());
        return null;
    }

    public static List<PlayerEntity> getPlayersInRange(PlayerEntity player, double distance) {
        return new ArrayList<>(player.world.getPlayers().stream().filter((e) -> e.distanceTo(player) < distance).toList());
    }

    public static void attackEntity(Entity entity) {
        if (Main.getClient().player != null) attackEntity(Main.getClient().player, entity);
    }

    public static void attackEntity(PlayerEntity player, Entity entity) {
        if (Main.getClient().interactionManager != null)
            Main.getClient().interactionManager.attackEntity(player, entity);
        player.swingHand(player.getActiveHand());
    }

    public static PlayerEntity getClosestPlayer(PlayerEntity player) {
        return getClosestPlayer(player, 4.5);
    }

    public static PlayerEntity getClosestPlayer(PlayerEntity player, double distance) {
        double closestDistance = distance * distance;
        PlayerEntity closest = null;
        for (PlayerEntity p : player.world.getPlayers()) {
            double d = p.squaredDistanceTo(player);
            if (p != player && d < closestDistance * closestDistance && p.squaredDistanceTo(player) < distance * distance) {
                closestDistance = d;
                closest = p;
            }
        }
        return closest;
    }
}
