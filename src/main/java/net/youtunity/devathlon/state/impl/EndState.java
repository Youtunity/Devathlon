package net.youtunity.devathlon.state.impl;

import com.quartercode.quarterbukkit.api.scheduler.ScheduleTask;
import net.youtunity.devathlon.DevathlonPlugin;
import net.youtunity.devathlon.state.State;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Created by thecrealm on 30.07.16.
 */
public class EndState extends State {

    private DevathlonPlugin plugin;

    public EndState(DevathlonPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void onEnter() {

        new BukkitRunnable() {
            @Override
            public void run() {
                Bukkit.shutdown();
            }
        }.runTaskLater(plugin, 100L);
    }

    @Override
    public void onLeave() {

    }
}
