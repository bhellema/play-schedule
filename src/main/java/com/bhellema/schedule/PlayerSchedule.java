package com.bhellema.schedule;

import net.minecraft.util.org.apache.commons.lang3.time.DateUtils;
import org.bukkit.entity.Player;

import java.util.Calendar;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

public class PlayerSchedule {

    private final String playerName;
    private Set<ScheduleEntry> scheduleEntries;

    public PlayerSchedule(String playerName) {
        this.playerName = playerName;
        scheduleEntries = new TreeSet<ScheduleEntry>();
    }

    public void addEntry(ScheduleEntry scheduleEntry) {
        scheduleEntries.add(scheduleEntry);
    }

    public String getPlayerName() {
        return playerName;
    }

    public Set<ScheduleEntry> getScheduleEntries() {
        return scheduleEntries;
    }

    public Iterator<ScheduleEntry> iterator() {
        return scheduleEntries.iterator();
    }

    public boolean canPlayToday() {
        Calendar now = Calendar.getInstance();
        Iterator<ScheduleEntry> i = iterator();
        int cnt = 0;
        while (i.hasNext()) {
            ScheduleEntry scheduleEntry = i.next();
            Calendar start = scheduleEntry.getStartTime();
            Calendar end = scheduleEntry.getEndTime();
            //System.out.println("Can Play Entry: " + cnt++ + " " + start.getTime().toString() + " " + end.getTime().toString());

            if (DateUtils.isSameDay(now, start)) {
                if (start.getTimeInMillis() < now.getTimeInMillis() && now.getTimeInMillis() < end.getTimeInMillis()) {
                    return true;
                }
            }
        }
        return false;
    }


    public Calendar getNextPlayTime() {
        Calendar now = Calendar.getInstance();
        Iterator<ScheduleEntry> i = iterator();
        int cnt = 0;
        while (i.hasNext()) {
            ScheduleEntry scheduleEntry = i.next();
            Calendar start = scheduleEntry.getStartTime();
            Calendar end = scheduleEntry.getEndTime();
            //System.out.println("Entry: " + cnt++ + " " + start.getTime().toString() + " " + end.getTime().toString());

            if (DateUtils.truncatedCompareTo(now, start, Calendar.MILLISECOND) < 1) {
                return start;
            }
        }
        // really?
        return now;
    }
}
