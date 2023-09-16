package me.margiux.miniutils.gui.input;

import io.github.cottonmc.cotton.gui.widget.TooltipBuilder;
import io.github.cottonmc.cotton.gui.widget.WTextField;
import me.margiux.miniutils.gui.Widget;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

import java.util.Objects;

public abstract class InputWidget<T> extends WTextField implements Widget {
    public final String name;
    public final String description;
    public final T defaultValue;

    public InputWidget(String name, String description, T defaultValue) {
        this.name = name;
        this.description = description;
        this.defaultValue = defaultValue;
        this.setSuggestion(Text.literal(this.name));
        this.addTooltip(new TooltipBuilder().add(Text.literal(this.description)));
    }

    @Override
    public void onCharTyped(char ch) {
        super.onCharTyped(ch);
        if (ch == GLFW.GLFW_KEY_ENTER && Objects.equals(getText(), "")) {
            setText(getDefaultValue());
        }
    }

    public abstract String getDefaultValue();
}