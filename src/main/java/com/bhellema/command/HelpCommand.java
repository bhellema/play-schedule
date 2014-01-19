package com.bhellema.command;

import org.bukkit.ChatColor;

public class HelpCommand extends BaseCommand {

    public HelpCommand(CommandInfo info) {
        super(info);
    }

    @Override
    public boolean perform() {
        if(info.getArgs().length != 1) {
            info.getPlayer().sendMessage(ChatColor.RED + "Usage: /"+ info.getAlias() + " show");
        }
        else if(!info.getPlayer().hasPermission("schedule.help") && !info.getPlayer().isOp()) {
            info.getPlayer().sendMessage(ChatColor.RED + "You aren't allowed to use this command!");
        }
        else {
            info.getPlayer().sendMessage(ChatColor.RED + "Usage: /"+ info.getAlias() + " show");
        }

        return true;
    }
}
