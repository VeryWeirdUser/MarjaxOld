package me.margiux.miniutils.task;

public class Task {
    protected final Runnable task;
    public boolean taskCompleted = false;

    public Task(Runnable task) {
        this.task = task;
    }

    public void tick() {
    }
}
