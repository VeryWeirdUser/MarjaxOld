package me.margiux.miniutils.module.combat;

import me.margiux.miniutils.Mode;
import me.margiux.miniutils.event.EntityAddedEvent;
import me.margiux.miniutils.event.ModuleEventHandler;
import me.margiux.miniutils.module.Category;
import me.margiux.miniutils.module.Module;
import me.margiux.miniutils.task.DelayTask;
import me.margiux.miniutils.task.TaskManager;
import net.minecraft.client.network.OtherClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class AntiBot extends Module {
    public AntiBot(String name, String description, Category category, int activationKey, Mode defaultMode) {
        super(name, description, category, activationKey, defaultMode);
    }

    @ModuleEventHandler
    public void onEntityAdded(EntityAddedEvent e) {
        if (!isClientInGame()) return;

        if (!e.getEntity().getUuid().equals(UUID.nameUUIDFromBytes(("OfflinePlayer:" + e.getEntity().getName().getString()).getBytes(StandardCharsets.UTF_8))) &&
                e.getEntity() instanceof OtherClientPlayerEntity player &&
                !e.getEntity().getName().getString().contains("-")) {
            Killaura.exception = player;
            Vec3d prevPos = e.getEntity().getPos();
            TaskManager.addTask(new DelayTask((task) -> {
                if (prevPos != e.getEntity().getPos()) e.getEntity().remove(Entity.RemovalReason.KILLED);
                Killaura.exception = null;
            }, 5));
        }
    }
}