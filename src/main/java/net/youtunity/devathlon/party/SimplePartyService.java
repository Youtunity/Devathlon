package net.youtunity.devathlon.party;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import net.youtunity.devathlon.DevathlonPlugin;
import net.youtunity.devathlon.config.DevathlonConfig;
import net.youtunity.devathlon.kit.Kit;
import net.youtunity.devathlon.kit.KitService;
import net.youtunity.devathlon.service.Initializable;
import net.youtunity.devathlon.service.ServiceRegistry;
import net.youtunity.devathlon.user.User;
import net.youtunity.devathlon.user.UserService;
import net.youtunity.devathlon.utils.Cuboid;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Optional;

/**
 * Created by thecrealm on 16.07.16.
 */
public class SimplePartyService implements PartyService, Initializable {

    private DevathlonPlugin plugin;
    private List<Party> parties = Lists.newArrayList();

    public SimplePartyService(DevathlonPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void init() {

        KitService kitService = ServiceRegistry.lookupService(KitService.class);

        for (DevathlonConfig.PartyConfig config : plugin.getDevathlonConfig().getParties()) {

            Party party = new Party(config.getPartyName());

            party.setSpawn(config.getSpawn());
            party.setEgg(config.getEgg());

            Cuboid cuboid = new Cuboid(config.getCuboidOne(), config.getCuboidTwo());
            party.setLobbyCuboid(cuboid);

            for (String kit : config.getKits()) {
                Optional<Kit> found = kitService.find(kit);
                if(found.isPresent()) {
                    party.assignAvailableKit(found.get());
                }
            }

            parties.add(party);

            String kits = "";
            for (Kit kit : party.getAvailableKits()) {
                kits += kit.getName() + ",";
            }

            plugin.getLogger().info("Registered Party: " + party.getDisplayName() + " [" + kits + "]");
        }
    }

    @Override
    public List<Party> getParties() {
        return parties;
    }

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
