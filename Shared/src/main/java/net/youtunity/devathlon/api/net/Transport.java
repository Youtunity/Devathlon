package net.youtunity.devathlon.api.net;

import io.netty.channel.Channel;
import net.youtunity.devathlon.api.net.message.Message;

/**
 * Created by thecrealm on 24.07.16.
 */
public interface Transport {

    Channel getChannel();

    boolean isActive();

    void sendMessage(Message message);

}
