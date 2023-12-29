package me.margiux.miniutils.access;

import net.minecraft.text.Text;

public interface IChatHud {
    void addHiddenMessage(String message);
    void addHiddenMessage(Text message);
}
