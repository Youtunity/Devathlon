package net.youtunity.devathlon.daemon.server;

/**
 * Created by thecrealm on 24.07.16.
 */
public class ServerContextFactory {

    public static ServerContext create(String server) {

        ServerContext context = new ServerContext(server);

        return context;
    }
}
