package me.robertle.mcconquest;

import com.hazebyte.crate.api.util.ItemHelper;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class InventoryUtil {

    public static ItemStack[] loadItemStackList(String path, int size) {
        ItemStack[] is = new ItemStack[size];
        List<ItemStack> ls = (List<ItemStack>) Clan.clanConfig.get(path);
        for (int n = 0; n < size; n++) {
            if (ls.get(n) != null) is[n] = ls.get(n);
        }
        return is;
    }

    public static boolean hasEnchant(ItemStack item, Enchantment enchantment) {
        if (item.containsEnchantment(enchantment)) return true;
        return false;
    }

    public static boolean hasCustomEnchant(Player player, String enchantment) {
        if (player.getInventory().getArmorContents().length > 0) {
            for (ItemStack itemStack : player.getInventory().getArmorContents()) {
                if (ItemHelper.hasLore(itemStack)) {
                    List<String> lores = ItemHelper.getLore(itemStack);
                    if (lores.size() > 3 && lores.get(3).contains(enchantment)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static int getEnchantLevel(Player player, String enchantment) {
        int level = 0;
        if (player.getInventory().getArmorContents().length > 0) {
            for (ItemStack itemStack : player.getInventory().getArmorContents()) {
                if (ItemHelper.hasLore(itemStack)) {
                    List<String> lores = ItemHelper.getLore(itemStack);
                    if (lores.size() > 3 && lores.get(3).contains(enchantment)) {
                        if (Integer.parseInt(lores.get(3).substring(lores.get(3).length() - 1)) > level)
                            level = Integer.parseInt(lores.get(3).substring(lores.get(3).length() - 1));
                    }
                }
            }
        }
        return level;
    }

}
