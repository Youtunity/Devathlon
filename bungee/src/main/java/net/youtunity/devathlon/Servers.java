package net.youtunity.devathlon;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.net.InetSocketAddress;

/**
 * Created by thecrealm on 25.07.16.
 */
public class Servers {

    public static void addServer(String name, String host, int port) {
        ServerInfo serverInfo = ProxyServer.getInstance().constructServerInfo(name, new InetSocketAddress(host, port), "", false);
        ProxyServer.getInstance().getServers().put(name, serverInfo);
    }

    public static void removeServer(String name) {

        if (!ProxyServer.getInstance().getServers().containsKey(name)) {
            return;
        }

        for (final ProxiedPlayer player : ProxyServer.getInstance().getServers().get(name).getPlayers()) {
            player.disconnect(new ComponentBuilder("Server stopped, please rejoin in 20 seconds.").color(ChatColor.GOLD).create());
        }

        ProxyServer.getInstance().getServers().remove(name);
    }
}
