package net.youtunity.devathlon.api.net.pipeline;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import net.youtunity.devathlon.api.net.NetworkBase;
import net.youtunity.devathlon.api.net.message.Message;

import java.util.function.Consumer;

/**
 * Created by thecrealm on 23.07.16.
 */
public class MessageHandler extends SimpleChannelInboundHandler<Message> {

    private NetworkBase base;
    private HandlerObserver observer;
    private ChannelHandlerContext ctx;

    public MessageHandler(NetworkBase base) {
        this.base = base;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        this.ctx = ctx;
        callObserver(handlerObserver -> handlerObserver.onActive(this));
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        callObserver(handlerObserver -> handlerObserver.onInactive(this));
    }

    @Override
    protected void messageReceived(ChannelHandlerContext ctx, Message message) throws Exception {
        callObserver(handlerObserver -> handlerObserver.onMessage(this, message));

        //handle message
        net.youtunity.devathlon.api.net.message.MessageHandler<Message> handler = base.getMessageRegistry().lookupHandler(message.getClass());

        if(handler != null) {
            handler.handle(ctx.channel(), message);
        }
    }

    private void callObserver(Consumer<HandlerObserver> consumer) {
        if(this.observer != null) {
            consumer.accept(this.observer);
        }
    }

    /**
     *
     * @param observer
     */
    public void setObserver(HandlerObserver observer) {
        this.observer = observer;
    }

    public ChannelHandlerContext getChannelHandlerContext() {
        return ctx;
    }

    public interface HandlerObserver {

        void onActive(MessageHandler handler);

        void onInactive(MessageHandler handler);

        void onMessage(MessageHandler handler, Message message);
    }
}
