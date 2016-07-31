package net.youtunity.devathlon;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonWriter;
import com.quartercode.quarterbukkit.api.command.CommandExecutor;
import net.youtunity.devathlon.bay.BayManager;
import net.youtunity.devathlon.command.*;
import net.youtunity.devathlon.gson.GsonLocationAdapter;
import net.youtunity.devathlon.state.StateManager;
import net.youtunity.devathlon.state.impl.EndState;
import net.youtunity.devathlon.state.impl.IngameState;
import net.youtunity.devathlon.state.impl.LobbyState;
import net.youtunity.devathlon.team.TeamManager;
import net.youtunity.devathlon.user.UserManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;

/**
 * Created by thecrealm on 30.07.16.
 */
public class DevathlonPlugin extends JavaPlugin {

    public static String PREFIX = "§7[§cBayHunters§7]§3 ";

    private File configFile;
    private DevathlonConfig config;

    private UserManager userManager;
    private StateManager stateManager;
    private TeamManager teamManager;
    private BayManager bayManager;

    private Gson gson = new GsonBuilder()
            .registerTypeAdapter(Location.class, new GsonLocationAdapter())
            .setPrettyPrinting()
            .create();

    @Override
    public void onEnable() {

        //Config
        this.configFile = new File(getDataFolder() + File.separator + "config.json");
        loadConfig();

        this.userManager = new UserManager(this);
        this.userManager.init();

        this.teamManager = new TeamManager(this);
        this.teamManager.init();

        this.bayManager = new BayManager(this);
        this.bayManager.init();

        this.stateManager = new StateManager();
        this.stateManager.registerState(new LobbyState(this));
        this.stateManager.registerState(new IngameState(this));
        this.stateManager.registerState(new EndState(this));

        CommandExecutor setup = new CommandExecutor(this, "setup");
        setup.addCommandHandler(new LobbyCommand(this));
        setup.addCommandHandler(new BayCommand(this));
        setup.addCommandHandler(new TeamCommand(this));
        setup.addCommandHandler(new LootCommand(this));
        setup.addCommandHandler(new ConfigCommand(this));

        //Utils
        new BoatListener(this);

        this.stateManager.prepareAndStart();
    }

    public Gson getGson() {
        return gson;
    }

    public StateManager getStateManager() {
        return stateManager;
    }

    public UserManager getUserManager() {
        return userManager;
    }

    public TeamManager getTeamManager() {
        return teamManager;
    }

    public File getConfigFile() {
        return configFile;
    }

    public BayManager getBayManager() {
        return bayManager;
    }

    public DevathlonConfig getDevathlonConfig() {
        return config;
    }

    public void loadConfig() {

        if(!getConfigFile().exists()) {
            this.config = new DevathlonConfig();
            saveConfig();
        }

        try (Reader reader = new FileReader(getConfigFile())) {
            this.config = getGson().fromJson(reader, DevathlonConfig.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveConfig() {
        try (JsonWriter writer = new JsonWriter(new FileWriter(getConfigFile()))) {
            getGson().toJson(this.config, DevathlonConfig.class, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
