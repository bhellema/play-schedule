package com.bhellema.schedule;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * The PlayEntry is a single entry in the ScheduleConfig.  For
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
public class PlayEntry {

    private int day;
    private Date startTime;
    private Date endTime;

    public PlayEntry(String entry) {
        String[] e = StringUtils.split(entry, "/");

        if (e == null || e.length != 3) {
            throw new IllegalArgumentException("Play Entries must be in the format of day:0000:0000");
        }

        SimpleDateFormat sdf = new SimpleDateFormat("h:m");
        this.day = Integer.parseInt(e[0]);

        Date s = null;
        Date f = null;
        try {
            s = sdf.parse(e[1]);
            f = sdf.parse(e[2]);
        } catch (ParseException e1) {
            e1.printStackTrace();
        }

        startTime = getDate(this.day, s);
        endTime = getDate(this.day, f);
    }

    private Date getDate(int day, Date time) {
        Calendar now = Calendar.getInstance();

        int nowDay = now.get(Calendar.DAY_OF_WEEK);
        int nextDate = 0;
        if (day <= nowDay) {
            nextDate = now.get(Calendar.DATE) + day;
        }

        now.set(Calendar.DATE, nextDate);
        now.set(Calendar.HOUR_OF_DAY, time.getHours());
        now.set(Calendar.MINUTE, time.getMinutes());
        now.set(Calendar.SECOND, 0);

        return now.getTime();
    }

    public Date getStartTime() {
        return startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public String toString() {
        return startTime.toString() + " - " + endTime.toString();
    }
}
