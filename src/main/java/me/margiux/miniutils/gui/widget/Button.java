package me.margiux.miniutils.gui.widget;

import io.github.cottonmc.cotton.gui.widget.TooltipBuilder;
import io.github.cottonmc.cotton.gui.widget.WButton;

import io.github.cottonmc.cotton.gui.widget.WWidget;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.function.Consumer;

public class Button extends WButton implements Widget {
    public final String name;
    public final String description;

    public Button(String name, String description, @Nullable Consumer<Button> consumer) {
        this.name = name;
        this.description = description;
        this.setConsumer(consumer);
        this.setLabel(Text.literal(name));
        this.addTooltip(new TooltipBuilder().add(Text.literal(description)));
    }

    public Button setConsumer(Consumer<Button> consumer) {
        this.setOnClick(() -> consumer.accept(this));
        return this;
    }

    public void destroy() {
        Objects.requireNonNull(this.getParent()).remove(this);
    }
}