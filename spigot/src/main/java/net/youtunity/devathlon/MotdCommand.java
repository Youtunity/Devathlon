package net.youtunity.devathlon;

import net.youtunity.devathlon.api.protocol.interaction.UpdateMOTDRequest;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * Created by thecrealm on 25.07.16.
 */
public class MotdCommand implements CommandExecutor {

    private DevathlonPlugin plugin;

    public MotdCommand(DevathlonPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        String motd = String.join(" ", strings);
        plugin.getClient().sendMessage(new UpdateMOTDRequest(plugin.getServerName(), motd));
        commandSender.sendMessage("Updated Motd to: " + motd);
        return true;
    }
}
