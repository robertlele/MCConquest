package me.robertle.mcconquest;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class CustomItemManager {

    //Add to which section

    public static ItemStack getRandomArmor() {
        List<MCCArmor> bag = new ArrayList<>();
        for (int i = 0; i < 55; i++) {
            if (i < 5) {
                bag.add(MCCArmor.DRAGON_HELMET);
                bag.add(MCCArmor.DRAGON_CHESTPLATE);
                bag.add(MCCArmor.DRAGON_LEGGINGS);
                bag.add(MCCArmor.DRAGON_BOOTS);
                bag.add(MCCArmor.GIANT_HELMET);
                bag.add(MCCArmor.GIANT_CHESTPLATE);
                bag.add(MCCArmor.GIANT_LEGGINGS);
                bag.add(MCCArmor.GIANT_BOOTS);
            }
            if (i < 40) {
                bag.add(MCCArmor.SQUID_HELMET);
                bag.add(MCCArmor.SQUID_CHESTPLATE);
                bag.add(MCCArmor.SQUID_LEGGINGS);
                bag.add(MCCArmor.SQUID_BOOTS);
                bag.add(MCCArmor.GOLEM_HELMET);
                bag.add(MCCArmor.GOLEM_CHESTPLATE);
                bag.add(MCCArmor.GOLEM_LEGGINGS);
                bag.add(MCCArmor.GOLEM_BOOTS);
            }
            if (i < 55) {
                bag.add(MCCArmor.SKELETON_HELMET);
                bag.add(MCCArmor.SKELETON_CHESTPLATE);
                bag.add(MCCArmor.SKELETON_LEGGINGS);
                bag.add(MCCArmor.SKELETON_BOOTS);
                bag.add(MCCArmor.PIG_HELMET);
                bag.add(MCCArmor.PIG_CHESTPLATE);
                bag.add(MCCArmor.PIG_LEGGINGS);
                bag.add(MCCArmor.PIG_BOOTS);
                bag.add(MCCArmor.SILVERFISH_HELMET);
                bag.add(MCCArmor.SILVERFISH_CHESTPLATE);
                bag.add(MCCArmor.SILVERFISH_LEGGINGS);
                bag.add(MCCArmor.SILVERFISH_BOOTS);
            }
        }
        return getArmorPiece(bag.get(Core.generateNumber(0, bag.size() - 1)));
    }

    public static ItemStack getSpecialRandomArmor() {
        List<MCCArmor> bag = new ArrayList<>();
        for (int i = 0; i < 60; i++) {
            if (i < 10) {
                bag.add(MCCArmor.DRAGON_HELMET);
                bag.add(MCCArmor.DRAGON_CHESTPLATE);
                bag.add(MCCArmor.DRAGON_LEGGINGS);
                bag.add(MCCArmor.DRAGON_BOOTS);
                bag.add(MCCArmor.GIANT_HELMET);
                bag.add(MCCArmor.GIANT_CHESTPLATE);
                bag.add(MCCArmor.GIANT_LEGGINGS);
                bag.add(MCCArmor.GIANT_BOOTS);
            }
            if (i < 60) {
                bag.add(MCCArmor.SQUID_HELMET);
                bag.add(MCCArmor.SQUID_CHESTPLATE);
                bag.add(MCCArmor.SQUID_LEGGINGS);
                bag.add(MCCArmor.SQUID_BOOTS);
                bag.add(MCCArmor.GOLEM_HELMET);
                bag.add(MCCArmor.GOLEM_CHESTPLATE);
                bag.add(MCCArmor.GOLEM_LEGGINGS);
                bag.add(MCCArmor.GOLEM_BOOTS);
            }
            if (i < 30) {
                bag.add(MCCArmor.SKELETON_HELMET);
                bag.add(MCCArmor.SKELETON_CHESTPLATE);
                bag.add(MCCArmor.SKELETON_LEGGINGS);
                bag.add(MCCArmor.SKELETON_BOOTS);
                bag.add(MCCArmor.PIG_HELMET);
                bag.add(MCCArmor.PIG_CHESTPLATE);
                bag.add(MCCArmor.PIG_LEGGINGS);
                bag.add(MCCArmor.PIG_BOOTS);
                bag.add(MCCArmor.SILVERFISH_HELMET);
                bag.add(MCCArmor.SILVERFISH_CHESTPLATE);
                bag.add(MCCArmor.SILVERFISH_LEGGINGS);
                bag.add(MCCArmor.SILVERFISH_BOOTS);
            }
        }
        return getArmorPiece(bag.get(Core.generateNumber(0, bag.size() - 1)));
    }

    public static ItemStack getRandomWeapon() {
        List<MCCWeapon> bag = new ArrayList<>();
        for (int i = 0; i < 60; i++) {
            if (i < 10) {
                bag.add(MCCWeapon.DRAGON_SWORD);
                bag.add(MCCWeapon.DRAGON_BOW);
                bag.add(MCCWeapon.GIANT_AXE);
                bag.add(MCCWeapon.GIANT_BOW);
            }
            if (i < 35) {
                bag.add(MCCWeapon.SQUID_SWORD);
                bag.add(MCCWeapon.GOLEM_AXE);
                bag.add(MCCWeapon.GOLEM_BOW);
            }
            if (i < 55) {
                bag.add(MCCWeapon.PIG_SWORD);
                bag.add(MCCWeapon.SILVERFISH_AXE);
                bag.add(MCCWeapon.SKELETON_BOW);
            }
        }
        return getWeapon(bag.get(Core.generateNumber(0, bag.size() - 1)));
    }

    public static ItemStack getSpecialRandomWeapon() {
        List<MCCWeapon> bag = new ArrayList<>();
        for (int i = 0; i < 60; i++) {
            if (i < 15) {
                bag.add(MCCWeapon.DRAGON_SWORD);
                bag.add(MCCWeapon.DRAGON_BOW);
                bag.add(MCCWeapon.GIANT_AXE);
                bag.add(MCCWeapon.GIANT_BOW);
            }
            if (i < 60) {
                bag.add(MCCWeapon.SQUID_SWORD);
                bag.add(MCCWeapon.GOLEM_AXE);
                bag.add(MCCWeapon.GOLEM_BOW);
            }
            if (i < 25) {
                bag.add(MCCWeapon.PIG_SWORD);
                bag.add(MCCWeapon.SILVERFISH_AXE);
                bag.add(MCCWeapon.SKELETON_BOW);
            }
        }
        return getWeapon(bag.get(Core.generateNumber(0, bag.size() - 1)));
    }

    public static ItemStack getRandomArtifact() {
        List<MCCArtifact> bag = new ArrayList<>();
        for (int i = 0; i < 60; i++) {
            if (i < 15) {
                bag.add(MCCArtifact.GUARDIAN_ROD);
                bag.add(MCCArtifact.WITHER_PICKAXE);
                bag.add(MCCArtifact.GIANT_GRINDER);
                bag.add(MCCArtifact.EXTREME_KNOCKBACK_STICK);
                bag.add(MCCArtifact.CRYSTAL_BEATER);
            }
            if (i < 85) {
                bag.add(MCCArtifact.SQUID_ROD);
                bag.add(MCCArtifact.PIGMAN_PICKAXE);
                bag.add(MCCArtifact.ZOMBIE_GRINDER);
                bag.add(MCCArtifact.KNOCKBACK_STICK);
            }
        }
        return getArtifact(bag.get(Core.generateNumber(0, bag.size() - 1)));
    }

    //Add to which tier

    public static ItemStack getRandomSTier() {
        int r = Core.generateNumber(0, 2);
        if (r == 0) {
            List<MCCArmor> bag = new ArrayList<>();
            bag.add(MCCArmor.DRAGON_HELMET);
            bag.add(MCCArmor.DRAGON_CHESTPLATE);
            bag.add(MCCArmor.DRAGON_LEGGINGS);
            bag.add(MCCArmor.DRAGON_BOOTS);
            bag.add(MCCArmor.GIANT_HELMET);
            bag.add(MCCArmor.GIANT_CHESTPLATE);
            bag.add(MCCArmor.GIANT_LEGGINGS);
            bag.add(MCCArmor.GIANT_BOOTS);
            return getArmorPiece(bag.get(Core.generateNumber(0, bag.size() - 1)));
        } else if (r == 1) {
            List<MCCWeapon> bag = new ArrayList<>();
            bag.add(MCCWeapon.DRAGON_SWORD);
            bag.add(MCCWeapon.DRAGON_BOW);
            bag.add(MCCWeapon.GIANT_AXE);
            bag.add(MCCWeapon.GIANT_BOW);
            return getWeapon(bag.get(Core.generateNumber(0, bag.size() - 1)));
        } else if (r == 2) {
            List<MCCArtifact> bag = new ArrayList<>();
            bag.add(MCCArtifact.GUARDIAN_ROD);
            bag.add(MCCArtifact.WITHER_PICKAXE);
            bag.add(MCCArtifact.GIANT_GRINDER);
            bag.add(MCCArtifact.EXTREME_KNOCKBACK_STICK);
            bag.add(MCCArtifact.CRYSTAL_BEATER);
            return getArtifact(bag.get(Core.generateNumber(0, bag.size() - 1)));
        }
        return null;
    }

    public static ItemStack getRandomATier() {
        int r = Core.generateNumber(0, 2);
        if (r == 0) {
            List<MCCArmor> bag = new ArrayList<>();
            bag.add(MCCArmor.SQUID_HELMET);
            bag.add(MCCArmor.SQUID_CHESTPLATE);
            bag.add(MCCArmor.SQUID_LEGGINGS);
            bag.add(MCCArmor.SQUID_BOOTS);
            bag.add(MCCArmor.GOLEM_HELMET);
            bag.add(MCCArmor.GOLEM_CHESTPLATE);
            bag.add(MCCArmor.GOLEM_LEGGINGS);
            bag.add(MCCArmor.GOLEM_BOOTS);
            return getArmorPiece(bag.get(Core.generateNumber(0, bag.size() - 1)));
        } else if (r == 1) {
            List<MCCWeapon> bag = new ArrayList<>();
            bag.add(MCCWeapon.SQUID_SWORD);
            bag.add(MCCWeapon.GOLEM_AXE);
            bag.add(MCCWeapon.GOLEM_BOW);
            return getWeapon(bag.get(Core.generateNumber(0, bag.size() - 1)));
        } else if (r == 2) {
            List<MCCArtifact> bag = new ArrayList<>();
            bag.add(MCCArtifact.SQUID_ROD);
            bag.add(MCCArtifact.PIGMAN_PICKAXE);
            bag.add(MCCArtifact.ZOMBIE_GRINDER);
            bag.add(MCCArtifact.KNOCKBACK_STICK);
            return getArtifact(bag.get(Core.generateNumber(0, bag.size() - 1)));
        }
        return null;
    }

    //Add new item here

    public static ItemStack getArmorPiece(MCCArmor armor) {
        if (armor == MCCArmor.DRAGON_HELMET) {
            ItemBuilder item = new ItemBuilder(Material.DIAMOND_HELMET, 1);
            item.displayName("§5§lDragon Helmet");
            item.enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
            item.unbreakable(true);
            int r = Core.generateNumber(0, 2);
            if (r == 0) {
                item.lore("§4§l✸S-TIER", "§a§l" + Core.generateNumber(2, 4) + " Lives", "", "§6Disorder " + Core.generateNumber(1, 3), "§7Chance to shuffle attacker's hotbar when hit");
            } else if (r == 1) {
                item.lore("§4§l✸S-TIER", "§a§l" + Core.generateNumber(2, 4) + " Lives", "", "§6Immunity " + Core.generateNumber(1, 3), "§7Chance for debuffs not to be applied");
            } else if (r == 2) {
                item.lore("§4§l✸S-TIER", "§a§l" + Core.generateNumber(2, 4) + " Lives", "", "");
            }
            return item.asItemStack();
        } else if (armor == MCCArmor.DRAGON_CHESTPLATE) {
            ItemBuilder item = new ItemBuilder(Material.DIAMOND_CHESTPLATE, 1);
            item.displayName("§5§lDragon Chestplate");
            item.enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
            item.unbreakable(true);
            int r = Core.generateNumber(0, 2);
            if (r == 0) {
                item.lore("§4§l✸S-TIER", "§a§l" + Core.generateNumber(2, 4) + " Lives", "", "§6Disorder " + Core.generateNumber(1, 3), "§7Chance to shuffle attacker's hotbar when hit");
            } else if (r == 1) {
                item.lore("§4§l✸S-TIER", "§a§l" + Core.generateNumber(2, 4) + " Lives", "", "§6Immunity " + Core.generateNumber(1, 3), "§7Chance for debuffs not to be applied");
            } else if (r == 2) {
                item.lore("§4§l✸S-TIER", "§a§l" + Core.generateNumber(2, 4) + " Lives", "", "");
            }
            return item.asItemStack();
        } else if (armor == MCCArmor.DRAGON_LEGGINGS) {
            ItemBuilder item = new ItemBuilder(Material.DIAMOND_LEGGINGS, 1);
            item.displayName("§5§lDragon Leggings");
            item.enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
            item.unbreakable(true);
            int r = Core.generateNumber(0, 2);
            if (r == 0) {
                item.lore("§4§l✸S-TIER", "§a§l" + Core.generateNumber(2, 4) + " Lives", "", "§6Disorder " + Core.generateNumber(1, 3), "§7Chance to shuffle attacker's hotbar when hit");
            } else if (r == 1) {
                item.lore("§4§l✸S-TIER", "§a§l" + Core.generateNumber(2, 4) + " Lives", "", "§6Immunity " + Core.generateNumber(1, 3), "§7Chance for debuffs not to be applied");
            } else if (r == 2) {
                item.lore("§4§l✸S-TIER", "§a§l" + Core.generateNumber(2, 4) + " Lives", "", "");
            }
            return item.asItemStack();
        } else if (armor == MCCArmor.DRAGON_BOOTS) {
            ItemBuilder item = new ItemBuilder(Material.DIAMOND_BOOTS, 1);
            item.displayName("§5§lDragon Boots");
            item.enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
            item.unbreakable(true);
            int r = Core.generateNumber(0, 2);
            if (r == 0) {
                item.lore("§4§l✸S-TIER", "§a§l" + Core.generateNumber(2, 4) + " Lives", "", "§6Disorder " + Core.generateNumber(1, 3), "§7Chance to shuffle attacker's hotbar when hit");
            } else if (r == 1) {
                item.lore("§4§l✸S-TIER", "§a§l" + Core.generateNumber(2, 4) + " Lives", "", "§6Immunity " + Core.generateNumber(1, 3), "§7Chance for debuffs not to be applied");
            } else if (r == 2) {
                item.lore("§4§l✸S-TIER", "§a§l" + Core.generateNumber(2, 4) + " Lives", "", "");
            }
            return item.asItemStack();
        } else if (armor == MCCArmor.GIANT_HELMET) {
            ItemBuilder item = new ItemBuilder(Material.DIAMOND_HELMET, 1);
            item.displayName("§2§lGiant Helmet");
            item.unsafeEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
            item.unbreakable(true);
            int r = Core.generateNumber(0, 2);
            if (r == 0) {
                item.lore("§4§l✸S-TIER", "§a§l" + Core.generateNumber(2, 4) + " Lives", "", "§6Unstoppable 1", "§7Immune to all slow debuffs");
            } else if (r == 1) {
                item.lore("§4§l✸S-TIER", "§a§l" + Core.generateNumber(2, 4) + " Lives", "", "§6Juggernaut " + Core.generateNumber(1, 3), "§7Chance to gain resistance 1 for a short time");
            } else if (r == 2) {
                item.lore("§4§l✸S-TIER", "§a§l" + Core.generateNumber(2, 4) + " Lives", "", "");
            }
            return item.asItemStack();
        } else if (armor == MCCArmor.GIANT_CHESTPLATE) {
            ItemBuilder item = new ItemBuilder(Material.DIAMOND_CHESTPLATE, 1);
            item.displayName("§2§lGiant Chestplate");
            item.unsafeEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
            item.unbreakable(true);
            int r = Core.generateNumber(0, 2);
            if (r == 0) {
                item.lore("§4§l✸S-TIER", "§a§l" + Core.generateNumber(2, 4) + " Lives", "", "§6Unstoppable 1", "§7Immune to all slow debuffs");
            } else if (r == 1) {
                item.lore("§4§l✸S-TIER", "§a§l" + Core.generateNumber(2, 4) + " Lives", "", "§6Juggernaut " + Core.generateNumber(1, 3), "§7Chance to gain resistance 1 for a short time");
            } else if (r == 2) {
                item.lore("§4§l✸S-TIER", "§a§l" + Core.generateNumber(2, 4) + " Lives", "", "");
            }
            return item.asItemStack();
        } else if (armor == MCCArmor.GIANT_LEGGINGS) {
            ItemBuilder item = new ItemBuilder(Material.DIAMOND_LEGGINGS, 1);
            item.displayName("§2§lGiant Leggings");
            item.unsafeEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
            item.unbreakable(true);
            int r = Core.generateNumber(0, 2);
            if (r == 0) {
                item.lore("§4§l✸S-TIER", "§a§l" + Core.generateNumber(2, 4) + " Lives", "", "§6Unstoppable 1", "§7Immune to all slow debuffs");
            } else if (r == 1) {
                item.lore("§4§l✸S-TIER", "§a§l" + Core.generateNumber(2, 4) + " Lives", "", "§6Juggernaut " + Core.generateNumber(1, 3), "§7Chance to gain resistance 1 for a short time");
            } else if (r == 2) {
                item.lore("§4§l✸S-TIER", "§a§l" + Core.generateNumber(2, 4) + " Lives", "", "");
            }
            return item.asItemStack();
        } else if (armor == MCCArmor.GIANT_BOOTS) {
            ItemBuilder item = new ItemBuilder(Material.DIAMOND_BOOTS, 1);
            item.displayName("§2§lGiant Boots");
            item.unsafeEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
            item.unbreakable(true);
            int r = Core.generateNumber(0, 2);
            if (r == 0) {
                item.lore("§4§l✸S-TIER", "§a§l" + Core.generateNumber(2, 4) + " Lives", "", "§6Unstoppable 1", "§7Immune to all slow debuffs");
            } else if (r == 1) {
                item.lore("§4§l✸S-TIER", "§a§l" + Core.generateNumber(2, 4) + " Lives", "", "§6Juggernaut " + Core.generateNumber(1, 3), "§7Chance to gain resistance 1 for a short time");
            } else if (r == 2) {
                item.lore("§4§l✸S-TIER", "§a§l" + Core.generateNumber(2, 4) + " Lives", "", "");
            }
            return item.asItemStack();
        } else if (armor == MCCArmor.SQUID_HELMET) {
            ItemBuilder item = new ItemBuilder(Material.DIAMOND_HELMET, 1);
            item.displayName("§3§lSquid Helmet");
            item.unsafeEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
            item.unbreakable(true);
            int r = Core.generateNumber(0, 2);
            if (r == 0) {
                item.lore("§e§l✸A-TIER", "§a§l" + Core.generateNumber(2, 4) + " Lives", "", "§6Molten Shield 1", "§7Immune to fire");
            } else if (r == 1) {
                int depth = Core.generateNumber(1, 3);
                item.lore("§e§l✸A-TIER", "§a§l" + Core.generateNumber(2, 4) + " Lives", "", "§6Depth Strider " + depth, "§7Gain speed when in water");
                item.unsafeEnchant(Enchantment.DEPTH_STRIDER, depth);
            } else if (r == 2) {
                item.lore("§e§l✸A-TIER", "§a§l" + Core.generateNumber(2, 4) + " Lives", "", "");
            }
            return item.asItemStack();
        } else if (armor == MCCArmor.SQUID_CHESTPLATE) {
            ItemBuilder item = new ItemBuilder(Material.DIAMOND_CHESTPLATE, 1);
            item.displayName("§3§lSquid Chestplate");
            item.unsafeEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
            item.unbreakable(true);
            int r = Core.generateNumber(0, 2);
            if (r == 0) {
                item.lore("§e§l✸A-TIER", "§a§l" + Core.generateNumber(2, 4) + " Lives", "", "§6Molten Shield 1", "§7Immune to fire");
            } else if (r == 1) {
                int depth = Core.generateNumber(1, 3);
                item.lore("§e§l✸A-TIER", "§a§l" + Core.generateNumber(2, 4) + " Lives", "", "§6Depth Strider " + depth, "§7Gain speed when in water");
                item.unsafeEnchant(Enchantment.DEPTH_STRIDER, depth);
            } else if (r == 2) {
                item.lore("§e§l✸A-TIER", "§a§l" + Core.generateNumber(2, 4) + " Lives", "", "");
            }
            return item.asItemStack();
        } else if (armor == MCCArmor.SQUID_LEGGINGS) {
            ItemBuilder item = new ItemBuilder(Material.DIAMOND_LEGGINGS, 1);
            item.displayName("§3§lSquid Leggings");
            item.unsafeEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
            item.unbreakable(true);
            int r = Core.generateNumber(0, 2);
            if (r == 0) {
                item.lore("§e§l✸A-TIER", "§a§l" + Core.generateNumber(2, 4) + " Lives", "", "§6Molten Shield 1", "§7Immune to fire");
            } else if (r == 1) {
                int depth = Core.generateNumber(1, 3);
                item.lore("§e§l✸A-TIER", "§a§l" + Core.generateNumber(2, 4) + " Lives", "", "§6Depth Strider " + depth, "§7Gain speed when in water");
                item.unsafeEnchant(Enchantment.DEPTH_STRIDER, depth);
            } else if (r == 2) {
                item.lore("§e§l✸A-TIER", "§a§l" + Core.generateNumber(2, 4) + " Lives", "", "");
            }
            return item.asItemStack();
        } else if (armor == MCCArmor.SQUID_BOOTS) {
            ItemBuilder item = new ItemBuilder(Material.DIAMOND_BOOTS, 1);
            item.displayName("§3§lSquid Boots");
            item.unsafeEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
            item.unbreakable(true);
            int r = Core.generateNumber(0, 2);
            if (r == 0) {
                item.lore("§e§l✸A-TIER", "§a§l" + Core.generateNumber(2, 4) + " Lives", "", "§6Molten Shield 1", "§7Immune to fire");
            } else if (r == 1) {
                int depth = Core.generateNumber(1, 3);
                item.lore("§e§l✸A-TIER", "§a§l" + Core.generateNumber(2, 4) + " Lives", "", "§6Depth Strider " + depth, "§7Gain speed when in water");
                item.unsafeEnchant(Enchantment.DEPTH_STRIDER, depth);
            } else if (r == 2) {
                item.lore("§e§l✸A-TIER", "§a§l" + Core.generateNumber(2, 4) + " Lives", "", "");
            }
            return item.asItemStack();
        } else if (armor == MCCArmor.GOLEM_HELMET) {
            ItemBuilder item = new ItemBuilder(Material.DIAMOND_HELMET, 1);
            item.displayName("§8§lGolem Helmet");
            item.unsafeEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
            item.unbreakable(true);
            int r = Core.generateNumber(0, 3);
            if (r == 0) {
                item.lore("§e§l✸A-TIER", "§a§l" + Core.generateNumber(2, 4) + " Lives", "", "§6Seismic Escape " + Core.generateNumber(1, 3), "§7Knockback and slow nearby enemies when low");
            } else if (r == 1) {
                item.lore("§e§l✸A-TIER", "§a§l" + Core.generateNumber(2, 4) + " Lives", "", "§6Reflect " + Core.generateNumber(1, 3), "§7Chance to reflect damage back when hit");
            } else if (r == 2) {
                item.lore("§e§l✸A-TIER", "§a§l" + Core.generateNumber(2, 4) + " Lives", "", "");
            }
            return item.asItemStack();
        } else if (armor == MCCArmor.GOLEM_CHESTPLATE) {
            ItemBuilder item = new ItemBuilder(Material.DIAMOND_CHESTPLATE, 1);
            item.displayName("§8§lGolem Chestplate");
            item.unsafeEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
            item.unbreakable(true);
            int r = Core.generateNumber(0, 3);
            if (r == 0) {
                item.lore("§e§l✸A-TIER", "§a§l" + Core.generateNumber(2, 4) + " Lives", "", "§6Seismic Escape " + Core.generateNumber(1, 3), "§7Knockback and slow nearby enemies when low");
            } else if (r == 1) {
                item.lore("§e§l✸A-TIER", "§a§l" + Core.generateNumber(2, 4) + " Lives", "", "§6Reflect " + Core.generateNumber(1, 3), "§7Chance to reflect damage back when hit");
            } else if (r == 2) {
                item.lore("§e§l✸A-TIER", "§a§l" + Core.generateNumber(2, 4) + " Lives", "", "");
            }
            return item.asItemStack();
        } else if (armor == MCCArmor.GOLEM_LEGGINGS) {
            ItemBuilder item = new ItemBuilder(Material.DIAMOND_LEGGINGS, 1);
            item.displayName("§8§lGolem Leggings");
            item.unsafeEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
            item.unbreakable(true);
            int r = Core.generateNumber(0, 3);
            if (r == 0) {
                item.lore("§e§l✸A-TIER", "§a§l" + Core.generateNumber(2, 4) + " Lives", "", "§6Seismic Escape " + Core.generateNumber(1, 3), "§7Knockback and slow nearby enemies when low");
            } else if (r == 1) {
                item.lore("§e§l✸A-TIER", "§a§l" + Core.generateNumber(2, 4) + " Lives", "", "§6Reflect " + Core.generateNumber(1, 3), "§7Chance to reflect damage back when hit");
            } else if (r == 2) {
                item.lore("§e§l✸A-TIER", "§a§l" + Core.generateNumber(2, 4) + " Lives", "", "");
            }
            return item.asItemStack();
        } else if (armor == MCCArmor.GOLEM_BOOTS) {
            ItemBuilder item = new ItemBuilder(Material.DIAMOND_BOOTS, 1);
            item.displayName("§8§lGolem Boots");
            item.unsafeEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
            item.unbreakable(true);
            int r = Core.generateNumber(0, 3);
            if (r == 0) {
                item.lore("§e§l✸A-TIER", "§a§l" + Core.generateNumber(2, 4) + " Lives", "", "§6Seismic Escape " + Core.generateNumber(1, 3), "§7Knockback and slow nearby enemies when low");
            } else if (r == 1) {
                item.lore("§e§l✸A-TIER", "§a§l" + Core.generateNumber(2, 4) + " Lives", "", "§6Reflect " + Core.generateNumber(1, 3), "§7Chance to reflect damage back when hit");
            } else if (r == 2) {
                item.lore("§e§l✸A-TIER", "§a§l" + Core.generateNumber(2, 4) + " Lives", "", "");
            }
            return item.asItemStack();
        } else if (armor == MCCArmor.SKELETON_HELMET) {
            ItemBuilder item = new ItemBuilder(Material.DIAMOND_HELMET, 1);
            item.displayName("§f§lSkeleton Helmet");
            item.unsafeEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
            item.unbreakable(true);
            int r = Core.generateNumber(0, 3);
            if (r == 0) {
                item.lore("§a§l✸B-TIER", "§a§l3 Lives", "", "§6Fragile Escape " + Core.generateNumber(1, 3), "§7Apply weakness to nearby enemies when low");
            } else if (r == 1) {
                item.lore("§a§l✸B-TIER", "§a§l3 Lives", "", "§6Kite " + Core.generateNumber(1, 3), "§7Chance to gain jump boost when hit");
            } else if (r == 2) {
                item.lore("§a§l✸B-TIER", "§a§l3 Lives", "", "");
            }
            return item.asItemStack();
        } else if (armor == MCCArmor.SKELETON_CHESTPLATE) {
            ItemBuilder item = new ItemBuilder(Material.DIAMOND_CHESTPLATE, 1);
            item.displayName("§f§lSkeleton Chestplate");
            item.unsafeEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
            item.unbreakable(true);
            int r = Core.generateNumber(0, 3);
            if (r == 0) {
                item.lore("§a§l✸B-TIER", "§a§l3 Lives", "", "§6Fragile Escape " + Core.generateNumber(1, 3), "§7Apply weakness to nearby enemies when low");
            } else if (r == 1) {
                item.lore("§a§l✸B-TIER", "§a§l3 Lives", "", "§6Kite " + Core.generateNumber(1, 3), "§7Chance to gain jump boost when hit");
            } else if (r == 2) {
                item.lore("§a§l✸B-TIER", "§a§l3 Lives", "", "");
            }
            return item.asItemStack();
        } else if (armor == MCCArmor.SKELETON_LEGGINGS) {
            ItemBuilder item = new ItemBuilder(Material.DIAMOND_LEGGINGS, 1);
            item.displayName("§f§lSkeleton Leggings");
            item.unsafeEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
            item.unbreakable(true);
            int r = Core.generateNumber(0, 3);
            if (r == 0) {
                item.lore("§a§l✸B-TIER", "§a§l3 Lives", "", "§6Fragile Escape " + Core.generateNumber(1, 3), "§7Apply weakness to nearby enemies when low");
            } else if (r == 1) {
                item.lore("§a§l✸B-TIER", "§a§l3 Lives", "", "§6Kite " + Core.generateNumber(1, 3), "§7Chance to gain jump boost when hit");
            } else if (r == 2) {
                item.lore("§a§l✸B-TIER", "§a§l3 Lives", "", "");
            }
            return item.asItemStack();
        } else if (armor == MCCArmor.SKELETON_BOOTS) {
            ItemBuilder item = new ItemBuilder(Material.DIAMOND_BOOTS, 1);
            item.displayName("§f§lSkeleton Boots");
            item.unsafeEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
            item.unbreakable(true);
            int r = Core.generateNumber(0, 3);
            if (r == 0) {
                item.lore("§a§l✸B-TIER", "§a§l3 Lives", "", "§6Fragile Escape " + Core.generateNumber(1, 3), "§7Apply weakness to nearby enemies when low");
            } else if (r == 1) {
                item.lore("§a§l✸B-TIER", "§a§l3 Lives", "", "§6Kite " + Core.generateNumber(1, 3), "§7Chance to gain jump boost when hit");
            } else if (r == 2) {
                item.lore("§a§l✸B-TIER", "§a§l3 Lives", "", "");
            }
            return item.asItemStack();
        } else if (armor == MCCArmor.PIG_HELMET) {
            ItemBuilder item = new ItemBuilder(Material.DIAMOND_HELMET, 1);
            item.displayName("§d§lPig Helmet");
            item.unsafeEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
            item.unbreakable(true);
            int r = Core.generateNumber(0, 3);
            if (r == 0) {
                item.lore("§a§l✸B-TIER", "§a§l3 Lives", "", "§6Miracle " + Core.generateNumber(1, 3), "§7Gain absorption and regeneration when low");
            } else if (r == 1) {
                item.lore("§a§l✸B-TIER", "§a§l3 Lives", "", "§6Scatter " + Core.generateNumber(1, 3), "§7Chance to gain speed 2 when hit");
            } else if (r == 2) {
                item.lore("§a§l✸B-TIER", "§a§l3 Lives", "", "");
            }
            return item.asItemStack();
        } else if (armor == MCCArmor.PIG_CHESTPLATE) {
            ItemBuilder item = new ItemBuilder(Material.DIAMOND_CHESTPLATE, 1);
            item.displayName("§d§lPig Chestplate");
            item.unsafeEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
            item.unbreakable(true);
            int r = Core.generateNumber(0, 3);
            if (r == 0) {
                item.lore("§a§l✸B-TIER", "§a§l3 Lives", "", "§6Miracle " + Core.generateNumber(1, 3), "§7Gain absorption and regeneration when low");
            } else if (r == 1) {
                item.lore("§a§l✸B-TIER", "§a§l3 Lives", "", "§6Scatter " + Core.generateNumber(1, 3), "§7Chance to gain speed 2 when hit");
            } else if (r == 2) {
                item.lore("§a§l✸B-TIER", "§a§l3 Lives", "", "");
            }
            return item.asItemStack();
        } else if (armor == MCCArmor.PIG_LEGGINGS) {
            ItemBuilder item = new ItemBuilder(Material.DIAMOND_LEGGINGS, 1);
            item.displayName("§d§lPig Leggings");
            item.unsafeEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
            item.unbreakable(true);
            int r = Core.generateNumber(0, 3);
            if (r == 0) {
                item.lore("§a§l✸B-TIER", "§a§l3 Lives", "", "§6Miracle " + Core.generateNumber(1, 3), "§7Gain absorption and regeneration when low");
            } else if (r == 1) {
                item.lore("§a§l✸B-TIER", "§a§l3 Lives", "", "§6Scatter " + Core.generateNumber(1, 3), "§7Chance to gain speed 2 when hit");
            } else if (r == 2) {
                item.lore("§a§l✸B-TIER", "§a§l3 Lives", "", "");
            }
            return item.asItemStack();
        } else if (armor == MCCArmor.PIG_BOOTS) {
            ItemBuilder item = new ItemBuilder(Material.DIAMOND_BOOTS, 1);
            item.displayName("§d§lPig Boots");
            item.unsafeEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
            item.unbreakable(true);
            int r = Core.generateNumber(0, 3);
            if (r == 0) {
                item.lore("§a§l✸B-TIER", "§a§l3 Lives", "", "§6Miracle " + Core.generateNumber(1, 3), "§7Gain absorption and regeneration when low");
            } else if (r == 1) {
                item.lore("§a§l✸B-TIER", "§a§l3 Lives", "", "§6Scatter " + Core.generateNumber(1, 3), "§7Chance to gain speed 2 when hit");
            } else if (r == 2) {
                item.lore("§a§l✸B-TIER", "§a§l3 Lives", "", "");
            }
            return item.asItemStack();
        } else if (armor == MCCArmor.SILVERFISH_HELMET) {
            ItemBuilder item = new ItemBuilder(Material.DIAMOND_HELMET, 1);
            item.displayName("§7§lSilverfish Helmet");
            item.unsafeEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
            item.unbreakable(true);
            int r = Core.generateNumber(0, 3);
            if (r == 0) {
                item.lore("§a§l✸B-TIER", "§a§l3 Lives", "", "§6Light Feet 1", "§7Take no fall damage");
            } else if (r == 1) {
                item.lore("§a§l✸B-TIER", "§a§l3 Lives", "", "§6Dodge " + Core.generateNumber(1, 3), "§7Chance to dodge attacks");
            } else if (r == 2) {
                item.lore("§a§l✸B-TIER", "§a§l3 Lives", "", "");
            }
            return item.asItemStack();
        } else if (armor == MCCArmor.SILVERFISH_CHESTPLATE) {
            ItemBuilder item = new ItemBuilder(Material.DIAMOND_CHESTPLATE, 1);
            item.displayName("§7§lSilverfish Chestplate");
            item.unsafeEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
            item.unbreakable(true);
            int r = Core.generateNumber(0, 3);
            if (r == 0) {
                item.lore("§a§l✸B-TIER", "§a§l3 Lives", "", "§6Light Feet 1", "§7Take no fall damage");
            } else if (r == 1) {
                item.lore("§a§l✸B-TIER", "§a§l3 Lives", "", "§6Dodge " + Core.generateNumber(1, 3), "§7Chance to dodge attacks");
            } else if (r == 2) {
                item.lore("§a§l✸B-TIER", "§a§l3 Lives", "", "");
            }
            return item.asItemStack();
        } else if (armor == MCCArmor.SILVERFISH_LEGGINGS) {
            ItemBuilder item = new ItemBuilder(Material.DIAMOND_LEGGINGS, 1);
            item.displayName("§7§lSilverfish Leggings");
            item.unsafeEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
            item.unbreakable(true);
            int r = Core.generateNumber(0, 3);
            if (r == 0) {
                item.lore("§a§l✸B-TIER", "§a§l3 Lives", "", "§6Light Feet 1", "§7Take no fall damage");
            } else if (r == 1) {
                item.lore("§a§l✸B-TIER", "§a§l3 Lives", "", "§6Dodge " + Core.generateNumber(1, 3), "§7Chance to dodge attacks");
            } else if (r == 2) {
                item.lore("§a§l✸B-TIER", "§a§l3 Lives", "", "");
            }
            return item.asItemStack();
        } else if (armor == MCCArmor.SILVERFISH_BOOTS) {
            ItemBuilder item = new ItemBuilder(Material.DIAMOND_BOOTS, 1);
            item.displayName("§7§lSilverfish Boots");
            item.unsafeEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
            item.unbreakable(true);
            int r = Core.generateNumber(0, 3);
            if (r == 0) {
                item.lore("§a§l✸B-TIER", "§a§l3 Lives", "", "§6Light Feet 1", "§7Take no fall damage");
            } else if (r == 1) {
                item.lore("§a§l✸B-TIER", "§a§l3 Lives", "", "§6Dodge " + Core.generateNumber(1, 3), "§7Chance to dodge attacks");
            } else if (r == 2) {
                item.lore("§a§l✸B-TIER", "§a§l3 Lives", "", "");
            }
            return item.asItemStack();
        } else if (armor == MCCArmor.DIAMOND_HELMET) {
            ItemBuilder item = new ItemBuilder(Material.DIAMOND_HELMET, 1);
            item.displayName("§b§lDiamond Helmet");
            item.unbreakable(true);
            item.lore("§a§l✸B-TIER", "§a§l3 Lives", "", "");
            return item.asItemStack();
        } else if (armor == MCCArmor.DIAMOND_CHESTPLATE) {
            ItemBuilder item = new ItemBuilder(Material.DIAMOND_CHESTPLATE, 1);
            item.displayName("§b§lDiamond Chestplate");
            item.unbreakable(true);
            item.lore("§a§l✸B-TIER", "§a§l3 Lives", "", "");
            return item.asItemStack();
        } else if (armor == MCCArmor.DIAMOND_LEGGINGS) {
            ItemBuilder item = new ItemBuilder(Material.DIAMOND_LEGGINGS, 1);
            item.displayName("§b§lDiamond Leggings");
            item.unbreakable(true);
            item.lore("§a§l✸B-TIER", "§a§l3 Lives", "", "");
            return item.asItemStack();
        } else if (armor == MCCArmor.DIAMOND_BOOTS) {
            ItemBuilder item = new ItemBuilder(Material.DIAMOND_BOOTS, 1);
            item.displayName("§b§lDiamond Boots");
            item.unbreakable(true);
            item.lore("§a§l✸B-TIER", "§a§l3 Lives", "", "");
            return item.asItemStack();
        }
        return null;
    }

    public static ItemStack getWeapon(MCCWeapon weapon) {
        if (weapon == MCCWeapon.DRAGON_SWORD) {
            ItemBuilder item = new ItemBuilder(Material.DIAMOND_SWORD, 1);
            item.displayName("§5§lDragon Sword");
            item.enchant(Enchantment.DAMAGE_ALL, 1);
            item.unbreakable(true);
            int r = Core.generateNumber(0, 3);
            if (r == 0) {
                item.lore("§4§l✸S-TIER", "§a§l" + Core.generateNumber(3, 5) + " Lives", "", "§6Wither " + Core.generateNumber(1, 3), "§7Chance to apply wither to enemies");
            } else if (r == 1) {
                item.lore("§4§l✸S-TIER", "§a§l" + Core.generateNumber(3, 5) + " Lives", "", "§6Glare " + Core.generateNumber(1, 3), "§7Chance to paralyze enemies");
            } else if (r == 2) {
                item.lore("§4§l✸S-TIER", "§a§l" + Core.generateNumber(3, 5) + " Lives", "", "§6Dragon Wrath " + Core.generateNumber(1, 3), "§7Chance to deal more damage");
            } else if (r == 3) {
                item.lore("§4§l✸S-TIER", "§a§l" + Core.generateNumber(3, 5) + " Lives", "");
            }
            return item.asItemStack();
        } else if (weapon == MCCWeapon.DRAGON_BOW) {
            ItemBuilder item = new ItemBuilder(Material.BOW, 1);
            item.displayName("§5§lDragon Bow");
            item.enchant(Enchantment.ARROW_DAMAGE, 4);
            item.enchant(Enchantment.ARROW_INFINITE, 1);
            item.enchant(Enchantment.ARROW_FIRE, 1);
            item.lore("§4§l✸S-TIER", "§a§l" + Core.generateNumber(3, 5) + " Lives", "");
            item.unbreakable(true);
            return item.asItemStack();
        } else if (weapon == MCCWeapon.GIANT_AXE) {
            ItemBuilder item = new ItemBuilder(Material.DIAMOND_AXE, 1);
            item.displayName("§2§lGiant Axe");
            item.unsafeEnchant(Enchantment.DAMAGE_ALL, 2);
            item.unbreakable(true);
            int r = Core.generateNumber(0, 3);
            if (r == 0) {
                item.lore("§4§l✸S-TIER", "§a§l" + Core.generateNumber(3, 5) + " Lives", "", "§6Freeze " + Core.generateNumber(1, 3), "§7Chance to freeze enemies");
            } else if (r == 1) {
                item.lore("§4§l✸S-TIER", "§a§l" + Core.generateNumber(3, 5) + " Lives", "", "§6Strip " + Core.generateNumber(1, 3), "§7Chance to take off an armor piece");
            } else if (r == 2) {
                item.lore("§4§l✸S-TIER", "§a§l" + Core.generateNumber(3, 5) + " Lives", "", "§6Demolish " + Core.generateNumber(1, 3), "§7Your initial hit stuns the enemy");
            } else if (r == 3) {
                item.lore("§4§l✸S-TIER", "§a§l" + Core.generateNumber(3, 5) + " Lives", "");
            }
            return item.asItemStack();
        } else if (weapon == MCCWeapon.GIANT_BOW) {
            ItemBuilder item = new ItemBuilder(Material.BOW, 1);
            item.displayName("§2§lGiant Bow");
            item.enchant(Enchantment.ARROW_KNOCKBACK, 2);
            item.enchant(Enchantment.ARROW_INFINITE, 1);
            item.lore("§4§l✸S-TIER", "§a§l" + Core.generateNumber(3, 5) + " Lives", "");
            item.unbreakable(true);
            return item.asItemStack();
        } else if (weapon == MCCWeapon.SQUID_SWORD) {
            ItemBuilder item = new ItemBuilder(Material.DIAMOND_SWORD, 1);
            item.displayName("§3§lSquid Sword");
            item.unbreakable(true);
            int r = Core.generateNumber(0, 2);
            if (r == 0) {
                item.lore("§e§l✸A-TIER", "§a§l" + Core.generateNumber(3, 5) + " Lives", "", "§6Blinding Strike " + Core.generateNumber(1, 3), "§7Chance to blind enemies");
            } else if (r == 1) {
                item.lore("§e§l✸A-TIER", "§a§l" + Core.generateNumber(3, 5) + " Lives", "", "§6Torrent " + Core.generateNumber(1, 3), "§7Deal increased damage in water");
            } else if (r == 2) {
                item.lore("§e§l✸A-TIER", "§a§l" + Core.generateNumber(3, 5) + " Lives", "");
            }
            return item.asItemStack();
        } else if (weapon == MCCWeapon.GOLEM_AXE) {
            ItemBuilder item = new ItemBuilder(Material.DIAMOND_AXE, 1);
            item.unsafeEnchant(Enchantment.DAMAGE_ALL, 1);
            item.displayName("§8§lGolem Axe");
            item.unbreakable(true);
            int r = Core.generateNumber(0, 2);
            if (r == 0) {
                item.lore("§e§l✸A-TIER", "§a§l" + Core.generateNumber(3, 5) + " Lives", "", "§6Icy Bit " + Core.generateNumber(1, 3), "§7Chance to slow enemies");
            } else if (r == 1) {
                item.lore("§e§l✸A-TIER", "§a§l" + Core.generateNumber(3, 5) + " Lives", "", "§6Knockup " + Core.generateNumber(1, 3), "§7Chance to knockup enemies");
            } else if (r == 2) {
                item.lore("§e§l✸A-TIER", "§a§l" + Core.generateNumber(3, 5) + " Lives", "");
            }
            return item.asItemStack();
        } else if (weapon == MCCWeapon.GOLEM_BOW) {
            ItemBuilder item = new ItemBuilder(Material.BOW, 1);
            item.displayName("§8§lGolem Bow");
            item.unbreakable(true);
            item.enchant(Enchantment.ARROW_KNOCKBACK, 1);
            item.enchant(Enchantment.ARROW_INFINITE, 1);
            item.lore("§e§l✸A-TIER", "§a§l" + Core.generateNumber(2, 4) + " Lives", "");
            return item.asItemStack();
        } else if (weapon == MCCWeapon.SKELETON_BOW) {
            ItemBuilder item = new ItemBuilder(Material.BOW, 1);
            item.displayName("§f§lSkeleton Bow");
            item.unbreakable(true);
            item.enchant(Enchantment.ARROW_DAMAGE, 3);
            item.enchant(Enchantment.ARROW_INFINITE, 1);
            item.lore("§a§l✸B-TIER", "§a§l3 Lives", "");
            return item.asItemStack();
        } else if (weapon == MCCWeapon.PIG_SWORD) {
            ItemBuilder item = new ItemBuilder(Material.IRON_SWORD, 1);
            item.displayName("§d§lPig Sword");
            item.unbreakable(true);
            item.lore("§a§l✸B-TIER", "§a§l3 Lives", "");
            return item.asItemStack();
        } else if (weapon == MCCWeapon.SILVERFISH_AXE) {
            ItemBuilder item = new ItemBuilder(Material.IRON_AXE, 1);
            item.displayName("§7§lSilverfish Axe");
            item.unbreakable(true);
            item.unsafeEnchant(Enchantment.DAMAGE_ALL, 1);
            item.lore("§a§l✸B-TIER", "§a§l3 Lives", "");
            return item.asItemStack();
        } else if (weapon == MCCWeapon.IRON_SWORD) {
            ItemBuilder item = new ItemBuilder(Material.IRON_SWORD, 1);
            item.displayName("§7§lIron Sword");
            item.unbreakable(true);
            item.lore("§a§l✸B-TIER", "§a§l3 Lives", "");
            return item.asItemStack();
        }
        return null;
    }

    public static ItemStack getArtifact(MCCArtifact artifact) {
        if (artifact == MCCArtifact.GUARDIAN_ROD) {
            ItemBuilder item = new ItemBuilder(Material.FISHING_ROD, 1);
            item.displayName("§9Guardian Rod");
            int r = Core.generateNumber(0, 1);
            if (r == 0) {
                item.unsafeEnchant(Enchantment.DURABILITY, 1);
            }
            item.lore("§4§l✸S-TIER", "", "§eIncreases loot from fishing");
            return item.asItemStack();
        } else if (artifact == MCCArtifact.WITHER_PICKAXE) {
            ItemBuilder item = new ItemBuilder(Material.IRON_PICKAXE, 1);
            item.displayName("§8Wither Pickaxe");
            int r = Core.generateNumber(0, 2);
            if (r == 2) {
                item.unsafeEnchant(Enchantment.DURABILITY, 2);
            } else if (r == 1) {
                item.unsafeEnchant(Enchantment.DURABILITY, 1);
            }
            item.lore("§4§l✸S-TIER", "", "§eIncreases ingots when mining");
            return item.asItemStack();
        } else if (artifact == MCCArtifact.GIANT_GRINDER) {
            ItemBuilder item = new ItemBuilder(Material.IRON_SWORD, 1);
            item.displayName("§2Giant Grinder");
            item.unsafeEnchant(Enchantment.DAMAGE_UNDEAD, 3);
            int r = Core.generateNumber(0, 1);
            if (r == 0) {
                item.unsafeEnchant(Enchantment.DURABILITY, 2);
            } else {
                item.unsafeEnchant(Enchantment.DURABILITY, 1);
            }
            item.lore("§4§l✸S-TIER", "", "§eIncreases essence gained from mobs");
            return item.asItemStack();
        } else if (artifact == MCCArtifact.EXTREME_KNOCKBACK_STICK) {
            ItemBuilder item = new ItemBuilder(Material.STICK, 1);
            item.displayName("§4§lExtreme Knockback Stick");
            item.enchant(Enchantment.KNOCKBACK, 2);
            item.flag(ItemFlag.HIDE_ENCHANTS);
            item.lore("§4§l✸S-TIER", "§a§l" + Core.generateNumber(3, 5) + " Lives", "");
            return item.asItemStack();
        } else if (artifact == MCCArtifact.CRYSTAL_BEATER) {
            ItemBuilder item = new ItemBuilder(Material.GOLDEN_SWORD, 1);
            item.displayName("§5§lCrystal Beater");
            item.setGlowing(true);
            item.unbreakable(true);
            item.lore("§4§l✸S-TIER", "§a§l" + Core.generateNumber(3, 5) + " Lives", "");
            return item.asItemStack();
        } else if (artifact == MCCArtifact.SQUID_ROD) {
            ItemBuilder item = new ItemBuilder(Material.FISHING_ROD, 1);
            item.displayName("§3Squid Rod");
            item.lore("§e§l✸A-TIER", "", "§eIncreases loot from fishing");
            return item.asItemStack();
        } else if (artifact == MCCArtifact.PIGMAN_PICKAXE) {
            ItemBuilder item = new ItemBuilder(Material.IRON_PICKAXE, 1);
            item.displayName("§cPigman Pickaxe");
            item.lore("§e§l✸A-TIER", "", "§eIncreases ingots when mining");
            return item.asItemStack();
        } else if (artifact == MCCArtifact.ZOMBIE_GRINDER) {
            ItemBuilder item = new ItemBuilder(Material.IRON_SWORD, 1);
            item.displayName("§2Zombie Grinder");
            item.unsafeEnchant(Enchantment.DAMAGE_UNDEAD, 3);
            item.lore("§e§l✸A-TIER", "", "§eIncreases essence gained from mobs");
            return item.asItemStack();
        } else if (artifact == MCCArtifact.FISHING_ROD) {
            ItemBuilder item = new ItemBuilder(Material.FISHING_ROD, 1);
            item.displayName("§eFishing Rod");
            item.lore("§a§l✸B-TIER", "", "");
            return item.asItemStack();
        } else if (artifact == MCCArtifact.IRON_PICKAXE) {
            ItemBuilder item = new ItemBuilder(Material.IRON_PICKAXE, 1);
            item.displayName("§7Iron Pickaxe");
            item.lore("§a§l✸B-TIER", "", "");
            return item.asItemStack();
        } else if (artifact == MCCArtifact.KNOCKBACK_STICK) {
            ItemBuilder item = new ItemBuilder(Material.STICK, 1);
            item.displayName("§c§lKnockback Stick");
            item.enchant(Enchantment.KNOCKBACK, 1);
            item.flag(ItemFlag.HIDE_ENCHANTS);
            item.lore("§a§l✸A-TIER", "§a§l" + Core.generateNumber(2, 4) + " Lives", "");
            return item.asItemStack();
        }
        return null;
    }

    public static ItemStack getArmorVoucher(boolean special) {
        if (special) {
            ItemBuilder item = new ItemBuilder(Material.BOOK, 1);
            item.displayName("§6§lSpecial Armor Voucher");
            item.lore("§eRight click to get a random armor.");
            item.setGlowing(true);
            return item.asItemStack();
        } else {
            ItemBuilder item = new ItemBuilder(Material.BOOK, 1);
            item.displayName("§6§lArmor Voucher");
            item.lore("§eRight click to get a random armor.");
            item.setGlowing(true);
            return item.asItemStack();
        }
    }

    public static ItemStack getWeaponVoucher(boolean special) {
        if (special) {
            ItemBuilder item = new ItemBuilder(Material.BOOK, 1);
            item.displayName("§6§lSpecial Weapon Voucher");
            item.lore("§eRight click to get a random weapon.");
            item.setGlowing(true);
            return item.asItemStack();
        } else {
            ItemBuilder item = new ItemBuilder(Material.BOOK, 1);
            item.displayName("§6§lWeapon Voucher");
            item.lore("§eRight click to get a random weapon.");
            item.setGlowing(true);
            return item.asItemStack();
        }
    }

    public static ItemStack getArtifactVoucher(boolean special) {
        if (special) {
            ItemBuilder item = new ItemBuilder(Material.BOOK, 1);
            item.displayName("§6§lSpecial Artifact Voucher");
            item.lore("§eRight click to get a random artifact.");
            item.setGlowing(true);
            return item.asItemStack();
        } else {
            ItemBuilder item = new ItemBuilder(Material.BOOK, 1);
            item.displayName("§6§lArtifact Voucher");
            item.lore("§eRight click to get a random artifact.");
            item.setGlowing(true);
            return item.asItemStack();
        }
    }

    public static ItemStack getSTierVoucher() {
        ItemBuilder item = new ItemBuilder(Material.BOOK, 1);
        item.displayName("§4§lS-Tier Voucher");
        item.lore("§eRight click to get a random S-Tier item.");
        item.setGlowing(true);
        return item.asItemStack();
    }

    public static ItemStack getATierVoucher() {
        ItemBuilder item = new ItemBuilder(Material.BOOK, 1);
        item.displayName("§e§lA-Tier Voucher");
        item.lore("§eRight click to get a random A-Tier item.");
        item.setGlowing(true);
        return item.asItemStack();
    }

    public static ItemStack getGeneratorVoucher() {
        ItemBuilder item = new ItemBuilder(Material.BOOK, 1);
        item.displayName("§8§lGenerator Voucher");
        item.lore("§eRight click to get a random generator.");
        item.setGlowing(true);
        return item.asItemStack();
    }

    public static ItemStack getPetVoucher() {
        ItemBuilder item = new ItemBuilder(Material.BOOK, 1);
        item.displayName("§e§lPet Voucher");
        item.lore("§eRight click to get a random pet.");
        item.setGlowing(true);
        return item.asItemStack();
    }

    public static ItemStack getEssence(boolean rare, int amount) {
        if (!rare) {
            ItemBuilder item = new ItemBuilder(Material.PRISMARINE_CRYSTALS, 1);
            item.displayName("§3Essence");
            item.amount(amount);
            return item.asItemStack();
        } else {
            ItemBuilder item = new ItemBuilder(Material.PRISMARINE_SHARD, 1);
            item.displayName("§3Rare Essence");
            item.amount(amount);
            return item.asItemStack();
        }
    }

    public static ItemStack getIngot(boolean rare, int amount) {
        if (!rare) {
            ItemBuilder item = new ItemBuilder(Material.IRON_INGOT, 1);
            item.displayName("§7Iron Ingot");
            item.amount(amount);
            return item.asItemStack();
        } else {
            ItemBuilder item = new ItemBuilder(Material.GOLD_INGOT, 1);
            item.displayName("§eGold Ingot");
            item.amount(amount);
            return item.asItemStack();
        }
    }

    public static ItemStack getCrate(Crate crate) {
        switch (crate) {
            case VOTE_CRATE:
                return null;
            case BASIC_CRATE:
                return null;
            case SUPER_CRATE:
                return null;
            case ULTRA_CRATE:
                return null;
            case GODLY_CRATE:

                return null;
        }
        return null;
    }

    public static ItemStack getRandomMoneyNote(int min, int max) {
        ItemBuilder item = new ItemBuilder(Material.PAPER, 1);
        item.displayName("§2§lMoney Note");
        int value = Core.generateNumber(min, max);
        item.lore("§6Value: $" + value, "", "§eRight click to redeem the note");
        item.setGlowing(true);
        return item.asItemStack();
    }

    public static ItemStack getBlacksmithsHammer() {
        ItemBuilder item = new ItemBuilder(Material.GOLDEN_AXE, 1);
        item.displayName("§4§lBlacksmith's Hammer");
        item.lore("§eHand this to the blacksmith to reroll an armor enchant.");
        item.setGlowing(true);
        return item.asItemStack();
    }

    public static ItemStack getBlacksmithsMagmaRod() {
        ItemBuilder item = new ItemBuilder(Material.BLAZE_ROD, 1);
        item.displayName("§4§lBlacksmith's Magma Rod");
        item.lore("§eHand this to the blacksmith to reroll a sword enchant.");
        item.setGlowing(true);
        return item.asItemStack();
    }

    public static ItemStack getBlacksmithsMagicDust() {
        ItemBuilder item = new ItemBuilder(Material.REDSTONE, 1);
        item.displayName("§4§lBlacksmith's Magic Dust");
        item.lore("§eHand this to the blacksmith to increase an item's enchant level.");
        item.setGlowing(true);
        return item.asItemStack();
    }

    public static ItemStack getBlacksmithsLifeOrb() {
        ItemBuilder item = new ItemBuilder(Material.HEART_OF_THE_SEA, 1);
        item.displayName("§d§lBlacksmith's Life Orb");
        item.lore("§eHand this to the blacksmith to increase an item's lives.");
        item.setGlowing(true);
        return item.asItemStack();
    }

    public static ItemStack getGenerator(GeneratorType type) {
        ItemBuilder item;
        switch (type) {
            case MONEY:
                item = new ItemBuilder(CustomHeadManager.heads.get("moneyprinter"));
                item.displayName("§a§lMoney Generator");
                item.lore("§eRight click to claim this generator", "§cNote: You can only claim up to", "§c4 generators");
                return item.asItemStack();
            case INGOT:
                item = new ItemBuilder(CustomHeadManager.heads.get("minecartchest"));
                item.displayName("§a§lIngot Generator");
                item.lore("§eRight click to claim this generator", "§cNote: You can only claim up to", "§c4 generators");
                return item.asItemStack();
            case ESSENCE:
                item = new ItemBuilder(CustomHeadManager.heads.get("mobspawner"));
                item.displayName("§a§lEssence Generator");
                item.lore("§eRight click to claim this generator", "§cNote: You can only claim up to", "§c4 generators");
                return item.asItemStack();
        }
        return null;
    }
}
