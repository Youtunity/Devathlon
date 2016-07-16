package net.youtunity.devathlon;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import net.cubespace.Yamler.Config.InvalidConfigurationException;
import net.youtunity.devathlon.config.DevathlonConfig;
import net.youtunity.devathlon.kit.Kit;
import net.youtunity.devathlon.party.Party;
import net.youtunity.devathlon.party.PartyService;
import net.youtunity.devathlon.party.SimplePartyService;
import net.youtunity.devathlon.service.ServiceRegistry;
import net.youtunity.devathlon.spell.Spell;
import net.youtunity.devathlon.spell.SpellMetaCache;
import net.youtunity.devathlon.spell.spells.ConfringoSpell;
import net.youtunity.devathlon.spell.spells.TestSpell;
import net.youtunity.devathlon.state.IngameState;
import net.youtunity.devathlon.state.LobbyState;
import net.youtunity.devathlon.state.State;
import net.youtunity.devathlon.user.SimpleUserService;
import net.youtunity.devathlon.user.User;
import net.youtunity.devathlon.user.UserListener;
import net.youtunity.devathlon.user.UserService;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.*;

/**
 * Created by thecrealm on 16.07.16.
 */
public class DevathlonPlugin extends JavaPlugin {

    private DevathlonConfig config;

    private State currentGameState = null;
    private LinkedList<State> allStates = Lists.newLinkedList();
    private ListIterator<State> stateIterator = null;

    @Override
    public void onEnable() {

        //config
        loadConfig();

        //states
        registerState(new LobbyState(this));
        registerState(new IngameState(this));

        //services
        ServiceRegistry.registerService(UserService.class, new SimpleUserService(this));
        ServiceRegistry.registerService(PartyService.class, new SimplePartyService());

        //listener util inits
        new UserListener(this);

        //start
        prepareAndRunGame();
    }

    private void prepareAndRunGame() {
        stateIterator = allStates.listIterator();
        currentGameState = stateIterator.next();
        getCurrentGameState().onEnter();
    }

    // STATE SYSTEM

    private void registerState(State state) {

        if(stateIterator != null) {
            // while not initialized, we can add states to them
            this.allStates.add(state);
            getLogger().info("Added '" + state.getClass().getSimpleName() + "' state!");
        }
    }

    private State getCurrentGameState() {
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
            this.currentGameState = next;
        } else {
            Bukkit.broadcastMessage("Game Ended, restarting in 5 seconds.");
            getServer().getScheduler().runTaskLater(this, Bukkit::shutdown, 100L);
        }
    }

    public DevathlonConfig getDevathlonConfig() {
        return this.config;
    }

    // Config
    private void loadConfig() {
        this.config = new DevathlonConfig();

        try {
            this.config.init();
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }
}
