package net.youtunity.devathlon.command;

import com.quartercode.quarterbukkit.api.MathUtil;
import com.quartercode.quarterbukkit.api.command.Command;
import com.quartercode.quarterbukkit.api.command.CommandHandler;
import com.quartercode.quarterbukkit.api.command.CommandInfo;
import com.quartercode.quarterbukkit.api.scheduler.ScheduleTask;
import net.youtunity.devathlon.DevathlonPlugin;
import net.youtunity.devathlon.loot.LootSpawner;
import org.bukkit.entity.Player;

/**
 * Created by thecrealm on 30.07.16.
 */
public class LootCommand implements CommandHandler {

    private DevathlonPlugin plugin;

    @Override
    public CommandInfo getInfo() {
        return new CommandInfo(true, "<action>", "", "", "loot");
    }

    public LootCommand(DevathlonPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void execute(Command command) {
        Player player = (Player) command.getSender();

        switch (command.getArguments()[0]) {
            case "add":
                player.sendMessage("Adding Loot Position");
                plugin.getDevathlonConfig().getLoots().add(player.getLocation());
                return;
            case "debug":
                player.sendMessage("Starting LootSpawner");
                LootSpawner spawner = new LootSpawner(plugin);
                spawner.startRandomLoop();

                new ScheduleTask(plugin) {
                    @Override
                    public void run() {
                        spawner.stopRandomLoop();
                    }
                }.run(true, 2 * 60 * 1000);
        }
    }
}
