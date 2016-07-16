package net.youtunity.devathlon.user;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.Maps;
import net.youtunity.devathlon.kit.Kit;
import org.bukkit.entity.Player;

import java.util.Map;

/**
 * Created by thecrealm on 16.07.16.
 */
public class User {

    private Player player;
    private Kit kit;

    private Map<String, Integer> cooldowns = Maps.newHashMap();

    public User(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public void assignKit(Kit kit) {
        this.kit = kit;
    }

    public Kit getKit() {
        return kit;
    }

    public boolean isCooldown(String spell) {
        return cooldowns.containsKey(spell);
    }

    public void cooldown(String spell, int cooldown) {
        cooldowns.put(spell, cooldown);
    }
}
