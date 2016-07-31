package net.youtunity.devathlon.bay;

import net.youtunity.devathlon.DevathlonConfig;
import net.youtunity.devathlon.team.Team;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by thecrealm on 30.07.16.
 */
public class Bay {

    private String name;
    private DevathlonConfig.Bay config;
    private BayTower tower;
    private BayAreaBlocks blocks;

    private CatchingStatus status = CatchingStatus.UNCAPTURED;
    private Team catcher;
    private Map<Team, Integer> captureProgress = new HashMap<>();

    public Bay(String name, DevathlonConfig.Bay config) {
        this.name = name;
        this.config = config;
        this.tower = new BayTower(this);
        this.blocks = new BayAreaBlocks(this);
        this.blocks.scan();

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

    public Team getCatcher() {
        return catcher;
    }

    public void setCatcher(Team catcher) {
        this.catcher = catcher;
    }

    public CatchingStatus getStatus() {
        return status;
    }

    public BayAreaBlocks getBlocks() {
        return blocks;
    }

    public void setStatus(CatchingStatus status) {
        this.status = status;
    }

    public int getCatchProgress(Team team) {

        if(!captureProgress.containsKey(team)) {
            captureProgress.put(team, 0);
        }

        return captureProgress.get(team);
    }

    public void setCatchProgress(Team team, int catchProgress) {
        captureProgress.replace(team, catchProgress);
    }

    public static enum CatchingStatus {

        CAPTURED,
        UNCAPTURED;
    }
}
