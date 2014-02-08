package com.bhellema.listener;

import com.bhellema.PlaySchedule;
import com.bhellema.schedule.PlayerSchedule;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scoreboard.Objective;

import java.text.SimpleDateFormat;

public class PlayerLoginListener implements Listener {

    private PlaySchedule plugin;

    public PlayerLoginListener(PlaySchedule plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerPreLogin(final AsyncPlayerPreLoginEvent event) {
        String name = event.getName();
        PlayerSchedule playerSchedule = plugin.getScheduler().getPlayerSchedule(name);
        if (playerSchedule != null) {
            if (!playerSchedule.canPlayNow()) {
                SimpleDateFormat df = new SimpleDateFormat("MMM, EEEE, d h:mm a");
                String result = df.format(playerSchedule.getNextPlayTime().getTime());
                String msg = ChatColor.YELLOW + "\nYou're Not Scheduled To Play\n" +
                        ChatColor.BLUE + "-------------------------------------\n" +
                        ChatColor.GREEN + "Next Play Time\n" +
                        ChatColor.GREEN + result +"\n" +
                        ChatColor.BLUE + "-------------------------------------";
                event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, msg);
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerJoinLogin(final PlayerJoinEvent event) {
        String name = event.getPlayer().getDisplayName();
        PlayerSchedule playerSchedule = plugin.getScheduler().getPlayerSchedule(name);
        if (playerSchedule != null) {
            plugin.getScheduler().schedulePlayer(name);
            //this.plugin.getPlayerBoard().addPlayerToBoard(playerSchedule);
        } else {
            for (Objective objective : event.getPlayer().getScoreboard().getObjectives()) {
                objective.unregister();
            }
        }
    }

    @EventHandler
    public void onPlayerLogout(PlayerQuitEvent event) {
        //this.plugin.getPlayerBoard().removePlayerFromBoard(event.getPlayer().getDisplayName());
        plugin.getScheduler().unschedulePlayer(event.getPlayer().getDisplayName());
    }
}
