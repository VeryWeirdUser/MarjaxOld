package me.margiux.miniutils.event;

public interface Executor {
    <T extends Event> void execute(T event);
}
