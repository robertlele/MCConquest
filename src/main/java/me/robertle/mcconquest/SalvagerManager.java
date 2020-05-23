package me.robertle.mcconquest;

import org.bukkit.entity.Player;

public class SalvagerManager {

    public static void salvageItem(Player player) {
        if (InventoryUtil.verifyWeapon(player.getInventory().getItemInMainHand())) {
            String tier = InventoryUtil.getItemTier(player.getInventory().getItemInMainHand());
            if (tier.equalsIgnoreCase("S-TIER")) {
                InventoryUtil.removeAnItemInHand(player);
                Core.econ.depositPlayer(player, 200000);
                player.sendMessage("§a§lSalvager §f> §aThanks, I'll salvage this weapon for $200k.");
            } else if (tier.equalsIgnoreCase("A-TIER")) {
                InventoryUtil.removeAnItemInHand(player);
                Core.econ.depositPlayer(player, 150000);
                player.sendMessage("§a§lSalvager §f> §aThanks, I'll salvage this weapon for $150k.");
            } else {
                InventoryUtil.removeAnItemInHand(player);
                Core.econ.depositPlayer(player, 75000);
                player.sendMessage("§a§lSalvager §f> §aThanks, I'll salvage this weapon for $75k.");
            }
        } else if (InventoryUtil.verifyArmor(player.getInventory().getItemInMainHand())) {
            String tier = InventoryUtil.getItemTier(player.getInventory().getItemInMainHand());
            if (tier.equalsIgnoreCase("S-TIER")) {
                InventoryUtil.removeAnItemInHand(player);
                Core.econ.depositPlayer(player, 150000);
                player.sendMessage("§a§lSalvager §f> §aThanks, I'll salvage this armor for $150k.");
            } else if (tier.equalsIgnoreCase("A-TIER")) {
                InventoryUtil.removeAnItemInHand(player);
                Core.econ.depositPlayer(player, 100000);
                player.sendMessage("§a§lSalvager §f> §aThanks, I'll salvage this armor for $100k.");
            } else {
                InventoryUtil.removeAnItemInHand(player);
                Core.econ.depositPlayer(player, 50000);
                player.sendMessage("§a§lSalvager §f> §aThanks, I'll salvage this armor for $75k.");
            }
        } else {
            player.sendMessage("§a§lSalvager §f> §cI can only salvage weapons and armor.");
            return;
        }
    }

}
