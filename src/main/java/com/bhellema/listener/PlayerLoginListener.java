package com.bhellema.listener;

import com.bhellema.PlaySchedule;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

public class PlayerLoginListener implements Listener {

    private PlaySchedule plugin;

    public PlayerLoginListener(PlaySchedule plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerLogin(PlayerLoginEvent event) {
        plugin.getLogger().info("normal priority player has logged in! " + event.getPlayer().getName());
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void highLogin(PlayerLoginEvent event) {
        plugin.getLogger().info("high priority player has logged in! " + event.getPlayer().getName());

    }

}
