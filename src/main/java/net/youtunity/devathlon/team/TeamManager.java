package net.youtunity.devathlon.team;

import net.youtunity.devathlon.DevathlonConfig;
import net.youtunity.devathlon.DevathlonPlugin;
import org.bukkit.DyeColor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by thecrealm on 30.07.16.
 */
public class TeamManager {

    private DevathlonPlugin plugin;
    private List<Team> teams = new ArrayList<>();

    public TeamManager(DevathlonPlugin plugin) {
        this.plugin = plugin;
    }

    public void init() {

        for (DevathlonConfig.Team team : plugin.getDevathlonConfig().getTeams()) {
            teams.add(new Team(team.getName(), team));
        }
    }

    public List<Team> getTeams() {
        return teams;
    }

    public Team find(String name) {
        for (Team team : teams) {
            if(team.getName().equalsIgnoreCase(name)) {
                return team;
            }
        }

        return null;
    }
}
