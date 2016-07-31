package net.youtunity.devathlon.command;

import com.quartercode.quarterbukkit.api.command.Command;
import com.quartercode.quarterbukkit.api.command.CommandHandler;
import com.quartercode.quarterbukkit.api.command.CommandInfo;
import net.youtunity.devathlon.DevathlonConfig;
import net.youtunity.devathlon.DevathlonPlugin;
import org.bukkit.Location;
import org.bukkit.entity.Player;

/**
 * Created by thecrealm on 30.07.16.
 */
public class TeamCommand implements CommandHandler {

    private DevathlonPlugin plugin;

    @Override
    public CommandInfo getInfo() {
        return new CommandInfo(true, "<team> <config> <param>", "", "", "team");
    }

    public TeamCommand(DevathlonPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void execute(Command command) {

        Player player = ((Player) command.getSender());
        String team = command.getArguments()[0];
        Location location = player.getLocation();

        player.sendMessage("Team Command executing!");

        switch (command.getArguments()[1]) {
            case "lobby-min":
                findTeam(team).setLobbyMin(location);
                return;
            case "lobby-max":
                findTeam(team).setLobbyMax(location);
                return;
            case "lobby-radius":
                findTeam(team).setLobbyRadius(Integer.valueOf(command.getArguments()[2]));
                return;
            case "spawn":
                findTeam(team).setSpawn(location);
        }
    }

    private DevathlonConfig.Team findTeam(String name) {

        for (DevathlonConfig.Team team : plugin.getDevathlonConfig().getTeams()) {
            if(team.getName().equals(name)) {
                return team;
            }
        }

        DevathlonConfig.Team team = plugin.getDevathlonConfig().createTeam();
        team.setName(name);
        plugin.getDevathlonConfig().getTeams().add(team);
        return team;
    }
}
