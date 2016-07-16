package net.youtunity.devathlon.state;

import net.youtunity.devathlon.DevathlonPlugin;
import net.youtunity.devathlon.kit.Kit;
import net.youtunity.devathlon.party.Party;
import net.youtunity.devathlon.party.PartyService;
import net.youtunity.devathlon.service.ServiceRegistry;
import net.youtunity.devathlon.user.User;
import net.youtunity.devathlon.user.UserService;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Created by thecrealm on 16.07.16.
 */
public class IngameState extends State {

    BossBar bossBar = null;


    private DevathlonPlugin plugin;

    public IngameState(DevathlonPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void onEnter() {

        this.bossBar = Bukkit.createBossBar("Time", BarColor.BLUE, BarStyle.SOLID, BarFlag.DARKEN_SKY);

        UserService userService = ServiceRegistry.lookupService(UserService.class);
        PartyService partyService = ServiceRegistry.lookupService(PartyService.class);

        for (User user : userService.getUsers()) {
            this.bossBar.addPlayer(user.getPlayer());

            Optional<Party> party = partyService.find(user);
            if(!party.isPresent()) {
                Optional<Party> gryffindor = partyService.find("Gryffindor");
                gryffindor.get().join(user);
                party = gryffindor;
                //TODO: remove later, debug purpose only
            }

            Kit kit = party.get().getAvailableKits().get(0);
            System.out.println(kit.getName());
            user.assignKit(kit);
            user.getKit().giveItems(user);
            user.getPlayer().getInventory().setHeldItemSlot(8);
        }

        this.bossBar.setVisible(true);
    }

    @Override
    public void onQuit() {

    }
}
