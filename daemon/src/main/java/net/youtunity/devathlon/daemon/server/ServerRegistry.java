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
    private List<Integer> availablePorts = new ArrayList<>();

    public ServerRegistry() {

        for (int i = 0; i < 1000; i++) {
            availablePorts.add(i);
        }

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

    public int getAvailablePort() {
        int port = availablePorts.get(0);
        availablePorts.remove(port);
        return port + 40000;
    }

    public void freePort(int port) {
        availablePorts.add(port - 40000);
    }
}
