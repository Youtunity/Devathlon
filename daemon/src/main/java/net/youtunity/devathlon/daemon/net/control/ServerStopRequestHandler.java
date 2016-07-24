package net.youtunity.devathlon.daemon.net.control;

import net.youtunity.devathlon.api.net.Transport;
import net.youtunity.devathlon.api.net.message.MessageHandler;
import net.youtunity.devathlon.api.protocol.control.ServerStopRequest;
import net.youtunity.devathlon.daemon.Daemon;

/**
 * Created by thecrealm on 24.07.16.
 */
public class ServerStopRequestHandler implements MessageHandler<ServerStopRequest> {

    @Override
    public void handle(Transport transport, ServerStopRequest message) {
        Daemon.getInstance().getServerRegistry().lookupContext(message.getServer()).setRunning(false);
    }
}
