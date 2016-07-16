package net.youtunity.devathlon.spell;

import net.youtunity.devathlon.party.Party;
import net.youtunity.devathlon.user.User;

/**
 * Created by thecrealm on 16.07.16.
 */
public class SpellContext {

    private User user;
    private Party party;
    private boolean executed = false;

    public SpellContext(User user, Party party) {
        this.user = user;
        this.party = party;
    }

    public boolean isExecuted() {
        return executed;
    }

    public void setExecuted(boolean executed) {
        this.executed = executed;
    }

    public User getInvoker() {
        return user;
    }

    public Party getParty() {
        return party;
    }
}
