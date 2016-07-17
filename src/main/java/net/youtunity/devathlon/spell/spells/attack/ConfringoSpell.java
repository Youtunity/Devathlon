package net.youtunity.devathlon.spell.spells.attack;

import net.youtunity.devathlon.service.ServiceRegistry;
import net.youtunity.devathlon.spell.Spell;
import net.youtunity.devathlon.spell.SpellContext;
import net.youtunity.devathlon.spell.SpellMeta;
import net.youtunity.devathlon.user.User;
import net.youtunity.devathlon.user.UserService;
import net.youtunity.devathlon.utils.SchedulerUtils;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by thecrealm on 16.07.16.
 */
@SpellMeta(name = "confringo", cooldown = 10, material = Material.STICK)
public class ConfringoSpell implements Spell {

    //Explosionszauber

    @Override
    public void execute(SpellContext context) {

        Location startLocation = context.getInvoker().getPlayer().getLocation().add(0, 1.8, 0);
        AtomicReference<Location> custom = new AtomicReference<>(startLocation);

        AtomicInteger count = new AtomicInteger(0);

        SchedulerUtils.runTaskTimerRuns(context.getPlugin(), () -> {
            custom.set(custom.get().add(startLocation.getDirection().multiply(0.4)));
            context.getInvoker().getPlayer().getWorld().spawnParticle(Particle.SNOWBALL, custom.get(), 10);

            if(count.incrementAndGet() % 2 == 0) {
                startLocation.getWorld().playSound(startLocation, Sound.BLOCK_CLOTH_BREAK, 2f, 1f);
            }

            for (Entity entity : startLocation.getWorld().getNearbyEntities(startLocation, 1.5D, 1.5D, 1)) {
                if(entity instanceof Player) {
                    User user = ServiceRegistry.lookupService(UserService.class).find(((Player) entity)).get();
                    if(!context.getParty().getUsers().contains(user)) {
                        ((Player) entity).damage(3D);
                        entity.playEffect(EntityEffect.HURT);
                        entity.getWorld().playSound(entity.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 1f, 1f);
                        entity.getWorld().playEffect(entity.getLocation().add(0D, 1.4D, 0D), Effect.EXPLOSION, 5);
                    }
                }
            }

        }, 0L, 1L, 200);
    }
}
