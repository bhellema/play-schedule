package com.bhellema.event;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class TimeExpiredEvent extends Event implements Cancellable {

    private static final HandlerList handlers = new HandlerList();
    private String message;
    private boolean isCancelled = false;

    public TimeExpiredEvent(String example) {
        message = example;
    }

    public String getMessage() {
        return message;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    @Override
    public boolean isCancelled() {
        return this.isCancelled;
    }

    @Override
    public void setCancelled(boolean isCancelled) {
        this.isCancelled = isCancelled;
    }
}
