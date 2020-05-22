package me.robertle.mcconquest;

import org.bukkit.inventory.ItemStack;

public enum MCCArmor {
    DRAGON_HELMET, DRAGON_CHESTPLATE, DRAGON_LEGGINGS, DRAGON_BOOTS,
    GIANT_HELMET, GIANT_CHESTPLATE, GIANT_LEGGINGS, GIANT_BOOTS,
    SQUID_HELMET, SQUID_CHESTPLATE, SQUID_LEGGINGS, SQUID_BOOTS,
    GOLEM_HELMET, GOLEM_CHESTPLATE, GOLEM_LEGGINGS, GOLEM_BOOTS,
    PIG_HELMET, PIG_CHESTPLATE, PIG_LEGGINGS, PIG_BOOTS,
    SILVERFISH_HELMET, SILVERFISH_CHESTPLATE, SILVERFISH_LEGGINGS, SILVERFISH_BOOTS,
    SKELETON_HELMET, SKELETON_CHESTPLATE, SKELETON_LEGGINGS, SKELETON_BOOTS,
    DIAMOND_HELMET, DIAMOND_CHESTPLATE, DIAMOND_LEGGINGS, DIAMOND_BOOTS;


    public static MCCArmor getArmorFromItemName(ItemStack itemStack) {
        if (ItemHelper.hasName(itemStack)) {
            return MCCArmor.valueOf(ItemHelper.getName(itemStack).substring(4).replaceAll(" ", "_").toUpperCase());
        }
        return null;
    }

}
