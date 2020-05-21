package me.robertle.mcconquest;

import com.hazebyte.crate.api.util.ItemHelper;
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
                    if (e.getPlayer().getInventory().getItemInMainHand().getAmount() > 1) {
                        e.getPlayer().getInventory().getItemInMainHand().setAmount(e.getPlayer().getInventory().getItemInMainHand().getAmount() - 1);
                    } else e.getPlayer().getInventory().setItemInMainHand(new ItemStack(Material.AIR));
                    e.getPlayer().sendMessage(DefaultConfig.prefix + "§aYou've redeemed the money note: §6+$" + value);
                } else if (ItemHelper.getName(e.getItem()).equalsIgnoreCase("§6§lArmor Voucher")) {
                    ItemStack item = CustomItemManager.getRandomArmor();
                    e.getPlayer().getInventory().addItem(item);
                    Core.shout(DefaultConfig.prefix + "§6" + e.getPlayer().getName() + " opened " + item.getItemMeta().getDisplayName());
                    if (e.getPlayer().getInventory().getItemInMainHand().getAmount() > 1) {
                        e.getPlayer().getInventory().getItemInMainHand().setAmount(e.getPlayer().getInventory().getItemInMainHand().getAmount() - 1);
                    } else e.getPlayer().getInventory().setItemInMainHand(new ItemStack(Material.AIR));
                } else if (ItemHelper.getName(e.getItem()).equalsIgnoreCase("§6§lSpecial Armor Voucher")) {
                    ItemStack item = CustomItemManager.getSpecialRandomArmor();
                    e.getPlayer().getInventory().addItem(item);
                    Core.shout(DefaultConfig.prefix + "§6" + e.getPlayer().getName() + " opened " + item.getItemMeta().getDisplayName());
                    if (e.getPlayer().getInventory().getItemInMainHand().getAmount() > 1) {
                        e.getPlayer().getInventory().getItemInMainHand().setAmount(e.getPlayer().getInventory().getItemInMainHand().getAmount() - 1);
                    } else e.getPlayer().getInventory().setItemInMainHand(new ItemStack(Material.AIR));
                } else if (ItemHelper.getName(e.getItem()).equalsIgnoreCase("§6§lWeapon Voucher")) {
                    ItemStack item = CustomItemManager.getRandomWeapon();
                    e.getPlayer().getInventory().addItem(item);
                    Core.shout(DefaultConfig.prefix + "§6" + e.getPlayer().getName() + " opened " + item.getItemMeta().getDisplayName());
                    if (e.getPlayer().getInventory().getItemInMainHand().getAmount() > 1) {
                        e.getPlayer().getInventory().getItemInMainHand().setAmount(e.getPlayer().getInventory().getItemInMainHand().getAmount() - 1);
                    } else e.getPlayer().getInventory().setItemInMainHand(new ItemStack(Material.AIR));
                } else if (ItemHelper.getName(e.getItem()).equalsIgnoreCase("§6§lSpecial Weapon Voucher")) {
                    ItemStack item = CustomItemManager.getSpecialRandomWeapon();
                    e.getPlayer().getInventory().addItem(item);
                    Core.shout(DefaultConfig.prefix + "§6" + e.getPlayer().getName() + " opened " + item.getItemMeta().getDisplayName());
                    if (e.getPlayer().getInventory().getItemInMainHand().getAmount() > 1) {
                        e.getPlayer().getInventory().getItemInMainHand().setAmount(e.getPlayer().getInventory().getItemInMainHand().getAmount() - 1);
                    } else e.getPlayer().getInventory().setItemInMainHand(new ItemStack(Material.AIR));
                } else if (ItemHelper.getName(e.getItem()).equalsIgnoreCase("§6§lArtifact Voucher")) {
                    ItemStack item = CustomItemManager.getRandomArtifact();
                    e.getPlayer().getInventory().addItem(item);
                    Core.shout(DefaultConfig.prefix + "§6" + e.getPlayer().getName() + " opened " + item.getItemMeta().getDisplayName());
                    if (e.getPlayer().getInventory().getItemInMainHand().getAmount() > 1) {
                        e.getPlayer().getInventory().getItemInMainHand().setAmount(e.getPlayer().getInventory().getItemInMainHand().getAmount() - 1);
                    } else e.getPlayer().getInventory().setItemInMainHand(new ItemStack(Material.AIR));
                } else if (ItemHelper.getName(e.getItem()).equalsIgnoreCase("§6§lSpecial Artifact Voucher")) {
                    ItemStack item = CustomItemManager.getSpecialRandomArtifact();
                    e.getPlayer().getInventory().addItem(item);
                    Core.shout(DefaultConfig.prefix + "§6" + e.getPlayer().getName() + " opened " + item.getItemMeta().getDisplayName());
                    if (e.getPlayer().getInventory().getItemInMainHand().getAmount() > 1) {
                        e.getPlayer().getInventory().getItemInMainHand().setAmount(e.getPlayer().getInventory().getItemInMainHand().getAmount() - 1);
                    } else e.getPlayer().getInventory().setItemInMainHand(new ItemStack(Material.AIR));
                } else if (ItemHelper.getName(e.getItem()).equalsIgnoreCase("§4§lS-Tier Voucher")) {
                    ItemStack item = CustomItemManager.getRandomSTier();
                    e.getPlayer().getInventory().addItem(item);
                    Core.shout(DefaultConfig.prefix + "§6" + e.getPlayer().getName() + " opened " + item.getItemMeta().getDisplayName());
                    if (e.getPlayer().getInventory().getItemInMainHand().getAmount() > 1) {
                        e.getPlayer().getInventory().getItemInMainHand().setAmount(e.getPlayer().getInventory().getItemInMainHand().getAmount() - 1);
                    } else e.getPlayer().getInventory().setItemInMainHand(new ItemStack(Material.AIR));
                } else if (ItemHelper.getName(e.getItem()).equalsIgnoreCase("§e§lA-Tier Voucher")) {
                    ItemStack item = CustomItemManager.getRandomATier();
                    e.getPlayer().getInventory().addItem(item);
                    Core.shout(DefaultConfig.prefix + "§6" + e.getPlayer().getName() + " opened " + item.getItemMeta().getDisplayName());
                    if (e.getPlayer().getInventory().getItemInMainHand().getAmount() > 1) {
                        e.getPlayer().getInventory().getItemInMainHand().setAmount(e.getPlayer().getInventory().getItemInMainHand().getAmount() - 1);
                    } else e.getPlayer().getInventory().setItemInMainHand(new ItemStack(Material.AIR));
                }
            }
        }
    }

}
