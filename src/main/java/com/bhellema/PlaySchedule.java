package com.bhellema;

import com.bhellema.command.ScheduleCommandExecutor;
import com.bhellema.listener.PlayerLoginListener;
import com.bhellema.listener.TimeEventListener;
import com.bhellema.schedule.Scheduler;
import org.bukkit.plugin.java.JavaPlugin;

public class PlaySchedule extends JavaPlugin {

	public void onDisable() {
        getLogger().info("onDisable has been invoked!");
	}

	public void onEnable() {
        getLogger().info("[" + getName() + "] has been enabled");

        registerCommands();
        registerListeners();
        buildSchedule();
	}

    private void buildSchedule() {
        Scheduler scheduler = new Scheduler(this);
        scheduler.build();
    }

    private void registerCommands() {
        getCommand("schedule").setExecutor(new ScheduleCommandExecutor(this));
    }

    private void registerListeners() {
        new PlayerLoginListener(this);
        new TimeEventListener(this);
    }

}
