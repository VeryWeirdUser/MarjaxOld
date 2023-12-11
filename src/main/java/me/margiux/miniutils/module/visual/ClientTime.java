package me.margiux.miniutils.module.visual;

import me.margiux.miniutils.module.Category;
import me.margiux.miniutils.module.Module;
import me.margiux.miniutils.setting.EnumSetting;
import me.margiux.miniutils.utils.Enum;

public class ClientTime extends Module {
    public enum Time implements Enum<Time> {
        NIGHT(18000, "Night"),
        SUNRISE(23250, "Sunrise"),
        DAY(6000, "Day"),
        SUNSET(12500, "Sunset");

        public final int time;
        public final String name;

        Time(int time, String name) {
            this.time = time;
            this.name = name;
        }

        @Override
        public Time[] getEnumValues() {
            return values();
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public boolean isDisplayOnly() {
            return false;
        }
    }
    public EnumSetting<Time> timeSetting = new EnumSetting<>("Time", "Time of the world", Time.SUNRISE);
    public ClientTime(String name, String description, Category category, int activationKey) {
        super(name, description, category, activationKey);
        addSetting(timeSetting);
    }
}
