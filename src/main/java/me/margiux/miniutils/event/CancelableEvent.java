package me.margiux.miniutils.event;

public class CancelableEvent extends Event {
    protected boolean canceled;

    public void setCanceled() {
        setCanceled(true);
    }

    public void setCanceled(boolean canceled) {
        this.canceled = canceled;
    }

    public boolean isCanceled() {
        return canceled;
    }
}
