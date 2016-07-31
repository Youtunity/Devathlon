package net.youtunity.devathlon.util;

import com.quartercode.quarterbukkit.api.shape.Shape;
import net.youtunity.devathlon.DevathlonPlugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

/**
 * Created by thecrealm on 30.07.16.
 */
public abstract class ShapeListener implements Listener {

    private DevathlonPlugin plugin;
    private Shape shape;

    public ShapeListener(DevathlonPlugin plugin, Shape shape) {
        this.plugin = plugin;
        this.shape = shape;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    public Shape getShape() {
        return shape;
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        if(shape.intersects(event.getTo())) {
            onIntersects(event.getPlayer());
        }
    }

    public void stopListening() {
        HandlerList.unregisterAll(this);
    }

    protected abstract void onIntersects(Player player);
}
