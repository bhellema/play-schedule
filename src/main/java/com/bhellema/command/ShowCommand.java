package com.bhellema.command;

public class ShowCommand extends BaseCommand {

    public ShowCommand(CommandInfo info) {
        super(info);
    }

    @Override
    public boolean perform() {
        info.commandSender.sendMessage("So, you want to show the list of people eh?");
        return true;
    }
}
