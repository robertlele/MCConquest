package me.robertle.mcconquest;

import org.bukkit.inventory.ItemStack;

public enum MCCArtifact {
    GUARDIAN_ROD('S'), WITHER_PICKAXE('S'), GIANT_GRINDER('S'), EXTREME_KNOCKBACK_STICK('S'), GOD_SLAYER('S'),
    SQUID_ROD('A'), PIGMAN_PICKAXE('A'), ZOMBIE_GRINDER('A'), KNOCKBACK_STICK('A'), CRYSTAL_BEATER('A'),
    FISHING_ROD('B'), IRON_PICKAXE('B');

    public static MCCArtifact getArtifactFromItemName(ItemStack itemStack) {
        if (ItemHelper.hasName(itemStack)) {
            return MCCArtifact.valueOf(ItemHelper.getName(itemStack).substring(4).replaceAll(" ", "_").toUpperCase());
        }
        return null;
    }

    public char getTier() {
        return tier;
    }

    private char tier;

    MCCArtifact(char tier) {
        this.tier = tier;
    }
}
