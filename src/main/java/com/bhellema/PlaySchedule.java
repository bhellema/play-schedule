package com.bhellema;

/*
    This file is part of com.bhellema.play-schedule

    Foobar is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    Foobar is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with Foobar.  If not, see <http://www.gnu.org/licenses/>.
 */

import com.bhellema.event.TimeExpiredEvent;
import com.bhellema.listener.PlayerLoginListener;
import com.bhellema.listener.TimeEventListener;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;
import java.util.Set;

public class PlaySchedule extends JavaPlugin {


	private final PlayScheduleCommandExecutor commandExecutor = new PlayScheduleCommandExecutor(this);
	private final Listener eventListener = new PlayScheduleEventListener(this);

	public void onDisable() {
        getLogger().info("onDisable has been invoked!");
	}

	public void onEnable() {
        new PlayerLoginListener(this);
        new TimeEventListener(this);

        getLogger().info("onEnable has been invoked!");

		PluginManager pm = this.getServer().getPluginManager();

		getCommand("command").setExecutor(commandExecutor);

		// you can register multiple classes to handle events if you want
		// just call pm.registerEvents() on an instance of each class
		pm.registerEvents(eventListener, this);

        callEvent();
	}

    private void callEvent() {

        TimeExpiredEvent event = new TimeExpiredEvent("Time has expired");
        Bukkit.getServer().getPluginManager().callEvent(event);
        Bukkit.getServer().broadcastMessage(event.getMessage());

    }
}
