package net.youtunity.devathlon.util;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.EnumWrappers;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by thecrealm on 30.07.16.
 */
public class ParticleUtils {

    public static void sendColoredDust(Location origin, int red, int green, int blue) {
        origin.getWorld().spigot().playEffect(origin, Effect.COLOURED_DUST, 0, 0, (float) red / 255, (float) green / 255, (float) blue / 255, 1, 0, 30);
    }
}
