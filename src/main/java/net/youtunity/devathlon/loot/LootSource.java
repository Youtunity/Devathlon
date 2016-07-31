package net.youtunity.devathlon.loot;

import com.quartercode.quarterbukkit.api.MathUtil;
import com.quartercode.quarterbukkit.api.objectsystem.ActiveObjectSystem;
import com.quartercode.quarterbukkit.api.objectsystem.Source;
import com.quartercode.quarterbukkit.api.objectsystem.object.ParticleDefinition;
import com.quartercode.quarterbukkit.api.objectsystem.object.ParticleObject;
import com.quartercode.quarterbukkit.api.objectsystem.object.ParticleType;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

import java.util.Random;

/**
 * Created by thecrealm on 30.07.16.
 */
public class LootSource implements Source {

    @Override
    public void update(Plugin plugin, ActiveObjectSystem objectSystem, Random random) {

        if(objectSystem.getLifetime() > MathUtil.getTicks(6 * 1000)) return;
        double distanceFromCenter = (objectSystem.getLifetime()) / 20;

        for (int counter = 0; counter < distanceFromCenter * 2; counter++) {
            double angle = random.nextDouble() * 2 * Math.PI;
            double xComponent = Math.cos(angle);
            double zComponent = Math.sin(angle);
            Vector initialPosition = new Vector(xComponent * distanceFromCenter, 150, zComponent * distanceFromCenter);
            double velocityFactor = distanceFromCenter / 10;
            Vector initialVelocity = new Vector(-zComponent * velocityFactor, -1, xComponent * velocityFactor);

            objectSystem.addObjects(new ParticleObject(((int) MathUtil.getTicks(8 * 1000)), initialPosition, initialVelocity)
                    .addParticles(new ParticleDefinition().setType(ParticleType.FIREWORKS_SPARK)));
        }
    }
}