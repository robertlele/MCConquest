package me.robertle.mcconquest;

import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class SalvagerManager {

    public static void salvageItem(Player player) {
        if (InventoryUtil.verifyWeapon(player.getInventory().getItemInMainHand())) {
            String tier = InventoryUtil.getItemTier(player.getInventory().getItemInMainHand());
            if (tier.equalsIgnoreCase("S-TIER")) {
                InventoryUtil.removeAnItemInHand(player);
                Core.econ.depositPlayer(player, 200000);
                player.sendMessage("§c§lSalvager §f> Thanks, I'll salvage this weapon for §a$200k§f.");
                player.playSound(player.getLocation(), Sound.BLOCK_METAL_BREAK, 1f, 1f);
            } else if (tier.equalsIgnoreCase("A-TIER")) {
                InventoryUtil.removeAnItemInHand(player);
                Core.econ.depositPlayer(player, 150000);
                player.sendMessage("§c§lSalvager §f> Thanks, I'll salvage this weapon for §a$150k§f.");
                player.playSound(player.getLocation(), Sound.BLOCK_METAL_BREAK, 1f, 1f);
            } else {
                InventoryUtil.removeAnItemInHand(player);
                Core.econ.depositPlayer(player, 75000);
                player.sendMessage("§c§lSalvager §f> §aThanks, I'll salvage this weapon for §a$75k§f.");
                player.playSound(player.getLocation(), Sound.BLOCK_METAL_BREAK, 1f, 1f);
            }
        } else if (InventoryUtil.verifyArmor(player.getInventory().getItemInMainHand())) {
            String tier = InventoryUtil.getItemTier(player.getInventory().getItemInMainHand());
            if (tier.equalsIgnoreCase("S-TIER")) {
                InventoryUtil.removeAnItemInHand(player);
                Core.econ.depositPlayer(player, 150000);
                player.sendMessage("§c§lSalvager §f> Thanks, I'll salvage this armor for §a$150k§f.");
                player.playSound(player.getLocation(), Sound.BLOCK_METAL_BREAK, 1f, 1f);
            } else if (tier.equalsIgnoreCase("A-TIER")) {
                InventoryUtil.removeAnItemInHand(player);
                Core.econ.depositPlayer(player, 100000);
                player.sendMessage("§c§lSalvager §f> Thanks, I'll salvage this armor for §a$100k§f.");
                player.playSound(player.getLocation(), Sound.BLOCK_METAL_BREAK, 1f, 1f);
            } else {
                InventoryUtil.removeAnItemInHand(player);
                Core.econ.depositPlayer(player, 50000);
                player.sendMessage("§c§lSalvager §f> Thanks, I'll salvage this armor for §a$75k§f.");
                player.playSound(player.getLocation(), Sound.BLOCK_METAL_BREAK, 1f, 1f);
            }
        } else {
            player.sendMessage("§c§lSalvager §f> §fI can only salvage armor and weapons.");
            return;
        }
    }

}
