package net.youtunity.devathlon.spell.spells;

import net.youtunity.devathlon.spell.Spell;
import net.youtunity.devathlon.spell.SpellContext;
import org.bukkit.Location;
import org.bukkit.util.Vector;

/**
 * Created by thecrealm on 16.07.16.
 */
public class ConfringoSpell implements Spell {

    private int maxLenght = 10;

    @Override
    public void execute(SpellContext context) {

        Location startLocation = context.getInvoker().getPlayer().getLocation().add(0, 0.5, 0);




    }

    private void schedule(Location startLocation, Vector direction) {

    }
}
