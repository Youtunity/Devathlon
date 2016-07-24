package net.youtunity.devathlon.api.protocol.info;

import io.netty.buffer.ByteBuf;
import net.youtunity.devathlon.api.ServerStatus;
import net.youtunity.devathlon.api.net.message.Message;
import net.youtunity.devathlon.api.net.util.ByteBufUtils;

/**
 * Created by thecrealm on 24.07.16.
 */
public class ServerInformationResponse implements Message {

    // DAEMON --> CLIENT

    private String server;
    private String host;
    private int port;
    private String motd;
    private ServerStatus status;

    public ServerInformationResponse() {
    }

    public ServerInformationResponse(String server, String host, int port, String motd, ServerStatus status) {
        this.server = server;
        this.host = host;
        this.port = port;
        this.motd = motd;
        this.status = status;
    }

    @Override
    public void encode(ByteBuf writer) {
        ByteBufUtils.writeString(server, writer);
        ByteBufUtils.writeString(host, writer);
        writer.writeInt(port);
        ByteBufUtils.writeString(motd, writer);
        writer.writeInt(status.ordinal());
    }

    @Override
    public void decode(ByteBuf reader) {
        this.server = ByteBufUtils.readString(reader);
        this.host = ByteBufUtils.readString(reader);
        this.port = reader.readInt();
        this.motd = ByteBufUtils.readString(reader);
        this.status = ServerStatus.values()[reader.readInt()];
    }

    public String getServer() {
        return server;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public String getMotd() {
        return motd;
    }
}
