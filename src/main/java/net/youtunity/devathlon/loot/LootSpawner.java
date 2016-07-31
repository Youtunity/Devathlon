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
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.inventory.ItemStack;
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

        new ScheduleTask(plugin) {
            @Override
            public void run() {
                spawnRandomLoot(location);
            }
        }.run(true, 5000);
    }

    //FUCK I HAVE NOT TIME
    ItemStack[][] item = new ItemStack[][] {
            {
                    new ItemStack(Material.COOKED_BEEF, 17),
                    new ItemStack(Material.IRON_CHESTPLATE),
                    new ItemStack(Material.LEATHER_BOOTS),
                    new ItemStack(Material.IRON_AXE),
                    new ItemStack(Material.DIAMOND_BOOTS),
                    new ItemStack(Material.APPLE, 3),

            },
            {
                    new ItemStack(Material.BEETROOT, 14),
                    new ItemStack(Material.IRON_LEGGINGS, 2),
                    new ItemStack(Material.WOOD_SWORD),
                    new ItemStack(Material.IRON_HOE),
                    new ItemStack(Material.CHAINMAIL_CHESTPLATE),
                    new ItemStack(Material.APPLE, 8),
            },
            {
                    new ItemStack(Material.GOLD_HELMET, 2),
                    new ItemStack(Material.LEATHER_BOOTS),
                    new ItemStack(Material.CHAINMAIL_CHESTPLATE),
                    new ItemStack(Material.IRON_SWORD),
                    new ItemStack(Material.BAKED_POTATO, 15),
                    new ItemStack(Material.APPLE, 3),
            },
            {
                    new ItemStack(Material.DIAMOND_SWORD, 1),
                    new ItemStack(Material.LEATHER_BOOTS),
                    new ItemStack(Material.RABBIT_STEW, 7),
            },
            {
                    new ItemStack(Material.GOLD_LEGGINGS, 17),
                    new ItemStack(Material.IRON_AXE),
                    new ItemStack(Material.LEATHER_HELMET),
                    new ItemStack(Material.DIAMOND_AXE),
                    new ItemStack(Material.APPLE, 4),
            },
            {
                    new ItemStack(Material.IRON_CHESTPLATE, 3),
                    new ItemStack(Material.LEATHER_HELMET),
                    new ItemStack(Material.LEATHER_BOOTS),
                    new ItemStack(Material.IRON_SWORD),
            },
            {
                    new ItemStack(Material.BOW, 4),
                    new ItemStack(Material.ARROW, 64),
                    new ItemStack(Material.ARROW, 32),
                    new ItemStack(Material.WOOD_AXE),
                    new ItemStack(Material.GOLDEN_APPLE, 2),
            },
            {
                    new ItemStack(Material.GOLD_SWORD, 1),
                    new ItemStack(Material.IRON_CHESTPLATE),
                    new ItemStack(Material.DIAMOND_BOOTS),
                    new ItemStack(Material.CHAINMAIL_LEGGINGS),
                    new ItemStack(Material.APPLE, 8),
            }
    };


    public void spawnRandomLoot(Location location) {

        Random random = new Random();
        int length = item.length;
        ItemStack[] array = item[random.nextInt(length)];
        location.getWorld().playSound(location, Sound.ENTITY_MINECART_INSIDE, 1f, 1f);
        for (ItemStack itemStack : array) {
            location.getWorld().dropItem(location, itemStack);
        }
    }
}
