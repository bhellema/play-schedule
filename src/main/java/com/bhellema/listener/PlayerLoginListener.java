package com.bhellema.listener;

import com.bhellema.PlaySchedule;
import com.bhellema.event.TimeExpiredEvent;
import com.bhellema.schedule.PlayerSchedule;
import com.bhellema.util.Time;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

import java.util.HashMap;
import java.util.Map;

public class PlayerLoginListener implements Listener {

    private static final int TICK = 20;
    private PlaySchedule plugin;

    Map<String, Integer> tasks = new HashMap<String, Integer>();

    public PlayerLoginListener(PlaySchedule plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerLogin(final PlayerLoginEvent event) {
        Player player = event.getPlayer();
        PlayerSchedule playerSchedule = plugin.getScheduler().getPlayerSchedule(player);
        if (playerSchedule != null) {
            this.plugin.getPlayerBoard().addPlayerToBoard(playerSchedule);
        } else {
            // no schedule setup for player allow them free play :)
        }

    }

    @EventHandler
    public void onPlayerLogout(PlayerQuitEvent event) {
        int taskId = tasks.get(event.getPlayer().getName());
        System.out.println("Shutting down player listener... " + taskId + " for player " + event.getPlayer().getName());
        plugin.getServer().getScheduler().cancelTask(taskId);
    }
}
