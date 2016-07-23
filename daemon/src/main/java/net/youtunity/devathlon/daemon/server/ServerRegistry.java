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
    private Map<Integer, Boolean> ports = new HashMap<>();

    public ServerRegistry() {

        for (int i = 0; i < 2000; i++) {
            ports.put(i + 40000, false);
        }
    }

    public ServerContext lookupContext(String server) {
        ServerContext context = servers.get(server);

        if(context == null) {
            context = new ServerContext(server);
            servers.put(server, context);
        }

        return context;
    }

    public int usePort() {

        Optional<Map.Entry<Integer, Boolean>> any = ports.entrySet().stream()
                .filter(entry -> !entry.getValue())
                .findAny();

        if(any.isPresent()) {
            int port = any.get().getKey();
            any.get().setValue(true);
            return port;
        } else {
            throw new IllegalStateException("All ports are in use");
        }
    }

    public void freePort(int port) {
        ports.put(port, false);
    }
}
