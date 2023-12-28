package me.margiux.miniutils.utils;

import me.margiux.miniutils.Main;
import net.minecraft.text.Text;

public class HudUtil {
    public static void setSubTitle(String message) {
        Main.MC.inGameHud.setSubtitle(Text.literal(""));
        Main.MC.inGameHud.setTitleTicks(1, 20, 1);
        Main.MC.inGameHud.setTitle(Text.literal(""));
        Main.MC.inGameHud.setSubtitle(Text.literal(message));
        Main.MC.inGameHud.setDefaultTitleFade();
    }
    public static void setActionbar(String message) {
        Main.MC.inGameHud.setTitleTicks(1, 20, 1);
        Main.MC.inGameHud.setOverlayMessage(Text.literal(message), true);
        Main.MC.inGameHud.setDefaultTitleFade();
    }
}
