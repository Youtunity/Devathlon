package net.youtunity.devathlon.spell.spells.common;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.youtunity.devathlon.DevathlonPlugin;
import net.youtunity.devathlon.service.ServiceRegistry;
import net.youtunity.devathlon.spell.Spell;
import net.youtunity.devathlon.spell.SpellContext;
import net.youtunity.devathlon.spell.SpellMeta;
import net.youtunity.devathlon.user.User;
import net.youtunity.devathlon.user.UserService;
import net.youtunity.devathlon.utils.Cuboid;
import net.youtunity.devathlon.utils.SchedulerUtils;
import org.bukkit.Bukkit;
import org.bukkit.EntityEffect;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

/**
 * Created by thecrealm on 17.07.16.
 */
@SpellMeta(name = "TripJinx", cooldown = 15, material = Material.ANVIL)
public class TripJinxSpell implements Spell, Listener {

    // magische Stolperfalle
    private Map<User, Cuboid> activeTraps = Maps.newHashMap();
    private DevathlonPlugin plugin;

    @Override
    public void execute(SpellContext context) {
        this.plugin = context.getPlugin();


    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        Iterator<Map.Entry<User, Cuboid>> it = activeTraps.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<User, Cuboid> next = it.next();
            if(next.getValue().intersects(event.getTo())) {
                it.remove();
                action(next.getKey(), ServiceRegistry.lookupService(UserService.class).find(event.getPlayer()).get());
            }
        }
    }

    private void action(User damager, User damaged) {
        SchedulerUtils.runTaskTimerRuns(plugin, () -> damaged.getPlayer().playEffect(EntityEffect.HURT), 0L, 1L, 40);

        EntityDamageByEntityEvent entityDamageByEntityEvent = new EntityDamageByEntityEvent(
                damager.getPlayer(), damaged.getPlayer(), EntityDamageEvent.DamageCause.CUSTOM, 2D
        );

        Bukkit.getPluginManager().callEvent(entityDamageByEntityEvent);
        if(!entityDamageByEntityEvent.isCancelled()) {
            damaged.getPlayer().damage(2D);
        }
    }
}
