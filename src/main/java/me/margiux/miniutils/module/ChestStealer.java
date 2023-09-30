package me.margiux.miniutils.module;

import me.margiux.miniutils.Enum;

import java.util.ArrayList;
import java.util.List;

public class ChestStealer extends Module {
    public static class StealMode extends Enum<StealMode> {
        protected static final List<StealMode> enumList = new ArrayList<>();
        public static StealMode ON_SCREEN_OPEN = new StealMode("§aEnabled", 0, false);
        public static StealMode ON_KEY_PRESSED = new StealMode("§cDisabled", 1, false);

        public StealMode(String name, int ordinal, boolean displayOnly) {
            super(name, ordinal, displayOnly);
            enumList.add(this);
        }
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
    public ChestStealer(String name, String description, int activationKey) {
        super(name, description, activationKey);
    }
}
