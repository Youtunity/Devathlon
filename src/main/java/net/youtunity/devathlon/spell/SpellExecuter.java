package net.youtunity.devathlon.spell;

import net.youtunity.devathlon.DevathlonPlugin;
import net.youtunity.devathlon.user.User;

/**
 * Created by thecrealm on 16.07.16.
 */
public class SpellExecuter {

    private static DevathlonPlugin plugin;

    public static void init(DevathlonPlugin plugin) {
        SpellExecuter.plugin = plugin;
    }

    public static SpellContext execute(User user, Spell spell) {

        SpellMeta meta = SpellMetaCache.get(spell.getClass());

        if(user.isCooldown(meta.name())) {
            SpellContext context = new SpellContext(user, plugin.findParty(user));
            context.setExecuted(false);
            return context;
        }

        user.cooldown(meta.name(), meta.cooldown());
        SpellContext context = new SpellContext(user, plugin.findParty(user));
        spell.execute(context);
        context.setExecuted(true);
        return context;
    }
}
