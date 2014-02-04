package com.bhellema;

import com.bhellema.command.ScheduleCommandExecutor;
import com.bhellema.scoreboard.PlayerBoard;
import com.bhellema.listener.PlayerLoginListener;
import com.bhellema.listener.TimeEventListener;
import com.bhellema.schedule.Scheduler;
import org.bukkit.plugin.java.JavaPlugin;

public class PlaySchedule extends JavaPlugin {

    private Scheduler scheduler;
    private PlayerBoard playerBoard;

    public void onDisable() {
        getLogger().info("onDisable has been invoked!");
	}

	public void onEnable() {
        getLogger().info("[" + getName() + "] has been enabled");

        registerCommands();
        registerListeners();
        buildSchedule();

        playerBoard = new PlayerBoard();
	}

    public PlayerBoard getPlayerBoard() {
        return playerBoard;
    }

    /**
     * Parse the schedule config and build a player
     * schedule.
     */
    private void buildSchedule() {
        scheduler = new Scheduler(this);
        scheduler.build();
    }

    private void registerCommands() {
        getCommand("schedule").setExecutor(new ScheduleCommandExecutor(this));
    }

    private void registerListeners() {
        new PlayerLoginListener(this);
        new TimeEventListener(this);
    }

    /**
     * Return a Scheduler that contains the schedules for
     * different players.
     *
     * @return the scheduler.
     */
    public Scheduler getScheduler() {
        return this.scheduler;
    }
}
