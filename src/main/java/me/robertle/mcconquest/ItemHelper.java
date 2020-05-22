package me.robertle.mcconquest;

import com.google.common.base.Strings;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class ItemHelper {
    public ItemHelper() {
    }

    public static boolean hasName(ItemStack var0) {
        return var0 != null && var0.hasItemMeta() && var0.getItemMeta().hasDisplayName();
    }

    public static boolean hasLore(ItemStack var0) {
        return var0 != null && var0.hasItemMeta() && var0.getItemMeta().hasLore();
    }

    public static String getName(ItemStack var0) {
        return !hasName(var0) ? null : var0.getItemMeta().getDisplayName();
    }

    public static List<String> getLore(ItemStack var0) {
        return !hasLore(var0) ? null : var0.getItemMeta().getLore();
    }

    public static ItemStack setName(ItemStack var0, String var1) {
        if (var0 != null && !Strings.isNullOrEmpty(var1)) {
            var1 = Replacer.replace(var1);
            return ItemBuilder.of(var0).displayName(var1).asItemStack();
        } else {
            return var0;
        }
    }

    public static ItemStack setLore(ItemStack var0, List<String> var1) {
        if (var0 != null && var1 != null && !var1.isEmpty()) {
            var1 = Replacer.replace(var1);
            return ItemBuilder.of(var0).lore(var1).asItemStack();
        } else {
            return var0;
        }
    }

    public static ItemStack setNameAndLore(ItemStack var0, String var1, List<String> var2) {
        if (var0 == null) {
            return null;
        } else {
            var0 = setName(var0, var1);
            var0 = setLore(var0, var2);
            return var0;
        }
    }
}
