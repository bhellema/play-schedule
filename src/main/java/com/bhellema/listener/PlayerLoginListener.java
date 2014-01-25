package com.bhellema.listener;

import com.bhellema.PlaySchedule;
import com.bhellema.event.TimeExpiredEvent;
import com.bhellema.schedule.Time;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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
        final Scoreboard board = event.getPlayer().getScoreboard();
        Objective objective = board.getObjective("Time");
        if (objective == null) {
            objective = board.registerNewObjective("Time", "Time Remaining");
        }
        objective.setDisplayName(ChatColor.GRAY + "Time Remaining");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        final Score score = objective.getScore(Bukkit.getOfflinePlayer(event.getPlayer().getName()));
        score.setScore(10);

        int taskId = plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new BukkitRunnable() {
            @Override
            public void run() {
                int currentScore = score.getScore();
                if (currentScore > 0) {
                    currentScore--;
                    score.setScore(currentScore);
                } else {
                    TimeExpiredEvent timeExpiredEvent = new TimeExpiredEvent(event.getPlayer(), "Time has expired");
                    Bukkit.getServer().getPluginManager().callEvent(timeExpiredEvent);

                    int taskId = tasks.get(event.getPlayer().getName());

                    plugin.getServer().getScheduler().cancelTask(taskId);
                    //Bukkit.getServer().broadcastMessage(event.getMessage());
                }

            }
        }, 0, Time.ONE_SECOND);

        tasks.put(event.getPlayer().getName(), taskId);
    }

    @EventHandler
    public void onPlayerLogout(PlayerQuitEvent event) {
        int taskId = tasks.get(event.getPlayer().getName());
        System.out.println("Shutting down player listener... " + taskId + " for player " + event.getPlayer().getName());
        plugin.getServer().getScheduler().cancelTask(taskId);
    }
}
