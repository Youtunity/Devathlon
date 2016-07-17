package net.youtunity.devathlon.spell.spells.stun;

import net.youtunity.devathlon.service.ServiceRegistry;
import net.youtunity.devathlon.spell.Spell;
import net.youtunity.devathlon.spell.SpellContext;
import net.youtunity.devathlon.spell.SpellMeta;
import net.youtunity.devathlon.user.UserService;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.material.MaterialData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by thecrealm on 17.07.16.
 */
@SpellMeta(name = "Impedimenta", cooldown = 8, material = Material.ICE)
public class ImpedimentaSpell implements Spell {


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

                loc = loc.add(loc.getDirection().multiply(0.5));
                context.getInvoker().getPlayer().getWorld().spawnParticle(Particle.CRIT, loc, 6);

                Optional<Entity> first = world.getNearbyEntities(loc, 1, 1, 1).stream()
                        .filter(entity -> entity instanceof Player)
                        .findFirst();


                if(first.isPresent()) {
                    Player player = (Player) first.get();

                    if(!context.getParty().getUsers().contains(ServiceRegistry.lookupService(UserService.class).find(player).get())) {
                        this.cancel();
                        player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 60, 2, true, false));
                        player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 80, 2, true, false));
                        player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 120, 3, true, false));
                        world.spawnParticle(Particle.CLOUD, loc, 20, 0.2D, 0.2D, 0.2D);
                    }
                }
            }
        }.runTaskTimer(context.getPlugin(), 0L, 1L);
    }
}
