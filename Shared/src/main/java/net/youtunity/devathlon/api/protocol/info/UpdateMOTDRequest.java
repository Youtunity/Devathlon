package net.youtunity.devathlon.api.protocol.info;

import io.netty.buffer.ByteBuf;
import net.youtunity.devathlon.api.net.message.Message;
import net.youtunity.devathlon.api.net.util.ByteBufUtils;

/**
 * Created by thecrealm on 25.07.16.
 */
public class UpdateMOTDRequest implements Message {

    private String server;
    private String motd;

    public UpdateMOTDRequest() {
    }

    public UpdateMOTDRequest(String server) {
        this.server = server;
    }

    @Override
    public void encode(ByteBuf writer) {
        ByteBufUtils.writeString(server, writer);
        ByteBufUtils.writeString(motd, writer);
    }

    @Override
    public void decode(ByteBuf reader) {
        this.server = ByteBufUtils.readString(reader);
        this.motd = ByteBufUtils.readString(reader);
    }

    public String getServer() {
        return server;
    }

    public String getMotd() {
        return motd;
    }
}
