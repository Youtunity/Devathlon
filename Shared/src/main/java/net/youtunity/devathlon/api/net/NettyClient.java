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
import net.youtunity.devathlon.api.net.util.ReconnectListener;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.function.Consumer;

/**
 * Created by thecrealm on 23.07.16.
 */
public class NettyClient implements NetworkBase, Transport {

    private String host;
    private int port;

    private MessageRegistry registry = new MessageRegistry();
    private Channel channel;
    private MessageHandler handler;

    private List<Message> beforeActive = new ArrayList<>();

    private ReconnectListener reconnectListener;
    private ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(4);

    public NettyClient(String host, int port, boolean reconnect) {
        this.host = host;
        this.port = port;

        this.reconnectListener = new ReconnectListener(this, 10, executorService);
        this.reconnectListener.setReconnecting(reconnect);
    }

    public void connect() {

        Bootstrap bootstrap = prepareBootstrap();
        ChannelFuture channelFuture = bootstrap.connect();

        channelFuture.addListener(future -> {
            if(!future.isSuccess()) {
                reconnectListener.scheduleReconnect();
                System.out.println("Failed to connect, reconnecting..");
                return;
            }

            NettyClient.this.channel = channelFuture.channel();
            System.out.println("Client connected!");
            beforeActive.forEach(this::sendMessage);
            channel.closeFuture().addListener(reconnectListener);

        });
    }

    public Channel getChannel() {
        return channel;
    }

    public MessageHandler getHandler() {
        return handler;
    }

    @Override
    public MessageRegistry getMessageRegistry() {
        return this.registry;
    }

    @Override
    public void sendMessage(Message message) {

        if(channel != null && channel.isActive()) {
            channel.writeAndFlush(message);
        } else {
            beforeActive.add(message);
        }
    }

    @Override
    public boolean isActive() {
        return channel != null && channel.isActive();
    }

    private Bootstrap prepareBootstrap() {

        return new Bootstrap()
                .group(new NioEventLoopGroup())
                .channel(NioSocketChannel.class)
                .remoteAddress(new InetSocketAddress(host, port))
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
                    }
                });
    }
}
