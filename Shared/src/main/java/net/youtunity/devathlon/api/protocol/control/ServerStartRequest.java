package net.youtunity.devathlon.api.protocol.control;

import io.netty.buffer.ByteBuf;
import net.youtunity.devathlon.api.net.message.Message;
import net.youtunity.devathlon.api.net.util.ByteBufUtils;

/**
 * Created by thecrealm on 24.07.16.
 */
public class ServerStartRequest implements Message {

    // CLIENT --> DAEMON

    private String server;

    public ServerStartRequest() {
    }

    public ServerStartRequest(String server) {
        this.server = server;
    }

    @Override
    public void encode(ByteBuf writer) {
        ByteBufUtils.writeString(server, writer);
    }

    @Override
    public void decode(ByteBuf reader) {
        this.server = ByteBufUtils.readString(reader);
    }

    public String getServer() {
        return server;
    }
}
