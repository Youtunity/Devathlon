package net.youtunity.devathlon;

import net.youtunity.devathlon.api.net.NettyClient;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

/**
 * Created by thecrealm on 24.07.16.
 */
public class DevathlonPlugin extends JavaPlugin {

    private NettyClient client;

    @Override
    public void onEnable() {

        this.client = new NettyClient();
        this.client.setObserver(new NettyClient.ClientObserver() {
            @Override
            public void onReady() {
                getLogger().info("Successfully connected to daemon");
            }

            @Override
            public void onError(Throwable throwable) {
                getLogger().log(Level.SEVERE, "Failed to connect to daemon", throwable);
            }
        });

        this.client.connect("127.0.0.1", 4040);
    }

    public NettyClient getClient() {
        return client;
    }
}
