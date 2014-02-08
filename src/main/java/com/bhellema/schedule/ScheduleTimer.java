package com.bhellema.schedule;

import com.bhellema.event.TimeExpiredEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class ScheduleTimer {

    private PlayerSchedule playerSchedule;
    private Timer timer;
    private Scoreboard scoreboard;
    private Objective timeObjective;

    public ScheduleTimer(PlayerSchedule playerSchedule) {
        Player player = Bukkit.getPlayer(playerSchedule.getPlayerName());

        this.playerSchedule = playerSchedule;
        this.timer = new Timer();
        this.scoreboard = player.getScoreboard();

        timeObjective = scoreboard.getObjective("time");
        if (timeObjective == null) {
            timeObjective = scoreboard.registerNewObjective("time", "Time Remaining");
        }
        timeObjective.setDisplaySlot(DisplaySlot.SIDEBAR);

        startTimer();
    }

    public void startTimer() {
        ScheduleEntry scheduleEntry = playerSchedule.getCurrentScheduleEntry();
        PreKickTimerTask task = new PreKickTimerTask(scheduleEntry.getEndTime());
        timer.schedule(task, 0, 1000);
    }

    public void cancel() {
        timer.cancel();
    }

    class PreKickTimerTask extends TimerTask {
        private int previoustime = 0;
        private int timeleft = -1;
        private Calendar kickTime;
        public PreKickTimerTask(Calendar kickTime) {
            this.kickTime = kickTime;
        }

        @Override
        public void run() {
            Calendar now = Calendar.getInstance();
            Score score = timeObjective.getScore(Bukkit.getOfflinePlayer(playerSchedule.getPlayerName()));

            int TIME = kickTime.getTimeInMillis() - now.getTimeInMillis() < 60000 ? 60 : 1;
            timeleft = (int) ((kickTime.getTimeInMillis() / (60000 / TIME)) - (now.getTimeInMillis() / (60000 / TIME)));

            // be kind and not hammer the score board, no need to
            if (previoustime != timeleft) {
                score.setScore(timeleft);
                previoustime = timeleft;
            }

            if (timeleft == 0) {
                TimeExpiredEvent timeExpiredEvent = new TimeExpiredEvent(playerSchedule);
                Bukkit.getServer().getPluginManager().callEvent(timeExpiredEvent);
            }
        }
    }
}
