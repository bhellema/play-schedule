package com.bhellema.schedule;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class Scheduler {


    private final JavaPlugin plugin;

    public Scheduler(JavaPlugin plugin) {
        this.plugin = plugin;
    }


    public void build() {
        FileConfiguration config = plugin.getConfig();
        ScheduleConfigReader reader = new ScheduleConfigReader(config);
        List<PlayerSchedule> playerScheduleList = reader.getPlayerSchedules();

    }
}
