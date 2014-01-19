package com.bhellema.command;

public class CommandResolver {

    public static ScheduleCommand resolve(CommandInfo info) {
        if (info == null) {
            throw new IllegalArgumentException("CommandInfo can not be null");
        }

        String cmd = info.getCommand();

        if (cmd.equals("show")) {
            return new ShowCommand(info);
        } else if (cmd.equals("hud")) {
            return new HudCommand(info);
        }

        return new HelpCommand(info);
    }

}
