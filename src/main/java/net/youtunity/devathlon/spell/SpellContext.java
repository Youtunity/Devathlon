package net.youtunity.devathlon.spell;

import net.youtunity.devathlon.user.User;

/**
 * Created by thecrealm on 16.07.16.
 */
public interface SpellContext {

    User getInvoker();

    void callParent();
}
