package com.bhellema.command.help;

import com.bhellema.command.CommandInfo;
import org.bukkit.ChatColor;

/**
 * A Helper class for command messages that will display
 * a more detailed message to the end user.
 */
public class CommandHelpMessage {

    private final String msg;
    private final CommandInfo info;

    public CommandHelpMessage(CommandInfo info, String msg) {
        this.info = info;
        this.msg = msg;
    }

    /**
     * Display the message to the caller of this command.
     */
    public void displayHelp() {
        String msg = String.format("%s /%s %s",
            ChatColor.GOLD + "Usage: " + ChatColor.WHITE,
            info.getAlias(),
            this.msg);

        info.sendMessage(ChatColor.GOLD + msg);
    }
}
