package net.youtunity.devathlon.team;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by thecrealm on 30.07.16.
 */
public class TeamManager {

    private List<Team> teams = new ArrayList<>();

    public void registerTeam(Team team) {
        teams.add(team);
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
