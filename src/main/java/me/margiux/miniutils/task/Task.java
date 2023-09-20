package me.margiux.miniutils.task;

public class Task {
    protected final Runnable task;
    protected boolean taskCompleted = false;

    public Task(Runnable task) {
        this.task = task;
    }

    public void tick() {
    }

    public void setTaskCompleted() {
        setTaskCompleted(true);
    }

    public void setTaskCompleted(boolean value) {
        taskCompleted = value;
    }

    public boolean isTaskCompleted() {
        return taskCompleted;
    }
}
