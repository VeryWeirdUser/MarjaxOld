package me.margiux.miniutils.gui;

import io.github.cottonmc.cotton.gui.widget.TooltipBuilder;
import io.github.cottonmc.cotton.gui.widget.WButton;
import net.minecraft.text.Text;
import org.apache.commons.lang3.mutable.MutableBoolean;

public class ModuleWidget extends WButton {
    public final String name;
    public final String description;
    public MutableBoolean toggle;

    public ModuleWidget(String name, String description) {
        this.name = name;
        this.description = description;
        this.setLabel(Text.literal(name + ": " + (this.toggle.booleanValue() ? "§aenabled" : "§4disabled")));
        this.addTooltip(new TooltipBuilder().add(Text.literal(description)));
        this.setOnClick(() -> {
            this.toggle.setValue(!this.toggle.booleanValue());
            this.setLabel(Text.literal(name + ": " + (this.toggle.booleanValue() ? "§aenabled" : "§4disabled")));
            toggle.setValue(this.toggle.booleanValue());
        });
    }
}