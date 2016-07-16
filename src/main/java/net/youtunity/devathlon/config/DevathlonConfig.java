package net.youtunity.devathlon.config;

import com.google.common.collect.Lists;
import net.cubespace.Yamler.Config.YamlConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by thecrealm on 16.07.16.
 */
public class DevathlonConfig extends YamlConfig {

    private int playersPerTeam = 3;
    private List<KitConfig> kits = Lists.newArrayList(new KitConfig(), new KitConfig());
    private List<PartyConfig> parties = Lists.newArrayList(new PartyConfig(), new PartyConfig());

    public int getPlayersPerTeam() {
        return playersPerTeam;
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

        public String getPartyName() {
            return partyName;
        }

        public String[] getKits() {
            return kits;
        }
    }


}
