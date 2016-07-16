package net.youtunity.devathlon;

import net.youtunity.devathlon.state.State;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by thecrealm on 16.07.16.
 */
public class DevathlonPlugin extends JavaPlugin {

    private State currentGameState = null;

    @Override
    public void onEnable() {
        getLogger().info("Enabled!");
    }



    
}
