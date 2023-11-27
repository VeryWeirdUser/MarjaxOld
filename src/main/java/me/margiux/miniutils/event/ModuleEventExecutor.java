package me.margiux.miniutils.event;

import me.margiux.miniutils.CheatMode;
import me.margiux.miniutils.Main;
import me.margiux.miniutils.module.Module;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public record ModuleEventExecutor(Module module, Method method, Listener listener,
                                  ModuleEventHandler handler) implements Executor {
    public ModuleEventExecutor(@NotNull Module module, Method method, Listener listener, ModuleEventHandler handler) {
        this.method = method;
        this.listener = listener;
        this.handler = handler;
        this.module = module;
    }

    @Override
    public <T extends Event> void execute(T event) {
        if (module.isEnabled()) {
            try {
                if ((!event.isCanceled() || (event.isCanceled() && !handler.ignoreCanceled())) &&
                        (Main.instance.STATUS.getValue() != CheatMode.PANIC || handler.executeInPanicMode()))
                    method.invoke(listener, event);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
