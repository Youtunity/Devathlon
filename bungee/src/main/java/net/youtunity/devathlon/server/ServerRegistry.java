package net.youtunity.devathlon.server;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by thecrealm on 23.07.16.
 */
public class ServerRegistry {

    private Map<String, ServerContext> servers = new ConcurrentHashMap<>();

    public ServerContext lookupContext(String server) {
        ServerContext context = servers.get(server);

        if (context == null) {
            context = new ServerContext(server);
            servers.put(server, context);
        }

        return context;
    }
}
