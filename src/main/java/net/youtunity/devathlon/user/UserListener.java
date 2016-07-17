package net.youtunity.devathlon.user;

import net.youtunity.devathlon.DevathlonPlugin;
import net.youtunity.devathlon.service.ServiceRegistry;
import net.youtunity.devathlon.spell.Spell;
import net.youtunity.devathlon.spell.SpellExecuter;
import net.youtunity.devathlon.state.IngameState;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.Optional;

/**
 * Created by thecrealm on 16.07.16.
 */
public class UserListener implements Listener {

    private DevathlonPlugin plugin;

    public UserListener(DevathlonPlugin plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        event.getPlayer().setGameMode(GameMode.SURVIVAL);
        event.getPlayer().setFoodLevel(20);
        event.getPlayer().setHealth(event.getPlayer().getMaxHealth());
    }

    @EventHandler
    public void onFood(FoodLevelChangeEvent event) {
        event.setCancelled(true);
    }
}
