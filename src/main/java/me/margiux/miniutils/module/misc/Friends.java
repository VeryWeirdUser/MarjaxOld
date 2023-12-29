package me.margiux.miniutils.module.misc;

import me.margiux.miniutils.event.ModuleEventHandler;
import me.margiux.miniutils.event.MouseEvent;
import me.margiux.miniutils.module.Category;
import me.margiux.miniutils.module.Module;
import me.margiux.miniutils.utils.PlayerUtils;
import net.minecraft.entity.player.PlayerEntity;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;

public class Friends extends Module {
    public static final List<PlayerEntity> friends = new ArrayList<>();

    public Friends(String name, String description, Category category, int activationKey) {
        super(name, description, category, activationKey);
    }

    public static boolean isFriend(PlayerEntity player) {
        return friends.contains(player);
    }

    @ModuleEventHandler
    public void onMouse(MouseEvent event) {
        if (event.clicked() && event.getButton() == GLFW.GLFW_MOUSE_BUTTON_MIDDLE) {
            if (event.isDoubleClick()) {
                PlayerUtils.getPlayerFromLook();
            }
        }
    }
}