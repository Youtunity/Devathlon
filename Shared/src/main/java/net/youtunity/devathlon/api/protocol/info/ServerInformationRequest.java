package net.youtunity.devathlon.api.protocol.info;

import io.netty.buffer.ByteBuf;
import net.youtunity.devathlon.api.net.message.Message;
import net.youtunity.devathlon.api.net.util.ByteBufUtils;

/**
 * Created by thecrealm on 24.07.16.
 */
public class ServerInformationRequest implements Message {

    // CLIENT --> DAEMON

    private String server;
    private boolean bulkFlag = false;

    public ServerInformationRequest() {
    }

    public ServerInformationRequest(String server) {
        if (server == null) {
            this.bulkFlag = true;
            this.server = "useless";
        } else {
            this.server = server;
        }
    }

    @Override
    public void encode(ByteBuf writer) {
        writer.writeBoolean(bulkFlag);
        ByteBufUtils.writeString(server, writer);
    }

    @Override
    public void decode(ByteBuf reader) {
        this.bulkFlag = reader.readBoolean();
        this.server = ByteBufUtils.readString(reader);
    }

    public boolean isBulkRequest() {
        return bulkFlag;
    }

    public String getServer() {
        return server;
    }
}
