package net.youtunity.devathlon.daemon;

import net.youtunity.devathlon.api.net.NettyServer;
import net.youtunity.devathlon.api.net.pipeline.MessageHandler;

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

    // package local
    void init(String[] args) {

        this.server = new NettyServer();
        setObserver();
        this.server.bind(DAEMON_DEFAULT_PORT);

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
