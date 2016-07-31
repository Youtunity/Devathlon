package net.youtunity.devathlon.bay;

import net.youtunity.devathlon.DevathlonConfig;
import net.youtunity.devathlon.team.Team;

/**
 * Created by thecrealm on 30.07.16.
 */
public class Bay {

    private String name;
    private DevathlonConfig.Bay config;
    private BayTower tower;
    private Team hold;

    public Bay(String name, DevathlonConfig.Bay config) {
        this.name = name;
        this.config = config;
        this.tower = new BayTower(this);
    }

    public String getName() {
        return name;
    }

    public DevathlonConfig.Bay getConfig() {
        return config;
    }

    public BayTower getTower() {
        return tower;
    }

    public Team getHolder() {
        return hold;
    }

    public void setHolder(Team hold) {
        this.hold = hold;
    }
}
