package com.bhellema.command;

import org.apache.commons.lang.ArrayUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

public class HudCommand extends BaseCommand {

    public static final String NAME = "hud";
    public static final String[] OPTIONS = {"on", "off"};

    public HudCommand(CommandInfo info) {
        super(info);
    }

    @Override
    public boolean isValid() {
        return  (    info.getArgs().length == 2
                  && ArrayUtils.contains(OPTIONS, info.getArgs()[1]));
    }

    @Override
    public boolean perform() {
        String cmd = info.getArgs()[1];

        Scoreboard board = info.getPlayer().getScoreboard();
        if ("off".equals(cmd)) {
            board.getObjective("Time").unregister();
            return true;
        } else if ("on".equals(cmd)) {
            Objective objective = board.getObjective("Time");
            if (objective == null) {
                objective = board.registerNewObjective("Time", "Time Remaining");
                objective.setDisplayName(ChatColor.GRAY + "Time Remaining");
                objective.setDisplaySlot(DisplaySlot.SIDEBAR);
                Score score = objective.getScore(Bukkit.getOfflinePlayer(info.getPlayer().getName()));
                score.setScore(100);
            }
            return true;
        }
        return false;
    }

}
