package net.youtunity.devathlon.spell;

import net.youtunity.devathlon.user.User;

/**
 * Created by thecrealm on 16.07.16.
 */
public class SpellExecuter {

    public static SpellContext execute(User user, Spell spell) {

        SpellContext context = new SpellContext(user, null);
        spell.execute(context);
        return context;
    }
}
