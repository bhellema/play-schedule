package com.bhellema.command;

import com.bhellema.command.help.CommandHelpProvider;
import org.apache.commons.lang.ArrayUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class ScheduleCommandExecutor implements CommandExecutor {

    private JavaPlugin plugin;

    public ScheduleCommandExecutor(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        CommandInfo info = new CommandInfo(commandSender, command, label, args);

        if(isValidCommand(info)) {
            ScheduleCommand cmd = CommandResolver.resolve(info);
            if(!cmd.execute() && cmd instanceof CommandHelpProvider) {
                return ((CommandHelpProvider) cmd).displayHelp();
            } else {
                return true;
            }
        }
        return false;
    }

    private boolean isValidCommand(CommandInfo commandInfo) {
        return    commandInfo.getCommandName().equalsIgnoreCase("schedule")
               && !ArrayUtils.isEmpty(commandInfo.getArgs())
               && commandInfo.isPlayerCommand();
    }
}
