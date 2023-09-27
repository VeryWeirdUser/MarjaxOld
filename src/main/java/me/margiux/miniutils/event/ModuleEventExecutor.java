package me.margiux.miniutils.event;

import me.margiux.miniutils.module.Module;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Method;

public class ModuleEventExecutor extends EventExecutor {
    public Module module;

    public ModuleEventExecutor(@NotNull Module module, Method method, Listener listener, EventHandler handler) {
        super(method, listener, handler);
        this.module = module;
    }

    @Override
    public <T extends Event> void execute(T event) {
        if (module.isEnabled()) {
            super.execute(event);
        }
    }
}
