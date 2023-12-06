package me.margiux.miniutils.task;

import java.util.function.Consumer;

public class RepeatTask extends Task {
    private final int delay;

    public RepeatTask(Consumer<Task> task, int delay) {
        super(task);
        this.delay = delay;
    }

    @Override
    public void tick() {
        super.tick();
        if (++ticks >= delay) {
            task.accept(this);
            ticks = 0;
        }
    }
}
