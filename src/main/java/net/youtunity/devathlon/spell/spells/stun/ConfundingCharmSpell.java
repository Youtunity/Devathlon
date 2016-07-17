package net.youtunity.devathlon.spell.spells.stun;

import net.youtunity.devathlon.service.ServiceRegistry;
import net.youtunity.devathlon.spell.Spell;
import net.youtunity.devathlon.spell.SpellContext;
import net.youtunity.devathlon.spell.SpellMeta;
import net.youtunity.devathlon.user.UserService;
import net.youtunity.devathlon.utils.SchedulerUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by thecrealm on 17.07.16.
 */
@SpellMeta(name = "ConfundingCharm", material = Material.BAKED_POTATO)
public class ConfundingCharmSpell implements Spell {

    // Gegner Orientierungslos machen
    //Idee: Abdunkeln

    private static final int MAX_RUNS = 200;

    @Override
    public void execute(SpellContext context) {

        new BukkitRunnable() {

            AtomicInteger runs = new AtomicInteger(MAX_RUNS);
            Location loc = context.getInvoker().getPlayer().getLocation().add(0, 1.8, 0);
            World world = loc.getWorld();

            @Override
            public void run() {

                if(runs.decrementAndGet() <= 0) {
                    this.cancel();
                }

                loc = loc.add(loc.getDirection().multiply(0.4));

                context.getInvoker().getPlayer().getWorld().spawnParticle(Particle.CLOUD, loc, 5);

                Optional<Entity> first = world.getNearbyEntities(loc, 1, 1, 1).stream()
                        .filter(entity -> entity instanceof Player)
                        .findFirst();

                if(first.isPresent()) {
                    Player player = (Player) first.get();

                    if(!context.getParty().getUsers().contains(ServiceRegistry.lookupService(UserService.class).find(player).get())) {
                        this.cancel();
                        context.getInvoker().getPlayer().sendMessage("You hit, yeah!");
                        player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 50, 2, true, false));
                        player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 100, 3, true, false));
                        world.spawnParticle(Particle.CLOUD, loc, 15, 0.2D, 0.2D, 0.2D);
                    }
                }
            }
        }.runTaskTimer(context.getPlugin(), 0L, 1L);
    }
}
