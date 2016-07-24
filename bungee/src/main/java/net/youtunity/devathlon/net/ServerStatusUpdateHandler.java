package net.youtunity.devathlon.net;

import net.youtunity.devathlon.DevathlonPlugin;
import net.youtunity.devathlon.api.net.Transport;
import net.youtunity.devathlon.api.net.message.MessageHandler;
import net.youtunity.devathlon.api.protocol.info.ServerStatusUpdate;

/**
 * Created by thecrealm on 24.07.16.
 */
public class ServerStatusUpdateHandler implements MessageHandler<ServerStatusUpdate> {

    private DevathlonPlugin plugin;

    public ServerStatusUpdateHandler(DevathlonPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void handle(Transport transport, ServerStatusUpdate message) {
        plugin.getServerRegistry().lookupContext(message.getServer()).doStatusChange(message.getStatus());
    }
}
