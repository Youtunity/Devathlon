package net.youtunity.devathlon.bay;

import net.youtunity.devathlon.DevathlonConfig;
import net.youtunity.devathlon.DevathlonPlugin;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by thecrealm on 30.07.16.
 */
public class BayManager {

    private DevathlonPlugin plugin;
    private List<Bay> bays = new ArrayList<>();

    public BayManager(DevathlonPlugin plugin) {
        this.plugin = plugin;
    }

    public void init() {

        for (DevathlonConfig.Bay bay : plugin.getDevathlonConfig().getBays()) {
            bays.add(new Bay(bay.getName(), bay));
        }
    }

    public Bay findBay(String name) {
        for (Bay bay : getBays()) {
            if(bay.getName().equals(name)) {
                return bay;
            }
        }

        return null;
    }

    public List<Bay> getBays() {
        return bays;
    }
}
