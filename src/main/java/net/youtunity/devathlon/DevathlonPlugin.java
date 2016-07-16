package net.youtunity.devathlon;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import net.youtunity.devathlon.kit.Kit;
import net.youtunity.devathlon.party.Party;
import net.youtunity.devathlon.spell.spells.TestSpell;
import net.youtunity.devathlon.state.IngameState;
import net.youtunity.devathlon.state.LobbyState;
import net.youtunity.devathlon.state.State;
import net.youtunity.devathlon.user.User;
import net.youtunity.devathlon.user.UserListener;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

/**
 * Created by thecrealm on 16.07.16.
 */
public class DevathlonPlugin extends JavaPlugin {

    private Set<User> users = Sets.newHashSet();
    private Set<Party> parties = Sets.newHashSet();

    private State currentGameState = null;
    private LinkedList<State> allStates = Lists.newLinkedList();
    private ListIterator<State> stateIterator = null;

    @Override
    public void onEnable() {
        getLogger().info("Enabled!");

        Bukkit.getOnlinePlayers().forEach(player -> {
            users.add(new User(player));
        });

        new UserListener(this);

        parties.add(new Party("1"));
        parties.add(new Party("2"));
        parties.add(new Party("3"));
        parties.add(new Party("4"));

        Kit kit = new Kit();
        kit.addSpell(TestSpell.class, 0);

        Party party = findParty("1");

        party.assignAvailableKit(kit);

        for (User user : getUsers()) {
            party.join(user);
        }

        allStates.add(new LobbyState(this));
        allStates.add(new IngameState(this));

        stateIterator = allStates.listIterator();

        currentGameState = stateIterator.next();
        getCurrentGameState().onEnter();
    }

    // User related

    public Set<User> getUsers() {
        return Collections.unmodifiableSet(users);
    }

    public User findUser(Player player) {
        return getUsers().stream()
                .filter(user -> user.getPlayer().equals(player))
                .findFirst()
                .orElse(null);
    }


    // PARTY RELATED

    public Set<Party> getParties() {
        return Collections.unmodifiableSet(parties);
    }

    public Party findParty(String partyName) {
        return parties.stream()
                .filter(party -> party.getDisplayName().equals(partyName))
                .findFirst()
                .orElse(null);
    }

    public Party findParty(User user) {
        return parties.stream()
                .filter(party -> party.getUsers().contains(user))
                .findFirst()
                .orElse(null);
    }


    // STATE SYSTEM

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
            System.out.println("Debug 2");

            State next = stateIterator.next();
            System.out.println(next.getClass().getSimpleName());

            getCurrentGameState().onQuit();
            next.onEnter();

            this.currentGameState = next;

        } else {
            Bukkit.broadcastMessage("Game Ended, restarting in 5 seconds.");
            getServer().getScheduler().runTaskLater(this, Bukkit::shutdown, 100L);
        }
    }
}
