package net.youtunity.devathlon.kit;

import com.google.common.collect.Lists;
import net.youtunity.devathlon.DevathlonPlugin;
import net.youtunity.devathlon.config.DevathlonConfig;
import net.youtunity.devathlon.service.Initializable;
import net.youtunity.devathlon.service.ServiceRegistry;
import net.youtunity.devathlon.spell.Spell;
import net.youtunity.devathlon.spell.SpellService;

import java.util.List;
import java.util.Optional;

/**
 * Created by thecrealm on 16.07.16.
 */
public class SimpleKitService implements KitService, Initializable {

    private DevathlonPlugin plugin;
    private List<Kit> kits = Lists.newArrayList();

    public SimpleKitService(DevathlonPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void init() {

        SpellService spellService = ServiceRegistry.lookupService(SpellService.class);

        for (DevathlonConfig.KitConfig config : plugin.getDevathlonConfig().getKits()) {
            Kit kit = new Kit(config.getKitName());

            for (int i = 0; i < config.getSpells().length; i++) {
                Optional<Spell> spell = spellService.find(config.getSpells()[i]);
                if(spell.isPresent()) {
                    kit.addSpell(i, spell.get());
                }
            }

            kits.add(kit);
            plugin.getLogger().info("Registered Kit: " + kit.getName());
        }
    }

    @Override
    public Optional<Kit> find(String name) {
        return kits.stream()
                .filter(kit -> kit.getName().equals(name))
                .findFirst();
    }


}
