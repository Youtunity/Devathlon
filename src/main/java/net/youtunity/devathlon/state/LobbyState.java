package net.youtunity.devathlon.state;

import net.youtunity.devathlon.DevathlonPlugin;

/**
 * Created by thecrealm on 16.07.16.
 */
public class LobbyState extends State {

    private DevathlonPlugin plugin;

    public LobbyState(DevathlonPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void onEnter() {

        System.out.println("IM alive");
        plugin.nextGamestate();
    }

    @Override
    public void onQuit() {

    }
}
