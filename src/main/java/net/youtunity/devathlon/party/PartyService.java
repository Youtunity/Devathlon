package net.youtunity.devathlon.party;

import net.youtunity.devathlon.user.User;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Optional;

/**
 * Created by thecrealm on 16.07.16.
 */
public interface PartyService {

    List<Party> getParties();

    Optional<Party> find(String name);

    Optional<Party> find(User user);

    Optional<Party> find(Player player);
}
