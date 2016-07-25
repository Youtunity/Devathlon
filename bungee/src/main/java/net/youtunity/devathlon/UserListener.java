package net.youtunity.devathlon;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ComponentBuilder;
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

    public UserListener(DevathlonPlugin plugin) {
        this.plugin = plugin;
    }


    @EventHandler
    public void onLogin(LoginEvent event) {

    }

    @EventHandler
    public void onHandshake(ProxyPingEvent event) {

        String host = event.getConnection().getVirtualHost().getHostString();
        ServerContext context = plugin.getServerRegistry().lookupContext(host);

        ChatColor chatColor = ChatColor.GRAY;
        if(context.getServerStatus() == ServerStatus.RUNNING) {
            chatColor = ChatColor.GREEN;
        } else if(context.getServerStatus() == ServerStatus.STARTING) {
            chatColor = ChatColor.YELLOW;
        } else if(context.getServerStatus() == ServerStatus.OFFLINE) {
            chatColor = ChatColor.RED;
        }

        event.getResponse().setDescriptionComponent(new ComponentBuilder(context.getMotd()).color(chatColor).create()[0]);
    }

    @EventHandler
    public void onConnect(ServerConnectEvent event) {

        String host = event.getPlayer().getPendingConnection().getVirtualHost().getHostString();
        ServerContext context = plugin.getServerRegistry().lookupContext(host);

        if (context.getServerStatus() == ServerStatus.RUNNING) {
            event.setTarget(ProxyServer.getInstance().getServerInfo(context.getServer()));
            event.getPlayer().sendMessage(TextComponent.fromLegacyText("The requested server is online, Welcome!"));
        } else {
            event.getPlayer().disconnect(new ComponentBuilder("The requested server will be created, join again in 20 seconds").color(ChatColor.GOLD).create());
            plugin.getClient().sendMessage(new ServerStartRequest(host));
        }
    }
}
