package me.margiux.miniutils.module;

import me.margiux.miniutils.Enum;
import me.margiux.miniutils.event.KeyEvent;
import me.margiux.miniutils.event.ModuleEventHandler;
import me.margiux.miniutils.event.OpenScreenEvent;
import me.margiux.miniutils.mixin.GenericContainerScreenAccessor;
import me.margiux.miniutils.mixin.HandledScreenAccessor;
import me.margiux.miniutils.mixin.ScreenAccessor;
import me.margiux.miniutils.module.setting.EnumSetting;
import me.margiux.miniutils.module.setting.IntegerSetting;
import me.margiux.miniutils.task.DelayTask;
import me.margiux.miniutils.task.TaskManager;
import net.minecraft.client.gui.screen.ingame.GenericContainerScreen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.ingame.ShulkerBoxScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public final class ChestStealer extends Module {
    public static class StealMode extends Enum<StealMode> {
        protected static final List<StealMode> enumList = new ArrayList<>();
        public static final StealMode ON_SCREEN_OPEN = new StealMode("On screen open", 0, false);
        public static final StealMode ON_KEY_PRESSED = new StealMode("On key pressed", 1, false);
        public static final StealMode ON_BUTTON_CLICKED = new StealMode("On button clicked", 2, false);

        public StealMode(String name, int ordinal, boolean displayOnly) {
            super(name, ordinal, displayOnly);
            enumList.add(this);
        }

        @SuppressWarnings("unused")
        @Override
        public StealMode next() {
            for (StealMode mode : enumList) {
                if (mode.ordinal <= this.ordinal) continue;
                if (mode.isDisplayOnly()) continue;
                return mode;
            }
            return enumList.get(0);
        }
    }
    public EnumSetting<StealMode> stealMode = new EnumSetting<>("Steal mode", "", StealMode.ON_BUTTON_CLICKED);
    public IntegerSetting tickDelay = new IntegerSetting("Delay", "Delay in ticks", 3);

    public ChestStealer(String name, String description, Category category, int activationKey) {
        super(name, description, category, activationKey);
        addSetting(tickDelay);
        addSetting(stealMode);
    }

    @ModuleEventHandler
    public void onScreenOpen(OpenScreenEvent event) {
        HandledScreen<?> screen;
        if (event.getScreen() instanceof GenericContainerScreen screen2) screen = screen2;
        else if (event.getScreen() instanceof ShulkerBoxScreen screen2) screen = screen2;
        else return;
        if (stealMode.getData() == StealMode.ON_BUTTON_CLICKED) {
            HandledScreenAccessor handledScreen = ((HandledScreenAccessor) screen);
            ((ScreenAccessor) screen).callAddDrawableChild(new ButtonWidget(handledScreen.getX() + handledScreen.getBackgroundWidth() - 60, handledScreen.getY() + 3, 54, 12, Text.literal("Steal"), (b) -> steal(screen)));
        } else if (stealMode.getData() == StealMode.ON_SCREEN_OPEN) TaskManager.addTask(new DelayTask((task) -> steal(screen), 5));
    }

    @ModuleEventHandler
    public void onKeyPressed(KeyEvent e) {
        if (e.getKey() == GLFW.GLFW_KEY_LEFT_ALT && stealMode.getData() == StealMode.ON_KEY_PRESSED && getClient().currentScreen != null && getClient().currentScreen instanceof HandledScreen<?> screen && (getClient().currentScreen instanceof GenericContainerScreen || getClient().currentScreen instanceof ShulkerBoxScreen)) {
            steal(screen);
        }
    }

    public <T extends HandledScreen<R>, R extends ScreenHandler> void steal(T screen) {
        try {
            new Thread(() -> {
                int rows;
                if (screen instanceof GenericContainerScreen screen2)
                    rows = ((GenericContainerScreenAccessor) screen2).getRows();
                else if (screen instanceof ShulkerBoxScreen) rows = 3;
                else rows = 3;
                for (int i = 0; i < rows * 9; i++) {
                    if (getClient().currentScreen != screen || getClient().interactionManager == null || getClient().player == null) break;
                    if (getClient().player.getInventory().getEmptySlot() == -1) return;

                    Slot slot = screen.getScreenHandler().slots.get(i);
                    if (slot.getStack().isEmpty())
                        continue;

                    AtomicBoolean canRun = new AtomicBoolean(false);
                    TaskManager.addTask(new DelayTask((task) -> canRun.set(true), tickDelay.getData()));

                    //noinspection StatementWithEmptyBody
                    while (!canRun.get()) {
                    }

                    getClient().interactionManager.clickSlot(screen.getScreenHandler().syncId, i, 0, SlotActionType.QUICK_MOVE, getClient().player);
                }
            }).start();
        } catch (Exception ignored) {
        }
    }
}
