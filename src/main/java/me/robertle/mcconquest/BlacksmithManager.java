package me.robertle.mcconquest;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class BlacksmithManager {

    public static boolean identifyBlacksmithItem(ItemStack blacksmithItem, ItemStack item, Player player) {
        if (blacksmithItem.isSimilar(CustomItemManager.getBlacksmithsHammer())) {
            if (verifyArmor(item) && ItemHelper.hasLore(item)) {
                String lore = ItemHelper.getLore(item).get(1);
                if (lore.contains("Lives")) {
                    int index = lore.indexOf("l");
                    int currentLives = Integer.parseInt(lore.substring(index + 1, index + 2));
                    ItemBuilder newArmor = new ItemBuilder(CustomItemManager.getArmorPiece(MCCArmor.getArmorFromItemName(item)));
                    List<String> lores = ItemHelper.getLore(newArmor.asItemStack());
                    lores.set(1, "§a§l" + currentLives + " Lives");
                    newArmor.lore(lores);
                    player.getInventory().addItem(newArmor.asItemStack());
                    if (lores.get(3).isEmpty()) {
                        player.sendMessage(DefaultConfig.prefix + "§cYour armor failed while forging and got no enchants.");
                    } else {
                        player.sendMessage(DefaultConfig.prefix + "§aYour armor has been forged with new enchants.");
                    }
                    return true;
                }
            } else return false;
        } else if (blacksmithItem.isSimilar(CustomItemManager.getBlacksmithsMagmaRod())) {
            if (verifyWeapon(item) && ItemHelper.hasLore(item)) {
                String lore = ItemHelper.getLore(item).get(1);
                if (lore.contains("Lives")) {
                    int index = lore.indexOf("l");
                    int currentLives = Integer.parseInt(lore.substring(index + 1, index + 2));
                    ItemBuilder newWeapon = new ItemBuilder(CustomItemManager.getWeapon(MCCWeapon.getWeaponFromItemName(item)));
                    List<String> lores = ItemHelper.getLore(newWeapon.asItemStack());
                    lores.set(1, "§a§l" + currentLives + " Lives");
                    newWeapon.lore(lores);
                    player.getInventory().addItem(newWeapon.asItemStack());
                    if (lores.get(3).isEmpty()) {
                        player.sendMessage(DefaultConfig.prefix + "§cYour weapon failed while forging and got no enchants.");
                    } else {
                        player.sendMessage(DefaultConfig.prefix + "§aYour weapon has been forged with new enchants.");
                    }
                    return true;
                }
            } else return false;
        } else if (blacksmithItem.isSimilar(CustomItemManager.getBlacksmithsMagicDust())) {
            if ((verifyWeapon(item) || verifyArmor(item)) && ItemHelper.hasLore(item)) {

            }
        } else if (blacksmithItem.isSimilar(CustomItemManager.getBlacksmithsLifeOrb())) {

        }
        return false;
    }

    public static boolean verifyWeapon(ItemStack item) {
        if (ItemHelper.hasName(item)) {
            if (ItemHelper.getName(item).contains("Sword") || ItemHelper.getName(item).contains("Axe")) {
                return true;
            }
        }
        return false;
    }

    public static boolean verifyArmor(ItemStack item) {
        if (ItemHelper.hasName(item)) {
            if (ItemHelper.getName(item).contains("Helmet") || ItemHelper.getName(item).contains("Chestplate") || ItemHelper.getName(item).contains("Leggings") || ItemHelper.getName(item).contains("Boots")) {
                return true;
            }
        }
        return false;
    }

    public static boolean verifyBlacksmithItem(ItemStack item) {
        if (item.isSimilar(CustomItemManager.getBlacksmithsHammer()) || item.isSimilar(CustomItemManager.getBlacksmithsMagmaRod()) || item.isSimilar(CustomItemManager.getBlacksmithsMagicDust()) || item.isSimilar(CustomItemManager.getBlacksmithsLifeOrb())) {
            return true;
        }
        return false;
    }

}
