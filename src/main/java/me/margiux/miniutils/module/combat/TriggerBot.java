package me.margiux.miniutils.module.combat;

import me.margiux.miniutils.event.ModuleEventHandler;
import me.margiux.miniutils.event.TickEvent;
import me.margiux.miniutils.module.Category;
import me.margiux.miniutils.module.Module;
import me.margiux.miniutils.setting.BooleanSetting;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.entity.projectile.ShulkerBulletEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.EntityHitResult;

public class TriggerBot extends Module {
    public BooleanSetting safeMode = new BooleanSetting("Safe mode", "Should TriggerBot make a delay before hitting player", true);
    public int randomTickDelay = 0;

    public TriggerBot(String name, String description, Category category, int activationKey) {
        super(name, description, category, activationKey);
        addSetting(safeMode);
    }

    @ModuleEventHandler
    public void onTick(TickEvent event) {
        if (getClient().crosshairTarget != null && getClient().crosshairTarget instanceof EntityHitResult result && getClient().player != null && getClient().interactionManager != null) {
            if (getClient().currentScreen != null) return;
            if (getClient().player.getAttackCooldownProgress(0f) != 1) return;
            if (getClient().player.distanceTo(result.getEntity()) > getClient().interactionManager.getReachDistance()) {
                return;
            }

            Entity e = result.getEntity();
            if (!(e != null && !e.isRemoved() && (e instanceof LivingEntity &&
                    ((LivingEntity) e).getHealth() > 0 || e instanceof EndCrystalEntity || e instanceof ShulkerBulletEntity)
                    && e != getClient().player)) return;

            if (--randomTickDelay <= 0 || !safeMode.getData()) {
                if (safeMode.getData()) randomTickDelay = (int) Math.round(Math.random() * 4);
                getClient().interactionManager.attackEntity(getClient().player, e);
                getClient().player.swingHand(Hand.MAIN_HAND);
            }
        }
    }
}
