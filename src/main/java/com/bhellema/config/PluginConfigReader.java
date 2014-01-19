package com.bhellema.config;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.Set;

public class PluginConfigReader {

    private JavaPlugin plugin;

    public PluginConfigReader(JavaPlugin plugin) {
        this.plugin = plugin;
        readConfig();
    }

    private void readConfig() {
        FileConfiguration config = plugin.getConfig();

        List<String> monday = config.getStringList("schedule.monday");

        for(String time : monday) {
            plugin.getLogger().info(time);
        }
    }
}
