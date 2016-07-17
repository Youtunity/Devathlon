package net.youtunity.devathlon.spell.spells.attack;

import net.youtunity.devathlon.service.ServiceRegistry;
import net.youtunity.devathlon.spell.Spell;
import net.youtunity.devathlon.spell.SpellContext;
import net.youtunity.devathlon.spell.SpellMeta;
import net.youtunity.devathlon.user.User;
import net.youtunity.devathlon.user.UserService;
import net.youtunity.devathlon.utils.SchedulerUtils;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.material.MaterialData;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

/**
 * Created by thecrealm on 16.07.16.
 */
@SpellMeta(name = "FiendFyre", cooldown = 6, material = Material.APPLE)
public class FiendFyreSpell implements Spell {

    // Schwarz, magisches Feuer
    // Idee: Obsidian Crack Particle!

    private static final int ROUNDS = 200;

    @Override
    public void execute(SpellContext context) {

        new BukkitRunnable() {
            int round = 0;

            Location location = context.getInvoker().getPlayer().getLocation().add(0, 1.7, 0);
            Vector direction = location.getDirection().multiply(0.4);

            @Override
            public void run() {

                round++;

                if(round >= ROUNDS) {
                    this.cancel();
                }

                location.add(direction);
                if(round % 2 == 0) {
                    location.getWorld().playEffect(location, Effect.ANVIL_BREAK, 8);
                } else {
                    location.getWorld().playEffect(location, Effect.TILE_BREAK, 8, Material.OBSIDIAN.getId());
                }

                for (Entity entity : location.getWorld().getNearbyEntities(location, 1.5, 1.5, 1.5)) {
                    if (entity instanceof Player) {
                        User user = ServiceRegistry.lookupService(UserService.class).find(((Player) entity)).get();
                        if(!context.getParty().getUsers().contains(user)) {
                            ((Player) entity).damage(5D);
                            this.cancel();
                            location.getWorld().playEffect(location, Effect.LAVA_POP, 30);
                        }
                    }
                }

            }
        }.runTaskTimer(context.getPlugin(), 0L, 2L);
    }
}
