package net.youtunity.devathlon.daemon.net;

import io.netty.channel.Channel;
import net.youtunity.devathlon.api.messages.ServerStartupRequestMessage;
import net.youtunity.devathlon.api.net.message.MessageHandler;

/**
 * Created by thecrealm on 23.07.16.
 */
public class ServerStartupRequestListener implements MessageHandler<ServerStartupRequestMessage> {

    @Override
    public void handle(Channel channel, ServerStartupRequestMessage message) {

    }
}
