package net.youtunity.devathlon.state;

import net.youtunity.devathlon.DevathlonPlugin;
import net.youtunity.devathlon.party.Party;
import net.youtunity.devathlon.user.User;

import java.util.Set;

/**
 * Created by thecrealm on 16.07.16.
 */
public class IngameState extends State {

    private DevathlonPlugin plugin;

    public IngameState(DevathlonPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void onEnter() {

        for (User user : plugin.getUsers()) {
            user.getKit().giveItems(user);
        }

    }

    @Override
    public void onQuit() {

    }
}
