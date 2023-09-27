package me.margiux.miniutils.task;

import java.util.function.Consumer;

public class RepeatTask extends Task {
    private final int delay;
    private int ticks;

    public RepeatTask(Consumer<Task> task, int delay) {
        super(task);
        this.delay = delay;
    }

    @Override
    public void tick() {
        if (predicate != null && !predicate.test(true)) return;
        if (taskCompleted) return;
        if (delay > ticks) {
            ++ticks;
        } else {
            task.accept(this);
            ticks = 0;
        }
    }
}
