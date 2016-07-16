package net.youtunity.devathlon.user;

import org.bukkit.entity.Player;

import java.util.Optional;

/**
 * Created by thecrealm on 16.07.16.
 */
public interface UserService {

    Optional<User> find(Player player);
}
