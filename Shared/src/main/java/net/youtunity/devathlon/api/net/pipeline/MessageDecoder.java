package net.youtunity.devathlon.api.net.pipeline;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import net.youtunity.devathlon.api.net.NetworkBase;
import net.youtunity.devathlon.api.net.message.Message;
import net.youtunity.devathlon.api.net.util.ByteBufUtils;

import java.util.List;

/**
 * Created by thecrealm on 23.07.16.
 */
public class MessageDecoder extends MessageToMessageDecoder<ByteBuf> {

    private NetworkBase base;

    public MessageDecoder(NetworkBase base) {
        this.base = base;
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> out) throws Exception {

        int id = byteBuf.readInt();
        Message message = base.getMessageRegistry().createMessage(id);
        message.decode(byteBuf);

        out.add(message);
    }
}
