package me.robertle.mcconquest;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class MCCCommands implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getLabel().equalsIgnoreCase("mcc")) {
            // /mcc give <item> /mcc bs <player>
            if (args.length == 2) {
                if (args[0].equalsIgnoreCase("give")) {
                    if (!(sender instanceof Player)) return false;
                    Player player = (Player) sender;
                    if (player.hasPermission("mcc.admin")) {
                        switch (args[1]) {
                            case "randomarmor":
                                player.getInventory().addItem(CustomItemManager.getRandomArmor());
                                break;
                            case "specialrandomarmor":
                                player.getInventory().addItem(CustomItemManager.getSpecialRandomArmor());
                                break;
                            case "randomweapon":
                                player.getInventory().addItem(CustomItemManager.getRandomWeapon());
                                break;
                            case "specialrandomweapon":
                                player.getInventory().addItem(CustomItemManager.getSpecialRandomWeapon());
                                break;
                            case "randomartifact":
                                player.getInventory().addItem(CustomItemManager.getRandomArtifact());
                                break;
                            case "armorvoucher":
                                player.getInventory().addItem(CustomItemManager.getArmorVoucher(false));
                                break;
                            case "specialarmorvoucher":
                                player.getInventory().addItem(CustomItemManager.getArmorVoucher(true));
                                break;
                            case "weaponvoucher":
                                player.getInventory().addItem(CustomItemManager.getWeaponVoucher(false));
                                break;
                            case "specialweaponvoucher":
                                player.getInventory().addItem(CustomItemManager.getWeaponVoucher(true));
                                break;
                            case "artifactvoucher":
                                player.getInventory().addItem(CustomItemManager.getArtifactVoucher());
                                break;
                            case "votecrate":
                                player.getInventory().addItem(CustomItemManager.getCrate(Crate.VOTE_CRATE));
                                break;
                            case "basiccrate":
                                player.getInventory().addItem(CustomItemManager.getCrate(Crate.BASIC_CRATE));
                                break;
                            case "supercrate":
                                player.getInventory().addItem(CustomItemManager.getCrate(Crate.SUPER_CRATE));
                                break;
                            case "ultracrate":
                                player.getInventory().addItem(CustomItemManager.getCrate(Crate.ULTRA_CRATE));
                                break;
                            case "godlycrate":
                                player.getInventory().addItem(CustomItemManager.getCrate(Crate.GODLY_CRATE));
                                break;
                            case "petcrate":
                                player.getInventory().addItem(CustomItemManager.getCrate(Crate.PET_CRATE));
                                break;
                            case "stiervoucher":
                                player.getInventory().addItem(CustomItemManager.getSTierVoucher());
                                break;
                            case "atiervoucher":
                                player.getInventory().addItem(CustomItemManager.getATierVoucher());
                                break;
                            case "dragonset":
                                player.getInventory().addItem(CustomItemManager.getArmorPiece(MCCArmor.DRAGON_HELMET));
                                player.getInventory().addItem(CustomItemManager.getArmorPiece(MCCArmor.DRAGON_CHESTPLATE));
                                player.getInventory().addItem(CustomItemManager.getArmorPiece(MCCArmor.DRAGON_LEGGINGS));
                                player.getInventory().addItem(CustomItemManager.getArmorPiece(MCCArmor.DRAGON_BOOTS));
                                player.getInventory().addItem(CustomItemManager.getWeapon(MCCWeapon.DRAGON_SWORD));
                                player.getInventory().addItem(CustomItemManager.getWeapon(MCCWeapon.DRAGON_BOW));
                                break;
                            case "giantset":
                                player.getInventory().addItem(CustomItemManager.getArmorPiece(MCCArmor.GIANT_HELMET));
                                player.getInventory().addItem(CustomItemManager.getArmorPiece(MCCArmor.GIANT_CHESTPLATE));
                                player.getInventory().addItem(CustomItemManager.getArmorPiece(MCCArmor.GIANT_LEGGINGS));
                                player.getInventory().addItem(CustomItemManager.getArmorPiece(MCCArmor.GIANT_BOOTS));
                                player.getInventory().addItem(CustomItemManager.getWeapon(MCCWeapon.GIANT_AXE));
                                player.getInventory().addItem(CustomItemManager.getWeapon(MCCWeapon.GIANT_BOW));
                                player.getInventory().addItem(CustomItemManager.getArtifact(MCCArtifact.GIANT_GRINDER));
                                break;
                            case "squidset":
                                player.getInventory().addItem(CustomItemManager.getArmorPiece(MCCArmor.SQUID_HELMET));
                                player.getInventory().addItem(CustomItemManager.getArmorPiece(MCCArmor.SQUID_CHESTPLATE));
                                player.getInventory().addItem(CustomItemManager.getArmorPiece(MCCArmor.SQUID_LEGGINGS));
                                player.getInventory().addItem(CustomItemManager.getArmorPiece(MCCArmor.SQUID_BOOTS));
                                player.getInventory().addItem(CustomItemManager.getWeapon(MCCWeapon.SQUID_SWORD));
                                player.getInventory().addItem(CustomItemManager.getArtifact(MCCArtifact.SQUID_ROD));
                                break;
                            case "golemset":
                                player.getInventory().addItem(CustomItemManager.getArmorPiece(MCCArmor.GOLEM_HELMET));
                                player.getInventory().addItem(CustomItemManager.getArmorPiece(MCCArmor.GOLEM_CHESTPLATE));
                                player.getInventory().addItem(CustomItemManager.getArmorPiece(MCCArmor.GOLEM_LEGGINGS));
                                player.getInventory().addItem(CustomItemManager.getArmorPiece(MCCArmor.GOLEM_BOOTS));
                                player.getInventory().addItem(CustomItemManager.getWeapon(MCCWeapon.GOLEM_AXE));
                                player.getInventory().addItem(CustomItemManager.getWeapon(MCCWeapon.GOLEM_BOW));
                                break;
                            case "skeletonset":
                                player.getInventory().addItem(CustomItemManager.getArmorPiece(MCCArmor.SKELETON_HELMET));
                                player.getInventory().addItem(CustomItemManager.getArmorPiece(MCCArmor.SKELETON_CHESTPLATE));
                                player.getInventory().addItem(CustomItemManager.getArmorPiece(MCCArmor.SKELETON_LEGGINGS));
                                player.getInventory().addItem(CustomItemManager.getArmorPiece(MCCArmor.SKELETON_BOOTS));
                                player.getInventory().addItem(CustomItemManager.getWeapon(MCCWeapon.SKELETON_BOW));
                                break;
                            case "pigset":
                                player.getInventory().addItem(CustomItemManager.getArmorPiece(MCCArmor.PIG_HELMET));
                                player.getInventory().addItem(CustomItemManager.getArmorPiece(MCCArmor.PIG_CHESTPLATE));
                                player.getInventory().addItem(CustomItemManager.getArmorPiece(MCCArmor.PIG_LEGGINGS));
                                player.getInventory().addItem(CustomItemManager.getArmorPiece(MCCArmor.PIG_BOOTS));
                                player.getInventory().addItem(CustomItemManager.getWeapon(MCCWeapon.PIG_SWORD));
                                break;
                            case "silverfishset":
                                player.getInventory().addItem(CustomItemManager.getArmorPiece(MCCArmor.SILVERFISH_HELMET));
                                player.getInventory().addItem(CustomItemManager.getArmorPiece(MCCArmor.SILVERFISH_CHESTPLATE));
                                player.getInventory().addItem(CustomItemManager.getArmorPiece(MCCArmor.SILVERFISH_LEGGINGS));
                                player.getInventory().addItem(CustomItemManager.getArmorPiece(MCCArmor.SILVERFISH_BOOTS));
                                player.getInventory().addItem(CustomItemManager.getWeapon(MCCWeapon.SILVERFISH_AXE));
                                break;
                            case "guardianset":
                                player.getInventory().addItem(CustomItemManager.getArtifact(MCCArtifact.GUARDIAN_ROD));
                                break;
                            case "witherset":
                                player.getInventory().addItem(CustomItemManager.getArtifact(MCCArtifact.WITHER_PICKAXE));
                                break;
                            case "zombieset":
                                player.getInventory().addItem(CustomItemManager.getArtifact(MCCArtifact.ZOMBIE_GRINDER));
                                break;
                            case "pigmanset":
                                player.getInventory().addItem(CustomItemManager.getArtifact(MCCArtifact.PIGMAN_PICKAXE));
                                break;
                            case "knockbackset":
                                player.getInventory().addItem(CustomItemManager.getArtifact(MCCArtifact.EXTREME_KNOCKBACK_STICK));
                                player.getInventory().addItem(CustomItemManager.getArtifact(MCCArtifact.KNOCKBACK_STICK));
                                break;
                            case "crystalbeater":
                                player.getInventory().addItem(CustomItemManager.getArtifact(MCCArtifact.CRYSTAL_BEATER));
                                break;
                            case "starter":
                                player.getInventory().addItem(CustomItemManager.getWeapon(MCCWeapon.IRON_SWORD));
                                player.getInventory().addItem(CustomItemManager.getArtifact(MCCArtifact.IRON_PICKAXE));
                                player.getInventory().addItem(CustomItemManager.getArtifact(MCCArtifact.FISHING_ROD));
                                player.getInventory().addItem(CustomItemManager.getArmorPiece(MCCArmor.DIAMOND_HELMET));
                                player.getInventory().addItem(CustomItemManager.getArmorPiece(MCCArmor.DIAMOND_CHESTPLATE));
                                player.getInventory().addItem(CustomItemManager.getArmorPiece(MCCArmor.DIAMOND_LEGGINGS));
                                player.getInventory().addItem(CustomItemManager.getArmorPiece(MCCArmor.DIAMOND_BOOTS));
                                break;
                            case "blacksmith":
                                player.getInventory().addItem(CustomItemManager.getBlacksmithsCoal());
                                player.getInventory().addItem(CustomItemManager.getBlacksmithsMagmaRod());
                                player.getInventory().addItem(CustomItemManager.getBlacksmithsMagicDust());
                                player.getInventory().addItem(CustomItemManager.getBlacksmithsLifeOrb());
                                break;
                            case "generatorvoucher":
                                player.getInventory().addItem(CustomItemManager.getGeneratorVoucher());
                                break;
                            case "generator":
                                player.getInventory().addItem(CustomItemManager.getGenerator(GeneratorType.MONEY));
                                player.getInventory().addItem(CustomItemManager.getGenerator(GeneratorType.INGOT));
                                player.getInventory().addItem(CustomItemManager.getGenerator(GeneratorType.ESSENCE));
                                break;
                            case "ingot":
                                player.getInventory().addItem(CustomItemManager.getIngot(false, 64));
                                player.getInventory().addItem(CustomItemManager.getIngot(true, 64));
                                break;
                            case "essence":
                                player.getInventory().addItem(CustomItemManager.getEssence(false, 64));
                                player.getInventory().addItem(CustomItemManager.getEssence(true, 64));
                                break;
                            case "pets":
                                for (Pet p : Pet.values()) {
                                    player.getInventory().addItem(CustomItemManager.getPetVoucher(p));
                                }
                                break;
                            case "tags":
                                for (Tag tag : Tag.values()) {
                                    player.getInventory().addItem(CustomItemManager.getTag(tag));
                                }
                        }
                        player.sendMessage(DefaultConfig.prefix + "Item(s) given.");
                        return true;
                    }
                }
                else if (args[0].equalsIgnoreCase("location")) {
                    if (sender.hasPermission("mcc.admin")) {
                        if (!(sender instanceof Player)) return false;
                        Player player = (Player) sender;
                        DefaultConfig.locations.put(args[1], player.getLocation());
                        player.sendMessage(DefaultConfig.prefix + "§aLocation successfully set.");
                    }
                } else if (args[0].equalsIgnoreCase("bs")) {
                    if (sender.hasPermission("mcc.admin")) {
                        if (Bukkit.getPlayer(args[1]) != null) {
                            Bukkit.getPlayer(args[1]).openInventory(InventoryManager.getBlacksmithGui());
                        }
                    }
                } else if (args[0].equalsIgnoreCase("sal")) {
                    if (sender.hasPermission("mcc.admin")) {
                        if (Bukkit.getPlayer(args[1]) != null) {
                            SalvagerManager.salvageItem(Bukkit.getPlayer(args[1]));
                        }
                    }
                } else if (args[0].equalsIgnoreCase("combat")) {
                    if (sender.hasPermission("mcc.admin")) {
                        if (Bukkit.getPlayer(args[1]) != null) {
                            Bukkit.getPlayer(args[1]).openInventory(InventoryManager.getCombatMerchantGui());
                        }
                    }
                } else if (args[0].equalsIgnoreCase("potion")) {
                    if (sender.hasPermission("mcc.admin")) {
                        if (Bukkit.getPlayer(args[1]) != null) {
                            Bukkit.getPlayer(args[1]).openInventory(InventoryManager.getPotionMerchant());
                        }
                    }
                } else if (args[0].equalsIgnoreCase("special")) {
                    if (sender.hasPermission("mcc.admin")) {
                        if (Bukkit.getPlayer(args[1]) != null) {
                            Bukkit.getPlayer(args[1]).openInventory(InventoryManager.getSpecialMerchant());
                        }
                    }
                } else if (args[0].equalsIgnoreCase("close")) {
                    if (sender instanceof Player) {
                        ((Player) sender).closeInventory();
                    }
                }
            }

            // /mcc rm <min> <max> <player>
            else if (args.length == 4) {
                if (args[0].equalsIgnoreCase("rm")) {
                    if (sender.hasPermission("mcc.admin")) {
                        ItemStack note = CustomItemManager.getRandomMoneyNote(Integer.parseInt(args[1]), Integer.parseInt(args[2]));
                        Player gifted = Bukkit.getPlayer(args[3]);
                        gifted.getInventory().addItem(note);
                    }
                }
            }
        }

        if (command.getLabel().equalsIgnoreCase("generator")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                player.openInventory(InventoryManager.getGeneratorGui(player));
            }
        }
        return false;
    }
}
