package net.youtunity.devathlon.net;

import io.netty.channel.Channel;
import net.youtunity.devathlon.DevathlonPlugin;
import net.youtunity.devathlon.api.messages.ServerStatusMessage;
import net.youtunity.devathlon.api.net.message.MessageHandler;

/**
 * Created by thecrealm on 23.07.16.
 */
public class ServerStatusListener implements MessageHandler<ServerStatusMessage> {

    private DevathlonPlugin plugin;

    public ServerStatusListener(DevathlonPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void handle(Channel channel, ServerStatusMessage message) {
        plugin.getServerRegistry().lookupContext(message.getServer()).doStatusChange(message.getStatus());
    }
}
