package me.robertle.mcconquest.Managers;

import me.robertle.mcconquest.*;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class CustomEnchantManager implements Listener {

    public static List<UUID> nullified = new ArrayList<>();

    public static void addPotionEffect(Player player, PotionEffect effect) {
        if (effect.getType() == PotionEffectType.BLINDNESS) {
            if (InventoryUtil.hasCustomArmorEnchant(player, "Keen Eye")) {
                player.sendMessage("§6Your keen eye has prevented you from getting blinded.");
                return;
            }

            if (chanceEnchant(player, "Immunity")) {
                player.sendMessage("§6Your immunity has prevented you from being debuffed.");
                return;
            }

            if (InventoryUtil.hasCustomArmorEnchant(player, "Thunder God")) {
                player.sendMessage("§6Your thunder god has proc.");
                addPotionEffect(player, new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 200, 0));
                addPotionEffect(player, new PotionEffect(PotionEffectType.SPEED, 200, 1));
                return;
            }
        } else if (effect.getType() == PotionEffectType.CONFUSION) {
            if (chanceEnchant(player, "Immunity")) {
                player.sendMessage("§6Your immunity has prevented you from being debuffed.");
                return;
            }
        } else if (effect.getType() == PotionEffectType.HUNGER) {
            if (Pet.isSummoned(player, Pet.ZOMBIE)) {
                player.sendMessage("§6Your zombie pet has prevented you from losing hunger.");
                return;
            }

            if (chanceEnchant(player, "Immunity")) {
                player.sendMessage("§6Your immunity has prevented you from being debuffed.");
                return;
            }
        } else if (effect.getType() == PotionEffectType.LEVITATION) {
            if (chanceEnchant(player, "Immunity")) {
                player.sendMessage("§6Your immunity has prevented you from being debuffed.");
                return;
            }
        } else if (effect.getType() == PotionEffectType.POISON) {
            if (chanceEnchant(player, "Immunity")) {
                player.sendMessage("§6Your immunity has prevented you from being debuffed.");
                return;
            }
        } else if (effect.getType() == PotionEffectType.SLOW) {
            if (Pet.isSummoned(player, Pet.POLAR_BEAR)) {
                player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 200, 0));
                player.sendMessage("§6Your polar bear pet has given you resistance 1.");
            } else if (Pet.isSummoned(player, Pet.SLIME)) {
                player.sendMessage("§6Your slime pet has prevented you from being slowed.");
                return;
            }

            if (InventoryUtil.hasCustomArmorEnchant(player, "Unstoppable")) {
                player.sendMessage("§6Your unstoppable has prevented you from getting slowed.");
                return;
            }

            if (chanceEnchant(player, "Immunity")) {
                player.sendMessage("§6Your immunity has prevented you from being debuffed.");
                return;
            }
        } else if (effect.getType() == PotionEffectType.SLOW_DIGGING) {
            if (chanceEnchant(player, "Immunity")) {
                player.sendMessage("§6Your immunity has prevented you from being debuffed.");
                return;
            }
        } else if (effect.getType() == PotionEffectType.WEAKNESS) {
            if (chanceEnchant(player, "Immunity")) {
                player.sendMessage("§6Your immunity has prevented you from being debuffed.");
                return;
            }
        } else if (effect.getType() == PotionEffectType.WITHER) {
            if (chanceEnchant(player, "Immunity")) {
                player.sendMessage("§6Your immunity has prevented you from being debuffed.");
                return;
            }
        }
        if (!nullified.contains(player.getUniqueId()))
            player.addPotionEffect(effect);
    }

    public static boolean chanceEnchant(Player player, String enchant) {
        if (enchant.equalsIgnoreCase("Immunity")) {
            if (InventoryUtil.hasCustomArmorEnchant(player, enchant)) {
                switch (InventoryUtil.getArmorEnchantLevel(player, enchant)) {
                    case 1:
                        if (Core.chance(25)) {
                            player.sendMessage("§6Your immunity has prevented you from getting debuffed.");
                            return true;
                        }
                        break;
                    case 2:
                        if (Core.chance(30)) {
                            player.sendMessage("§6Your immunity has prevented you from getting debuffed.");
                            return true;
                        }
                        break;
                    case 3:
                        if (Core.chance(40)) {
                            player.sendMessage("§6Your immunity has prevented you from getting debuffed.");
                            return true;
                        }
                        break;
                }
            }
        } else if (enchant.equalsIgnoreCase("Disorder")) {
            if (InventoryUtil.hasCustomArmorEnchant(player, enchant)) {
                switch (InventoryUtil.getArmorEnchantLevel(player, enchant)) {
                    case 1:
                        if (Core.chance(1)) {
                            player.sendMessage("§6Your disorder has proc.");
                            return true;
                        }
                        break;
                    case 2:
                        if (Core.chance(1.5)) {
                            player.sendMessage("§6Your disorder has proc.");
                            return true;
                        }
                        break;
                    case 3:
                        if (Core.chance(2.5)) {
                            player.sendMessage("§6Your disorder has proc.");
                            return true;
                        }
                        break;
                }
            }
        } else if (enchant.equalsIgnoreCase("Juggernaut")) {
            if (InventoryUtil.hasCustomArmorEnchant(player, enchant)) {
                switch (InventoryUtil.getArmorEnchantLevel(player, enchant)) {
                    case 1:
                        if (Core.chance(7)) {
                            player.sendMessage("§6Your juggernaut has proc.");
                            return true;
                        }
                        break;
                    case 2:
                        if (Core.chance(8.5)) {
                            player.sendMessage("§6Your juggernaut has proc.");
                            return true;
                        }
                        break;
                    case 3:
                        if (Core.chance(10)) {
                            player.sendMessage("§6Your juggernaut has proc.");
                            return true;
                        }
                        break;
                }
            }
        } else if (enchant.equalsIgnoreCase("Spores")) {
            if (InventoryUtil.hasCustomArmorEnchant(player, enchant)) {
                switch (InventoryUtil.getArmorEnchantLevel(player, enchant)) {
                    case 1:
                        if (Core.chance(5)) {
                            player.sendMessage("§6Your spores has proc.");
                            return true;
                        }
                        break;
                    case 2:
                        if (Core.chance(6)) {
                            player.sendMessage("§6Your spores has proc.");
                            return true;
                        }
                        break;
                    case 3:
                        if (Core.chance(7)) {
                            player.sendMessage("§6Your spores has proc.");
                            return true;
                        }
                        break;
                }
            }
        } else if (enchant.equalsIgnoreCase("Lightning Rod")) {
            if (InventoryUtil.hasCustomArmorEnchant(player, enchant)) {
                switch (InventoryUtil.getArmorEnchantLevel(player, enchant)) {
                    case 1:
                        if (Core.chance(5)) {
                            player.sendMessage("§6Your lightning rod has proc.");
                            return true;
                        }
                        break;
                    case 2:
                        if (Core.chance(6)) {
                            player.sendMessage("§6Your lightning rod has proc.");
                            return true;
                        }
                        break;
                    case 3:
                        if (Core.chance(7)) {
                            player.sendMessage("§6Your lightning rod has proc.");
                            return true;
                        }
                        break;
                }
            }
        } else if (enchant.equalsIgnoreCase("Molten Armor")) {
            if (InventoryUtil.hasCustomArmorEnchant(player, "Molten Armor")) {
                switch (InventoryUtil.getArmorEnchantLevel(player, enchant)) {
                    case 1:
                        if (Core.chance(10)) {
                            return true;
                        }
                        break;
                    case 2:
                        if (Core.chance(12.5)) {
                            return true;
                        }
                        break;
                    case 3:
                        if (Core.chance(15)) {
                            return true;
                        }
                        break;
                }
            }
        } else if (enchant.equalsIgnoreCase("Dodge")) {
            if (InventoryUtil.hasCustomArmorEnchant(player, enchant)) {
                switch (InventoryUtil.getArmorEnchantLevel(player, enchant)) {
                    case 1:
                        if (Core.chance(7.5)) {
                            return true;
                        }
                        break;
                    case 2:
                        if (Core.chance(8.5)) {
                            return true;
                        }
                        break;
                    case 3:
                        if (Core.chance(10)) {
                            return true;
                        }
                        break;
                }
            }
        } else if (enchant.equalsIgnoreCase("Sticky Armor")) {
            if (InventoryUtil.hasCustomArmorEnchant(player, enchant)) {
                switch (InventoryUtil.getArmorEnchantLevel(player, enchant)) {
                    case 1:
                        if (Core.chance(7.5)) {
                            return true;
                        }
                        break;
                    case 2:
                        if (Core.chance(8.5)) {
                            return true;
                        }
                        break;
                    case 3:
                        if (Core.chance(10)) {
                            return true;
                        }
                        break;
                }
            }
        } else if (enchant.equalsIgnoreCase("Reflect")) {
            if (InventoryUtil.hasCustomArmorEnchant(player, enchant)) {
                switch (InventoryUtil.getArmorEnchantLevel(player, enchant)) {
                    case 1:
                        if (Core.chance(5)) {
                            return true;
                        }
                        break;
                    case 2:
                        if (Core.chance(6.5)) {
                            return true;
                        }
                        break;
                    case 3:
                        if (Core.chance(8)) {
                            return true;
                        }
                        break;
                }
            }
        } else if (enchant.equalsIgnoreCase("Kite")) {
            if (InventoryUtil.hasCustomArmorEnchant(player, enchant)) {
                switch (InventoryUtil.getArmorEnchantLevel(player, enchant)) {
                    case 1:
                        if (Core.chance(10)) {
                            return true;
                        }
                        break;
                    case 2:
                        if (Core.chance(10)) {
                            return true;
                        }
                        break;
                    case 3:
                        if (Core.chance(15)) {
                            return true;
                        }
                        break;
                }
            }
        } else if (enchant.equalsIgnoreCase("Scatter")) {
            if (InventoryUtil.hasCustomArmorEnchant(player, enchant)) {
                switch (InventoryUtil.getArmorEnchantLevel(player, enchant)) {
                    case 1:
                        if (Core.chance(4.5)) {
                            player.sendMessage("§6Your scatter has proc.");
                            return true;
                        }
                        break;
                    case 2:
                        if (Core.chance(5)) {
                            player.sendMessage("§6Your scatter has proc.");
                            return true;
                        }
                        break;
                    case 3:
                        if (Core.chance(6)) {
                            player.sendMessage("§6Your scatter has proc.");
                            return true;
                        }
                        break;
                }
            }
        } else if (enchant.equalsIgnoreCase("Undead")) {
            if (InventoryUtil.hasCustomArmorEnchant(player, enchant)) {
                switch (InventoryUtil.getArmorEnchantLevel(player, enchant)) {
                    case 1:
                        if (Core.chance(3)) {
                            return true;
                        }
                        break;
                    case 2:
                        if (Core.chance(3.5)) {
                            return true;
                        }
                        break;
                    case 3:
                        if (Core.chance(4)) {
                            return true;
                        }
                        break;
                }
            }
        } else if (enchant.equalsIgnoreCase("Glare")) {
            if (InventoryUtil.hasCustomSwordEnchant(player, enchant)) {
                switch (InventoryUtil.getSwordEnchantLevel(player, enchant)) {
                    case 1:
                        if (Core.chance(1.5)) {
                            player.sendMessage("§6Your glare has proc.");
                            return true;
                        }
                        break;
                    case 2:
                        if (Core.chance(2)) {
                            player.sendMessage("§6Your glare has proc.");
                            return true;
                        }
                        break;
                    case 3:
                        if (Core.chance(2.5)) {
                            player.sendMessage("§6Your glare has proc.");
                            return true;
                        }
                        break;
                }
            }
        } else if (enchant.equalsIgnoreCase("Dragon Wrath")) {
            if (InventoryUtil.hasCustomSwordEnchant(player, enchant)) {
                switch (InventoryUtil.getSwordEnchantLevel(player, enchant)) {
                    case 1:
                        if (Core.chance(6.5)) {
                            player.sendMessage("§6Your dragon wrath has proc.");
                            return true;
                        }
                        break;
                    case 2:
                        if (Core.chance(7)) {
                            player.sendMessage("§6Your dragon wrath has proc.");
                            return true;
                        }
                        break;
                    case 3:
                        if (Core.chance(7.5)) {
                            player.sendMessage("§6Your dragon wrath has proc.");
                            return true;
                        }
                        break;
                }
            }
        } else if (enchant.equalsIgnoreCase("Wither")) {
            if (InventoryUtil.hasCustomSwordEnchant(player, enchant)) {
                switch (InventoryUtil.getSwordEnchantLevel(player, enchant)) {
                    case 1:
                        if (Core.chance(7.5)) {
                            player.sendMessage("§6Your wither has proc.");
                            return true;
                        }
                        break;
                    case 2:
                        if (Core.chance(8.5)) {
                            player.sendMessage("§6Your wither has proc.");
                            return true;
                        }
                        break;
                    case 3:
                        if (Core.chance(10)) {
                            player.sendMessage("§6Your wither has proc.");
                            return true;
                        }
                        break;
                }
            }
        } else if (enchant.equalsIgnoreCase("Smite")) {
            if (InventoryUtil.hasCustomSwordEnchant(player, enchant)) {
                switch (InventoryUtil.getSwordEnchantLevel(player, enchant)) {
                    case 1:
                        if (Core.chance(20)) {
                            return true;
                        }
                        break;
                    case 2:
                        if (Core.chance(22.5)) {
                            return true;
                        }
                        break;
                    case 3:
                        if (Core.chance(25)) {
                            return true;
                        }
                        break;
                }
            }
        } else if (enchant.equalsIgnoreCase("Strip")) {
            if (InventoryUtil.hasCustomSwordEnchant(player, enchant)) {
                switch (InventoryUtil.getSwordEnchantLevel(player, enchant)) {
                    case 1:
                        if (Core.chance(.5)) {
                            player.sendMessage("§6Your strip has proc.");
                            return true;
                        }
                        break;
                    case 2:
                        if (Core.chance(.75)) {
                            player.sendMessage("§6Your strip has proc.");
                            return true;
                        }
                        break;
                    case 3:
                        if (Core.chance(1)) {
                            player.sendMessage("§6Your strip has proc.");
                            return true;
                        }
                        break;
                }
            }
        } else if (enchant.equalsIgnoreCase("Ignite")) {
            if (InventoryUtil.hasCustomSwordEnchant(player, enchant)) {
                switch (InventoryUtil.getSwordEnchantLevel(player, enchant)) {
                    case 1:
                        if (Core.chance(3)) {
                            player.sendMessage("§6Your ignite has proc.");
                            return true;
                        }
                        break;
                    case 2:
                        if (Core.chance(3.5)) {
                            player.sendMessage("§6Your ignite has proc.");
                            return true;
                        }
                        break;
                    case 3:
                        if (Core.chance(4)) {
                            player.sendMessage("§6Your ignite has proc.");
                            return true;
                        }
                        break;
                }
            }
        } else if (enchant.equalsIgnoreCase("Multishot")) {
            if (InventoryUtil.hasCustomSwordEnchant(player, enchant)) {
                switch (InventoryUtil.getSwordEnchantLevel(player, enchant)) {
                    case 1:
                        if (Core.chance(30)) {
                            return true;
                        }
                        break;
                    case 2:
                        if (Core.chance(40)) {
                            return true;
                        }
                        break;
                    case 3:
                        if (Core.chance(50)) {
                            return true;
                        }
                        break;
                }
            }
        } else if (enchant.equalsIgnoreCase("Lifesteal")) {
            if (InventoryUtil.hasCustomSwordEnchant(player, enchant)) {
                switch (InventoryUtil.getSwordEnchantLevel(player, enchant)) {
                    case 1:
                        if (Core.chance(5)) {
                            player.sendMessage("§6Your lifesteal has proc.");
                            return true;
                        }
                        break;
                    case 2:
                        if (Core.chance(6)) {
                            player.sendMessage("§6Your lifesteal has proc.");
                            return true;
                        }
                        break;
                    case 3:
                        if (Core.chance(7)) {
                            player.sendMessage("§6Your lifesteal has proc.");
                            return true;
                        }
                        break;
                }
            }
        } else if (enchant.equalsIgnoreCase("Blinding Strike")) {
            if (InventoryUtil.hasCustomSwordEnchant(player, enchant)) {
                switch (InventoryUtil.getSwordEnchantLevel(player, enchant)) {
                    case 1:
                        if (Core.chance(3)) {
                            player.sendMessage("§6Your blinding strike has proc.");
                            return true;
                        }
                        break;
                    case 2:
                        if (Core.chance(3.5)) {
                            player.sendMessage("§6Your blinding strike has proc.");
                            return true;
                        }
                        break;
                    case 3:
                        if (Core.chance(4)) {
                            player.sendMessage("§6Your blinding strike has proc.");
                            return true;
                        }
                        break;
                }
            }
        } else if (enchant.equalsIgnoreCase("Double Hit")) {
            if (InventoryUtil.hasCustomSwordEnchant(player, enchant)) {
                switch (InventoryUtil.getSwordEnchantLevel(player, enchant)) {
                    case 1:
                        if (Core.chance(8)) {
                            player.sendMessage("§6Your double hit has proc.");
                            return true;
                        }
                        break;
                    case 2:
                        if (Core.chance(9)) {
                            player.sendMessage("§6Your double hit has proc.");
                            return true;
                        }
                        break;
                    case 3:
                        if (Core.chance(10)) {
                            player.sendMessage("§6Your double hit has proc.");
                            return true;
                        }
                        break;
                }
            }
        } else if (enchant.equalsIgnoreCase("Disarm")) {
            if (InventoryUtil.hasCustomSwordEnchant(player, enchant)) {
                switch (InventoryUtil.getSwordEnchantLevel(player, enchant)) {
                    case 1:
                        if (Core.chance(4)) {
                            player.sendMessage("§6Your disarm has proc.");
                            return true;
                        }
                        break;
                    case 2:
                        if (Core.chance(4.5)) {
                            player.sendMessage("§6Your disarm has proc.");
                            return true;
                        }
                        break;
                    case 3:
                        if (Core.chance(5)) {
                            player.sendMessage("§6Your disarm has proc.");
                            return true;
                        }
                        break;
                }
            }
        } else if (enchant.equalsIgnoreCase("Paralyze")) {
            if (InventoryUtil.hasCustomSwordEnchant(player, enchant)) {
                switch (InventoryUtil.getSwordEnchantLevel(player, enchant)) {
                    case 1:
                        if (Core.chance(5)) {
                            player.sendMessage("§6Your paralyze has proc.");
                            return true;
                        }
                        break;
                    case 2:
                        if (Core.chance(6)) {
                            player.sendMessage("§6Your paralyze has proc.");
                            return true;
                        }
                        break;
                    case 3:
                        if (Core.chance(7.5)) {
                            player.sendMessage("§6Your paralyze has proc.");
                            return true;
                        }
                        break;
                }
            }
        } else if (enchant.equalsIgnoreCase("Piercing")) {
            if (InventoryUtil.hasCustomSwordEnchant(player, enchant)) {
                switch (InventoryUtil.getSwordEnchantLevel(player, enchant)) {
                    case 1:
                        if (Core.chance(6)) {
                            player.sendMessage("§6Your piercing has proc.");
                            return true;
                        }
                        break;
                    case 2:
                        if (Core.chance(7)) {
                            player.sendMessage("§6Your piercing has proc.");
                            return true;
                        }
                        break;
                    case 3:
                        if (Core.chance(8)) {
                            player.sendMessage("§6Your piercing has proc.");
                            return true;
                        }
                        break;
                }
            }
        } else if (enchant.equalsIgnoreCase("Nausea")) {
            if (InventoryUtil.hasCustomSwordEnchant(player, enchant)) {
                switch (InventoryUtil.getSwordEnchantLevel(player, enchant)) {
                    case 1:
                        if (Core.chance(4.5)) {
                            player.sendMessage("§6Your nausea has proc.");
                            return true;
                        }
                        break;
                    case 2:
                        if (Core.chance(5)) {
                            player.sendMessage("§6Your nausea has proc.");
                            return true;
                        }
                        break;
                    case 3:
                        if (Core.chance(5.5)) {
                            player.sendMessage("§6Your nausea has proc.");
                            return true;
                        }
                        break;
                }
            }
        } else if (enchant.equalsIgnoreCase("Famine")) {
            if (InventoryUtil.hasCustomSwordEnchant(player, enchant)) {
                switch (InventoryUtil.getSwordEnchantLevel(player, enchant)) {
                    case 1:
                        if (Core.chance(3)) {
                            player.sendMessage("§6Your famine has proc.");
                            return true;
                        }
                        break;
                    case 2:
                        if (Core.chance(3.5)) {
                            player.sendMessage("§6Your famine has proc.");
                            return true;
                        }
                        break;
                    case 3:
                        if (Core.chance(4)) {
                            player.sendMessage("§6Your famine has proc.");
                            return true;
                        }
                        break;
                }
            }
        } else if (enchant.equalsIgnoreCase("Knockup")) {
            if (InventoryUtil.hasCustomSwordEnchant(player, enchant)) {
                switch (InventoryUtil.getSwordEnchantLevel(player, enchant)) {
                    case 1:
                        if (Core.chance(3)) {
                            player.sendMessage("§6Your knockup has proc.");
                            return true;
                        }
                        break;
                    case 2:
                        if (Core.chance(3.5)) {
                            player.sendMessage("§6Your knockup has proc.");
                            return true;
                        }
                        break;
                    case 3:
                        if (Core.chance(4)) {
                            player.sendMessage("§6Your knockup has proc.");
                            return true;
                        }
                        break;
                }
            }
        } else if (enchant.equalsIgnoreCase("Ice Aspect")) {
            if (InventoryUtil.hasCustomSwordEnchant(player, enchant)) {
                switch (InventoryUtil.getSwordEnchantLevel(player, enchant)) {
                    case 1:
                        if (Core.chance(7.5)) {
                            return true;
                        }
                        break;
                    case 2:
                        if (Core.chance(8.5)) {
                            return true;
                        }
                        break;
                    case 3:
                        if (Core.chance(10)) {
                            return true;
                        }
                        break;
                }
            }
        } else if (enchant.equalsIgnoreCase("Frozen")) {
            if (InventoryUtil.hasCustomSwordEnchant(player, enchant)) {
                switch (InventoryUtil.getSwordEnchantLevel(player, enchant)) {
                    case 1:
                        if (Core.chance(2)) {
                            player.sendMessage("§6Your frozen has proc.");
                            return true;
                        }
                        break;
                    case 2:
                        if (Core.chance(2.5)) {
                            player.sendMessage("§6Your frozen has proc.");
                            return true;
                        }
                        break;
                    case 3:
                        if (Core.chance(3)) {
                            player.sendMessage("§6Your frozen has proc.");
                            return true;
                        }
                        break;
                }
            }
        } else if (enchant.equalsIgnoreCase("Nullify")) {
            if (InventoryUtil.hasCustomSwordEnchant(player, enchant)) {
                switch (InventoryUtil.getSwordEnchantLevel(player, enchant)) {
                    case 1:
                        if (Core.chance(3)) {
                            player.sendMessage("§6Your nullify has proc.");
                            return true;
                        }
                        break;
                    case 2:
                        if (Core.chance(3.5)) {
                            player.sendMessage("§6Your nullify has proc.");
                            return true;
                        }
                        break;
                    case 3:
                        if (Core.chance(4)) {
                            player.sendMessage("§6Your nullify has proc.");
                            return true;
                        }
                        break;
                }
            }
        }
        return false;
    }

    public static List<UUID> berserkCD = new ArrayList<>();
    public static List<UUID> blindinglightCD = new ArrayList<>();
    public static List<UUID> seismicescapeCD = new ArrayList<>();
    public static List<UUID> fragileescapeCD = new ArrayList<>();
    public static List<UUID> mircaleCD = new ArrayList<>();
    public static List<UUID> demolishCD = new ArrayList<>();
    public static List<UUID> thunderCD = new ArrayList<>();
    public static List<UUID> freezeCD = new ArrayList<>();
    public static List<UUID> trapCD = new ArrayList<>();

    public static void checkLowEnchant(Player player) {
        if (InventoryUtil.hasCustomArmorEnchant(player, "Berserk")) {
            if (berserkCD.contains(player.getUniqueId())) return;
            switch (InventoryUtil.getArmorEnchantLevel(player, "Berserk")) {
                case 1:
                    if (player.getHealth() <= 3) {
                        addPotionEffect(player, new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 200, 0));
                        player.sendMessage("§6Your berserk has proc.");
                        berserkCD.add(player.getUniqueId());
                        new BukkitRunnable() {
                            public void run() {
                                berserkCD.remove(player.getUniqueId());
                            }
                        }.runTaskLater(Core.instance, 3600L);
                    }
                    break;
                case 2:
                    if (player.getHealth() <= 5) {
                        addPotionEffect(player, new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 200, 0));
                        player.sendMessage("§6Your berserk has proc.");
                        berserkCD.add(player.getUniqueId());
                        new BukkitRunnable() {
                            public void run() {
                                berserkCD.remove(player.getUniqueId());
                            }
                        }.runTaskLater(Core.instance, 3600L);
                    }
                    break;
                case 3:
                    if (player.getHealth() <= 6) {
                        addPotionEffect(player, new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 300, 0));
                        player.sendMessage("§6Your berserk has proc.");
                        berserkCD.add(player.getUniqueId());
                        new BukkitRunnable() {
                            public void run() {
                                berserkCD.remove(player.getUniqueId());
                            }
                        }.runTaskLater(Core.instance, 3600L);
                    }
                    break;
            }
        }
        if (InventoryUtil.hasCustomArmorEnchant(player, "Blinding Light")) {
            if (blindinglightCD.contains(player.getUniqueId())) return;
            switch (InventoryUtil.getArmorEnchantLevel(player, "Blinding Light")) {
                case 1:
                    if (player.getHealth() <= 3) {
                        for (Entity enemy : player.getNearbyEntities(2, 2, 2)) {
                            if (enemy instanceof Player) {
                                if (!Clan.sameClan(player, (Player) enemy))
                                    addPotionEffect((Player) enemy, new PotionEffect(PotionEffectType.BLINDNESS, 200, 0));
                            }
                        }
                        player.sendMessage("§6Your blinding light has proc.");
                        blindinglightCD.add(player.getUniqueId());
                        new BukkitRunnable() {
                            public void run() {
                                blindinglightCD.remove(player.getUniqueId());
                            }
                        }.runTaskLater(Core.instance, 3600L);
                    }
                    break;
                case 2:
                    if (player.getHealth() <= 5) {
                        for (Entity enemy : player.getNearbyEntities(3, 3, 3)) {
                            if (enemy instanceof Player) {
                                if (!Clan.sameClan(player, (Player) enemy))
                                    addPotionEffect((Player) enemy, new PotionEffect(PotionEffectType.BLINDNESS, 250, 0));
                            }
                        }
                        player.sendMessage("§6Your blinding light has proc.");
                        blindinglightCD.add(player.getUniqueId());
                        new BukkitRunnable() {
                            public void run() {
                                blindinglightCD.remove(player.getUniqueId());
                            }
                        }.runTaskLater(Core.instance, 3600L);
                    }
                    break;
                case 3:
                    if (player.getHealth() <= 6) {
                        for (Entity enemy : player.getNearbyEntities(4, 4, 4)) {
                            if (enemy instanceof Player) {
                                if (!Clan.sameClan(player, (Player) enemy))
                                    addPotionEffect((Player) enemy, new PotionEffect(PotionEffectType.BLINDNESS, 300, 0));
                            }
                        }
                        player.sendMessage("§6Your blinding light has proc.");
                        blindinglightCD.add(player.getUniqueId());
                        new BukkitRunnable() {
                            public void run() {
                                blindinglightCD.remove(player.getUniqueId());
                            }
                        }.runTaskLater(Core.instance, 3600L);
                    }
                    break;
            }
        }
        if (InventoryUtil.hasCustomArmorEnchant(player, "Seismic Escape")) {
            if (seismicescapeCD.contains(player.getUniqueId())) return;
            switch (InventoryUtil.getArmorEnchantLevel(player, "Seismic Escape")) {
                case 1:
                    if (player.getHealth() <= 3) {
                        for (Entity enemy : player.getNearbyEntities(3, 3, 3)) {
                            if (enemy instanceof Player) {
                                if (!Clan.sameClan(player, (Player) enemy)) {
                                    addPotionEffect((Player) enemy, new PotionEffect(PotionEffectType.SLOW, 200, 0));
                                    enemy.setVelocity(enemy.getLocation().getDirection().normalize().multiply(-2.5));
                                }
                            }
                        }
                        player.sendMessage("§6Your seismic escape has proc.");
                        seismicescapeCD.add(player.getUniqueId());
                        new BukkitRunnable() {
                            public void run() {
                                seismicescapeCD.remove(player.getUniqueId());
                            }
                        }.runTaskLater(Core.instance, 3600L);
                    }
                    break;
                case 2:
                    if (player.getHealth() <= 4) {
                        for (Entity enemy : player.getNearbyEntities(3, 3, 3)) {
                            if (enemy instanceof Player) {
                                if (!Clan.sameClan(player, (Player) enemy)) {
                                    addPotionEffect((Player) enemy, new PotionEffect(PotionEffectType.SLOW, 200, 1));
                                    enemy.setVelocity(enemy.getLocation().getDirection().normalize().multiply(-2.5));
                                }
                            }
                        }
                        player.sendMessage("§6Your seismic escape has proc.");
                        seismicescapeCD.add(player.getUniqueId());
                        new BukkitRunnable() {
                            public void run() {
                                seismicescapeCD.remove(player.getUniqueId());
                            }
                        }.runTaskLater(Core.instance, 3600L);
                    }
                    break;
                case 3:
                    if (player.getHealth() <= 6) {
                        for (Entity enemy : player.getNearbyEntities(4, 4, 4)) {
                            if (enemy instanceof Player) {
                                if (!Clan.sameClan(player, (Player) enemy)) {
                                    addPotionEffect((Player) enemy, new PotionEffect(PotionEffectType.SLOW, 300, 1));
                                    enemy.setVelocity(enemy.getLocation().getDirection().normalize().multiply(-3));
                                }
                            }
                        }
                        player.sendMessage("§6Your seismic escape has proc.");
                        seismicescapeCD.add(player.getUniqueId());
                        new BukkitRunnable() {
                            public void run() {
                                seismicescapeCD.remove(player.getUniqueId());
                            }
                        }.runTaskLater(Core.instance, 3600L);
                    }
                    break;
            }
        } else if (InventoryUtil.hasCustomArmorEnchant(player, "Fragile Escape")) {
            if (fragileescapeCD.contains(player.getUniqueId())) return;
            switch (InventoryUtil.getArmorEnchantLevel(player, "Fragile Escape")) {
                case 1:
                    if (player.getHealth() <= 3) {
                        for (Entity enemy : player.getNearbyEntities(3, 3, 3)) {
                            if (enemy instanceof Player) {
                                if (!Clan.sameClan(player, (Player) enemy)) {
                                    addPotionEffect((Player) enemy, new PotionEffect(PotionEffectType.WEAKNESS, 200, 0));
                                }
                            }
                        }
                        player.sendMessage("§6Your fragile escape has proc.");
                        fragileescapeCD.add(player.getUniqueId());
                        new BukkitRunnable() {
                            public void run() {
                                fragileescapeCD.remove(player.getUniqueId());
                            }
                        }.runTaskLater(Core.instance, 3600L);
                    }
                    break;
                case 2:
                    if (player.getHealth() <= 4) {
                        for (Entity enemy : player.getNearbyEntities(3, 3, 3)) {
                            if (enemy instanceof Player) {
                                if (!Clan.sameClan(player, (Player) enemy)) {
                                    addPotionEffect((Player) enemy, new PotionEffect(PotionEffectType.WEAKNESS, 250, 1));
                                }
                            }
                        }
                        player.sendMessage("§6Your fragile escape has proc.");
                        fragileescapeCD.add(player.getUniqueId());
                        new BukkitRunnable() {
                            public void run() {
                                fragileescapeCD.remove(player.getUniqueId());
                            }
                        }.runTaskLater(Core.instance, 3600L);
                    }
                    break;
                case 3:
                    if (player.getHealth() <= 6) {
                        for (Entity enemy : player.getNearbyEntities(4, 4, 4)) {
                            if (enemy instanceof Player) {
                                if (!Clan.sameClan(player, (Player) enemy)) {
                                    addPotionEffect((Player) enemy, new PotionEffect(PotionEffectType.WEAKNESS, 300, 1));
                                }
                            }
                        }
                        player.sendMessage("§6Your fragile escape has proc.");
                        fragileescapeCD.add(player.getUniqueId());
                        new BukkitRunnable() {
                            public void run() {
                                fragileescapeCD.remove(player.getUniqueId());
                            }
                        }.runTaskLater(Core.instance, 3600L);
                    }
                    break;
            }
        }
        if (InventoryUtil.hasCustomArmorEnchant(player, "Miracle")) {
            if (mircaleCD.contains(player.getUniqueId())) return;
            switch (InventoryUtil.getArmorEnchantLevel(player, "Miracle")) {
                case 1:
                    if (player.getHealth() <= 3) {
                        addPotionEffect(player, new PotionEffect(PotionEffectType.REGENERATION, 200, 0));
                        addPotionEffect(player, new PotionEffect(PotionEffectType.ABSORPTION, 200, 0));
                        player.sendMessage("§6Your miracle has proc.");
                        mircaleCD.add(player.getUniqueId());
                        new BukkitRunnable() {
                            public void run() {
                                mircaleCD.remove(player.getUniqueId());
                            }
                        }.runTaskLater(Core.instance, 3600L);
                    }
                    break;
                case 2:
                    if (player.getHealth() <= 4) {
                        addPotionEffect(player, new PotionEffect(PotionEffectType.REGENERATION, 200, 1));
                        addPotionEffect(player, new PotionEffect(PotionEffectType.ABSORPTION, 200, 1));
                        player.sendMessage("§6Your miracle has proc.");
                        mircaleCD.add(player.getUniqueId());
                        new BukkitRunnable() {
                            public void run() {
                                mircaleCD.remove(player.getUniqueId());
                            }
                        }.runTaskLater(Core.instance, 3600L);
                    }
                    break;
                case 3:
                    if (player.getHealth() <= 5) {
                        addPotionEffect(player, new PotionEffect(PotionEffectType.REGENERATION, 200, 1));
                        addPotionEffect(player, new PotionEffect(PotionEffectType.ABSORPTION, 200, 2));
                        player.sendMessage("§6Your miracle has proc.");
                        mircaleCD.add(player.getUniqueId());
                        new BukkitRunnable() {
                            public void run() {
                                mircaleCD.remove(player.getUniqueId());
                            }
                        }.runTaskLater(Core.instance, 3600L);
                    }
                    break;
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onDamageEnchant(EntityDamageByEntityEvent e) {
        if (e.getEntity() instanceof Player) {
            Player player = (Player) e.getEntity();
            Player damager = null;

            if (e.getDamager() instanceof Player || e.getDamager() instanceof Arrow || e.getDamager() instanceof Zombie) {

                if (e.getDamager() instanceof Player) {
                    damager = (Player) e.getDamager();
                    if (Clan.sameClan(player, damager)) {
                        e.setDamage(0);
                        e.setCancelled(true);
                        return;
                    }
                }

                if (e.getDamager() instanceof Arrow) {
                    if (((Arrow) e.getDamager()).getShooter() instanceof Player) {
                        damager = (Player) ((Arrow) e.getDamager()).getShooter();
                        if (Clan.sameClan(player, damager)) {
                            e.setDamage(0);
                            e.setCancelled(true);
                            return;
                        }
                    }
                }

                if (e.isCancelled()) return;

                if (damager != null) {
                    if (chanceEnchant(player, "Disorder") && damager instanceof Player) {
                        damager = (Player) e.getDamager();
                        List<ItemStack> items = new ArrayList<>();
                        for (int i = 0; i < 9; i++) {
                            if (damager.getInventory().getItem(i) != null) {
                                items.add(damager.getInventory().getItem(i));
                                damager.getInventory().setItem(i, null);
                            }
                        }
                        Collections.shuffle(items);
                        for (ItemStack item : items) {
                            damager.getInventory().addItem(item);
                        }
                        damager.sendMessage("§cYour hotbar has been shuffled.");
                    }

                    if (chanceEnchant(player, "Molten Armor") && damager instanceof Player) {
                        damager.setFireTicks(120);
                    }

                    if (chanceEnchant(player, "Sticky Armor") && damager instanceof Player) {
                        switch (InventoryUtil.getArmorEnchantLevel(player, "Sticky Armor")) {
                            case 1:
                                addPotionEffect(damager, new PotionEffect(PotionEffectType.SLOW, 150, 0));
                                break;
                            case 2:
                                addPotionEffect(damager, new PotionEffect(PotionEffectType.SLOW, 150, 0));
                                break;
                            case 3:
                                addPotionEffect(damager, new PotionEffect(PotionEffectType.SLOW, 200, 0));
                                break;
                        }
                    }

                    if (chanceEnchant(player, "Reflect") && damager instanceof Player) {
                        damager.damage(e.getDamage(), player);
                    }
                }

                checkLowEnchant(player);

                if (chanceEnchant(player, "Dodge")) {
                    e.getDamager().sendMessage("§cYour attack was dodged.");
                    e.setDamage(0);
                    e.setCancelled(true);
                    return;
                }

                if (Pet.isSummoned(player, Pet.CHICKEN)) {
                    if (Core.chance(5)) {
                        e.setDamage(0);
                        e.setCancelled(true);
                        player.sendMessage("§6Your chicken pet dodged the attack.");
                        return;
                    }
                }

                if (chanceEnchant(player, "Juggernaut")) {
                    switch (InventoryUtil.getArmorEnchantLevel(player, "Juggernaut")) {
                        case 1:
                            addPotionEffect(player, new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 100, 0));
                            break;
                        case 2:
                            addPotionEffect(player, new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 100, 0));
                            break;
                        case 3:
                            addPotionEffect(player, new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 150, 0));
                            break;
                    }
                }

                if (chanceEnchant(player, "Kite")) {
                    switch (InventoryUtil.getArmorEnchantLevel(player, "Kite")) {
                        case 1:
                            addPotionEffect(player, new PotionEffect(PotionEffectType.JUMP, 150, 1));
                            break;
                        case 2:
                            addPotionEffect(player, new PotionEffect(PotionEffectType.JUMP, 200, 1));
                            break;
                        case 3:
                            addPotionEffect(player, new PotionEffect(PotionEffectType.JUMP, 250, 1));
                            break;
                    }
                }

                if (chanceEnchant(player, "Scatter")) {
                    switch (InventoryUtil.getArmorEnchantLevel(player, "Scatter")) {
                        case 1:
                            addPotionEffect(player, new PotionEffect(PotionEffectType.SPEED, 150, 1));
                            break;
                        case 2:
                            addPotionEffect(player, new PotionEffect(PotionEffectType.SPEED, 150, 1));
                            break;
                        case 3:
                            addPotionEffect(player, new PotionEffect(PotionEffectType.SPEED, 200, 1));
                            break;
                    }
                }

                if (chanceEnchant(player, "Undead") && damager instanceof Player) {
                    switch (InventoryUtil.getArmorEnchantLevel(player, "Undead")) {
                        case 1:
                            addPotionEffect(damager, new PotionEffect(PotionEffectType.WEAKNESS, 200, 0));
                            break;
                        case 2:
                            addPotionEffect(damager, new PotionEffect(PotionEffectType.WEAKNESS, 250, 0));
                            break;
                        case 3:
                            addPotionEffect(damager, new PotionEffect(PotionEffectType.WEAKNESS, 300, 0));
                            break;
                    }
                }

                if (Pet.isSummoned(player, Pet.DRAGON)) {
                    if (Core.chance(2.5)) {
                        addPotionEffect(player, new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 150, 1));
                        player.sendMessage("§6Your dragon pet gave you strength 2.");
                    }
                }

                if (Pet.isSummoned(player, Pet.SKELETON_HORSE)) {
                    if (Core.chance(4)) {
                        addPotionEffect(player, new PotionEffect(PotionEffectType.SPEED, 200, 1));
                        player.sendMessage("§6Your skeleton horse pet gave you speed 2.");
                    }
                }

                if (Pet.isSummoned(player, Pet.GOLEM)) {
                    if (Core.chance(4)) {
                        addPotionEffect(player, new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 200, 1));
                        player.sendMessage("§6Your golem pet gave you resistance 2.");
                    }
                }

                if (Pet.isSummoned(player, Pet.MOOSHROOM)) {
                    if (Core.chance(3.5)) {
                        addPotionEffect(player, new PotionEffect(PotionEffectType.REGENERATION, 60, 3));
                        player.sendMessage("§6Your mooshroom pet gave you regeneration 4.");
                    }
                }

                if (Pet.isSummoned(player, Pet.PIGMAN)) {
                    if (Core.chance(4)) {
                        addPotionEffect(player, new PotionEffect(PotionEffectType.SPEED, 200, 0));
                        addPotionEffect(player, new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 200, 0));
                        player.sendMessage("§6Your pigman pet gave you speed and strength 1.");
                    }
                }

                if (Pet.isSummoned(player, Pet.WOLF)) {
                    if (Core.chance(10)) {
                        Wolf wolf = (Wolf) player.getWorld().spawnEntity(player.getLocation(), EntityType.WOLF);
                        wolf.setOwner(player);
                        wolf.setAngry(true);
                        wolf.setMaxHealth(100);
                        wolf.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, Integer.MAX_VALUE, 1));
                        wolf.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, Integer.MAX_VALUE, 1));
                        wolf.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 1));
                        player.sendMessage("§6Your wolf pet spawned a wolf.");
                    }
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEnchantDamage(EntityDamageByEntityEvent e) {
        if (e.getEntity() instanceof Player) {

            Player player = (Player) e.getEntity();

            if (e.getDamager() instanceof Player) {
                Player damager = (Player) e.getDamager();
                if (Clan.sameClan(player, damager)) {
                    e.setDamage(0);
                    e.setCancelled(true);
                    return;
                }
            }

            if (e.isCancelled()) return;
        }

        if (e.getDamager() instanceof Player && e.getEntity() instanceof Player) {
            Player player = (Player) e.getDamager();
            Player enemy = (Player) e.getEntity();

            if (enemy.hasPotionEffect(PotionEffectType.INVISIBILITY))
                enemy.removePotionEffect(PotionEffectType.INVISIBILITY);

            if (chanceEnchant(player, "Glare")) {
                switch (InventoryUtil.getSwordEnchantLevel(player, "Glare")) {
                    case 1:
                        freeze(enemy, 3);
                        addPotionEffect(enemy, new PotionEffect(PotionEffectType.BLINDNESS, 3 * 20, 0));
                        break;
                    case 2:
                        freeze(enemy, 3.5);
                        addPotionEffect(enemy, new PotionEffect(PotionEffectType.BLINDNESS, 70, 0));
                        break;
                    case 3:
                        freeze(enemy, 4);
                        addPotionEffect(enemy, new PotionEffect(PotionEffectType.BLINDNESS, 4 * 20, 0));
                        break;
                }
                enemy.sendMessage("§cYou've been glared.");
            }

            if (chanceEnchant(player, "Dragon Wrath")) {
                switch (InventoryUtil.getSwordEnchantLevel(player, "Dragon Wrath")) {
                    case 1:
                        e.setDamage(e.getDamage() + 6);
                        break;
                    case 2:
                        e.setDamage(e.getDamage() + 7);
                        break;
                    case 3:
                        e.setDamage(e.getDamage() + 8);
                        break;
                }
            }

            if (InventoryUtil.hasCustomSwordEnchant(player, "Fire Aspect")) {
                switch (InventoryUtil.getSwordEnchantLevel(player, "Fire Aspect")) {
                    case 1:
                        if (enemy.getFireTicks() > 0) e.setDamage(e.getDamage() + 2.5);
                        break;
                    case 2:
                        if (enemy.getFireTicks() > 0) e.setDamage(e.getDamage() + 3);
                        break;
                    case 3:
                        if (enemy.getFireTicks() > 0) e.setDamage(e.getDamage() + 3.5);
                        break;
                }
            }

            if (chanceEnchant(player, "Ignite")) {
                if (enemy.hasPotionEffect(PotionEffectType.FIRE_RESISTANCE)) {
                    enemy.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
                    enemy.setFireTicks(100);
                } else enemy.setFireTicks(100);
                enemy.sendMessage("§cYou were ignited.");
            }

            if (chanceEnchant(player, "Strip")) {
                int rng = Core.generateNumber(0, 100);
                if (rng < 50) {
                    if (enemy.getInventory().getHelmet() == null) return;
                    ItemStack armor = enemy.getInventory().getHelmet();
                    enemy.getInventory().setItem(EquipmentSlot.HEAD, null);
                    for (ItemStack i : enemy.getInventory().addItem(armor).values()) {
                        enemy.getLocation().getWorld().dropItemNaturally(enemy.getLocation(), i);
                    }
                    enemy.sendMessage("§cAn armor piece was stripped.");
                } else {
                    if (enemy.getInventory().getBoots() == null) return;
                    ItemStack armor = enemy.getInventory().getBoots();
                    enemy.getInventory().setItem(EquipmentSlot.FEET, null);
                    for (ItemStack i : enemy.getInventory().addItem(armor).values()) {
                        enemy.getLocation().getWorld().dropItemNaturally(enemy.getLocation(), i);
                    }
                    enemy.sendMessage("§cAn armor piece was stripped.");
                }
            }

            if (InventoryUtil.hasCustomSwordEnchant(player, "Demolish")) {
                if (demolishCD.contains(player.getUniqueId())) {
                    return;
                }
                switch (InventoryUtil.getSwordEnchantLevel(player, "Demolish")) {
                    case 1:
                        freeze(enemy, 2.5);
                        break;
                    case 2:
                        freeze(enemy, 3);
                        break;
                    case 3:
                        freeze(enemy, 3.5);
                        break;
                }
                player.sendMessage("§aYour demolish has proc.");
                demolishCD.add(player.getUniqueId());
                new BukkitRunnable() {
                    public void run() {
                        demolishCD.remove(player.getUniqueId());
                    }
                }.runTaskLater(Core.instance, 2400L);
            }

            if (InventoryUtil.hasCustomSwordEnchant(player, "Rivalry")) {
                if (enemy.getInventory().getItemInMainHand() != null && ItemHelper.hasName(enemy.getInventory().getItemInMainHand())) {
                    if (ItemHelper.getName(enemy.getInventory().getItemInMainHand()).contains("Sword")) {
                        switch (InventoryUtil.getSwordEnchantLevel(player, "Rivalry")) {
                            case 1:
                                e.setDamage(e.getDamage() + 2.5);
                                break;
                            case 2:
                                e.setDamage(e.getDamage() + 3);
                                break;
                            case 3:
                                e.setDamage(e.getDamage() + 3.5);
                                break;
                        }
                    }
                }
            }

            if (InventoryUtil.hasCustomSwordEnchant(player, "Torrent")) {
                if (player.getLocation().getBlock().getType() == Material.WATER) {
                    switch (InventoryUtil.getSwordEnchantLevel(player, "Torrent")) {
                        case 1:
                            e.setDamage(e.getDamage() + 3.5);
                            break;
                        case 2:
                            e.setDamage(e.getDamage() + 4);
                            break;
                        case 3:
                            e.setDamage(e.getDamage() + 4.5);
                            break;
                    }
                }
            }

            if (chanceEnchant(player, "Double Hit")) {
                enemy.damage(4, player);
            }

            if (chanceEnchant(player, "Nullify")) {
                if (!nullified.contains(enemy.getUniqueId())) {
                    nullified.add(enemy.getUniqueId());
                    enemy.sendMessage("§cYou've been nullified.");

                    int delay = 0;
                    switch (InventoryUtil.getSwordEnchantLevel(player, "Nullify")) {
                        case 1:
                            delay = 10;
                            break;
                        case 2:
                            delay = 15;
                            break;
                        case 3:
                            delay = 20;
                            break;
                    }

                    new BukkitRunnable() {
                        public void run() {
                            nullified.remove(enemy.getUniqueId());
                        }
                    }.runTaskLater(Core.instance, delay * 20);
                }
            }

            if (chanceEnchant(player, "Disarm")) {
                enemy.getInventory().setHeldItemSlot(Core.generateNumber(1, 8));
                enemy.sendMessage("§cYour weapon has been disarmed.");
            }

            if (chanceEnchant(player, "Blinding Strike")) {
                switch (InventoryUtil.getSwordEnchantLevel(player, "Blinding Strike")) {
                    case 1:
                        addPotionEffect(enemy, new PotionEffect(PotionEffectType.BLINDNESS, 150, 0));
                        break;
                    case 2:
                        addPotionEffect(enemy, new PotionEffect(PotionEffectType.BLINDNESS, 200, 0));
                        break;
                    case 3:
                        addPotionEffect(enemy, new PotionEffect(PotionEffectType.BLINDNESS, 250, 0));
                        break;
                }
            }

            if (chanceEnchant(player, "Nausea")) {
                switch (InventoryUtil.getSwordEnchantLevel(player, "Nausea")) {
                    case 1:
                        addPotionEffect(enemy, new PotionEffect(PotionEffectType.CONFUSION, 200, 1));
                        break;
                    case 2:
                        addPotionEffect(enemy, new PotionEffect(PotionEffectType.CONFUSION, 250, 1));
                        break;
                    case 3:
                        addPotionEffect(enemy, new PotionEffect(PotionEffectType.CONFUSION, 300, 1));
                        break;
                }
            }

            if (chanceEnchant(player, "Famine")) {
                switch (InventoryUtil.getSwordEnchantLevel(player, "Famine")) {
                    case 1:
                        addPotionEffect(enemy, new PotionEffect(PotionEffectType.HUNGER, 100, 50));
                        break;
                    case 2:
                        addPotionEffect(enemy, new PotionEffect(PotionEffectType.HUNGER, 150, 50));
                        break;
                    case 3:
                        addPotionEffect(enemy, new PotionEffect(PotionEffectType.HUNGER, 200, 50));
                        break;
                }
            }

            if (chanceEnchant(player, "Lifesteal")) {
                double newHealth;
                switch (InventoryUtil.getSwordEnchantLevel(player, "Lifesteal")) {
                    case 1:
                        newHealth = player.getHealth() + 3;
                        if (newHealth <= player.getMaxHealth())
                            player.setHealth(newHealth);
                        break;
                    case 2:
                        newHealth = player.getHealth() + 3.5;
                        if (newHealth <= player.getMaxHealth())
                            player.setHealth(newHealth);
                        break;
                    case 3:
                        newHealth = player.getHealth() + 4;
                        if (newHealth <= player.getMaxHealth())
                            player.setHealth(newHealth);
                        break;
                }
            }

            if (chanceEnchant(player, "Knockup")) {
                addPotionEffect(enemy, new PotionEffect(PotionEffectType.LEVITATION, 10, 20));
            }

            if (chanceEnchant(player, "Ice Aspect")) {
                switch (InventoryUtil.getSwordEnchantLevel(player, "Ice Aspect")) {
                    case 1:
                        addPotionEffect(enemy, new PotionEffect(PotionEffectType.SLOW, 150, 1));
                        break;
                    case 2:
                        addPotionEffect(enemy, new PotionEffect(PotionEffectType.SLOW, 200, 1));
                        break;
                    case 3:
                        addPotionEffect(enemy, new PotionEffect(PotionEffectType.SLOW, 250, 1));
                        break;
                }
            }

            if (chanceEnchant(player, "Frozen")) {
                switch (InventoryUtil.getSwordEnchantLevel(player, "Frozen")) {
                    case 1:
                        freeze(enemy, 2);
                        break;
                    case 2:
                        freeze(enemy, 2.5);
                        break;
                    case 3:
                        freeze(enemy, 3);
                        break;
                }
            }

            if (Pet.isSummoned(player, Pet.SPIDER)) {
                if (Core.chance(6.5)) {
                    addPotionEffect(enemy, new PotionEffect(PotionEffectType.SLOW, 200, 1));
                    player.sendMessage("§6Your spider pet inflicted slowness.");
                }
            }

            if (Pet.isSummoned(player, Pet.BAT)) {
                if (Core.chance(4)) {
                    addPotionEffect(enemy, new PotionEffect(PotionEffectType.BLINDNESS, 200, 0));
                    player.sendMessage("§6Your bat pet inflicted blindness.");
                }
            }

            if (Pet.isSummoned(player, Pet.WITCH)) {
                if (Core.chance(4)) {
                    addPotionEffect(enemy, new PotionEffect(PotionEffectType.POISON, 200, 0));
                    player.sendMessage("§6Your witch pet inflicted poison.");
                }
            }

            if (Pet.isSummoned(player, Pet.MAGMA_CUBE)) {
                if (Core.chance(4)) {
                    if (enemy.hasPotionEffect(PotionEffectType.FIRE_RESISTANCE)) {
                        enemy.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
                        enemy.setFireTicks(100);
                    } else enemy.setFireTicks(100);
                    enemy.sendMessage("§cYou were ignited.");
                    player.sendMessage("§6Your magma cube pet ignited the enemy.");
                }
            }

        }

        if (e.getDamager() instanceof Arrow && e.getEntity() instanceof Player) {
            if (Pet.isSummoned((Player) e.getEntity(), Pet.SKELETON)) {
                if (Core.chance(50)) {
                    e.setDamage(0);
                    e.setCancelled(true);
                    e.getEntity().sendMessage("§6Your skeleton pet dodged the arrow.");
                    return;
                }
            }
        }

        if (e.getDamager() instanceof Arrow && ((Arrow) e.getDamager()).getShooter() instanceof Player && e.getEntity() instanceof Player) {
            Player player = (Player) ((Arrow) e.getDamager()).getShooter();
            Player enemy = (Player) e.getEntity();

            if (Clan.sameClan(player, enemy)) {
                e.setDamage(0);
                e.setCancelled(true);
                return;
            }

            if (Pet.isSummoned(player, Pet.ENDERMAN)) {
                if (Core.chance(20)) {
                    player.teleport(enemy.getLocation());
                    player.sendMessage("§6Your enderman pet teleported you to your target.");
                    return;
                }
            }

            if (chanceEnchant(player, "Wither")) {
                switch (InventoryUtil.getSwordEnchantLevel(player, "Wither")) {
                    case 1:
                        addPotionEffect(enemy, new PotionEffect(PotionEffectType.WITHER, 150, 0));
                        break;
                    case 2:
                        addPotionEffect(enemy, new PotionEffect(PotionEffectType.WITHER, 200, 0));
                        break;
                    case 3:
                        addPotionEffect(enemy, new PotionEffect(PotionEffectType.WITHER, 250, 0));
                        break;
                }
            }

            if (InventoryUtil.hasCustomArmorEnchant(player, "Smite")) {
                enemy.getLocation().getWorld().strikeLightning(enemy.getLocation());
                enemy.damage(10, player);
                enemy.setFireTicks(200);
            }

            if (chanceEnchant(player, "Piercing")) {
                e.setDamage(EntityDamageEvent.DamageModifier.BASE, 8);
                enemy.sendMessage("§6Your armor was pierced.");
            }

            if (InventoryUtil.hasCustomSwordEnchant(player, "Identify")) {
                addPotionEffect(enemy, new PotionEffect(PotionEffectType.GLOWING, 1200, 0));
            }

            if (chanceEnchant(player, "Paralyze")) {
                switch (InventoryUtil.getSwordEnchantLevel(player, "Paralyze")) {
                    case 1:
                        freeze(enemy, 3);
                        break;
                    case 2:
                        freeze(enemy, 3.5);
                        break;
                    case 3:
                        freeze(enemy, 4);
                        break;
                }
            }

            if (InventoryUtil.hasCustomSwordEnchant(player, "Thunder")) {
                if (thunderCD.contains(player.getUniqueId())) {
                    return;
                }
                switch (InventoryUtil.getSwordEnchantLevel(player, "Thunder")) {
                    case 1:
                        enemy.damage(10, player);
                        enemy.setFireTicks(150);
                        enemy.getWorld().strikeLightningEffect(enemy.getLocation());
                        new BukkitRunnable() {
                            public void run() {
                                enemy.damage(10, player);
                                enemy.setFireTicks(150);
                                enemy.getWorld().strikeLightningEffect(enemy.getLocation());
                            }
                        }.runTaskLater(Core.instance, 60L);
                        new BukkitRunnable() {
                            public void run() {
                                enemy.damage(10, player);
                                enemy.setFireTicks(150);
                                enemy.getWorld().strikeLightningEffect(enemy.getLocation());
                            }
                        }.runTaskLater(Core.instance, 120L);
                        break;
                    case 2:
                        enemy.damage(12, player);
                        enemy.setFireTicks(150);
                        enemy.getWorld().strikeLightningEffect(enemy.getLocation());
                        new BukkitRunnable() {
                            public void run() {
                                enemy.damage(12, player);
                                enemy.setFireTicks(150);
                                enemy.getWorld().strikeLightningEffect(enemy.getLocation());
                            }
                        }.runTaskLater(Core.instance, 60L);
                        new BukkitRunnable() {
                            public void run() {
                                enemy.damage(12, player);
                                enemy.setFireTicks(150);
                                enemy.getWorld().strikeLightningEffect(enemy.getLocation());
                            }
                        }.runTaskLater(Core.instance, 120L);
                        break;
                    case 3:
                        enemy.damage(14, player);
                        enemy.setFireTicks(150);
                        enemy.getWorld().strikeLightningEffect(enemy.getLocation());
                        new BukkitRunnable() {
                            public void run() {
                                enemy.damage(14, player);
                                enemy.setFireTicks(150);
                                enemy.getWorld().strikeLightningEffect(enemy.getLocation());
                            }
                        }.runTaskLater(Core.instance, 60L);
                        new BukkitRunnable() {
                            public void run() {
                                enemy.damage(14, player);
                                enemy.setFireTicks(150);
                                enemy.getWorld().strikeLightningEffect(enemy.getLocation());
                            }
                        }.runTaskLater(Core.instance, 120L);
                        break;
                }
                player.sendMessage("§aYour thunder has proc.");
                thunderCD.add(player.getUniqueId());
                new BukkitRunnable() {
                    public void run() {
                        thunderCD.remove(player.getUniqueId());
                    }
                }.runTaskLater(Core.instance, 2400L);
            }

            if (InventoryUtil.hasCustomSwordEnchant(player, "Trap")) {
                if (trapCD.contains(player.getUniqueId())) {
                    return;
                }

                trapped.add(enemy.getUniqueId());
                enemy.sendMessage("§cYou've been trapped!");

                new BukkitRunnable() {
                    public void run() {
                        enemy.sendMessage("§aYou're no longer trapped.");
                        trapCD.remove(player.getUniqueId());
                    }
                }.runTaskLater(Core.instance, 300L);

                player.sendMessage("§aYour trap has proc.");
                trapCD.add(player.getUniqueId());

                new BukkitRunnable() {
                    public void run() {
                        trapCD.remove(player.getUniqueId());
                    }
                }.runTaskLater(Core.instance, 1200L);
            }

            if (Pet.isSummoned(player, Pet.GHAST)) {
                if (Core.chance(10)) {
                    addPotionEffect(enemy, new PotionEffect(PotionEffectType.WITHER, 250, 0));
                    player.sendMessage("§6Your ghast pet inflicted wither to your target.");
                }
            }
        }
    }

    @EventHandler
    public void onMultishot(EntityShootBowEvent e) {
        if (e.getEntity() instanceof Player) {
            Player player = (Player) e.getEntity();
            Vector vector = e.getProjectile().getVelocity();
            if (chanceEnchant(player, "Multishot")) {
                for (int i = 1; i < 5; i++) {
                    new BukkitRunnable() {
                        public void run() {
                            Arrow arrow = player.launchProjectile(Arrow.class);
                            arrow.setVelocity(vector);
                            arrow.setDamage(8);
                            arrow.setPickupStatus(AbstractArrow.PickupStatus.DISALLOWED);
                            arrow.setShooter(player);
                        }
                    }.runTaskLater(Core.instance, 3L * i);
                }
            }
        }
    }

    @EventHandler
    public void onExternalDamage(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player) {
            Player player = (Player) e.getEntity();
            if (InventoryUtil.hasCustomArmorEnchant(player, "Toxic Shield")) {
                if (e.getCause() == EntityDamageEvent.DamageCause.WITHER || e.getCause() == EntityDamageEvent.DamageCause.POISON) {
                    e.setCancelled(true);
                    double newHealth = player.getHealth() + e.getDamage();
                    if (newHealth <= player.getMaxHealth())
                        player.setHealth(newHealth);
                }
            }

            if (InventoryUtil.hasCustomArmorEnchant(player, "Light Feet")) {
                if (e.getCause() == EntityDamageEvent.DamageCause.FALL) {
                    e.setDamage(0);
                    e.setCancelled(true);
                }
            }
        }
    }

    public static List<UUID> trapped = new ArrayList<>();

    @EventHandler(priority = EventPriority.LOWEST)
    public void onEnderpearl(PlayerInteractEvent e) {
        if (e.getItem() != null && e.getHand() == EquipmentSlot.HAND)
            if (e.getItem().getType() == Material.ENDER_PEARL) {
                if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
                    if (trapped.contains(e.getPlayer().getUniqueId())) {
                        e.setCancelled(true);
                        e.getPlayer().sendMessage("§cYou are trapped and can't enderpearl!");
                    }
                }
            }
    }

    @EventHandler
    public void onFreezeMove(PlayerMoveEvent e) {
        if (freezeCD.contains(e.getPlayer().getUniqueId())) {
            Player player = e.getPlayer();
            Double xTo = e.getTo().getX();
            Double xFrom = e.getFrom().getX();
            Double yTo = e.getTo().getY();
            Double yFrom = e.getFrom().getY();
            Double zTo = e.getTo().getZ();
            Double zFrom = e.getFrom().getZ();
            if (e.getTo().locToBlock(xTo) != e.getFrom().locToBlock(xFrom) || e.getTo().locToBlock(zTo) != e.getFrom().locToBlock(zFrom) || e.getTo().locToBlock(yTo) != e.getFrom().locToBlock(yFrom)) {
                e.setCancelled(true);
            }
        }
    }

    public static void freeze(Player player, double time) {
        if (!freezeCD.contains(player.getUniqueId())) {
            if (InventoryUtil.hasCustomArmorEnchant(player, "Unstoppable") || chanceEnchant(player, "Immunity")) {
                return;
            }
            if (InventoryUtil.hasCustomArmorEnchant(player, "Dying Rage")) {
                addPotionEffect(player, new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 300, 1));
            }
            addPotionEffect(player, new PotionEffect(PotionEffectType.SLOW, (int) (time * 20), 128));
            freezeCD.add(player.getUniqueId());
            new BukkitRunnable() {
                public void run() {
                    freezeCD.remove(player.getUniqueId());
                }
            }.runTaskLater(Core.instance, (long) (20 * time));
        }
    }

    public static void runEnchantTimer() {
        //Every second
        new BukkitRunnable() {
            public void run() {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    if (InventoryUtil.hasCustomArmorEnchant(player, "Flame Rage")) {
                        if (player.getFireTicks() > 0)
                            addPotionEffect(player, new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 60, 0));
                    }

                    if (InventoryUtil.hasCustomArmorEnchant(player, "Molten Shield")) {
                        addPotionEffect(player, new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 60, 0));
                        if (player.getFireTicks() > 0) player.setFireTicks(0);
                    }

                    if (InventoryUtil.hasCustomArmorEnchant(player, "Starvation")) {
                        player.setFoodLevel(20);
                    }

                    if (InventoryUtil.hasCustomArmorEnchant(player, "Health") || Pet.isSummoned(player, Pet.GIANT)) {
                        if (!player.hasPotionEffect(PotionEffectType.HEALTH_BOOST))
                            addPotionEffect(player, new PotionEffect(PotionEffectType.HEALTH_BOOST, Integer.MAX_VALUE, 1));
                    } else {
                        if (player.hasPotionEffect(PotionEffectType.HEALTH_BOOST))
                            player.removePotionEffect(PotionEffectType.HEALTH_BOOST);
                    }

                    if (Pet.isSummoned(player, Pet.HORSE) || Pet.isSummoned(player, Pet.SKELETON_HORSE)) {
                        addPotionEffect(player, new PotionEffect(PotionEffectType.SPEED, 60, 0));
                    }

                    if (Pet.isSummoned(player, Pet.SQUID)) {
                        addPotionEffect(player, new PotionEffect(PotionEffectType.WATER_BREATHING, 60, 0));
                    }

                    if (Pet.isSummoned(player, Pet.BLAZE)) {
                        addPotionEffect(player, new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 60, 0));
                        if (player.getFireTicks() > 0) player.setFireTicks(0);
                    }

                    if (Pet.isSummoned(player, Pet.SILVERFISH)) {
                        addPotionEffect(player, new PotionEffect(PotionEffectType.FAST_DIGGING, 60, 0));
                    }

                    if (Pet.isSummoned(player, Pet.RABBIT)) {
                        addPotionEffect(player, new PotionEffect(PotionEffectType.JUMP, 60, 1));
                    }

                    if (Pet.isSummoned(player, Pet.ZOMBIE)) {
                        player.setFoodLevel(20);
                    }
                }
            }
        }.runTaskTimer(Core.instance, 0L, 20L);

        //Every 5 second
        new BukkitRunnable() {
            public void run() {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    if (chanceEnchant(player, "Spores")) {
                        if (!WGRegionManager.inRegion(player, "nopvp"))
                            for (Entity enemy : player.getNearbyEntities(3, 3, 3)) {
                                if (enemy instanceof Player) {
                                    if (!Clan.sameClan(player, (Player) enemy)) {
                                        int rng = Core.generateNumber(0, 6);
                                        if (rng == 0) {
                                            addPotionEffect((Player) enemy, new PotionEffect(PotionEffectType.BLINDNESS, 200, 0));
                                        } else if (rng == 1) {
                                            addPotionEffect((Player) enemy, new PotionEffect(PotionEffectType.CONFUSION, 300, 0));
                                        } else if (rng == 2) {
                                            addPotionEffect((Player) enemy, new PotionEffect(PotionEffectType.SLOW, 300, 0));
                                        } else if (rng == 3) {
                                            addPotionEffect((Player) enemy, new PotionEffect(PotionEffectType.POISON, 300, 0));
                                        } else if (rng == 4) {
                                            addPotionEffect((Player) enemy, new PotionEffect(PotionEffectType.WITHER, 300, 0));
                                        } else if (rng == 5) {
                                            addPotionEffect((Player) enemy, new PotionEffect(PotionEffectType.HUNGER, 300, 0));
                                        } else if (rng == 6) {
                                            addPotionEffect((Player) enemy, new PotionEffect(PotionEffectType.WEAKNESS, 300, 0));
                                        }
                                    }
                                }
                            }
                    }

                    if (chanceEnchant(player, "Lightning Rod")) {
                        if (!WGRegionManager.inRegion(player, "nopvp"))
                            for (Entity enemy : player.getNearbyEntities(4, 4, 4)) {
                                if (enemy instanceof Player) {
                                    if (!Clan.sameClan(player, (Player) enemy)) {
                                        ((Player) enemy).damage(20, player);
                                        enemy.setFireTicks(150);
                                        enemy.getWorld().strikeLightningEffect(enemy.getLocation());
                                    }
                                }
                            }
                    }

                }
            }
        }.runTaskTimer(Core.instance, 0L, 100L);
    }

}
