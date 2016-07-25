package net.youtunity.devathlon;

import net.youtunity.devathlon.api.net.NettyClient;
import net.youtunity.devathlon.api.protocol.control.ServerStartRequest;
import net.youtunity.devathlon.api.protocol.control.ServerStopRequest;
import net.youtunity.devathlon.api.protocol.info.ServerInformationRequest;
import net.youtunity.devathlon.api.protocol.info.ServerInformationResponse;
import net.youtunity.devathlon.api.protocol.info.ServerOnlinePlayersUpdate;
import net.youtunity.devathlon.api.protocol.info.ServerStatusUpdate;
import net.youtunity.devathlon.api.protocol.interaction.AvailableTemplatesRequest;
import net.youtunity.devathlon.api.protocol.interaction.AvailableTemplatesResponse;
import net.youtunity.devathlon.api.protocol.interaction.ChangeTemplateRequest;
import net.youtunity.devathlon.api.protocol.interaction.UpdateMOTDRequest;
import net.youtunity.devathlon.net.AvailableTemplatesResponseHandler;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by thecrealm on 24.07.16.
 */
public class DevathlonPlugin extends JavaPlugin {

    private String server;
    private NettyClient client;
    private List<String> availableTemplates = new ArrayList<>();

    @Override
    public void onEnable() {

        this.server = System.getProperty("server");

        this.client = new NettyClient("127.0.0.1", 4040, true);

        //control
        this.client.getMessageRegistry().register(ServerStartRequest.class, null);
        this.client.getMessageRegistry().register(ServerStopRequest.class, null);

        //info
        this.client.getMessageRegistry().register(ChangeTemplateRequest.class, null);
        this.client.getMessageRegistry().register(ServerInformationRequest.class, null);
        this.client.getMessageRegistry().register(UpdateMOTDRequest.class, null);
        this.client.getMessageRegistry().register(ServerInformationResponse.class, null);
        this.client.getMessageRegistry().register(ServerStatusUpdate.class, null);
        this.client.getMessageRegistry().register(ServerOnlinePlayersUpdate.class, null);


        //interaction
        this.client.getMessageRegistry().register(AvailableTemplatesRequest.class, null);
        this.client.getMessageRegistry().register(AvailableTemplatesResponse.class, new AvailableTemplatesResponseHandler(this));

        this.client.connect();
        this.client.sendMessage(new AvailableTemplatesRequest());

        getCommand("menu").setExecutor(new MenuCommand(this));
        getCommand("motd").setExecutor(new MotdCommand(this));

        new PlayerListener(this);
    }

    public String getServerName() {
        return server;
    }

    public List<String> getAvailableTemplates() {
        return availableTemplates;
    }

    public NettyClient getClient() {
        return client;
    }
}
