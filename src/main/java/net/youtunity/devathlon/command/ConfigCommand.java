package net.youtunity.devathlon.command;

import com.quartercode.quarterbukkit.api.command.Command;
import com.quartercode.quarterbukkit.api.command.CommandHandler;
import com.quartercode.quarterbukkit.api.command.CommandInfo;
import net.youtunity.devathlon.DevathlonPlugin;

/**
 * Created by thecrealm on 31.07.16.
 */
public class ConfigCommand implements CommandHandler {

    private DevathlonPlugin plugin;

    @Override
    public CommandInfo getInfo() {
        return new CommandInfo(true, "", "", "", "config");
    }

    public ConfigCommand(DevathlonPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void execute(Command command) {

        switch (command.getArguments()[0]) {
            case "save":
                plugin.saveConfig();
                command.getSender().sendMessage("Config saved!");
                return;
            case "load":
                plugin.loadConfig();
                command.getSender().sendMessage("Config loaded!");
        }
    }
}
