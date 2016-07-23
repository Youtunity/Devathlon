package net.youtunity.devathlon.api.net;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import net.youtunity.devathlon.api.net.message.Message;
import net.youtunity.devathlon.api.net.message.MessageRegistry;
import net.youtunity.devathlon.api.net.pipeline.MessageDecoder;
import net.youtunity.devathlon.api.net.pipeline.MessageEncoder;
import net.youtunity.devathlon.api.net.pipeline.MessageHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by thecrealm on 23.07.16.
 */
public class NettyServer implements NetworkBase {

    private final MessageRegistry registry = new MessageRegistry();
    private final List<MessageHandler> handlers = new ArrayList<>();

    /**
     *
     * @param port The port to bind on.
     */
    public void bind(int port) {

        prepareBootstrap().bind(port).addListener(future -> {
            if(future.isSuccess()) {
                System.out.println("successfully bound server!");
            } else {
                System.out.println("Failed to bind port on '" + port + "'");
            }
        });
    }

    @Override
    public MessageRegistry getMessageRegistry() {
        return this.registry;
    }

    private ServerBootstrap prepareBootstrap() {

        return new ServerBootstrap()
                .group(new NioEventLoopGroup(), new NioEventLoopGroup())
                .childHandler(new ChannelInitializer<SocketChannel>() {

                    @Override
                    public void initChannel(SocketChannel channel) throws Exception {
                        ChannelPipeline pipe = channel.pipeline();

                        pipe.addLast(new MessageEncoder(NettyServer.this));
                        pipe.addLast(new MessageDecoder(NettyServer.this));

                        MessageHandler newHandler = new MessageHandler(NettyServer.this);
                        pipe.addLast(newHandler);

                        newHandler.setObserver(new MessageHandler.HandlerObserver() {

                            @Override
                            public void onActive(ChannelHandlerContext ctx) {

                                System.out.println("Incomming connection, adding ' " + ctx.name() + "'");

                                synchronized (handlers) {
                                    handlers.add(newHandler);
                                }
                            }

                            @Override
                            public void onInactive(ChannelHandlerContext ctx) {

                                System.out.println("Connection become inactive, removing ' " + ctx.name() + "'");

                                synchronized (handlers) {
                                    handlers.remove(newHandler);
                                }
                            }

                            @Override
                            public void onMessage(ChannelHandlerContext ctx, Message message) {
                                // Do nothing
                            }
                        });
                    }
                });
    }
}
