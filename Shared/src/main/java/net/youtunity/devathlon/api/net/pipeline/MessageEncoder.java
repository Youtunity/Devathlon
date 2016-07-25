package net.youtunity.devathlon.api.net.pipeline;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import net.youtunity.devathlon.api.net.NetworkBase;
import net.youtunity.devathlon.api.net.message.Message;

import java.util.List;

/**
 * Created by thecrealm on 23.07.16.
 */
public class MessageEncoder extends MessageToMessageEncoder<Message> {

    private NetworkBase base;

    public MessageEncoder(NetworkBase base) {
        this.base = base;
    }

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Message message, List<Object> out) throws Exception {

        ByteBuf byteBuf = Unpooled.buffer();

        int id = base.getMessageRegistry().lookupId(message.getClass());
        System.out.println("OUT: " + message.getClass().getName());

        byteBuf.writeInt(id);
        message.encode(byteBuf);
        out.add(byteBuf);
    }
}
