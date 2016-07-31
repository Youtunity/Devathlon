package net.youtunity.devathlon.state.impl;

import com.quartercode.quarterbukkit.api.shape.Cuboid;
import net.youtunity.devathlon.DevathlonPlugin;
import net.youtunity.devathlon.state.CountedState;
import net.youtunity.devathlon.team.Team;
import net.youtunity.devathlon.user.User;
import net.youtunity.devathlon.util.ShapeListener;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by thecrealm on 30.07.16.
 */
public class LobbyState extends CountedState {

    private List<ShapeListener> teamListeners = new ArrayList<>();

    public LobbyState(DevathlonPlugin plugin) {
        super(plugin, 60, 0, Direction.DOWN);
    }

    @Override
    protected void enter() {

        for (User user : plugin.getUserManager().getUsers()) {
            user.getPlayer().teleport(plugin.getDevathlonConfig().getLobby());
        }

        for (Team team : plugin.getTeamManager().getTeams()) {
            ShapeListener listener = new ShapeListener(plugin, team.getConfig().toLobbyCylinder()) {

                @Override
                protected void onIntersects(Player player) {
                    team.addUser(plugin.getUserManager().find(player));
                }
            };

            teamListeners.add(listener);
        }
    }

    @Override
    protected void leave() {

        for (ShapeListener teamListener : teamListeners) {
            teamListener.stopListening();
        }
    }

    @Override
    public void onCount(int count) {

        if(count % 15 == 0 || count == 10 || count <= 5 && count != 0) {
            Bukkit.broadcastMessage(DevathlonPlugin.PREFIX + "Das Spiel beginnt in " + count + " Sekunden");

            if(count <= 5) {

            }
        }
    }
}
