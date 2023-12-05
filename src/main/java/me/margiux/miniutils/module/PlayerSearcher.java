package me.margiux.miniutils.module;

import me.margiux.miniutils.event.KeyEvent;
import me.margiux.miniutils.event.ModuleEventHandler;
import me.margiux.miniutils.event.TickEvent;
import me.margiux.miniutils.setting.StringSetting;
import me.margiux.miniutils.utils.HudUtil;
import net.minecraft.command.argument.EntityAnchorArgumentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import org.lwjgl.glfw.GLFW;

public class PlayerSearcher extends Module {
    private PlayerEntity player = null;
    StringSetting playerName = new StringSetting("Player name", "Name of the player");

    public PlayerSearcher(String name, String description, Category category, int activationKey) {
        super(name, description, category, activationKey);
        addSetting(playerName);
    }

    @ModuleEventHandler
    public void onTick(TickEvent event) {
        player = null;
        if (getClient().player == null) return;
        if (getClient().world == null) return;
        if (playerName.getData() == null) return;
        for (Entity e : getClient().world.getEntities()) {
            if (e instanceof PlayerEntity pe && pe.getName().getString().equals(playerName.getData())) {
                player = pe;
                break;
            }
        }

        if (player == null) {
            HudUtil.setActionbar("§cPlayer §f" + playerName.getData() + "§c not found");
            return;
        }
        String pos = player.getBlockPos().toShortString();

        HudUtil.setActionbar("§aPlayer §f" + playerName.getData() + "§a found, position: §f" + pos + "§a | Press arrow up to set look at player");
    }

    @ModuleEventHandler
    public void onKey(KeyEvent event) {
        if (event.getKey() == GLFW.GLFW_KEY_UP && event.getAction() == 0 && player != null) {
            if (getClient().player == null || getClient().world == null) return;
            getClient().player.lookAt(EntityAnchorArgumentType.EntityAnchor.EYES, player.getPos());
        }
    }
}
