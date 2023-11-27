package me.margiux.miniutils.event;

import me.margiux.miniutils.module.Module;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;

public class EventManager {
    private static final HashMap<EventPriority, HashMap<Class<? extends Event>, ArrayList<Executor>>> eventExecutorMap = new HashMap<>();

    public static <T extends Class<? extends Event>> void addExecutor(T event, Executor eventExecutor, EventPriority priority) {
        eventExecutorMap.computeIfAbsent(priority, k -> new HashMap<>());
        eventExecutorMap.get(priority).computeIfAbsent(event, k -> new ArrayList<>()).add(eventExecutor);
    }

    public static <T extends Class<? extends Event>> void addExecutor(T event, Method staticMethod, EventHandler handler, EventPriority priority) {
        if (Modifier.isStatic(staticMethod.getModifiers()))
            addExecutor(event, new EventExecutor(staticMethod, null, handler), priority);
    }

    @SuppressWarnings("unchecked")
    public static void addListener(@NotNull Listener listener) {
        Method[] methods = listener.getClass().getMethods();
        for (final Method method : methods) {
            method.setAccessible(true);
            if (method.getAnnotation(EventHandler.class) != null && !method.isBridge() && !method.isSynthetic() && method.getParameterCount() == 1) {
                if (Event.class.isAssignableFrom(method.getParameterTypes()[0])) {
                    Executor executor = new EventExecutor(method, listener, method.getAnnotation(EventHandler.class));
                    addExecutor((Class<? extends Event>) method.getParameterTypes()[0], executor, method.getAnnotation(EventHandler.class).priority());
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    public static void addStaticListener(@NotNull Class<? extends Listener> listener) {
        Method[] methods = listener.getMethods();

        for (final Method method : methods) {
            method.setAccessible(true);
            if (method.getAnnotation(EventHandler.class) != null && !method.isBridge() && !method.isSynthetic() && method.getParameterCount() == 1 && Modifier.isStatic(method.getModifiers())) {
                if (Event.class.isAssignableFrom(method.getParameterTypes()[0])) {
                    addExecutor((Class<? extends Event>) method.getParameterTypes()[0], method, method.getAnnotation(EventHandler.class), method.getAnnotation(EventHandler.class).priority());
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    public static void addModuleListener(@NotNull Listener listener, @NotNull Module module) {
        Method[] methods = listener.getClass().getMethods();

        for (final Method method : methods) {
            method.setAccessible(true);
            if (method.getAnnotation(ModuleEventHandler.class) != null && !method.isBridge() && !method.isSynthetic() && method.getParameterCount() == 1) {
                if (Event.class.isAssignableFrom(method.getParameterTypes()[0])) {
                    Executor executor = new ModuleEventExecutor(module, method, listener, method.getAnnotation(ModuleEventHandler.class));
                    addExecutor((Class<? extends Event>) method.getParameterTypes()[0], executor, method.getAnnotation(ModuleEventHandler.class).priority());
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
