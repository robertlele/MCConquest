package me.robertle.mcconquest;

import me.robertle.mcconquest.Managers.BossManager;
import me.robertle.mcconquest.Managers.CustomItemManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public class VoucherEvent implements Listener {

    @EventHandler
    public void onRightClickVoucher(PlayerInteractEvent e) {
        if (e.getHand() == EquipmentSlot.OFF_HAND) return;
        if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (ItemHelper.hasName(e.getItem()) && ItemHelper.hasLore(e.getItem())) {
                if (ItemHelper.getName(e.getItem()).equalsIgnoreCase("§a§lMoney Note")) {
                    String moneyLore = ItemHelper.getLore(e.getItem()).get(0);
                    int value = Integer.parseInt(moneyLore.substring(moneyLore.indexOf("$") + 1));
                    if (Pet.isSummoned(e.getPlayer(), Pet.SHEEP)) {
                        value *= 1.25;
                        Core.econ.depositPlayer(e.getPlayer(), value);
                    } else Core.econ.depositPlayer(e.getPlayer(), value);
                    if (Core.chance(.5)) e.getPlayer().getInventory().addItem(CustomItemManager.getTag(Tag.CASH));
                    InventoryUtil.removeAnItemInHand(e.getPlayer());
                    e.getPlayer().sendMessage(DefaultConfig.prefix + "§6You've redeemed the money note: §f+§a$" + value);
                    e.setCancelled(true);
                } else if (ItemHelper.getName(e.getItem()).equalsIgnoreCase("§6§lArmor Voucher")) {
                    ItemStack item = CustomItemManager.getRandomArmor();
                    e.getPlayer().getInventory().addItem(item);
                    if (Core.chance(.5)) e.getPlayer().getInventory().addItem(CustomItemManager.getTag(Tag.BTIER));
                    else if (Core.chance(.5))
                        e.getPlayer().getInventory().addItem(CustomItemManager.getTag(Tag.SPECIAL));
                    Core.shout(DefaultConfig.prefix + "§6" + e.getPlayer().getName() + " §fopened " + item.getItemMeta().getDisplayName());
                    InventoryUtil.removeAnItemInHand(e.getPlayer());
                    e.setCancelled(true);
                } else if (ItemHelper.getName(e.getItem()).equalsIgnoreCase("§6§lWeapon Voucher")) {
                    ItemStack item = CustomItemManager.getRandomWeapon();
                    e.getPlayer().getInventory().addItem(item);
                    if (Core.chance(.5)) e.getPlayer().getInventory().addItem(CustomItemManager.getTag(Tag.BTIER));
                    else if (Core.chance(.5))
                        e.getPlayer().getInventory().addItem(CustomItemManager.getTag(Tag.SPECIAL));
                    Core.shout(DefaultConfig.prefix + "§6" + e.getPlayer().getName() + " §fopened " + item.getItemMeta().getDisplayName());
                    InventoryUtil.removeAnItemInHand(e.getPlayer());
                    e.setCancelled(true);
                } else if (ItemHelper.getName(e.getItem()).equalsIgnoreCase("§6§lSword Voucher")) {
                    ItemStack item = CustomItemManager.getRandomSword();
                    e.getPlayer().getInventory().addItem(item);
                    Core.shout(DefaultConfig.prefix + "§6" + e.getPlayer().getName() + " §fopened " + item.getItemMeta().getDisplayName());
                    InventoryUtil.removeAnItemInHand(e.getPlayer());
                } else if (ItemHelper.getName(e.getItem()).equalsIgnoreCase("§6§lAxe Voucher")) {
                    ItemStack item = CustomItemManager.getRandomAxe();
                    e.getPlayer().getInventory().addItem(item);
                    Core.shout(DefaultConfig.prefix + "§6" + e.getPlayer().getName() + " §fopened " + item.getItemMeta().getDisplayName());
                    InventoryUtil.removeAnItemInHand(e.getPlayer());
                    e.setCancelled(true);
                } else if (ItemHelper.getName(e.getItem()).equalsIgnoreCase("§4§lS-Tier §6§lArmor Voucher")) {
                    ItemStack item = CustomItemManager.getRandomSArmor();
                    e.getPlayer().getInventory().addItem(item);
                    Core.shout(DefaultConfig.prefix + "§6" + e.getPlayer().getName() + " §fopened " + item.getItemMeta().getDisplayName());
                    InventoryUtil.removeAnItemInHand(e.getPlayer());
                    e.setCancelled(true);
                } else if (ItemHelper.getName(e.getItem()).equalsIgnoreCase("§8§lGenerator Voucher")) {
                    int r = Core.generateNumber(0, 2);
                    if (r == 0) {
                        e.getPlayer().getInventory().addItem(CustomItemManager.getGenerator(GeneratorType.MONEY));
                        Core.shout(DefaultConfig.prefix + "§6" + e.getPlayer().getName() + " §fopened a §6Money Generator");
                    } else if (r == 1) {
                        e.getPlayer().getInventory().addItem(CustomItemManager.getGenerator(GeneratorType.INGOT));
                        Core.shout(DefaultConfig.prefix + "§6" + e.getPlayer().getName() + " §fopened a §6Ingot Generator.");
                    } else if (r == 2) {
                        e.getPlayer().getInventory().addItem(CustomItemManager.getGenerator(GeneratorType.ESSENCE));
                        Core.shout(DefaultConfig.prefix + "§6" + e.getPlayer().getName() + " §fopened a §6Essence Generator.");
                    }
                    InventoryUtil.removeAnItemInHand(e.getPlayer());
                    e.setCancelled(true);
                } else if (ItemHelper.getName(e.getItem()).equalsIgnoreCase("§4§lS-Tier Voucher")) {
                    ItemStack item = CustomItemManager.getRandomSTier();
                    e.getPlayer().getInventory().addItem(item);
                    if (Core.chance(2.5)) e.getPlayer().getInventory().addItem(CustomItemManager.getTag(Tag.STIER));
                    Core.shout(DefaultConfig.prefix + "§6" + e.getPlayer().getName() + " §fopened " + item.getItemMeta().getDisplayName());
                    InventoryUtil.removeAnItemInHand(e.getPlayer());
                    e.setCancelled(true);
                } else if (ItemHelper.getName(e.getItem()).equalsIgnoreCase("§e§lA-Tier Voucher")) {
                    ItemStack item = CustomItemManager.getRandomATier();
                    e.getPlayer().getInventory().addItem(item);
                    if (Core.chance(2.5)) e.getPlayer().getInventory().addItem(CustomItemManager.getTag(Tag.ATIER));
                    Core.shout(DefaultConfig.prefix + "§6" + e.getPlayer().getName() + " §fopened " + item.getItemMeta().getDisplayName());
                    InventoryUtil.removeAnItemInHand(e.getPlayer());
                    e.setCancelled(true);
                } else if (ItemHelper.getName(e.getItem()).equalsIgnoreCase("§a§lMoney Generator")) {
                    if (Generator.addNewGenerator(e.getPlayer(), GeneratorType.MONEY)) {
                        e.getPlayer().sendMessage(DefaultConfig.prefix + "§aMoney Generator successfully claimed.");
                        InventoryUtil.removeAnItemInHand(e.getPlayer());
                    } else {
                        e.getPlayer().sendMessage(DefaultConfig.prefix + "§cYour generator limit has already been reached.");
                    }
                    e.setCancelled(true);
                } else if (ItemHelper.getName(e.getItem()).equalsIgnoreCase("§a§lIngot Generator")) {
                    if (Generator.addNewGenerator(e.getPlayer(), GeneratorType.INGOT)) {
                        e.getPlayer().sendMessage(DefaultConfig.prefix + "§aIngot Generator successfully claimed.");
                        InventoryUtil.removeAnItemInHand(e.getPlayer());
                    } else {
                        e.getPlayer().sendMessage(DefaultConfig.prefix + "§cYour generator limit has already been reached.");
                    }
                    e.setCancelled(true);
                } else if (ItemHelper.getName(e.getItem()).equalsIgnoreCase("§a§lEssence Generator")) {
                    if (Generator.addNewGenerator(e.getPlayer(), GeneratorType.ESSENCE)) {
                        e.getPlayer().sendMessage(DefaultConfig.prefix + "§aEssence Generator successfully claimed.");
                        InventoryUtil.removeAnItemInHand(e.getPlayer());
                    } else {
                        e.getPlayer().sendMessage(DefaultConfig.prefix + "§cYour generator limit has already been reached.");
                    }
                    e.setCancelled(true);
                } else if (ItemHelper.getName(e.getItem()).contains("Tag Voucher")) {
                    Tag tag = Tag.getTagFromTagString(ItemHelper.getName(e.getItem()).replace(" §fTag Voucher", ""));
                    Tags.giveTag(e.getPlayer(), tag);
                    e.getPlayer().sendMessage(DefaultConfig.prefix + "§aYou've successfully claimed the tag.");
                    InventoryUtil.removeAnItemInHand(e.getPlayer());
                    e.setCancelled(true);
                } else if (ItemHelper.getName(e.getItem()).contains("Pet") && e.getItem().getType() == Material.PLAYER_HEAD) {
                    String petName = ItemHelper.getName(e.getItem()).substring(2).replace(" Pet", "").replaceAll(" ", "_");
                    String command = "givec " + e.getPlayer().getName() + " " + petName.toLowerCase();
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
                    e.getPlayer().sendMessage("§e§lPet §r> You've claimed the " + ItemHelper.getName(e.getItem()));
                    InventoryUtil.removeAnItemInHand(e.getPlayer());
                    e.setCancelled(true);
                } else if (ItemHelper.getName(e.getItem()).contains("Kit") && e.getItem().getType() == Material.DIAMOND) {
                    String kitName = ItemHelper.getName(e.getItem()).substring(4).replace(" Kit", "").replaceAll(" ", "_");
                    String command = "lp user " + e.getPlayer().getName() + " permission set ultimatekits.claim." + kitName.toLowerCase();
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
                    e.getPlayer().sendMessage(DefaultConfig.prefix + "You've claimed the " + ItemHelper.getName(e.getItem()));
                    InventoryUtil.removeAnItemInHand(e.getPlayer());
                    e.setCancelled(true);
                } else if (ItemHelper.getName(e.getItem()).contains("Rank") && e.getItem().getType() == Material.EMERALD) {
                    String rankName = ItemHelper.getName(e.getItem()).substring(4).replace(" Rank Gem", "").replaceAll(" ", "_");
                    String command = "lp user " + e.getPlayer().getName() + " parent add " + rankName.toLowerCase();
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
                    e.getPlayer().sendMessage(DefaultConfig.prefix + "You've claimed the " + ItemHelper.getName(e.getItem()));
                    InventoryUtil.removeAnItemInHand(e.getPlayer());
                    e.setCancelled(true);
                }
            }
        }

        if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (ItemHelper.hasName(e.getItem()) && ItemHelper.hasLore(e.getItem())) {
                if (!WGRegionManager.inRegion(e.getPlayer(), "nopvp")) {
                    if (ItemHelper.getName(e.getItem()).equalsIgnoreCase("§6§lZeus Summoner")) {
                        if (BossManager.activeBosses.size() > 0) {
                            e.setCancelled(true);
                            e.getPlayer().sendMessage(DefaultConfig.prefix + "§cThere's currently already a boss summoned.");
                            return;
                        }
                        e.setCancelled(true);
                        BossManager.spawnBoss(MCCBoss.ZEUS, e.getClickedBlock().getLocation().add(0, 1, 0));
                        Core.shout("§4§lBoss §f> §6" + e.getPlayer().getName() + " §fhas summoned §6§lZeus §fat §e" + Core.getStringFromLocation(e.getClickedBlock().getLocation()));
                        InventoryUtil.removeAnItemInHand(e.getPlayer());
                    } else if (ItemHelper.getName(e.getItem()).equalsIgnoreCase("§4§lHades Summoner")) {
                        if (BossManager.activeBosses.size() > 0) {
                            e.setCancelled(true);
                            e.getPlayer().sendMessage(DefaultConfig.prefix + "§cThere's currently already a boss summoned.");
                            return;
                        }
                        e.setCancelled(true);
                        BossManager.spawnBoss(MCCBoss.HADES, e.getClickedBlock().getLocation().add(0, 1, 0));
                        Core.shout("§4§lBoss §f> §6" + e.getPlayer().getName() + " §fhas summoned §4§lHades §fat §e" + Core.getStringFromLocation(e.getClickedBlock().getLocation()));
                        InventoryUtil.removeAnItemInHand(e.getPlayer());
                    }
                }
            }
        }
    }

}
