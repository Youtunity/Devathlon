package net.youtunity.devathlon.api.protocol.parameter;

import io.netty.buffer.ByteBuf;
import net.youtunity.devathlon.api.net.message.Message;
import net.youtunity.devathlon.api.net.util.ByteBufUtils;

/**
 * Created by thecrealm on 24.07.16.
 */
public class ServerParameterRequest implements Message {

    // CLIENT --> DAEMON

    private boolean bulkFlag = false;
    private String server;

    public ServerParameterRequest() {
    }

    public ServerParameterRequest(String server) {
        if(server == null) {
            bulkFlag = true;
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

    public boolean isBulkFlag() {
        return bulkFlag;
    }

    public String getServer() {
        return server;
    }
}
