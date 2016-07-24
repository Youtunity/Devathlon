package net.youtunity.devathlon;

import net.youtunity.devathlon.api.net.NettyClient;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by thecrealm on 24.07.16.
 */
public class DevathlonPlugin extends JavaPlugin {

    private NettyClient client;

    @Override
    public void onEnable() {

        this.client = new NettyClient("127.0.0.1", 4040, true);

        this.client.connect();
    }

    public NettyClient getClient() {
        return client;
    }
}
