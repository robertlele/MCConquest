package me.robertle.mcconquest;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Iterator;
import java.util.List;

public class MCCEvents implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerDeath(PlayerDeathEvent e) {
        e.setShouldDropExperience(false);
        e.setKeepLevel(false);

        if (Challenge.activeChallenge != null && Challenge.activeChallenge.team1Participants.contains(e.getEntity().getPlayer())) {
            Challenge.activeChallenge.team1Participants.remove(e.getEntity().getPlayer());
            e.getEntity().sendMessage(DefaultConfig.prefix + "§cYou've been eliminated from the clan battle.");
        } else if (Challenge.activeChallenge != null && Challenge.activeChallenge.team2Participants.contains(e.getEntity().getPlayer())) {
            Challenge.activeChallenge.team2Participants.remove(e.getEntity().getPlayer());
            e.getEntity().sendMessage(DefaultConfig.prefix + "§cYou've been eliminated from the clan battle.");
        }

        //Remove armor & weapons from drops
        List<ItemStack> currentDrops = e.getDrops();
        Iterator<ItemStack> newDrops = currentDrops.iterator();
        while (newDrops.hasNext()) {
            ItemStack i = newDrops.next();
            if (i.getType() == Material.IRON_HELMET || i.getType() == Material.IRON_CHESTPLATE || i.getType() == Material.IRON_LEGGINGS || i.getType() == Material.IRON_BOOTS || i.getType() == Material.DIAMOND_HELMET || i.getType() == Material.DIAMOND_CHESTPLATE || i.getType() == Material.DIAMOND_LEGGINGS || i.getType() == Material.DIAMOND_BOOTS || i.getType() == Material.STICK || i.getType() == Material.GOLDEN_SWORD || i.getType() == Material.IRON_SWORD || i.getType() == Material.DIAMOND_SWORD || i.getType() == Material.DIAMOND_AXE || i.getType() == Material.BOW) {
                newDrops.remove();
            }
        }

        //Add if statements here to check if lives should be deducted
        for (ItemStack itemStack : e.getEntity().getInventory().getStorageContents()) {
            if (ItemHelper.hasLore(itemStack) && ItemHelper.getLore(itemStack).size() > 1) {
                String lore = ItemHelper.getLore(itemStack).get(1);
                if (lore.contains("Lives")) {
                    int index = lore.indexOf("l");
                    int lives = Integer.parseInt(lore.substring(index + 1, index + 2));
                    if (lives != 1) {
                        List<String> lores = ItemHelper.getLore(itemStack);
                        lives--;
                        lores.set(1, "§a§l" + lives + " Lives");
                        e.getItemsToKeep().add(ItemHelper.setLore(itemStack, lores));
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
                    if (lives != 1) {
                        List<String> lores = ItemHelper.getLore(armorContents[i]);
                        lives--;
                        lores.set(1, "§a§l" + lives + " Lives");
                        ItemHelper.setLore(armorContents[i], lores);
                        e.getItemsToKeep().add(armorContents[i]);
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
            Core.econ.depositPlayer(e.getEntity().getKiller(), (int) tax);
            e.getEntity().sendMessage(DefaultConfig.prefix + "§fYou gained §a$" + ((int) tax) + " for killing §6" + e.getEntity().getName() + "§f.");
        }
    }

    @EventHandler
    public void onDrinkMilk(PlayerItemConsumeEvent e) {
        if (e.getItem().getType() == Material.MILK_BUCKET) {
            if (e.getItem().getAmount() <= 1) {
                e.setReplacement(new ItemStack(Material.AIR));
            }
        }
    }

}
