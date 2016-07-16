package net.youtunity.devathlon.user;

import com.google.common.collect.Lists;
import net.youtunity.devathlon.DevathlonPlugin;
import net.youtunity.devathlon.service.Initializable;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Created by thecrealm on 16.07.16.
 */
public class SimpleUserService implements UserService, Initializable, Listener {

    private DevathlonPlugin plugin;
    private List<User> users = Lists.newArrayList();

    public SimpleUserService(DevathlonPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void init() {
        Bukkit.getOnlinePlayers().forEach(o -> users.add(new User(o)));
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @Override
    public List<User> getUsers() {
        return Collections.unmodifiableList(this.users);
    }

    @Override
    public Optional<User> find(Player player) {

        return users.stream()
                .filter(user -> user.getPlayer().equals(player))
                .findFirst();
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
    public void onJoin(PlayerJoinEvent event) {
        User user = new User(event.getPlayer());
        users.add(user);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onQuit(PlayerQuitEvent event) {
        Optional<User> user = find(event.getPlayer());

        if(user.isPresent()) {
            users.remove(user.get());
        }
    }
}
