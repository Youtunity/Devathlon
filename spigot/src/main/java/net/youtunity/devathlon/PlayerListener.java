package net.youtunity.devathlon;

import net.youtunity.devathlon.api.protocol.info.ServerOnlinePlayersUpdate;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * Created by thecrealm on 25.07.16.
 */
public class PlayerListener implements Listener {

    private DevathlonPlugin plugin;

    public PlayerListener(DevathlonPlugin plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        int players = Bukkit.getOnlinePlayers().size();
        plugin.getClient().sendMessage(new ServerOnlinePlayersUpdate(plugin.getServerName(), players));
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        int players = Bukkit.getOnlinePlayers().size() -1;
        plugin.getClient().sendMessage(new ServerOnlinePlayersUpdate(plugin.getServerName(), players));
    }
}
