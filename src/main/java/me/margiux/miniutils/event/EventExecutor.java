package me.margiux.miniutils.event;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class EventExecutor implements Executor {
    public Method method;
    public Listener listener;
    public EventHandler handler;

    public EventExecutor(Method method, Listener listener, EventHandler handler) {
        this.method = method;
        this.listener = listener;
        this.handler = handler;
    }

    @Override
    public <T extends Event> void execute(T event) {
        try {
            if (!event.isCanceled() || (event.isCanceled() && !handler.ignoreCanceled()))
                method.invoke(listener, event);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}
