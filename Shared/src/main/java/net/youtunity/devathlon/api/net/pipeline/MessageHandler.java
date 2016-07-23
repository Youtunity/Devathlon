package net.youtunity.devathlon.api.net.pipeline;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import net.youtunity.devathlon.api.net.NetworkBase;
import net.youtunity.devathlon.api.net.message.Message;

/**
 * Created by thecrealm on 23.07.16.
 */
public class MessageHandler extends SimpleChannelInboundHandler<Message> {

    private NetworkBase base;
    private HandlerObserver observer;

    public MessageHandler(NetworkBase base) {
        this.base = base;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        observer.onActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        observer.onInactive(ctx);
    }

    @Override
    protected void messageReceived(ChannelHandlerContext ctx, Message message) throws Exception {
        observer.onMessage(ctx, message);

        //handle message
        base.getMessageRegistry().lookupHandler(message.getClass()).handle(ctx.channel(), message);
    }

    /**
     *
     * @param observer
     */
    public void setObserver(HandlerObserver observer) {
        this.observer = observer;
    }


    public interface HandlerObserver {

        void onActive(ChannelHandlerContext ctx);

        void onInactive(ChannelHandlerContext ctx);

        void onMessage(ChannelHandlerContext ctx, Message message);
    }
}
