package net.youtunity.devathlon.api.protocol.info;

import io.netty.buffer.ByteBuf;
import net.youtunity.devathlon.api.net.message.Message;
import net.youtunity.devathlon.api.net.util.ByteBufUtils;

/**
 * Created by thecrealm on 25.07.16.
 */
public class ServerOnlinePlayersUpdate implements Message {

    private String server;
    private int count;

    public ServerOnlinePlayersUpdate() {
    }

    public ServerOnlinePlayersUpdate(String server, int count) {
        this.server = server;
        this.count = count;
    }

    @Override
    public void encode(ByteBuf writer) {
        ByteBufUtils.writeString(server, writer);
        writer.writeInt(count);
    }

    @Override
    public void decode(ByteBuf reader) {
        this.server = ByteBufUtils.readString(reader);
        this.count = reader.readInt();
    }

    public String getServer() {
        return server;
    }

    public int getCount() {
        return count;
    }
}
