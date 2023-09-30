package me.margiux.miniutils.gui.widget;

import io.github.cottonmc.cotton.gui.widget.TooltipBuilder;
import io.github.cottonmc.cotton.gui.widget.WButton;

import me.margiux.miniutils.utils.Mutable;
import net.minecraft.text.Text;

public class Toggle extends WButton implements Widget {
    public final String name;
    public final String description;
    public final Mutable<Boolean> toggle;
    public final Runnable runnable;

    public Toggle(String name, String description, Mutable<Boolean> toggle, Runnable runnable) {
        this.name = name;
        this.description = description;
        this.toggle = toggle;
        this.runnable = runnable;
        this.setLabel(Text.literal(name + ": " + (this.toggle.getValue() ? "§aenabled" : "§4disabled")));
        this.addTooltip(new TooltipBuilder().add(Text.literal(description)));
        this.setOnClick(runnable);
        this.toggle.setOnValueChanged((active) -> this.setLabel(Text.literal(name + ": " + (active ? "§aenabled" : "§4disabled"))));
    }
}