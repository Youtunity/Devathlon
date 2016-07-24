package net.youtunity.devathlon.daemon.net;

import net.youtunity.devathlon.api.ServerStatus;
import net.youtunity.devathlon.api.messages.ServerStartRequest;
import net.youtunity.devathlon.api.net.Transport;
import net.youtunity.devathlon.api.net.message.MessageHandler;
import net.youtunity.devathlon.daemon.Daemon;
import net.youtunity.devathlon.daemon.server.ServerContext;

/**
 * Created by thecrealm on 23.07.16.
 */
public class ServerStartRequestListener implements MessageHandler<ServerStartRequest> {

    @Override
    public void handle(Transport transport, ServerStartRequest message) {
        ServerContext context = Daemon.getInstance().getServerRegistry().lookupContext(message.getServer());

        if(context.getStatus() == ServerStatus.OFFLINE) {
            context.setRunning(true);
        }
    }
}
