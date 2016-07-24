package net.youtunity.devathlon.daemon.command;

import java.util.Arrays;

/**
 * Created by thecrealm on 24.07.16.
 */
public class CommandContext {

    private final String[] rawArgs;

    public CommandContext(String[] args) {
        this.rawArgs = args;
    }

    /**
     * Contains command + args
     */
    public String[] getRawArgs() {
        return this.rawArgs;
    }

    /**
     * Contains only args
     */
    public String[] getArgs() {
        return Arrays.copyOfRange(this.rawArgs, 1, rawArgs.length);
    }
}
