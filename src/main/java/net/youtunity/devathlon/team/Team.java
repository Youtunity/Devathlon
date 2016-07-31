package net.youtunity.devathlon.team;

import net.youtunity.devathlon.DevathlonConfig;
import net.youtunity.devathlon.DevathlonPlugin;
import net.youtunity.devathlon.user.User;
import org.bukkit.DyeColor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by thecrealm on 30.07.16.
 */
public class Team {

    private String name;
    private DevathlonConfig.Team config;
    private List<User> users = new ArrayList<>();

    private int tickets = 150;

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
        user.getPlayer().sendMessage(DevathlonPlugin.PREFIX + "Du bist Team " + getName() + " beigetreten!");
    }

    public void removeUser(User user) {
        users.remove(user);
        user.setTeam(null);
    }

    public void setTickets(int tickets) {
        this.tickets = tickets;
    }

    // NO TIME, HARDCODE THIS SHIT
    public DyeColor getDyeColor() {

        if(getName().equals("red")) {
            return DyeColor.RED;
        } else {
            return DyeColor.BLUE;
        }
    }

    public void decrementTickets() {
        this.tickets--;
    }

    public int getTickets() {
        return tickets;
    }

    public DevathlonConfig.Team getConfig() {
        return config;
    }

    public List<User> getUsers() {
        return users;
    }
}
