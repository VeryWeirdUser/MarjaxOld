package me.margiux.miniutils.module.combat;

import me.margiux.miniutils.event.ModuleEventHandler;
import me.margiux.miniutils.event.TickEvent;
import me.margiux.miniutils.module.Category;
import me.margiux.miniutils.module.Module;
import me.margiux.miniutils.module.ModuleManager;
import me.margiux.miniutils.setting.BooleanSetting;
import me.margiux.miniutils.utils.PlayerUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.entity.projectile.ShulkerBulletEntity;
import net.minecraft.util.hit.EntityHitResult;

public class TriggerBot extends Module {
    public final BooleanSetting safeMode = new BooleanSetting("Safe mode", "Should TriggerBot make a delay before hitting player", true);
    public int randomTickDelay = 0;

    public TriggerBot(String name, String description, Category category, int activationKey) {
        super(name, description, category, activationKey);
        addSetting(safeMode);
    }

    @Override
    protected void onDisable() {
        ModuleManager.killaura.disable();
        super.onDisable();
    }

    @ModuleEventHandler
    public void onTick(TickEvent event) {
        if (!isClientInGame()) return;
        if (MC.currentScreen == null) return;
        if (MC.player.getAttackCooldownProgress(0f) != 1) return;
        if (MC.crosshairTarget instanceof EntityHitResult result) {
            if (MC.player.squaredDistanceTo(result.getEntity()) > MC.interactionManager.getReachDistance() * MC.interactionManager.getReachDistance()) return;

            Entity e = result.getEntity();
            if (!(e != null && !e.isRemoved() && ((e instanceof LivingEntity l && l.getHealth() > 0) || e instanceof EndCrystalEntity || e instanceof ShulkerBulletEntity) && e != MC.player))
                return;

            if (--randomTickDelay <= 0 || !safeMode.getData()) {
                if (MC.player.isUsingItem()) return;
                if (safeMode.getData()) randomTickDelay = (int) Math.round(Math.random() * 4);
                PlayerUtils.attackEntity(e);
            }
        }
    }
}
