package me.margiux.miniutils.module;

import me.margiux.miniutils.Mode;
import me.margiux.miniutils.event.EventManager;
import me.margiux.miniutils.event.Listener;
import me.margiux.miniutils.event.TickEvent;
import me.margiux.miniutils.gui.MiniutilsGui;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;

public class ModuleManager {
    public static final List<Module> modules = new ArrayList<>();
    public static final Truesight truesight = new Truesight("Truesight", "Makes invisible players visible", GLFW.GLFW_KEY_I);
    public static final ChorusFarmer chorusFarmer = new ChorusFarmer("ChorusFarmer", "Farms choruses automatically", GLFW.GLFW_KEY_G);
    public static final ElytraHunter elytraHunter = new ElytraHunter("ElytraHunter", "Informs if invisible players with an elytra are found and shoots 'em down if required ) xD", GLFW.GLFW_KEY_E);
    public static final AutoSell auctionSeller = new AutoSell("AutoSell", "Sells items to auction", GLFW.GLFW_KEY_A);
    public static final ChestStealer chestStealer = new ChestStealer("AutoSell", "Sells items to auction", GLFW.GLFW_KEY_C);

    static {
        modules.add(truesight);
        modules.add(chorusFarmer);
        modules.add(elytraHunter);
        modules.add(auctionSeller);
        modules.add(chestStealer);
        for (Module mod : modules) {
            EventManager.addListeners(mod);
            EventManager.addModuleListeners(mod, mod);
        }
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
        MiniutilsGui.instance.validate();
    }
}
