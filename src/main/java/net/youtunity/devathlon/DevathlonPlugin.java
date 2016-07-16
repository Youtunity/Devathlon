package net.youtunity.devathlon;

import com.google.common.collect.Lists;
import net.youtunity.devathlon.state.IngameState;
import net.youtunity.devathlon.state.LobbyState;
import net.youtunity.devathlon.state.State;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.LinkedList;
import java.util.ListIterator;

/**
 * Created by thecrealm on 16.07.16.
 */
public class DevathlonPlugin extends JavaPlugin {

    private State currentGameState = null;

    private LinkedList<State> allStates = Lists.newLinkedList();
    private ListIterator<State> stateIterator = null;

    @Override
    public void onEnable() {
        getLogger().info("Enabled!");

        allStates.add(new LobbyState());
        allStates.add(new IngameState());

        stateIterator = allStates.listIterator();

        currentGameState = stateIterator.next();
        getCurrentGameState().onEnter();

    }

    public State getCurrentGameState() {
        return this.currentGameState;
    }

    public boolean isGameState(Class<?> toCheck) {
        if(null == getCurrentGameState()) {
            return false;
        }

        return getCurrentGameState().getClass().equals(toCheck);
    }

    public void nextGamestate() {
        if (stateIterator.hasNext()) {
            State next = stateIterator.next();
            getCurrentGameState().onQuit();
            next.onEnter();
            currentGameState = next;
        } else {
            Bukkit.broadcastMessage("Game Ended, restarting in 5 seconds.");
            getServer().getScheduler().runTaskLater(this, Bukkit::shutdown, 100L);
        }
    }
}
