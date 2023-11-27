package me.margiux.miniutils.task;

import me.margiux.miniutils.event.EventHandler;
import me.margiux.miniutils.event.Listener;
import me.margiux.miniutils.event.TickEvent;

import java.util.ArrayList;
import java.util.List;

public class TaskManager implements Listener {
    protected static final List<Task> taskList = new ArrayList<>();

    @EventHandler
    public static void tick(TickEvent event) {
        for (int i = 0; i < taskList.size(); i++) {
            try {
                taskList.get(i).tick();
                if (taskList.get(i).taskCompleted) {
                    taskList.remove(i);
                    --i;
                }
            } catch (Exception ignored) {
            }
        }
    }

    public static Task addTask(Task task) {
        taskList.add(task);
        return task;
    }

    public static void removeTask(Task task) {
        taskList.remove(task);
    }
}
