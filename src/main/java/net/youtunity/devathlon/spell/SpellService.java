package net.youtunity.devathlon.spell;

import java.util.Optional;

/**
 * Created by thecrealm on 16.07.16.
 */
public interface SpellService {

    Optional<SpellMeta> lookupMeta(Spell spell);

    Optional<SpellMeta> lookupMeta(Class<? extends Spell> spell);

    Optional<Spell> find(String name);
}
