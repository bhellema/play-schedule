package com.bhellema.scoreboard;

import com.bhellema.schedule.PlayerSchedule;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

public class PlayerBoard {

    public void addPlayerToBoard(PlayerSchedule playerSchedule) {
        Player player = Bukkit.getPlayer(playerSchedule.getPlayerName());
        final Scoreboard scoreboard = player.getScoreboard();
        Objective timeObjective = scoreboard.getObjective("Play Time");
        if (timeObjective == null) {
            timeObjective = scoreboard.registerNewObjective("Play Time", "Time Remaining");
        }
        timeObjective.setDisplayName(ChatColor.GRAY + "Time Remaining");
        timeObjective.setDisplaySlot(DisplaySlot.SIDEBAR);
        final Score score = timeObjective.getScore(Bukkit.getOfflinePlayer(player.getName()));
        score.setScore(9999);
    }
}

/*

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
 */
