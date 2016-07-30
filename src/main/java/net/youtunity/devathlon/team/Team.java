package net.youtunity.devathlon.team;

import net.youtunity.devathlon.user.User;

import java.util.List;

/**
 * Created by thecrealm on 30.07.16.
 */
public class Team {

    private String name;
    private List<User> users;

    public Team(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void addUser(User user) {
        this.users.add(user);
    }

    public List<User> getUsers() {
        return users;
    }
}
