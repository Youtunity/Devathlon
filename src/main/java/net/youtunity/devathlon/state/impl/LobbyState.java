package net.youtunity.devathlon.state.impl;

import net.youtunity.devathlon.DevathlonPlugin;
import net.youtunity.devathlon.state.CountedState;
import org.bukkit.Bukkit;

/**
 * Created by thecrealm on 30.07.16.
 */
public class LobbyState extends CountedState {

    public LobbyState(DevathlonPlugin plugin) {
        super(plugin, 60, 0, Direction.DOWN);
    }

    @Override
    protected void enter() {

    }

    @Override
    protected void leave() {

    }

    @Override
    public void onCount(int count) {
        Bukkit.broadcastMessage(count + "");
    }
}
