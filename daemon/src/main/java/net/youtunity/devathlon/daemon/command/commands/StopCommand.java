package net.youtunity.devathlon.daemon.command.commands;

import net.youtunity.devathlon.daemon.command.CommandContext;
import net.youtunity.devathlon.daemon.command.CommandHandler;

/**
 * Created by thecrealm on 24.07.16.
 */
public class StopCommand implements CommandHandler {

    @Override
    public void execute(CommandContext context) {
        System.exit(0);
    }
}
