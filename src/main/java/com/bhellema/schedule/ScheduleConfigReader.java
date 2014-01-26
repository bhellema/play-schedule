package com.bhellema.schedule;

import org.apache.commons.lang.StringUtils;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ScheduleConfigReader {

    private FileConfiguration config;
    private List<PlayerSchedule> schedules;

    public ScheduleConfigReader(FileConfiguration config) {
        this.config = config;
        this.schedules = new ArrayList<PlayerSchedule>();

        read();
    }

    private void read() {
        List<?> users = config.getList("schedule.users");
        Iterator<?> userIterator = users.iterator();

        while (userIterator.hasNext()) {
            Map userEntry = (Map) userIterator.next();
            Set userKeys = userEntry.keySet();
            Iterator userNameIterator = userKeys.iterator();

            while (userNameIterator.hasNext()) {
                String playerName = (String) userNameIterator.next();
                PlayerSchedule playerSchedule = new PlayerSchedule(playerName);

                List<String> entries = (ArrayList) userEntry.get(playerName);
                for (String entry : entries) {
                    PlayEntry playEntry = new PlayEntry(entry);
                    System.out.println(playerName + " " + playEntry.toString());
                }
            }
        }
    }

    public List<PlayerSchedule> getPlayerSchedules() {
        return schedules;
    }

}
