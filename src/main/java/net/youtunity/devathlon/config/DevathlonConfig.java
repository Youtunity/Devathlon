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
    private List<PartyConfig> parties = Lists.newArrayList(new PartyConfig());

    public class PartyConfig extends YamlConfig {

        private String displayName = "default";

        private String[] kits = new String[] {
                "kit1",
                "kit2"
        };
    }


}
