package com.bhellema.command;

public abstract class BaseCommand implements ScheduleCommand {

    protected CommandInfo info;

    public BaseCommand(CommandInfo info) {
        this.info = info;
    }

    abstract public boolean perform();

    @Override
    public boolean execute() {
        if (isValid()) {
            return perform();
        }
        return false;
    }

    public boolean isValid() {
        return true;
    }
}
