package net.youtunity.devathlon;

import com.quartercode.quarterbukkit.api.MathUtil;
import com.quartercode.quarterbukkit.api.objectsystem.ActiveObjectSystem;
import com.quartercode.quarterbukkit.api.objectsystem.ObjectSystemDefinition;
import com.quartercode.quarterbukkit.api.objectsystem.Source;
import com.quartercode.quarterbukkit.api.objectsystem.object.ParticleDefinition;
import com.quartercode.quarterbukkit.api.objectsystem.object.ParticleObject;
import com.quartercode.quarterbukkit.api.objectsystem.object.ParticleType;
import com.quartercode.quarterbukkit.api.objectsystem.physics.PhysicsObject;
import com.quartercode.quarterbukkit.api.objectsystem.physics.VelocityModificationRule;
import com.quartercode.quarterbukkit.api.objectsystem.run.ObjectSystemRunner;
import com.quartercode.quarterbukkit.api.objectsystem.util.TargetedVelocityModifier;
import com.quartercode.quarterbukkit.api.scheduler.ScheduleTask;
import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by thecrealm on 25.07.16.
 */
public class SecretCommand implements CommandExecutor {

    private DevathlonPlugin plugin;

    public SecretCommand(DevathlonPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

            if(commandSender instanceof Player) {
                Player player = ((Player) commandSender);
                Location source = player.getLocation();
                source.getWorld().setDifficulty(Difficulty.NORMAL);
                source.getWorld().setTime(16000);

                BossBar bossBar = Bukkit.createBossBar("I0#09#20!6", BarColor.GREEN, BarStyle.SOLID, BarFlag.DARKEN_SKY);
                Bukkit.getOnlinePlayers().forEach(bossBar::addPlayer);


                ObjectSystemDefinition systemDef = new ObjectSystemDefinition();

                systemDef.addModificationRules(new VelocityModificationRule<>(PhysicsObject.class,
                        new TargetedVelocityModifier<>(new Vector()).setIgnoredComponenets(false, true, false)));

                systemDef.addSources(new OBSource());

                ObjectSystemRunner runner = new ObjectSystemRunner(plugin, new ActiveObjectSystem(systemDef, player.getLocation().add(0, 200, 0)), true);
                runner.setRunning(true);

                AtomicInteger counter = new AtomicInteger(0);
                new ScheduleTask(plugin) {

                    @Override
                    public void run() {

                        if(counter.get() % 25 == 0) {
                            source.getWorld().playSound(source, Sound.AMBIENT_CAVE, 1f, 1f);
                        }

                        if(counter.get() > 170 && counter.get() % 4 == 0 && counter.get() < 224) {
                            for (int i = 0; i < (counter.get() - 190 + 1) / 2; i++) {
                                source.getWorld().strikeLightning(source);
                            }
                        }

                        bossBar.setProgress(counter.get() / 230);

                        if(counter.incrementAndGet() > 230) {
                            cancel();
                            Entity entity = source.getWorld().spawnEntity(source, EntityType.GIANT);
                            new ScheduleTask(plugin) {
                                @Override
                                public void run() {
                                    bossBar.removeFlag(BarFlag.DARKEN_SKY);
                                    bossBar.setVisible(false);
                                    entity.remove();
                                }
                            }.run(true, 60 * 20);
                        }
                    }
                }.run(true, 0L, 1L);

            }
        return true;
    }

    private static class OBSource implements Source {

        public void update(Plugin plugin, ActiveObjectSystem objectSystem, Random random) {

            if (objectSystem.getLifetime() > MathUtil.getTicks(10 * 1000)) return;

            double distanceFromCenter = (objectSystem.getLifetime()) / 20;
            for (int counter = 0; counter < distanceFromCenter * 2; counter++) {
                double angle = random.nextDouble() * 2 * Math.PI;
                double xComponent = Math.cos(angle);
                double zComponent = Math.sin(angle);
                Vector initialPosition = new Vector(xComponent * distanceFromCenter, 0, zComponent * distanceFromCenter);

                double velocityFactor = distanceFromCenter / 10;
                Vector initialVelocity = new Vector(-zComponent * velocityFactor, -1, xComponent * velocityFactor);

                // Spawn the new objects
                objectSystem.addObjects(new ParticleObject(((int) MathUtil.getTicks(10 * 1000)), initialPosition, initialVelocity)
                        .addParticles(new ParticleDefinition().setType(ParticleType.FIREWORKS_SPARK)));
            }
        }
    }
}
