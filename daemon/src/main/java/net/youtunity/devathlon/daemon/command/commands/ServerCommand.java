package net.youtunity.devathlon.daemon.command.commands;

import net.youtunity.devathlon.api.ServerStatus;
import net.youtunity.devathlon.daemon.Daemon;
import net.youtunity.devathlon.daemon.command.CommandContext;
import net.youtunity.devathlon.daemon.command.CommandHandler;
import net.youtunity.devathlon.daemon.server.ServerContext;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by thecrealm on 24.07.16.
 */
public class ServerCommand implements CommandHandler {

    @Override
    public void execute(CommandContext context) {

        if(context.getArgs()[0].equalsIgnoreCase("list")) {
            System.out.println("----------[ RUNNING ]----------");
            List<Map.Entry<String, ServerContext>> list = Daemon.getInstance().getServerRegistry().getServers().entrySet().stream()
                    .filter(entry -> entry.getValue().getStatus() == ServerStatus.RUNNING)
                    .collect(Collectors.toList());
            for (Map.Entry<String, ServerContext> entry : list) {
                System.out.println(entry.getKey() + " - " + entry.getValue().getPort());
            }
            System.out.println("-------------------------------");
        }
    }
}
