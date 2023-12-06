package me.margiux.miniutils.event;

import me.margiux.miniutils.CheatMode;
import me.margiux.miniutils.Main;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public record EventExecutor(Method method, Listener listener, EventHandler handler) implements Executor {
    @Override
    public <T extends Event> void execute(T event) {
        try {
            if ((!event.isCanceled() || (event.isCanceled() && !handler.ignoreCanceled())) &&
                    (Main.instance.status.getData() != CheatMode.PANIC || handler.executeInPanicMode()))
                method.invoke(listener, event);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}
