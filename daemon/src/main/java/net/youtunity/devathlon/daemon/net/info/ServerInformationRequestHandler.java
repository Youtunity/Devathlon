package net.youtunity.devathlon.daemon.net.info;

import net.youtunity.devathlon.api.net.Transport;
import net.youtunity.devathlon.api.net.message.MessageHandler;
import net.youtunity.devathlon.api.protocol.info.ServerInformationRequest;
import net.youtunity.devathlon.api.protocol.info.ServerInformationResponse;
import net.youtunity.devathlon.daemon.Daemon;
import net.youtunity.devathlon.daemon.server.ServerContext;

import java.util.Map;

/**
 * Created by thecrealm on 24.07.16.
 */
public class ServerInformationRequestHandler implements MessageHandler<ServerInformationRequest> {

    @Override
    public void handle(Transport transport, ServerInformationRequest message) {

        Map<String, ServerContext> servers = Daemon.getInstance().getServerRegistry().getServers();

        servers.forEach((server, context) -> {
            transport.sendMessage(new ServerInformationResponse(server, context.getHost(), context.getPort(), context.getMotd(), context.getStatus()));
        });
    }
}
