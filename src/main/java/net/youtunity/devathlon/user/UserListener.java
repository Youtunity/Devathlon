package net.youtunity.devathlon.user;

import net.youtunity.devathlon.DevathlonPlugin;
import net.youtunity.devathlon.spell.Spell;
import net.youtunity.devathlon.spell.SpellExecuter;
import net.youtunity.devathlon.state.IngameState;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerMoveEvent;

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
   public void onHotbar(PlayerItemHeldEvent event) {
        if(plugin.isGameState(IngameState.class)) {
            event.setCancelled(true);
            User user = plugin.findUser(event.getPlayer());
            int spellIndex = event.getNewSlot();
            Spell spell = user.getKit().createSpell(spellIndex);
            SpellExecuter.execute(user, spell);
        }
   }

    public void onInteract(PlayerInteractEvent event) {
        if(event.getAction() != Action.PHYSICAL) {
            User user = plugin.findUser(event.getPlayer());
            if(user == null) {
                return;
            }

            SpellExecuter.execute(user, null);

        }
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {

    }


}
