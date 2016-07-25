package net.youtunity.devathlon.daemon.server;

import net.youtunity.devathlon.daemon.server.persistence.LoadServersTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by thecrealm on 23.07.16.
 */
public class ServerRegistry {

    private Map<String, ServerContext> servers = new HashMap<>();
    private List<Integer> used = new ArrayList<>();

    public ServerRegistry() {

        List<ServerContext> execute = LoadServersTask.execute();
        System.out.println("Loaded " + execute.size() + " servers from database!");
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

    public static final int MIN_PORT = 40000;
    public static final int MAX_PORT = 50000;

    //Ports are blocked, fix that later!

    public int getAvailablePort() {

        for (int i = MIN_PORT; i < MAX_PORT; i++) {

            if(used.contains(i)) {
                continue;
            }

            used.add(i);
            return i;
        }

        throw new RuntimeException("why the hell are 10000 servers running?");
    }
}
