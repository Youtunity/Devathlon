package net.youtunity.devathlon.net;

import net.youtunity.devathlon.DevathlonPlugin;
import net.youtunity.devathlon.api.net.Transport;
import net.youtunity.devathlon.api.net.message.MessageHandler;
import net.youtunity.devathlon.api.protocol.info.ServerInformationResponse;
import net.youtunity.devathlon.server.ServerContext;

/**
 * Created by thecrealm on 24.07.16.
 */
public class ServerInformationResponseHandler implements MessageHandler<ServerInformationResponse> {

    private DevathlonPlugin plugin;

    public ServerInformationResponseHandler(DevathlonPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void handle(Transport transport, ServerInformationResponse message) {
        ServerContext context = plugin.getServerRegistry().lookupContext(message.getServer());

        context.setHost(message.getHost());
        context.setPort(message.getPort());
        context.setMotd(message.getMotd());
        context.doStatusChange(message.getStatus());
    }
}
