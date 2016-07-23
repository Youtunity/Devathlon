package net.youtunity.devathlon.api.net.message;

import io.netty.channel.Channel;

/**
 * Created by thecrealm on 23.07.16.
 */
public interface MessageHandler<M extends Message> {

    void handle(Channel channel, M message);
}
