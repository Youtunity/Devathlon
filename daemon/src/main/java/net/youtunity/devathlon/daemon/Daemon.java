package net.youtunity.devathlon.daemon;

import net.youtunity.devathlon.api.protocol.control.ServerStartRequest;
import net.youtunity.devathlon.api.protocol.info.ServerStatusUpdate;
import net.youtunity.devathlon.api.net.NettyServer;
import net.youtunity.devathlon.daemon.net.ServerStartRequestListener;
import net.youtunity.devathlon.daemon.server.ServerRegistry;

/**
 * Created by thecrealm on 23.07.16.
 */
public class Daemon {

    private static Daemon instance;

    public static Daemon getInstance() {

        if(null == instance) {
            instance = new Daemon();
        }

        return instance;
    }

    private NettyServer server;
    private ServerRegistry serverRegistry;

    // package local
    void init(String[] args) {

        this.serverRegistry = new ServerRegistry();
        this.server = new NettyServer();

        // In
        this.server.getMessageRegistry().register(ServerStartRequest.class, new ServerStartRequestListener());

        // Out
        this.server.getMessageRegistry().register(ServerInformationMessage.class, null);
        this.server.getMessageRegistry().register(ServerStatusUpdate.class, null);

        // Bind
        this.server.bind(Constants.DAEMON_DEFAULT_PORT);

    }

    public NettyServer getServer() {
        return server;
    }

    public ServerRegistry getServerRegistry() {
        return serverRegistry;
    }

}
