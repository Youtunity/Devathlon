package net.youtunity.devathlon.spell;

import com.google.common.collect.Maps;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by thecrealm on 16.07.16.
 */
public class SpellMetaCache {

    private static Map<Class<?>, SpellMeta> cache = Maps.newHashMap();

    public static SpellMeta get(Class<?> spell) {
        SpellMeta spellMeta = cache.get(spell);

        if(spellMeta == null) {
            if (spell.isAnnotationPresent(SpellMeta.class)) {
                spellMeta = cache.put(spell, spell.getAnnotation(SpellMeta.class));
            } else {
                throw new IllegalStateException("Spell should have a SpellMeta");
            }
        }

        return spellMeta;
    }
}
