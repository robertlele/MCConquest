package me.robertle.mcconquest.Managers;

import me.robertle.mcconquest.InventoryUtil;
import me.robertle.mcconquest.ItemHelper;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class MergerManager {

    public static boolean mergeWeapon(ItemStack keepItem, ItemStack addItem, Player player) {
        if (InventoryUtil.verifyWeapon(keepItem) && InventoryUtil.verifyWeapon(addItem)) {
            if (keepItem.getType() == addItem.getType()) {
                if (ItemHelper.getLore(addItem).size() > 3) {
                    if (!InventoryUtil.isFullWeaponEnchant(keepItem)) {
                        ItemStack newItem = InventoryUtil.addEnchant(keepItem, addItem);
                        if (!InventoryUtil.isFullWeaponEnchant(newItem)) {
                            if (player.getInventory().containsAtLeast(CustomItemManager.getMergerDust(true), 3)) {
                                InventoryUtil.removeItems(player, CustomItemManager.getMergerDust(true), 3);
                            } else {
                                player.sendMessage("§c§lMerger §f> You don't have enough weapon dust to merge!");
                                return false;
                            }
                            player.getInventory().addItem(newItem);
                            player.sendMessage("§c§lMerger §f> Your item was successfully merged.");
                            player.playSound(player.getLocation(), Sound.BLOCK_ENCHANTMENT_TABLE_USE, 1, 1);
                            return true;
                        } else player.sendMessage("§c§lMerger §f> There's too many enchants to merge.");
                    } else player.sendMessage("§c§lMerger §f> The weapon is already maxed out.");
                } else player.sendMessage("§c§lMerger §f> The weapon has no enchants to merge.");
            } else player.sendMessage("§c§lMerger §f> The weapons must be the same type.");
        } else if (InventoryUtil.verifyArmor(keepItem) && InventoryUtil.verifyArmor(addItem)) {
            if (InventoryUtil.isSameTier(keepItem, addItem)) {
                if (ItemHelper.getLore(addItem).size() > 3) {
                    if (!InventoryUtil.isFullArmorEnchant(keepItem)) {
                        ItemStack newItem = InventoryUtil.addEnchant(keepItem, addItem);
                        if (!InventoryUtil.isFullArmorEnchant(newItem)) {
                            if (player.getInventory().containsAtLeast(CustomItemManager.getMergerDust(false), 6)) {
                                InventoryUtil.removeItems(player, CustomItemManager.getMergerDust(false), 6);
                            } else {
                                player.sendMessage("§c§lMerger §f> You don't have enough armor dust to merge!");
                                return false;
                            }
                            player.getInventory().addItem(newItem);
                            player.sendMessage("§c§lMerger §f> Your item was successfully merged.");
                            player.playSound(player.getLocation(), Sound.BLOCK_ENCHANTMENT_TABLE_USE, 1, 1);
                            return true;
                        } else player.sendMessage("§c§lMerger §f> There's too many enchants to merge.");
                    }
                } else player.sendMessage("§c§lMerger §f> The armor has no enchant to merge.");
            } else player.sendMessage("§c§lMerger §f> The armor must be the same tier.");
        } else {
            player.sendMessage("§c§lMerger §f> You can only merge weapons and armor.");
            return false;
        }
        return false;
    }

}
