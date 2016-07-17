package net.youtunity.devathlon.kit;

import com.google.common.collect.Maps;
import net.youtunity.devathlon.service.ServiceRegistry;
import net.youtunity.devathlon.spell.Spell;
import net.youtunity.devathlon.spell.SpellMeta;
import net.youtunity.devathlon.spell.SpellService;
import net.youtunity.devathlon.user.User;
import net.youtunity.devathlon.utils.GlowUtils;
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

    public int getSpellIndex(String name) {

        SpellService spellService = ServiceRegistry.lookupService(SpellService.class);
        Optional<Spell> spell = spellService.find(name);

        if(spell.isPresent()) {
            SpellMeta meta = spellService.lookupMeta(spell.get()).get();
            for (Map.Entry<Integer, Spell> entry : availableSpells.entrySet()) {
                if (meta.name().equals(spellService.lookupMeta(entry.getValue()).get().name())) {
                    return entry.getKey();
                }
            }
        }

        return 0;
    }

    public void giveItems(User user) {
        availableSpells.forEach((integer, spell) -> {
            PlayerInventory inventory = user.getPlayer().getInventory();
            SpellMeta meta = ServiceRegistry.lookupService(SpellService.class).lookupMeta(spell).get();
            ItemStack itemStack = new ItemStack(meta.material());
            GlowUtils.addGlow(itemStack);
            inventory.setItem(integer, itemStack);
        });
    }
}
