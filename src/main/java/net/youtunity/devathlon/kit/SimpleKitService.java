package net.youtunity.devathlon.kit;

import com.google.common.collect.Lists;
import net.youtunity.devathlon.DevathlonPlugin;
import net.youtunity.devathlon.service.Initializable;

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
        //Load kits from cfg
    }

    @Override
    public Optional<Kit> find(String name) {
        return kits.stream()
                .filter(kit -> kit.getName().equals(name))
                .findFirst();
    }


}
