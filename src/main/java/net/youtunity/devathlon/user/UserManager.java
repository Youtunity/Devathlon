package net.youtunity.devathlon.user;

import net.youtunity.devathlon.DevathlonPlugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by thecrealm on 30.07.16.
 */
public class UserManager implements Listener {

    private DevathlonPlugin plugin;
    private List<User> users = new ArrayList<>();

    public UserManager(DevathlonPlugin plugin) {
        this.plugin = plugin;
    }

    public void init() {
        Bukkit.getPluginManager().registerEvents(this, plugin);
        Bukkit.getOnlinePlayers().forEach(this::add);
    }

    public User find(Player player) {

        for (User user : users) {
            if(player.equals(user.getPlayer())) {
                return user;
            }
        }

        return null;
    }

    private void add(Player player) {
        users.add(new User(player));
    }

    public void remove(User user) {
        users.remove(user);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        add(event.getPlayer());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        remove(find(event.getPlayer()));
    }
}
