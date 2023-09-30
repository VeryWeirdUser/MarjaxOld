package me.margiux.miniutils.task;

import java.util.function.Consumer;

public class DelayedRepeatTask extends Task {
    private final int delay;
    private final int delayUntilStart;
    private int ticks;

    public DelayedRepeatTask(Consumer<Task> task, int delay, int delayUntilStart) {
        super(task);
        this.delay = delay;
        this.delayUntilStart = delayUntilStart;
    }

    @Override
    public void tick() {
        if (predicate != null && !predicate.test(true)) return;
        if (taskCompleted) return;
        if (delayUntilStart > ticks) {
            ++ticks;
            return;
        }

        if (delay > ticks) {
            ++ticks;
        } else {
            task.accept(this);
            ticks = 0;
        }
    }
}
