package net.youtunity.devathlon.api.protocol.event;

import io.netty.buffer.ByteBuf;
import net.youtunity.devathlon.api.net.message.Message;
import net.youtunity.devathlon.api.net.util.ByteBufUtils;

/**
 * Created by thecrealm on 24.07.16.
 */
public class UnregisterListenerRequest implements Message {

    // CLIENT --> DAEMON

    private String event;

    public UnregisterListenerRequest() {
    }

    public UnregisterListenerRequest(String event) {
        this.event = event;
    }

    @Override
    public void encode(ByteBuf writer) {
        ByteBufUtils.writeString(event, writer);
    }

    @Override
    public void decode(ByteBuf reader) {
        this.event = ByteBufUtils.readString(reader);
    }

    public String getEvent() {
        return event;
    }
}
