package net.youtunity.devathlon.party;

import com.google.common.collect.Sets;
import net.youtunity.devathlon.user.User;

import java.util.Collections;
import java.util.Set;

/**
 * Created by thecrealm on 16.07.16.
 */
public class Party {

    private Set<User> users = Sets.newHashSet();
    private int health = 10; //TODO: maybe respawnable?

    public Set<User> getUsers() {
        return Collections.unmodifiableSet(users);
    }


}
