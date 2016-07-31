package net.youtunity.devathlon.loot;

import com.quartercode.quarterbukkit.api.objectsystem.ActiveObjectSystem;
import com.quartercode.quarterbukkit.api.objectsystem.ObjectSystemDefinition;
import com.quartercode.quarterbukkit.api.objectsystem.physics.PhysicsObject;
import com.quartercode.quarterbukkit.api.objectsystem.physics.VelocityModificationRule;
import com.quartercode.quarterbukkit.api.objectsystem.run.ObjectSystemRunner;
import com.quartercode.quarterbukkit.api.objectsystem.util.TargetedVelocityModifier;
import com.quartercode.quarterbukkit.api.scheduler.ScheduleTask;
import net.youtunity.devathlon.DevathlonPlugin;
import org.bukkit.Location;
import org.bukkit.util.Vector;

import java.util.List;
import java.util.Random;

/**
 * Created by thecrealm on 30.07.16.
 */
public class LootSpawner {

    private Random random = new Random();
    private DevathlonPlugin plugin;
    private ScheduleTask scheduleTask;

    public LootSpawner(DevathlonPlugin plugin) {
        this.plugin = plugin;
    }

    public void startRandomLoop() {

        this.scheduleTask = new ScheduleTask(plugin) {
            @Override
            public void run() {
                if(random .nextInt(15) == 0) {
                    spawnRandom();
                }
            }
        };

        this.scheduleTask.run(true, 50, 1000);
    }

    public void stopRandomLoop() {
        this.scheduleTask.cancel();
    }

    public void spawnRandom() {
        List<Location> loots = plugin.getDevathlonConfig().getLoots();
        spawn(loots.get(random.nextInt(loots.size())));
    }

    public void spawn(Location location) {

        ObjectSystemDefinition definition = new ObjectSystemDefinition();
        definition.addModificationRules(new VelocityModificationRule<>(PhysicsObject.class,
                new TargetedVelocityModifier<>(new Vector()).setIgnoredComponenets(false, true, false)));

        definition.addSources(new LootSource());

        ObjectSystemRunner runner = new ObjectSystemRunner(plugin, new ActiveObjectSystem(definition, location), true);
        runner.setRunning(true);
    }
}
