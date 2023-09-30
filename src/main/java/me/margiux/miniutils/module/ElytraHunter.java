package me.margiux.miniutils.module;

import me.margiux.miniutils.Main;
import me.margiux.miniutils.event.KeyEvent;
import me.margiux.miniutils.event.ModuleEventHandler;
import me.margiux.miniutils.event.TickEvent;
import me.margiux.miniutils.gui.widget.Input;
import me.margiux.miniutils.gui.MiniutilsGui;
import me.margiux.miniutils.utils.Mutable;
import me.margiux.miniutils.task.DelayTask;
import me.margiux.miniutils.task.TaskManager;
import me.margiux.miniutils.utils.HudUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("FieldCanBeLocal")
public class ElytraHunter extends Module {
    enum Aimlock {
        None,
        Requested,
        Confirmed
    }

    private Aimlock aimlockStatus;
    private final Mutable<Float> radiusInput = new Mutable<>(250f);
    private final Input<Float> radiusField = new Input<>("Radius of search", radiusInput, Input.INT_FILTER, (e) -> radiusInput.setValue(Float.valueOf(e), true));

    public ElytraHunter(String name, String description, int activationKey) {
        super(name, description, activationKey);
    }

    private PlayerEntity target = null;
    private final List<PlayerEntity> targets = new ArrayList<>();
    private int selectedTarget;
    private boolean canRun = true;
    private boolean wasFound = false;

    public void findPlayers(float radius) {
        targets.clear();
        for (PlayerEntity e : getClient().world.getPlayers()) {
            if (e != getClient().player) {
                for (ItemStack i :
                        e.getArmorItems()) {
                    if (i.getItem() == Items.ELYTRA) {
                        if (e.distanceTo(getClient().player) <= radius) targets.add(e);
                    }
                }
            }
        }
    }

    public boolean hasTargets() {
        return !targets.isEmpty();
    }

    @ModuleEventHandler
    public void tick(TickEvent event) {
        if (!canRun) return;
        if (getClient().world == null) return;
        float radius = 25;
        try {
            radius = (radiusInput.getValue() == 0) ? radius : radiusInput.getValue();
        } catch (Exception e) {
            Main.instance.LOGGER.error("Failed to parse input!", e);
        }
        wasFound = hasTargets();
        findPlayers(radius);

        if (hasTargets() && !wasFound) {
            HudUtil.setActionbar("§fTargets found! Use arrows to select target!");
        }
        if (selectedTarget > targets.size())
            selectedTarget = targets.size() - 1;

        if (aimlockStatus == Aimlock.Confirmed) {
            aim();
        }
    }

    public void selectNext() {
        if (hasTargets()) {
            if (++selectedTarget >= targets.size())
                selectedTarget = 0;
            HudUtil.setActionbar(String.format("§eSelected target: %s", targets.get(selectedTarget).getDisplayName().getString()));
            aim(targets.get(selectedTarget));
        }
    }

    public void selectPrevious() {
        if (hasTargets()) {
            if (--selectedTarget == -1)
                selectedTarget = targets.size() - 1;
            HudUtil.setActionbar(String.format("§eSelected target: %s", targets.get(selectedTarget).getDisplayName().getString()));
            aim(targets.get(selectedTarget));
        }
    }

    public void requestAimlock() {
        if (aimlockStatus == Aimlock.Confirmed) {
            HudUtil.setActionbar(String.format("§eYou already did confirm aimlock, to cancel press arrow down! Target: %s!", target.getDisplayName().getString()));
            return;
        }
        if (aimlockStatus == Aimlock.Requested) {
            aimlockStatus = Aimlock.Confirmed;
            if (selectedTarget >= targets.size() || selectedTarget < 0) {
                abortAimlock("§cInvalid target selected, select target and repeat aimlock!");
                return;
            }
            target = targets.get(selectedTarget);
            if (target != null)
                HudUtil.setActionbar(String.format("§aAimlock confirmed. Target: %s!", target.getDisplayName().getString()));
            return;
        }
        aimlockStatus = Aimlock.Requested;
        HudUtil.setActionbar("§aPress up arrow again to confirm aimlock!");
    }

    public void abortAimlock(String message) {
        if (target == null) {
            HudUtil.setActionbar("§eNo target is selected, abort failed");
            return;
        }
        reset();
        HudUtil.setActionbar(message);
    }

    public void abortAimlock() {
        abortAimlock("§cAimlock aborted!");
    }

    public void aim() {
        aim(target);
    }

    public void aim(PlayerEntity target) {
        for (PlayerEntity p : targets) {
            if (p.getUuid() == target.getUuid()) {
                target = p;
                break;
            }
        }
        if (hasTargets()) {
            double posX = target.getX() - getClient().player.getX();
            double posY = target.getY() - (getClient().player.getY() + getClient().player.getEyeHeight(getClient().player.getPose()));
            double posZ = target.getZ() - getClient().player.getZ();

            double posXZ = Math.sqrt(posX * posX + posZ * posZ);

            float newYaw = (float) Math.toDegrees(Math.atan2(posZ, posX)) - 90F;

            double hDistanceSq = posXZ * posXZ;
            float g = 0.006F;
            float velocitySq = 1;
            float velocityPow4 = velocitySq * velocitySq;
            float newPitch = (float) -Math.toDegrees(Math.atan((velocitySq - Math
                    .sqrt(velocityPow4 - g * (g * hDistanceSq + 2 * posY * velocitySq)))
                    / (g * posXZ)));

            getClient().player.setYaw(getClient().player.getYaw() + MathHelper.wrapDegrees(newYaw - getClient().player.getYaw()));
            getClient().player.setPitch(getClient().player.getPitch() + MathHelper.wrapDegrees(newPitch - getClient().player.getPitch()));
        }
    }

    @Override
    public void initGui() {
        MiniutilsGui.instance.main.add(toggleButton);
        MiniutilsGui.instance.main.add(radiusField);
    }

    public void reset() {
        target = null;
        wasFound = false;
        targets.clear();
        canRun = false;
        selectedTarget = 0;
        TaskManager.addTask(new DelayTask((task) -> canRun = true, 20));
    }

    @ModuleEventHandler
    public void onKey(KeyEvent event) {
        if (getClient().world == null) return;
        if (event.getAction() != 1) return;
        switch (event.getKey()) {
            case GLFW.GLFW_KEY_RIGHT -> selectNext();
            case GLFW.GLFW_KEY_LEFT -> selectPrevious();
            case GLFW.GLFW_KEY_UP -> requestAimlock();
            case GLFW.GLFW_KEY_DOWN -> abortAimlock();
        }
        if (event.getModifiers() == 7 && event.getKey() == GLFW.GLFW_KEY_DOWN) {
            reset();
            HudUtil.setActionbar("§cForce aimlock reset [ElytraHunter]");
        }
    }
}