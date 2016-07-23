package net.youtunity.devathlon.daemon.net;

import io.netty.channel.Channel;
import net.youtunity.devathlon.api.ServerStatus;
import net.youtunity.devathlon.api.messages.ServerStartupRequestMessage;
import net.youtunity.devathlon.api.net.message.MessageHandler;
import net.youtunity.devathlon.daemon.Daemon;
import net.youtunity.devathlon.daemon.server.ServerContext;

/**
 * Created by thecrealm on 23.07.16.
 */
public class ServerStartupRequestListener implements MessageHandler<ServerStartupRequestMessage> {

    @Override
    public void handle(Channel channel, ServerStartupRequestMessage message) {
        ServerContext context = Daemon.getInstance().getServerRegistry().lookupContext(message.getServer());

        System.out.println("REQUESTED SERVER: " + message.getServer());

        if(context.getStatus() == ServerStatus.OFFLINE) {
            context.setRunning(true);
        }
    }
}
