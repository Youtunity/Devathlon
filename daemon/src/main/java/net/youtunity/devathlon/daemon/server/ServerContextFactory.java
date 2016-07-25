package net.youtunity.devathlon.daemon.server;

import net.youtunity.devathlon.daemon.server.persistence.PersistenceContext;

/**
 * Created by thecrealm on 24.07.16.
 */
public class ServerContextFactory {

    public static ServerContext create(String server) {
        ServerContext context = PersistenceContext.findContext(server);

        if (context == null) {
            context = new ServerContext(server);
            context.setMotd("Create your server simply by join this server!");
            PersistenceContext.insertContext(context);
        }

        return context;
    }
}
