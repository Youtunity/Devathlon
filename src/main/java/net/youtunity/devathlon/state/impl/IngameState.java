package net.youtunity.devathlon.state.impl;

import com.quartercode.quarterbukkit.api.scheduler.ScheduleTask;
import net.youtunity.devathlon.DevathlonPlugin;
import net.youtunity.devathlon.bay.Bay;
import net.youtunity.devathlon.loot.LootSpawner;
import net.youtunity.devathlon.state.CountedState;
import net.youtunity.devathlon.team.Team;
import net.youtunity.devathlon.user.User;
import net.youtunity.devathlon.util.FormatUtils;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Boat;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

/**
 * Created by thecrealm on 30.07.16.
 */
public class IngameState extends CountedState implements Listener {

    private BossBar infoBar;
    private Scoreboard scoreboard;
    private Objective objective;
    private ScheduleTask logicTask;

    public IngameState(DevathlonPlugin plugin) {
        super(plugin, 600, 0, Direction.DOWN);
    }

    @Override
    protected void enter() {

        Bukkit.getPluginManager().registerEvents(this, plugin);

        //Reset world
        World world = plugin.getTeamManager().getTeams().get(0).getConfig().getSpawn().getWorld();
        world.setTime(6000);
        world.setDifficulty(Difficulty.EASY);
        world.setPVP(true);
        world.setThundering(false);
        world.setStorm(false);

        for (Entity e : world.getEntities()) {
            if(e instanceof Boat || e instanceof Item) {
                e.remove();
            }
        }

        //Info
        this.infoBar = Bukkit.createBossBar("INFO", BarColor.BLUE, BarStyle.SEGMENTED_20);
        this.scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        this.objective = scoreboard.registerNewObjective("game", "dummy");
        this.objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        for (Team team : plugin.getTeamManager().getTeams()) {
            objective.getScore(team.getName()).setScore(team.getTickets());
        }

        LootSpawner spawner = new LootSpawner(plugin);
        spawner.startRandomLoop();

        //Player actions
        plugin.getUserManager().getUsers().forEach(user -> {
            Team team = user.getTeam();
            user.getPlayer().teleport(team.getConfig().getSpawn());
            user.getPlayer().getInventory().clear();
            user.getPlayer().setGameMode(GameMode.SURVIVAL);
            user.getPlayer().setHealth(20D);
            user.getPlayer().setFoodLevel(20);

            infoBar.addPlayer(user.getPlayer());
            user.getPlayer().setScoreboard(scoreboard);
        });

        this.logicTask = new ScheduleTask(plugin) {

            int counter = 0;

            @Override
            public void run() {

                for (Bay bay : plugin.getBayManager().getBays()) {
                    bay.getTower().visualizeHolder();
                    bay.getTower().checkCapturing(plugin);

                    if (counter >= 70) {
                        if (bay.getCatcher() != null) {
                            bay.getCatcher().setTickets(bay.getCatcher().getTickets() + 1);
                        }
                    }
                }

                if(counter >= 70) {
                    counter = 0;
                    for (Team team : plugin.getTeamManager().getTeams()) {
                        team.setTickets(team.getTickets() - 2);
                        objective.getScore(team.getName()).setScore(team.getTickets());

                        if(team.getTickets() <= 0) {
                            for (Team found : plugin.getTeamManager().getTeams()) {
                                if(!team.equals(found)) {
                                    Bukkit.broadcastMessage(DevathlonPlugin.PREFIX + "Team " + found.getName() + " hat das Spiel gewonnen!");

                                    if(this.isRunning()) {
                                        cancel();
                                    }

                                    plugin.getStateManager().doNextState();
                                }
                            }
                        }
                    }
                }

                counter++;
            }
        };

        this.logicTask.run(true, 0, 50);
    }

    @Override
    protected void leave() {

        if(this.logicTask != null && logicTask.isRunning()) {
            logicTask.cancel();
        }

        infoBar.setVisible(false);

        for (User user : plugin.getUserManager().getUsers()) {
            user.getPlayer().setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());
        }

        for (Bay bay : plugin.getBayManager().getBays()) {
            bay.getBlocks().setColor(DyeColor.GRAY);
        }
    }

    @Override
    protected void onCount(int count) {

        //Update Bossboar
        double percent = count * 1D / getInitialCount() * 1D;
        this.infoBar.setProgress(percent);
        this.infoBar.setTitle(FormatUtils.format(count));
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onJoin(PlayerLoginEvent e) {
        e.disallow(PlayerLoginEvent.Result.KICK_FULL, "Game already started!");
    }

//    @EventHandler
//    public void onMove(PlayerMoveEvent e) {
//        Location newLoc = e.getTo();
//
//        // If the player's head is inside water, the water's clearly at least two blocks deep
//        if (newLoc.clone().add(0, 1, 0).getBlock().isLiquid()) {
//            e.getPlayer().remove();
//        }
//        // If the blocks at and below the player's feet are water, then he must be in two-deep water as well
//        else if (newLoc.getBlock().isLiquid() && newLoc.clone().subtract(0, 1, 0).getBlock().isLiquid()) {
//            e.getPlayer().remove();
//        }
//    }
}
