package me.robertle.mcconquest;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class BlacksmithManager {

    public static boolean forgeBlacksmithItem(ItemStack blacksmithItem, ItemStack item, Player player) {
        if (blacksmithItem.isSimilar(CustomItemManager.getBlacksmithsCoal())) {
            if (InventoryUtil.verifyArmor(item) && ItemHelper.hasLore(item)) {
                String lore = ItemHelper.getLore(item).get(1);
                if (lore.contains("Lives")) {
                    int currentLives = InventoryUtil.getItemLives(item);
                    ItemBuilder newArmor = new ItemBuilder(CustomItemManager.getArmorPiece(MCCArmor.getArmorFromItemName(item)));
                    List<String> lores = ItemHelper.getLore(newArmor.asItemStack());
                    lores.set(1, "§a§l" + currentLives + " Lives");
                    newArmor.lore(lores);
                    player.getInventory().addItem(newArmor.asItemStack());
                    if (lores.get(3).isEmpty()) {
                        player.sendMessage("§8§lBlacksmith §f> §cYour armor failed while forging and got no enchants.");
                    } else {
                        player.sendMessage("§8§lBlacksmith §f> §aYour armor has been forged with new enchants.");
                    }
                    return true;
                }
            } else {
                player.sendMessage("§8§lBlacksmith §f> §cThis item cannot be forged with my hammer.");
                return false;
            }
        } else if (blacksmithItem.isSimilar(CustomItemManager.getBlacksmithsMagmaRod())) {
            if (InventoryUtil.verifyWeapon(item) && ItemHelper.hasLore(item)) {
                String lore = ItemHelper.getLore(item).get(1);
                if (lore.contains("Lives")) {
                    int currentLives = InventoryUtil.getItemLives(item);
                    ItemBuilder newWeapon = new ItemBuilder(CustomItemManager.getWeapon(MCCWeapon.getWeaponFromItemName(item)));
                    List<String> lores = ItemHelper.getLore(newWeapon.asItemStack());
                    if (lores.get(0).contains("B-TIER")) {
                        player.sendMessage("§8§lBlacksmith §f> §cB-Tier weapons don't have any enchants.");
                        return false;
                    }
                    lores.set(1, "§a§l" + currentLives + " Lives");
                    newWeapon.lore(lores);
                    player.getInventory().addItem(newWeapon.asItemStack());
                    if (lores.get(3).isEmpty()) {
                        player.sendMessage("§8§lBlacksmith §f> §cYour weapon failed while forging and got no enchants.");
                    } else {
                        player.sendMessage("§8§lBlacksmith §f> §aYour weapon has been forged with new enchants.");
                    }
                    return true;
                }
            } else {
                player.sendMessage("§8§lBlacksmith §f> §cThis item cannot be forged with my magma rod.");
                return false;
            }
        } else if (blacksmithItem.isSimilar(CustomItemManager.getBlacksmithsMagicDust())) {
            if ((InventoryUtil.verifyWeapon(item) || InventoryUtil.verifyArmor(item)) && ItemHelper.hasLore(item)) {
                if (ItemHelper.getLore(item).size() < 4 || ItemHelper.getLore(item).get(3).isEmpty()) {
                    player.sendMessage("§8§lBlacksmith §f> §cYour item has no enchant on it.");
                    return false;
                }
                if (InventoryUtil.checkMaxLevel(item)) {
                    player.sendMessage("§8§lBlacksmith §f> §cYour enchant is already maxed level.");
                    return false;
                }
                player.getInventory().addItem(InventoryUtil.increaseEnchantLevel(item));
                player.sendMessage("§8§lBlacksmith §f> §aYour enchant's level has been increased.");
                return true;
            } else {
                player.sendMessage("§8§lBlacksmith §f> §cThis item cannot be forged with my magic dust.");
                return false;
            }
        } else if (blacksmithItem.isSimilar(CustomItemManager.getBlacksmithsLifeOrb())) {
            if ((InventoryUtil.verifyWeapon(item) || InventoryUtil.verifyArmor(item) || InventoryUtil.verifyArtifact(item)) && ItemHelper.hasLore(item)) {
                player.getInventory().addItem(InventoryUtil.increaseItemLives(item));
                player.sendMessage("§8§lBlacksmith §f> §aYour item's lives has been increased.");
                return true;
            } else {
                player.sendMessage("§8§lBlacksmith §f> §cThis item cannot be forged with my life orb.");
                return false;
            }
        }
        return false;
    }

}
