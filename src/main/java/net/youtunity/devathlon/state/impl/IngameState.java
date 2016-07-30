package net.youtunity.devathlon.state.impl;

import net.youtunity.devathlon.DevathlonPlugin;
import net.youtunity.devathlon.state.CountedState;
import net.youtunity.devathlon.util.FormatUtils;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;

/**
 * Created by thecrealm on 30.07.16.
 */
public class IngameState extends CountedState {

    private BossBar infoBar;

    public IngameState(DevathlonPlugin plugin) {
        super(plugin, 600, 0, Direction.DOWN);
    }

    @Override
    protected void enter() {
        this.infoBar = Bukkit.createBossBar("INFO", BarColor.BLUE, BarStyle.SEGMENTED_20);

        //Teleport Players

    }

    @Override
    protected void leave() {

    }

    @Override
    protected void onCount(int count) {

        //Update Bossboar
        double percent = getEndCount() * 1D / getCurrentCount() * 1D;
        this.infoBar.setProgress(percent);
        this.infoBar.setTitle(FormatUtils.format(count));
    }
}
