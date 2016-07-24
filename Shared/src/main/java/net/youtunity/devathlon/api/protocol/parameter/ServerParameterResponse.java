package net.youtunity.devathlon.api.protocol.parameter;

import io.netty.buffer.ByteBuf;
import net.youtunity.devathlon.api.net.message.Message;
import net.youtunity.devathlon.api.net.util.ByteBufUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by thecrealm on 24.07.16.
 */
public class ServerParameterResponse implements Message {

    // DAEMON --> CLIENT

    private String server;
    private Map<String, String> parameters;

    public ServerParameterResponse() {
    }

    public ServerParameterResponse(String server, Map<String, String> parameters) {
        this.server = server;
        this.parameters = parameters;
    }

    @Override
    public void encode(ByteBuf writer) {
        ByteBufUtils.writeString(server, writer);
        writer.writeInt(parameters.size());

        for (Map.Entry<String, String> entry : parameters.entrySet()) {
            ByteBufUtils.writeString(entry.getKey(), writer);
            ByteBufUtils.writeString(entry.getValue(), writer);
        }
    }

    @Override
    public void decode(ByteBuf reader) {
        this.server = ByteBufUtils.readString(reader);
        int size = reader.readInt();

        Map<String, String> map = new HashMap<>();
        for (int i = 0; i < size; i++) {
            map.put(ByteBufUtils.readString(reader), ByteBufUtils.readString(reader));
        }

        this.parameters = map;
    }
}
