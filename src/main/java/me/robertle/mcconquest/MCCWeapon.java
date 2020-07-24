package me.robertle.mcconquest;

import org.bukkit.inventory.ItemStack;

public enum MCCWeapon {
    DRAGON_SWORD('S'), GIANT_AXE('S'), DRAGON_BOW('S'), GIANT_BOW('S'), MOOSHROOM_AXE('S'), BLAZE_SWORD('S'), BLAZE_BOW('S'), ZEUS_SWORD('S', true), ZEUS_BOW('S', true), HADES_AXE('S', true), HADES_BOW('S', true),
    SQUID_SWORD('A'), GOLEM_AXE('A'), GOLEM_BOW('A'), PIGMAN_SWORD('A'), POLAR_BEAR_AXE('A'), ENDERMAN_AXE('A'), ENDERMAN_BOW('A'), BAT_SWORD('A'), SLIME_SWORD('A'),
    PIG_SWORD('B'), SILVERFISH_AXE('B'), SKELETON_BOW('B'), ZOMBIE_SWORD('B'), DIAMOND_SWORD('B');

    public static MCCWeapon getWeaponFromItemName(ItemStack itemStack) {
        if (ItemHelper.hasName(itemStack)) {
            return MCCWeapon.valueOf(ItemHelper.getName(itemStack).substring(4).replaceAll(" ", "_").toUpperCase());
        }
        return null;
    }

    public char getTier() {
        return tier;
    }

    private char tier;
    public boolean limited;

    MCCWeapon(char tier) {
        this.tier = tier;
        this.limited = false;
    }

    MCCWeapon(char tier, boolean limited) {
        this.tier = tier;
        this.limited = limited;
    }
}
