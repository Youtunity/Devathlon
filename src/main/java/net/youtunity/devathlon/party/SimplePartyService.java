package net.youtunity.devathlon.party;

import com.google.common.collect.Lists;
import net.youtunity.devathlon.service.ServiceRegistry;
import net.youtunity.devathlon.user.User;
import net.youtunity.devathlon.user.UserService;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Optional;

/**
 * Created by thecrealm on 16.07.16.
 */
public class SimplePartyService implements PartyService {

    private List<Party> parties = Lists.newArrayList();

    @Override
    public Optional<Party> find(String name) {

        return parties.stream()
                .filter(party -> party.getDisplayName().equals(name))
                .findFirst();
    }

    @Override
    public Optional<Party> find(User user) {

        return parties.stream()
                .filter(party -> party.getUsers().contains(user))
                .findFirst();
    }

    @Override
    public Optional<Party> find(Player player) {
        Optional<User> user = ServiceRegistry.lookupService(UserService.class).find(player);

        if(user.isPresent()) {
            return find(user.get());
        }

        return Optional.empty();
    }
}
