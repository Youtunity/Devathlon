package net.youtunity.devathlon.spell;

import net.youtunity.devathlon.DevathlonPlugin;
import net.youtunity.devathlon.party.Party;
import net.youtunity.devathlon.party.PartyService;
import net.youtunity.devathlon.service.ServiceRegistry;
import net.youtunity.devathlon.user.User;
import net.youtunity.devathlon.user.UserService;
import org.bukkit.Bukkit;

import java.util.Iterator;
import java.util.Map;

/**
 * Created by thecrealm on 16.07.16.
 */
public class SpellExecuter {

    private static DevathlonPlugin plugin;
    private static UserService userService;
    private static SpellService spellService;
    private static PartyService partyService;

    public static void init(DevathlonPlugin plugin) {
        SpellExecuter.plugin = plugin;
        SpellExecuter.userService = ServiceRegistry.lookupService(UserService.class);
        SpellExecuter.spellService = ServiceRegistry.lookupService(SpellService.class);
        SpellExecuter.partyService = ServiceRegistry.lookupService(PartyService.class);

        Bukkit.getScheduler().runTaskTimer(plugin, new CooldownRunner(), 10L, 20L);
    }

    public static SpellContext execute(User user, Spell spell) {

        SpellMeta meta = spellService.lookupMeta(spell).get();
        Party party = partyService.find(user).get();

        if(user.isCooldown(meta.name())) {
            SpellContext context = new SpellContext(plugin, user, party);
            context.setExecuted(false);
            return context;
        }

        user.cooldown(meta.name(), meta.cooldown());
        SpellContext context = new SpellContext(plugin, user, party);
        spell.execute(context);
        context.setExecuted(true);
        return context;
    }

    private static class CooldownRunner implements Runnable {

        @Override
        public void run() {

            for (User user : userService.getUsers()) {

                for(Iterator<Map.Entry<String, Integer>> it = user.getCooldowns().entrySet().iterator(); it.hasNext();) {
                    Map.Entry<String, Integer> next = it.next();
                    if(next.getValue() == 0) {
                        it.remove();
                    } else {
                        int index = user.getKit().getSpellIndex(next.getKey());
                        user.getPlayer().getInventory().getItem(index).setAmount(next.getValue());
                    }

                    next.setValue(next.getValue() -1);
                }
            }
        }
    }
}
