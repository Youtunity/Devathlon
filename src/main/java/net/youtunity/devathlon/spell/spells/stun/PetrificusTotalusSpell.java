package net.youtunity.devathlon.spell.spells.stun;

import net.youtunity.devathlon.party.PartyService;
import net.youtunity.devathlon.service.ServiceRegistry;
import net.youtunity.devathlon.spell.Spell;
import net.youtunity.devathlon.spell.SpellContext;
import net.youtunity.devathlon.spell.SpellMeta;
import net.youtunity.devathlon.user.UserService;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Created by thecrealm on 17.07.16.
 */
@SpellMeta(name = "Petrificus", material = Material.ANVIL, cooldown = 8)
public class PetrificusTotalusSpell implements Spell {

    // Lähmt den ganzen Körper

    private static final int ROUNDS = 17;
    private static final float ADD_RAD = 0.4f;
    private static final int STEPS = 90;

    @Override
    public void execute(SpellContext context) {

        new BukkitRunnable() {

            Location location = context.getInvoker().getPlayer().getLocation();
            double round = 1;

            @Override
            public void run() {

                for (int i = 0; i < STEPS; i++) {
                    double angle = 2D * Math.PI * (i * 1D) / STEPS * 1D;
                    double x = Math.cos(angle) * round * ADD_RAD;
                    double z = Math.sin(angle) * round * ADD_RAD;
                    Location play = location.clone().add(x, 0, z);
                    location.getWorld().playEffect(play, Effect.TILE_BREAK, 10, 10);
                }

                location.getWorld().playSound(location, Sound.BLOCK_ANVIL_BREAK, 1f, 1f);

                double factor = round * 1D * ADD_RAD;
                for (Entity entity : location.getWorld().getNearbyEntities(location, factor, factor, factor)) {
                    if (entity instanceof Player) {
                        if(!ServiceRegistry.lookupService(PartyService.class).find(((Player) entity)).get().equals(context.getParty())) {
                            ((Player) entity).addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 80, 3, true, false));
                        }
                    }
                }

                if(round >= ROUNDS) {
                    this.cancel();
                }

                round++;
            }
        }.runTaskTimer(context.getPlugin(), 6L, 1L);
    }
}
