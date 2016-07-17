package net.youtunity.devathlon.spell.spells.attack;

import net.youtunity.devathlon.spell.Spell;
import net.youtunity.devathlon.spell.SpellContext;
import net.youtunity.devathlon.spell.SpellMeta;
import net.youtunity.devathlon.utils.SchedulerUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.util.Vector;

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

        SchedulerUtils.runTaskTimerRuns(context.getPlugin(), () -> {
            custom.set(custom.get().add(startLocation.getDirection().multiply(0.3)));
            context.getInvoker().getPlayer().getWorld().spawnParticle(Particle.SNOWBALL, custom.get(), 10);
        }, 0L, 1L, 200);
    }
}
