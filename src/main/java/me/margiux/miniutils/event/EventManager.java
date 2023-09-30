package me.margiux.miniutils.event;

import me.margiux.miniutils.module.Module;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

public class EventManager {
    private static final HashMap<EventPriority, HashMap<Class<? extends Event>, ArrayList<Executor>>> eventExecutorMap = new HashMap<>();

    public static <T extends Class<? extends Event>> void addListener(T event, Executor eventExecutor, EventPriority priority) {
        eventExecutorMap.computeIfAbsent(priority, k -> new HashMap<>());
        eventExecutorMap.get(priority).computeIfAbsent(event, k -> new ArrayList<>()).add(eventExecutor);
    }

    @SuppressWarnings("unchecked")
    public static void addListeners(@NotNull Listener listener) {
        Method[] methods = listener.getClass().getMethods();

        for (final Method method : methods) {
            method.setAccessible(true);
            if (method.getAnnotation(EventHandler.class) != null && !method.isBridge() && !method.isSynthetic() && method.getParameterCount() == 1) {
                if (Event.class.isAssignableFrom(method.getParameterTypes()[0])) {
                    Executor executor;
                    executor = new EventExecutor(method, listener, method.getAnnotation(EventHandler.class));
                    addListener((Class<? extends Event>) method.getParameterTypes()[0], executor, method.getAnnotation(EventHandler.class).priority());
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    public static void addModuleListeners(@NotNull Listener listener, @NotNull Module module) {
        Method[] methods = listener.getClass().getMethods();

        for (final Method method : methods) {
            method.setAccessible(true);
            if (method.getAnnotation(ModuleEventHandler.class) != null && !method.isBridge() && !method.isSynthetic() && method.getParameterCount() == 1) {
                if (Event.class.isAssignableFrom(method.getParameterTypes()[0])) {
                    Executor executor;
                    executor = new ModuleEventExecutor(module, method, listener, method.getAnnotation(ModuleEventHandler.class));
                    addListener((Class<? extends Event>) method.getParameterTypes()[0], executor, method.getAnnotation(ModuleEventHandler.class).priority());
                }
            }
        }
    }

    public static <T extends Event> void fireEvent(T event) {
        for (int i = EventPriority.values().length - 1; i >= 0; i--) {
            eventExecutorMap.computeIfAbsent(EventPriority.values()[i], (k) -> new HashMap<>()).computeIfAbsent(event.getClass(), (k) -> new ArrayList<>()).forEach(listener -> listener.execute(event));
        }
    }
}
