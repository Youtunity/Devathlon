package net.youtunity.devathlon.daemon;

import net.youtunity.devathlon.api.messages.ServerInformationMessage;
import net.youtunity.devathlon.api.messages.ServerStartupRequestMessage;
import net.youtunity.devathlon.api.messages.ServerStatusMessage;
import net.youtunity.devathlon.api.net.NettyServer;
import net.youtunity.devathlon.api.net.pipeline.MessageHandler;
import net.youtunity.devathlon.daemon.net.ServerStartupRequestListener;
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

    private static final int DAEMON_DEFAULT_PORT = 4040;

    private NettyServer server;
    private ServerRegistry serverRegistry;

    // package local
    void init(String[] args) {

        this.serverRegistry = new ServerRegistry();

        this.server = new NettyServer();
        setObserver();

        // In
        this.server.getMessageRegistry().register(ServerStartupRequestMessage.class, new ServerStartupRequestListener());

        // Out
        this.server.getMessageRegistry().register(ServerInformationMessage.class, null);
        this.server.getMessageRegistry().register(ServerStatusMessage.class, null);

        this.server.bind(DAEMON_DEFAULT_PORT);

    }

    public NettyServer getServer() {
        return server;
    }

    public ServerRegistry getServerRegistry() {
        return serverRegistry;
    }

    private void setObserver() {

        this.server.setObserver(new NettyServer.ServerObserver() {

            @Override
            public void onActiveHandler(MessageHandler handler) {
                System.out.println("FUCK YEAH");
            }

            @Override
            public void onInactiveHandler(MessageHandler handler) {
                System.out.println("BYE BYE");
            }
        });
    }
}
