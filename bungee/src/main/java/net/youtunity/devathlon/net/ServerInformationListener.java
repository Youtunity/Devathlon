package net.youtunity.devathlon.net;

import io.netty.channel.Channel;
import net.youtunity.devathlon.DevathlonPlugin;
import net.youtunity.devathlon.api.messages.ServerInformationMessage;
import net.youtunity.devathlon.api.net.message.MessageHandler;
import net.youtunity.devathlon.server.ServerContext;

/**
 * Created by thecrealm on 23.07.16.
 */
public class ServerInformationListener implements MessageHandler<ServerInformationMessage> {

    private DevathlonPlugin plugin;

    public ServerInformationListener(DevathlonPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void handle(Channel channel, ServerInformationMessage message) {
        ServerContext context = plugin.getServerRegistry().lookupContext(message.getServer());

        context.setHost(message.getHost());
        context.setPort(message.getPort());
        context.setMotd(message.getMotd());
    }
}
