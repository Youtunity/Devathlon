package net.youtunity.devathlon.party;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import net.youtunity.devathlon.kit.Kit;
import net.youtunity.devathlon.user.User;
import net.youtunity.devathlon.utils.Cuboid;
import org.bukkit.Location;

import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * Created by thecrealm on 16.07.16.
 */
public class Party {

    private String displayName;

    private Set<User> users = Sets.newHashSet();
    private List<Kit> availableKits = Lists.newArrayList();

    private Cuboid lobbyCuboid;

    private Location spawn;
    private Location egg;

    public Party(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    //Locations


    public void setLobbyCuboid(Cuboid lobbyCuboid) {
        this.lobbyCuboid = lobbyCuboid;
    }

    public Cuboid getLobbyCuboid() {
        return lobbyCuboid;
    }

    public void setEgg(Location egg) {
        this.egg = egg;
    }

    public Location getEgg() {
        return egg;
    }

    public void setSpawn(Location spawn) {
        this.spawn = spawn;
    }

    public Location getSpawn() {
        return spawn;
    }

    //Kits

    public List<Kit> getAvailableKits() {
        return availableKits;
    }

    public void assignAvailableKit(Kit kit) {
        this.availableKits.add(kit);
    }

    //User

    public void join(User user) {
        this.users.add(user);
        user.getPlayer().sendMessage("You joined Party " + getDisplayName());
    }

    public void left(User user) {
        this.users.remove(user);
        user.getPlayer().sendMessage("You left Party " + getDisplayName());
    }

    public Set<User> getUsers() {
        return Collections.unmodifiableSet(users);
    }
}
