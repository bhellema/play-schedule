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

    public Iterator<ScheduleEntry> iterator() {
        return scheduleEntries.iterator();
    }

    public boolean canPlayToday() {
        Calendar now = Calendar.getInstance();
        Iterator<ScheduleEntry> i = iterator();
        int cnt = 0;
        while (i.hasNext()) {
            ScheduleEntry scheduleEntry = i.next();
            int dayOfWeek = scheduleEntry.getDayOfWeek();
            Calendar start = scheduleEntry.getStartTime();
            Calendar end = scheduleEntry.getEndTime();
            // if we have the same day of the week then we can check
            // the time range
            if (dayOfWeek == now.get(Calendar.DAY_OF_WEEK)) {
                if (start.getTimeInMillis() < now.getTimeInMillis() && now.getTimeInMillis() < end.getTimeInMillis()) {
                    return true;
                }
            }
        }
        return false;
    }


    public Calendar getNextPlayTime() {
        Calendar current = Calendar.getInstance();
        int nowDayOfWeek = current.get(Calendar.DAY_OF_WEEK);

        Iterator<ScheduleEntry> i = iterator();

        // go through the schedule and see if we can find a day of the week
        // that matches the login day of the week
        //   if we have the same day then check to see if the current time is
        //      less than the end play time if true return the start time
        //   if the current day of the week is less than tomorrow, then return
        //      tomorrows schedule start time
        //   the last case to check is adjusting the date for days of the
        //      week that have already past so we need to adjust for next week's date.
        while (i.hasNext()) {
            ScheduleEntry scheduleEntry = i.next();
            int scheduleDayOfWeek = scheduleEntry.getDayOfWeek();

            if (nowDayOfWeek == scheduleDayOfWeek) {
                long end   = scheduleEntry.getEndTime().getTimeInMillis();
                long now   = current.getTimeInMillis();

                if (now < end) {
                    return scheduleEntry.getStartTime();
                }
            }

            if (nowDayOfWeek < scheduleDayOfWeek) {
                return scheduleEntry.getStartTime();
            }
        }

        ScheduleEntry entry = iterator().next();
        Calendar start = entry.getStartTime();
        int offset = 7 - nowDayOfWeek + entry.getDayOfWeek();
        current.add(Calendar.DAY_OF_WEEK, offset);
        current.set(Calendar.HOUR_OF_DAY, start.get(Calendar.HOUR_OF_DAY));
        current.set(Calendar.MINUTE, start.get(Calendar.MINUTE));
        current.set(Calendar.SECOND, 0);
        current.set(Calendar.MILLISECOND, 0);
        return current;
    }
}
