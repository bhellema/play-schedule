package com.bhellema.schedule.task;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class PlayerTask extends BukkitRunnable {

    private JavaPlugin plugin;
    long start = 0;
    public PlayerTask(JavaPlugin plugin) {
        this.plugin = plugin;
        start = System.currentTimeMillis();
    }

    @Override
    public void run() {
        long end = System.currentTimeMillis() - start;
        plugin.getServer().broadcastMessage("finished running..." + end);
    }

}
