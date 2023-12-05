package me.margiux.miniutils;

import me.margiux.miniutils.event.ChatReceiveMessageEvent;
import me.margiux.miniutils.event.EventHandler;
import me.margiux.miniutils.event.KeyEvent;
import me.margiux.miniutils.event.Listener;
import me.margiux.miniutils.task.RepeatTask;
import me.margiux.miniutils.task.TaskManager;
import me.margiux.miniutils.utils.HudUtil;
import org.lwjgl.glfw.GLFW;

public class MainListener implements Listener {
    public static boolean panicTriggered = false;
    public static int holdTime = 0;
    public static RepeatTask repeatTask = new RepeatTask((task -> {
        HudUtil.setActionbar("§c§lIf you want to continue - press Enter, otherwise press Ctrl + C");
        HudUtil.setSubTitle("§e§l[WARNING] Panic mode triggered!");
    }), 1);

    @EventHandler(executeInPanicMode = true)
    public static void onKey(KeyEvent event) {
        if (event.getModifiers() == GLFW.GLFW_MOD_ALT && event.getKey() == GLFW.GLFW_KEY_KP_DECIMAL) {
            if (event.getAction() == 2) holdTime++;
            if (holdTime > 60 && !panicTriggered) {
                panicTriggered = true;
                TaskManager.addTask(repeatTask);
            } else if (event.getAction() == 0) {
                if (panicTriggered && holdTime != 0) {
                    holdTime = 0;
                    return;
                }
                if (!panicTriggered) holdTime = 0;
                Main.instance.openScreen();
                event.setCanceled();
            }
        }
        if (panicTriggered) {
            if (event.getKey() == GLFW.GLFW_KEY_ENTER) {
                Main.instance.changeStatus(CheatMode.PANIC);
                TaskManager.removeTask(repeatTask);
            } else if (event.getKey() == GLFW.GLFW_KEY_C && event.getModifiers() == 2) {
                holdTime = 0;
                panicTriggered = false;
                TaskManager.removeTask(repeatTask);
            }
        }
    }

    @EventHandler(ignoreCanceled = false)
    public static void onChat(ChatReceiveMessageEvent message) {
        if (message.message.getString().contains("Вы были вызваны на проверку читов") && Main.instance.STATUS.getValue() != CheatMode.ENABLED) {
            HudUtil.setSubTitle("§c§lHacks have been disabled!");
            Main.instance.changeStatus(CheatMode.DISABLED);
        }
    }
}
