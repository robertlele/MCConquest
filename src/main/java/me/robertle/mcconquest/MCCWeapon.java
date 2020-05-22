package me.robertle.mcconquest;

import org.bukkit.inventory.ItemStack;

public enum MCCWeapon {
    DRAGON_SWORD, GIANT_AXE, DRAGON_BOW, GIANT_BOW,
    SQUID_SWORD, GOLEM_AXE, GOLEM_BOW, PIG_SWORD,
    SILVERFISH_AXE, SKELETON_BOW, IRON_SWORD;

    public static MCCWeapon getWeaponFromItemName(ItemStack itemStack) {
        if (ItemHelper.hasName(itemStack)) {
            return MCCWeapon.valueOf(ItemHelper.getName(itemStack).substring(4).replaceAll(" ", "_").toUpperCase());
        }
        return null;
    }
}
