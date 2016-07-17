package net.youtunity.devathlon.spell.spells.attack;

import net.youtunity.devathlon.party.PartyService;
import net.youtunity.devathlon.service.ServiceRegistry;
import net.youtunity.devathlon.spell.Spell;
import net.youtunity.devathlon.spell.SpellContext;
import net.youtunity.devathlon.spell.SpellMeta;
import net.youtunity.devathlon.user.User;
import net.youtunity.devathlon.user.UserService;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.Random;

/**
 * Created by thecrealm on 17.07.16.
 */
@SpellMeta(name = "Expulso", material = Material.COAL, cooldown = 9)
public class ExpulsoSpell implements Spell {

    @Override
    public void execute(SpellContext context) {

        Vector one = new Vector(1, 0, 0).multiply(0.2);
        Vector two = new Vector(0, 0, 1).multiply(0.2);

        Random random = new Random();

        new BukkitRunnable() {

            Location start = context.getInvoker().getPlayer().getLocation();
            int round = 0;

            @Override
            public void run() {
                round++;

                Location player = context.getInvoker().getPlayer().getLocation();

                if(round >= 20) {
                    this.cancel();
                }

                player.getWorld().playEffect(player.clone().add(one), Effect.EXPLOSION, 2);
                player.getWorld().playEffect(player.clone().add(two), Effect.EXPLOSION, 2);
                one.add(new Vector(0.2, 0, random.nextDouble()));
                two.add(new Vector(random.nextDouble(), 0, 0.2));

                for (Entity entity : start.getWorld().getNearbyEntities(start, 0.3 * round, 3, 0.3 * round)) {
                    if(entity instanceof Player) {
                        User user = ServiceRegistry.lookupService(UserService.class).find(((Player) entity)).get();
                        if(!context.getParty().getUsers().contains(user)) {
                            ((Player) entity).damage(3D);
                            entity.getWorld().playSound(entity.getLocation(), Sound.BLOCK_DISPENSER_LAUNCH, 1f, 1f);
                            ((Player) entity).playEffect(EntityEffect.HURT);
                        }
                    }
                }
            }
        }.runTaskTimer(context.getPlugin(), 0L, 1L);

    }
}
