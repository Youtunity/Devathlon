package net.youtunity.devathlon.api.net.message;

import io.netty.channel.Channel;
import net.youtunity.devathlon.api.net.Transport;

/**
 * Created by thecrealm on 23.07.16.
 */
public interface MessageHandler<M extends Message> {

    void handle(Transport transport, M message);
}
