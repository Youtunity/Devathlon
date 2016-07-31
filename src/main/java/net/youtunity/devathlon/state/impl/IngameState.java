package net.youtunity.devathlon.state.impl;

import net.youtunity.devathlon.DevathlonPlugin;
import net.youtunity.devathlon.state.CountedState;
import net.youtunity.devathlon.team.Team;
import net.youtunity.devathlon.util.FormatUtils;
import org.bukkit.*;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

/**
 * Created by thecrealm on 30.07.16.
 */
public class IngameState extends CountedState implements Listener {

    private BossBar infoBar;

    public IngameState(DevathlonPlugin plugin) {
        super(plugin, 600, 0, Direction.DOWN);
    }

    @Override
    protected void enter() {

        //Reset world
        World world = plugin.getTeamManager().getTeams().get(0).getConfig().getSpawn().getWorld();
        world.setTime(6000);
        world.setDifficulty(Difficulty.EASY);
        world.setPVP(true);
        world.setThundering(false);
        world.setStorm(false);

        this.infoBar = Bukkit.createBossBar("INFO", BarColor.BLUE, BarStyle.SEGMENTED_20);

        //Player actions
        plugin.getUserManager().getUsers().forEach(user -> {
            Team team = user.getTeam();
            user.getPlayer().teleport(team.getConfig().getSpawn());
            user.getPlayer().getInventory().clear();
            user.getPlayer().setGameMode(GameMode.SURVIVAL);
            user.getPlayer().setHealth(20D);
            user.getPlayer().setFoodLevel(20);
            user.getPlayer().setAllowFlight(false);
            infoBar.addPlayer(user.getPlayer());
        });

    }

    @Override
    protected void leave() {

    }

    @Override
    protected void onCount(int count) {

        //Update Bossboar
        double percent = getEndCount() * 1D / getCurrentCount() * 1D;
        this.infoBar.setProgress(percent);
        this.infoBar.setTitle(FormatUtils.format(count));
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {

        Location to = event.getTo();

        if (to.getBlock().getType() == Material.STATIONARY_WATER) {

            if(to.clone().add(0, 1, 0).getBlock().getType() == Material.STATIONARY_WATER ||
                    to.clone().add(0, -1, 0).getBlock().getType() == Material.STATIONARY_WATER) {
                event.getPlayer().setHealth(0D);
            }
        }
    }
}
