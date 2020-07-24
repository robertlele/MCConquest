package me.robertle.mcconquest.Managers;

import me.robertle.mcconquest.*;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class MiningManager implements Listener {

    @EventHandler
    public void onMine(BlockBreakEvent e) {
        if (e.getPlayer().getGameMode() == GameMode.CREATIVE) return;
        int boost = 0;
        if (ItemHelper.hasName(e.getPlayer().getInventory().getItemInMainHand())) {
            String itemName = ItemHelper.getName(e.getPlayer().getInventory().getItemInMainHand());
            if (itemName.equalsIgnoreCase("§8Wither Pickaxe")) {
                boost += 2;
            } else if (itemName.equalsIgnoreCase("§cPigman Pickaxe")) {
                boost += 1;
            }
        }
        if (Pet.isSummoned(e.getPlayer(), Pet.PIG)) boost += 1;
        if (e.getBlock().getType() == Material.IRON_ORE) {
            if (ItemHelper.hasName(e.getPlayer().getInventory().getItemInMainHand())) {
                if (Core.chance(.10)) e.getPlayer().getInventory().addItem(CustomItemManager.getTag(Tag.MINER));
                e.setDropItems(false);
                e.setExpToDrop(0);
                e.getPlayer().getInventory().addItem(CustomItemManager.getIngot(false, Core.generateNumber(1, 3) + boost));
                if (Core.chance(.05)) {
                    e.getPlayer().getInventory().addItem(CustomItemManager.getGeneratorVoucher());
                    e.getPlayer().sendMessage(DefaultConfig.prefix + "§6§lYou found a generator voucher!");
                }
                if (ItemHelper.getName(e.getPlayer().getInventory().getItemInMainHand()).equalsIgnoreCase("§8Wither Pickaxe")) {
                    if (Core.chance(.10)) {
                        e.getPlayer().getInventory().addItem(CustomItemManager.getMergerDust(true));
                        e.getPlayer().sendMessage(DefaultConfig.prefix + "§6§lYou found a weapon dust!");
                    }
                }
                new BukkitRunnable() {
                    public void run() {
                        e.getBlock().setType(Material.STONE);
                    }
                }.runTaskLater(Core.instance, 5);
                new BukkitRunnable() {
                    public void run() {
                        if (Core.chance(10)) e.getBlock().setType(Material.IRON_BLOCK);
                        else e.getBlock().setType(Material.IRON_ORE);
                    }
                }.runTaskLater(Core.instance, Core.generateNumber(600, 800));
            } else {
                e.setCancelled(true);
            }
        } else if (e.getBlock().getType() == Material.IRON_BLOCK) {
            if (ItemHelper.hasName(e.getPlayer().getInventory().getItemInMainHand())) {
                if (Core.chance(.10)) e.getPlayer().getInventory().addItem(CustomItemManager.getTag(Tag.MINER));
                e.setDropItems(false);
                e.setExpToDrop(0);
                e.getPlayer().getInventory().addItem(CustomItemManager.getIngot(false, Core.generateNumber(4, 6) + boost));
                if (Core.chance(.05)) {
                    e.getPlayer().getInventory().addItem(CustomItemManager.getGeneratorVoucher());
                    e.getPlayer().sendMessage(DefaultConfig.prefix + "§6§lYou found a generator voucher!");
                }
                if (ItemHelper.getName(e.getPlayer().getInventory().getItemInMainHand()).equalsIgnoreCase("§8Wither Pickaxe")) {
                    if (Core.chance(.10)) {
                        e.getPlayer().getInventory().addItem(CustomItemManager.getMergerDust(true));
                        e.getPlayer().sendMessage(DefaultConfig.prefix + "§6§lYou found a weapon dust!");
                    }
                }
                new BukkitRunnable() {
                    public void run() {
                        e.getBlock().setType(Material.STONE);
                    }
                }.runTaskLater(Core.instance, 5);
                new BukkitRunnable() {
                    public void run() {
                        if (Core.chance(10)) e.getBlock().setType(Material.IRON_BLOCK);
                        else e.getBlock().setType(Material.IRON_ORE);
                    }
                }.runTaskLater(Core.instance, Core.generateNumber(600, 800));
            } else {
                e.setCancelled(true);
            }
        } else if (e.getBlock().getType() == Material.GOLD_ORE) {
            if (ItemHelper.hasName(e.getPlayer().getInventory().getItemInMainHand())) {
                if (Core.chance(.10)) e.getPlayer().getInventory().addItem(CustomItemManager.getTag(Tag.MINER));
                e.setDropItems(false);
                e.setExpToDrop(0);
                e.getPlayer().getInventory().addItem(CustomItemManager.getIngot(true, Core.generateNumber(1, 2) + boost));
                if (Core.chance(.05)) {
                    e.getPlayer().getInventory().addItem(CustomItemManager.getGeneratorVoucher());
                    e.getPlayer().sendMessage(DefaultConfig.prefix + "§6§lYou found a generator voucher!");
                }
                if (ItemHelper.getName(e.getPlayer().getInventory().getItemInMainHand()).equalsIgnoreCase("§8Wither Pickaxe")) {
                    if (Core.chance(.10)) {
                        e.getPlayer().getInventory().addItem(CustomItemManager.getMergerDust(true));
                        e.getPlayer().sendMessage(DefaultConfig.prefix + "§6§lYou found a weapon dust!");
                    }
                }
                new BukkitRunnable() {
                    public void run() {
                        e.getBlock().setType(Material.STONE);
                    }
                }.runTaskLater(Core.instance, 5);
                new BukkitRunnable() {
                    public void run() {
                        if (Core.chance(5)) e.getBlock().setType(Material.GOLD_BLOCK);
                        else e.getBlock().setType(Material.GOLD_ORE);
                    }
                }.runTaskLater(Core.instance, Core.generateNumber(600, 800));
            } else {
                e.setCancelled(true);
            }
        } else if (e.getBlock().getType() == Material.GOLD_BLOCK) {
            if (ItemHelper.hasName(e.getPlayer().getInventory().getItemInMainHand())) {
                if (Core.chance(.10)) e.getPlayer().getInventory().addItem(CustomItemManager.getTag(Tag.MINER));
                e.setDropItems(false);
                e.setExpToDrop(0);
                e.getPlayer().getInventory().addItem(CustomItemManager.getIngot(true, Core.generateNumber(3, 4) + boost));
                if (Core.chance(.05)) {
                    e.getPlayer().getInventory().addItem(CustomItemManager.getGeneratorVoucher());
                    e.getPlayer().sendMessage(DefaultConfig.prefix + "§6§lYou found a generator voucher!");
                }
                if (ItemHelper.getName(e.getPlayer().getInventory().getItemInMainHand()).equalsIgnoreCase("§8Wither Pickaxe")) {
                    if (Core.chance(.10)) {
                        e.getPlayer().getInventory().addItem(CustomItemManager.getMergerDust(true));
                        e.getPlayer().sendMessage(DefaultConfig.prefix + "§6§lYou found a weapon dust!");
                    }
                }
                new BukkitRunnable() {
                    public void run() {
                        e.getBlock().setType(Material.STONE);
                    }
                }.runTaskLater(Core.instance, 5);
                new BukkitRunnable() {
                    public void run() {
                        if (Core.chance(5)) e.getBlock().setType(Material.GOLD_BLOCK);
                        else e.getBlock().setType(Material.GOLD_ORE);
                    }
                }.runTaskLater(Core.instance, Core.generateNumber(600, 800));
            } else {
                e.setCancelled(true);
            }
        }
    }

}
