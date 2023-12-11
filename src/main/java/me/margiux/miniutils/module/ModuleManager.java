package me.margiux.miniutils.module;

import me.margiux.miniutils.Mode;
import me.margiux.miniutils.event.EventManager;
import me.margiux.miniutils.module.combat.ElytraHunter;
import me.margiux.miniutils.module.combat.TriggerBot;
import me.margiux.miniutils.module.misc.*;
import me.margiux.miniutils.module.player.GuiMove;
import me.margiux.miniutils.module.visual.Truesight;
import me.margiux.miniutils.module.world.ChestStealer;
import me.margiux.miniutils.module.world.ChorusFarmer;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;

public class ModuleManager {
    public static final List<Module> modules = new ArrayList<>();
    public static final Truesight truesight = new Truesight("Truesight", "Makes invisible players visible", Category.VISUAL, GLFW.GLFW_KEY_I);
    public static final ChorusFarmer chorusFarmer = new ChorusFarmer("ChorusFarmer", "Farms choruses automatically", Category.WORLD, GLFW.GLFW_KEY_G);
    public static final ElytraHunter elytraHunter = new ElytraHunter("ElytraHunter", "Informs if invisible players with an elytra are found and shoots 'em down if required ) xD", Category.COMBAT, GLFW.GLFW_KEY_E);
    public static final AutoSell auctionSeller = new AutoSell("AutoSell", "Sells items to auction", Category.MISC, GLFW.GLFW_KEY_A);
    public static final ChestStealer chestStealer = new ChestStealer("ChestSteal", "Steals items from a container", Category.WORLD, GLFW.GLFW_KEY_C);
    public static final TriggerBot triggerBot = new TriggerBot("TriggerBot", "Immediately attacks entities you are looking at", Category.COMBAT, GLFW.GLFW_KEY_T);
    public static final PlayerSearcher playerSearcher = new PlayerSearcher("PlayerSearcher", "Searching for a defined player", Category.MISC, GLFW.GLFW_KEY_T);
    public static final AnticheatTrigger anticheatTrigger = new AnticheatTrigger("AnticheatTrigger", "Anticheat trigger", Category.MISC, GLFW.GLFW_KEY_KP_1);
    public static final GuiMove guiMove = new GuiMove("GUI Move", "Allows to move while have opened a GUI", Category.PLAYER, GLFW.GLFW_KEY_M);

    static {
        addModule(truesight);
        addModule(chorusFarmer);
        addModule(elytraHunter);
        addModule(auctionSeller);
        addModule(chestStealer);
        addModule(triggerBot);
        addModule(playerSearcher);
        addModule(anticheatTrigger);
        addModule(guiMove);
    }

    public static void disable() {
        for (Module module : modules) {
            if (module.mode.getData() == Mode.ENABLED) {
                module.changeMode(Mode.FORCE_DISABLED);
                module.disabledByMain = true;
            } else module.changeMode(Mode.FORCE_DISABLED);
        }
    }

    public static void enableDisabled() {
        for (Module module : modules) {
            if (module.mode.getData() == Mode.FORCE_DISABLED) {
                module.changeMode(module.disabledByMain ? Mode.ENABLED : Mode.DISABLED);
                module.disabledByMain = false;
            }
        }
    }

    public static void addModule(Module module) {
        modules.add(module);
        EventManager.addListener(module);
        EventManager.addModuleListener(module, module);
    }
}
