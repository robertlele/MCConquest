package me.robertle.mcconquest;

import org.bukkit.inventory.ItemStack;

public enum MCCArmor {
    DRAGON_HELMET('S'), DRAGON_CHESTPLATE('S'), DRAGON_LEGGINGS('S'), DRAGON_BOOTS('S'),
    GIANT_HELMET('S'), GIANT_CHESTPLATE('S'), GIANT_LEGGINGS('S'), GIANT_BOOTS('S'),
    MOOSHROOM_HELMET('S'), MOOSHROOM_CHESTPLATE('S'), MOOSHROOM_LEGGINGS('S'), MOOSHROOM_BOOTS('S'),
    BLAZE_HELMET('S'), BLAZE_CHESTPLATE('S'), BLAZE_LEGGINGS('S'), BLAZE_BOOTS('S'),
    PIGMAN_HELMET('A'), PIGMAN_CHESTPLATE('A'), PIGMAN_LEGGINGS('A'), PIGMAN_BOOTS('A'),
    POLAR_BEAR_HELMET('A'), POLAR_BEAR_CHESTPLATE('A'), POLAR_BEAR_LEGGINGS('A'), POLAR_BEAR_BOOTS('A'),
    ENDERMAN_HELMET('A'), ENDERMAN_CHESTPLATE('A'), ENDERMAN_LEGGINGS('A'), ENDERMAN_BOOTS('A'),
    BAT_HELMET('A'), BAT_CHESTPLATE('A'), BAT_LEGGINGS('A'), BAT_BOOTS('A'),
    SLIME_HELMET('A'), SLIME_CHESTPLATE('A'), SLIME_LEGGINGS('A'), SLIME_BOOTS('A'),
    SQUID_HELMET('A'), SQUID_CHESTPLATE('A'), SQUID_LEGGINGS('A'), SQUID_BOOTS('A'),
    GOLEM_HELMET('A'), GOLEM_CHESTPLATE('A'), GOLEM_LEGGINGS('A'), GOLEM_BOOTS('A'),
    ZOMBIE_HELMET('B'), ZOMBIE_CHESTPLATE('B'), ZOMBIE_LEGGINGS('B'), ZOMBIE_BOOTS('B'),
    PIG_HELMET('B'), PIG_CHESTPLATE('B'), PIG_LEGGINGS('B'), PIG_BOOTS('B'),
    SILVERFISH_HELMET('B'), SILVERFISH_CHESTPLATE('B'), SILVERFISH_LEGGINGS('B'), SILVERFISH_BOOTS('B'),
    SKELETON_HELMET('B'), SKELETON_CHESTPLATE('B'), SKELETON_LEGGINGS('B'), SKELETON_BOOTS('B'),
    DIAMOND_HELMET('B'), DIAMOND_CHESTPLATE('B'), DIAMOND_LEGGINGS('B'), DIAMOND_BOOTS('B');


    public static MCCArmor getArmorFromItemName(ItemStack itemStack) {
        if (ItemHelper.hasName(itemStack)) {
            return MCCArmor.valueOf(ItemHelper.getName(itemStack).substring(4).replaceAll(" ", "_").toUpperCase());
        }
        return null;
    }

    public char getTier() {
        return tier;
    }

    private char tier;

    MCCArmor(char tier) {
        this.tier = tier;
    }

}
