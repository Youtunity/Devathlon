package net.youtunity.devathlon;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Boat;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.vehicle.VehicleExitEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by thecrealm on 31.07.16.
 */
public class BoatListener implements Listener {

    private DevathlonPlugin plugin;

    public BoatListener(DevathlonPlugin plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {

        if(event.getPlayer().getInventory().contains(Material.BOAT)) return;
        Block relative = event.getTo().clone().getBlock().getRelative(BlockFace.DOWN);
        if(relative.getType() == Material.WATER || relative.getType() == Material.STATIONARY_WATER) {
            if(!event.getPlayer().isInsideVehicle()) {
                Entity entity = event.getPlayer().getLocation().getWorld().spawnEntity(event.getPlayer().getLocation(), EntityType.BOAT);
                entity.setPassenger(event.getPlayer());
            }
        }
    }

    @EventHandler
    public void onVehicle(VehicleExitEvent event) {
        if(event.getVehicle() instanceof Boat) {
            event.getVehicle().remove();

            if(event.getExited() instanceof Player) {
                ((Player) event.getExited()).getInventory().addItem(new ItemStack(Material.BOAT, 1));
            }
        }
    }
}
