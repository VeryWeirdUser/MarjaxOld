package me.margiux.miniutils.task;

import java.util.function.Consumer;
import java.util.function.Predicate;

public abstract class Task {
    protected final Consumer<Task> task;
    protected boolean taskCompleted = false;
    protected Predicate<Boolean> predicate;
    protected Task onCompleteTask = null;

    public Task(Consumer<Task> task) {
        this.task = task;
    }

    public abstract void tick();
    public abstract void onTaskEnded();

    public void setTaskCompleted() {
        setTaskCompleted(true);
    }

    public void setTaskCompleted(boolean value) {
        taskCompleted = value;
        if (onCompleteTask != null) TaskManager.addTask(onCompleteTask);
    }

    public boolean isTaskCompleted() {
        return taskCompleted;
    }

    public Task setPredicate(Predicate<Boolean> predicate) {
        this.predicate = predicate;
        return this;
    }

    public Task setOnCompleteTask(Task onCompleteTask) {
        this.onCompleteTask = onCompleteTask;
        return onCompleteTask;
    }

    public void endTask() {
        this.taskCompleted = true;
        onTaskEnded();
    }
}
