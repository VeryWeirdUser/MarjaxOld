package me.margiux.miniutils.event;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.function.Consumer;

public class EventManager {
    private static final HashMap<Class<? extends Event>, ArrayList<Consumer<Event>>> listenerMap = new HashMap<>();

    public static <T extends Class<? extends Event>> void addListener(T event, Consumer<Event> listener) {
        ArrayList<Consumer<Event>> listeners = listenerMap.get(event);

        if(listeners == null)
        {
            listeners = new ArrayList<>(Collections.singletonList(listener));
            listenerMap.put(event, listeners);
            return;
        }

        listeners.add(listener);
    }

    public static <T extends Event> void fireEvent(T event) {
        if (listenerMap.get(event.getClass()) != null) listenerMap.get(event.getClass()).forEach(listener -> listener.accept(event));
    }
}
