package me.margiux.miniutils.task;

import java.util.function.Consumer;

public class DelayTask extends Task {
    private final int delay;

    public DelayTask(Consumer<Task> task, int delay) {
        super(task);
        this.delay = delay;
    }

    @Override
    public void tick() {
        super.tick();
        if (++ticks >= delay) {
            task.accept(this);
            setTaskCompleted();
        }
    }
}
