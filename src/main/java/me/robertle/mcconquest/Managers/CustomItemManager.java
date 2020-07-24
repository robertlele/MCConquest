package me.robertle.mcconquest.Managers;

import me.robertle.mcconquest.*;
import net.splodgebox.elitelootbox.EliteLootbox;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CustomItemManager {

    //Add to which section

    public static ItemStack getRandomArmor() {
        List<MCCArmor> bag = new ArrayList<>();
        for (int i = 0; i < 60; i++) {
            if (i < 20) {
                for (MCCArmor armor : MCCArmor.values()) {
                    if (armor.getTier() == 'S' && !armor.limited) bag.add(armor);
                }
            }
            if (i < 60) {
                for (MCCArmor armor : MCCArmor.values()) {
                    if (armor.getTier() == 'A') bag.add(armor);
                }
            }
            if (i < 20) {
                for (MCCArmor armor : MCCArmor.values()) {
                    if (armor.getTier() == 'B') bag.add(armor);
                }
            }
        }
        Collections.shuffle(bag);
        return getArmorPiece(bag.get(Core.generateNumber(0, bag.size() - 1)));
    }

    public static ItemStack getRandomWeapon() {
        List<MCCWeapon> bag = new ArrayList<>();
        for (int i = 0; i < 60; i++) {
            if (i < 20) {
                for (MCCWeapon weapon : MCCWeapon.values()) {
                    if (weapon.getTier() == 'S' && !weapon.limited) bag.add(weapon);
                }
            }
            if (i < 60) {
                for (MCCWeapon weapon : MCCWeapon.values()) {
                    if (weapon.getTier() == 'A') bag.add(weapon);
                }
            }
            if (i < 20) {
                for (MCCWeapon weapon : MCCWeapon.values()) {
                    if (weapon.getTier() == 'B') bag.add(weapon);
                }
            }
        }
        Collections.shuffle(bag);
        return getWeapon(bag.get(Core.generateNumber(0, bag.size() - 1)));
    }

    public static ItemStack getRandomSword() {
        List<MCCWeapon> bag = new ArrayList<>();
        for (MCCWeapon weapon : MCCWeapon.values()) {
            if (weapon.toString().contains("SWORD") && (weapon.getTier() == 'S' || weapon.getTier() == 'A')) {
                bag.add(weapon);
                if (!weapon.limited) bag.add(weapon);
            }
        }
        Collections.shuffle(bag);
        return getWeapon(bag.get(Core.generateNumber(0, bag.size() - 1)));
    }

    public static ItemStack getRandomAxe() {
        List<MCCWeapon> bag = new ArrayList<>();
        for (MCCWeapon weapon : MCCWeapon.values()) {
            if (weapon.toString().contains("AXE") && (weapon.getTier() == 'S' || weapon.getTier() == 'A')) {
                bag.add(weapon);
                if (!weapon.limited) bag.add(weapon);
            }
        }
        Collections.shuffle(bag);
        return getWeapon(bag.get(Core.generateNumber(0, bag.size() - 1)));
    }

    public static ItemStack getRandomSArmor() {
        List<MCCArmor> bag = new ArrayList<>();
        for (MCCArmor armor : MCCArmor.values()) {
            if (armor.getTier() == 'S') {
                bag.add(armor);
                if (!armor.limited) bag.add(armor);
            }
        }
        Collections.shuffle(bag);
        return getArmorPiece(bag.get(Core.generateNumber(0, bag.size() - 1)));
    }

    public static ItemStack getRandomKit() {
        List<MCCKit> bag = new ArrayList<>();
        for (MCCKit kit : MCCKit.values()) {
            if (kit != MCCKit.OMEGA && kit != MCCKit.ALPHA && kit != MCCKit.GAMMA && kit != MCCKit.DELTA && kit != MCCKit.ETA)
                bag.add(kit);
        }
        Collections.shuffle(bag);
        return getKitVoucher(bag.get(Core.generateNumber(0, bag.size() - 1)));
    }

    public static ItemStack getRandomZeusArmor() {
        List<MCCArmor> bag = new ArrayList<>();
        for (MCCArmor armor : MCCArmor.values()) {
            if (armor.name().contains("ZEUS")) bag.add(armor);
        }
        Collections.shuffle(bag);
        return getArmorPiece(bag.get(Core.generateNumber(0, bag.size() - 1)));
    }

    public static ItemStack getRandomHadesArmor() {
        List<MCCArmor> bag = new ArrayList<>();
        for (MCCArmor armor : MCCArmor.values()) {
            if (armor.name().contains("HADES")) bag.add(armor);
        }
        Collections.shuffle(bag);
        return getArmorPiece(bag.get(Core.generateNumber(0, bag.size() - 1)));
    }

    public static ItemStack getRandomZeusWeapon() {
        List<MCCWeapon> bag = new ArrayList<>();
        for (MCCWeapon weapon : MCCWeapon.values()) {
            if (weapon.name().contains("ZEUS")) bag.add(weapon);
        }
        Collections.shuffle(bag);
        return getWeapon(bag.get(Core.generateNumber(0, bag.size() - 1)));
    }

    public static ItemStack getRandomHadesWeapon() {
        List<MCCWeapon> bag = new ArrayList<>();
        for (MCCWeapon weapon : MCCWeapon.values()) {
            if (weapon.name().contains("HADES")) bag.add(weapon);
        }
        Collections.shuffle(bag);
        return getWeapon(bag.get(Core.generateNumber(0, bag.size() - 1)));
    }

    //Add to which tier

    public static ItemStack getRandomSTier() {
        int r = Core.generateNumber(0, 2);
        if (r == 0) {
            List<MCCArmor> bag = new ArrayList<>();
            for (MCCArmor armor : MCCArmor.values()) {
                if (armor.getTier() == 'S') {
                    bag.add(armor);
                    if (!armor.limited) bag.add(armor);
                }
            }
            return getArmorPiece(bag.get(Core.generateNumber(0, bag.size() - 1)));
        } else if (r == 1) {
            List<MCCWeapon> bag = new ArrayList<>();
            for (MCCWeapon weapon : MCCWeapon.values()) {
                if (weapon.getTier() == 'S') {
                    bag.add(weapon);
                    if (!weapon.limited) bag.add(weapon);
                }
            }
            return getWeapon(bag.get(Core.generateNumber(0, bag.size() - 1)));
        } else if (r == 2) {
            List<MCCArtifact> bag = new ArrayList<>();
            for (MCCArtifact artifact : MCCArtifact.values()) {
                if (artifact.getTier() == 'S') bag.add(artifact);
            }
            return getArtifact(bag.get(Core.generateNumber(0, bag.size() - 1)));
        }
        return null;
    }

    public static ItemStack getRandomATier() {
        int r = Core.generateNumber(0, 1);
        if (r == 0) {
            List<MCCArmor> bag = new ArrayList<>();
            for (MCCArmor armor : MCCArmor.values()) {
                if (armor.getTier() == 'A') bag.add(armor);
            }
            return getArmorPiece(bag.get(Core.generateNumber(0, bag.size() - 1)));
        } else if (r == 1) {
            List<MCCWeapon> bag = new ArrayList<>();
            for (MCCWeapon weapon : MCCWeapon.values()) {
                if (weapon.getTier() == 'A') bag.add(weapon);
            }
            return getWeapon(bag.get(Core.generateNumber(0, bag.size() - 1)));
        } else if (r == 2) {
            List<MCCArtifact> bag = new ArrayList<>();
            for (MCCArtifact artifact : MCCArtifact.values()) {
                if (artifact.getTier() == 'A') bag.add(artifact);
            }
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
                item.lore("§4§l✸S-TIER", "§a§l" + Core.generateNumber(1, 3) + " Lives", "", "§6Disorder " + Core.generateNumber(1, 3), "§7Chance to shuffle attacker's hotbar when hit");
            } else if (r == 1) {
                item.lore("§4§l✸S-TIER", "§a§l" + Core.generateNumber(1, 3) + " Lives", "", "§6Immunity " + Core.generateNumber(1, 3), "§7Chance for debuffs not to be applied");
            } else if (r == 2) {
                item.lore("§4§l✸S-TIER", "§a§l" + Core.generateNumber(1, 3) + " Lives", "");
            }
            return item.asItemStack();
        } else if (armor == MCCArmor.DRAGON_CHESTPLATE) {
            ItemBuilder item = new ItemBuilder(Material.DIAMOND_CHESTPLATE, 1);
            item.displayName("§5§lDragon Chestplate");
            item.enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
            item.unbreakable(true);
            int r = Core.generateNumber(0, 2);
            if (r == 0) {
                item.lore("§4§l✸S-TIER", "§a§l" + Core.generateNumber(1, 3) + " Lives", "", "§6Disorder " + Core.generateNumber(1, 3), "§7Chance to shuffle attacker's hotbar when hit");
            } else if (r == 1) {
                item.lore("§4§l✸S-TIER", "§a§l" + Core.generateNumber(1, 3) + " Lives", "", "§6Immunity " + Core.generateNumber(1, 3), "§7Chance for debuffs not to be applied");
            } else if (r == 2) {
                item.lore("§4§l✸S-TIER", "§a§l" + Core.generateNumber(1, 3) + " Lives", "");
            }
            return item.asItemStack();
        } else if (armor == MCCArmor.DRAGON_LEGGINGS) {
            ItemBuilder item = new ItemBuilder(Material.DIAMOND_LEGGINGS, 1);
            item.displayName("§5§lDragon Leggings");
            item.enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
            item.unbreakable(true);
            int r = Core.generateNumber(0, 2);
            if (r == 0) {
                item.lore("§4§l✸S-TIER", "§a§l" + Core.generateNumber(1, 3) + " Lives", "", "§6Disorder " + Core.generateNumber(1, 3), "§7Chance to shuffle attacker's hotbar when hit");
            } else if (r == 1) {
                item.lore("§4§l✸S-TIER", "§a§l" + Core.generateNumber(1, 3) + " Lives", "", "§6Immunity " + Core.generateNumber(1, 3), "§7Chance for debuffs not to be applied");
            } else if (r == 2) {
                item.lore("§4§l✸S-TIER", "§a§l" + Core.generateNumber(1, 3) + " Lives", "");
            }
            return item.asItemStack();
        } else if (armor == MCCArmor.DRAGON_BOOTS) {
            ItemBuilder item = new ItemBuilder(Material.DIAMOND_BOOTS, 1);
            item.displayName("§5§lDragon Boots");
            item.enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
            item.unbreakable(true);
            int r = Core.generateNumber(0, 2);
            if (r == 0) {
                item.lore("§4§l✸S-TIER", "§a§l" + Core.generateNumber(1, 3) + " Lives", "", "§6Disorder " + Core.generateNumber(1, 3), "§7Chance to shuffle attacker's hotbar when hit");
            } else if (r == 1) {
                item.lore("§4§l✸S-TIER", "§a§l" + Core.generateNumber(1, 3) + " Lives", "", "§6Immunity " + Core.generateNumber(1, 3), "§7Chance for debuffs not to be applied");
            } else if (r == 2) {
                item.lore("§4§l✸S-TIER", "§a§l" + Core.generateNumber(1, 3) + " Lives", "");
            }
            return item.asItemStack();
        } else if (armor == MCCArmor.ZEUS_HELMET) {
            ItemBuilder item = new ItemBuilder(Material.DIAMOND_HELMET, 1);
            item.displayName("§6§lZeus Helmet");
            item.unsafeEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
            item.unbreakable(true);
            int r = Core.generateNumber(0, 2);
            if (r == 0) {
                item.lore("§4§l✸S-TIER", "§a§l" + Core.generateNumber(1, 3) + " Lives", "", "§6Lightning Rod " + Core.generateNumber(1, 3), "§7Chance to smite nearby enemies");
            } else if (r == 1) {
                item.lore("§4§l✸S-TIER", "§a§l" + Core.generateNumber(1, 3) + " Lives", "", "§6Thunder God 1", "§7When blinded, gain strength 1 & speed 2");
            } else if (r == 2) {
                item.lore("§4§l✸S-TIER", "§a§l" + Core.generateNumber(1, 3) + " Lives", "");
            }
            return item.asItemStack();
        } else if (armor == MCCArmor.ZEUS_CHESTPLATE) {
            ItemBuilder item = new ItemBuilder(Material.DIAMOND_CHESTPLATE, 1);
            item.displayName("§6§lZeus Chestplate");
            item.unsafeEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
            item.unbreakable(true);
            int r = Core.generateNumber(0, 2);
            if (r == 0) {
                item.lore("§4§l✸S-TIER", "§a§l" + Core.generateNumber(1, 3) + " Lives", "", "§6Lightning Rod " + Core.generateNumber(1, 3), "§7Chance to smite nearby enemies");
            } else if (r == 1) {
                item.lore("§4§l✸S-TIER", "§a§l" + Core.generateNumber(1, 3) + " Lives", "", "§6Thunder God 1", "§7When blinded, gain strength 1 & speed 2");
            } else if (r == 2) {
                item.lore("§4§l✸S-TIER", "§a§l" + Core.generateNumber(1, 3) + " Lives", "");
            }
            return item.asItemStack();
        } else if (armor == MCCArmor.ZEUS_LEGGINGS) {
            ItemBuilder item = new ItemBuilder(Material.DIAMOND_LEGGINGS, 1);
            item.displayName("§6§lZeus Leggings");
            item.unsafeEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
            item.unbreakable(true);
            int r = Core.generateNumber(0, 2);
            if (r == 0) {
                item.lore("§4§l✸S-TIER", "§a§l" + Core.generateNumber(1, 3) + " Lives", "", "§6Lightning Rod " + Core.generateNumber(1, 3), "§7Chance to smite nearby enemies");
            } else if (r == 1) {
                item.lore("§4§l✸S-TIER", "§a§l" + Core.generateNumber(1, 3) + " Lives", "", "§6Thunder God 1", "§7When blinded, gain strength 1 & speed 2");
            } else if (r == 2) {
                item.lore("§4§l✸S-TIER", "§a§l" + Core.generateNumber(1, 3) + " Lives", "");
            }
            return item.asItemStack();
        } else if (armor == MCCArmor.ZEUS_BOOTS) {
            ItemBuilder item = new ItemBuilder(Material.DIAMOND_BOOTS, 1);
            item.displayName("§6§lZeus Boots");
            item.unsafeEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
            item.unbreakable(true);
            int r = Core.generateNumber(0, 2);
            if (r == 0) {
                item.lore("§4§l✸S-TIER", "§a§l" + Core.generateNumber(1, 3) + " Lives", "", "§6Lightning Rod " + Core.generateNumber(1, 3), "§7Chance to smite nearby enemies");
            } else if (r == 1) {
                item.lore("§4§l✸S-TIER", "§a§l" + Core.generateNumber(1, 3) + " Lives", "", "§6Thunder God 1", "§7When blinded, gain strength 1 & speed 2");
            } else if (r == 2) {
                item.lore("§4§l✸S-TIER", "§a§l" + Core.generateNumber(1, 3) + " Lives", "");
            }
            return item.asItemStack();
        } else if (armor == MCCArmor.HADES_HELMET) {
            ItemBuilder item = new ItemBuilder(Material.DIAMOND_HELMET, 1);
            item.displayName("§4§lHades Helmet");
            item.unsafeEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
            item.unbreakable(true);
            int r = Core.generateNumber(0, 2);
            if (r == 0) {
                item.lore("§4§l✸S-TIER", "§a§l" + Core.generateNumber(1, 3) + " Lives", "", "§6Undead " + Core.generateNumber(1, 3), "§7Chance to apply weakness when hit");
            } else if (r == 1) {
                item.lore("§4§l✸S-TIER", "§a§l" + Core.generateNumber(1, 3) + " Lives", "", "§6Dying Rage 1", "§7When stunned, gain strength 2");
            } else if (r == 2) {
                item.lore("§4§l✸S-TIER", "§a§l" + Core.generateNumber(1, 3) + " Lives", "");
            }
            return item.asItemStack();
        } else if (armor == MCCArmor.HADES_CHESTPLATE) {
            ItemBuilder item = new ItemBuilder(Material.DIAMOND_CHESTPLATE, 1);
            item.displayName("§4§lHades Chestplate");
            item.unsafeEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
            item.unbreakable(true);
            int r = Core.generateNumber(0, 2);
            if (r == 0) {
                item.lore("§4§l✸S-TIER", "§a§l" + Core.generateNumber(1, 3) + " Lives", "", "§6Undead " + Core.generateNumber(1, 3), "§7Chance to apply weakness when hit");
            } else if (r == 1) {
                item.lore("§4§l✸S-TIER", "§a§l" + Core.generateNumber(1, 3) + " Lives", "", "§6Dying Rage 1", "§7When stunned, gain strength 2");
            } else if (r == 2) {
                item.lore("§4§l✸S-TIER", "§a§l" + Core.generateNumber(1, 3) + " Lives", "");
            }
            return item.asItemStack();
        } else if (armor == MCCArmor.HADES_LEGGINGS) {
            ItemBuilder item = new ItemBuilder(Material.DIAMOND_LEGGINGS, 1);
            item.displayName("§4§lHades Leggings");
            item.unsafeEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
            item.unbreakable(true);
            int r = Core.generateNumber(0, 2);
            if (r == 0) {
                item.lore("§4§l✸S-TIER", "§a§l" + Core.generateNumber(1, 3) + " Lives", "", "§6Undead " + Core.generateNumber(1, 3), "§7Chance to apply weakness when hit");
            } else if (r == 1) {
                item.lore("§4§l✸S-TIER", "§a§l" + Core.generateNumber(1, 3) + " Lives", "", "§6Dying Rage 1", "§7When stunned, gain strength 2");
            } else if (r == 2) {
                item.lore("§4§l✸S-TIER", "§a§l" + Core.generateNumber(1, 3) + " Lives", "");
            }
            return item.asItemStack();
        } else if (armor == MCCArmor.HADES_BOOTS) {
            ItemBuilder item = new ItemBuilder(Material.DIAMOND_BOOTS, 1);
            item.displayName("§4§lHades Boots");
            item.unsafeEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
            item.unbreakable(true);
            int r = Core.generateNumber(0, 2);
            if (r == 0) {
                item.lore("§4§l✸S-TIER", "§a§l" + Core.generateNumber(1, 3) + " Lives", "", "§6Undead " + Core.generateNumber(1, 3), "§7Chance to apply weakness when hit");
            } else if (r == 1) {
                item.lore("§4§l✸S-TIER", "§a§l" + Core.generateNumber(1, 3) + " Lives", "", "§6Dying Rage 1", "§7When stunned, gain strength 2");
            } else if (r == 2) {
                item.lore("§4§l✸S-TIER", "§a§l" + Core.generateNumber(1, 3) + " Lives", "");
            }
            return item.asItemStack();
        } else if (armor == MCCArmor.GIANT_HELMET) {
            ItemBuilder item = new ItemBuilder(Material.DIAMOND_HELMET, 1);
            item.displayName("§2§lGiant Helmet");
            item.unsafeEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
            item.unbreakable(true);
            int r = Core.generateNumber(0, 2);
            if (r == 0) {
                item.lore("§4§l✸S-TIER", "§a§l" + Core.generateNumber(1, 3) + " Lives", "", "§6Unstoppable 1", "§7Immune to slows and stuns");
            } else if (r == 1) {
                item.lore("§4§l✸S-TIER", "§a§l" + Core.generateNumber(1, 3) + " Lives", "", "§6Juggernaut " + Core.generateNumber(1, 3), "§7Chance to gain resistance 1 for a short time");
            } else if (r == 2) {
                item.lore("§4§l✸S-TIER", "§a§l" + Core.generateNumber(1, 3) + " Lives", "");
            }
            return item.asItemStack();
        } else if (armor == MCCArmor.GIANT_CHESTPLATE) {
            ItemBuilder item = new ItemBuilder(Material.DIAMOND_CHESTPLATE, 1);
            item.displayName("§2§lGiant Chestplate");
            item.unsafeEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
            item.unbreakable(true);
            int r = Core.generateNumber(0, 2);
            if (r == 0) {
                item.lore("§4§l✸S-TIER", "§a§l" + Core.generateNumber(1, 3) + " Lives", "", "§6Unstoppable 1", "§7Immune to slows and stuns");
            } else if (r == 1) {
                item.lore("§4§l✸S-TIER", "§a§l" + Core.generateNumber(1, 3) + " Lives", "", "§6Juggernaut " + Core.generateNumber(1, 3), "§7Chance to gain resistance 1 for a short time");
            } else if (r == 2) {
                item.lore("§4§l✸S-TIER", "§a§l" + Core.generateNumber(1, 3) + " Lives", "");
            }
            return item.asItemStack();
        } else if (armor == MCCArmor.GIANT_LEGGINGS) {
            ItemBuilder item = new ItemBuilder(Material.DIAMOND_LEGGINGS, 1);
            item.displayName("§2§lGiant Leggings");
            item.unsafeEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
            item.unbreakable(true);
            int r = Core.generateNumber(0, 2);
            if (r == 0) {
                item.lore("§4§l✸S-TIER", "§a§l" + Core.generateNumber(1, 3) + " Lives", "", "§6Unstoppable 1", "§7Immune to slows and stuns");
            } else if (r == 1) {
                item.lore("§4§l✸S-TIER", "§a§l" + Core.generateNumber(1, 3) + " Lives", "", "§6Juggernaut " + Core.generateNumber(1, 3), "§7Chance to gain resistance 1 for a short time");
            } else if (r == 2) {
                item.lore("§4§l✸S-TIER", "§a§l" + Core.generateNumber(1, 3) + " Lives", "");
            }
            return item.asItemStack();
        } else if (armor == MCCArmor.GIANT_BOOTS) {
            ItemBuilder item = new ItemBuilder(Material.DIAMOND_BOOTS, 1);
            item.displayName("§2§lGiant Boots");
            item.unsafeEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
            item.unbreakable(true);
            int r = Core.generateNumber(0, 2);
            if (r == 0) {
                item.lore("§4§l✸S-TIER", "§a§l" + Core.generateNumber(1, 3) + " Lives", "", "§6Unstoppable 1", "§7Immune to slows and stuns");
            } else if (r == 1) {
                item.lore("§4§l✸S-TIER", "§a§l" + Core.generateNumber(1, 3) + " Lives", "", "§6Juggernaut " + Core.generateNumber(1, 3), "§7Chance to gain resistance for a short time");
            } else if (r == 2) {
                item.lore("§4§l✸S-TIER", "§a§l" + Core.generateNumber(1, 3) + " Lives", "");
            }
            return item.asItemStack();
        } else if (armor == MCCArmor.MOOSHROOM_HELMET) {
            ItemBuilder item = new ItemBuilder(Material.DIAMOND_HELMET, 1);
            item.displayName("§c§lMooshroom Helmet");
            item.unsafeEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
            item.unbreakable(true);
            int r = Core.generateNumber(0, 2);
            if (r == 0) {
                item.lore("§4§l✸S-TIER", "§a§l" + Core.generateNumber(1, 3) + " Lives", "", "§6Health Boost 1 ", "§7Gain permanent health boost 2");
            } else if (r == 1) {
                item.lore("§4§l✸S-TIER", "§a§l" + Core.generateNumber(1, 3) + " Lives", "", "§6Spores " + Core.generateNumber(1, 3), "§7Chance to apply random debuff to nearby enemies");
            } else if (r == 2) {
                item.lore("§4§l✸S-TIER", "§a§l" + Core.generateNumber(1, 3) + " Lives", "");
            }
            return item.asItemStack();
        } else if (armor == MCCArmor.MOOSHROOM_CHESTPLATE) {
            ItemBuilder item = new ItemBuilder(Material.DIAMOND_CHESTPLATE, 1);
            item.displayName("§c§lMooshroom Chestplate");
            item.unsafeEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
            item.unbreakable(true);
            int r = Core.generateNumber(0, 2);
            if (r == 0) {
                item.lore("§4§l✸S-TIER", "§a§l" + Core.generateNumber(1, 3) + " Lives", "", "§6Health Boost 1 ", "§7Gain permanent health boost 2");
            } else if (r == 1) {
                item.lore("§4§l✸S-TIER", "§a§l" + Core.generateNumber(1, 3) + " Lives", "", "§6Spores " + Core.generateNumber(1, 3), "§7Chance to apply random debuff to nearby enemies");
            } else if (r == 2) {
                item.lore("§4§l✸S-TIER", "§a§l" + Core.generateNumber(1, 3) + " Lives", "");
            }
            return item.asItemStack();
        } else if (armor == MCCArmor.MOOSHROOM_LEGGINGS) {
            ItemBuilder item = new ItemBuilder(Material.DIAMOND_LEGGINGS, 1);
            item.displayName("§c§lMooshroom Leggings");
            item.unsafeEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
            item.unbreakable(true);
            int r = Core.generateNumber(0, 2);
            if (r == 0) {
                item.lore("§4§l✸S-TIER", "§a§l" + Core.generateNumber(1, 3) + " Lives", "", "§6Health Boost 1 ", "§7Gain permanent health boost 2");
            } else if (r == 1) {
                item.lore("§4§l✸S-TIER", "§a§l" + Core.generateNumber(1, 3) + " Lives", "", "§6Spores " + Core.generateNumber(1, 3), "§7Chance to apply random debuff to nearby enemies");
            } else if (r == 2) {
                item.lore("§4§l✸S-TIER", "§a§l" + Core.generateNumber(1, 3) + " Lives", "");
            }
            return item.asItemStack();
        } else if (armor == MCCArmor.MOOSHROOM_BOOTS) {
            ItemBuilder item = new ItemBuilder(Material.DIAMOND_BOOTS, 1);
            item.displayName("§c§lMooshroom Boots");
            item.unsafeEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
            item.unbreakable(true);
            int r = Core.generateNumber(0, 2);
            if (r == 0) {
                item.lore("§4§l✸S-TIER", "§a§l" + Core.generateNumber(1, 3) + " Lives", "", "§6Health Boost 1 ", "§7Gain permanent health boost 2");
            } else if (r == 1) {
                item.lore("§4§l✸S-TIER", "§a§l" + Core.generateNumber(1, 3) + " Lives", "", "§6Spores " + Core.generateNumber(1, 3), "§7Chance to apply random debuff to nearby enemies");
            } else if (r == 2) {
                item.lore("§4§l✸S-TIER", "§a§l" + Core.generateNumber(1, 3) + " Lives", "");
            }
            return item.asItemStack();
        } else if (armor == MCCArmor.BLAZE_HELMET) {
            ItemBuilder item = new ItemBuilder(Material.DIAMOND_HELMET, 1);
            item.displayName("§6§lBlaze Helmet");
            item.unsafeEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
            item.unbreakable(true);
            int r = Core.generateNumber(0, 2);
            if (r == 0) {
                item.lore("§4§l✸S-TIER", "§a§l" + Core.generateNumber(1, 3) + " Lives", "", "§6Flame Rage 1", "§7Gain strength 1 when on fire");
            } else if (r == 1) {
                item.lore("§4§l✸S-TIER", "§a§l" + Core.generateNumber(1, 3) + " Lives", "", "§6Molten Armor " + Core.generateNumber(1, 3), "§7Chance to set enemy on fire when hit");
            } else if (r == 2) {
                item.lore("§4§l✸S-TIER", "§a§l" + Core.generateNumber(1, 3) + " Lives", "");
            }
            return item.asItemStack();
        } else if (armor == MCCArmor.BLAZE_CHESTPLATE) {
            ItemBuilder item = new ItemBuilder(Material.DIAMOND_CHESTPLATE, 1);
            item.displayName("§6§lBlaze Chestplate");
            item.unsafeEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
            item.unbreakable(true);
            int r = Core.generateNumber(0, 2);
            if (r == 0) {
                item.lore("§4§l✸S-TIER", "§a§l" + Core.generateNumber(1, 3) + " Lives", "", "§6Flame Rage 1", "§7Gain strength 1 when on fire");
            } else if (r == 1) {
                item.lore("§4§l✸S-TIER", "§a§l" + Core.generateNumber(1, 3) + " Lives", "", "§6Molten Armor " + Core.generateNumber(1, 3), "§7Chance to set enemy on fire when hit");
            } else if (r == 2) {
                item.lore("§4§l✸S-TIER", "§a§l" + Core.generateNumber(1, 3) + " Lives", "");
            }
            return item.asItemStack();
        } else if (armor == MCCArmor.BLAZE_LEGGINGS) {
            ItemBuilder item = new ItemBuilder(Material.DIAMOND_LEGGINGS, 1);
            item.displayName("§6§lBlaze Leggings");
            item.unsafeEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
            item.unbreakable(true);
            int r = Core.generateNumber(0, 2);
            if (r == 0) {
                item.lore("§4§l✸S-TIER", "§a§l" + Core.generateNumber(1, 3) + " Lives", "", "§6Flame Rage 1", "§7Gain strength 1 when on fire");
            } else if (r == 1) {
                item.lore("§4§l✸S-TIER", "§a§l" + Core.generateNumber(1, 3) + " Lives", "", "§6Molten Armor " + Core.generateNumber(1, 3), "§7Chance to set enemy on fire when hit");
            } else if (r == 2) {
                item.lore("§4§l✸S-TIER", "§a§l" + Core.generateNumber(1, 3) + " Lives", "");
            }
            return item.asItemStack();
        } else if (armor == MCCArmor.BLAZE_BOOTS) {
            ItemBuilder item = new ItemBuilder(Material.DIAMOND_BOOTS, 1);
            item.displayName("§6§lBlaze Boots");
            item.unsafeEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
            item.unbreakable(true);
            int r = Core.generateNumber(0, 2);
            if (r == 0) {
                item.lore("§4§l✸S-TIER", "§a§l" + Core.generateNumber(1, 3) + " Lives", "", "§6Flame Rage 1", "§7Gain strength 1 when on fire");
            } else if (r == 1) {
                item.lore("§4§l✸S-TIER", "§a§l" + Core.generateNumber(1, 3) + " Lives", "", "§6Molten Armor " + Core.generateNumber(1, 3), "§7Chance to set enemy on fire when hit");
            } else if (r == 2) {
                item.lore("§4§l✸S-TIER", "§a§l" + Core.generateNumber(1, 3) + " Lives", "");
            }
            return item.asItemStack();
        } else if (armor == MCCArmor.PIGMAN_HELMET) {
            ItemBuilder item = new ItemBuilder(Material.DIAMOND_HELMET, 1);
            item.displayName("§c§lPigman Helmet");
            item.unsafeEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
            item.unbreakable(true);
            int r = Core.generateNumber(0, 2);
            if (r == 0) {
                item.lore("§e§l✸A-TIER", "§a§l" + Core.generateNumber(1, 3) + " Lives", "", "§6Molten Shield 1", "§7Immune to fire");
            } else if (r == 1) {
                item.lore("§e§l✸A-TIER", "§a§l" + Core.generateNumber(1, 3) + " Lives", "", "§6Molten Armor " + Core.generateNumber(1, 3), "§7Chance to set enemy on fire when hit");
            } else if (r == 2) {
                item.lore("§e§l✸A-TIER", "§a§l" + Core.generateNumber(1, 3) + " Lives", "");
            }
            return item.asItemStack();
        } else if (armor == MCCArmor.PIGMAN_CHESTPLATE) {
            ItemBuilder item = new ItemBuilder(Material.DIAMOND_CHESTPLATE, 1);
            item.displayName("§c§lPigman Chestplate");
            item.unsafeEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
            item.unbreakable(true);
            int r = Core.generateNumber(0, 2);
            if (r == 0) {
                item.lore("§e§l✸A-TIER", "§a§l" + Core.generateNumber(1, 3) + " Lives", "", "§6Molten Shield 1", "§7Immune to fire");
            } else if (r == 1) {
                item.lore("§e§l✸A-TIER", "§a§l" + Core.generateNumber(1, 3) + " Lives", "", "§6Molten Armor " + Core.generateNumber(1, 3), "§7Chance to set enemy on fire when hit");
            } else if (r == 2) {
                item.lore("§e§l✸A-TIER", "§a§l" + Core.generateNumber(1, 3) + " Lives", "");
            }
            return item.asItemStack();
        } else if (armor == MCCArmor.PIGMAN_LEGGINGS) {
            ItemBuilder item = new ItemBuilder(Material.DIAMOND_LEGGINGS, 1);
            item.displayName("§c§lPigman Leggings");
            item.unsafeEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
            item.unbreakable(true);
            int r = Core.generateNumber(0, 2);
            if (r == 0) {
                item.lore("§e§l✸A-TIER", "§a§l" + Core.generateNumber(1, 3) + " Lives", "", "§6Molten Shield 1", "§7Immune to fire");
            } else if (r == 1) {
                item.lore("§e§l✸A-TIER", "§a§l" + Core.generateNumber(1, 3) + " Lives", "", "§6Molten Armor " + Core.generateNumber(1, 3), "§7Chance to set enemy on fire when hit");
            } else if (r == 2) {
                item.lore("§e§l✸A-TIER", "§a§l" + Core.generateNumber(1, 3) + " Lives", "");
            }
            return item.asItemStack();
        } else if (armor == MCCArmor.PIGMAN_BOOTS) {
            ItemBuilder item = new ItemBuilder(Material.DIAMOND_BOOTS, 1);
            item.displayName("§c§lPigman Boots");
            item.unsafeEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
            item.unbreakable(true);
            int r = Core.generateNumber(0, 2);
            if (r == 0) {
                item.lore("§e§l✸A-TIER", "§a§l" + Core.generateNumber(1, 3) + " Lives", "", "§6Molten Shield 1", "§7Immune to fire");
            } else if (r == 1) {
                item.lore("§e§l✸A-TIER", "§a§l" + Core.generateNumber(1, 3) + " Lives", "", "§6Molten Armor " + Core.generateNumber(1, 3), "§7Chance to set enemy on fire when hit");
            } else if (r == 2) {
                item.lore("§e§l✸A-TIER", "§a§l" + Core.generateNumber(1, 3) + " Lives", "");
            }
            return item.asItemStack();
        } else if (armor == MCCArmor.POLAR_BEAR_HELMET) {
            ItemBuilder item = new ItemBuilder(Material.DIAMOND_HELMET, 1);
            item.displayName("§f§lPolar Bear Helmet");
            item.unsafeEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
            item.unbreakable(true);
            int r = Core.generateNumber(0, 2);
            if (r == 0) {
                item.lore("§e§l✸A-TIER", "§a§l" + Core.generateNumber(1, 3) + " Lives", "", "§6Sticky Armor " + Core.generateNumber(1, 3), "§7Chance to inflict slowness 1 when hit");
            } else if (r == 1) {
                item.lore("§e§l✸A-TIER", "§a§l" + Core.generateNumber(1, 3) + " Lives", "", "§6Berserk " + Core.generateNumber(1, 3), "§7Gain strength 1 when low");
            } else if (r == 2) {
                item.lore("§e§l✸A-TIER", "§a§l" + Core.generateNumber(1, 3) + " Lives", "");
            }
            return item.asItemStack();
        } else if (armor == MCCArmor.POLAR_BEAR_CHESTPLATE) {
            ItemBuilder item = new ItemBuilder(Material.DIAMOND_CHESTPLATE, 1);
            item.displayName("§f§lPolar Bear Chestplate");
            item.unsafeEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
            item.unbreakable(true);
            int r = Core.generateNumber(0, 2);
            if (r == 0) {
                item.lore("§e§l✸A-TIER", "§a§l" + Core.generateNumber(1, 3) + " Lives", "", "§6Sticky Armor " + Core.generateNumber(1, 3), "§7Chance to inflict slowness 1 when hit");
            } else if (r == 1) {
                item.lore("§e§l✸A-TIER", "§a§l" + Core.generateNumber(1, 3) + " Lives", "", "§6Berserk " + Core.generateNumber(1, 3), "§7Gain strength 1 when low");
            } else if (r == 2) {
                item.lore("§e§l✸A-TIER", "§a§l" + Core.generateNumber(1, 3) + " Lives", "");
            }
            return item.asItemStack();
        } else if (armor == MCCArmor.POLAR_BEAR_LEGGINGS) {
            ItemBuilder item = new ItemBuilder(Material.DIAMOND_LEGGINGS, 1);
            item.displayName("§f§lPolar Bear Leggings");
            item.unsafeEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
            item.unbreakable(true);
            int r = Core.generateNumber(0, 2);
            if (r == 0) {
                item.lore("§e§l✸A-TIER", "§a§l" + Core.generateNumber(1, 3) + " Lives", "", "§6Sticky Armor " + Core.generateNumber(1, 3), "§7Chance to inflict slowness 1 when hit");
            } else if (r == 1) {
                item.lore("§e§l✸A-TIER", "§a§l" + Core.generateNumber(1, 3) + " Lives", "", "§6Berserk " + Core.generateNumber(1, 3), "§7Gain strength 1 when low");
            } else if (r == 2) {
                item.lore("§e§l✸A-TIER", "§a§l" + Core.generateNumber(1, 3) + " Lives", "");
            }
            return item.asItemStack();
        } else if (armor == MCCArmor.POLAR_BEAR_BOOTS) {
            ItemBuilder item = new ItemBuilder(Material.DIAMOND_BOOTS, 1);
            item.displayName("§f§lPolar Bear Boots");
            item.unsafeEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
            item.unbreakable(true);
            int r = Core.generateNumber(0, 2);
            if (r == 0) {
                item.lore("§e§l✸A-TIER", "§a§l" + Core.generateNumber(1, 3) + " Lives", "", "§6Frost Walker 1", "§7Gain permanent frost walker 1");
                item.unsafeEnchant(Enchantment.FROST_WALKER, 1);
            } else if (r == 1) {
                item.lore("§e§l✸A-TIER", "§a§l" + Core.generateNumber(1, 3) + " Lives", "", "§6Berserk " + Core.generateNumber(1, 3), "§7Gain strength 1 when low");
            } else if (r == 2) {
                item.lore("§e§l✸A-TIER", "§a§l" + Core.generateNumber(1, 3) + " Lives", "");
            }
            return item.asItemStack();
        } else if (armor == MCCArmor.ENDERMAN_HELMET) {
            ItemBuilder item = new ItemBuilder(Material.DIAMOND_HELMET, 1);
            item.displayName("§5§lEnderman Helmet");
            item.unsafeEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
            item.unbreakable(true);
            int r = Core.generateNumber(0, 2);
            if (r == 0) {
                item.lore("§e§l✸A-TIER", "§a§l" + Core.generateNumber(1, 3) + " Lives", "", "§6Blinding Light " + Core.generateNumber(1, 3), "§7Apply blindness to nearby enemies when low");
            } else if (r == 1) {
                item.lore("§e§l✸A-TIER", "§a§l" + Core.generateNumber(1, 3) + " Lives", "", "§6Toxic Shield " + Core.generateNumber(1, 3), "§7Heal from poison and wither effects");
            } else if (r == 2) {
                item.lore("§e§l✸A-TIER", "§a§l" + Core.generateNumber(1, 3) + " Lives", "");
            }
            return item.asItemStack();
        } else if (armor == MCCArmor.ENDERMAN_CHESTPLATE) {
            ItemBuilder item = new ItemBuilder(Material.DIAMOND_CHESTPLATE, 1);
            item.displayName("§5§lEnderman Chestplate");
            item.unsafeEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
            item.unbreakable(true);
            int r = Core.generateNumber(0, 2);
            if (r == 0) {
                item.lore("§e§l✸A-TIER", "§a§l" + Core.generateNumber(1, 3) + " Lives", "", "§6Blinding Light " + Core.generateNumber(1, 3), "§7Apply blindness to nearby enemies when low");
            } else if (r == 1) {
                item.lore("§e§l✸A-TIER", "§a§l" + Core.generateNumber(1, 3) + " Lives", "", "§6Toxic Shield " + Core.generateNumber(1, 3), "§7Heal from poison and wither effects");
            } else if (r == 2) {
                item.lore("§e§l✸A-TIER", "§a§l" + Core.generateNumber(1, 3) + " Lives", "");
            }
            return item.asItemStack();
        } else if (armor == MCCArmor.ENDERMAN_LEGGINGS) {
            ItemBuilder item = new ItemBuilder(Material.DIAMOND_LEGGINGS, 1);
            item.displayName("§5§lEnderman Leggings");
            item.unsafeEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
            item.unbreakable(true);
            int r = Core.generateNumber(0, 2);
            if (r == 0) {
                item.lore("§e§l✸A-TIER", "§a§l" + Core.generateNumber(1, 3) + " Lives", "", "§6Blinding Light " + Core.generateNumber(1, 3), "§7Apply blindness to nearby enemies when low");
            } else if (r == 1) {
                item.lore("§e§l✸A-TIER", "§a§l" + Core.generateNumber(1, 3) + " Lives", "", "§6Toxic Shield " + Core.generateNumber(1, 3), "§7Heal from poison and wither effects");
            } else if (r == 2) {
                item.lore("§e§l✸A-TIER", "§a§l" + Core.generateNumber(1, 3) + " Lives", "");
            }
            return item.asItemStack();
        } else if (armor == MCCArmor.ENDERMAN_BOOTS) {
            ItemBuilder item = new ItemBuilder(Material.DIAMOND_BOOTS, 1);
            item.displayName("§5§lEnderman Boots");
            item.unsafeEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
            item.unbreakable(true);
            int r = Core.generateNumber(0, 2);
            if (r == 0) {
                item.lore("§e§l✸A-TIER", "§a§l" + Core.generateNumber(1, 3) + " Lives", "", "§6Blinding Light " + Core.generateNumber(1, 3), "§7Apply blindness to nearby enemies when low");
            } else if (r == 1) {
                item.lore("§e§l✸A-TIER", "§a§l" + Core.generateNumber(1, 3) + " Lives", "", "§6Toxic Shield " + Core.generateNumber(1, 3), "§7Heal from poison and wither effects");
            } else if (r == 2) {
                item.lore("§e§l✸A-TIER", "§a§l" + Core.generateNumber(1, 3) + " Lives", "");
            }
            return item.asItemStack();
        } else if (armor == MCCArmor.BAT_HELMET) {
            ItemBuilder item = new ItemBuilder(Material.DIAMOND_HELMET, 1);
            item.displayName("§8§lBat Helmet");
            item.unsafeEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
            item.unbreakable(true);
            int r = Core.generateNumber(0, 2);
            if (r == 0) {
                item.lore("§e§l✸A-TIER", "§a§l" + Core.generateNumber(1, 3) + " Lives", "", "§6Dodge " + Core.generateNumber(1, 3), "§7Chance to dodge attacks");
            } else if (r == 1) {
                item.lore("§e§l✸A-TIER", "§a§l" + Core.generateNumber(1, 3) + " Lives", "", "§6Keen Eye 1 ", "§7Immune to blindness");
            } else if (r == 2) {
                item.lore("§e§l✸A-TIER", "§a§l" + Core.generateNumber(1, 3) + " Lives", "");
            }
            return item.asItemStack();
        } else if (armor == MCCArmor.BAT_CHESTPLATE) {
            ItemBuilder item = new ItemBuilder(Material.DIAMOND_CHESTPLATE, 1);
            item.displayName("§8§lBat Chestplate");
            item.unsafeEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
            item.unbreakable(true);
            int r = Core.generateNumber(0, 2);
            if (r == 0) {
                item.lore("§e§l✸A-TIER", "§a§l" + Core.generateNumber(1, 3) + " Lives", "", "§6Dodge " + Core.generateNumber(1, 3), "§7Chance to dodge attacks");
            } else if (r == 1) {
                item.lore("§e§l✸A-TIER", "§a§l" + Core.generateNumber(1, 3) + " Lives", "", "§6Keen Eye 1 ", "§7Immune to blindness");
            } else if (r == 2) {
                item.lore("§e§l✸A-TIER", "§a§l" + Core.generateNumber(1, 3) + " Lives", "");
            }
            return item.asItemStack();
        } else if (armor == MCCArmor.BAT_LEGGINGS) {
            ItemBuilder item = new ItemBuilder(Material.DIAMOND_LEGGINGS, 1);
            item.displayName("§8§lBat Leggings");
            item.unsafeEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
            item.unbreakable(true);
            int r = Core.generateNumber(0, 2);
            if (r == 0) {
                item.lore("§e§l✸A-TIER", "§a§l" + Core.generateNumber(1, 3) + " Lives", "", "§6Dodge " + Core.generateNumber(1, 3), "§7Chance to dodge attacks");
            } else if (r == 1) {
                item.lore("§e§l✸A-TIER", "§a§l" + Core.generateNumber(1, 3) + " Lives", "", "§6Keen Eye 1 ", "§7Immune to blindness");
            } else if (r == 2) {
                item.lore("§e§l✸A-TIER", "§a§l" + Core.generateNumber(1, 3) + " Lives", "");
            }
            return item.asItemStack();
        } else if (armor == MCCArmor.BAT_BOOTS) {
            ItemBuilder item = new ItemBuilder(Material.DIAMOND_BOOTS, 1);
            item.displayName("§8§lBat Boots");
            item.unsafeEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
            item.unbreakable(true);
            int r = Core.generateNumber(0, 2);
            if (r == 0) {
                item.lore("§e§l✸A-TIER", "§a§l" + Core.generateNumber(1, 3) + " Lives", "", "§6Dodge " + Core.generateNumber(1, 3), "§7Chance to dodge attacks");
            } else if (r == 1) {
                item.lore("§e§l✸A-TIER", "§a§l" + Core.generateNumber(1, 3) + " Lives", "", "§6Keen Eye 1 ", "§7Immune to blindness");
            } else if (r == 2) {
                item.lore("§e§l✸A-TIER", "§a§l" + Core.generateNumber(1, 3) + " Lives", "");
            }
            return item.asItemStack();
        } else if (armor == MCCArmor.SLIME_HELMET) {
            ItemBuilder item = new ItemBuilder(Material.DIAMOND_HELMET, 1);
            item.displayName("§a§lSlime Helmet");
            item.unsafeEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
            item.unbreakable(true);
            int r = Core.generateNumber(0, 2);
            if (r == 0) {
                item.lore("§e§l✸A-TIER", "§a§l" + Core.generateNumber(1, 3) + " Lives", "", "§6Sticky Armor " + Core.generateNumber(1, 3), "§7Chance to give slowness 1 when hit");
            } else if (r == 1) {
                item.lore("§e§l✸A-TIER", "§a§l" + Core.generateNumber(1, 3) + " Lives", "", "§6Reflect " + Core.generateNumber(1, 3), "§7Chance to reflect damage when hit");
            } else if (r == 2) {
                item.lore("§e§l✸A-TIER", "§a§l" + Core.generateNumber(1, 3) + " Lives", "");
            }
            return item.asItemStack();
        } else if (armor == MCCArmor.SLIME_CHESTPLATE) {
            ItemBuilder item = new ItemBuilder(Material.DIAMOND_CHESTPLATE, 1);
            item.displayName("§a§lSlime Chestplate");
            item.unsafeEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
            item.unbreakable(true);
            int r = Core.generateNumber(0, 2);
            if (r == 0) {
                item.lore("§e§l✸A-TIER", "§a§l" + Core.generateNumber(1, 3) + " Lives", "", "§6Sticky Armor " + Core.generateNumber(1, 3), "§7Chance to give slowness 1 when hit");
            } else if (r == 1) {
                item.lore("§e§l✸A-TIER", "§a§l" + Core.generateNumber(1, 3) + " Lives", "", "§6Reflect " + Core.generateNumber(1, 3), "§7Chance to reflect damage when hit");
            } else if (r == 2) {
                item.lore("§e§l✸A-TIER", "§a§l" + Core.generateNumber(1, 3) + " Lives", "");
            }
            return item.asItemStack();
        } else if (armor == MCCArmor.SLIME_LEGGINGS) {
            ItemBuilder item = new ItemBuilder(Material.DIAMOND_LEGGINGS, 1);
            item.displayName("§a§lSlime Leggings");
            item.unsafeEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
            item.unbreakable(true);
            int r = Core.generateNumber(0, 2);
            if (r == 0) {
                item.lore("§e§l✸A-TIER", "§a§l" + Core.generateNumber(1, 3) + " Lives", "", "§6Sticky Armor " + Core.generateNumber(1, 3), "§7Chance to give slowness 1 when hit");
            } else if (r == 1) {
                item.lore("§e§l✸A-TIER", "§a§l" + Core.generateNumber(1, 3) + " Lives", "", "§6Reflect " + Core.generateNumber(1, 3), "§7Chance to reflect damage when hit");
            } else if (r == 2) {
                item.lore("§e§l✸A-TIER", "§a§l" + Core.generateNumber(1, 3) + " Lives", "");
            }
            return item.asItemStack();
        } else if (armor == MCCArmor.SLIME_BOOTS) {
            ItemBuilder item = new ItemBuilder(Material.DIAMOND_BOOTS, 1);
            item.displayName("§a§lSlime Boots");
            item.unsafeEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
            item.unbreakable(true);
            int r = Core.generateNumber(0, 2);
            if (r == 0) {
                item.lore("§e§l✸A-TIER", "§a§l" + Core.generateNumber(1, 3) + " Lives", "", "§6Sticky Armor " + Core.generateNumber(1, 3), "§7Chance to give slowness 1 when hit");
            } else if (r == 1) {
                item.lore("§e§l✸A-TIER", "§a§l" + Core.generateNumber(1, 3) + " Lives", "", "§6Reflect " + Core.generateNumber(1, 3), "§7Chance to reflect damage when hit");
            } else if (r == 2) {
                item.lore("§e§l✸A-TIER", "§a§l" + Core.generateNumber(1, 3) + " Lives", "");
            }
            return item.asItemStack();
        } else if (armor == MCCArmor.SQUID_HELMET) {
            ItemBuilder item = new ItemBuilder(Material.DIAMOND_HELMET, 1);
            item.displayName("§3§lSquid Helmet");
            item.unsafeEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
            item.unbreakable(true);
            int r = Core.generateNumber(0, 2);
            if (r == 0) {
                item.lore("§e§l✸A-TIER", "§a§l" + Core.generateNumber(1, 3) + " Lives", "", "§6Molten Shield 1", "§7Immune to fire");
            } else if (r == 1) {
                item.lore("§e§l✸A-TIER", "§a§l" + Core.generateNumber(1, 3) + " Lives", "", "§6Keen Eye 1 ", "§7Immune to blindness");
            } else if (r == 2) {
                item.lore("§e§l✸A-TIER", "§a§l" + Core.generateNumber(1, 3) + " Lives", "");
            }
            return item.asItemStack();
        } else if (armor == MCCArmor.SQUID_CHESTPLATE) {
            ItemBuilder item = new ItemBuilder(Material.DIAMOND_CHESTPLATE, 1);
            item.displayName("§3§lSquid Chestplate");
            item.unsafeEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
            item.unbreakable(true);
            int r = Core.generateNumber(0, 2);
            if (r == 0) {
                item.lore("§e§l✸A-TIER", "§a§l" + Core.generateNumber(1, 3) + " Lives", "", "§6Molten Shield 1", "§7Immune to fire");
            } else if (r == 1) {
                item.lore("§e§l✸A-TIER", "§a§l" + Core.generateNumber(1, 3) + " Lives", "", "§6Keen Eye 1 ", "§7Immune to blindness");
            } else if (r == 2) {
                item.lore("§e§l✸A-TIER", "§a§l" + Core.generateNumber(1, 3) + " Lives", "");
            }
            return item.asItemStack();
        } else if (armor == MCCArmor.SQUID_LEGGINGS) {
            ItemBuilder item = new ItemBuilder(Material.DIAMOND_LEGGINGS, 1);
            item.displayName("§3§lSquid Leggings");
            item.unsafeEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
            item.unbreakable(true);
            int r = Core.generateNumber(0, 2);
            if (r == 0) {
                item.lore("§e§l✸A-TIER", "§a§l" + Core.generateNumber(1, 3) + " Lives", "", "§6Molten Shield 1", "§7Immune to fire");
            } else if (r == 1) {
                item.lore("§e§l✸A-TIER", "§a§l" + Core.generateNumber(1, 3) + " Lives", "", "§6Keen Eye 1 ", "§7Immune to blindness");
            } else if (r == 2) {
                item.lore("§e§l✸A-TIER", "§a§l" + Core.generateNumber(1, 3) + " Lives", "");
            }
            return item.asItemStack();
        } else if (armor == MCCArmor.SQUID_BOOTS) {
            ItemBuilder item = new ItemBuilder(Material.DIAMOND_BOOTS, 1);
            item.displayName("§3§lSquid Boots");
            item.unsafeEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
            item.unbreakable(true);
            int r = Core.generateNumber(0, 2);
            if (r == 0) {
                item.lore("§e§l✸A-TIER", "§a§l" + Core.generateNumber(1, 3) + " Lives", "", "§6Keen Eye 1 ", "§7Immune to blindness");
            } else if (r == 1) {
                int depth = Core.generateNumber(1, 3);
                item.unsafeEnchant(Enchantment.DEPTH_STRIDER, depth);
                item.lore("§e§l✸A-TIER", "§a§l" + Core.generateNumber(1, 3) + " Lives", "", "§6Depth Strider " + depth, "§7Gain speed when in water");
            } else if (r == 2) {
                item.lore("§e§l✸A-TIER", "§a§l" + Core.generateNumber(1, 3) + " Lives", "");
            }
            return item.asItemStack();
        } else if (armor == MCCArmor.GOLEM_HELMET) {
            ItemBuilder item = new ItemBuilder(Material.DIAMOND_HELMET, 1);
            item.displayName("§8§lGolem Helmet");
            item.unsafeEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
            item.unbreakable(true);
            int r = Core.generateNumber(0, 2);
            if (r == 0) {
                item.lore("§e§l✸A-TIER", "§a§l" + Core.generateNumber(1, 3) + " Lives", "", "§6Seismic Escape " + Core.generateNumber(1, 3), "§7Knockback and slow nearby enemies when low");
            } else if (r == 1) {
                item.lore("§e§l✸A-TIER", "§a§l" + Core.generateNumber(1, 3) + " Lives", "", "§6Reflect " + Core.generateNumber(1, 3), "§7Chance to reflect damage back when hit");
            } else if (r == 2) {
                item.lore("§e§l✸A-TIER", "§a§l" + Core.generateNumber(1, 3) + " Lives", "");
            }
            return item.asItemStack();
        } else if (armor == MCCArmor.GOLEM_CHESTPLATE) {
            ItemBuilder item = new ItemBuilder(Material.DIAMOND_CHESTPLATE, 1);
            item.displayName("§8§lGolem Chestplate");
            item.unsafeEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
            item.unbreakable(true);
            int r = Core.generateNumber(0, 2);
            if (r == 0) {
                item.lore("§e§l✸A-TIER", "§a§l" + Core.generateNumber(1, 3) + " Lives", "", "§6Seismic Escape " + Core.generateNumber(1, 3), "§7Knockback and slow nearby enemies when low");
            } else if (r == 1) {
                item.lore("§e§l✸A-TIER", "§a§l" + Core.generateNumber(1, 3) + " Lives", "", "§6Reflect " + Core.generateNumber(1, 3), "§7Chance to reflect damage back when hit");
            } else if (r == 2) {
                item.lore("§e§l✸A-TIER", "§a§l" + Core.generateNumber(1, 3) + " Lives", "");
            }
            return item.asItemStack();
        } else if (armor == MCCArmor.GOLEM_LEGGINGS) {
            ItemBuilder item = new ItemBuilder(Material.DIAMOND_LEGGINGS, 1);
            item.displayName("§8§lGolem Leggings");
            item.unsafeEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
            item.unbreakable(true);
            int r = Core.generateNumber(0, 2);
            if (r == 0) {
                item.lore("§e§l✸A-TIER", "§a§l" + Core.generateNumber(1, 3) + " Lives", "", "§6Seismic Escape " + Core.generateNumber(1, 3), "§7Knockback and slow nearby enemies when low");
            } else if (r == 1) {
                item.lore("§e§l✸A-TIER", "§a§l" + Core.generateNumber(1, 3) + " Lives", "", "§6Reflect " + Core.generateNumber(1, 3), "§7Chance to reflect damage back when hit");
            } else if (r == 2) {
                item.lore("§e§l✸A-TIER", "§a§l" + Core.generateNumber(1, 3) + " Lives", "");
            }
            return item.asItemStack();
        } else if (armor == MCCArmor.GOLEM_BOOTS) {
            ItemBuilder item = new ItemBuilder(Material.DIAMOND_BOOTS, 1);
            item.displayName("§8§lGolem Boots");
            item.unsafeEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
            item.unbreakable(true);
            int r = Core.generateNumber(0, 2);
            if (r == 0) {
                item.lore("§e§l✸A-TIER", "§a§l" + Core.generateNumber(1, 3) + " Lives", "", "§6Seismic Escape " + Core.generateNumber(1, 3), "§7Knockback and slow nearby enemies when low");
            } else if (r == 1) {
                item.lore("§e§l✸A-TIER", "§a§l" + Core.generateNumber(1, 3) + " Lives", "", "§6Reflect " + Core.generateNumber(1, 3), "§7Chance to reflect damage back when hit");
            } else if (r == 2) {
                item.lore("§e§l✸A-TIER", "§a§l" + Core.generateNumber(1, 3) + " Lives", "");
            }
            return item.asItemStack();
        } else if (armor == MCCArmor.SKELETON_HELMET) {
            ItemBuilder item = new ItemBuilder(Material.DIAMOND_HELMET, 1);
            item.displayName("§f§lSkeleton Helmet");
            item.unsafeEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
            item.unbreakable(true);
            int r = Core.generateNumber(0, 2);
            if (r == 0) {
                item.lore("§a§l✸B-TIER", "§a§l3 Lives", "", "§6Fragile Escape " + Core.generateNumber(1, 3), "§7Apply weakness to nearby enemies when low");
            } else if (r == 1) {
                item.lore("§a§l✸B-TIER", "§a§l3 Lives", "", "§6Kite " + Core.generateNumber(1, 3), "§7Chance to gain jump boost when hit");
            } else if (r == 2) {
                item.lore("§a§l✸B-TIER", "§a§l3 Lives", "");
            }
            return item.asItemStack();
        } else if (armor == MCCArmor.SKELETON_CHESTPLATE) {
            ItemBuilder item = new ItemBuilder(Material.DIAMOND_CHESTPLATE, 1);
            item.displayName("§f§lSkeleton Chestplate");
            item.unsafeEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
            item.unbreakable(true);
            int r = Core.generateNumber(0, 2);
            if (r == 0) {
                item.lore("§a§l✸B-TIER", "§a§l3 Lives", "", "§6Fragile Escape " + Core.generateNumber(1, 3), "§7Apply weakness to nearby enemies when low");
            } else if (r == 1) {
                item.lore("§a§l✸B-TIER", "§a§l3 Lives", "", "§6Kite " + Core.generateNumber(1, 3), "§7Chance to gain jump boost when hit");
            } else if (r == 2) {
                item.lore("§a§l✸B-TIER", "§a§l3 Lives", "");
            }
            return item.asItemStack();
        } else if (armor == MCCArmor.SKELETON_LEGGINGS) {
            ItemBuilder item = new ItemBuilder(Material.DIAMOND_LEGGINGS, 1);
            item.displayName("§f§lSkeleton Leggings");
            item.unsafeEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
            item.unbreakable(true);
            int r = Core.generateNumber(0, 2);
            if (r == 0) {
                item.lore("§a§l✸B-TIER", "§a§l3 Lives", "", "§6Fragile Escape " + Core.generateNumber(1, 3), "§7Apply weakness to nearby enemies when low");
            } else if (r == 1) {
                item.lore("§a§l✸B-TIER", "§a§l3 Lives", "", "§6Kite " + Core.generateNumber(1, 3), "§7Chance to gain jump boost when hit");
            } else if (r == 2) {
                item.lore("§a§l✸B-TIER", "§a§l3 Lives", "");
            }
            return item.asItemStack();
        } else if (armor == MCCArmor.SKELETON_BOOTS) {
            ItemBuilder item = new ItemBuilder(Material.DIAMOND_BOOTS, 1);
            item.displayName("§f§lSkeleton Boots");
            item.unsafeEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
            item.unbreakable(true);
            int r = Core.generateNumber(0, 2);
            if (r == 0) {
                item.lore("§a§l✸B-TIER", "§a§l3 Lives", "", "§6Fragile Escape " + Core.generateNumber(1, 3), "§7Apply weakness to nearby enemies when low");
            } else if (r == 1) {
                item.lore("§a§l✸B-TIER", "§a§l3 Lives", "", "§6Kite " + Core.generateNumber(1, 3), "§7Chance to gain jump boost when hit");
            } else if (r == 2) {
                item.lore("§a§l✸B-TIER", "§a§l3 Lives", "");
            }
            return item.asItemStack();
        } else if (armor == MCCArmor.PIG_HELMET) {
            ItemBuilder item = new ItemBuilder(Material.DIAMOND_HELMET, 1);
            item.displayName("§d§lPig Helmet");
            item.unsafeEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
            item.unbreakable(true);
            int r = Core.generateNumber(0, 2);
            if (r == 0) {
                item.lore("§a§l✸B-TIER", "§a§l3 Lives", "", "§6Miracle " + Core.generateNumber(1, 3), "§7Gain absorption and regeneration when low");
            } else if (r == 1) {
                item.lore("§a§l✸B-TIER", "§a§l3 Lives", "", "§6Scatter " + Core.generateNumber(1, 3), "§7Chance to gain speed 2 when hit");
            } else if (r == 2) {
                item.lore("§a§l✸B-TIER", "§a§l3 Lives", "");
            }
            return item.asItemStack();
        } else if (armor == MCCArmor.PIG_CHESTPLATE) {
            ItemBuilder item = new ItemBuilder(Material.DIAMOND_CHESTPLATE, 1);
            item.displayName("§d§lPig Chestplate");
            item.unsafeEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
            item.unbreakable(true);
            int r = Core.generateNumber(0, 2);
            if (r == 0) {
                item.lore("§a§l✸B-TIER", "§a§l3 Lives", "", "§6Miracle " + Core.generateNumber(1, 3), "§7Gain absorption and regeneration when low");
            } else if (r == 1) {
                item.lore("§a§l✸B-TIER", "§a§l3 Lives", "", "§6Scatter " + Core.generateNumber(1, 3), "§7Chance to gain speed 2 when hit");
            } else if (r == 2) {
                item.lore("§a§l✸B-TIER", "§a§l3 Lives", "");
            }
            return item.asItemStack();
        } else if (armor == MCCArmor.PIG_LEGGINGS) {
            ItemBuilder item = new ItemBuilder(Material.DIAMOND_LEGGINGS, 1);
            item.displayName("§d§lPig Leggings");
            item.unsafeEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
            item.unbreakable(true);
            int r = Core.generateNumber(0, 2);
            if (r == 0) {
                item.lore("§a§l✸B-TIER", "§a§l3 Lives", "", "§6Miracle " + Core.generateNumber(1, 3), "§7Gain absorption and regeneration when low");
            } else if (r == 1) {
                item.lore("§a§l✸B-TIER", "§a§l3 Lives", "", "§6Scatter " + Core.generateNumber(1, 3), "§7Chance to gain speed 2 when hit");
            } else if (r == 2) {
                item.lore("§a§l✸B-TIER", "§a§l3 Lives", "");
            }
            return item.asItemStack();
        } else if (armor == MCCArmor.PIG_BOOTS) {
            ItemBuilder item = new ItemBuilder(Material.DIAMOND_BOOTS, 1);
            item.displayName("§d§lPig Boots");
            item.unsafeEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
            item.unbreakable(true);
            int r = Core.generateNumber(0, 2);
            if (r == 0) {
                item.lore("§a§l✸B-TIER", "§a§l3 Lives", "", "§6Miracle " + Core.generateNumber(1, 3), "§7Gain absorption and regeneration when low");
            } else if (r == 1) {
                item.lore("§a§l✸B-TIER", "§a§l3 Lives", "", "§6Scatter " + Core.generateNumber(1, 3), "§7Chance to gain speed 2 when hit");
            } else if (r == 2) {
                item.lore("§a§l✸B-TIER", "§a§l3 Lives", "");
            }
            return item.asItemStack();
        } else if (armor == MCCArmor.ZOMBIE_HELMET) {
            ItemBuilder item = new ItemBuilder(Material.DIAMOND_HELMET, 1);
            item.displayName("§2§lZombie Helmet");
            item.unsafeEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
            item.unbreakable(true);
            int r = Core.generateNumber(0, 2);
            if (r == 0) {
                item.lore("§a§l✸B-TIER", "§a§l3 Lives", "", "§6Starvation  1", "§7Lose no hunger");
            } else if (r == 1) {
                item.lore("§a§l✸B-TIER", "§a§l3 Lives", "", "§6Undead " + Core.generateNumber(1, 3), "§7Chance to apply weakness when hit");
            } else if (r == 2) {
                item.lore("§a§l✸B-TIER", "§a§l3 Lives", "");
            }
            return item.asItemStack();
        } else if (armor == MCCArmor.ZOMBIE_CHESTPLATE) {
            ItemBuilder item = new ItemBuilder(Material.DIAMOND_CHESTPLATE, 1);
            item.displayName("§2§lZombie Chestplate");
            item.unsafeEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
            item.unbreakable(true);
            int r = Core.generateNumber(0, 2);
            if (r == 0) {
                item.lore("§a§l✸B-TIER", "§a§l3 Lives", "", "§6Starvation  1", "§7Lose no hunger");
            } else if (r == 1) {
                item.lore("§a§l✸B-TIER", "§a§l3 Lives", "", "§6Undead " + Core.generateNumber(1, 3), "§7Chance to apply weakness when hit");
            } else if (r == 2) {
                item.lore("§a§l✸B-TIER", "§a§l3 Lives", "");
            }
            return item.asItemStack();
        } else if (armor == MCCArmor.ZOMBIE_LEGGINGS) {
            ItemBuilder item = new ItemBuilder(Material.DIAMOND_LEGGINGS, 1);
            item.displayName("§2§lZombie Leggings");
            item.unsafeEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
            item.unbreakable(true);
            int r = Core.generateNumber(0, 2);
            if (r == 0) {
                item.lore("§a§l✸B-TIER", "§a§l3 Lives", "", "§6Starvation  1", "§7Lose no hunger");
            } else if (r == 1) {
                item.lore("§a§l✸B-TIER", "§a§l3 Lives", "", "§6Undead " + Core.generateNumber(1, 3), "§7Chance to apply weakness when hit");
            } else if (r == 2) {
                item.lore("§a§l✸B-TIER", "§a§l3 Lives", "");
            }
            return item.asItemStack();
        } else if (armor == MCCArmor.ZOMBIE_BOOTS) {
            ItemBuilder item = new ItemBuilder(Material.DIAMOND_BOOTS, 1);
            item.displayName("§2§lZombie Boots");
            item.unsafeEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
            item.unbreakable(true);
            int r = Core.generateNumber(0, 2);
            if (r == 0) {
                item.lore("§a§l✸B-TIER", "§a§l3 Lives", "", "§6Starvation  1", "§7Lose no hunger");
            } else if (r == 1) {
                item.lore("§a§l✸B-TIER", "§a§l3 Lives", "", "§6Undead " + Core.generateNumber(1, 3), "§7Chance to apply weakness when hit");
            } else if (r == 2) {
                item.lore("§a§l✸B-TIER", "§a§l3 Lives", "");
            }
            return item.asItemStack();
        } else if (armor == MCCArmor.SILVERFISH_HELMET) {
            ItemBuilder item = new ItemBuilder(Material.DIAMOND_HELMET, 1);
            item.displayName("§7§lSilverfish Helmet");
            item.unsafeEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
            item.unbreakable(true);
            int r = Core.generateNumber(0, 2);
            if (r == 0) {
                item.lore("§a§l✸B-TIER", "§a§l3 Lives", "", "§6Light Feet 1", "§7Take no fall damage");
            } else if (r == 1) {
                item.lore("§a§l✸B-TIER", "§a§l3 Lives", "", "§6Dodge " + Core.generateNumber(1, 3), "§7Chance to dodge attacks");
            } else if (r == 2) {
                item.lore("§a§l✸B-TIER", "§a§l3 Lives", "");
            }
            return item.asItemStack();
        } else if (armor == MCCArmor.SILVERFISH_CHESTPLATE) {
            ItemBuilder item = new ItemBuilder(Material.DIAMOND_CHESTPLATE, 1);
            item.displayName("§7§lSilverfish Chestplate");
            item.unsafeEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
            item.unbreakable(true);
            int r = Core.generateNumber(0, 2);
            if (r == 0) {
                item.lore("§a§l✸B-TIER", "§a§l3 Lives", "", "§6Light Feet 1", "§7Take no fall damage");
            } else if (r == 1) {
                item.lore("§a§l✸B-TIER", "§a§l3 Lives", "", "§6Dodge " + Core.generateNumber(1, 3), "§7Chance to dodge attacks");
            } else if (r == 2) {
                item.lore("§a§l✸B-TIER", "§a§l3 Lives", "");
            }
            return item.asItemStack();
        } else if (armor == MCCArmor.SILVERFISH_LEGGINGS) {
            ItemBuilder item = new ItemBuilder(Material.DIAMOND_LEGGINGS, 1);
            item.displayName("§7§lSilverfish Leggings");
            item.unsafeEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
            item.unbreakable(true);
            int r = Core.generateNumber(0, 2);
            if (r == 0) {
                item.lore("§a§l✸B-TIER", "§a§l3 Lives", "", "§6Light Feet 1", "§7Take no fall damage");
            } else if (r == 1) {
                item.lore("§a§l✸B-TIER", "§a§l3 Lives", "", "§6Dodge " + Core.generateNumber(1, 3), "§7Chance to dodge attacks");
            } else if (r == 2) {
                item.lore("§a§l✸B-TIER", "§a§l3 Lives", "");
            }
            return item.asItemStack();
        } else if (armor == MCCArmor.SILVERFISH_BOOTS) {
            ItemBuilder item = new ItemBuilder(Material.DIAMOND_BOOTS, 1);
            item.displayName("§7§lSilverfish Boots");
            item.unsafeEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
            item.unbreakable(true);
            int r = Core.generateNumber(0, 2);
            if (r == 0) {
                item.lore("§a§l✸B-TIER", "§a§l3 Lives", "", "§6Light Feet 1", "§7Take no fall damage");
            } else if (r == 1) {
                item.lore("§a§l✸B-TIER", "§a§l3 Lives", "", "§6Dodge " + Core.generateNumber(1, 3), "§7Chance to dodge attacks");
            } else if (r == 2) {
                item.lore("§a§l✸B-TIER", "§a§l3 Lives", "");
            }
            return item.asItemStack();
        } else if (armor == MCCArmor.DIAMOND_HELMET) {
            ItemBuilder item = new ItemBuilder(Material.DIAMOND_HELMET, 1);
            item.displayName("§b§lDiamond Helmet");
            item.unbreakable(true);
            item.lore("§a§l✸B-TIER", "§a§l5 Lives", "");
            return item.asItemStack();
        } else if (armor == MCCArmor.DIAMOND_CHESTPLATE) {
            ItemBuilder item = new ItemBuilder(Material.DIAMOND_CHESTPLATE, 1);
            item.displayName("§b§lDiamond Chestplate");
            item.unbreakable(true);
            item.lore("§a§l✸B-TIER", "§a§l5 Lives", "");
            return item.asItemStack();
        } else if (armor == MCCArmor.DIAMOND_LEGGINGS) {
            ItemBuilder item = new ItemBuilder(Material.DIAMOND_LEGGINGS, 1);
            item.displayName("§b§lDiamond Leggings");
            item.unbreakable(true);
            item.lore("§a§l✸B-TIER", "§a§l5 Lives", "");
            return item.asItemStack();
        } else if (armor == MCCArmor.DIAMOND_BOOTS) {
            ItemBuilder item = new ItemBuilder(Material.DIAMOND_BOOTS, 1);
            item.displayName("§b§lDiamond Boots");
            item.unbreakable(true);
            item.lore("§a§l✸B-TIER", "§a§l5 Lives", "");
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
            int r = Core.generateNumber(0, 2);
            if (r == 0) {
                item.lore("§4§l✸S-TIER", "§a§l" + Core.generateNumber(2, 3) + " Lives", "", "§6Glare " + Core.generateNumber(1, 3), "§7Chance to paralyze enemies");
            } else if (r == 1) {
                item.lore("§4§l✸S-TIER", "§a§l" + Core.generateNumber(2, 3) + " Lives", "", "§6Dragon Wrath " + Core.generateNumber(1, 3), "§7Chance to deal more damage");
            } else if (r == 2) {
                item.lore("§4§l✸S-TIER", "§a§l" + Core.generateNumber(2, 3) + " Lives", "");
            }
            return item.asItemStack();
        } else if (weapon == MCCWeapon.ZEUS_SWORD) {
            ItemBuilder item = new ItemBuilder(Material.DIAMOND_SWORD, 1);
            item.displayName("§6§lZeus Sword");
            item.enchant(Enchantment.DAMAGE_ALL, 2);
            item.unbreakable(true);
            int r = Core.generateNumber(0, 2);
            if (r == 0) {
                item.lore("§4§l✸S-TIER", "§a§l" + Core.generateNumber(2, 3) + " Lives", "", "§6Glare " + Core.generateNumber(1, 3), "§7Chance to paralyze enemies");
            } else if (r == 1) {
                item.lore("§4§l✸S-TIER", "§a§l" + Core.generateNumber(2, 3) + " Lives", "", "§6Nullify " + Core.generateNumber(1, 3), "§7Chance to prevent enemy from gaining buffs");
            } else if (r == 2) {
                item.lore("§4§l✸S-TIER", "§a§l" + Core.generateNumber(2, 3) + " Lives", "");
            }
            return item.asItemStack();
        } else if (weapon == MCCWeapon.ZEUS_BOW) {
            ItemBuilder item = new ItemBuilder(Material.BOW, 1);
            item.displayName("§6§lZeus Bow");
            item.enchant(Enchantment.ARROW_DAMAGE, 4);
            int r = Core.generateNumber(0, 2);
            if (r == 0) {
                item.lore("§4§l✸S-TIER", "§a§l" + Core.generateNumber(2, 3) + " Lives", "", "§6Thunder " + Core.generateNumber(1, 3), "§7Initial shot consecutively smites the target");
            } else if (r == 1) {
                item.lore("§4§l✸S-TIER", "§a§l" + Core.generateNumber(2, 3) + " Lives", "", "§6Piercing " + Core.generateNumber(1, 3), "§7Chance to deal true damage");
            } else if (r == 2) {
                item.lore("§4§l✸S-TIER", "§a§l" + Core.generateNumber(2, 3) + " Lives", "");
            }
            item.unbreakable(true);
            return item.asItemStack();
        } else if (weapon == MCCWeapon.HADES_AXE) {
            ItemBuilder item = new ItemBuilder(Material.DIAMOND_AXE, 1);
            item.displayName("§4§lHades Axe");
            item.unsafeEnchant(Enchantment.DAMAGE_ALL, 3);
            item.unbreakable(true);
            int r = Core.generateNumber(0, 2);
            if (r == 0) {
                item.lore("§4§l✸S-TIER", "§a§l" + Core.generateNumber(2, 3) + " Lives", "", "§6Blinding Strike " + Core.generateNumber(1, 3), "§7Chance to blind enemies");
            } else if (r == 1) {
                item.lore("§4§l✸S-TIER", "§a§l" + Core.generateNumber(2, 3) + " Lives", "", "§6Nullify " + Core.generateNumber(1, 3), "§7Chance to prevent enemy from gaining buffs");
            } else if (r == 2) {
                item.lore("§4§l✸S-TIER", "§a§l" + Core.generateNumber(2, 3) + " Lives", "");
            }
            return item.asItemStack();
        } else if (weapon == MCCWeapon.HADES_BOW) {
            ItemBuilder item = new ItemBuilder(Material.BOW, 1);
            item.displayName("§4§lHades Bow");
            item.enchant(Enchantment.ARROW_DAMAGE, 4);
            int r = Core.generateNumber(0, 2);
            if (r == 0) {
                item.lore("§4§l✸S-TIER", "§a§l" + Core.generateNumber(2, 3) + " Lives", "", "§6Trap 1", "§7Initial shot prevents target from enderpearling");
            } else if (r == 1) {
                item.lore("§4§l✸S-TIER", "§a§l" + Core.generateNumber(2, 3) + " Lives", "", "§6Piercing " + Core.generateNumber(1, 3), "§7Chance to deal true damage");
            } else if (r == 2) {
                item.lore("§4§l✸S-TIER", "§a§l" + Core.generateNumber(2, 3) + " Lives", "");
            }
            item.unbreakable(true);
            return item.asItemStack();
        } else if (weapon == MCCWeapon.DRAGON_BOW) {
            ItemBuilder item = new ItemBuilder(Material.BOW, 1);
            item.displayName("§5§lDragon Bow");
            item.enchant(Enchantment.ARROW_DAMAGE, 4);
            int r = Core.generateNumber(0, 2);
            if (r == 0) {
                item.lore("§4§l✸S-TIER", "§a§l" + Core.generateNumber(2, 3) + " Lives", "", "§6Wither " + Core.generateNumber(1, 3), "§7Chance to apply wither to enemies");
            } else if (r == 1) {
                item.lore("§4§l✸S-TIER", "§a§l" + Core.generateNumber(2, 3) + " Lives", "", "§6Smite " + Core.generateNumber(1, 3), "§7Chance to smite enemies");
            } else if (r == 2) {
                item.lore("§4§l✸S-TIER", "§a§l" + Core.generateNumber(2, 3) + " Lives", "");
            }
            item.unbreakable(true);
            return item.asItemStack();
        } else if (weapon == MCCWeapon.BLAZE_SWORD) {
            ItemBuilder item = new ItemBuilder(Material.DIAMOND_SWORD, 1);
            item.displayName("§6§lBlaze Sword");
            item.enchant(Enchantment.DAMAGE_ALL, 1);
            item.unbreakable(true);
            int r = Core.generateNumber(0, 2);
            if (r == 0) {
                item.lore("§4§l✸S-TIER", "§a§l" + Core.generateNumber(2, 3) + " Lives", "", "§6Fire Aspect " + Core.generateNumber(1, 3), "§7Deal more damage to enemies on fire");
            } else if (r == 1) {
                item.lore("§4§l✸S-TIER", "§a§l" + Core.generateNumber(2, 3) + " Lives", "", "§6Ignite " + Core.generateNumber(1, 3), "§7Chance to remove fire resistance and ignite enemy");
            } else if (r == 2) {
                item.lore("§4§l✸S-TIER", "§a§l" + Core.generateNumber(2, 3) + " Lives", "");
            }
            return item.asItemStack();
        } else if (weapon == MCCWeapon.BLAZE_BOW) {
            ItemBuilder item = new ItemBuilder(Material.BOW, 1);
            item.displayName("§6§lBlaze Bow");
            item.enchant(Enchantment.ARROW_DAMAGE, 4);
            int r = Core.generateNumber(0, 2);
            if (r == 0) {
                item.lore("§4§l✸S-TIER", "§a§l" + Core.generateNumber(2, 3) + " Lives", "", "§6Flame 1", "§7Set enemies hit on fire");
                item.enchant(Enchantment.ARROW_FIRE, 1);
            } else if (r == 1) {
                item.lore("§4§l✸S-TIER", "§a§l" + Core.generateNumber(2, 3) + " Lives", "", "§6Multishot " + Core.generateNumber(1, 3), "§7Chance to fire multiple arrows");
            } else if (r == 2) {
                item.lore("§4§l✸S-TIER", "§a§l" + Core.generateNumber(2, 3) + " Lives", "");
            }
            item.unbreakable(true);
            return item.asItemStack();
        } else if (weapon == MCCWeapon.GIANT_AXE) {
            ItemBuilder item = new ItemBuilder(Material.DIAMOND_AXE, 1);
            item.displayName("§2§lGiant Axe");
            item.unsafeEnchant(Enchantment.DAMAGE_ALL, 2);
            item.unbreakable(true);
            int r = Core.generateNumber(0, 2);
            if (r == 0) {
                item.lore("§4§l✸S-TIER", "§a§l" + Core.generateNumber(2, 3) + " Lives", "", "§6Strip " + Core.generateNumber(1, 3), "§7Chance to take off an armor piece");
            } else if (r == 1) {
                item.lore("§4§l✸S-TIER", "§a§l" + Core.generateNumber(2, 3) + " Lives", "", "§6Demolish " + Core.generateNumber(1, 3), "§7Your initial hit stuns the enemy");
            } else if (r == 2) {
                item.lore("§4§l✸S-TIER", "§a§l" + Core.generateNumber(2, 3) + " Lives", "");
            }
            return item.asItemStack();
        } else if (weapon == MCCWeapon.GIANT_BOW) {
            ItemBuilder item = new ItemBuilder(Material.BOW, 1);
            item.displayName("§2§lGiant Bow");
            item.enchant(Enchantment.ARROW_KNOCKBACK, 2);
            item.enchant(Enchantment.ARROW_INFINITE, 1);
            item.lore("§4§l✸S-TIER", "§a§l" + Core.generateNumber(2, 3) + " Lives", "");
            item.unbreakable(true);
            return item.asItemStack();
        } else if (weapon == MCCWeapon.MOOSHROOM_AXE) {
            ItemBuilder item = new ItemBuilder(Material.DIAMOND_AXE, 1);
            item.displayName("§c§lMooshroom Axe");
            item.unsafeEnchant(Enchantment.DAMAGE_ALL, 2);
            item.unbreakable(true);
            int r = Core.generateNumber(0, 2);
            if (r == 0) {
                item.lore("§4§l✸S-TIER", "§a§l" + Core.generateNumber(2, 3) + " Lives", "", "§6Rivalry " + Core.generateNumber(1, 3), "§7Deal increased damage to sword users");
            } else if (r == 1) {
                item.lore("§4§l✸S-TIER", "§a§l" + Core.generateNumber(2, 3) + " Lives", "", "§6Lifesteal " + Core.generateNumber(1, 3), "§7Chance to steal health");
            } else if (r == 2) {
                item.lore("§4§l✸S-TIER", "§a§l" + Core.generateNumber(2, 3) + " Lives", "");
            }
            return item.asItemStack();
        } else if (weapon == MCCWeapon.SQUID_SWORD) {
            ItemBuilder item = new ItemBuilder(Material.DIAMOND_SWORD, 1);
            item.displayName("§3§lSquid Sword");
            item.unbreakable(true);
            int r = Core.generateNumber(0, 2);
            if (r == 0) {
                item.lore("§e§l✸A-TIER", "§a§l" + Core.generateNumber(2, 3) + " Lives", "", "§6Blinding Strike " + Core.generateNumber(1, 3), "§7Chance to blind enemies");
            } else if (r == 1) {
                item.lore("§e§l✸A-TIER", "§a§l" + Core.generateNumber(2, 3) + " Lives", "", "§6Torrent " + Core.generateNumber(1, 3), "§7Deal increased damage in water");
            } else if (r == 2) {
                item.lore("§e§l✸A-TIER", "§a§l" + Core.generateNumber(2, 3) + " Lives", "");
            }
            return item.asItemStack();
        } else if (weapon == MCCWeapon.PIGMAN_SWORD) {
            ItemBuilder item = new ItemBuilder(Material.DIAMOND_SWORD, 1);
            item.displayName("§c§lPigman Sword");
            item.unbreakable(true);
            int r = Core.generateNumber(0, 2);
            if (r == 0) {
                item.lore("§e§l✸A-TIER", "§a§l" + Core.generateNumber(2, 3) + " Lives", "", "§6Double Hit " + Core.generateNumber(1, 3), "§7Chance to strike twice");
            } else if (r == 1) {
                item.lore("§e§l✸A-TIER", "§a§l" + Core.generateNumber(2, 3) + " Lives", "", "§6Disarm " + Core.generateNumber(1, 3), "§7Chance to disarm enemy's weapon");
            } else if (r == 2) {
                item.lore("§e§l✸A-TIER", "§a§l" + Core.generateNumber(2, 3) + " Lives", "");
            }
            return item.asItemStack();
        } else if (weapon == MCCWeapon.ENDERMAN_AXE) {
            ItemBuilder item = new ItemBuilder(Material.DIAMOND_AXE, 1);
            item.displayName("§5§lEnderman Axe");
            item.unbreakable(true);
            item.unsafeEnchant(Enchantment.DAMAGE_ALL, 1);
            int r = Core.generateNumber(0, 2);
            if (r == 0) {
                item.lore("§e§l✸A-TIER", "§a§l" + Core.generateNumber(2, 3) + " Lives", "", "§6Blinding Strike " + Core.generateNumber(1, 3), "§7Chance to blind enemies");
            } else if (r == 1) {
                item.lore("§e§l✸A-TIER", "§a§l" + Core.generateNumber(2, 3) + " Lives", "", "§6Knockup " + Core.generateNumber(1, 3), "§7Chance to knockup enemies");
            } else if (r == 2) {
                item.lore("§e§l✸A-TIER", "§a§l" + Core.generateNumber(2, 3) + " Lives", "");
            }
            return item.asItemStack();
        } else if (weapon == MCCWeapon.ENDERMAN_BOW) {
            ItemBuilder item = new ItemBuilder(Material.BOW, 1);
            item.displayName("§5§lEnderman Bow");
            item.unbreakable(true);
            item.enchant(Enchantment.ARROW_DAMAGE, 3);
            int r = Core.generateNumber(0, 2);
            if (r == 0) {
                item.lore("§e§l✸A-TIER", "§a§l" + Core.generateNumber(2, 3) + " Lives", "", "§6Identify 1", "§7Apply glow to enemies hit");
            } else if (r == 1) {
                item.lore("§e§l✸A-TIER", "§a§l" + Core.generateNumber(2, 3) + " Lives", "", "§6Paralyze " + Core.generateNumber(1, 3), "§7Chance to stun enemies hit");
            } else if (r == 2) {
                item.lore("§e§l✸A-TIER", "§a§l" + Core.generateNumber(2, 3) + " Lives", "");
            }
            return item.asItemStack();
        } else if (weapon == MCCWeapon.BAT_SWORD) {
            ItemBuilder item = new ItemBuilder(Material.DIAMOND_SWORD, 1);
            item.displayName("§8§lBat Sword");
            item.unbreakable(true);
            int r = Core.generateNumber(0, 2);
            if (r == 0) {
                item.lore("§e§l✸A-TIER", "§a§l" + Core.generateNumber(2, 3) + " Lives", "", "§6Nausea " + Core.generateNumber(1, 3), "§7Chance to confuse enemies");
            } else if (r == 1) {
                item.lore("§e§l✸A-TIER", "§a§l" + Core.generateNumber(2, 3) + " Lives", "", "§6Famine " + Core.generateNumber(1, 3), "§7Chance to starve enemies");
            } else if (r == 2) {
                item.lore("§e§l✸A-TIER", "§a§l" + Core.generateNumber(2, 3) + " Lives", "");
            }
            return item.asItemStack();
        } else if (weapon == MCCWeapon.SLIME_SWORD) {
            ItemBuilder item = new ItemBuilder(Material.DIAMOND_SWORD, 1);
            item.displayName("§a§lSlime Sword");
            item.unbreakable(true);
            int r = Core.generateNumber(0, 2);
            if (r == 0) {
                item.lore("§e§l✸A-TIER", "§a§l" + Core.generateNumber(2, 3) + " Lives", "", "§6Lifesteal " + Core.generateNumber(1, 3), "§7Chance to steal health");
            } else if (r == 1) {
                item.lore("§e§l✸A-TIER", "§a§l" + Core.generateNumber(2, 3) + " Lives", "", "§6Knockup " + Core.generateNumber(1, 3), "§7Chance to knockup enemies");
            } else if (r == 2) {
                item.lore("§e§l✸A-TIER", "§a§l" + Core.generateNumber(2, 3) + " Lives", "");
            }
            return item.asItemStack();
        } else if (weapon == MCCWeapon.GOLEM_AXE) {
            ItemBuilder item = new ItemBuilder(Material.DIAMOND_AXE, 1);
            item.unsafeEnchant(Enchantment.DAMAGE_ALL, 1);
            item.displayName("§8§lGolem Axe");
            item.unbreakable(true);
            int r = Core.generateNumber(0, 2);
            if (r == 0) {
                item.lore("§e§l✸A-TIER", "§a§l" + Core.generateNumber(2, 3) + " Lives", "", "§6Ice Aspect " + Core.generateNumber(1, 3), "§7Chance to slow enemies");
            } else if (r == 1) {
                item.lore("§e§l✸A-TIER", "§a§l" + Core.generateNumber(2, 3) + " Lives", "", "§6Rivalry " + Core.generateNumber(1, 3), "§7Deal increased damage to sword users");
            } else if (r == 2) {
                item.lore("§e§l✸A-TIER", "§a§l" + Core.generateNumber(2, 3) + " Lives", "");
            }
            return item.asItemStack();
        } else if (weapon == MCCWeapon.POLAR_BEAR_AXE) {
            ItemBuilder item = new ItemBuilder(Material.DIAMOND_AXE, 1);
            item.unsafeEnchant(Enchantment.DAMAGE_ALL, 1);
            item.displayName("§f§lPolar Bear Axe");
            item.unbreakable(true);
            int r = Core.generateNumber(0, 2);
            if (r == 0) {
                item.lore("§e§l✸A-TIER", "§a§l" + Core.generateNumber(2, 3) + " Lives", "", "§6Ice Aspect " + Core.generateNumber(1, 3), "§7Chance to slow enemies");
            } else if (r == 1) {
                item.lore("§e§l✸A-TIER", "§a§l" + Core.generateNumber(2, 3) + " Lives", "", "§6Frozen " + Core.generateNumber(1, 3), "§7Chance to freeze enemies");
            } else if (r == 2) {
                item.lore("§e§l✸A-TIER", "§a§l" + Core.generateNumber(2, 3) + " Lives", "");
            }
            return item.asItemStack();
        } else if (weapon == MCCWeapon.GOLEM_BOW) {
            ItemBuilder item = new ItemBuilder(Material.BOW, 1);
            item.displayName("§8§lGolem Bow");
            item.unbreakable(true);
            item.enchant(Enchantment.ARROW_KNOCKBACK, 1);
            item.enchant(Enchantment.ARROW_INFINITE, 1);
            item.lore("§e§l✸A-TIER", "§a§l" + Core.generateNumber(1, 3) + " Lives", "");
            return item.asItemStack();
        } else if (weapon == MCCWeapon.SKELETON_BOW) {
            ItemBuilder item = new ItemBuilder(Material.BOW, 1);
            item.displayName("§f§lSkeleton Bow");
            item.unbreakable(true);
            item.enchant(Enchantment.ARROW_DAMAGE, 3);
            item.lore("§a§l✸B-TIER", "§a§l3 Lives", "");
            return item.asItemStack();
        } else if (weapon == MCCWeapon.PIG_SWORD) {
            ItemBuilder item = new ItemBuilder(Material.DIAMOND_SWORD, 1);
            item.displayName("§d§lPig Sword");
            item.unbreakable(true);
            item.lore("§a§l✸B-TIER", "§a§l3 Lives", "");
            return item.asItemStack();
        } else if (weapon == MCCWeapon.ZOMBIE_SWORD) {
            ItemBuilder item = new ItemBuilder(Material.DIAMOND_SWORD, 1);
            item.displayName("§2§lZombie Sword");
            item.unbreakable(true);
            item.lore("§a§l✸B-TIER", "§a§l3 Lives", "");
            return item.asItemStack();
        } else if (weapon == MCCWeapon.SILVERFISH_AXE) {
            ItemBuilder item = new ItemBuilder(Material.DIAMOND_AXE, 1);
            item.displayName("§7§lSilverfish Axe");
            item.unbreakable(true);
            item.lore("§a§l✸B-TIER", "§a§l3 Lives", "");
            return item.asItemStack();
        } else if (weapon == MCCWeapon.DIAMOND_SWORD) {
            ItemBuilder item = new ItemBuilder(Material.DIAMOND_SWORD, 1);
            item.displayName("§b§lDiamond Sword");
            item.unbreakable(true);
            item.lore("§a§l✸B-TIER", "§a§l5 Lives", "");
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
            item.unsafeEnchant(Enchantment.DIG_SPEED, 1);
            item.lore("§4§l✸S-TIER", "", "§eIncreases ingots when mining");
            return item.asItemStack();
        } else if (artifact == MCCArtifact.GIANT_GRINDER) {
            ItemBuilder item = new ItemBuilder(Material.IRON_SWORD, 1);
            item.displayName("§2Giant Grinder");
            item.unsafeEnchant(Enchantment.DAMAGE_UNDEAD, 4);
            item.unsafeEnchant(Enchantment.DURABILITY, 2);
            item.lore("§4§l✸S-TIER", "", "§eIncreases essence gained from mobs");
            return item.asItemStack();
        } else if (artifact == MCCArtifact.GOD_SLAYER) {
            ItemBuilder item = new ItemBuilder(Material.DIAMOND_SWORD, 1);
            item.displayName("§4God Slayer");
            item.unsafeEnchant(Enchantment.DAMAGE_UNDEAD, 4);
            item.unsafeEnchant(Enchantment.DAMAGE_ALL, 1);
            item.lore("§4§l✸S-TIER", "", "§eDeal more damage to bosses");
            return item.asItemStack();
        } else if (artifact == MCCArtifact.EXTREME_KNOCKBACK_STICK) {
            ItemBuilder item = new ItemBuilder(Material.STICK, 1);
            item.displayName("§4§lExtreme Knockback Stick");
            item.enchant(Enchantment.KNOCKBACK, 2);
            item.flag(ItemFlag.HIDE_ENCHANTS);
            item.lore("§4§l✸S-TIER", "§a§l" + Core.generateNumber(1, 2) + " Lives", "", "§eDeal extreme knockback with this stick");
            return item.asItemStack();
        } else if (artifact == MCCArtifact.CRYSTAL_BEATER) {
            ItemBuilder item = new ItemBuilder(Material.GOLDEN_SWORD, 1);
            item.displayName("§d§lCrystal Beater");
            item.setGlowing(true);
            item.unbreakable(true);
            item.lore("§e§l✸A-TIER", "§a§l" + Core.generateNumber(1, 2) + " Lives", "", "§eDeal extra damage to crystals");
            return item.asItemStack();
        } else if (artifact == MCCArtifact.SQUID_ROD) {
            ItemBuilder item = new ItemBuilder(Material.FISHING_ROD, 1);
            item.displayName("§3Squid Rod");
            item.lore("§e§l✸A-TIER", "", "§eIncreases loot from fishing");
            return item.asItemStack();
        } else if (artifact == MCCArtifact.PIGMAN_PICKAXE) {
            ItemBuilder item = new ItemBuilder(Material.IRON_PICKAXE, 1);
            item.displayName("§cPigman Pickaxe");
            item.unsafeEnchant(Enchantment.DIG_SPEED, 1);
            item.lore("§e§l✸A-TIER", "", "§eIncreases ingots when mining");
            return item.asItemStack();
        } else if (artifact == MCCArtifact.ZOMBIE_GRINDER) {
            ItemBuilder item = new ItemBuilder(Material.IRON_SWORD, 1);
            item.displayName("§2Zombie Grinder");
            item.unsafeEnchant(Enchantment.DAMAGE_UNDEAD, 3);
            item.unsafeEnchant(Enchantment.DURABILITY, 1);
            item.lore("§e§l✸A-TIER", "", "§eIncreases essence gained from mobs");
            return item.asItemStack();
        } else if (artifact == MCCArtifact.FISHING_ROD) {
            ItemBuilder item = new ItemBuilder(Material.FISHING_ROD, 1);
            item.displayName("§eFishing Rod");
            item.lore("§a§l✸B-TIER", "");
            return item.asItemStack();
        } else if (artifact == MCCArtifact.IRON_PICKAXE) {
            ItemBuilder item = new ItemBuilder(Material.IRON_PICKAXE, 1);
            item.displayName("§7Iron Pickaxe");
            item.lore("§a§l✸B-TIER", "");
            return item.asItemStack();
        } else if (artifact == MCCArtifact.KNOCKBACK_STICK) {
            ItemBuilder item = new ItemBuilder(Material.STICK, 1);
            item.displayName("§c§lKnockback Stick");
            item.enchant(Enchantment.KNOCKBACK, 1);
            item.flag(ItemFlag.HIDE_ENCHANTS);
            item.lore("§e§l✸A-TIER", "§a§l" + Core.generateNumber(1, 2) + " Lives", "", "§eDeal knockback with this stick");
            return item.asItemStack();
        }
        return null;
    }

    public static ItemStack getPetVoucher(Pet pet) {
        ItemBuilder item;
        switch (pet) {
            case GIANT:
                item = new ItemBuilder(CustomHeadManager.getHead(pet));
                item.displayName("§9Giant Pet");
                item.lore("§f§lABILITY: §eGain permanent health boost 2", "", "§eRight click to claim this pet.");
                return item.asItemStack();
            case DRAGON:
                item = new ItemBuilder(CustomHeadManager.getHead(pet));
                item.displayName("§9Dragon Pet");
                item.lore("§f§lABILITY: §eSmall chance to gain strength 2 when hit", "", "§eRight click to claim this pet.");
                return item.asItemStack();
            case HORSE:
                item = new ItemBuilder(CustomHeadManager.getHead(pet));
                item.displayName("§9Horse Pet");
                item.lore("§f§lABILITY: §eGain permanent speed 1", "", "§eRight click to claim this pet.");
                return item.asItemStack();
            case SKELETON_HORSE:
                item = new ItemBuilder(CustomHeadManager.getHead(pet));
                item.displayName("§9Skeleton Horse Pet");
                item.lore("§f§lABILITY: §eGain permanent speed 1,", "§eSmall chance to gain speed 2 when hit", "§eRight click to claim this pet.");
                return item.asItemStack();
            case GOLEM:
                item = new ItemBuilder(CustomHeadManager.getHead(pet));
                item.displayName("§9Golem Pet");
                item.lore("§f§lABILITY: §eSmall chance to gain resistance 2 when hit", "", "§eRight click to claim this pet.");
                return item.asItemStack();
            case MOOSHROOM:
                item = new ItemBuilder(CustomHeadManager.getHead(pet));
                item.displayName("§9Mooshroom Pet");
                item.lore("§f§lABILITY: §eSmall chance to gain regeneration 4 when hit", "", "§eRight click to claim this pet.");
                return item.asItemStack();
            case SQUID:
                item = new ItemBuilder(CustomHeadManager.getHead(pet));
                item.displayName("§9Squid Pet");
                item.lore("§f§lABILITY: §eGain permanent water breathing 1", "", "§eRight click to claim this pet.");
                return item.asItemStack();
            case PIGMAN:
                item = new ItemBuilder(CustomHeadManager.getHead(pet));
                item.displayName("§9Pigman Pet");
                item.lore("§f§lABILITY: §eSmall chance to gain speed 1 & strength 1", "", "§eRight click to claim this pet.");
                return item.asItemStack();
            case BLAZE:
                item = new ItemBuilder(CustomHeadManager.getHead(pet));
                item.displayName("§9Blaze Pet");
                item.lore("§f§lABILITY: §eGain immunity to fire", "", "§eRight click to claim this pet.");
                return item.asItemStack();
            case POLAR_BEAR:
                item = new ItemBuilder(CustomHeadManager.getHead(pet));
                item.displayName("§9Polar Bear Pet");
                item.lore("§f§lABILITY: §eWhen slowed, gain resistance 2", "", "§eRight click to claim this pet.");
                return item.asItemStack();
            case SLIME:
                item = new ItemBuilder(CustomHeadManager.getHead(pet));
                item.displayName("§9Slime Pet");
                item.lore("§f§lABILITY: §eGain immunity to slowness", "", "§eRight click to claim this pet.");
                return item.asItemStack();
            case PIG:
                item = new ItemBuilder(CustomHeadManager.getHead(pet));
                item.displayName("§9Pig Pet");
                item.lore("§f§lABILITY: §eGain an extra ingot when mining", "", "§eRight click to claim this pet.");
                return item.asItemStack();
            case OCELOT:
                item = new ItemBuilder(CustomHeadManager.getHead(pet));
                item.displayName("§9Ocelot Pet");
                item.lore("§f§lABILITY: §eFishing times are shorter", "", "§eRight click to claim this pet.");
                return item.asItemStack();
            case SILVERFISH:
                item = new ItemBuilder(CustomHeadManager.getHead(pet));
                item.displayName("§9Silverfish Pet");
                item.lore("§f§lABILITY: §eGain permanent haste 1", "", "§eRight click to claim this pet.");
                return item.asItemStack();
            case SKELETON:
                item = new ItemBuilder(CustomHeadManager.getHead(pet));
                item.displayName("§9Skeleton Pet");
                item.lore("§f§lABILITY: §eChance to dodge bow shots", "", "§eRight click to claim this pet.");
                return item.asItemStack();
            case ENDERMAN:
                item = new ItemBuilder(CustomHeadManager.getHead(pet));
                item.displayName("§9Enderman Pet");
                item.lore("§f§lABILITY: §eChance to teleport to targets hit with bow shots", "", "§eRight click to claim this pet.");
                return item.asItemStack();
            case SPIDER:
                item = new ItemBuilder(CustomHeadManager.getHead(pet));
                item.displayName("§9Spider Pet");
                item.lore("§f§lABILITY: §eSmall chance to give slowness", "", "§eRight click to claim this pet.");
                return item.asItemStack();
            case BAT:
                item = new ItemBuilder(CustomHeadManager.getHead(pet));
                item.displayName("§9Bat Pet");
                item.lore("§f§lABILITY: §eSmall chance to give blindness", "", "§eRight click to claim this pet.");
                return item.asItemStack();
            case RABBIT:
                item = new ItemBuilder(CustomHeadManager.getHead(pet));
                item.displayName("§9Rabbit Pet");
                item.lore("§f§lABILITY: §eGain permanent jump boost 1", "", "§eRight click to claim this pet.");
                return item.asItemStack();
            case CHICKEN:
                item = new ItemBuilder(CustomHeadManager.getHead(pet));
                item.displayName("§9Chicken Pet");
                item.lore("§f§lABILITY: §eSmall chance to dodge attacks", "", "§eRight click to claim this pet.");
                return item.asItemStack();
            case WOLF:
                item = new ItemBuilder(CustomHeadManager.getHead(pet));
                item.displayName("§9Wolf Pet");
                item.lore("§f§lABILITY: §eSmall chance to spawn a wolf", "", "§eRight click to claim this pet.");
                return item.asItemStack();
            case GHAST:
                item = new ItemBuilder(CustomHeadManager.getHead(pet));
                item.displayName("§9Ghast Pet");
                item.lore("§f§lABILITY: §eSmall chance to apply wither with bow shots", "", "§eRight click to claim this pet.");
                return item.asItemStack();
            case MAGMA_CUBE:
                item = new ItemBuilder(CustomHeadManager.getHead(pet));
                item.displayName("§9Magma Cube Pet");
                item.lore("§f§lABILITY: §eSmall chance to remove fire", "§eresistance & ignite enemy", "§eRight click to claim this pet.");
                return item.asItemStack();
            case GUARDIAN:
                item = new ItemBuilder(CustomHeadManager.getHead(pet));
                item.displayName("§9Guardian Pet");
                item.lore("§f§lABILITY: §eIncrease chances of fishing up crates", "", "§eRight click to claim this pet.");
                return item.asItemStack();
            case WITCH:
                item = new ItemBuilder(CustomHeadManager.getHead(pet));
                item.displayName("§9Witch Pet");
                item.lore("§f§lABILITY: §eSmall chance to give poison", "", "§eRight click to claim this pet.");
                return item.asItemStack();
            case SHEEP:
                item = new ItemBuilder(CustomHeadManager.getHead(pet));
                item.displayName("§9Sheep Pet");
                item.lore("§f§lABILITY: §eGain extra money from money vouchers", "", "§eRight click to claim this pet.");
                return item.asItemStack();
            case ZOMBIE:
                item = new ItemBuilder(CustomHeadManager.getHead(pet));
                item.displayName("§9Zombie Pet");
                item.lore("§f§lABILITY: §eLose no hunger", "", "§eRight click to claim this pet.");
                return item.asItemStack();

        }
        return null;
    }

    public static ItemStack getArmorVoucher() {
        ItemBuilder item = new ItemBuilder(Material.BOOK, 1);
        item.displayName("§6§lArmor Voucher");
        item.lore("§eRight click to get a random armor.");
        item.setGlowing(true);
        return item.asItemStack();
    }

    public static ItemStack getWeaponVoucher() {
        ItemBuilder item = new ItemBuilder(Material.BOOK, 1);
        item.displayName("§6§lWeapon Voucher");
        item.lore("§eRight click to get a random weapon.");
        item.setGlowing(true);
        return item.asItemStack();
    }

    public static ItemStack getSwordVoucher() {
        ItemBuilder item = new ItemBuilder(Material.BOOK, 1);
        item.displayName("§6§lSword Voucher");
        item.lore("§eRight click to get a random S/A Tier sword.");
        item.setGlowing(true);
        return item.asItemStack();
    }

    public static ItemStack getAxeVoucher() {
        ItemBuilder item = new ItemBuilder(Material.BOOK, 1);
        item.displayName("§6§lAxe Voucher");
        item.lore("§eRight click to get a random S/A Tier axe.");
        item.setGlowing(true);
        return item.asItemStack();
    }

    public static ItemStack getSTierArmorVoucher() {
        ItemBuilder item = new ItemBuilder(Material.BOOK, 1);
        item.displayName("§4§lS-Tier §6§lArmor Voucher");
        item.lore("§eRight click to get a random S Tier armor.");
        item.setGlowing(true);
        return item.asItemStack();
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

    public static ItemStack getKitVoucher(MCCKit kit) {
        ItemBuilder item = new ItemBuilder(Material.DIAMOND);
        item.displayName("§a§l" + kit.name() + " Kit");
        item.lore("§eRight click to get a random generator.");
        item.setGlowing(true);
        return item.asItemStack();
    }

    public static ItemStack getEssence(boolean rare, int amount) {
        if (!rare) {
            ItemBuilder item = new ItemBuilder(Material.PRISMARINE_CRYSTALS, 1);
            item.displayName("§3Essence");
            item.lore("§eUse this to trade with merchants.");
            item.amount(amount);
            return item.asItemStack();
        } else {
            ItemBuilder item = new ItemBuilder(Material.PRISMARINE_SHARD, 1);
            item.displayName("§3Rare Essence");
            item.lore("§eUse this to trade with merchants.");
            item.amount(amount);
            return item.asItemStack();
        }
    }

    public static ItemStack getIngot(boolean rare, int amount) {
        if (!rare) {
            ItemBuilder item = new ItemBuilder(Material.IRON_INGOT, 1);
            item.displayName("§7Iron Ingot");
            item.lore("§eUse this to trade with merchants.");
            item.amount(amount);
            return item.asItemStack();
        } else {
            ItemBuilder item = new ItemBuilder(Material.GOLD_INGOT, 1);
            item.displayName("§eGold Ingot");
            item.lore("§eUse this to trade with merchants.");
            item.amount(amount);
            return item.asItemStack();
        }
    }

    public static ItemStack getCrate(Crate crate) {
        switch (crate) {
            case VOTE_CRATE:
                return EliteLootbox.getLootboxes().get("vote").getItemStack();
            case BASIC_CRATE:
                return EliteLootbox.getLootboxes().get("basic").getItemStack();
            case SUPER_CRATE:
                return EliteLootbox.getLootboxes().get("super").getItemStack();
            case ULTRA_CRATE:
                return EliteLootbox.getLootboxes().get("ultra").getItemStack();
            case GODLY_CRATE:
                return EliteLootbox.getLootboxes().get("godly").getItemStack();
            case PET_CRATE:
                return EliteLootbox.getLootboxes().get("pet").getItemStack();
            case SEASON_CRATE:
                return new ItemStack(Material.END_PORTAL_FRAME);
        }
        return null;
    }

    public static ItemStack getBossEgg(MCCBoss boss) {
        if (boss == MCCBoss.ZEUS) {
            ItemBuilder item = new ItemBuilder(Material.DANDELION, 1);
            item.displayName("§6§lZeus Summoner");
            item.lore("§eRight click a block to summon Zeus", "", "§4This boss can only be summoned in the PvP Zone.");
            item.setGlowing(true);
            return item.asItemStack();
        } else if (boss == MCCBoss.HADES) {
            ItemBuilder item = new ItemBuilder(Material.WITHER_ROSE, 1);
            item.displayName("§4§lHades Summoner");
            item.lore("§eRight click a block to summon Hades", "", "§4This boss can only be summoned in the PvP Zone.");
            item.setGlowing(true);
            return item.asItemStack();
        }
        return null;
    }

    public static ItemStack getRankVoucher(String rank) {
        ItemBuilder item = new ItemBuilder(Material.EMERALD, 1);
        item.displayName("§f§l" + rank + " Rank Gem");
        item.lore("§eRight click to claim this rank", "", "§4Once claimed, the rank can not be unclaimed!");
        return item.asItemStack();
    }

    public static ItemStack getRandomMoneyNote(int min, int max) {
        ItemBuilder item = new ItemBuilder(Material.PAPER, 1);
        item.displayName("§a§lMoney Note");
        int value = Core.generateNumber(min, max);
        item.lore("§6Value: §a$" + value, "", "§eRight click to redeem the note");
        item.setGlowing(true);
        return item.asItemStack();
    }

    public static ItemStack getMergerDust(boolean WeaponOrArmor) {
        if (WeaponOrArmor) {
            ItemBuilder item = new ItemBuilder(Material.GLOWSTONE_DUST, 1);
            item.displayName("§c§lWeapon Dust");
            item.lore("§eTrade this in to merge weapons.");
            item.setGlowing(true);
            return item.asItemStack();
        } else {
            ItemBuilder item = new ItemBuilder(Material.GUNPOWDER, 1);
            item.displayName("§c§lArmor Dust");
            item.lore("§eTrade this in to merge armor.");
            item.setGlowing(true);
            return item.asItemStack();
        }
    }

    public static ItemStack getBlacksmithsCoal() {
        ItemBuilder item = new ItemBuilder(Material.COAL, 1);
        item.displayName("§4§lBlacksmith's Coal");
        item.lore("§eForge this to reroll an armor enchant.");
        item.setGlowing(true);
        return item.asItemStack();
    }

    public static ItemStack getBlacksmithsMagmaRod() {
        ItemBuilder item = new ItemBuilder(Material.BLAZE_ROD, 1);
        item.displayName("§4§lBlacksmith's Magma Rod");
        item.lore("§eForge this to reroll a weapon enchant.");
        item.setGlowing(true);
        return item.asItemStack();
    }

    public static ItemStack getBlacksmithsMagicDust() {
        ItemBuilder item = new ItemBuilder(Material.REDSTONE, 1);
        item.displayName("§5§lBlacksmith's Magic Dust");
        item.lore("§eForge this to increase an item's enchant level.");
        item.setGlowing(true);
        return item.asItemStack();
    }

    public static ItemStack getBlacksmithsLifeOrb() {
        ItemBuilder item = new ItemBuilder(Material.SLIME_BALL, 1);
        item.displayName("§d§lBlacksmith's Life Orb");
        item.lore("§eForge this to increase an item's lives.");
        item.setGlowing(true);
        return item.asItemStack();
    }

    public static ItemStack getTag(Tag tag) {
        ItemBuilder item = new ItemBuilder((Material.NAME_TAG));
        item.displayName(tag.getTagString() + " §fTag Voucher");
        item.lore("", "§eRight click to claim the tag.");
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
