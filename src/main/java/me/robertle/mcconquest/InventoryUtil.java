package me.robertle.mcconquest;

import me.robertle.mcconquest.Managers.CustomItemManager;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class InventoryUtil {

    public static List<String> levelOneEnchants = new ArrayList<>();

    public static void addLevelOneEnchants() {
        levelOneEnchants.add("Unstoppable");
        levelOneEnchants.add("Molten Shield");
        levelOneEnchants.add("Light Feet");
        levelOneEnchants.add("Flame Rage");
        levelOneEnchants.add("Keen Eye");
        levelOneEnchants.add("Flame");
        levelOneEnchants.add("Identify");
        levelOneEnchants.add("Starvation");
        levelOneEnchants.add("Health Boost");
        levelOneEnchants.add("Thunder God");
        levelOneEnchants.add("Dying Rage");
        levelOneEnchants.add("Trap");
    }

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
                    if (lores.size() > 3) {
                        for (String lore : lores) {
                            if (lore.contains(enchantment)) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    public static boolean hasCustomSwordEnchant(Player player, String enchantment) {
        ItemStack itemStack = player.getInventory().getItemInMainHand();
        if (itemStack == null) return false;
        if (ItemHelper.hasLore(itemStack)) {
            List<String> lores = ItemHelper.getLore(itemStack);
            if (lores.size() > 3) {
                for (String lore : lores) {
                    if (lore.contains(enchantment)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static int getSwordEnchantLevel(Player player, String enchantment) {
        int level = 0;
        ItemStack itemStack = player.getInventory().getItemInMainHand();
        if (itemStack == null) return level;
        if (ItemHelper.hasLore(itemStack)) {
            List<String> lores = ItemHelper.getLore(itemStack);
            if (lores.size() > 3) {
                for (String lore : lores) {
                    if (lore.contains(enchantment)) {
                        if (Integer.parseInt(lore.substring(lore.length() - 1)) > level)
                            level = Integer.parseInt(lore.substring(lore.length() - 1));
                    }
                }
            }
        }
        return level;
    }

    public static int getArmorEnchantLevel(Player player, String enchantment) {
        int level = 0;
        if (player.getInventory().getArmorContents().length > 0) {
            for (ItemStack itemStack : player.getInventory().getArmorContents()) {
                if (ItemHelper.hasLore(itemStack)) {
                    List<String> lores = ItemHelper.getLore(itemStack);
                    if (lores.size() > 3) {
                        for (String lore : lores) {
                            if (lore.contains(enchantment)) {
                                if (Integer.parseInt(lore.substring(lore.length() - 1)) > level)
                                    level = Integer.parseInt(lore.substring(lore.length() - 1));
                            }
                        }
                    }
                }
            }
        }
        return level;
    }

    public static ItemStack increaseArmorEnchantLevel(ItemStack itemStack, String enchant) {
        if (ItemHelper.hasLore(itemStack)) {
            List<String> lores = ItemHelper.getLore(itemStack);
            if (lores.size() > 3) {
                int currentLevel;
                ItemBuilder newItem = new ItemBuilder(itemStack);
                for (int i = 0; i < lores.size(); i++) {
                    if (lores.get(i).contains(enchant)) {
                        currentLevel = Integer.parseInt(lores.get(i).substring(lores.get(i).length() - 1));
                        if (!checkMaxArmorLevel(itemStack, enchant)) {
                            int newLevel = currentLevel + 1;
                            String levelLore = lores.get(i).replaceAll(String.valueOf(currentLevel), String.valueOf(newLevel));
                            lores.set(i, levelLore);
                        }
                    }
                }
                newItem.lore(lores);
                return newItem.asItemStack();
            }
        }
        return null;
    }

    public static ItemStack increaseSwordEnchantLevel(ItemStack itemStack, String enchant) {
        if (ItemHelper.hasLore(itemStack)) {
            List<String> lores = ItemHelper.getLore(itemStack);
            if (lores.size() > 3) {
                int currentLevel;
                ItemBuilder newItem = new ItemBuilder(itemStack);
                for (int i = 0; i < lores.size(); i++) {
                    if (lores.get(i).contains(enchant)) {
                        currentLevel = Integer.parseInt(lores.get(i).substring(lores.get(i).length() - 1));
                        if (!checkMaxSwordLevel(itemStack, enchant)) {
                            int newLevel = currentLevel + 1;
                            String levelLore = lores.get(i).replaceAll(String.valueOf(currentLevel), String.valueOf(newLevel));
                            lores.set(i, levelLore);
                        }
                    }
                }
                newItem.lore(lores);
                return newItem.asItemStack();
            }
        }
        return null;
    }

    public static ItemStack addEnchant(ItemStack keepItem, ItemStack addItem) {
        if (ItemHelper.hasLore(keepItem) && ItemHelper.hasLore(addItem)) {
            ItemBuilder newItem = new ItemBuilder(new ItemStack(keepItem));
            List<String> keepLore = ItemHelper.getLore(keepItem);
            List<String> addLore = ItemHelper.getLore(addItem);
            for (int i = 0; i < 3; i++) {
                addLore.remove(0);
            }
            for (String s : addLore) keepLore.add(s);
            newItem.lore(keepLore);
            return newItem.asItemStack();
        }
        return keepItem;
    }

    public static boolean isFullWeaponEnchant(ItemStack itemStack) {
        if (ItemHelper.hasLore(itemStack)) {
            if (ItemHelper.getLore(itemStack).size() > 9) {
                return true;
            } else
                return false;
        }
        return false;
    }

    public static boolean isFullArmorEnchant(ItemStack itemStack) {
        if (ItemHelper.hasLore(itemStack)) {
            if (ItemHelper.getLore(itemStack).size() > 7) {
                return true;
            } else
                return false;
        }
        return false;
    }

    public static boolean isSameTier(ItemStack item1, ItemStack item2) {
        if (ItemHelper.hasLore(item1) && ItemHelper.hasLore(item2)) {
            if (ItemHelper.getLore(item1).get(0).equalsIgnoreCase(ItemHelper.getLore(item2).get(0))) return true;
        }
        return false;
    }

    public static boolean checkMaxArmorLevel(ItemStack itemStack, String enchant) {
        if (ItemHelper.hasLore(itemStack)) {
            List<String> lores = ItemHelper.getLore(itemStack);
            if (lores.size() > 3 && !lores.get(3).isEmpty()) {
                int currentLevel;
                for (int i = 0; i < lores.size(); i++) {
                    if (lores.get(i).contains(enchant)) {
                        for (String enchants : levelOneEnchants) {
                            if (ItemHelper.getLore(itemStack).get(i).contains(enchants)) return true;
                        }
                        currentLevel = Integer.parseInt(lores.get(i).substring(lores.get(i).length() - 1));
                        if (currentLevel < 3) return false;
                    }
                }
            }
        }
        return true;
    }

    public static boolean checkMaxSwordLevel(ItemStack itemStack, String enchant) {
        if (ItemHelper.hasLore(itemStack)) {
            List<String> lores = ItemHelper.getLore(itemStack);
            if (lores.size() > 3 && !lores.get(3).isEmpty()) {
                int currentLevel;
                for (int i = 0; i < lores.size(); i++) {
                    if (lores.get(i).contains(enchant)) {
                        for (String enchants : levelOneEnchants) {
                            if (ItemHelper.getLore(itemStack).get(i).contains(enchants)) return true;
                        }
                        currentLevel = Integer.parseInt(lores.get(i).substring(lores.get(i).length() - 1));
                        if (currentLevel < 3) return false;
                    }
                }
            }
        }
        return true;
    }

    public static int getItemLives(ItemStack itemStack) {
        if (ItemHelper.hasLore(itemStack)) {
            String lore = ItemHelper.getLore(itemStack).get(1);
            if (lore.contains("Lives")) {
                int index = lore.indexOf("l");
                return Integer.parseInt(lore.substring(index + 1, index + 2));
            }
        }
        return 0;
    }

    public static String getItemTier(ItemStack itemStack) {
        if (ItemHelper.hasLore(itemStack)) {
            String lore = ItemHelper.getLore(itemStack).get(0);
            if (lore.contains("S-TIER")) {
                return "S-TIER";
            } else if (lore.contains("A-TIER")) {
                return "A-TIER";
            } else if (lore.contains("B-TIER")) {
                return "B-TIER";
            }
        }
        return "";
    }

    public static ItemStack increaseItemLives(ItemStack itemStack) {
        if (ItemHelper.hasLore(itemStack)) {
            List<String> lores = ItemHelper.getLore(itemStack);
            if (lores.size() > 1) {
                int newLives = getItemLives(itemStack) + 1;
                ItemBuilder newItem = new ItemBuilder(itemStack);
                lores.set(1, "§a§l" + newLives + " Lives");
                newItem.lore(lores);
                return newItem.asItemStack();
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
            if (ItemHelper.getName(item).contains("Sword") || ItemHelper.getName(item).contains("Axe") || ItemHelper.getName(item).contains("Bow")) {
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
        if (item.isSimilar(CustomItemManager.getBlacksmithsCoal()) || item.isSimilar(CustomItemManager.getBlacksmithsMagmaRod()) || item.isSimilar(CustomItemManager.getBlacksmithsMagicDust()) || item.isSimilar(CustomItemManager.getBlacksmithsLifeOrb())) {
            return true;
        }
        return false;
    }
}
