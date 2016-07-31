package net.youtunity.devathlon.team;

import net.youtunity.devathlon.DevathlonConfig;
import net.youtunity.devathlon.user.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by thecrealm on 30.07.16.
 */
public class Team {

    private String name;
    private DevathlonConfig.Team config;
    private List<User> users = new ArrayList<>();

    public Team(String name, DevathlonConfig.Team config) {
        this.name = name;
        this.config = config;
    }

    public String getName() {
        return name;
    }

    public void addUser(User user) {
        if(users.contains(user)) return;

        if(user.getTeam() != null) {
            user.getTeam().removeUser(user);
        }

        this.users.add(user);
        user.setTeam(this);
        user.getPlayer().sendMessage("Du bist Team " + getName() + " beigetreten!");
    }

    public void removeUser(User user) {
        users.remove(user);
        user.setTeam(null);
    }

    public DevathlonConfig.Team getConfig() {
        return config;
    }

    public List<User> getUsers() {
        return users;
    }
}
