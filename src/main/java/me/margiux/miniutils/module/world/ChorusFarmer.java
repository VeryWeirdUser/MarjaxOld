package me.margiux.miniutils.module.world;

import me.margiux.miniutils.Main;
import me.margiux.miniutils.event.ModuleEventHandler;
import me.margiux.miniutils.event.TickEvent;
import me.margiux.miniutils.gui.widget.Field;
import me.margiux.miniutils.module.Category;
import me.margiux.miniutils.module.Module;
import me.margiux.miniutils.setting.FieldSetting;
import net.minecraft.block.ChorusFlowerBlock;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.BowItem;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;

import java.util.ArrayList;
import java.util.List;

public final class ChorusFarmer extends Module {
    private final FieldSetting radiusField = new FieldSetting("Radius", "Radius of chorus flower searching area", "50", Field.NUMBER_PREDICATE);
    private final FieldSetting maxYField = new FieldSetting("Y radius", "Radius of chorus flower searching area by Y", "20", Field.NUMBER_PREDICATE);

    public ChorusFarmer(String name, String description, Category category, int activationKey) {
        super(name, description, category, activationKey);
        addSetting(radiusField);
        addSetting(maxYField);
    }

    public final List<BlockPos> blocks = new ArrayList<>();
    public int tick = 0;

    public void findFlowers(MinecraftClient client) {
        blocks.clear();
        int radius = 25;
        int yRadius = 20;
        try {
            radius = (radiusField.getIntegerData() < 0 || radiusField.getIntegerData() >= 100) ? radius : radiusField.getIntegerData();
            yRadius = maxYField.getIntegerData();
        } catch (Exception e) {
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

    @ModuleEventHandler
    public void tick(TickEvent event) {
        MinecraftClient client = Main.instance.getClient();
        if (client.player != null && client.interactionManager != null && client.player.getMainHandStack().getItem() instanceof BowItem) {
            findFlowers(client);
            if (!blocks.isEmpty()) {
                BlockPos target = null;
                for (BlockPos pos : blocks) {
                    BlockPos raycast = null;
                    if (client.world != null) {
                        raycast = client.world.raycast(
                                new RaycastContext(new Vec3d(client.player.getEyePos().getX(), client.player.getEyePos().getY(), client.player.getEyePos().getZ()),
                                        new Vec3d(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5),
                                        RaycastContext.ShapeType.COLLIDER,
                                        RaycastContext.FluidHandling.SOURCE_ONLY, client.player
                                )
                        ).getBlockPos();
                    }

                    if (raycast != null && raycast.getX() == pos.getX() && raycast.getY() == pos.getY() && raycast.getZ() == pos.getZ()) {
                        if (pos.getSquaredDistance(client.player.getEyePos()) < ((target != null) ? target.getSquaredDistance(client.player.getEyePos()) : 10000)) {
                            target = pos;
                        }
                    }
                }
                if (target == null) return;

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
            if (getClient().interactionManager == null) return;
            getClient().interactionManager.stopUsingItem(getClient().player);
            tick = 0;
            return;
        }
        tick++;
    }

    @Override
    public void onDisable() {
        super.onDisable();
        tick = 0;
        blocks.clear();
        getClient().options.useKey.setPressed(false);
    }
}
