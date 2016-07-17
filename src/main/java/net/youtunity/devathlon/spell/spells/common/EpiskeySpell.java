package net.youtunity.devathlon.spell.spells.common;

import net.youtunity.devathlon.spell.Spell;
import net.youtunity.devathlon.spell.SpellContext;
import net.youtunity.devathlon.spell.SpellMeta;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.entity.Player;

/**
 * Created by thecrealm on 17.07.16.
 */
@SpellMeta(name = "Episkey", material = Material.GOLDEN_APPLE, cooldown = 10)
public class EpiskeySpell implements Spell {

    // Heilung um kleine Verletzung zu beheben

    private static double HEAL_POWER = 5D;
    private static int FOOD_POWER = 4;

    @Override
    public void execute(SpellContext context) {

        Player player = context.getInvoker().getPlayer();
        player.playEffect(player.getLocation(), Effect.HEART, null);
        player.setHealth(player.getHealth() + HEAL_POWER);
        player.setFoodLevel(player.getFoodLevel() + FOOD_POWER);
    }
}
