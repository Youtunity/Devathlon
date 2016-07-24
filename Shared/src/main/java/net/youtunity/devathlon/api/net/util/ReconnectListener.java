package net.youtunity.devathlon.api.net.util;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import net.youtunity.devathlon.api.net.NettyClient;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by thecrealm on 24.07.16.
 */
public class ReconnectListener implements ChannelFutureListener {

    private AtomicBoolean reconnecting = new AtomicBoolean(false);
    private NettyClient client;
    private int reconnectInterval;
    private ScheduledExecutorService executorService;

    public ReconnectListener(NettyClient client, int reconnectInterval, ScheduledExecutorService executorService) {
        this.client = client;
        this.reconnectInterval = reconnectInterval;
        this.executorService = executorService;
    }

    public void setReconnecting(boolean reconnecting) {
        this.reconnecting.set(reconnecting);
    }

    @Override
    public void operationComplete(ChannelFuture channelFuture) throws Exception {
        final Channel channel = channelFuture.channel();
        channel.disconnect();

    }

    public void scheduleReconnect() {

        if (reconnecting.get()) {
            executorService.schedule(() -> client.connect(), reconnectInterval, TimeUnit.SECONDS);
        }
    }
}
