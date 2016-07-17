package net.youtunity.devathlon.spell.spells.common;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.youtunity.devathlon.DevathlonPlugin;
import net.youtunity.devathlon.party.Party;
import net.youtunity.devathlon.party.PartyService;
import net.youtunity.devathlon.service.ServiceRegistry;
import net.youtunity.devathlon.spell.Spell;
import net.youtunity.devathlon.spell.SpellContext;
import net.youtunity.devathlon.spell.SpellMeta;
import net.youtunity.devathlon.user.User;
import net.youtunity.devathlon.user.UserService;
import net.youtunity.devathlon.utils.Cuboid;
import net.youtunity.devathlon.utils.SchedulerUtils;
import org.bukkit.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.*;

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

        Location location = context.getInvoker().getPlayer().getLocation();

        Location min = location.add(-1.5, -2, -1.5);
        Location max = location.add(1.5, 2, 1.5);

        activeTraps.put(context.getInvoker(), new Cuboid(min, max));

        Random random = new Random();
        SchedulerUtils.runTaskTimerRuns(plugin, () -> {

            for (int i = 0; i < 30; i++) {
                double bla = random.nextDouble() * 3;
                double haha = random.nextDouble() * 3;
                min.getWorld().playEffect(min.clone().add(bla, random.nextDouble() * 2, haha), Effect.COLOURED_DUST, 4);
            }

        }, 0L, 10L, 8);

    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        Iterator<Map.Entry<User, Cuboid>> it = activeTraps.entrySet().iterator();
        User user = ServiceRegistry.lookupService(UserService.class).find(event.getPlayer()).get();
        while (it.hasNext()) {
            Map.Entry<User, Cuboid> next = it.next();

            Optional<Party> party = ServiceRegistry.lookupService(PartyService.class).find(next.getKey());
            if(party.isPresent()) {
               if(party.get().getUsers().contains(user)) {
                   continue;
               }
            } else {
                return;
            }

            if(next.getValue().intersects(event.getTo())) {
                it.remove();
                action(next.getKey(), user);
            }
        }
    }

    private void action(User damager, User damaged) {
        SchedulerUtils.runTaskTimerRuns(plugin, () -> damaged.getPlayer().playEffect(EntityEffect.HURT), 0L, 1L, 40);
        damaged.getPlayer().damage(6D);
        damaged.getPlayer().getWorld().playSound(damaged.getPlayer().getLocation(), Sound.BLOCK_END_GATEWAY_SPAWN, 1f, 1f);
        damaged.getPlayer().getWorld().playEffect(damaged.getPlayer().getLocation(), Effect.ANVIL_USE, 8);
    }
}
