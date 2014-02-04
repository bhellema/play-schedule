package com.bhellema.command;

import com.bhellema.command.help.CommandHelpMessage;
import com.bhellema.command.help.CommandHelpProvider;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

public class HudCommand extends BaseCommand implements CommandHelpProvider {

    public static final String ON = "on";
    public static final String OFF = "off";

    public static final String[] OPTIONS = {ON,OFF};

    public HudCommand(CommandInfo info) {
        super(info);
    }

    @Override
    public boolean isValid() {
        return (    info.getArgs().length == 2
                 && ArrayUtils.contains(OPTIONS, info.getArgs()[1]));
    }

    @Override
    public boolean perform() {
        String cmd = info.getArgs()[1];

        Scoreboard board = info.getPlayer().getScoreboard();
        if (OFF.equals(cmd)) {
            board.getObjective("Play Time").unregister();
            return true;
        } else if (ON.equals(cmd)) {
            Objective objective = board.getObjective("Play Time");
            if (objective == null) {
                objective = board.registerNewObjective("Play Time", "Time Remaining");
                objective.setDisplayName(ChatColor.GRAY + "Time Remaining");
                objective.setDisplaySlot(DisplaySlot.SIDEBAR);
                Score score = objective.getScore(Bukkit.getOfflinePlayer(info.getPlayer().getName()));
                score.setScore(100);
            }
            return true;
        } else {
            return displayHelp();
        }
    }

    @Override
    public boolean displayHelp() {
        String msg =  "hud <" + StringUtils.join(OPTIONS, "|") + ">";
        CommandHelpMessage helpMessage = new CommandHelpMessage(info, msg);
        helpMessage.displayHelp();
        return true;
    }
}
