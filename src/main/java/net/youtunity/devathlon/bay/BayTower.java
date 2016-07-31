package net.youtunity.devathlon.bay;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.wrappers.EnumWrappers;
import net.youtunity.devathlon.util.ParticleUtils;
import org.bukkit.Color;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Particle;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by thecrealm on 30.07.16.
 */
public class BayTower {

    private Bay bay;

    public BayTower(Bay bay) {
        this.bay = bay;
    }

    public void update() {

        for (Location location : pointsOnLineY(bay.getConfig().getTowerMin(), bay.getConfig().getTowerMax(), 30)) {
            ParticleUtils.sendColoredDust(location, Color.AQUA.getRed(), Color.AQUA.getGreen(), 255);
            System.out.println(location);
        }
    }

    private List<Location> pointsOnLineY(Location one, Location two, int points) {

        List<Location> out = new ArrayList<>();
        Location min, max;

        if(one.getY() > two.getY()) {
            min = two;
            max = one;
        } else {
            min = one;
            max = two;
        }

        double distance = max.getY() - min.getY();
        double addPerRount = distance / points;
        for (int i = 0; i < points; i++) {
            out.add(min.clone().add(0, addPerRount * i, 0));
        }

        return out;
    }
}
