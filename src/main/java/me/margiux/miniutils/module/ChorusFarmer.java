package me.margiux.miniutils.module;

import me.margiux.miniutils.Main;
import me.margiux.miniutils.gui.*;
import me.margiux.miniutils.gui.widget.Input;
import me.margiux.miniutils.mutable.MutableExtended;
import me.margiux.miniutils.utils.HudUtil;
import net.minecraft.block.ChorusFlowerBlock;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.BowItem;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;

import java.util.ArrayList;
import java.util.List;

public class ChorusFarmer extends Module {
    public MutableExtended<Integer> radius;
    public MutableExtended<Integer> maxY;
    private final Input<Integer> radiusField;
    private final Input<Integer> maxYField;

    public ChorusFarmer(String name, String description) {
        super(name, description);
        radius = new MutableExtended<>(50);
        maxY = new MutableExtended<>(20);
        radiusField = new Input<>("Radius of chorus flower searching area", radius, Input.INT_FILTER, (e) -> radius.setValue(Integer.valueOf(e), true));
        maxYField = new Input<>("Radius of chorus flower searching area by Y", maxY, Input.INT_FILTER, (e) -> maxY.setValue(Integer.valueOf(e), true));
    }

    public List<BlockPos> blocks = new ArrayList<>();
    public int tick = 0;

    public void findFlowers(MinecraftClient client) {
        blocks.clear();
        int radius = 25;
        int yRadius = 20;
        try {
            radius = (this.radius.getValue() == 0 || this.radius.getValue() > 100) ? radius : this.radius.getValue();
            yRadius = maxY.getValue();
        } catch (Exception e) {
            Main.instance.LOGGER.error("Failed to parse input!", e);
            radius = 25;
        }
        if (client.world != null) {
            for (int x = -radius; x < radius; x++) {
                for (int y = -yRadius; y < yRadius; y++) {
                    for (int z = -radius; z < radius; z++) {
                        if (client.player != null) {
                            if (client.world.getBlockState(new BlockPos(client.player.getX() + x, client.player.getY() + y, client.player.getZ() + z)).getBlock() instanceof ChorusFlowerBlock) {
                                blocks.add(new BlockPos(client.player.getX() + x, client.player.getY() + y, client.player.getZ() + z));
                            }
                        }
                    }
                }
            }
        }
    }

    public void tick() {
        if (!isEnabled()) return;
        MinecraftClient client = Main.instance.getClient();
        if (client.player != null && client.interactionManager != null && client.player.getMainHandStack().getItem() instanceof BowItem) {
            findFlowers(client);
            if (!blocks.isEmpty()) {
                BlockPos target = null;
                for (BlockPos pos : blocks) {
                    BlockPos raycast = client.world.raycast(
                            new RaycastContext(new Vec3d(client.player.getEyePos().getX(), client.player.getEyePos().getY(), client.player.getEyePos().getZ()),
                                    new Vec3d(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5),
                                    RaycastContext.ShapeType.COLLIDER,
                                    RaycastContext.FluidHandling.SOURCE_ONLY, client.player
                            )
                    ).getBlockPos();

                    if (raycast.getX() == pos.getX() && raycast.getY() == pos.getY() && raycast.getZ() == pos.getZ()) {
                        if (pos.getSquaredDistance(client.player.getEyePos()) < ((target != null) ? target.getSquaredDistance(client.player.getEyePos()) : 10000)) {
                            target = pos;
                        }
                    }
                }
                if (target == null) {
                    Main.instance.LOGGER.warn("Target is null, preventing module from further actions");
                    return;
                }
                double posX = target.getX() + 0.5 - client.player.getX();
                double posY = target.getY() + 0.5 - (client.player.getY() + client.player.getEyeHeight(client.player.getPose()));
                double posZ = target.getZ() + 0.5 - client.player.getZ();

                double posXZ = Math.sqrt(posX * posX + posZ * posZ);

                float newYaw = (float) Math.toDegrees(Math.atan2(posZ, posX)) - 90F;

                double hDistanceSq = posXZ * posXZ;
                float g = 0.006F;
                float velocitySq = 1;
                float velocityPow4 = velocitySq * velocitySq;
                float newPitch = (float) -Math.toDegrees(Math.atan((velocitySq - Math
                        .sqrt(velocityPow4 - g * (g * hDistanceSq + 2 * posY * velocitySq)))
                        / (g * posXZ)));

                client.player.setYaw(client.player.getYaw() + MathHelper.wrapDegrees(newYaw - client.player.getYaw()));
                client.player.setPitch(client.player.getPitch() + MathHelper.wrapDegrees(newPitch - client.player.getPitch()));
                useBow();
            }
        }
    }

    private void useBow() {
        if (tick <= 22) {
            getClient().options.useKey.setPressed(true);
        }
        if (tick == 23) {
            getClient().interactionManager.stopUsingItem(getClient().player);
            tick = 0;
            return;
        }
        tick++;
    }

    @Override
    public void onEnable() {
        HudUtil.setSubTitle("§7ChorusFarmer: §aEnabled");
    }

    @Override
    public void onDisable() {
        tick = 0;
        blocks.clear();
        getClient().options.useKey.setPressed(false);
        HudUtil.setSubTitle("§7ChorusFarmer: §4Disabled");
    }

    @Override
    public void initGui() {
        MiniutilsGui.instance.main.add(toggleButton);
        MiniutilsGui.instance.main.add(radiusField);
        MiniutilsGui.instance.main.add(maxYField);
    }
}
