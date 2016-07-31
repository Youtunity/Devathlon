package net.youtunity.devathlon.command;

import com.quartercode.quarterbukkit.api.command.Command;
import com.quartercode.quarterbukkit.api.command.CommandHandler;
import com.quartercode.quarterbukkit.api.command.CommandInfo;
import net.youtunity.devathlon.DevathlonPlugin;
import org.bukkit.entity.Player;

/**
 * Created by thecrealm on 30.07.16.
 */
public class LobbyCommand implements CommandHandler {

    private DevathlonPlugin plugin;

    @Override
    public CommandInfo getInfo() {
        return new CommandInfo(true, "", "", "", "lobby");
    }

    public LobbyCommand(DevathlonPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void execute(Command command) {
        plugin.getDevathlonConfig().setLobby(((Player) command.getSender()).getLocation());
        command.getSender().sendMessage("Lobby executed!");
    }
}
