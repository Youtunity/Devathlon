package net.youtunity.devathlon.spell;

import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.reflect.ClassPath;
import net.youtunity.devathlon.DevathlonPlugin;
import net.youtunity.devathlon.service.Initializable;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Created by thecrealm on 16.07.16.
 */
public class SimpleSpellService implements SpellService, Initializable {

    private DevathlonPlugin plugin;
    private Map<Class<? extends Spell>, SpellMeta> metaCache = Maps.newHashMap();
    private List<Spell> spells = Lists.newArrayList();

    public SimpleSpellService(DevathlonPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void init() {

        try {
            ClassPath classPath = ClassPath.from(plugin.getClass().getClassLoader());
            ImmutableSet<ClassPath.ClassInfo> classes = classPath.getTopLevelClassesRecursive("net.youtunity.devathlon.spell.spells");

            for (ClassPath.ClassInfo info : classes) {
                Class<?> clazz = info.load();
                if(Spell.class.isAssignableFrom(clazz)) {
                    Object inst = clazz.newInstance();
                    if(inst.getClass().isAnnotationPresent(SpellMeta.class)) {
                        spells.add(((Spell) inst));
                        plugin.getLogger().info("Registered Spell: " + info.getSimpleName());
                    } else {
                        plugin.getLogger().warning("Failed to register spell '" + info.getSimpleName() + "', Meta not present");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<SpellMeta> lookupMeta(Spell spell) {
        return lookupMeta(spell.getClass());
    }

    @Override
    public Optional<SpellMeta> lookupMeta(Class<? extends Spell> spell) {
        Preconditions.checkNotNull(spell, "Spell");

        SpellMeta spellMeta = metaCache.get(spell);

        if(spellMeta == null) {
            if (spell.isAnnotationPresent(SpellMeta.class)) {
                metaCache.put(spell, spell.getAnnotation(SpellMeta.class));
                return lookupMeta(spell);
            } else {
                throw new IllegalStateException("Spell should have a SpellMeta");
            }
        }

        return Optional.ofNullable(spellMeta);
    }

    @Override
    public Optional<Spell> find(String name) {

        for (Spell spell : this.spells) {
            Optional<SpellMeta> meta = lookupMeta(spell);
            if(meta.isPresent()) {
                if (meta.get().name().equals(name)) {
                    return Optional.of(spell);
                }
            }
        }

        return Optional.empty();
    }
}
