package net.youtunity.devathlon.spell;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by thecrealm on 16.07.16.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface SpellMeta {

    String name();

    int manaUsage() default 5;

    int cooldown() default 10;

    Material material() default Material.STICK;
}
