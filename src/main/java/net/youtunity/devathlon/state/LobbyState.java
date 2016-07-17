package net.youtunity.devathlon.state;

import net.youtunity.devathlon.DevathlonPlugin;
import net.youtunity.devathlon.kit.Kit;
import net.youtunity.devathlon.party.Party;
import net.youtunity.devathlon.party.PartyService;
import net.youtunity.devathlon.service.ServiceRegistry;
import net.youtunity.devathlon.user.User;
import net.youtunity.devathlon.user.UserService;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Optional;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by thecrealm on 16.07.16.
 */
public class LobbyState extends State implements Listener {

    private DevathlonPlugin plugin;
    private UserService userService;
    private PartyService partyService;
    private BossBar bossBar;
    private AtomicInteger timer = new AtomicInteger(60);

    public LobbyState(DevathlonPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void onEnter() {
        this.userService = ServiceRegistry.lookupService(UserService.class);
        this.partyService = ServiceRegistry.lookupService(PartyService.class);
        Bukkit.getPluginManager().registerEvents(this, plugin);

        this.bossBar = Bukkit.createBossBar("Starts in: ", BarColor.BLUE, BarStyle.SOLID);

        new BukkitRunnable() {
            @Override
            public void run() {

                bossBar.setTitle("Starts in: " + timer.decrementAndGet());
                double percentage = timer.get() * 1D / 60D;
                bossBar.setProgress(percentage);

                if(timer.get() <= 0) {
                    this.cancel();
                    plugin.nextGamestate();
                }

            }
        }.runTaskTimer(plugin, 0L, 20L);

        for (Player player : Bukkit.getOnlinePlayers()) {
            player.getInventory().clear();
            player.teleport(plugin.getDevathlonConfig().getSpawn());
            bossBar.addPlayer(player);
        }

        this.bossBar.setVisible(true);
    }

    @Override
    public void onQuit() {
        this.bossBar.setVisible(false);
        HandlerList.unregisterAll(this);

        Random random = new Random();
        for (User user : userService.getUsers()) {
            Optional<Party> party = partyService.find(user);

            if(!party.isPresent()) {
                Party randomParty = partyService.getParties().get(random.nextInt(partyService.getParties().size()));
                randomParty.join(user);
                party = Optional.of(randomParty);
            }

            if(user.getKit() == null) {
                Kit kit = party.get().getAvailableKits().get(random.nextInt(party.get().getAvailableKits().size()));
                user.assignKit(kit);
            }

            user.getPlayer().sendMessage("You play as '" + user.getKit().getName() + "' for Party '" + party.get().getDisplayName() + "'");
        }
    }

    @EventHandler
    public void onDmg(EntityDamageByEntityEvent entityDamageByEntityEvent) {
        entityDamageByEntityEvent.setCancelled(true);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        this.bossBar.addPlayer(event.getPlayer());
        event.getPlayer().getInventory().clear();
        event.getPlayer().teleport(plugin.getDevathlonConfig().getSpawn());
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {

        User user = userService.find(event.getPlayer()).get();

        for (Party party : partyService.getParties()) {
            if (party.getLobbyCuboid().intersects(event.getTo())) {
                Optional<Party> curr = partyService.find(user);

                if(curr.isPresent()) {
                    if(party.equals(curr.get())) {
                        //already in
                    } else {
                        curr.get().left(user);
                        party.join(user);
                    }
                } else {
                    party.join(user);
                }
            }
        }
    }
}
