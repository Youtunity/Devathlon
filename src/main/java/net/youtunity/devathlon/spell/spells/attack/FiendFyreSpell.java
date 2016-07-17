package net.youtunity.devathlon.spell.spells.attack;

import net.youtunity.devathlon.spell.Spell;
import net.youtunity.devathlon.spell.SpellContext;
import net.youtunity.devathlon.spell.SpellMeta;
import net.youtunity.devathlon.utils.SchedulerUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;

/**
 * Created by thecrealm on 16.07.16.
 */
@SpellMeta(name = "FiendFyre", cooldown = 6, material = Material.APPLE)
public class FiendFyreSpell implements Spell {

    // Schwarz, magisches Feuer
    // Idee: Obsidian Crack Particle!

    @Override
    public void execute(SpellContext context) {

        final Location location = context.getInvoker().getPlayer().getLocation().add(0, 1.7, 0);

        SchedulerUtils.runTaskTimerRuns(context.getPlugin(), () -> {
            context.getInvoker().getPlayer().spawnParticle(Particle.FLAME, location, 5);
        }, 0L, 1L, 400);

    }
}
