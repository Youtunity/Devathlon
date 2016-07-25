package net.youtunity.devathlon;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;
import net.youtunity.devathlon.api.net.NettyClient;
import net.youtunity.devathlon.api.protocol.control.ServerStartRequest;
import net.youtunity.devathlon.api.protocol.control.ServerStopRequest;
import net.youtunity.devathlon.api.protocol.info.ServerInformationRequest;
import net.youtunity.devathlon.api.protocol.info.ServerInformationResponse;
import net.youtunity.devathlon.api.protocol.info.ServerStatusUpdate;
import net.youtunity.devathlon.api.protocol.interaction.ChangeTemplateRequest;
import net.youtunity.devathlon.api.protocol.interaction.UpdateMOTDRequest;
import net.youtunity.devathlon.net.ServerInformationResponseHandler;
import net.youtunity.devathlon.net.ServerStatusUpdateHandler;
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

        //control
        this.client.getMessageRegistry().register(ServerStartRequest.class, null);
        this.client.getMessageRegistry().register(ServerStopRequest.class, null);

        //info
        this.client.getMessageRegistry().register(ChangeTemplateRequest.class, null);
        this.client.getMessageRegistry().register(ServerInformationRequest.class, null);
        this.client.getMessageRegistry().register(UpdateMOTDRequest.class, null);
        this.client.getMessageRegistry().register(ServerInformationResponse.class, new ServerInformationResponseHandler(this));
        this.client.getMessageRegistry().register(ServerStatusUpdate.class, new ServerStatusUpdateHandler(this));

        this.client.connect();
        this.client.sendMessage(new ServerInformationRequest(null));

        ProxyServer.getInstance().getPluginManager().registerListener(this, new UserListener(this));

    }

    public NettyClient getClient() {
        return client;
    }

    public ServerRegistry getServerRegistry() {
        return serverRegistry;
    }

}
