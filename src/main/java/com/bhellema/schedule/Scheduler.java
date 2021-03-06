package com.bhellema.schedule;

import net.minecraft.util.org.apache.commons.lang3.time.DateUtils;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Scheduler {

    private Map<String, PlayerSchedule> playerSchedules;
    private Map<String, ScheduleTimer> scheduleTimers;
    private final JavaPlugin plugin;

    /**
     * Constructor.
     * @param plugin
     */
    public Scheduler(JavaPlugin plugin) {
        this.plugin = plugin;
        playerSchedules = new HashMap<String, PlayerSchedule>();
        scheduleTimers = new HashMap<String, ScheduleTimer>();
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
     * @param player the name of the player to retrieve the schedule for.
     * @return the player's schedule.
     * @throws ScheduleException if the schedule has not been initialized.
     */
    public PlayerSchedule getPlayerSchedule(String player) throws ScheduleException {
        if (playerSchedules == null) {
            throw new ScheduleException("The schedules have not been built yet");
        }

        if (playerSchedules.containsKey(player)) {
            PlayerSchedule playerSchedule = playerSchedules.get(player);
            return playerSchedule;
        }
        return null;
    }

    /**
     * Schedule a player to play right now.  Allow the player's
     * Schedule determine when they have to stop.
     * @param playerName the name of the player to start scheduling
     *                   their play time.
     */
    public void schedulePlayer(String playerName) {
        PlayerSchedule playerSchedule = getPlayerSchedule(playerName);
        ScheduleTimer timer = new ScheduleTimer(playerSchedule);
        scheduleTimers.put(playerName, timer);
    }

    /**
     * Remove the specified player from the scheduler.  Unscheduling
     * a player performs necessary cleanup for their schedule.
     * @param playerName the name of the player to unschedule.
     */
    public void unschedulePlayer(String playerName) {
        ScheduleTimer timer = scheduleTimers.remove(playerName);
        if (timer != null) {
            timer.cancel();
        }
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
