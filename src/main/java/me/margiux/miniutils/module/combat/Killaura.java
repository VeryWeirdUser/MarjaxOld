package me.margiux.miniutils.module.combat;

import me.margiux.miniutils.event.KeyEvent;
import me.margiux.miniutils.event.ModuleEventHandler;
import me.margiux.miniutils.event.UpdateEvent;
import me.margiux.miniutils.module.Category;
import me.margiux.miniutils.module.Module;
import me.margiux.miniutils.module.ModuleManager;
import me.margiux.miniutils.utils.PlayerUtils;
import net.minecraft.command.argument.EntityAnchorArgumentType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.glfw.GLFW;

public class Killaura extends Module {
    public static PlayerEntity exception;
    @Nullable
    public static PlayerEntity target;
    long lastClick;

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

        if (target != null && !PlayerUtils.isAttackable(target)) resetTarget();

        if (!PlayerUtils.isAttackable(target) || target == exception) return;

        double posX = target.getX() - MC.player.getX();
        double posZ = target.getZ() - MC.player.getZ();

        float newYaw = MathHelper.wrapDegrees((float) (MathHelper.atan2(posZ, posX) * 57.2957763671875) - 90.0F);
        float playerYaw = MathHelper.wrapDegrees(MC.player.getYaw());


        if (MathHelper.angleBetween(newYaw, playerYaw) < 25) {
            double targetY = MC.player.getEyeY();
            if (target.getY() > targetY) targetY = target.getY();
            else if (target.getY() + target.getHeight() - 0.05 < targetY)
                targetY = target.getY() + target.getHeight() - 0.05;

            double x = target.getX() - MC.player.getX();
            double y = targetY - MC.player.getEyeY();
            double z = target.getZ() - MC.player.getZ();

            double d = Math.sqrt(x * x + z * z);
            float pitch = MathHelper.wrapDegrees((float) (MathHelper.atan2(y, d) * 57.2957763671875));
            float yaw = MathHelper.wrapDegrees((float) (MathHelper.atan2(z, x) * 57.2957763671875) - 90.0F);
            MC.player.setPitch((MC.player.getPitch() % 90) + ((pitch - MC.player.getPitch() % 90) / 10));
            MC.player.setYaw(MathHelper.wrapDegrees(playerYaw + (yaw - playerYaw) / 10));
            MC.player.setBodyYaw(MC.player.getYaw());
        }
    }

    @ModuleEventHandler
    public void onKey(KeyEvent event) {
        if (event.pressed() && event.getKey() == GLFW.GLFW_KEY_X) {
            if (System.currentTimeMillis() - lastClick <= 250L) {
                if (target != null) resetTarget();
                return;
            }
            lastClick = System.currentTimeMillis();
            target = PlayerUtils.getPlayerFromLook();
            if (target != null) sendMessage(target.getName().getString() + " is now selected as the target!");
        }
    }

    public void resetTarget() {
        target = null;
        sendMessage("Target removed!");
    }
}