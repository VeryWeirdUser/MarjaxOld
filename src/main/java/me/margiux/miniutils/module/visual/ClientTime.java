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
        public String getName() {
            return name;
        }

        @Override
        public Time getNext() {
            int thisIndex = 0;
            for (int i = 0; i < values().length; i++) {
                if (values()[i] == this) {
                    thisIndex = i;
                    break;
                }
            }
            for (int i = thisIndex; i < values().length; i++) {
                if (i != thisIndex) return values()[i];
            }
            return values()[0];
        }
    }
    public EnumSetting<Time> timeSetting = new EnumSetting<>("Time", "Time of the world", Time.SUNRISE);
    public ClientTime(String name, String description, Category category, int activationKey) {
        super(name, description, category, activationKey);
        addSetting(timeSetting);
    }
}
