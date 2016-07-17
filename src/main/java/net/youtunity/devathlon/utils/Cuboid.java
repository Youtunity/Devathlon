package net.youtunity.devathlon.utils;

import org.bukkit.Location;
import org.bukkit.util.Vector;

/**
 * Created by thecrealm on 17.07.16.
 */
public class Cuboid {

    private Vector min;
    private Vector max;

    public Cuboid(Vector one, Vector two) {

        double minX, minY, minZ;
        double maxX, maxY, maxZ;

        if(one.getX() > two.getX()) {
            maxX = one.getX();
            minX = two.getX();
        } else {
            maxX = two.getX();
            minX = one.getX();
        }

        if(one.getY() > two.getY()) {
            maxY = one.getY();
            minY = two.getY();
        } else {
            maxY = two.getY();
            minY = one.getY();
        }

        if(one.getZ() > two.getZ()) {
            maxZ = one.getZ();
            minZ = two.getZ();
        } else {
            maxZ = two.getZ();
            minZ = one.getZ();
        }

        this.min = new Vector(minX, minY, minZ);
        this.max = new Vector(maxX, maxY, maxZ);
    }

    public Cuboid(Location one, Location two) {
        this(one.toVector(), two.toVector());
    }

    public boolean intersects(Location test) {
        return test.toVector().isInAABB(min, max);
    }
}
