package me.margiux.miniutils.event;

public class Event {
    protected boolean canceled = false;

    public void setCanceled() {
        setCanceled(true);
    }

    public void setCanceled(boolean canceled) {
        if (this instanceof Cancelable) this.canceled = canceled;
    }

    public boolean isCanceled() {
        return canceled;
    }
}
