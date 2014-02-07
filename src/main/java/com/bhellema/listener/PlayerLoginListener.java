package com.bhellema.listener;

import com.bhellema.PlaySchedule;
import com.bhellema.schedule.PlayerSchedule;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;

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
            if (!playerSchedule.canPlayToday()) {
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
    public void onPlayerLogin(final PlayerJoinEvent event) {
        String name = event.getPlayer().getDisplayName();
        PlayerSchedule playerSchedule = plugin.getScheduler().getPlayerSchedule(name);
        this.plugin.getPlayerBoard().addPlayerToBoard(playerSchedule);
    }

    /*@EventHandler
    public void onPlayerLogout(PlayerQuitEvent event) {
        // nothing to do here yet...
    }*/
}
