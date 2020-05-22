package me.robertle.mcconquest;

import org.bukkit.inventory.ItemStack;

public enum MCCArtifact {
    GUARDIAN_ROD, WITHER_PICKAXE, GIANT_GRINDER, EXTREME_KNOCKBACK_STICK,
    CRYSTAL_BEATER, SQUID_ROD, PIGMAN_PICKAXE, ZOMBIE_GRINDER, KNOCKBACK_STICK,
    FISHING_ROD, IRON_PICKAXE;

    public static MCCArtifact getArtifactFromItemName(ItemStack itemStack) {
        if (ItemHelper.hasName(itemStack)) {
            return MCCArtifact.valueOf(ItemHelper.getName(itemStack).substring(4).replaceAll(" ", "_").toUpperCase());
        }
        return null;
    }
}
