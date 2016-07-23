package net.youtunity.devathlon.api.net;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.ServerSocketChannel;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import net.youtunity.devathlon.api.net.message.Message;
import net.youtunity.devathlon.api.net.message.MessageRegistry;
import net.youtunity.devathlon.api.net.pipeline.MessageDecoder;
import net.youtunity.devathlon.api.net.pipeline.MessageEncoder;
import net.youtunity.devathlon.api.net.pipeline.MessageHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Created by thecrealm on 23.07.16.
 */
public class NettyServer implements NetworkBase {

    private final MessageRegistry registry = new MessageRegistry();
    private ServerObserver observer;
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

    public List<MessageHandler> getHandlers() {
        return handlers;
    }

    private ServerBootstrap prepareBootstrap() {

        return new ServerBootstrap()
                .group(new NioEventLoopGroup(), new NioEventLoopGroup())
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .channel(NioServerSocketChannel.class)
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
                            public void onActive(MessageHandler handler) {

                                System.out.println("Incomming connection, adding '" + handler.getChannelHandlerContext().channel().remoteAddress() + "'");

                                synchronized (handlers) {
                                    handlers.add(newHandler);
                                }

                                callObserver(serverObserver -> serverObserver.onActiveHandler(handler));
                            }

                            @Override
                            public void onInactive(MessageHandler handler) {

                                System.out.println("Connection become inactive, removing '" + handler.getChannelHandlerContext().channel().remoteAddress() + "'");

                                synchronized (handlers) {
                                    handlers.remove(newHandler);
                                }

                                callObserver(serverObserver -> serverObserver.onInactiveHandler(handler));
                            }

                            @Override
                            public void onMessage(MessageHandler handler, Message message) {
                                // Do nothing
                            }
                        });
                    }
                });
    }

    public void setObserver(NettyServer.ServerObserver observer) {
        this.observer = observer;
    }

    private void callObserver(Consumer<NettyServer.ServerObserver> consumer) {
        if(this.observer != null) {
            consumer.accept(this.observer);
        }
    }


    public static interface ServerObserver {

        void onActiveHandler(MessageHandler handler);

        void onInactiveHandler(MessageHandler handler);
    }
}
