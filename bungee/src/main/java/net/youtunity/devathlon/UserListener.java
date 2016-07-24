package net.youtunity.devathlon;

import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.LoginEvent;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.event.ServerConnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.youtunity.devathlon.api.ServerStatus;
import net.youtunity.devathlon.api.protocol.control.ServerStartRequest;
import net.youtunity.devathlon.server.ServerContext;
import net.youtunity.devathlon.server.ServerStatusChangedEvent;

import java.util.*;

/**
 * Created by thecrealm on 23.07.16.
 */
public class UserListener implements Listener {

    private DevathlonPlugin plugin;
    private List<UUID> join = new ArrayList<>();
    private Map<String, List<ProxiedPlayer>> players = new HashMap<>();

    public UserListener(DevathlonPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onStateChanged(ServerStatusChangedEvent e) {

        if (e.getNewStatus() == ServerStatus.RUNNING) {
            if (players.containsKey(e.getContext().getServer())) {
                players.get(e.getContext().getServer()).forEach(proxiedPlayer -> proxiedPlayer.connect(e.getContext().getServerInfo()));
            }
        }
    }

    @EventHandler
    public void onLogin(LoginEvent event) {
        join.add(event.getConnection().getUniqueId());
    }

    @EventHandler
    public void onHandshake(ProxyPingEvent event) {

        String host = event.getConnection().getVirtualHost().getHostString();

        ServerContext context = plugin.getServerRegistry().lookupContext(host);
        System.out.println(context.getMotd());

        event.getResponse().setDescriptionComponent(new TextComponent(context.getMotd()));
    }

    @EventHandler
    public void onConnect(ServerConnectEvent event) {

        if (!join.contains(event.getPlayer().getPendingConnection().getUniqueId())) {
            return;
        }

        join.remove(event.getPlayer().getPendingConnection().getUniqueId());
        String host = event.getPlayer().getPendingConnection().getVirtualHost().getHostString();
        ServerContext context = plugin.getServerRegistry().lookupContext(host);

        if (context.getServerStatus() == ServerStatus.RUNNING) {
            event.setTarget(context.getServerInfo());
            event.getPlayer().sendMessage(TextComponent.fromLegacyText("The requested server is online, Welcome!"));
        } else {
            event.getPlayer().sendMessage(TextComponent.fromLegacyText("The requested server is offline, starting up for you.."));
            plugin.getClient().sendMessage(new ServerStartRequest(host));

            if (!players.containsKey(host)) {
                players.put(host, new ArrayList<>());
            }

            players.get(host).add(event.getPlayer());
        }
    }
}
