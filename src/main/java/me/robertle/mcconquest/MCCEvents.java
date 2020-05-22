package me.robertle.mcconquest;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class MCCEvents implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerDeath(PlayerDeathEvent e) {
        e.setKeepInventory(true);
        e.getDrops().clear();
        e.setShouldDropExperience(false);
        e.setKeepLevel(false);
        //Add if statements here to check if lives should be deducted
        for (ItemStack itemStack : e.getEntity().getInventory().getStorageContents()) {
            if (ItemHelper.hasLore(itemStack) && ItemHelper.getLore(itemStack).size() > 1) {
                String lore = ItemHelper.getLore(itemStack).get(1);
                if (lore.contains("Lives")) {
                    int index = lore.indexOf("l");
                    int lives = Integer.parseInt(lore.substring(index + 1, index + 2));
                    if (lives == 1) {
                        e.getEntity().getInventory().remove(itemStack);
                    } else {
                        List<String> lores = ItemHelper.getLore(itemStack);
                        lives--;
                        lores.set(1, "§a§l" + lives + " Lives");
                        ItemHelper.setLore(itemStack, lores);
                    }
                }
            }
        }
        ItemStack[] armorContents = e.getEntity().getInventory().getArmorContents();
        for (int i = 0; i < armorContents.length; i++) {
            if (armorContents[i] != null && ItemHelper.hasLore(armorContents[i]) && ItemHelper.getLore(armorContents[i]).size() > 1) {
                String lore = ItemHelper.getLore(armorContents[i]).get(1);
                if (lore.contains("Lives")) {
                    int index = lore.indexOf("l");
                    int lives = Integer.parseInt(lore.substring(index + 1, index + 2));
                    if (lives == 1) {
                        armorContents[i] = null;
                    } else {
                        List<String> lores = ItemHelper.getLore(armorContents[i]);
                        lives--;
                        lores.set(1, "§a§l" + lives + " Lives");
                        ItemHelper.setLore(armorContents[i], lores);
                    }
                }
            }
        }
        e.getEntity().getInventory().setArmorContents(armorContents);
    }

    @EventHandler
    public void onDeathTax(PlayerDeathEvent e) {
        double tax = Core.econ.getBalance(e.getEntity()) * .15;
        Core.econ.withdrawPlayer(e.getEntity(), (int) tax);
        if (e.getEntity().getKiller() != null) {
            Core.econ.depositPlayer(e.getEntity().getKiller(), tax);
        }
    }

}
