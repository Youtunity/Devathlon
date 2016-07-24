package net.youtunity.devathlon.api.protocol.parameter;

import io.netty.buffer.ByteBuf;
import net.youtunity.devathlon.api.net.message.Message;
import net.youtunity.devathlon.api.net.util.ByteBufUtils;

/**
 * Created by thecrealm on 24.07.16.
 */
public class ServerParameterUpdateRequest implements Message {

    // CLIENT --> DAEMON

    private String server;
    private String parameter;
    private String value;

    public ServerParameterUpdateRequest() {
    }

    public ServerParameterUpdateRequest(String server, String parameter, String value) {
        this.server = server;
        this.parameter = parameter;
        this.value = value;
    }

    @Override
    public void encode(ByteBuf writer) {
        ByteBufUtils.writeString(server, writer);
        ByteBufUtils.writeString(parameter, writer);
        ByteBufUtils.writeString(value, writer);
    }

    @Override
    public void decode(ByteBuf reader) {
        this.server = ByteBufUtils.readString(reader);
        this.parameter = ByteBufUtils.readString(reader);
        this.value = ByteBufUtils.readString(reader);
    }

    public String getServer() {
        return server;
    }

    public String getParameter() {
        return parameter;
    }

    public String getValue() {
        return value;
    }
}
