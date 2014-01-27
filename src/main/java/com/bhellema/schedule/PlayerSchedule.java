package com.bhellema.schedule;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class PlayerSchedule {

    private final String playerName;
    private List<ScheduleEntry> schedule;
    private Player player;

    public PlayerSchedule(String playerName) {
        this.playerName = playerName;
        schedule = new ArrayList<ScheduleEntry>();
    }

    public void addEntry(ScheduleEntry scheduleEntry) {
        schedule.add(scheduleEntry);
    }

    public String getPlayerName() {
        return playerName;
    }

    public List<ScheduleEntry> getSchedule() {
        return schedule;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
