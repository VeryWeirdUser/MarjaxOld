package me.margiux.miniutils.task;

import java.util.ArrayList;
import java.util.List;

public class TaskManager {
    protected static final List<Task> taskList = new ArrayList<>();

    public static void tick() {
        for (int i = 0; i < taskList.size(); i++) {
            taskList.get(i).tick();
            if (taskList.get(i).taskCompleted) {
                taskList.remove(i);
                --i;
            }
        }
    }

    public static void addTask(Task task) {
        taskList.add(task);
    }
}
