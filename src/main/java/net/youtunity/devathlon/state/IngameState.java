package net.youtunity.devathlon.state;

import net.youtunity.devathlon.DevathlonPlugin;
import net.youtunity.devathlon.kit.Kit;
import net.youtunity.devathlon.party.Party;
import net.youtunity.devathlon.user.User;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;

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

        for (User user : plugin.getUsers()) {
            this.bossBar.addPlayer(user.getPlayer());
            Party party = plugin.findParty(user);
            Kit kit = party.getAvailableKits().get(0);
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
