package net.youtunity.devathlon.config;

import com.google.common.collect.Lists;
import net.cubespace.Yamler.Config.YamlConfig;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by thecrealm on 16.07.16.
 */
public class DevathlonConfig extends YamlConfig {

    private int playersPerTeam;
    private Location spawn;
    private List<KitConfig> kits;
    private List<PartyConfig> parties;

    public int getPlayersPerTeam() {
        return playersPerTeam;
    }

    public Location getSpawn() {
        return spawn;
    }

    public List<KitConfig> getKits() {
        return kits;
    }

    public List<PartyConfig> getParties() {
        return parties;
    }

    public class  KitConfig extends YamlConfig {

        private String kitName;
        private String[] spells;

        public String getKitName() {
            return kitName;
        }

        public String[] getSpells() {
            return spells;
        }
    }

    public class PartyConfig extends YamlConfig {

        private String partyName;
        private String[] kits;

        //Locations
        private Location spawn;
        private Location egg;

        private Location cuboidOne;
        private Location cuboidTwo;


        public String getPartyName() {
            return partyName;
        }

        public String[] getKits() {
            return kits;
        }

        public Location getSpawn() {
            return spawn;
        }

        public Location getEgg() {
            return egg;
        }

        public Location getCuboidOne() {
            return cuboidOne;
        }

        public Location getCuboidTwo() {
            return cuboidTwo;
        }
    }


}
