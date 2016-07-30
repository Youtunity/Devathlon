package net.youtunity.devathlon;

import net.youtunity.devathlon.cuboid.CuboidManager;
import net.youtunity.devathlon.state.StateManager;
import net.youtunity.devathlon.state.impl.IngameState;
import net.youtunity.devathlon.state.impl.LobbyState;
import net.youtunity.devathlon.team.TeamManager;
import net.youtunity.devathlon.user.UserManager;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by thecrealm on 30.07.16.
 */
public class DevathlonPlugin extends JavaPlugin {

    private DevathlonConfig config;

    private UserManager userManager;
    private StateManager stateManager;
    private TeamManager teamManager;
    private CuboidManager cuboidManager;

    @Override
    public void onEnable() {

        this.userManager = new UserManager(this);
        this.userManager.init();

        this.cuboidManager = new CuboidManager(this);
        this.cuboidManager.init();

        this.teamManager = new TeamManager();

        this.stateManager = new StateManager();
        this.stateManager.registerState(new LobbyState(this));
        this.stateManager.registerState(new IngameState(this));
        this.stateManager.prepareAndStart();
    }

    public StateManager getStateManager() {
        return stateManager;
    }

    public TeamManager getTeamManager() {
        return teamManager;
    }

    public CuboidManager getCuboidManager() {
        return cuboidManager;
    }
}
