package me.margiux.miniutils.event;

import me.margiux.miniutils.Main;

public class Event {
    protected boolean canceled = false;

    public void setCanceled() {
        setCanceled(true);
    }

    public void setCanceled(boolean canceled) {
        if (this instanceof Cancelable) this.canceled = canceled;
        else Main.instance.LOGGER.error("Tried to cancel not cancelable event!");
    }

    public boolean isCanceled() {
        return canceled;
    }
}
