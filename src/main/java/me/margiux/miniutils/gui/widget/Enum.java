package me.margiux.miniutils.gui.widget;

import io.github.cottonmc.cotton.gui.widget.TooltipBuilder;
import io.github.cottonmc.cotton.gui.widget.WButton;
import me.margiux.miniutils.mutable.MutableExtended;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class Enum<T extends me.margiux.miniutils.Enum<T>> extends WButton implements Widget {
    public final String name;
    public final String description;
    public final MutableExtended<T> mutableEnum;
    public Consumer<T> customAction;

    public Enum(String name, String description, MutableExtended<T> mutableEnum, @Nullable Consumer<T> customAction) {
        this.name = name;
        this.description = description;
        this.mutableEnum = mutableEnum;
        if (customAction != null) this.customAction = customAction;

        this.setLabel(Text.literal(name + ": " + this.mutableEnum.getValue().getName()));
        this.addTooltip(new TooltipBuilder().add(Text.literal(description)));
        this.setOnClick(() -> {
            if (!mutableEnum.getValue().isDisplayOnly()) {
                mutableEnum.setValue(mutableEnum.getValue().next());
                this.setLabel(Text.literal(name + ": " + this.mutableEnum.getValue().getName()));
                if (this.customAction != null) {
                    this.customAction.accept(this.mutableEnum.getValue());
                }
            }
        });
        this.mutableEnum.setOnValueChanged((newValue) -> this.setLabel(Text.literal(name + ": " + this.mutableEnum.getValue().getName())));
    }
}
