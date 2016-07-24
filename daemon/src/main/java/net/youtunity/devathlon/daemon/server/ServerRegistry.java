package net.youtunity.devathlon.daemon.server;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Created by thecrealm on 23.07.16.
 */
public class ServerRegistry {

    private Map<String, ServerContext> servers = new HashMap<>();

    public ServerContext lookupContext(String server) {
        ServerContext context = servers.get(server);

        if(context == null) {
            context = ServerContextFactory.create(server);
            servers.put(server, context);
        }

        return context;
    }

    public Map<String, ServerContext> getServers() {
        return servers;
    }
}
