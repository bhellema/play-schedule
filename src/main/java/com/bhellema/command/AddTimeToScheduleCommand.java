package com.bhellema.command;

import com.bhellema.PlaySchedule;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AddTimeToScheduleCommand implements CommandExecutor {

    public static final String NAME = "schedule";

    private PlaySchedule plugin;

    public AddTimeToScheduleCommand(PlaySchedule plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        plugin.getLogger().info("onCommand Reached in AddTimeToScheduleCommand");

        if (args.length > 2) {
            sender.sendMessage("Too many arguments!");
            return false;
        }

        if (args.length < 2) {
            sender.sendMessage("Not enough arguments!");
            return false;
        }

        /*Player target = (Bukkit.getServer().getPlayer(args[0]));
        if (target == null) {
            sender.sendMessage(args[0] + " is not online!");
            return false;
        }*/

        if (command.getName().equalsIgnoreCase("schedule")) {
            plugin.getLogger().info("schedule used");
            Bukkit.getServer().broadcastMessage("Player has time remaining...");
            return true;
        }
        return false;
    }
}
