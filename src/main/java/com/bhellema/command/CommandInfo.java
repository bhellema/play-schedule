package com.bhellema.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandInfo {

    protected final CommandSender commandSender;
    protected final Command command;
    protected final String alias;
    protected final String[] args;
    private Player player;
    private String issuedCommand;

    public CommandInfo(CommandSender commandSender, Command command, String alias, String[] args) {
        this.commandSender = commandSender;
        this.command = command;
        this.alias = alias;
        this.args = args;
    }

    public String[] getArgs() {
        return args;
    }

    public boolean isPlayerCommand() {
        return (commandSender instanceof Player);
    }

    public String getCommandName() {
        return command.getName();
    }

    public String getCommand() {
        return getArgs()[0].toLowerCase();
    }

    public Player getPlayer() {
        return commandSender.getServer().getPlayer(commandSender.getName());
    }

    /**
     * Alias of the command which was used
     * @return the alias of the command
     */
    public String getAlias() {
        return alias;
    }
}
