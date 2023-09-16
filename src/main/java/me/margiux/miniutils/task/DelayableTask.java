package me.margiux.miniutils.task;

public class DelayableTask extends Task {
    private int delay;

    public DelayableTask(Runnable task, int delay) {
        super(task);
        this.delay = delay;
    }

    @Override
    public void tick() {
        if (taskCompleted) return;
        if (delay > 0) {
            --delay;
        } else {
            task.run();
            taskCompleted = true;
        }
    }
}
