package net.youtunity.devathlon;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Plugin;
import net.youtunity.devathlon.api.ServerStatus;
import net.youtunity.devathlon.api.messages.ServerInformationMessage;
import net.youtunity.devathlon.api.messages.ServerStartupRequestMessage;
import net.youtunity.devathlon.api.messages.ServerStatusMessage;
import net.youtunity.devathlon.api.net.NettyClient;
import net.youtunity.devathlon.net.ServerInformationListener;
import net.youtunity.devathlon.net.ServerStatusListener;
import net.youtunity.devathlon.server.ServerRegistry;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by thecrealm on 23.07.16.
 */
public class DevathlonPlugin extends Plugin {

    private static final int DEFAULT_DAEMON_SERVER = 4040;

    private NettyClient client;
    private ServerRegistry serverRegistry;


    @Override
    public void onEnable() {

        this.serverRegistry = new ServerRegistry();

        this.client = new NettyClient();
        setObserver();

        // Out
        this.client.getMessageRegistry().register(ServerStartupRequestMessage.class, null);

        // In
        this.client.getMessageRegistry().register(ServerInformationMessage.class, new ServerInformationListener(this));
        this.client.getMessageRegistry().register(ServerStatusMessage.class, new ServerStatusListener(this));



        this.client.connect("127.0.0.1", DEFAULT_DAEMON_SERVER);

        ProxyServer.getInstance().getPluginManager().registerListener(this, new UserListener(this));

    }

    public NettyClient getClient() {
        return client;
    }

    public ServerRegistry getServerRegistry() {
        return serverRegistry;
    }

    private void setObserver() {

        this.client.setObserver(new NettyClient.ClientObserver() {
            @Override
            public void onReady() {
                System.out.println("Connected, yeah!");
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println("Error? :c");
                throwable.printStackTrace();
            }
        });
    }
}
