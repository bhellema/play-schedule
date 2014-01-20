package com.bhellema.command;

import org.bukkit.ChatColor;

public class HelpCommand extends BaseCommand {

    private static final String[] OPTIONS = { "show",
                                              "hud" };

    public HelpCommand(CommandInfo info) {
        super(info);
    }

    @Override
    public boolean perform() {
        if(info.getArgs().length != 1) {
            info.sendMessage(ChatColor.RED + "Usage: /"+ info.getAlias() + " " + printCommandOptions());
        }
        else if(!info.hasPermission("schedule.help")) {
            info.sendMessage(ChatColor.RED + "You aren't allowed to use this command!");
        }
        else {
            info.sendMessage(ChatColor.RED + "Usage: /"+ info.getAlias() + " " +  printCommandOptions());
        }
        return true;
    }

    /**
     * Formats the help command options.
     * @return a formatted string <option1|option2|option3>
     */
    private String printCommandOptions() {
        StringBuilder builder = new StringBuilder();
        builder.append("<");

        int cnt = 1;
        for (String option : OPTIONS) {
            builder.append(option);
            if (cnt++ < OPTIONS.length) {
                builder.append("|");
            }
        }

        builder.append(">");

        return builder.toString();
    }
}
