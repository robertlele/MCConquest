package me.robertle.mcconquest;

import me.astero.companions.api.CAPI;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
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

    public static CAPI pet = new CAPI();

    public static void addPotionEffect(Player player, PotionEffect effect) {
        if (effect.getType() == PotionEffectType.BLINDNESS) {
            if (InventoryUtil.hasCustomArmorEnchant(player, "Keen Eye")) {
                player.sendMessage(DefaultConfig.prefix + "§6Your keen eye has prevented you from getting blinded.");
                return;
            }

            if (chanceEnchant(player, "Immunity")) {
                player.sendMessage(DefaultConfig.prefix + "§6Your immunity has prevented you from being debuffed.");
                return;
            }
        } else if (effect.getType() == PotionEffectType.CONFUSION) {
            if (InventoryUtil.hasCustomArmorEnchant(player, "Immunity")) {
                player.sendMessage(DefaultConfig.prefix + "§6Your immunity has prevented you from being debuffed.");
                return;
            }
        } else if (effect.getType() == PotionEffectType.HUNGER) {
            if (Pet.isSummoned(player, Pet.ZOMBIE)) {
                player.sendMessage(DefaultConfig.prefix + "§6Your zombie pet has prevented you from losing hunger.");
                return;
            }

            if (chanceEnchant(player, "Immunity")) {
                player.sendMessage(DefaultConfig.prefix + "§6Your immunity has prevented you from being debuffed.");
                return;
            }
        } else if (effect.getType() == PotionEffectType.LEVITATION) {
            if (chanceEnchant(player, "Immunity")) {
                player.sendMessage(DefaultConfig.prefix + "§6Your immunity has prevented you from being debuffed.");
                return;
            }
        } else if (effect.getType() == PotionEffectType.POISON) {
            if (chanceEnchant(player, "Immunity")) {
                player.sendMessage(DefaultConfig.prefix + "§6Your immunity has prevented you from being debuffed.");
                return;
            }
        } else if (effect.getType() == PotionEffectType.SLOW) {
            if (Pet.isSummoned(player, Pet.POLAR_BEAR)) {
                player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 200, 0));
                player.sendMessage(DefaultConfig.prefix + "§6Your polar bear pet has given you resistance 1 for 10 seconds.");
            } else if (Pet.isSummoned(player, Pet.SLIME)) {
                player.sendMessage(DefaultConfig.prefix + "§6Your slime pet has prevented you from being slowed.");
                return;
            }

            if (InventoryUtil.hasCustomArmorEnchant(player, "Unstoppable")) {
                player.sendMessage(DefaultConfig.prefix + "§6Your unstoppable has prevented you from getting slowed.");
                return;
            }

            if (chanceEnchant(player, "Immunity")) {
                player.sendMessage(DefaultConfig.prefix + "§6Your immunity has prevented you from being debuffed.");
                return;
            }
        } else if (effect.getType() == PotionEffectType.SLOW_DIGGING) {
            if (chanceEnchant(player, "Immunity")) {
                player.sendMessage(DefaultConfig.prefix + "§6Your immunity has prevented you from being debuffed.");
                return;
            }
        } else if (effect.getType() == PotionEffectType.WEAKNESS) {
            if (chanceEnchant(player, "Immunity")) {
                player.sendMessage(DefaultConfig.prefix + "§6Your immunity has prevented you from being debuffed.");
                return;
            }
        } else if (effect.getType() == PotionEffectType.WITHER) {
            if (chanceEnchant(player, "Immunity")) {
                player.sendMessage(DefaultConfig.prefix + "§6Your immunity has prevented you from being debuffed.");
                return;
            }
        }
        player.addPotionEffect(effect);
    }

    public static boolean chanceEnchant(Player player, String enchant) {
        if (enchant.equalsIgnoreCase("Immunity")) {
            if (InventoryUtil.hasCustomArmorEnchant(player, enchant)) {
                switch (InventoryUtil.getArmorEnchantLevel(player, enchant)) {
                    case 1:
                        if (Core.chance(25)) {
                            player.sendMessage(DefaultConfig.prefix + "§6Your immunity has prevented you from getting debuffed.");
                            return true;
                        }
                        break;
                    case 2:
                        if (Core.chance(30)) {
                            player.sendMessage(DefaultConfig.prefix + "§6Your immunity has prevented you from getting debuffed.");
                            return true;
                        }
                        break;
                    case 3:
                        if (Core.chance(40)) {
                            player.sendMessage(DefaultConfig.prefix + "§6Your immunity has prevented you from getting debuffed.");
                            return true;
                        }
                        break;
                }
            }
        } else if (enchant.equalsIgnoreCase("Disorder")) {
            if (InventoryUtil.hasCustomArmorEnchant(player, enchant)) {
                switch (InventoryUtil.getArmorEnchantLevel(player, enchant)) {
                    case 1:
                        if (Core.chance(2.5)) {
                            player.sendMessage(DefaultConfig.prefix + "§6Your disorder has proc.");
                            return true;
                        }
                        break;
                    case 2:
                        if (Core.chance(3)) {
                            player.sendMessage(DefaultConfig.prefix + "§6Your disorder has proc.");
                            return true;
                        }
                        break;
                    case 3:
                        if (Core.chance(3.5)) {
                            player.sendMessage(DefaultConfig.prefix + "§6Your disorder has proc.");
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
                            player.sendMessage(DefaultConfig.prefix + "§6Your juggernaut has proc.");
                            return true;
                        }
                        break;
                    case 2:
                        if (Core.chance(8.5)) {
                            player.sendMessage(DefaultConfig.prefix + "§6Your juggernaut has proc.");
                            return true;
                        }
                        break;
                    case 3:
                        if (Core.chance(10)) {
                            player.sendMessage(DefaultConfig.prefix + "§6Your juggernaut has proc.");
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
                            player.sendMessage(DefaultConfig.prefix + "§6Your spores has proc.");
                            return true;
                        }
                        break;
                    case 2:
                        if (Core.chance(7.5)) {
                            player.sendMessage(DefaultConfig.prefix + "§6Your spores has proc.");
                            return true;
                        }
                        break;
                    case 3:
                        if (Core.chance(10)) {
                            player.sendMessage(DefaultConfig.prefix + "§6Your spores has proc.");
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
                            player.sendMessage(DefaultConfig.prefix + "§6Your molten armor has proc.");
                            return true;
                        }
                        break;
                    case 2:
                        if (Core.chance(15)) {
                            player.sendMessage(DefaultConfig.prefix + "§6Your molten armor has proc.");
                            return true;
                        }
                        break;
                    case 3:
                        if (Core.chance(20)) {
                            player.sendMessage(DefaultConfig.prefix + "§6Your molten armor has proc.");
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
                            player.sendMessage(DefaultConfig.prefix + "§6Your dodge has proc.");
                            return true;
                        }
                        break;
                    case 2:
                        if (Core.chance(8.5)) {
                            player.sendMessage(DefaultConfig.prefix + "§6Your dodge has proc.");
                            return true;
                        }
                        break;
                    case 3:
                        if (Core.chance(10)) {
                            player.sendMessage(DefaultConfig.prefix + "§6Your dodge has proc.");
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
                            player.sendMessage(DefaultConfig.prefix + "§6Your sticky armor has proc.");
                            return true;
                        }
                        break;
                    case 2:
                        if (Core.chance(8.5)) {
                            player.sendMessage(DefaultConfig.prefix + "§6Your sticky armor has proc.");
                            return true;
                        }
                        break;
                    case 3:
                        if (Core.chance(10)) {
                            player.sendMessage(DefaultConfig.prefix + "§6Your sticky armor has proc.");
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
                            player.sendMessage(DefaultConfig.prefix + "§6Your reflect has proc.");
                            return true;
                        }
                        break;
                    case 2:
                        if (Core.chance(6.5)) {
                            player.sendMessage(DefaultConfig.prefix + "§6Your reflect has proc.");
                            return true;
                        }
                        break;
                    case 3:
                        if (Core.chance(8)) {
                            player.sendMessage(DefaultConfig.prefix + "§6Your reflect has proc.");
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
                            player.sendMessage(DefaultConfig.prefix + "§6Your kite has proc.");
                            return true;
                        }
                        break;
                    case 2:
                        if (Core.chance(10)) {
                            player.sendMessage(DefaultConfig.prefix + "§6Your kite has proc.");
                            return true;
                        }
                        break;
                    case 3:
                        if (Core.chance(15)) {
                            player.sendMessage(DefaultConfig.prefix + "§6Your kite has proc.");
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
                            player.sendMessage(DefaultConfig.prefix + "§6Your scatter has proc.");
                            return true;
                        }
                        break;
                    case 2:
                        if (Core.chance(5)) {
                            player.sendMessage(DefaultConfig.prefix + "§6Your scatter has proc.");
                            return true;
                        }
                        break;
                    case 3:
                        if (Core.chance(6)) {
                            player.sendMessage(DefaultConfig.prefix + "§6Your scatter has proc.");
                            return true;
                        }
                        break;
                }
            }
        } else if (enchant.equalsIgnoreCase("Undead")) {
            if (InventoryUtil.hasCustomArmorEnchant(player, enchant)) {
                switch (InventoryUtil.getArmorEnchantLevel(player, enchant)) {
                    case 1:
                        if (Core.chance(5)) {
                            player.sendMessage(DefaultConfig.prefix + "§6Your undead has proc.");
                            return true;
                        }
                        break;
                    case 2:
                        if (Core.chance(5)) {
                            player.sendMessage(DefaultConfig.prefix + "§6Your undead has proc.");
                            return true;
                        }
                        break;
                    case 3:
                        if (Core.chance(6)) {
                            player.sendMessage(DefaultConfig.prefix + "§6Your undead has proc.");
                            return true;
                        }
                        break;
                }
            }
        } else if (enchant.equalsIgnoreCase("Glare")) {
            if (InventoryUtil.hasCustomSwordEnchant(player, enchant)) {
                switch (InventoryUtil.getSwordEnchantLevel(player, enchant)) {
                    case 1:
                        if (Core.chance(4)) {
                            player.sendMessage(DefaultConfig.prefix + "§6Your glare has proc.");
                            return true;
                        }
                        break;
                    case 2:
                        if (Core.chance(4.5)) {
                            player.sendMessage(DefaultConfig.prefix + "§6Your glare has proc.");
                            return true;
                        }
                        break;
                    case 3:
                        if (Core.chance(5.5)) {
                            player.sendMessage(DefaultConfig.prefix + "§6Your glare has proc.");
                            return true;
                        }
                        break;
                }
            }
        } else if (enchant.equalsIgnoreCase("Dragon Wrath")) {
            if (InventoryUtil.hasCustomSwordEnchant(player, enchant)) {
                switch (InventoryUtil.getSwordEnchantLevel(player, enchant)) {
                    case 1:
                        if (Core.chance(5)) {
                            player.sendMessage(DefaultConfig.prefix + "§6Your dragon wrath has proc.");
                            return true;
                        }
                        break;
                    case 2:
                        if (Core.chance(6)) {
                            player.sendMessage(DefaultConfig.prefix + "§6Your dragon wrath has proc.");
                            return true;
                        }
                        break;
                    case 3:
                        if (Core.chance(7.5)) {
                            player.sendMessage(DefaultConfig.prefix + "§6Your dragon wrath has proc.");
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
                            player.sendMessage(DefaultConfig.prefix + "§6Your wither has proc.");
                            return true;
                        }
                        break;
                    case 2:
                        if (Core.chance(8.5)) {
                            player.sendMessage(DefaultConfig.prefix + "§6Your wither has proc.");
                            return true;
                        }
                        break;
                    case 3:
                        if (Core.chance(10)) {
                            player.sendMessage(DefaultConfig.prefix + "§6Your wither has proc.");
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
                            player.sendMessage(DefaultConfig.prefix + "§6Your smite has proc.");
                            return true;
                        }
                        break;
                    case 2:
                        if (Core.chance(22.5)) {
                            player.sendMessage(DefaultConfig.prefix + "§6Your smite has proc.");
                            return true;
                        }
                        break;
                    case 3:
                        if (Core.chance(25)) {
                            player.sendMessage(DefaultConfig.prefix + "§6Your smite has proc.");
                            return true;
                        }
                        break;
                }
            }
        } else if (enchant.equalsIgnoreCase("Strip")) {
            if (InventoryUtil.hasCustomSwordEnchant(player, enchant)) {
                switch (InventoryUtil.getSwordEnchantLevel(player, enchant)) {
                    case 1:
                        if (Core.chance(1.5)) {
                            player.sendMessage(DefaultConfig.prefix + "§6Your strip has proc.");
                            return true;
                        }
                        break;
                    case 2:
                        if (Core.chance(2)) {
                            player.sendMessage(DefaultConfig.prefix + "§6Your strip has proc.");
                            return true;
                        }
                        break;
                    case 3:
                        if (Core.chance(2.5)) {
                            player.sendMessage(DefaultConfig.prefix + "§6Your strip has proc.");
                            return true;
                        }
                        break;
                }
            }
        } else if (enchant.equalsIgnoreCase("Ignite")) {
            if (InventoryUtil.hasCustomSwordEnchant(player, enchant)) {
                switch (InventoryUtil.getSwordEnchantLevel(player, enchant)) {
                    case 1:
                        if (Core.chance(5)) {
                            player.sendMessage(DefaultConfig.prefix + "§6Your ignite has proc.");
                            return true;
                        }
                        break;
                    case 2:
                        if (Core.chance(7.5)) {
                            player.sendMessage(DefaultConfig.prefix + "§6Your ignite has proc.");
                            return true;
                        }
                        break;
                    case 3:
                        if (Core.chance(10)) {
                            player.sendMessage(DefaultConfig.prefix + "§6Your ignite has proc.");
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
                            player.sendMessage(DefaultConfig.prefix + "§6Your multishot has proc.");
                            return true;
                        }
                        break;
                    case 2:
                        if (Core.chance(40)) {
                            player.sendMessage(DefaultConfig.prefix + "§6Your multishot has proc.");
                            return true;
                        }
                        break;
                    case 3:
                        if (Core.chance(50)) {
                            player.sendMessage(DefaultConfig.prefix + "§6Your multishot has proc.");
                            return true;
                        }
                        break;
                }
            }
        } else if (enchant.equalsIgnoreCase("Lifesteal")) {
            if (InventoryUtil.hasCustomSwordEnchant(player, enchant)) {
                switch (InventoryUtil.getSwordEnchantLevel(player, enchant)) {
                    case 1:
                        if (Core.chance(8)) {
                            player.sendMessage(DefaultConfig.prefix + "§6Your lifesteal has proc.");
                            return true;
                        }
                        break;
                    case 2:
                        if (Core.chance(9)) {
                            player.sendMessage(DefaultConfig.prefix + "§6Your lifesteal has proc.");
                            return true;
                        }
                        break;
                    case 3:
                        if (Core.chance(10)) {
                            player.sendMessage(DefaultConfig.prefix + "§6Your lifesteal has proc.");
                            return true;
                        }
                        break;
                }
            }
        } else if (enchant.equalsIgnoreCase("Blinding Strike")) {
            if (InventoryUtil.hasCustomSwordEnchant(player, enchant)) {
                switch (InventoryUtil.getSwordEnchantLevel(player, enchant)) {
                    case 1:
                        if (Core.chance(5)) {
                            player.sendMessage(DefaultConfig.prefix + "§6Your blinding strike has proc.");
                            return true;
                        }
                        break;
                    case 2:
                        if (Core.chance(5.5)) {
                            player.sendMessage(DefaultConfig.prefix + "§6Your blinding strike has proc.");
                            return true;
                        }
                        break;
                    case 3:
                        if (Core.chance(6.5)) {
                            player.sendMessage(DefaultConfig.prefix + "§6Your blinding strike has proc.");
                            return true;
                        }
                        break;
                }
            }
        } else if (enchant.equalsIgnoreCase("Double Hit")) {
            if (InventoryUtil.hasCustomSwordEnchant(player, enchant)) {
                switch (InventoryUtil.getSwordEnchantLevel(player, enchant)) {
                    case 1:
                        if (Core.chance(4)) {
                            player.sendMessage(DefaultConfig.prefix + "§6Your double hit has proc.");
                            return true;
                        }
                        break;
                    case 2:
                        if (Core.chance(4.5)) {
                            player.sendMessage(DefaultConfig.prefix + "§6Your double hit has proc.");
                            return true;
                        }
                        break;
                    case 3:
                        if (Core.chance(5.5)) {
                            player.sendMessage(DefaultConfig.prefix + "§6Your double hit has proc.");
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
                            player.sendMessage(DefaultConfig.prefix + "§6Your disarm has proc.");
                            return true;
                        }
                        break;
                    case 2:
                        if (Core.chance(4.5)) {
                            player.sendMessage(DefaultConfig.prefix + "§6Your disarm has proc.");
                            return true;
                        }
                        break;
                    case 3:
                        if (Core.chance(5)) {
                            player.sendMessage(DefaultConfig.prefix + "§6Your disarm has proc.");
                            return true;
                        }
                        break;
                }
            }
        } else if (enchant.equalsIgnoreCase("Paralyze")) {
            if (InventoryUtil.hasCustomSwordEnchant(player, enchant)) {
                switch (InventoryUtil.getSwordEnchantLevel(player, enchant)) {
                    case 1:
                        if (Core.chance(7.5)) {
                            player.sendMessage(DefaultConfig.prefix + "§6Your paralyze has proc.");
                            return true;
                        }
                        break;
                    case 2:
                        if (Core.chance(8.5)) {
                            player.sendMessage(DefaultConfig.prefix + "§6Your paralyze has proc.");
                            return true;
                        }
                        break;
                    case 3:
                        if (Core.chance(10)) {
                            player.sendMessage(DefaultConfig.prefix + "§6Your paralyze has proc.");
                            return true;
                        }
                        break;
                }
            }
        } else if (enchant.equalsIgnoreCase("Nausea")) {
            if (InventoryUtil.hasCustomSwordEnchant(player, enchant)) {
                switch (InventoryUtil.getSwordEnchantLevel(player, enchant)) {
                    case 1:
                        if (Core.chance(7)) {
                            player.sendMessage(DefaultConfig.prefix + "§6Your nausea has proc.");
                            return true;
                        }
                        break;
                    case 2:
                        if (Core.chance(8.5)) {
                            player.sendMessage(DefaultConfig.prefix + "§6Your nausea has proc.");
                            return true;
                        }
                        break;
                    case 3:
                        if (Core.chance(10)) {
                            player.sendMessage(DefaultConfig.prefix + "§6Your nausea has proc.");
                            return true;
                        }
                        break;
                }
            }
        } else if (enchant.equalsIgnoreCase("Famine")) {
            if (InventoryUtil.hasCustomSwordEnchant(player, enchant)) {
                switch (InventoryUtil.getSwordEnchantLevel(player, enchant)) {
                    case 1:
                        if (Core.chance(5)) {
                            player.sendMessage(DefaultConfig.prefix + "§6Your famine has proc.");
                            return true;
                        }
                        break;
                    case 2:
                        if (Core.chance(6)) {
                            player.sendMessage(DefaultConfig.prefix + "§6Your famine has proc.");
                            return true;
                        }
                        break;
                    case 3:
                        if (Core.chance(7.5)) {
                            player.sendMessage(DefaultConfig.prefix + "§6Your famine has proc.");
                            return true;
                        }
                        break;
                }
            }
        } else if (enchant.equalsIgnoreCase("Knockup")) {
            if (InventoryUtil.hasCustomSwordEnchant(player, enchant)) {
                switch (InventoryUtil.getSwordEnchantLevel(player, enchant)) {
                    case 1:
                        if (Core.chance(4)) {
                            player.sendMessage(DefaultConfig.prefix + "§6Your knockup has proc.");
                            return true;
                        }
                        break;
                    case 2:
                        if (Core.chance(4.5)) {
                            player.sendMessage(DefaultConfig.prefix + "§6Your knockup has proc.");
                            return true;
                        }
                        break;
                    case 3:
                        if (Core.chance(5.5)) {
                            player.sendMessage(DefaultConfig.prefix + "§6Your knockup has proc.");
                            return true;
                        }
                        break;
                }
            }
        } else if (enchant.equalsIgnoreCase("Ice Aspect")) {
            if (InventoryUtil.hasCustomSwordEnchant(player, enchant)) {
                switch (InventoryUtil.getSwordEnchantLevel(player, enchant)) {
                    case 1:
                        if (Core.chance(10)) {
                            player.sendMessage(DefaultConfig.prefix + "§6Your ice aspect has proc.");
                            return true;
                        }
                        break;
                    case 2:
                        if (Core.chance(12.5)) {
                            player.sendMessage(DefaultConfig.prefix + "§6Your ice aspect has proc.");
                            return true;
                        }
                        break;
                    case 3:
                        if (Core.chance(15)) {
                            player.sendMessage(DefaultConfig.prefix + "§6Your ice aspect has proc.");
                            return true;
                        }
                        break;
                }
            }
        } else if (enchant.equalsIgnoreCase("Frozen")) {
            if (InventoryUtil.hasCustomSwordEnchant(player, enchant)) {
                switch (InventoryUtil.getSwordEnchantLevel(player, enchant)) {
                    case 1:
                        if (Core.chance(4)) {
                            player.sendMessage(DefaultConfig.prefix + "§6Your frozen has proc.");
                            return true;
                        }
                        break;
                    case 2:
                        if (Core.chance(4.5)) {
                            player.sendMessage(DefaultConfig.prefix + "§6Your frozen has proc.");
                            return true;
                        }
                        break;
                    case 3:
                        if (Core.chance(5)) {
                            player.sendMessage(DefaultConfig.prefix + "§6Your frozen has proc.");
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
    public static List<UUID> freezeCD = new ArrayList<>();

    public static void checkLowEnchant(Player player) {
        if (InventoryUtil.hasCustomArmorEnchant(player, "Berserk")) {
            if (berserkCD.contains(player.getUniqueId())) return;
            switch (InventoryUtil.getArmorEnchantLevel(player, "Berserk")) {
                case 1:
                    if (player.getHealth() <= 3) {
                        addPotionEffect(player, new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 200, 0));
                        player.sendMessage(DefaultConfig.prefix + "§6Your berserk has proc.");
                        berserkCD.add(player.getUniqueId());
                        new BukkitRunnable() {
                            public void run() {
                                berserkCD.remove(player.getUniqueId());
                            }
                        }.runTaskLater(Core.instance, 1200L);
                    }
                    break;
                case 2:
                    if (player.getHealth() <= 5) {
                        addPotionEffect(player, new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 200, 0));
                        player.sendMessage(DefaultConfig.prefix + "§6Your berserk has proc.");
                        berserkCD.add(player.getUniqueId());
                        new BukkitRunnable() {
                            public void run() {
                                berserkCD.remove(player.getUniqueId());
                            }
                        }.runTaskLater(Core.instance, 1200L);
                    }
                    break;
                case 3:
                    if (player.getHealth() <= 6) {
                        addPotionEffect(player, new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 300, 0));
                        player.sendMessage(DefaultConfig.prefix + "§6Your berserk has proc.");
                        berserkCD.add(player.getUniqueId());
                        new BukkitRunnable() {
                            public void run() {
                                berserkCD.remove(player.getUniqueId());
                            }
                        }.runTaskLater(Core.instance, 1200L);
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
                                    addPotionEffect((Player) enemy, new PotionEffect(PotionEffectType.BLINDNESS, 100, 0));
                            }
                        }
                        player.sendMessage(DefaultConfig.prefix + "§6Your blinding light has proc.");
                        blindinglightCD.add(player.getUniqueId());
                        new BukkitRunnable() {
                            public void run() {
                                blindinglightCD.remove(player.getUniqueId());
                            }
                        }.runTaskLater(Core.instance, 1200L);
                    }
                    break;
                case 2:
                    if (player.getHealth() <= 5) {
                        for (Entity enemy : player.getNearbyEntities(3, 3, 3)) {
                            if (enemy instanceof Player) {
                                if (!Clan.sameClan(player, (Player) enemy))
                                    addPotionEffect((Player) enemy, new PotionEffect(PotionEffectType.BLINDNESS, 125, 0));
                            }
                        }
                        player.sendMessage(DefaultConfig.prefix + "§6Your blinding light has proc.");
                        blindinglightCD.add(player.getUniqueId());
                        new BukkitRunnable() {
                            public void run() {
                                blindinglightCD.remove(player.getUniqueId());
                            }
                        }.runTaskLater(Core.instance, 1200L);
                    }
                    break;
                case 3:
                    if (player.getHealth() <= 6) {
                        for (Entity enemy : player.getNearbyEntities(4, 4, 4)) {
                            if (enemy instanceof Player) {
                                if (!Clan.sameClan(player, (Player) enemy))
                                    addPotionEffect((Player) enemy, new PotionEffect(PotionEffectType.BLINDNESS, 150, 0));
                            }
                        }
                        player.sendMessage(DefaultConfig.prefix + "§6Your blinding light has proc.");
                        blindinglightCD.add(player.getUniqueId());
                        new BukkitRunnable() {
                            public void run() {
                                blindinglightCD.remove(player.getUniqueId());
                            }
                        }.runTaskLater(Core.instance, 1200L);
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
                        player.sendMessage(DefaultConfig.prefix + "§6Your seismic escape has proc.");
                        seismicescapeCD.add(player.getUniqueId());
                        new BukkitRunnable() {
                            public void run() {
                                seismicescapeCD.remove(player.getUniqueId());
                            }
                        }.runTaskLater(Core.instance, 1200L);
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
                        player.sendMessage(DefaultConfig.prefix + "§6Your seismic escape has proc.");
                        seismicescapeCD.add(player.getUniqueId());
                        new BukkitRunnable() {
                            public void run() {
                                seismicescapeCD.remove(player.getUniqueId());
                            }
                        }.runTaskLater(Core.instance, 1200L);
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
                        player.sendMessage(DefaultConfig.prefix + "§6Your seismic escape has proc.");
                        seismicescapeCD.add(player.getUniqueId());
                        new BukkitRunnable() {
                            public void run() {
                                seismicescapeCD.remove(player.getUniqueId());
                            }
                        }.runTaskLater(Core.instance, 1200L);
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
                        player.sendMessage(DefaultConfig.prefix + "§6Your fragile escape has proc.");
                        fragileescapeCD.add(player.getUniqueId());
                        new BukkitRunnable() {
                            public void run() {
                                fragileescapeCD.remove(player.getUniqueId());
                            }
                        }.runTaskLater(Core.instance, 1200L);
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
                        player.sendMessage(DefaultConfig.prefix + "§6Your fragile escape has proc.");
                        fragileescapeCD.add(player.getUniqueId());
                        new BukkitRunnable() {
                            public void run() {
                                fragileescapeCD.remove(player.getUniqueId());
                            }
                        }.runTaskLater(Core.instance, 1200L);
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
                        player.sendMessage(DefaultConfig.prefix + "§6Your fragile escape has proc.");
                        fragileescapeCD.add(player.getUniqueId());
                        new BukkitRunnable() {
                            public void run() {
                                fragileescapeCD.remove(player.getUniqueId());
                            }
                        }.runTaskLater(Core.instance, 1200L);
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
                        player.sendMessage(DefaultConfig.prefix + "§6Your miracle has proc.");
                        mircaleCD.add(player.getUniqueId());
                        new BukkitRunnable() {
                            public void run() {
                                mircaleCD.remove(player.getUniqueId());
                            }
                        }.runTaskLater(Core.instance, 1200L);
                    }
                    break;
                case 2:
                    if (player.getHealth() <= 4) {
                        addPotionEffect(player, new PotionEffect(PotionEffectType.REGENERATION, 200, 0));
                        addPotionEffect(player, new PotionEffect(PotionEffectType.ABSORPTION, 200, 1));
                        player.sendMessage(DefaultConfig.prefix + "§6Your miracle has proc.");
                        mircaleCD.add(player.getUniqueId());
                        new BukkitRunnable() {
                            public void run() {
                                mircaleCD.remove(player.getUniqueId());
                            }
                        }.runTaskLater(Core.instance, 1200L);
                    }
                    break;
                case 3:
                    if (player.getHealth() <= 5) {
                        addPotionEffect(player, new PotionEffect(PotionEffectType.REGENERATION, 200, 1));
                        addPotionEffect(player, new PotionEffect(PotionEffectType.ABSORPTION, 200, 1));
                        player.sendMessage(DefaultConfig.prefix + "§6Your miracle has proc.");
                        mircaleCD.add(player.getUniqueId());
                        new BukkitRunnable() {
                            public void run() {
                                mircaleCD.remove(player.getUniqueId());
                            }
                        }.runTaskLater(Core.instance, 1200L);
                    }
                    break;
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onDamageEnchant(EntityDamageByEntityEvent e) {
        if (e.getEntity() instanceof Player) {
            Player player = (Player) e.getEntity();
            Player damager = null;

            if (e.getDamager() instanceof Player || e.getDamager() instanceof Arrow) {

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

                checkLowEnchant(player);

                if (chanceEnchant(player, "Dodge")) {
                    e.getDamager().sendMessage(DefaultConfig.prefix + "§cYour attack was dodged.");
                    e.setDamage(0);
                    e.setCancelled(true);
                    return;
                }

                if (chanceEnchant(player, "Disorder")) {
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
                    damager.sendMessage(DefaultConfig.prefix + "§cYour hotbar has been shuffled.");
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

                if (chanceEnchant(player, "Molten Armor")) {
                    damager.setFireTicks(120);
                }

                if (chanceEnchant(player, "Sticky Armor")) {
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

                if (chanceEnchant(player, "Reflect")) {
                    damager.damage(e.getDamage(), player);
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
                            addPotionEffect(player, new PotionEffect(PotionEffectType.SPEED, 200, 1));
                            break;
                        case 3:
                            addPotionEffect(player, new PotionEffect(PotionEffectType.SPEED, 250, 1));
                            break;
                    }
                }

                if (chanceEnchant(player, "Undead")) {
                    switch (InventoryUtil.getArmorEnchantLevel(player, "Undead")) {
                        case 1:
                            addPotionEffect(damager, new PotionEffect(PotionEffectType.WEAKNESS, 200, 1));
                            break;
                        case 2:
                            addPotionEffect(damager, new PotionEffect(PotionEffectType.WEAKNESS, 250, 1));
                            break;
                        case 3:
                            addPotionEffect(damager, new PotionEffect(PotionEffectType.WEAKNESS, 300, 1));
                            break;
                    }
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
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
        }

        if (e.getDamager() instanceof Player && e.getEntity() instanceof Player) {
            Player player = (Player) e.getDamager();
            Player enemy = (Player) e.getEntity();

            if (chanceEnchant(player, "Glare")) {
                switch (InventoryUtil.getSwordEnchantLevel(player, "Glare")) {
                    case 1:
                        freeze(enemy, 3.5);
                        addPotionEffect(enemy, new PotionEffect(PotionEffectType.BLINDNESS, 70, 0));
                        break;
                    case 2:
                        freeze(enemy, 4);
                        addPotionEffect(enemy, new PotionEffect(PotionEffectType.BLINDNESS, 80, 0));
                        break;
                    case 3:
                        freeze(enemy, 4.5);
                        addPotionEffect(enemy, new PotionEffect(PotionEffectType.BLINDNESS, 90, 0));
                        break;
                }
                enemy.sendMessage(DefaultConfig.prefix + "§cYou've been paralyzed.");
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
                        if (enemy.getFireTicks() > 0) e.setDamage(e.getDamage() + 3);
                        break;
                    case 2:
                        if (enemy.getFireTicks() > 0) e.setDamage(e.getDamage() + 3.5);
                        break;
                    case 3:
                        if (enemy.getFireTicks() > 0) e.setDamage(e.getDamage() + 4);
                        break;
                }
            }

            if (chanceEnchant(player, "Ignite")) {
                if (enemy.hasPotionEffect(PotionEffectType.FIRE_RESISTANCE)) {
                    enemy.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
                    enemy.setFireTicks(100);
                } else enemy.setFireTicks(100);
                enemy.sendMessage(DefaultConfig.prefix + "§cYou were ignited.");
            }

            if (chanceEnchant(player, "Strip")) {
                int rng = Core.generateNumber(0, 3);
                if (rng == 0) {
                    if (enemy.getInventory().getHelmet() == null) return;
                    ItemStack armor = enemy.getInventory().getHelmet();
                    enemy.getInventory().setItem(EquipmentSlot.HEAD, null);
                    enemy.getInventory().addItem(armor);
                    enemy.sendMessage(DefaultConfig.prefix + "§cAn armor piece was stripped.");
                } else if (rng == 1) {
                    if (enemy.getInventory().getChestplate() == null) return;
                    ItemStack armor = enemy.getInventory().getChestplate();
                    enemy.getInventory().setItem(EquipmentSlot.CHEST, null);
                    enemy.getInventory().addItem(armor);
                    enemy.sendMessage(DefaultConfig.prefix + "§cAn armor piece was stripped.");
                } else if (rng == 2) {
                    if (enemy.getInventory().getLeggings() == null) return;
                    ItemStack armor = enemy.getInventory().getLeggings();
                    enemy.getInventory().setItem(EquipmentSlot.LEGS, null);
                    enemy.getInventory().addItem(armor);
                    enemy.sendMessage(DefaultConfig.prefix + "§cAn armor piece was stripped.");
                } else if (rng == 3) {
                    if (enemy.getInventory().getBoots() == null) return;
                    ItemStack armor = enemy.getInventory().getBoots();
                    enemy.getInventory().setItem(EquipmentSlot.FEET, null);
                    enemy.getInventory().addItem(armor);
                    enemy.sendMessage(DefaultConfig.prefix + "§cAn armor piece was stripped.");
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
                player.sendMessage(DefaultConfig.prefix + "§aYour demolish has proc.");
                demolishCD.add(player.getUniqueId());
                new BukkitRunnable() {
                    public void run() {
                        demolishCD.remove(player.getUniqueId());
                    }
                }.runTaskLater(Core.instance, 1200L);
            }

            if (InventoryUtil.hasCustomSwordEnchant(player, "Rivalry")) {
                if (enemy.getInventory().getItemInMainHand() != null && ItemHelper.hasName(enemy.getInventory().getItemInMainHand())) {
                    if (ItemHelper.getName(enemy.getInventory().getItemInMainHand()).contains("Sword")) {
                        switch (InventoryUtil.getSwordEnchantLevel(player, "Rivalry")) {
                            case 1:
                                e.setDamage(e.getDamage() + 3);
                                break;
                            case 2:
                                e.setDamage(e.getDamage() + 3.5);
                                break;
                            case 3:
                                e.setDamage(e.getDamage() + 4);
                                break;
                        }
                    }
                }
            }

            if (InventoryUtil.hasCustomSwordEnchant(player, "Torrent")) {
                if (player.getLocation().getBlock().getType() == Material.WATER) {
                    switch (InventoryUtil.getSwordEnchantLevel(player, "Torrent")) {
                        case 1:
                            e.setDamage(e.getDamage() + 4);
                            break;
                        case 2:
                            e.setDamage(e.getDamage() + 5);
                            break;
                        case 3:
                            e.setDamage(e.getDamage() + 6);
                            break;
                    }
                }
            }

            if (chanceEnchant(player, "Double Hit")) {
                e.setDamage(e.getDamage() + 6);
            }

            if (chanceEnchant(player, "Disarm")) {
                if (enemy.getInventory().getItemInMainHand() != null) {
                    ItemStack item = enemy.getInventory().getItemInMainHand();
                    enemy.getInventory().setItemInMainHand(null);
                    boolean added = false;
                    while (!added) {
                        int index = Core.generateNumber(0, 45);
                        if (enemy.getInventory().getItem(index) == null) {
                            enemy.getInventory().setItem(index, item);
                            added = true;
                        }
                    }
                    enemy.sendMessage(DefaultConfig.prefix + "§cYour weapon has been disarmed.");
                }
            }

            if (chanceEnchant(player, "Blinding Strike")) {
                switch (InventoryUtil.getSwordEnchantLevel(player, "Blinding Strike")) {
                    case 1:
                        addPotionEffect(enemy, new PotionEffect(PotionEffectType.BLINDNESS, 175, 0));
                        break;
                    case 2:
                        addPotionEffect(enemy, new PotionEffect(PotionEffectType.BLINDNESS, 200, 0));
                        break;
                    case 3:
                        addPotionEffect(enemy, new PotionEffect(PotionEffectType.BLINDNESS, 250, 0));
                        break;
                }
            }

            if (InventoryUtil.hasCustomArmorEnchant(player, "Backstab")) {
                Location playerLoc = player.getLocation();
                Location targetLoc = enemy.getLocation();
                double pvecy = -Math.sin(Math.toRadians(playerLoc.getPitch()));
                double pvecx = -Math.cos(Math.toRadians(playerLoc.getPitch())) * Math.sin(Math.toRadians(playerLoc.getYaw()));
                double pvecz = Math.cos(Math.toRadians(playerLoc.getPitch())) * Math.cos(Math.toRadians(playerLoc.getYaw()));
                double tvecy = -Math.sin(Math.toRadians(targetLoc.getPitch()));
                double tvecx = -Math.cos(Math.toRadians(targetLoc.getPitch())) * Math.sin(Math.toRadians(targetLoc.getYaw()));
                double tvecz = Math.cos(Math.toRadians(targetLoc.getPitch())) * Math.cos(Math.toRadians(targetLoc.getYaw()));
                double dot = tvecx * pvecx + tvecy * pvecy + tvecz * pvecz;
                if (dot > 0.6D) {
                    switch (InventoryUtil.getSwordEnchantLevel(player, "Backstab")) {
                        case 1:
                            e.setDamage(e.getDamage() + 5);
                            break;
                        case 2:
                            e.setDamage(e.getDamage() + 6.5);
                            break;
                        case 3:
                            e.setDamage(e.getDamage() + 8);
                            break;
                    }
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
                        newHealth = player.getHealth() + 3;
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
                enemy.setVelocity(new Vector(0, 1, 0).multiply(2));
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
                        freeze(enemy, 4.5);
                        break;
                    case 2:
                        freeze(enemy, 5);
                        break;
                    case 3:
                        freeze(enemy, 6);
                        break;
                }
            }

        }

        if (e.getDamager() instanceof Arrow && e.getEntity() instanceof Player) {
            Player player = (Player) ((Arrow) e.getDamager()).getShooter();
            Player enemy = (Player) e.getEntity();

            if (Clan.sameClan(player, enemy)) {
                e.setDamage(0);
                e.setCancelled(true);
                return;
            }

            if (chanceEnchant(player, "Wither")) {
                switch (InventoryUtil.getSwordEnchantLevel(player, "Wither")) {
                    case 1:
                        addPotionEffect(enemy, new PotionEffect(PotionEffectType.WITHER, 200, 0));
                        break;
                    case 2:
                        addPotionEffect(enemy, new PotionEffect(PotionEffectType.WITHER, 250, 0));
                        break;
                    case 3:
                        addPotionEffect(enemy, new PotionEffect(PotionEffectType.WITHER, 300, 0));
                        break;
                }
            }

            if (chanceEnchant(player, "Smite")) {
                enemy.getLocation().getWorld().strikeLightning(enemy.getLocation());
                enemy.setFireTicks(200);
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
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void damageNotifier(EntityDamageByEntityEvent e) {
        if (e.isCancelled()) return;

        if (e.getEntity() instanceof Player) {
            e.getDamager().sendMessage(DefaultConfig.prefix + "Raw Damage: §c" + e.getDamage());
            e.getDamager().sendMessage(DefaultConfig.prefix + "Final Damage: §c" + e.getFinalDamage());
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
                        addPotionEffect(player, new PotionEffect(PotionEffectType.SATURATION, 60, 1));
                    }

                    if (InventoryUtil.hasCustomArmorEnchant(player, "Health Boost")) {
                        if (!player.hasPotionEffect(PotionEffectType.HEALTH_BOOST))
                            addPotionEffect(player, new PotionEffect(PotionEffectType.HEALTH_BOOST, Integer.MAX_VALUE, 1));
                    } else {
                        if (player.hasPotionEffect(PotionEffectType.HEALTH_BOOST))
                            player.removePotionEffect(PotionEffectType.HEALTH_BOOST);
                    }
                }
            }
        }.runTaskTimer(Core.instance, 0L, 20L);

        //Every 5 second
        new BukkitRunnable() {
            public void run() {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    if (chanceEnchant(player, "Spores")) {
                        for (Entity enemy : player.getNearbyEntities(3, 3, 3)) {
                            if (enemy instanceof Player) {
                                if (!Clan.sameClan(player, (Player) enemy)) {
                                    int rng = Core.generateNumber(0, 7);
                                    if (rng == 0) {
                                        addPotionEffect((Player) enemy, new PotionEffect(PotionEffectType.BLINDNESS, 200, 0));
                                    } else if (rng == 1) {
                                        addPotionEffect((Player) enemy, new PotionEffect(PotionEffectType.CONFUSION, 200, 0));
                                    } else if (rng == 2) {
                                        addPotionEffect((Player) enemy, new PotionEffect(PotionEffectType.SLOW, 200, 0));
                                    } else if (rng == 3) {
                                        addPotionEffect((Player) enemy, new PotionEffect(PotionEffectType.POISON, 200, 0));
                                    } else if (rng == 4) {
                                        addPotionEffect((Player) enemy, new PotionEffect(PotionEffectType.WITHER, 200, 0));
                                    } else if (rng == 5) {
                                        addPotionEffect((Player) enemy, new PotionEffect(PotionEffectType.LEVITATION, 200, 0));
                                    } else if (rng == 6) {
                                        addPotionEffect((Player) enemy, new PotionEffect(PotionEffectType.HUNGER, 300, 0));
                                    } else if (rng == 7) {
                                        addPotionEffect((Player) enemy, new PotionEffect(PotionEffectType.WEAKNESS, 300, 0));
                                    }
                                }
                            }
                        }
                    }

                }
            }
        }.runTaskTimer(Core.instance, 0L, 100L);
    }

}
