package net.youtunity.devathlon.utils;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * Created by thecrealm on 17.07.16.
 */
public class GlowUtils {

    public static void addGlow(ItemStack itemStack) {
        itemStack.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        itemStack.setItemMeta(itemMeta);

    }

    public static void removeGlow(ItemStack itemStack) {
        itemStack.removeEnchantment(Enchantment.DURABILITY);
    }
}
