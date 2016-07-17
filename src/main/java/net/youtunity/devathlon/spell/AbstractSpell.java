package net.youtunity.devathlon.spell;

import net.youtunity.devathlon.DevathlonPlugin;
import net.youtunity.devathlon.service.ServiceRegistry;
import net.youtunity.devathlon.user.User;
import net.youtunity.devathlon.user.UserService;

import java.util.function.Consumer;

/**
 * Created by thecrealm on 16.07.16.
 */
public abstract class AbstractSpell implements Spell {

    private DevathlonPlugin plugin;

    @Override
    public void execute(SpellContext context) {
        this.plugin = context.getPlugin();
        executeSpell(context);
    }

    protected abstract void executeSpell(SpellContext context);

    protected void allUsers(Consumer<User> func) {

        for (User user : ServiceRegistry.lookupService(UserService.class).getUsers()) {
            func.accept(user);
        }
    }
}
