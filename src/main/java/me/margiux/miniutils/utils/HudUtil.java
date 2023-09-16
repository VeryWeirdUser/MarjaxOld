package me.margiux.miniutils.utils;

import me.margiux.miniutils.Main;
import net.minecraft.text.Text;

public class HudUtil {
    public static void setSubTitle(String message) {
        Main.instance.getClient().inGameHud.setSubtitle(Text.literal(""));
        Main.instance.getClient().inGameHud.setTitleTicks(1, 20, 1);
        Main.instance.getClient().inGameHud.setTitle(Text.literal(""));
        Main.instance.getClient().inGameHud.setSubtitle(Text.literal(message));
        Main.instance.getClient().inGameHud.setDefaultTitleFade();
    }
    public static void setActionbar(String message) {
        Main.instance.getClient().inGameHud.setTitleTicks(1, 20, 1);
        Main.instance.getClient().inGameHud.setOverlayMessage(Text.literal(message), true);
        Main.instance.getClient().inGameHud.setDefaultTitleFade();
    }
}
