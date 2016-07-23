package net.youtunity.devathlon.api.net;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import net.youtunity.devathlon.api.net.message.Message;
import net.youtunity.devathlon.api.net.message.MessageRegistry;
import net.youtunity.devathlon.api.net.pipeline.MessageDecoder;
import net.youtunity.devathlon.api.net.pipeline.MessageEncoder;
import net.youtunity.devathlon.api.net.pipeline.MessageHandler;

/**
 * Created by thecrealm on 23.07.16.
 */
public class NettyClient implements NetworkBase {

    private MessageRegistry registry = new MessageRegistry();
    private MessageHandler handler;

    public void connect(String host, int port) {

        prepareBootstrap().connect(host, port).addListener(future -> {
            if(future.isSuccess()) {
                System.out.println("Successfully connected!");
            } else {
                System.out.println("Failed to connect to server!");
                onError(future.cause());
            }
        });
    }

    @Override
    public MessageRegistry getMessageRegistry() {
        return this.registry;
    }

    private Bootstrap prepareBootstrap() {

        return new Bootstrap()
                .group(new NioEventLoopGroup())
                .handler(new ChannelInitializer<SocketChannel>() {

                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        ChannelPipeline pipe = socketChannel.pipeline();
                        pipe.addLast(new MessageEncoder(NettyClient.this));
                        pipe.addLast(new MessageDecoder(NettyClient.this));

                        NettyClient.this.handler = new MessageHandler(NettyClient.this);
                        pipe.addLast(NettyClient.this.handler);

                        handler.setObserver(new MessageHandler.HandlerObserver() {

                            @Override
                            public void onActive(ChannelHandlerContext ctx) {

                                //You can now send messages between the netty transport endpoints
                                NettyClient.this.onReady();
                            }

                            @Override
                            public void onInactive(ChannelHandlerContext ctx) {

                            }

                            @Override
                            public void onMessage(ChannelHandlerContext ctx, Message message) {

                            }
                        });
                    }
                });
    }

    /**
     * Override this method to determine the connected state
     */
    public void onReady() {

    }

    public void onError(Throwable throwable) {

    }
}
