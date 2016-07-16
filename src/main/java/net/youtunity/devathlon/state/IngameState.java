package net.youtunity.devathlon.state;

import net.youtunity.devathlon.DevathlonPlugin;
import net.youtunity.devathlon.kit.Kit;
import net.youtunity.devathlon.party.Party;
import net.youtunity.devathlon.user.User;
import org.bukkit.Bukkit;

import java.util.Set;

/**
 * Created by thecrealm on 16.07.16.
 */
public class IngameState extends State {

    private DevathlonPlugin plugin;

    public IngameState(DevathlonPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void onEnter() {

        for (User user : plugin.getUsers()) {
            Party party = plugin.findParty(user);
            Kit kit = party.getAvailableKits().get(0);
            user.assignKit(kit);
            user.getKit().giveItems(user);
            user.getPlayer().getInventory().setHeldItemSlot(8);
        }
    }

    @Override
    public void onQuit() {

    }
}
