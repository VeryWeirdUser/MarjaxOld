package me.margiux.miniutils.module.combat;

import me.margiux.miniutils.event.ModuleEventHandler;
import me.margiux.miniutils.event.UpdateEvent;
import me.margiux.miniutils.module.Category;
import me.margiux.miniutils.module.Module;
import me.margiux.miniutils.module.ModuleManager;
import me.margiux.miniutils.utils.PlayerUtils;
import net.minecraft.command.argument.EntityAnchorArgumentType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;

public class Killaura extends Module {
    public static PlayerEntity exception;
    public Killaura(String name, String description, Category category, int activationKey) {
        super(name, description, category, activationKey);
        addSetting(ModuleManager.triggerBot.safeMode);
    }

    @Override
    protected void onEnable() {
        ModuleManager.triggerBot.enable();
        super.onEnable();
    }

    @Override
    protected void onDisable() {
        ModuleManager.triggerBot.disable();
        super.onDisable();
    }

    @ModuleEventHandler
    public void onUpdate(UpdateEvent e) {
        aim();
    }


    public void aim() {
        if (!isEnabled()) return;
        if (MC.currentScreen != null) return;
        if (!isClientInGame()) return;

        PlayerEntity target = PlayerUtils.getClosestPlayer(MC.player, 7);
        if (target == null || target == exception) return;

        double posX = target.getX() - MC.player.getX();
        double posZ = target.getZ() - MC.player.getZ();

        float newYaw = (float) Math.toDegrees(Math.atan2(posZ, posX)) - 90F;
        if (newYaw < 0) newYaw = -newYaw;
        float playerYaw = (MC.player.getYaw() < 0) ? -MC.player.getYaw() : MC.player.getYaw();


        if (newYaw - playerYaw > -20 && newYaw - playerYaw < 20) {
            Vec3d targetPos = target.getPos();

            Box box = target.getBoundingBox();
            double x = box.getCenter().x;
            double y = MC.player.getEyeY();
            double z = box.getCenter().z;

            if (targetPos.y > MC.player.getEyeY()) y = targetPos.y;
            else if (targetPos.y + target.getHeight() - 0.05 < MC.player.getEyeY()) y = targetPos.y + target.getHeight() - 0.05;
            MC.player.lookAt(EntityAnchorArgumentType.EntityAnchor.EYES, new Vec3d(x, y, z));
        }
    }
}
