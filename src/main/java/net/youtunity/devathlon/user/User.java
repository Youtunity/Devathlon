package net.youtunity.devathlon.user;

import net.youtunity.devathlon.team.Team;
import org.bukkit.entity.Player;

/**
 * Created by thecrealm on 30.07.16.
 */
public class User {

    private Player player;
    private Team team;

    public User(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Team getTeam() {
        return team;
    }
}
