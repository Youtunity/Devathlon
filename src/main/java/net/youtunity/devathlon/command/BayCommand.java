package net.youtunity.devathlon.command;

import com.quartercode.quarterbukkit.api.command.Command;
import com.quartercode.quarterbukkit.api.command.CommandHandler;
import com.quartercode.quarterbukkit.api.command.CommandInfo;
import com.quartercode.quarterbukkit.api.scheduler.ScheduleTask;
import net.youtunity.devathlon.DevathlonConfig;
import net.youtunity.devathlon.DevathlonPlugin;
import org.bukkit.Location;
import org.bukkit.entity.Player;

/**
 * Created by thecrealm on 30.07.16.
 */
public class BayCommand implements CommandHandler {

    private DevathlonPlugin plugin;

    @Override
    public CommandInfo getInfo() {
        return new CommandInfo(true, "<bay> <config>", "", "", "bay");
    }

    public BayCommand(DevathlonPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void execute(Command command) {

        Player player = ((Player) command.getSender());
        String bay = command.getArguments()[0];
        Location location = player.getLocation();

        player.sendMessage("Bay Command executing!");

        switch (command.getArguments()[1]) {
            case "bay-min":
                findBay(bay).setBayMin(location);
                return;
            case "bay-max":
                findBay(bay).setBayMax(location);
                return;
            case "tower-min":
                findBay(bay).setTowerMin(location);
                return;
            case "tower-max":
                findBay(bay).setTowerMax(location);
                return;
            case "tower-radius":
                findBay(bay).setTowerRadius(Integer.valueOf(command.getArguments()[2]));
                return;
            case "test":

                new ScheduleTask(plugin) {

                    private int counter = 0;

                    @Override
                    public void run() {

                        counter++;
                        if(counter >= 100) {
                             cancel();
                        }

                        plugin.getBayManager().findBay(bay).getTower().update();
                    }
                }.run(true, 0, 50);
        }
    }

    private DevathlonConfig.Bay findBay(String name) {

        for (DevathlonConfig.Bay bay : plugin.getDevathlonConfig().getBays()) {
            if(bay.getName().equals(name)) {
                return bay;
            }
        }

        DevathlonConfig.Bay bay = plugin.getDevathlonConfig().createBay();
        bay.setName(name);
        plugin.getDevathlonConfig().getBays().add(bay);
        return bay;
    }
}
