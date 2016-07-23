package net.youtunity.devathlon.api.net.message;

import io.netty.buffer.ByteBuf;

/**
 * Created by thecrealm on 23.07.16.
 */
public interface Message {

    void encode(ByteBuf writer);

    void decode(ByteBuf reader);

}
