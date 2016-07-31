package net.youtunity.devathlon.bay;

import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.material.Wool;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by thecrealm on 31.07.16.
 */
public class BayAreaBlocks {

    private List<Location> locations = new ArrayList<>();
    private Bay bay;

    public BayAreaBlocks(Bay bay) {
        this.bay = bay;
    }

    public void scan() {
        locations.clear();

        for (Vector vector : bay.getConfig().toBayCuboid().getContent(1)) {
            if(vector.toLocation(bay.getConfig().getBayMin().getWorld()).getBlock().getType() == Material.WOOL) {
                locations.add(vector.toLocation(bay.getConfig().getBayMin().getWorld()));
            }
        }
    }

    public void setColor(DyeColor color) {
        Wool wool = new Wool(color);
        for (Location location : locations) {
            location.getBlock().setData(color.getWoolData());
        }
    }
}
