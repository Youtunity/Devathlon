package net.youtunity.devathlon.daemon.server;

import net.youtunity.devathlon.daemon.server.persistence.LoadServersTask;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by thecrealm on 23.07.16.
 */
public class ServerRegistry {

    private Map<String, ServerContext> servers = new HashMap<>();

    public ServerRegistry() {
        List<ServerContext> execute = LoadServersTask.execute();
        System.out.println("Loaded " + execute.size() + "servers from database!");
        for (ServerContext context : execute) {
            servers.put(context.getServer(), context);
        }
    }

    public ServerContext lookupContext(String server) {
        ServerContext context = servers.get(server);

        if (context == null) {
            context = ServerContextFactory.create(server);
            servers.put(server, context);
        }

        return context;
    }

    public Map<String, ServerContext> getServers() {
        return servers;
    }
}
