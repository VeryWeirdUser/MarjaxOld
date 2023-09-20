package me.margiux.miniutils.task;

public class DelayTask extends Task {
    private int delay;

    public DelayTask(Runnable task, int delay) {
        super(task);
        this.delay = delay;
    }

    @Override
    public void tick() {
        if (isTaskCompleted()) return;
        if (delay > 0) {
            --delay;
        } else {
            task.run();
            setTaskCompleted();
        }
    }
}
