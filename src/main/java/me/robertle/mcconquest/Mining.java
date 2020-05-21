package me.robertle.mcconquest;

import com.hazebyte.crate.api.util.ItemHelper;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class Mining implements Listener {

    @EventHandler
    public void onMine(BlockBreakEvent e) {
        if (e.getPlayer().getGameMode() == GameMode.CREATIVE) return;
        if (e.getBlock().getType() == Material.IRON_ORE) {
            if (ItemHelper.hasName(e.getPlayer().getInventory().getItemInMainHand())) {
                String itemName = ItemHelper.getName(e.getPlayer().getInventory().getItemInMainHand());
                e.setDropItems(false);
                e.setExpToDrop(0);
                if (itemName.equalsIgnoreCase("§8Wither Pickaxe")) {
                    e.getPlayer().getInventory().addItem(CustomItemManager.getIngot(false, Core.generateNumber(1, 3) + 2));
                } else if (itemName.equalsIgnoreCase("§cPigman Pickaxe")) {
                    e.getPlayer().getInventory().addItem(CustomItemManager.getIngot(false, Core.generateNumber(1, 3) + 1));
                } else {
                    e.getPlayer().getInventory().addItem(CustomItemManager.getIngot(false, Core.generateNumber(1, 3)));
                }
                new BukkitRunnable() {
                    public void run() {
                        e.getBlock().setType(Material.BEDROCK);
                    }
                }.runTaskLater(Core.instance, 5);
                new BukkitRunnable() {
                    public void run() {
                        if (Core.chance(15)) e.getBlock().setType(Material.IRON_BLOCK);
                        else e.getBlock().setType(Material.IRON_ORE);
                    }
                }.runTaskLater(Core.instance, Core.generateNumber(160, 280));
            }
        } else if (e.getBlock().getType() == Material.IRON_BLOCK) {
            if (ItemHelper.hasName(e.getPlayer().getInventory().getItemInMainHand())) {
                String itemName = ItemHelper.getName(e.getPlayer().getInventory().getItemInMainHand());
                e.setDropItems(false);
                e.setExpToDrop(0);
                if (itemName.equalsIgnoreCase("§8Wither Pickaxe")) {
                    e.getPlayer().getInventory().addItem(CustomItemManager.getIngot(false, Core.generateNumber(4, 6) + 2));
                } else if (itemName.equalsIgnoreCase("§cPigman Pickaxe")) {
                    e.getPlayer().getInventory().addItem(CustomItemManager.getIngot(false, Core.generateNumber(4, 6) + 1));
                } else {
                    e.getPlayer().getInventory().addItem(CustomItemManager.getIngot(false, Core.generateNumber(4, 6)));
                }
                new BukkitRunnable() {
                    public void run() {
                        e.getBlock().setType(Material.BEDROCK);
                    }
                }.runTaskLater(Core.instance, 5);
                new BukkitRunnable() {
                    public void run() {
                        if (Core.chance(15)) e.getBlock().setType(Material.IRON_BLOCK);
                        else e.getBlock().setType(Material.IRON_ORE);
                    }
                }.runTaskLater(Core.instance, Core.generateNumber(160, 280));
            }
        } else if (e.getBlock().getType() == Material.GOLD_ORE) {
            if (ItemHelper.hasName(e.getPlayer().getInventory().getItemInMainHand())) {
                String itemName = ItemHelper.getName(e.getPlayer().getInventory().getItemInMainHand());
                e.setDropItems(false);
                e.setExpToDrop(0);
                int goldBless = 0;
                if (InventoryUtil.hasCustomEnchant(e.getPlayer(), "Gold Bless")) goldBless++;
                if (itemName.equalsIgnoreCase("§8Wither Pickaxe")) {
                    e.getPlayer().getInventory().addItem(CustomItemManager.getIngot(true, Core.generateNumber(1, 2) + 2 + goldBless));
                } else if (itemName.equalsIgnoreCase("§cPigman Pickaxe")) {
                    e.getPlayer().getInventory().addItem(CustomItemManager.getIngot(true, Core.generateNumber(1, 2) + 1 + goldBless));
                } else {
                    e.getPlayer().getInventory().addItem(CustomItemManager.getIngot(true, Core.generateNumber(1, 2) + goldBless));
                }
                new BukkitRunnable() {
                    public void run() {
                        e.getBlock().setType(Material.BEDROCK);
                    }
                }.runTaskLater(Core.instance, 5);
                new BukkitRunnable() {
                    public void run() {
                        if (Core.chance(15)) e.getBlock().setType(Material.GOLD_BLOCK);
                        else e.getBlock().setType(Material.GOLD_ORE);
                    }
                }.runTaskLater(Core.instance, Core.generateNumber(160, 280));
            }
        } else if (e.getBlock().getType() == Material.GOLD_BLOCK) {
            if (ItemHelper.hasName(e.getPlayer().getInventory().getItemInMainHand())) {
                String itemName = ItemHelper.getName(e.getPlayer().getInventory().getItemInMainHand());
                e.setDropItems(false);
                e.setExpToDrop(0);
                int goldBless = 0;
                if (InventoryUtil.hasCustomEnchant(e.getPlayer(), "Gold Bless")) goldBless++;
                if (itemName.equalsIgnoreCase("§8Wither Pickaxe")) {
                    e.getPlayer().getInventory().addItem(CustomItemManager.getIngot(true, Core.generateNumber(3, 4) + 2 + goldBless));
                } else if (itemName.equalsIgnoreCase("§cPigman Pickaxe")) {
                    e.getPlayer().getInventory().addItem(CustomItemManager.getIngot(true, Core.generateNumber(3, 4) + 1 + goldBless));
                } else {
                    e.getPlayer().getInventory().addItem(CustomItemManager.getIngot(true, Core.generateNumber(3, 4) + goldBless));
                }
                new BukkitRunnable() {
                    public void run() {
                        e.getBlock().setType(Material.BEDROCK);
                    }
                }.runTaskLater(Core.instance, 5);
                new BukkitRunnable() {
                    public void run() {
                        if (Core.chance(15)) e.getBlock().setType(Material.GOLD_BLOCK);
                        else e.getBlock().setType(Material.GOLD_ORE);
                    }
                }.runTaskLater(Core.instance, Core.generateNumber(160, 280));
            }
        }
    }

}
