package com.bhellema.listener;

import com.bhellema.PlaySchedule;
import com.bhellema.event.TimeExpiredEvent;
import com.bhellema.schedule.PlayerSchedule;
import com.bhellema.util.Time;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerPreLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

import java.util.HashMap;
import java.util.Map;

public class PlayerLoginListener implements Listener {

    private PlaySchedule plugin;

    public PlayerLoginListener(PlaySchedule plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerLogin(final AsyncPlayerPreLoginEvent event) {
        String name = event.getName();
        PlayerSchedule playerSchedule = plugin.getScheduler().getPlayerSchedule(name);
        if (playerSchedule != null) {
            if (playerSchedule.canPlayToday()) {
                this.plugin.getPlayerBoard().addPlayerToBoard(playerSchedule);
            } else {
                String msg = ChatColor.YELLOW + "\nYou're Not Scheduled To Play\n" +
                        ChatColor.BLUE + "-------------------------------------\n" +
                        ChatColor.GREEN + "Next Play Time\n" +
                        ChatColor.GREEN + playerSchedule.getNextPlayTime().getTime().toString() +"\n" +
                        ChatColor.BLUE + "-------------------------------------";
                event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, msg);
            }
        } else {
            // no schedule setup for player allow them free play :)
        }

    }

    /*@EventHandler
    public void onPlayerLogout(PlayerQuitEvent event) {
        // nothing to do here yet...
    }*/
}
