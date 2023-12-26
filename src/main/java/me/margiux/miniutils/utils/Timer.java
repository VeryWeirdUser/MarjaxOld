package me.margiux.miniutils.utils;

/*
Not in use
 */
public class Timer {
    public long time;

    public Timer() {
        time = System.currentTimeMillis();
    }

    public boolean every(double s) {
        if ((System.currentTimeMillis() + (s * 1000) - time) > s * 1000) {
            reset();
            return true;
        }
        return false;
    }

    public boolean every(int ms) {
        if (System.currentTimeMillis() - time > ms) {
            reset();
            return true;
        }
        return false;
    }

    public void reset() {
        time = System.currentTimeMillis();
    }
}
