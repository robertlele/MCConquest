package me.robertle.mcconquest;

import me.robertle.mcconquest.Managers.CustomItemManager;
import me.robertle.mcconquest.Managers.InventoryManager;
import me.robertle.mcconquest.Managers.SalvagerManager;
import me.robertle.mcconquest.Managers.TutorialManager;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MCCCommands implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getLabel().equalsIgnoreCase("mcc")) {
            // /mcc give <item> /mcc bs <player>
            if (args.length <= 2) {
                if (args[0].equalsIgnoreCase("location")) {
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
                } else if (args[0].equalsIgnoreCase("mer")) {
                    if (sender.hasPermission("mcc.admin")) {
                        if (Bukkit.getPlayer(args[1]) != null) {
                            Bukkit.getPlayer(args[1]).openInventory(InventoryManager.getMergerGui());
                        }
                    }
                } else if (args[0].equalsIgnoreCase("sal")) {
                    if (sender.hasPermission("mcc.admin")) {
                        if (Bukkit.getPlayer(args[1]) != null) {
                            SalvagerManager.salvageItem(Bukkit.getPlayer(args[1]));
                        }
                    }
                } else if (args[0].equalsIgnoreCase("tutorial")) {
                    if (sender.hasPermission("mcc.admin")) {
                        if (Bukkit.getPlayer(args[1]) != null) {
                            TutorialManager.sendTutorial(Bukkit.getPlayer(args[1]));
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
                } else if (args[0].equalsIgnoreCase("food")) {
                    if (sender.hasPermission("mcc.admin")) {
                        if (Bukkit.getPlayer(args[1]) != null) {
                            Bukkit.getPlayer(args[1]).openInventory(InventoryManager.getFoodMerchant());
                        }
                    }
                } else if (args[0].equalsIgnoreCase("tools")) {
                    if (sender.hasPermission("mcc.admin")) {
                        if (Bukkit.getPlayer(args[1]) != null) {
                            Bukkit.getPlayer(args[1]).openInventory(InventoryManager.getToolsMerchant());
                        }
                    }
                } else if (args[0].equalsIgnoreCase("close")) {
                    if (sender instanceof Player) {
                        ((Player) sender).closeInventory();
                    }
                } else if (args[0].equalsIgnoreCase("warp")) {
                    if (sender instanceof Player) {
                        Player player = (Player) sender;
                        player.openInventory(InventoryManager.getWarpInventory());
                    }
                }
            }

            // /mcc rm <min> <max> <player>
            // /mcc gift <item> <amount> <player>
            // /mcc give <item> <amount> <player>
            else if (args.length == 4) {
                if (args[0].equalsIgnoreCase("rm")) {
                    if (sender.hasPermission("mcc.admin")) {
                        ItemStack note = CustomItemManager.getRandomMoneyNote(Integer.parseInt(args[1]), Integer.parseInt(args[2]));
                        Player gifted = Bukkit.getPlayer(args[3]);
                        gifted.getInventory().addItem(note);
                    }
                }

                if (args[0].equalsIgnoreCase("gift")) {
                    if (sender.hasPermission("mcc.admin")) {
                        UUID gifted = Core.getPlayerUUID(args[3]);
                        List<ItemStack> items = new ArrayList<>();
                        if (MCCPlayer.playerConfirms.containsKey(gifted))
                            items = MCCPlayer.playerConfirms.get(gifted);
                        for (int i = 0; i < Integer.parseInt(args[2]); i++) {
                            switch (args[1]) {
                                case "season":
                                    items.add(CustomItemManager.getCrate(Crate.SEASON_CRATE));
                                    break;
                                case "zeus":
                                    items.add(CustomItemManager.getBossEgg(MCCBoss.ZEUS));
                                    break;
                                case "hades":
                                    items.add(CustomItemManager.getBossEgg(MCCBoss.HADES));
                                    break;
                                case "godly":
                                    items.add(CustomItemManager.getCrate(Crate.GODLY_CRATE));
                                    break;
                            }
                        }
                        MCCPlayer.playerConfirms.put(gifted, items);
                        if (Bukkit.getOfflinePlayer(gifted).isOnline()) {
                            Bukkit.getPlayer(gifted).sendMessage(DefaultConfig.prefix + "§aYou have items to claim,");
                            TextComponent claim = new TextComponent("§a§lClick here to claim them.");
                            claim.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/claim"));
                            Bukkit.getPlayer(gifted).sendMessage(claim);
                        }
                    }
                }

                if (args[0].equalsIgnoreCase("give")) {
                    Player receiver = Bukkit.getPlayer(args[3]);
                    if (sender.hasPermission("mcc.admin")) {
                        for (int i = 0; i < Integer.parseInt(args[2]); i++) {
                            switch (args[1]) {
                                case "randomarmor":
                                    receiver.getInventory().addItem(CustomItemManager.getRandomArmor());
                                    break;
                                case "randomweapon":
                                    receiver.getInventory().addItem(CustomItemManager.getRandomWeapon());
                                    break;
                                case "armorvoucher":
                                    receiver.getInventory().addItem(CustomItemManager.getArmorVoucher());
                                    break;
                                case "weaponvoucher":
                                    receiver.getInventory().addItem(CustomItemManager.getWeaponVoucher());
                                    break;
                                case "swordvoucher":
                                    receiver.getInventory().addItem(CustomItemManager.getSwordVoucher());
                                    break;
                                case "axevoucher":
                                    receiver.getInventory().addItem(CustomItemManager.getAxeVoucher());
                                    break;
                                case "stierarmorvoucher":
                                    receiver.getInventory().addItem(CustomItemManager.getSTierArmorVoucher());
                                    break;
                                case "votecrate":
                                    receiver.getInventory().addItem(CustomItemManager.getCrate(Crate.VOTE_CRATE));
                                    break;
                                case "basiccrate":
                                    receiver.getInventory().addItem(CustomItemManager.getCrate(Crate.BASIC_CRATE));
                                    break;
                                case "supercrate":
                                    receiver.getInventory().addItem(CustomItemManager.getCrate(Crate.SUPER_CRATE));
                                    break;
                                case "ultracrate":
                                    receiver.getInventory().addItem(CustomItemManager.getCrate(Crate.ULTRA_CRATE));
                                    break;
                                case "godlycrate":
                                    receiver.getInventory().addItem(CustomItemManager.getCrate(Crate.GODLY_CRATE));
                                    break;
                                case "petcrate":
                                    receiver.getInventory().addItem(CustomItemManager.getCrate(Crate.PET_CRATE));
                                    break;
                                case "stiervoucher":
                                    receiver.getInventory().addItem(CustomItemManager.getSTierVoucher());
                                    break;
                                case "atiervoucher":
                                    receiver.getInventory().addItem(CustomItemManager.getATierVoucher());
                                    break;
                                case "weapondust":
                                    receiver.getInventory().addItem(CustomItemManager.getMergerDust(true));
                                    break;
                                case "armordust":
                                    receiver.getInventory().addItem(CustomItemManager.getMergerDust(false));
                                    break;
                                case "dragonset":
                                    receiver.getInventory().addItem(CustomItemManager.getArmorPiece(MCCArmor.DRAGON_HELMET));
                                    receiver.getInventory().addItem(CustomItemManager.getArmorPiece(MCCArmor.DRAGON_CHESTPLATE));
                                    receiver.getInventory().addItem(CustomItemManager.getArmorPiece(MCCArmor.DRAGON_LEGGINGS));
                                    receiver.getInventory().addItem(CustomItemManager.getArmorPiece(MCCArmor.DRAGON_BOOTS));
                                    receiver.getInventory().addItem(CustomItemManager.getWeapon(MCCWeapon.DRAGON_SWORD));
                                    receiver.getInventory().addItem(CustomItemManager.getWeapon(MCCWeapon.DRAGON_BOW));
                                    break;
                                case "zeusset":
                                    receiver.getInventory().addItem(CustomItemManager.getArmorPiece(MCCArmor.ZEUS_HELMET));
                                    receiver.getInventory().addItem(CustomItemManager.getArmorPiece(MCCArmor.ZEUS_CHESTPLATE));
                                    receiver.getInventory().addItem(CustomItemManager.getArmorPiece(MCCArmor.ZEUS_LEGGINGS));
                                    receiver.getInventory().addItem(CustomItemManager.getArmorPiece(MCCArmor.ZEUS_BOOTS));
                                    receiver.getInventory().addItem(CustomItemManager.getWeapon(MCCWeapon.ZEUS_SWORD));
                                    receiver.getInventory().addItem(CustomItemManager.getWeapon(MCCWeapon.ZEUS_BOW));
                                    receiver.getInventory().addItem(CustomItemManager.getArtifact(MCCArtifact.GOD_SLAYER));
                                    break;
                                case "hadesset":
                                    receiver.getInventory().addItem(CustomItemManager.getArmorPiece(MCCArmor.HADES_HELMET));
                                    receiver.getInventory().addItem(CustomItemManager.getArmorPiece(MCCArmor.HADES_CHESTPLATE));
                                    receiver.getInventory().addItem(CustomItemManager.getArmorPiece(MCCArmor.HADES_LEGGINGS));
                                    receiver.getInventory().addItem(CustomItemManager.getArmorPiece(MCCArmor.HADES_BOOTS));
                                    receiver.getInventory().addItem(CustomItemManager.getWeapon(MCCWeapon.HADES_AXE));
                                    receiver.getInventory().addItem(CustomItemManager.getWeapon(MCCWeapon.HADES_BOW));
                                    break;
                                case "giantset":
                                    receiver.getInventory().addItem(CustomItemManager.getArmorPiece(MCCArmor.GIANT_HELMET));
                                    receiver.getInventory().addItem(CustomItemManager.getArmorPiece(MCCArmor.GIANT_CHESTPLATE));
                                    receiver.getInventory().addItem(CustomItemManager.getArmorPiece(MCCArmor.GIANT_LEGGINGS));
                                    receiver.getInventory().addItem(CustomItemManager.getArmorPiece(MCCArmor.GIANT_BOOTS));
                                    receiver.getInventory().addItem(CustomItemManager.getWeapon(MCCWeapon.GIANT_AXE));
                                    receiver.getInventory().addItem(CustomItemManager.getWeapon(MCCWeapon.GIANT_BOW));
                                    receiver.getInventory().addItem(CustomItemManager.getArtifact(MCCArtifact.GIANT_GRINDER));
                                    break;
                                case "mooshroomset":
                                    receiver.getInventory().addItem(CustomItemManager.getArmorPiece(MCCArmor.MOOSHROOM_HELMET));
                                    receiver.getInventory().addItem(CustomItemManager.getArmorPiece(MCCArmor.MOOSHROOM_CHESTPLATE));
                                    receiver.getInventory().addItem(CustomItemManager.getArmorPiece(MCCArmor.MOOSHROOM_LEGGINGS));
                                    receiver.getInventory().addItem(CustomItemManager.getArmorPiece(MCCArmor.MOOSHROOM_BOOTS));
                                    receiver.getInventory().addItem(CustomItemManager.getWeapon(MCCWeapon.MOOSHROOM_AXE));
                                    break;
                                case "blazeset":
                                    receiver.getInventory().addItem(CustomItemManager.getArmorPiece(MCCArmor.BLAZE_HELMET));
                                    receiver.getInventory().addItem(CustomItemManager.getArmorPiece(MCCArmor.BLAZE_CHESTPLATE));
                                    receiver.getInventory().addItem(CustomItemManager.getArmorPiece(MCCArmor.BLAZE_LEGGINGS));
                                    receiver.getInventory().addItem(CustomItemManager.getArmorPiece(MCCArmor.BLAZE_BOOTS));
                                    receiver.getInventory().addItem(CustomItemManager.getWeapon(MCCWeapon.BLAZE_SWORD));
                                    receiver.getInventory().addItem(CustomItemManager.getWeapon(MCCWeapon.BLAZE_BOW));
                                    break;
                                case "pigmanset":
                                    receiver.getInventory().addItem(CustomItemManager.getArmorPiece(MCCArmor.PIGMAN_HELMET));
                                    receiver.getInventory().addItem(CustomItemManager.getArmorPiece(MCCArmor.PIGMAN_CHESTPLATE));
                                    receiver.getInventory().addItem(CustomItemManager.getArmorPiece(MCCArmor.PIGMAN_LEGGINGS));
                                    receiver.getInventory().addItem(CustomItemManager.getArmorPiece(MCCArmor.PIGMAN_BOOTS));
                                    receiver.getInventory().addItem(CustomItemManager.getWeapon(MCCWeapon.PIGMAN_SWORD));
                                    receiver.getInventory().addItem(CustomItemManager.getArtifact(MCCArtifact.PIGMAN_PICKAXE));
                                    break;
                                case "polarbearset":
                                    receiver.getInventory().addItem(CustomItemManager.getArmorPiece(MCCArmor.POLAR_BEAR_HELMET));
                                    receiver.getInventory().addItem(CustomItemManager.getArmorPiece(MCCArmor.POLAR_BEAR_CHESTPLATE));
                                    receiver.getInventory().addItem(CustomItemManager.getArmorPiece(MCCArmor.POLAR_BEAR_LEGGINGS));
                                    receiver.getInventory().addItem(CustomItemManager.getArmorPiece(MCCArmor.POLAR_BEAR_BOOTS));
                                    receiver.getInventory().addItem(CustomItemManager.getWeapon(MCCWeapon.POLAR_BEAR_AXE));
                                    break;
                                case "endermanset":
                                    receiver.getInventory().addItem(CustomItemManager.getArmorPiece(MCCArmor.ENDERMAN_HELMET));
                                    receiver.getInventory().addItem(CustomItemManager.getArmorPiece(MCCArmor.ENDERMAN_CHESTPLATE));
                                    receiver.getInventory().addItem(CustomItemManager.getArmorPiece(MCCArmor.ENDERMAN_LEGGINGS));
                                    receiver.getInventory().addItem(CustomItemManager.getArmorPiece(MCCArmor.ENDERMAN_BOOTS));
                                    receiver.getInventory().addItem(CustomItemManager.getWeapon(MCCWeapon.ENDERMAN_AXE));
                                    receiver.getInventory().addItem(CustomItemManager.getWeapon(MCCWeapon.ENDERMAN_BOW));
                                    break;
                                case "batset":
                                    receiver.getInventory().addItem(CustomItemManager.getArmorPiece(MCCArmor.BAT_HELMET));
                                    receiver.getInventory().addItem(CustomItemManager.getArmorPiece(MCCArmor.BAT_CHESTPLATE));
                                    receiver.getInventory().addItem(CustomItemManager.getArmorPiece(MCCArmor.BAT_LEGGINGS));
                                    receiver.getInventory().addItem(CustomItemManager.getArmorPiece(MCCArmor.BAT_BOOTS));
                                    receiver.getInventory().addItem(CustomItemManager.getWeapon(MCCWeapon.BAT_SWORD));
                                    break;
                                case "squidset":
                                    receiver.getInventory().addItem(CustomItemManager.getArmorPiece(MCCArmor.SQUID_HELMET));
                                    receiver.getInventory().addItem(CustomItemManager.getArmorPiece(MCCArmor.SQUID_CHESTPLATE));
                                    receiver.getInventory().addItem(CustomItemManager.getArmorPiece(MCCArmor.SQUID_LEGGINGS));
                                    receiver.getInventory().addItem(CustomItemManager.getArmorPiece(MCCArmor.SQUID_BOOTS));
                                    receiver.getInventory().addItem(CustomItemManager.getWeapon(MCCWeapon.SQUID_SWORD));
                                    receiver.getInventory().addItem(CustomItemManager.getArtifact(MCCArtifact.SQUID_ROD));
                                    break;
                                case "slimeset":
                                    receiver.getInventory().addItem(CustomItemManager.getArmorPiece(MCCArmor.SLIME_HELMET));
                                    receiver.getInventory().addItem(CustomItemManager.getArmorPiece(MCCArmor.SLIME_CHESTPLATE));
                                    receiver.getInventory().addItem(CustomItemManager.getArmorPiece(MCCArmor.SLIME_LEGGINGS));
                                    receiver.getInventory().addItem(CustomItemManager.getArmorPiece(MCCArmor.SLIME_BOOTS));
                                    receiver.getInventory().addItem(CustomItemManager.getWeapon(MCCWeapon.SLIME_SWORD));
                                    break;
                                case "golemset":
                                    receiver.getInventory().addItem(CustomItemManager.getArmorPiece(MCCArmor.GOLEM_HELMET));
                                    receiver.getInventory().addItem(CustomItemManager.getArmorPiece(MCCArmor.GOLEM_CHESTPLATE));
                                    receiver.getInventory().addItem(CustomItemManager.getArmorPiece(MCCArmor.GOLEM_LEGGINGS));
                                    receiver.getInventory().addItem(CustomItemManager.getArmorPiece(MCCArmor.GOLEM_BOOTS));
                                    receiver.getInventory().addItem(CustomItemManager.getWeapon(MCCWeapon.GOLEM_AXE));
                                    receiver.getInventory().addItem(CustomItemManager.getWeapon(MCCWeapon.GOLEM_BOW));
                                    break;
                                case "skeletonset":
                                    receiver.getInventory().addItem(CustomItemManager.getArmorPiece(MCCArmor.SKELETON_HELMET));
                                    receiver.getInventory().addItem(CustomItemManager.getArmorPiece(MCCArmor.SKELETON_CHESTPLATE));
                                    receiver.getInventory().addItem(CustomItemManager.getArmorPiece(MCCArmor.SKELETON_LEGGINGS));
                                    receiver.getInventory().addItem(CustomItemManager.getArmorPiece(MCCArmor.SKELETON_BOOTS));
                                    receiver.getInventory().addItem(CustomItemManager.getWeapon(MCCWeapon.SKELETON_BOW));
                                    break;
                                case "pigset":
                                    receiver.getInventory().addItem(CustomItemManager.getArmorPiece(MCCArmor.PIG_HELMET));
                                    receiver.getInventory().addItem(CustomItemManager.getArmorPiece(MCCArmor.PIG_CHESTPLATE));
                                    receiver.getInventory().addItem(CustomItemManager.getArmorPiece(MCCArmor.PIG_LEGGINGS));
                                    receiver.getInventory().addItem(CustomItemManager.getArmorPiece(MCCArmor.PIG_BOOTS));
                                    receiver.getInventory().addItem(CustomItemManager.getWeapon(MCCWeapon.PIG_SWORD));
                                    break;
                                case "silverfishset":
                                    receiver.getInventory().addItem(CustomItemManager.getArmorPiece(MCCArmor.SILVERFISH_HELMET));
                                    receiver.getInventory().addItem(CustomItemManager.getArmorPiece(MCCArmor.SILVERFISH_CHESTPLATE));
                                    receiver.getInventory().addItem(CustomItemManager.getArmorPiece(MCCArmor.SILVERFISH_LEGGINGS));
                                    receiver.getInventory().addItem(CustomItemManager.getArmorPiece(MCCArmor.SILVERFISH_BOOTS));
                                    receiver.getInventory().addItem(CustomItemManager.getWeapon(MCCWeapon.SILVERFISH_AXE));
                                    break;
                                case "guardianset":
                                    receiver.getInventory().addItem(CustomItemManager.getArtifact(MCCArtifact.GUARDIAN_ROD));
                                    break;
                                case "witherset":
                                    receiver.getInventory().addItem(CustomItemManager.getArtifact(MCCArtifact.WITHER_PICKAXE));
                                    break;
                                case "zombieset":
                                    receiver.getInventory().addItem(CustomItemManager.getArmorPiece(MCCArmor.ZOMBIE_HELMET));
                                    receiver.getInventory().addItem(CustomItemManager.getArmorPiece(MCCArmor.ZOMBIE_CHESTPLATE));
                                    receiver.getInventory().addItem(CustomItemManager.getArmorPiece(MCCArmor.ZOMBIE_LEGGINGS));
                                    receiver.getInventory().addItem(CustomItemManager.getArmorPiece(MCCArmor.ZOMBIE_BOOTS));
                                    receiver.getInventory().addItem(CustomItemManager.getWeapon(MCCWeapon.ZOMBIE_SWORD));
                                    receiver.getInventory().addItem(CustomItemManager.getArtifact(MCCArtifact.ZOMBIE_GRINDER));
                                    break;
                                case "knockbackset":
                                    receiver.getInventory().addItem(CustomItemManager.getArtifact(MCCArtifact.EXTREME_KNOCKBACK_STICK));
                                    receiver.getInventory().addItem(CustomItemManager.getArtifact(MCCArtifact.KNOCKBACK_STICK));
                                    break;
                                case "crystalbeater":
                                    receiver.getInventory().addItem(CustomItemManager.getArtifact(MCCArtifact.CRYSTAL_BEATER));
                                    break;
                                case "starter":
                                    receiver.getInventory().addItem(CustomItemManager.getWeapon(MCCWeapon.DIAMOND_SWORD));
                                    receiver.getInventory().addItem(CustomItemManager.getArtifact(MCCArtifact.IRON_PICKAXE));
                                    receiver.getInventory().addItem(CustomItemManager.getArtifact(MCCArtifact.FISHING_ROD));
                                    receiver.getInventory().addItem(CustomItemManager.getArmorPiece(MCCArmor.DIAMOND_HELMET));
                                    receiver.getInventory().addItem(CustomItemManager.getArmorPiece(MCCArmor.DIAMOND_CHESTPLATE));
                                    receiver.getInventory().addItem(CustomItemManager.getArmorPiece(MCCArmor.DIAMOND_LEGGINGS));
                                    receiver.getInventory().addItem(CustomItemManager.getArmorPiece(MCCArmor.DIAMOND_BOOTS));
                                    break;
                                case "blacksmith":
                                    receiver.getInventory().addItem(CustomItemManager.getBlacksmithsCoal());
                                    receiver.getInventory().addItem(CustomItemManager.getBlacksmithsMagmaRod());
                                    receiver.getInventory().addItem(CustomItemManager.getBlacksmithsMagicDust());
                                    receiver.getInventory().addItem(CustomItemManager.getBlacksmithsLifeOrb());
                                    break;
                                case "generatorvoucher":
                                    receiver.getInventory().addItem(CustomItemManager.getGeneratorVoucher());
                                    break;
                                case "generator":
                                    receiver.getInventory().addItem(CustomItemManager.getGenerator(GeneratorType.MONEY));
                                    receiver.getInventory().addItem(CustomItemManager.getGenerator(GeneratorType.INGOT));
                                    receiver.getInventory().addItem(CustomItemManager.getGenerator(GeneratorType.ESSENCE));
                                    break;
                                case "ironingot":
                                    receiver.getInventory().addItem(CustomItemManager.getIngot(false, 1));
                                    break;
                                case "goldingot":
                                    receiver.getInventory().addItem(CustomItemManager.getIngot(true, 1));
                                    break;
                                case "essence":
                                    receiver.getInventory().addItem(CustomItemManager.getEssence(false, 1));
                                    break;
                                case "rareessence":
                                    receiver.getInventory().addItem(CustomItemManager.getEssence(true, 1));
                                    break;
                                case "pets":
                                    for (Pet p : Pet.values()) {
                                        receiver.getInventory().addItem(CustomItemManager.getPetVoucher(p));
                                    }
                                    break;
                                case "kits":
                                    for (MCCKit kit : MCCKit.values()) {
                                        receiver.getInventory().addItem(CustomItemManager.getKitVoucher(kit));
                                    }
                                    break;
                                case "tags":
                                    for (Tag tag : Tag.values()) {
                                        receiver.getInventory().addItem(CustomItemManager.getTag(tag));
                                    }
                                    break;
                                case "boss":
                                    for (MCCBoss boss : MCCBoss.values()) {
                                        receiver.getInventory().addItem(CustomItemManager.getBossEgg(boss));
                                    }
                                    break;
                                case "rankgems":
                                    receiver.getInventory().addItem(CustomItemManager.getRankVoucher("Eta"));
                                    receiver.getInventory().addItem(CustomItemManager.getRankVoucher("Delta"));
                                    receiver.getInventory().addItem(CustomItemManager.getRankVoucher("Gamma"));
                                    receiver.getInventory().addItem(CustomItemManager.getRankVoucher("Alpha"));
                                    receiver.getInventory().addItem(CustomItemManager.getRankVoucher("Omega"));
                                    break;
                                case "etarankgem":
                                    receiver.getInventory().addItem(CustomItemManager.getRankVoucher("Eta"));
                                    break;
                                case "gammarankgem":
                                    receiver.getInventory().addItem(CustomItemManager.getRankVoucher("Gamma"));
                                    break;
                                case "hottag":
                                    receiver.getInventory().addItem(CustomItemManager.getTag(Tag.HOT));
                                    break;
                                case "onetag":
                                    receiver.getInventory().addItem(CustomItemManager.getTag(Tag.ONE));
                                    break;
                                case "lifeorb":
                                    receiver.getInventory().addItem(CustomItemManager.getBlacksmithsLifeOrb());
                                    break;
                                case "zeus":
                                    receiver.getInventory().addItem(CustomItemManager.getBossEgg(MCCBoss.ZEUS));
                                    break;
                                case "hades":
                                    receiver.getInventory().addItem(CustomItemManager.getBossEgg(MCCBoss.HADES));
                                    break;
                            }
                        }
                        if (sender instanceof Player) sender.sendMessage(DefaultConfig.prefix + "Item(s) given.");
                        return true;
                    }
                }
            }
        }

        if (command.getLabel().equalsIgnoreCase("analyze")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                if (args.length == 1) {
                    try {
                        if (Bukkit.getOfflinePlayer(args[0]).isOnline()) {
                            player.openInventory(InventoryManager.getAnalyzeInventory(Bukkit.getPlayer(args[0])));
                        } else {
                            player.sendMessage(DefaultConfig.prefix + "§cThat player is not online!");
                        }
                    } catch (Exception e) {
                        player.sendMessage(DefaultConfig.prefix + "§cThat player is not online!");
                    }
                } else player.sendMessage(DefaultConfig.prefix + "§cUsage: /a <player>");
            }
        }

        if (command.getLabel().equalsIgnoreCase("merchant")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                player.openInventory(InventoryManager.getMerchant());
            }
        }

        if (command.getLabel().equalsIgnoreCase("generator")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                player.openInventory(InventoryManager.getGeneratorGui(player));
            }
        }

        if (command.getLabel().equalsIgnoreCase("claim")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                if (MCCPlayer.playerConfirms.containsKey(player.getUniqueId())) {
                    for (ItemStack i : MCCPlayer.playerConfirms.get(player.getUniqueId())) {
                        if (i.getType() != Material.END_PORTAL_FRAME) {
                            player.getInventory().addItem(i);
                        } else {
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "amc sgive " + player.getName() + " season1 1");
                        }
                    }
                    MCCPlayer.playerConfirms.remove(player.getUniqueId());
                    player.sendMessage(DefaultConfig.prefix + "§aYou've successfully claimed your items.");
                } else {
                    player.sendMessage(DefaultConfig.prefix + "§cYou don't have any items to claim.");
                    return false;
                }
            }
        }

        if (command.getLabel().equalsIgnoreCase("shout")) {
            if (sender.hasPermission("mcc.shout")) {
                String message = DefaultConfig.prefix;
                for (int i = 0; i < args.length; i++) {
                    message += args[i] + " ";
                }
                Core.shout(message.replaceAll("&", "§"));
            }
        }

        if (command.getLabel().equalsIgnoreCase("debuff")) {
            if (sender.hasPermission("mcc.debuff") && sender instanceof Player) {
                Player player = (Player) sender;
                for (PotionEffect effect : player.getActivePotionEffects()) {
                    if (effect.getType().equals(PotionEffectType.POISON) || effect.getType().equals(PotionEffectType.WITHER) || effect.getType().equals(PotionEffectType.WEAKNESS) || effect.getType().equals(PotionEffectType.SLOW_DIGGING) || effect.getType().equals(PotionEffectType.SLOW) || effect.getType().equals(PotionEffectType.LEVITATION) || effect.getType().equals(PotionEffectType.HUNGER) || effect.getType().equals(PotionEffectType.CONFUSION) || effect.getType().equals(PotionEffectType.BLINDNESS)) {
                        player.removePotionEffect(effect.getType());
                    }
                }
                player.sendMessage(DefaultConfig.prefix + "All debuffs have been removed.");
                return true;
            } else {
                sender.sendMessage("§cInsufficient permission.");
                return false;
            }
        }

        if (command.getLabel().equalsIgnoreCase("punish")) {
            if (sender.hasPermission("mcc.staff") && sender instanceof Player) {
                Player player = (Player) sender;
                if (args.length == 1) {
                    player.openInventory(InventoryManager.getPunishInventory(args[0]));
                    return true;
                } else {
                    sender.sendMessage("§c/punish <player>");
                    return false;
                }
            } else {
                sender.sendMessage("§cInsufficient permission.");
                return false;
            }
        }

        if (command.getLabel().equalsIgnoreCase("mcchelp")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                StringUtil.sendCenteredMessage(player, "§f§m───────────§r §6Useful Commands §f§m───────────");
                player.sendMessage("");
                StringUtil.sendCenteredMessage(player, "§a§lEconomy");
                player.sendMessage("§f/bal <player> §eView a player's balance");
                player.sendMessage("§f/generator §eView your generators");
                player.sendMessage("§f/trade <player> §eTrade with another player");
                player.sendMessage("§f/market §eOpen the market menu");
                player.sendMessage("§f/mk sell <price> §eSell the item in your hand");
                player.sendMessage("§f/cf <player> §eCoinflip with another player");
                player.sendMessage("§f/pv §eOpens your personal vault (§aspawn zone only§e)");
                player.sendMessage("§f/kits §eView kits you have");
                player.sendMessage("");
                StringUtil.sendCenteredMessage(player, "§7§lOther");
                player.sendMessage("§f/wiki §eView link to wiki");
                player.sendMessage("§f/warp §eView warp locations");
                player.sendMessage("§f/trash §eOpen disposal");
                player.sendMessage("§f/report <player> <reason> §eReport a player");
                player.sendMessage("§f/analyze <player> §eView a player's loadout");
                player.sendMessage("§f/pet §eOpen the pet menu");
                player.sendMessage("§f/stats <player> §eView a player's stats");
                player.sendMessage("§f/leaderboard §eView the leaderboard");
                player.sendMessage("§f/tags §eOpen the tag menu");
                player.sendMessage("§f/event §eView information on the next or current event");
                player.sendMessage("§f/outpost §eView information on the outposts");
                player.sendMessage("§f/boss §eView information on the next boss");
                player.sendMessage("§f/clan §eView all clan commands");
                player.sendMessage("§f/war §eView all war commands");
                player.sendMessage("§f/vote §eView vote links");
                player.sendMessage("§f/reboot §eView when the server will reboot");
                StringUtil.sendCenteredMessage(player, "§f§m───────────────────────");
            }
        }

        if (command.getLabel().equalsIgnoreCase("reboot")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                player.sendMessage(DefaultConfig.prefix + "The server will be rebooting in §a" + DateUtil.getTimeTil(3, 0));
            }
        }

        if (command.getLabel().equalsIgnoreCase("leave")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                if (TutorialManager.tutorialPlayers.contains(player.getUniqueId())) {
                    TutorialManager.removeTutorial(player);
                } else {
                    player.sendMessage("§cYou aren't in the tutorial.");
                    return false;
                }
            }
        }

        if (command.getLabel().equalsIgnoreCase("wiki")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                TextComponent wiki = new TextComponent("§6§lWiki: §ewww.mcconquest.com/wiki");
                wiki.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://www.mcconquest.com/wiki"));
                player.sendMessage(wiki);
            }
        }

        if (command.getLabel().equalsIgnoreCase("discord")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                TextComponent discord = new TextComponent("§9§lDiscord: §ewww.mcconquest.com/discord");
                discord.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://www.mcconquest.com/discord"));
                player.sendMessage(discord);
            }
        }

        if (command.getLabel().equalsIgnoreCase("donate")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                TextComponent buy = new TextComponent("§a§lDonate: §ebuy.mcconquest.com");
                buy.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "http://buy.mcconquest.com"));
                player.sendMessage(buy);
            }
        }
        return false;
    }
}
