package net.youtunity.devathlon.api.messages;

import io.netty.buffer.ByteBuf;
import net.youtunity.devathlon.api.net.message.Message;
import net.youtunity.devathlon.api.net.message.MessageHandler;
import net.youtunity.devathlon.api.net.util.ByteBufUtils;

/**
 * Created by thecrealm on 23.07.16.
 */
public class ServerInformationMessage implements Message {

    private String server;
    private String host;
    private int port;
    private String motd;

    public ServerInformationMessage() {
    }

    public ServerInformationMessage(String server, String host, int port, String motd) {
        this.server = server;
        this.host = host;
        this.port = port;
        this.motd = motd;
    }

    @Override
    public void encode(ByteBuf writer) {
        ByteBufUtils.writeString(server, writer);
        ByteBufUtils.writeString(host, writer);
        ByteBufUtils.writeVarInt(port, writer);
        ByteBufUtils.writeString(motd, writer);
    }

    @Override
    public void decode(ByteBuf reader) {
        this.server = ByteBufUtils.readString(reader);
        this.host = ByteBufUtils.readString(reader);
        this.port = ByteBufUtils.readVarInt(reader);
        this.motd = ByteBufUtils.readString(reader);
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
