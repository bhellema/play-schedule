package com.bhellema.schedule;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Scheduler {

    private Map<String, PlayerSchedule> playerSchedules = null;
    private final JavaPlugin plugin;

    public Scheduler(JavaPlugin plugin) {
        this.plugin = plugin;
        playerSchedules = new HashMap<String, PlayerSchedule>();
    }

    /**
     * Build the player's schedules by parsing the plugin config
     * file.
     */
    public void build() {
        FileConfiguration config = plugin.getConfig();
        ScheduleConfigReader reader = new ScheduleConfigReader(config);
        List<PlayerSchedule> schedules = reader.getSchedules();

        for (PlayerSchedule schedule : schedules) {
            String name = schedule.getPlayerName();
            playerSchedules.put(name, schedule);
        }
    }

    /**
     * Return the player's schedule if one exists.  If no schedule
     * exists for the specified player then null is returned.
     * @param player the player to retrieve the schedule for.
     * @return the player's schedule.
     * @throws ScheduleException if the schedule has not been initialized.
     */
    public PlayerSchedule getPlayerSchedule(Player player) throws ScheduleException {
        if (playerSchedules == null) {
            throw new ScheduleException("The schedules have not been built yet");
        }

        if (playerSchedules.containsKey(player.getName())) {
            PlayerSchedule playerSchedule = playerSchedules.get(player.getName());
            playerSchedule.setPlayer(player);
            return playerSchedule;
        }
        return null;
    }


    /**
     * ScheduleException can occur if there's any issues with the
     * schedule.
     */
    public class ScheduleException extends RuntimeException {
        public ScheduleException(String message) {
            super(message);
        }
    }
}
