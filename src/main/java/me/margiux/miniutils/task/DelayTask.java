package me.margiux.miniutils.task;

import java.util.function.Consumer;

public class DelayTask extends Task {
    private int delay;

    public DelayTask(Consumer<Task> task, int delay) {
        super(task);
        this.delay = delay;
    }

    @Override
    public void tick() {
        if (predicate != null && !predicate.test(true)) return;
        if (isTaskCompleted()) return;
        if (delay > 0) {
            --delay;
        } else {
            task.accept(this);
            setTaskCompleted();
        }
    }
}
