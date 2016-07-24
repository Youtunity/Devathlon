package net.youtunity.devathlon;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;
import net.youtunity.devathlon.api.net.NettyClient;
import net.youtunity.devathlon.net.ServerInformationListener;
import net.youtunity.devathlon.net.ServerStatusListener;
import net.youtunity.devathlon.server.ServerRegistry;

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

        this.client = new NettyClient("127.0.0.1", 4040, true);

        // Out
        this.client.getMessageRegistry().register(ServerStartupRequestMessage.class, null);

        // In
        this.client.getMessageRegistry().register(ServerInformationMessage.class, new ServerInformationListener(this));
        this.client.getMessageRegistry().register(ServerStatusMessage.class, new ServerStatusListener(this));

        this.client.connect();

        ProxyServer.getInstance().getPluginManager().registerListener(this, new UserListener(this));

    }

    public NettyClient getClient() {
        return client;
    }

    public ServerRegistry getServerRegistry() {
        return serverRegistry;
    }

}
