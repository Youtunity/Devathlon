package net.youtunity.devathlon.api.protocol.interaction;

import io.netty.buffer.ByteBuf;
import net.youtunity.devathlon.api.net.message.Message;
import net.youtunity.devathlon.api.net.util.ByteBufUtils;

/**
 * Created by thecrealm on 25.07.16.
 */
public class ChangeTemplateRequest implements Message {

    private String server;
    private String template;

    public ChangeTemplateRequest() {
    }

    public ChangeTemplateRequest(String server, String template) {
        this.server = server;
        this.template = template;
    }

    @Override
    public void encode(ByteBuf writer) {
        ByteBufUtils.writeString(server, writer);
        ByteBufUtils.writeString(template, writer);
    }

    @Override
    public void decode(ByteBuf reader) {
        this.server = ByteBufUtils.readString(reader);
        this.template = ByteBufUtils.readString(reader);
    }

    public String getServer() {
        return server;
    }

    public String getTemplate() {
        return template;
    }
}
