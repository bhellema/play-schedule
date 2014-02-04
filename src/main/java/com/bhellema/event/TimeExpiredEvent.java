package com.bhellema.event;

import com.bhellema.schedule.PlayerSchedule;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class TimeExpiredEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    private PlayerSchedule playerSchedule;

    public TimeExpiredEvent(PlayerSchedule playerSchedule) {
        this.playerSchedule = playerSchedule;
    }

    public String getMessage() {
        return  ChatColor.YELLOW + "\nSorry time is up\n" +
                ChatColor.BLUE + "-------------------------------------\n" +
                ChatColor.GREEN + "Next Play Time\n" +
                ChatColor.GREEN + playerSchedule.getNextPlayTime().getTime().toString() +"\n" +
                ChatColor.BLUE + "-------------------------------------";
    }

    public Player getPlayer() {
        return Bukkit.getPlayer(playerSchedule.getPlayerName());
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }


}
