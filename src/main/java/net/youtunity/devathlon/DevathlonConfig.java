package net.youtunity.devathlon;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.quartercode.quarterbukkit.api.shape.Cuboid;
import com.quartercode.quarterbukkit.api.shape.Cylinder;
import lombok.Data;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by thecrealm on 30.07.16.
 */
@Data
public class DevathlonConfig {

    private Location lobby;
    private List<Team> teams = new ArrayList<>();
    private List<Bay> bays = new ArrayList<>();
    private List<Location> loots = new ArrayList<>();

    public Bay createBay() {
        return new Bay();
    }

    public Team createTeam() {
        return new Team();
    }

    @Data
    public class Team {

        private String name;
        private Location lobbyMin;
        private Location lobbyMax;
        private int lobbyRadius;
        private Location spawn;

        public Cylinder toLobbyCylinder() {
            return new Cylinder(lobbyMin, lobbyMax, lobbyRadius);
        }
    }

    @Data
    public class Bay {

        private String name;
        private Location bayMin;
        private Location bayMax;
        private Location towerMin;
        private Location towerMax;
        private int towerRadius;

        public Cuboid toBayCuboid() {
            return new Cuboid(bayMin, bayMax);
        }

        public Cylinder toTowerCylinder() {
            return new Cylinder(towerMin, towerMax, towerRadius);
        }
    }
}
