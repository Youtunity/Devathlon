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
import io.netty.handler.timeout.IdleStateHandler;
import net.youtunity.devathlon.api.net.message.Message;
import net.youtunity.devathlon.api.net.message.MessageRegistry;
import net.youtunity.devathlon.api.net.pipeline.*;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

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

    public List<MessageHandler> getHandlers() {
        return handlers;
    }

    private ServerBootstrap prepareBootstrap() {

        return new ServerBootstrap()
                .group(new NioEventLoopGroup(), new NioEventLoopGroup())
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {

                    @Override
                    public void initChannel(SocketChannel channel) throws Exception {
                        ChannelPipeline pipe = channel.pipeline();

                        pipe.addLast(new IdleStateHandler(60, 0, 0));
                        pipe.addLast(new IdleHandler());

                        pipe.addLast(new LenghtDecoder());
                        pipe.addLast(new MessageDecoder(NettyServer.this));
                        pipe.addLast(new LenghtEncoder());
                        pipe.addLast(new MessageEncoder(NettyServer.this));

                        MessageHandler newHandler = new MessageHandler(NettyServer.this);
                        pipe.addLast(newHandler);

                        handlers.add(newHandler);

                        channel.closeFuture().addListener(future -> {
                           handlers.remove(newHandler);
                        });
                    }
                });
    }
}
