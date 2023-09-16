package me.margiux.miniutils.module;

import me.margiux.miniutils.CheatMode;
import me.margiux.miniutils.Mode;

import java.util.ArrayList;
import java.util.List;

public class ModuleManager {
    public static final List<Module> modules = new ArrayList<>();
    public static final Truesight truesight = new Truesight("Truesight", "Makes invisible players visible");
    public static final ChorusFarmer chorusFarmer = new ChorusFarmer("ChorusFarmer", "Farms choruses automatically");
    public static final ElytraHunter elytraHunter = new ElytraHunter("ElytraHunter", "Informs if invisible players with an elytra are found and shoots 'em down if required ) xD");
    public static final AuctionSeller auctionSeller = new AuctionSeller("AuctionSeller", "(Re)sells items to auction");

    static {
        modules.add(truesight);
        modules.add(chorusFarmer);
        modules.add(elytraHunter);
        modules.add(auctionSeller);
    }

    public static void disable() {
        for (Module module : modules) {
            if (module.mode.getValue() == Mode.ENABLED) {
                module.changeMode(Mode.FORCE_DISABLED);
                module.disabledByMain = true;
            } else module.changeMode(Mode.FORCE_DISABLED);
        }
    }

    public static void enableDisabled() {
        for (Module module : modules) {
            if (module.mode.getValue() == Mode.FORCE_DISABLED) {
                module.changeMode(module.disabledByMain ? Mode.ENABLED : Mode.DISABLED);
                module.disabledByMain = false;
            }
        }
    }

    public static void initGuiElements() {
        for (Module module : modules) {
            module.initGui();
        }
    }
}
