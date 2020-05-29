package me.robertle.mcconquest;

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
                if (ItemHelper.getName(e.getItem()).equalsIgnoreCase("§2§lMoney Note")) {
                    String moneyLore = ItemHelper.getLore(e.getItem()).get(0);
                    int value = Integer.parseInt(moneyLore.substring(moneyLore.indexOf("$") + 1));
                    Core.econ.depositPlayer(e.getPlayer(), value);
                    if (Core.chance(1)) e.getPlayer().getInventory().addItem(CustomItemManager.getTag(Tag.CASH));;
                    InventoryUtil.removeAnItemInHand(e.getPlayer());
                    e.getPlayer().sendMessage(DefaultConfig.prefix + "§6You've redeemed the money note: §f+§a$" + value);
                } else if (ItemHelper.getName(e.getItem()).equalsIgnoreCase("§6§lArmor Voucher")) {
                    ItemStack item = CustomItemManager.getRandomArmor();
                    e.getPlayer().getInventory().addItem(item);
                    if (Core.chance(5)) e.getPlayer().getInventory().addItem(CustomItemManager.getTag(Tag.BTIER));
                    Core.shout(DefaultConfig.prefix + "§6" + e.getPlayer().getName() + " opened " + item.getItemMeta().getDisplayName());
                    InventoryUtil.removeAnItemInHand(e.getPlayer());
                } else if (ItemHelper.getName(e.getItem()).equalsIgnoreCase("§6§lSpecial Armor Voucher")) {
                    ItemStack item = CustomItemManager.getSpecialRandomArmor();
                    e.getPlayer().getInventory().addItem(item);
                    if (Core.chance(5)) e.getPlayer().getInventory().addItem(CustomItemManager.getTag(Tag.SPECIAL));
                    Core.shout(DefaultConfig.prefix + "§6" + e.getPlayer().getName() + " opened " + item.getItemMeta().getDisplayName());
                    InventoryUtil.removeAnItemInHand(e.getPlayer());
                } else if (ItemHelper.getName(e.getItem()).equalsIgnoreCase("§6§lWeapon Voucher")) {
                    ItemStack item = CustomItemManager.getRandomWeapon();
                    e.getPlayer().getInventory().addItem(item);
                    if (Core.chance(5)) e.getPlayer().getInventory().addItem(CustomItemManager.getTag(Tag.BTIER));
                    Core.shout(DefaultConfig.prefix + "§6" + e.getPlayer().getName() + " opened " + item.getItemMeta().getDisplayName());
                    InventoryUtil.removeAnItemInHand(e.getPlayer());
                } else if (ItemHelper.getName(e.getItem()).equalsIgnoreCase("§6§lSpecial Weapon Voucher")) {
                    ItemStack item = CustomItemManager.getSpecialRandomWeapon();
                    e.getPlayer().getInventory().addItem(item);
                    if (Core.chance(5)) e.getPlayer().getInventory().addItem(CustomItemManager.getTag(Tag.SPECIAL));
                    Core.shout(DefaultConfig.prefix + "§6" + e.getPlayer().getName() + " opened " + item.getItemMeta().getDisplayName());
                    InventoryUtil.removeAnItemInHand(e.getPlayer());
                } else if (ItemHelper.getName(e.getItem()).equalsIgnoreCase("§6§lArtifact Voucher")) {
                    ItemStack item = CustomItemManager.getRandomArtifact();
                    e.getPlayer().getInventory().addItem(item);
                    Core.shout(DefaultConfig.prefix + "§6" + e.getPlayer().getName() + " opened " + item.getItemMeta().getDisplayName());
                    InventoryUtil.removeAnItemInHand(e.getPlayer());
                } else if (ItemHelper.getName(e.getItem()).equalsIgnoreCase("§8§lGenerator Voucher")) {
                    int r = Core.generateNumber(0, 2);
                    if (r == 0) {
                        e.getPlayer().getInventory().addItem(CustomItemManager.getGenerator(GeneratorType.MONEY));
                        Core.shout(DefaultConfig.prefix + "§6" + e.getPlayer().getName() + " opened a money generator.");
                    } else if (r == 1) {
                        e.getPlayer().getInventory().addItem(CustomItemManager.getGenerator(GeneratorType.INGOT));
                        Core.shout(DefaultConfig.prefix + "§6" + e.getPlayer().getName() + " opened a ingot generator.");
                    } else if (r == 2) {
                        e.getPlayer().getInventory().addItem(CustomItemManager.getGenerator(GeneratorType.ESSENCE));
                        Core.shout(DefaultConfig.prefix + "§6" + e.getPlayer().getName() + " opened a essence generator.");
                    }
                    InventoryUtil.removeAnItemInHand(e.getPlayer());
                } else if (ItemHelper.getName(e.getItem()).equalsIgnoreCase("§4§lS-Tier Voucher")) {
                    ItemStack item = CustomItemManager.getRandomSTier();
                    e.getPlayer().getInventory().addItem(item);
                    if (Core.chance(5)) e.getPlayer().getInventory().addItem(CustomItemManager.getTag(Tag.STIER));
                    Core.shout(DefaultConfig.prefix + "§6" + e.getPlayer().getName() + " opened " + item.getItemMeta().getDisplayName());
                    InventoryUtil.removeAnItemInHand(e.getPlayer());
                } else if (ItemHelper.getName(e.getItem()).equalsIgnoreCase("§e§lA-Tier Voucher")) {
                    ItemStack item = CustomItemManager.getRandomATier();
                    e.getPlayer().getInventory().addItem(item);
                    if (Core.chance(5)) e.getPlayer().getInventory().addItem(CustomItemManager.getTag(Tag.ATIER));
                    Core.shout(DefaultConfig.prefix + "§6" + e.getPlayer().getName() + " opened " + item.getItemMeta().getDisplayName());
                    InventoryUtil.removeAnItemInHand(e.getPlayer());
                } else if (ItemHelper.getName(e.getItem()).equalsIgnoreCase("§a§lMoney Generator")) {
                    if (Generator.addNewGenerator(e.getPlayer(), GeneratorType.MONEY)) {
                        e.getPlayer().sendMessage(DefaultConfig.prefix + "§aMoney Generator successfully claimed.");
                        InventoryUtil.removeAnItemInHand(e.getPlayer());
                    } else {
                        e.getPlayer().sendMessage(DefaultConfig.prefix + "§cYour generator limit has already been reached.");
                    }
                } else if (ItemHelper.getName(e.getItem()).equalsIgnoreCase("§a§lIngot Generator")) {
                    if (Generator.addNewGenerator(e.getPlayer(), GeneratorType.INGOT)) {
                        e.getPlayer().sendMessage(DefaultConfig.prefix + "§aIngot Generator successfully claimed.");
                        InventoryUtil.removeAnItemInHand(e.getPlayer());
                    } else {
                        e.getPlayer().sendMessage(DefaultConfig.prefix + "§cYour generator limit has already been reached.");
                    }
                } else if (ItemHelper.getName(e.getItem()).equalsIgnoreCase("§a§lEssence Generator")) {
                    if (Generator.addNewGenerator(e.getPlayer(), GeneratorType.ESSENCE)) {
                        e.getPlayer().sendMessage(DefaultConfig.prefix + "§aEssence Generator successfully claimed.");
                        InventoryUtil.removeAnItemInHand(e.getPlayer());
                    } else {
                        e.getPlayer().sendMessage(DefaultConfig.prefix + "§cYour generator limit has already been reached.");
                    }
                } else if (ItemHelper.getName(e.getItem()).contains("Tag Voucher")) {
                    Tag tag = Tag.getTagFromTagString(ItemHelper.getName(e.getItem()).replace("Tag Voucher", ""));
                    Tags.giveTag(e.getPlayer(), tag);
                    e.getPlayer().sendMessage(DefaultConfig.prefix + "§aYou've successfully claimed the tag.");
                    InventoryUtil.removeAnItemInHand(e.getPlayer());
                } else if (ItemHelper.getName(e.getItem()).contains("Pet") && e.getItem().getType() == Material.PLAYER_HEAD) {
                    String petName = ItemHelper.getName(e.getItem()).substring(2).replace(" Pet", "").replaceAll(" ", "_");
                    String command = "givec " + e.getPlayer().getName() + " " + petName.toLowerCase();
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
                    InventoryUtil.removeAnItemInHand(e.getPlayer());
                }
            }
        }
    }

}
