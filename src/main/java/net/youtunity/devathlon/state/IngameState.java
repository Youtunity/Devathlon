package net.youtunity.devathlon.state;

import net.youtunity.devathlon.DevathlonPlugin;
import net.youtunity.devathlon.kit.Kit;
import net.youtunity.devathlon.party.Party;
import net.youtunity.devathlon.party.PartyService;
import net.youtunity.devathlon.service.ServiceRegistry;
import net.youtunity.devathlon.spell.Spell;
import net.youtunity.devathlon.spell.SpellExecuter;
import net.youtunity.devathlon.user.User;
import net.youtunity.devathlon.user.UserService;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
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
            user.getKit().giveItems(user);
            user.getPlayer().getInventory().setHeldItemSlot(8);
            user.getPlayer().sendMessage("Teleporting.. ");
            user.getPlayer().teleport(party.get().getSpawn());

            user.getPlayer().getInventory().setChestplate(new ItemStack(Material.CHAINMAIL_CHESTPLATE));
            user.getPlayer().getInventory().setItem(8, new ItemStack(Material.WOOD_SWORD));

        }

        for (Party party : partyService.getParties()) {
            party.getEgg().getWorld().getBlockAt(party.getEgg()).setType(Material.DRAGON_EGG);
        }

        // dont drop items
        partyService.getParties().get(0).getSpawn().getWorld().setGameRuleValue("keepInventory", "true");

        this.bossBar.setVisible(true);

        new BukkitRunnable() {
            @Override
            public void run() {

                bossBar.setTitle(formatTime(time.decrementAndGet()));
                double percentage = time.get() * 1D / maxTime * 1D;
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
        return ChatColor.BLUE + "" + minute + ":" + ((seconds < 10) ? "0" + seconds : seconds);
    }

    @EventHandler
    public void onJoin(PlayerLoginEvent event) {
        event.disallow(PlayerLoginEvent.Result.KICK_OTHER, "INGAME");
    }

    @Override
    public void onQuit() {
        this.bossBar.setVisible(false);
        HandlerList.unregisterAll(this);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {

        if(event.getAction() == Action.LEFT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (event.getClickedBlock().getType().equals(Material.DRAGON_EGG)) {

                User user = userService.find(event.getPlayer()).get();
                Party own = partyService.find(user).get();

                if(event.getClickedBlock().getLocation().equals(own.getEgg())) {
                    event.setCancelled(true);
                    event.getPlayer().sendMessage("Du kannst dein EI nicht zerstören");
                } else {
                    for (Party party : partyService.getParties()) {
                        event.setCancelled(true);
                        event.getClickedBlock().setType(Material.AIR);
                        if(event.getClickedBlock().getLocation().equals(party.getEgg())) {
                            Bukkit.broadcastMessage("Das Ei von Party '" + party.getDisplayName() + "' wurde abgebaut");
                            party.setDead();
                        }
                    }
                }


            }
        }
    }

    @EventHandler
    public void onDrag(InventoryDragEvent event) {
        event.setCancelled(true);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
    public void onBlock(BlockBreakEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onHotbar(PlayerItemHeldEvent event) {
        event.setCancelled(true);
        User user = ServiceRegistry.lookupService(UserService.class).find(event.getPlayer()).get();
        int spellIndex = event.getNewSlot();
        Optional<Spell> spell = user.getKit().getSpell(spellIndex);
        if(spell.isPresent()) {
             SpellExecuter.execute(user, spell.get());
        }
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent event) {
        Party party = partyService.find(event.getPlayer()).get();
        User user = userService.find(event.getPlayer()).get();

        if(party.isAlive()) {
            event.setRespawnLocation(partyService.find(event.getPlayer()).get().getSpawn());
        } else {
            event.getPlayer().sendMessage("Du bist ausgeschieden");
            party.left(user);
            user.getPlayer().kickPlayer("You are dead now, lel :P");

            if(party.getUsers().size() == 0) {
                Bukkit.broadcastMessage("Die Party '" + party.getDisplayName() + "' ist ausgeschieden.");
            }

            int count = 0;
            for (Party p : partyService.getParties()) {
                if(p.getUsers().size() > 0) {
                    count++;
                }
            }

            if(count <= 1) {
                Bukkit.broadcastMessage("Das Team '" +
                        partyService.getParties().stream()
                        .filter(b -> b.getUsers().size() >= 1)
                        .findFirst().get().getDisplayName()
                        + "' hat gewonnen!");
                plugin.nextGamestate();
            }
        }
    }
}
