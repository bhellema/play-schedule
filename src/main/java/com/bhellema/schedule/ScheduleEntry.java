package com.bhellema.schedule;

import net.minecraft.util.org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.lang.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * The ScheduleEntry is a single entry in the ScheduleConfig.  For
 * example a entry is in the format of day:startHour:endHour.
 *
 * The day is the first argument.  The value is one of the days
 * from Calendar.DAY_OF_WEEK.
 *
 * Examples:
 *       1:0800:0900   // allow only Sunday from 8:00 AM to 9:00 AM
 *       2:0800:1000   // allow only Monday from 8:00 AM to 10:00 AM
 *       3:0800:1400   // allow only Tuesdays from 8:00 AM to 2:00 PM
 *       4:0800:23:59  // allow only Wednesday 8:00 AM to 11:59 PM
 *       5:0800:0900   // allow only Thursdays from 8:00 AM to 9:00 AM
 *       6:0800:0900   // allow only Friday from 8:00 AM to 9:00 AM
 *       7:0800:0900   // allow only Saturday from 8:00 AM to 9:00 AM
 *       8:0600:7050   // allow every day from 6:00 AM to 7:50 AM
 */
public class ScheduleEntry implements Comparable {

    private int day;
    private Calendar startTime;
    private Calendar endTime;

    public ScheduleEntry(String entry) throws PlayEntryException {
        String[] args = StringUtils.split(entry, "/");

        if (args == null || args.length != 3) {
            throw new IllegalArgumentException("Play Entries must be in the format of day:0000:0000");
        }

        SimpleDateFormat sdf = new SimpleDateFormat("h:m");

        this.day = Integer.parseInt(args[0]);

        if (this.day > Calendar.DAY_OF_WEEK || this.day < Calendar.SUNDAY) {
            throw new IllegalArgumentException("Invalid day range " + entry);
        }

        try {
            startTime = getDate(this.day, sdf.parse(args[1]));
            endTime = getDate(this.day, sdf.parse(args[2]));
        } catch (ParseException e) {
            throw new PlayEntryException("Unable to parse the date", e);
        }
    }

    private Calendar getDate(int dayOfWeek, Date time) {
        Calendar now = Calendar.getInstance();

        Calendar scheduled = Calendar.getInstance();
        scheduled.setTime(time);

        int nowDayOfWeek = now.get(Calendar.DAY_OF_WEEK);
        if (dayOfWeek != nowDayOfWeek) {
            int daysOffset;
            // if today being Wednesday but the scedule day is Tuesday
            // we have to roll over to next week
            if (dayOfWeek < nowDayOfWeek) {
                daysOffset = 7 - nowDayOfWeek + dayOfWeek;
            } else {
                // if today is Saturday and tomorrow (dayOfWeek) is Sunday
                daysOffset = dayOfWeek - nowDayOfWeek;
            }
            now.add(Calendar.DATE, daysOffset);
        } else {
            // if we have the same day we need to check to see if we've already passed our play time
            if (DateUtils.truncatedCompareTo(now, scheduled, Calendar.MILLISECOND) == 1) {
                now.add(Calendar.DATE, 7);
            }
        }


        int hours = scheduled.get(Calendar.HOUR_OF_DAY);
        int minute = scheduled.get(Calendar.MINUTE);

        now.set(Calendar.HOUR_OF_DAY, hours);
        now.set(Calendar.MINUTE, minute);
        now.set(Calendar.SECOND, 0);

        return now;
    }

    public Calendar getStartTime() {
        return startTime;
    }

    public Calendar getEndTime() {
        return endTime;
    }

    public String toString() {
        return startTime.toString() + " - " + endTime.toString();
    }

    @Override
    public int compareTo(Object entry) {
        ScheduleEntry sEntry = (ScheduleEntry) entry;
        Calendar s1 = sEntry.getStartTime();
        return startTime.compareTo(s1);
    }

    private class PlayEntryException extends RuntimeException {
        public PlayEntryException(String s, ParseException e) {
            super(s, e);
        }
    }
}
