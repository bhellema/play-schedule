package com.bhellema.command;

public abstract class BaseCommand implements ScheduleCommand {

    protected CommandInfo info;

    public BaseCommand(CommandInfo info) {
        this.info = info;
    }

    /**
     * Subclasses of <code>BaseCommand</code> will
     * need to override perform where the commands
     * invocation should take place.
     */
    abstract public boolean perform();


    /**
     * Execute is called by a <code>CommandExecutor</code>
     * which causes the command to validate that it will
     * accept the command arguments.  If the command is valid
     * then the command will be performed.
     *
     * <ul>
     * <li>{@link #isValid()}
     * <li>{@link #perform()}
     * </ul>
     */
    @Override
    public boolean execute() {
        if (isValid()) {
            return perform();
        }
        return false;
    }

    /**
     * A command can respond if it's valid before
     * being performed.
     *
     * @return true if the commands arguments are valid
     * prior to being performed, false otherwise.
     *
     * Subclasses should override this as the default
     * value returned is true.
     */
    public boolean isValid() {
        return true;
    }
}
