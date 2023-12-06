package me.margiux.miniutils.task;

import java.util.function.Consumer;
import java.util.function.Predicate;

public abstract class Task {
    protected final Consumer<Task> task;
    protected boolean taskCompleted = false;
    protected boolean taskSuspended = false;
    protected Predicate<Boolean> predicate;
    protected Task onCompleteTask = null;
    protected int ticks;

    public Task(Consumer<Task> task) {
        this.task = task;
    }

    @SuppressWarnings("UnnecessaryReturnStatement")
    public void tick() {
        if (taskSuspended || taskCompleted) return;
        if (predicate != null && !predicate.test(true)) return;
    }
    public void onTaskEnded() {
        ticks = 0;
    }

    public void setTaskCompleted() {
        setTaskCompleted(true);
    }

    public void setTaskCompleted(boolean value) {
        taskCompleted = value;
        if (onCompleteTask != null && value) TaskManager.addTask(onCompleteTask);
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
        if (onCompleteTask != null) onCompleteTask.endTask();
        onTaskEnded();
    }
    @SuppressWarnings("unused")
    public void suspendTask() {
        taskSuspended = true;
        if (onCompleteTask != null) onCompleteTask.suspendTask();
    }

    @SuppressWarnings("unused")
    public void resumeTask() {
        taskSuspended = false;
        if (onCompleteTask != null) onCompleteTask.resumeTask();
    }

    public void restartTask() {
        setTaskCompleted(false);
    }

    public boolean isTaskSuspended() {
        return taskSuspended;
    }
}
