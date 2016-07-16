package net.youtunity.devathlon.spell.spells;

import net.youtunity.devathlon.spell.Spell;
import net.youtunity.devathlon.spell.SpellContext;
import net.youtunity.devathlon.spell.SpellMeta;
import org.bukkit.Material;

/**
 * Created by thecrealm on 16.07.16.
 */
@SpellMeta(name = "TestSpell", material = Material.CAKE, cooldown = 3, manaUsage = 4)
public class TestSpell implements Spell {

    @Override
    public void execute(SpellContext context) {
        context.getInvoker().getPlayer().sendMessage("Hello, my name is TestSpell .. lel");


    }
}
