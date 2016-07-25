package net.youtunity.devathlon.daemon.command.commands;

import net.youtunity.devathlon.daemon.Daemon;
import net.youtunity.devathlon.daemon.command.CommandContext;
import net.youtunity.devathlon.daemon.command.CommandHandler;
import net.youtunity.devathlon.daemon.server.ServerProcess;

/**
 * Created by thecrealm on 24.07.16.
 */
public class StopCommand implements CommandHandler {

    @Override
    public void execute(CommandContext context) {

        Daemon.getInstance().getServerRegistry().getServers().forEach((s, server) -> {
            ServerProcess process = server.getProcess();
            if (process != null) {
                process.stop();
            }
        });

        System.exit(0);
    }
}
