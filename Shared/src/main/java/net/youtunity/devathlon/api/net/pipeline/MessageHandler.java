package net.youtunity.devathlon.api.net.pipeline;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import net.youtunity.devathlon.api.net.NetworkBase;
import net.youtunity.devathlon.api.net.Transport;
import net.youtunity.devathlon.api.net.message.Message;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by thecrealm on 23.07.16.
 */
public class MessageHandler extends SimpleChannelInboundHandler<Message> implements Transport {

    private NetworkBase base;
    private ChannelHandlerContext ctx;
    private List<Message> beforeActive = new ArrayList<>();

    public MessageHandler(NetworkBase base) {
        this.base = base;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        this.ctx = ctx;

        // Send all protocol which was added before the connection was established
        beforeActive.forEach(this::sendMessage);
    }

    @Override
    protected void messageReceived(ChannelHandlerContext ctx, Message message) throws Exception {

        //handle message
        net.youtunity.devathlon.api.net.message.MessageHandler<Message> handler = base.getMessageRegistry().lookupHandler(message.getClass());

        if (handler != null) {
            handler.handle(this, message);
        }
    }

    @Override
    public Channel getChannel() {
        if (ctx != null) {
            return ctx.channel();
        }

        return null;
    }

    @Override
    public boolean isActive() {
        return getChannel() != null && getChannel().isActive();
    }

    @Override
    public void sendMessage(Message message) {

        if (isActive()) {
            getChannel().writeAndFlush(message);
        } else {
            beforeActive.add(message);
        }
    }
}
