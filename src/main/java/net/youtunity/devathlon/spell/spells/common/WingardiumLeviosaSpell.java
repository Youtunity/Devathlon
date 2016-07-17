package net.youtunity.devathlon.spell.spells.common;

import net.youtunity.devathlon.spell.Spell;
import net.youtunity.devathlon.spell.SpellContext;
import net.youtunity.devathlon.spell.SpellMeta;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * Created by thecrealm on 17.07.16.
 */
@SpellMeta(name = "WingardiumLeviosa", material = Material.ARROW, cooldown = 12)
public class WingardiumLeviosaSpell implements Spell {

    // Schweben lassen

    @Override
    public void execute(SpellContext context) {

        Location location = context.getInvoker().getPlayer().getLocation();

        for (int k = 0; k < 20; k++) {
            for (int i = 0; i < 80; i++) {
                double angle = 2D * Math.PI * (i * 1D) / 40 * 1D;
                double x = Math.cos(angle) * k * 0.25;
                double z = Math.sin(angle) * k * 0.25;
                Location play = location.clone().add(x, 0, z);
                location.getWorld().playEffect(play, Effect.COLOURED_DUST, 2, Material.END_CRYSTAL.getId());
            }
        }

        location.getWorld().playSound(location, Sound.BLOCK_END_GATEWAY_SPAWN, 1f, 1f);
        for (Entity entity : location.getWorld().getNearbyEntities(location, 5D, 4D, 5D)) {
            if(entity instanceof Player) {
                ((Player) entity).addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 75, 2, true, false));
            }
        }
    }
}
