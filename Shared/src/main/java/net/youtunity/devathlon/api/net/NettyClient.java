package net.youtunity.devathlon.api.net;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import net.youtunity.devathlon.api.net.message.Message;
import net.youtunity.devathlon.api.net.message.MessageRegistry;
import net.youtunity.devathlon.api.net.pipeline.*;

import java.util.function.Consumer;

/**
 * Created by thecrealm on 23.07.16.
 */
public class NettyClient implements NetworkBase {

    private MessageRegistry registry = new MessageRegistry();
    private ClientObserver observer;
    private MessageHandler handler;

    public void connect(String host, int port) {

        prepareBootstrap().connect(host, port).addListener(future -> {
            if(future.isSuccess()) {
                System.out.println("Successfully connected!");
            } else {
                System.out.println("Failed to connect to server!");
                callObserver(clientObserver -> clientObserver.onError(future.cause()));
            }
        });
    }

    public MessageHandler getHandler() {
        return handler;
    }

    @Override
    public MessageRegistry getMessageRegistry() {
        return this.registry;
    }

    private Bootstrap prepareBootstrap() {

        return new Bootstrap()
                .group(new NioEventLoopGroup())
                .channel(NioSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .handler(new ChannelInitializer<SocketChannel>() {

                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        ChannelPipeline pipe = socketChannel.pipeline();
                        pipe.addLast(new LenghtDecoder());
                        pipe.addLast(new MessageDecoder(NettyClient.this));
                        pipe.addLast(new LenghtEncoder());
                        pipe.addLast(new MessageEncoder(NettyClient.this));

                        NettyClient.this.handler = new MessageHandler(NettyClient.this);
                        pipe.addLast(handler);

                        handler.setObserver(new MessageHandler.HandlerObserver() {

                            @Override
                            public void onActive(MessageHandler handler) {

                                //You can now send messages between the netty transport endpoints
                                NettyClient.this.handler = handler;
                                callObserver(ClientObserver::onReady);
                            }

                            @Override
                            public void onInactive(MessageHandler handler) {

                            }

                            @Override
                            public void onMessage(MessageHandler handler, Message message) {

                            }
                        });
                    }
                });
    }

    public void setObserver(ClientObserver observer) {
        this.observer = observer;
    }

    private void callObserver(Consumer<ClientObserver> consumer) {
        if(this.observer != null) {
            consumer.accept(this.observer);
        }
    }


    public static interface ClientObserver {

        public void onReady();

        public void onError(Throwable throwable);

    }
}
