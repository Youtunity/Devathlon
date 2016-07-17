package net.youtunity.devathlon.state;

import net.youtunity.devathlon.DevathlonPlugin;
import net.youtunity.devathlon.kit.Kit;
import net.youtunity.devathlon.party.Party;
import net.youtunity.devathlon.party.PartyService;
import net.youtunity.devathlon.service.ServiceRegistry;
import net.youtunity.devathlon.user.User;
import net.youtunity.devathlon.user.UserService;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.time.Period;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by thecrealm on 16.07.16.
 */
public class IngameState extends State implements Listener {

    BossBar bossBar = null;

    private int maxTime = 300;
    private AtomicInteger time = new AtomicInteger(maxTime);

    private DevathlonPlugin plugin;
    private UserService userService;
    private PartyService partyService;

    public IngameState(DevathlonPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void onEnter() {

        Bukkit.getPluginManager().registerEvents(this, plugin);

        this.bossBar = Bukkit.createBossBar("Time", BarColor.BLUE, BarStyle.SOLID, BarFlag.DARKEN_SKY);

        this.userService = ServiceRegistry.lookupService(UserService.class);
        this.partyService = ServiceRegistry.lookupService(PartyService.class);

        for (User user : userService.getUsers()) {
            this.bossBar.addPlayer(user.getPlayer());

            Optional<Party> party = partyService.find(user);

            Kit kit = party.get().getAvailableKits().get(0);
            System.out.println(kit.getName());
            user.assignKit(kit);
            user.getKit().giveItems(user);
            user.getPlayer().getInventory().setHeldItemSlot(8);

            user.getPlayer().sendMessage("Teleporting.. ");
            user.getPlayer().teleport(party.get().getSpawn());

        }

        this.bossBar.setVisible(true);


        new BukkitRunnable() {
            @Override
            public void run() {

                bossBar.setTitle(formatTime(time.decrementAndGet()));
                double percentage = time.get() * 1D / maxTime * 1D;
                System.out.println(percentage);
                bossBar.setProgress(percentage);

                if(time.get() <= 0) {
                    this.cancel();
                    plugin.nextGamestate();
                }
            }
        }.runTaskTimer(plugin, 0L, 20L);
    }

    private String formatTime(long seconds) {
        long minute = TimeUnit.SECONDS.toMinutes(seconds);
        seconds = seconds - minute * 60;
        return minute + ":" + seconds;
    }

    @Override
    public void onQuit() {
        this.bossBar.setVisible(false);
        HandlerList.unregisterAll(this);
    }

    @EventHandler
    public void onDeath(PlayerRespawnEvent event) {
        event.setRespawnLocation(partyService.find(event.getPlayer()).get().getSpawn());
    }
}
