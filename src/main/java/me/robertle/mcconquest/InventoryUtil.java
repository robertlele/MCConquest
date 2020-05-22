package me.robertle.mcconquest;

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

    public static void removeAnItemInHand(Player player) {
        if (player.getInventory().getItemInMainHand().getAmount() != 1) {
            player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);
        } else {
            player.getInventory().removeItem(player.getInventory().getItemInMainHand());
        }

    }

    public static boolean hasCustomArmorEnchant(Player player, String enchantment) {
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

    public static int getArmorEnchantLevel(Player player, String enchantment) {
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

    public static ItemStack increaseEnchantLevel(ItemStack itemStack) {
        if (ItemHelper.hasLore(itemStack)) {
            List<String> lores = ItemHelper.getLore(itemStack);
            if (lores.size() > 3 && !lores.get(3).isEmpty()) {
                int currentLevel = Integer.parseInt(lores.get(3).substring(lores.get(3).length() - 1));
                ItemBuilder newItem = new ItemBuilder(itemStack);
                lores.set(3, lores.get(3).replace(currentLevel + "", "" + currentLevel + 1));
            }
        }
        return null;
    }

    public static void removeItems(Player player, ItemStack item, int amount) {
        for (int i = 0; i < player.getInventory().getContents().length; i++) {
            if (player.getInventory().getContents()[i] != null) {
                ItemStack itemStack = player.getInventory().getContents()[i];
                if (amount <= 0) return;
                if (itemStack.isSimilar(item)) {
                    if (amount < itemStack.getAmount()) {
                        itemStack.setAmount(itemStack.getAmount() - amount);
                        return;
                    }
                    player.getInventory().clear(i);
                    amount -= itemStack.getAmount();
                }
            }
        }
    }

}
