package com.bhellema.listener;

import com.bhellema.PlaySchedule;
import com.bhellema.event.TimeExpiredEvent;
import org.bukkit.Bukkit;
import org.bukkit.command.defaults.SaveCommand;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldSaveEvent;

public class TimeEventListener implements Listener {

    private PlaySchedule plugin;

    public TimeEventListener(PlaySchedule plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onTimeExpired(TimeExpiredEvent event) {
        event.getPlayer().kickPlayer(event.getMessage());
    }

}
