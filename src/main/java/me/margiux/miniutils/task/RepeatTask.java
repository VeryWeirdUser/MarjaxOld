package me.margiux.miniutils.task;

public class RepeatTask extends Task {
    private final int delay;
    private int ticks;

    public RepeatTask(Runnable task, int delay) {
        super(task);
        this.delay = delay;
    }

    @Override
    public void tick() {
        if (taskCompleted) return;
        if (delay > ticks) {
            ++ticks;
        } else {
            task.run();
            ticks = delay;
        }
    }
}
