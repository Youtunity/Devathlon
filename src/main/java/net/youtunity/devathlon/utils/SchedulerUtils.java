package net.youtunity.devathlon.utils;

import net.youtunity.devathlon.DevathlonPlugin;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by thecrealm on 16.07.16.
 */
public class SchedulerUtils {

    public static void runTaskTimerRuns(DevathlonPlugin plugin, Runnable runnable, long delay, long period, int runs) {

        final AtomicInteger runned = new AtomicInteger(0);
        new BukkitRunnable() {
            @Override
            public void run() {

                if(runned.incrementAndGet() >= runs) {
                    this.cancel();
                }

                runnable.run();
            }
        }.runTaskTimer(plugin, delay, period);
    }
}
