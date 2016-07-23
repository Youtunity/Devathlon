package net.youtunity.devathlon.api.messages;

import io.netty.buffer.ByteBuf;
import net.youtunity.devathlon.api.ServerStatus;
import net.youtunity.devathlon.api.net.message.Message;
import net.youtunity.devathlon.api.net.util.ByteBufUtils;

/**
 * Created by thecrealm on 23.07.16.
 */
public class ServerStatusMessage implements Message {

    private String server;
    private ServerStatus status;

    public ServerStatusMessage() {
    }

    public ServerStatusMessage(String server, ServerStatus status) {
        this.server = server;
        this.status = status;
    }

    @Override
    public void encode(ByteBuf writer) {
        ByteBufUtils.writeString(this.server, writer);
        ByteBufUtils.writeVarInt(status.ordinal(), writer);

    }

    @Override
    public void decode(ByteBuf reader) {
        this.server = ByteBufUtils.readString(reader);
        this.status = ServerStatus.values()[ByteBufUtils.readVarInt(reader)];
    }

    public String getServer() {
        return server;
    }

    public ServerStatus getStatus() {
        return status;
    }
}
