package net.youtunity.devathlon.spell.spells.common;

import net.youtunity.devathlon.spell.Spell;
import net.youtunity.devathlon.spell.SpellContext;
import net.youtunity.devathlon.spell.SpellMeta;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by thecrealm on 17.07.16.
 */
@SpellMeta(name = "Episkey", material = Material.GOLDEN_APPLE, cooldown = 10)
public class EpiskeySpell implements Spell {

    // Heilung um kleine Verletzung zu beheben

    private static float RADIUS = 1.2f;
    private static int PARTICLES_COUNT = 16;
    private static int RUNS = 12;
    private static double Y_STEP = 0.2D;

    private static double HEAL_POWER = 5D;
    private static int FOOD_POWER = 4;

    @Override
    public void execute(SpellContext context) {

        Player player = context.getInvoker().getPlayer();

        double health = player.getHealth() + HEAL_POWER;
        if (health > 20D) {
            health = 20D;
        }

        player.setHealth(health);

        int food = player.getFoodLevel() + FOOD_POWER;
        if (food > 20) {
            food = 20;
        }

        player.setFoodLevel(food);

        new BukkitRunnable() {

            int runs = 0;

            @Override
            public void run() {

                if(runs >= RUNS) {
                    this.cancel();
                }

                Location location = context.getInvoker().getPlayer().getLocation();
                for (int i = 0; i < PARTICLES_COUNT; i++) {
                    double angle = 2D * Math.PI * (i * 1D) / PARTICLES_COUNT * 1D;
                    double x = Math.cos(angle) * RADIUS;
                    double z = Math.sin(angle) * RADIUS;
                    Location play = location.clone().add(x, runs * Y_STEP, z);
                    location.getWorld().spawnParticle(Particle.HEART, play, 1, 0, 0, 0);
                }

                runs++;
            }
        }.runTaskTimer(context.getPlugin(), 5L, 2L);
    }
}
