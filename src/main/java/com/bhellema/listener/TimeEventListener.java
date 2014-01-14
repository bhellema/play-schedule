package com.bhellema.listener;

import com.bhellema.PlaySchedule;
import com.bhellema.event.TimeExpiredEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class TimeEventListener implements Listener {

    private PlaySchedule plugin;

    public TimeEventListener(PlaySchedule plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerLogin(TimeExpiredEvent event) {
        plugin.getLogger().info("Time has expired!");
    }

}
