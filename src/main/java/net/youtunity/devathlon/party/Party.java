package net.youtunity.devathlon.party;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import net.youtunity.devathlon.kit.Kit;
import net.youtunity.devathlon.user.User;

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

    public Party(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public Set<User> getUsers() {
        return Collections.unmodifiableSet(users);
    }

    //Kits


    public List<Kit> getAvailableKits() {
        return availableKits;
    }

    public void assignAvailableKit(Kit kit) {
        this.availableKits.add(kit);
    }


    public void join(User user) {
        this.users.add(user);
    }

}
