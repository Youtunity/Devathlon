package net.youtunity.devathlon.kit;

import com.google.common.collect.Maps;
import net.youtunity.devathlon.service.ServiceRegistry;
import net.youtunity.devathlon.spell.Spell;
import net.youtunity.devathlon.spell.SpellMeta;
import net.youtunity.devathlon.spell.SpellService;
import net.youtunity.devathlon.user.User;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.Map;
import java.util.Optional;

/**
 * Created by thecrealm on 16.07.16.
 */
public class Kit {

    private String name;
    private Map<Integer, Spell> availableSpells = Maps.newHashMap();

    public Kit(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    void addSpell(int slot, Spell spell) {
        availableSpells.put(slot, spell);
    }

    public Optional<Spell> getSpell(int index) {
        return Optional.ofNullable(availableSpells.get(index));
    }

    public void giveItems(User user) {
        availableSpells.forEach((integer, spell) -> {
            PlayerInventory inventory = user.getPlayer().getInventory();
            SpellMeta meta = ServiceRegistry.lookupService(SpellService.class).lookupMeta(spell).get();
            inventory.setItem(integer, new ItemStack(meta.material()));
        });
    }
}
