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
                    int currentLives = InventoryUtil.getItemLives(item);
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
                    int currentLives = InventoryUtil.getItemLives(item);
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
                if (ItemHelper.getLore(item).get(3).isEmpty()) {
                    player.sendMessage(DefaultConfig.prefix + "§aYour item has no enchant on it.");
                    return false;
                }
                if (InventoryUtil.checkMaxLevel(item)) {
                    player.sendMessage(DefaultConfig.prefix + "§aYour enchant is already maxed level.");
                    return false;
                }
                player.getInventory().addItem(InventoryUtil.increaseEnchantLevel(item));
                player.sendMessage(DefaultConfig.prefix + "§aYour enchant's level has increased.");
                return true;
            }
        } else if (blacksmithItem.isSimilar(CustomItemManager.getBlacksmithsLifeOrb())) {
            if ((verifyWeapon(item) || verifyArmor(item) || verifyArtifact(item)) && ItemHelper.hasLore(item)) {
                player.getInventory().addItem(InventoryUtil.increaseItemLives(item));
                player.sendMessage(DefaultConfig.prefix + "§aYour item's lives has increased.");
                return true;
            }
        }
        return false;
    }

    public static boolean verifyArtifact(ItemStack item) {
        if (ItemHelper.hasName(item)) {
            if (ItemHelper.getName(item).contains("Knockback") || ItemHelper.getName(item).contains("Beater")) {
                return true;
            }
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
