package net.youtunity.devathlon.gson;

import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.io.IOException;
import java.util.Map;

/**
 * Created by thecrealm on 31.07.16.
 */
public class GsonLocationAdapter extends TypeAdapter<Location> {

    @Override
    public void write(JsonWriter writer, Location location) throws IOException {

        if(location == null) {
            writer.nullValue();
            return;
        }

        writer.beginObject();
        writer.name("world").value(location.getWorld().getName());
        writer.name("x").value(location.getX());
        writer.name("y").value(location.getY());
        writer.name("z").value(location.getZ());
        writer.name("yaw").value(location.getYaw());
        writer.name("pitch").value(location.getPitch());
        writer.endObject();
    }

    @Override
    public Location read(JsonReader reader) throws IOException {

        if(reader.peek() == JsonToken.NULL) {
            reader.nextNull();
            return null;
        }

        reader.beginObject();

        reader.skipValue();
        World world = Bukkit.getWorld(reader.nextString());
        reader.skipValue();
        double x = reader.nextDouble();
        reader.skipValue();
        double y = reader.nextDouble();
        reader.skipValue();
        double z = reader.nextDouble();

        reader.skipValue();
        float yaw = ((float) reader.nextDouble());
        reader.skipValue();
        float pitch = ((float) reader.nextDouble());

        reader.endObject();

        return new Location(world, x, y, z, yaw, pitch);
    }
}
