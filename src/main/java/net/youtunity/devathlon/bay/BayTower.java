package net.youtunity.devathlon.bay;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.wrappers.EnumWrappers;
import com.quartercode.quarterbukkit.api.shape.Cylinder;
import net.youtunity.devathlon.DevathlonPlugin;
import net.youtunity.devathlon.team.Team;
import net.youtunity.devathlon.user.User;
import net.youtunity.devathlon.util.ParticleUtils;
import org.bukkit.*;

import java.util.*;

/**
 * Created by thecrealm on 30.07.16.
 */
public class BayTower {

    private Bay bay;

    public BayTower(Bay bay) {
        this.bay = bay;
    }

    public void visualizeHolder() {

        for (Location location : pointsOnLineY(bay.getConfig().getTowerMin(), bay.getConfig().getTowerMax(), 25)) {
            location.getWorld().spawnParticle(Particle.CLOUD, location, 0, 0, 0, 0);
        }
    }

    public void checkCapturing(DevathlonPlugin plugin) {

        Map<Team, List<User>> users = new HashMap<>();
        Cylinder cylinder = bay.getConfig().toTowerCylinder();
        for (User user : plugin.getUserManager().getUsers()) {
            if(cylinder.intersects(user.getPlayer().getLocation())) {
                if(!users.containsKey(user.getTeam())) {
                    users.put(user.getTeam(), new ArrayList<>());
                }

                users.get(user.getTeam()).add(user);
            }
        }

        if(users.size() == 1) {
            //exactly one team want to capture this tower
            Set<Map.Entry<Team, List<User>>> raw = users.entrySet();
            Team catcher = null;
            for (Map.Entry<Team, List<User>> entry : raw) {
                catcher = entry.getKey();

                if(bay.getStatus() == Bay.CatchingStatus.CAPTURED && bay.getCatcher() == catcher) {
                    return;
                    //That we are!
                }

                bay.setCatchProgress(catcher, bay.getCatchProgress(catcher) + entry.getValue().size());
            }

            if(bay.getCatchProgress(catcher) >= 200) {
                bay.setCatchProgress(catcher, 0);
                switch (bay.getStatus()) {
                    case UNCAPTURED:
                        bay.setStatus(Bay.CatchingStatus.CAPTURED);
                        bay.setCatcher(catcher);
                        Bukkit.broadcastMessage(DevathlonPlugin.PREFIX + "Team " + catcher.getName() + " hat die Bucht " + bay.getName() + " eingenommen!");
                        bay.getBlocks().setColor(catcher.getDyeColor());
                        return;
                    case CAPTURED:
                        bay.setStatus(Bay.CatchingStatus.UNCAPTURED);
                        Bukkit.broadcastMessage(DevathlonPlugin.PREFIX + "Team " + bay.getCatcher().getName() + " hat die Bucht " + bay.getName() + " verloren!");
                        bay.getBlocks().setColor(DyeColor.GRAY);
                        bay.setCatcher(null);
                }
            }
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
