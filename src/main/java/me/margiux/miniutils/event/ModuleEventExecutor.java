package me.margiux.miniutils.event;

import me.margiux.miniutils.module.Module;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ModuleEventExecutor implements Executor {
    public Module module;
    public Method method;
    public Listener listener;
    public ModuleEventHandler handler;

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
                if (!event.isCanceled() || (event.isCanceled() && !handler.ignoreCanceled()))
                    method.invoke(listener, event);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
