package net.youtunity.devathlon.api.protocol.interaction;

import io.netty.buffer.ByteBuf;
import net.youtunity.devathlon.api.net.message.Message;
import net.youtunity.devathlon.api.net.util.ByteBufUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by thecrealm on 25.07.16.
 */
public class AvailableTemplatesResponse implements Message {

    private List<String> templates;

    public AvailableTemplatesResponse() {
    }

    public AvailableTemplatesResponse(List<String> templates) {
        this.templates = templates;
    }

    @Override
    public void encode(ByteBuf writer) {
        writer.writeInt(templates.size());

        for (String template : templates) {
            ByteBufUtils.writeString(template, writer);
        }
    }

    @Override
    public void decode(ByteBuf reader) {
        this.templates = new ArrayList<>();
        int size = reader.readInt();

        for (int i = 0; i < size; i++) {
            templates.add(ByteBufUtils.readString(reader));
        }
    }

    public List<String> getTemplates() {
        return templates;
    }
}
