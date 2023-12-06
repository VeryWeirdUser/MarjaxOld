package me.margiux.miniutils.task;

import java.util.function.Consumer;

public class DelayedRepeatTask extends Task {
    private final int delay;
    private int delayUntilStart;
    private int maxRepeatCount = -1;
    private int repeatCount = 0;

    public DelayedRepeatTask(Consumer<Task> task, int delay, int delayUntilStart) {
        super(task);
        this.delay = delay;
        this.delayUntilStart = delayUntilStart;
    }

    public DelayedRepeatTask(Consumer<Task> task, int delay, int delayUntilStart, int maxRepeatCount) {
        super(task);
        this.delay = delay;
        this.delayUntilStart = delayUntilStart;
        this.maxRepeatCount = maxRepeatCount;
    }

    @Override
    public void tick() {
        super.tick();
        if (++ticks < delayUntilStart) return;
        else {
            ticks = 0;
            delayUntilStart = 0;
        }

        if (++ticks >= delay) {
            if (maxRepeatCount != -1) {
                if (++repeatCount < maxRepeatCount) {
                    setTaskCompleted();
                    return;
                }
            }
            task.accept(this);
            ticks = 0;
        }
    }
}
