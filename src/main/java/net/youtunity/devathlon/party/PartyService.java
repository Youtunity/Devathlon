package net.youtunity.devathlon.party;

import net.youtunity.devathlon.user.User;
import org.bukkit.entity.Player;

import java.util.Optional;

/**
 * Created by thecrealm on 16.07.16.
 */
public interface PartyService {

    Optional<Party> find(String name);

    Optional<Party> find(User user);

    Optional<Party> find(Player player);
}
