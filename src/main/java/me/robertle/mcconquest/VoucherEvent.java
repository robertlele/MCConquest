package me.robertle.mcconquest;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
                    InventoryUtil.removeAnItemInHand(e.getPlayer());
                    e.getPlayer().sendMessage(DefaultConfig.prefix + "§aYou've redeemed the money note: §6+$" + value);
                } else if (ItemHelper.getName(e.getItem()).equalsIgnoreCase("§6§lArmor Voucher")) {
                    ItemStack item = CustomItemManager.getRandomArmor();
                    e.getPlayer().getInventory().addItem(item);
                    Core.shout(DefaultConfig.prefix + "§6" + e.getPlayer().getName() + " opened " + item.getItemMeta().getDisplayName());
                    InventoryUtil.removeAnItemInHand(e.getPlayer());
                } else if (ItemHelper.getName(e.getItem()).equalsIgnoreCase("§6§lSpecial Armor Voucher")) {
                    ItemStack item = CustomItemManager.getSpecialRandomArmor();
                    e.getPlayer().getInventory().addItem(item);
                    Core.shout(DefaultConfig.prefix + "§6" + e.getPlayer().getName() + " opened " + item.getItemMeta().getDisplayName());
                    InventoryUtil.removeAnItemInHand(e.getPlayer());
                } else if (ItemHelper.getName(e.getItem()).equalsIgnoreCase("§6§lWeapon Voucher")) {
                    ItemStack item = CustomItemManager.getRandomWeapon();
                    e.getPlayer().getInventory().addItem(item);
                    Core.shout(DefaultConfig.prefix + "§6" + e.getPlayer().getName() + " opened " + item.getItemMeta().getDisplayName());
                    InventoryUtil.removeAnItemInHand(e.getPlayer());
                } else if (ItemHelper.getName(e.getItem()).equalsIgnoreCase("§6§lSpecial Weapon Voucher")) {
                    ItemStack item = CustomItemManager.getSpecialRandomWeapon();
                    e.getPlayer().getInventory().addItem(item);
                    Core.shout(DefaultConfig.prefix + "§6" + e.getPlayer().getName() + " opened " + item.getItemMeta().getDisplayName());
                    InventoryUtil.removeAnItemInHand(e.getPlayer());
                } else if (ItemHelper.getName(e.getItem()).equalsIgnoreCase("§6§lArtifact Voucher")) {
                    ItemStack item = CustomItemManager.getRandomArtifact();
                    e.getPlayer().getInventory().addItem(item);
                    Core.shout(DefaultConfig.prefix + "§6" + e.getPlayer().getName() + " opened " + item.getItemMeta().getDisplayName());
                    InventoryUtil.removeAnItemInHand(e.getPlayer());
                } else if (ItemHelper.getName(e.getItem()).equalsIgnoreCase("§e§lPet Voucher")) {
                    List<String> pets = new ArrayList<>();
                    pets.add("Golem");
                    Collections.shuffle(pets);
                    String pick = pets.get(Core.generateNumber(0, pets.size() - 1));
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "givecompanion " + e.getPlayer().getName() + " " + pick);
                    Core.shout(DefaultConfig.prefix + "§6" + e.getPlayer().getName() + " opened a " + pick + " pet.");
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
                    Core.shout(DefaultConfig.prefix + "§6" + e.getPlayer().getName() + " opened " + item.getItemMeta().getDisplayName());
                    InventoryUtil.removeAnItemInHand(e.getPlayer());
                } else if (ItemHelper.getName(e.getItem()).equalsIgnoreCase("§e§lA-Tier Voucher")) {
                    ItemStack item = CustomItemManager.getRandomATier();
                    e.getPlayer().getInventory().addItem(item);
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
                }
            }
        }
    }

}
