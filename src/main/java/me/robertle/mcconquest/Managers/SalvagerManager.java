package me.robertle.mcconquest.Managers;

import me.robertle.mcconquest.Core;
import me.robertle.mcconquest.InventoryUtil;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SalvagerManager {

    public static List<UUID> salvaging = new ArrayList<>();

    public static void salvageItem(Player player) {
        if (salvaging.contains(player.getUniqueId())) {
            if (InventoryUtil.verifyWeapon(player.getInventory().getItemInMainHand())) {
                String tier = InventoryUtil.getItemTier(player.getInventory().getItemInMainHand());
                if (tier.equalsIgnoreCase("S-TIER")) {
                    InventoryUtil.removeAnItemInHand(player);
                    Core.econ.depositPlayer(player, 100000);
                    player.sendMessage("§c§lSalvager §f> Thanks, I'll salvage this weapon for §a$100k§f.");
                    player.playSound(player.getLocation(), Sound.BLOCK_METAL_BREAK, 1f, 1f);
                } else if (tier.equalsIgnoreCase("A-TIER")) {
                    InventoryUtil.removeAnItemInHand(player);
                    Core.econ.depositPlayer(player, 60000);
                    player.sendMessage("§c§lSalvager §f> Thanks, I'll salvage this weapon for §a$60k§f.");
                    player.playSound(player.getLocation(), Sound.BLOCK_METAL_BREAK, 1f, 1f);
                } else {
                    InventoryUtil.removeAnItemInHand(player);
                    Core.econ.depositPlayer(player, 15000);
                    player.sendMessage("§c§lSalvager §f> Thanks, I'll salvage this weapon for §a$15k§f.");
                    player.playSound(player.getLocation(), Sound.BLOCK_METAL_BREAK, 1f, 1f);
                }
                salvaging.remove(player.getUniqueId());
            } else if (InventoryUtil.verifyArmor(player.getInventory().getItemInMainHand())) {
                String tier = InventoryUtil.getItemTier(player.getInventory().getItemInMainHand());
                if (tier.equalsIgnoreCase("S-TIER")) {
                    InventoryUtil.removeAnItemInHand(player);
                    Core.econ.depositPlayer(player, 75000);
                    player.sendMessage("§c§lSalvager §f> Thanks, I'll salvage this armor for §a$75k§f.");
                    player.playSound(player.getLocation(), Sound.BLOCK_METAL_BREAK, 1f, 1f);
                } else if (tier.equalsIgnoreCase("A-TIER")) {
                    InventoryUtil.removeAnItemInHand(player);
                    Core.econ.depositPlayer(player, 40000);
                    player.sendMessage("§c§lSalvager §f> Thanks, I'll salvage this armor for §a$40k§f.");
                    player.playSound(player.getLocation(), Sound.BLOCK_METAL_BREAK, 1f, 1f);
                } else {
                    InventoryUtil.removeAnItemInHand(player);
                    Core.econ.depositPlayer(player, 15000);
                    player.sendMessage("§c§lSalvager §f> Thanks, I'll salvage this armor for §a$15k§f.");
                    player.playSound(player.getLocation(), Sound.BLOCK_METAL_BREAK, 1f, 1f);
                }
                salvaging.remove(player.getUniqueId());
            } else {
                player.sendMessage("§c§lSalvager §f> §fI can only salvage armor and weapons.");
                salvaging.remove(player.getUniqueId());
                return;
            }
        } else {
            salvaging.add(player.getUniqueId());
            player.sendMessage("§c§lSalvager §f> §fClick me again with the item you want to salvage.");
            new BukkitRunnable() {
                public void run() {
                    salvaging.remove(player.getUniqueId());
                }
            }.runTaskLater(Core.instance, 200L);
        }
    }

}
