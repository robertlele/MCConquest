package me.robertle.mcconquest;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Iterator;
import java.util.List;

public class MCCEvents implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerDeath(PlayerDeathEvent e) {
        e.setShouldDropExperience(false);
        e.setKeepLevel(false);

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

        if (Challenge.activeChallenge != null && Challenge.activeChallenge.team1Participants.contains(e.getEntity().getPlayer())) {
            Challenge.activeChallenge.team1Participants.remove(e.getEntity().getPlayer());
            e.getEntity().sendMessage(DefaultConfig.prefix + "§cYou've been eliminated from the clan battle.");
            return;
        } else if (Challenge.activeChallenge != null && Challenge.activeChallenge.team2Participants.contains(e.getEntity().getPlayer())) {
            Challenge.activeChallenge.team2Participants.remove(e.getEntity().getPlayer());
            e.getEntity().sendMessage(DefaultConfig.prefix + "§cYou've been eliminated from the clan battle.");
            return;
        }

        double tax = Core.econ.getBalance(e.getEntity()) * .15;
        Core.econ.withdrawPlayer(e.getEntity(), (int) tax);
        e.getEntity().sendMessage(DefaultConfig.prefix + "§cYou lost §a$" + ((int) tax) + " §cfor dying.");
        if (e.getEntity().getKiller() != null) {
            Core.econ.depositPlayer(e.getEntity().getKiller(), (int) tax);
            e.getEntity().getKiller().sendMessage(DefaultConfig.prefix + "§fYou gained §a$" + ((int) tax) + " §ffor killing §6" + e.getEntity().getName() + "§f.");
        }
    }

    @EventHandler
    public void onHelpCommand(PlayerCommandPreprocessEvent e) {
        if (e.getMessage().equalsIgnoreCase("/help") || e.getMessage().equalsIgnoreCase("/?")) {
            e.setMessage("/mcchelp");
        }
        if (e.getMessage().equalsIgnoreCase("/warp")) {
            e.setMessage("/mcc warp");
        }
        if (e.getMessage().contains("/pv") || e.getMessage().contains("/clan storage") || e.getMessage().contains("/mcc warp")) {
            if (!WGRegionManager.inRegion(e.getPlayer(), "nopvp")) {
                e.getPlayer().sendMessage("§cYou must be in the spawn zone to use " + e.getMessage() + ".");
            }
        }
    }

    @EventHandler
    public void onJoinMOTD(PlayerJoinEvent e) {
        StringUtil.sendCenteredMessage(e.getPlayer(), "§f§m───────────────────────────────");
        StringUtil.sendCenteredMessage(e.getPlayer(), "§fWelcome to §8§lMC§c§lConquest");
        if (War.daysTilWar > 0) {
            StringUtil.sendCenteredMessage(e.getPlayer(), "§fDays until the Clan War: §6" + War.daysTilWar);
        } else {
            StringUtil.sendCenteredMessage(e.getPlayer(), "§fThe Clan War is §aactive");
        }
        e.getPlayer().sendMessage("");
        TextComponent discord = new TextComponent(StringUtil.getCenteredMessage("§9§lDiscord: §ewww.mcconquest.com/discord"));
        discord.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://www.mcconquest.com/discord"));
        TextComponent wiki = new TextComponent(StringUtil.getCenteredMessage("§6§lWiki: §ewiki.mcconquest.com"));
        wiki.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://www.mcconquest.com/wiki"));
        TextComponent buy = new TextComponent(StringUtil.getCenteredMessage("§a§lDonate: §ebuy.mcconquest.com"));
        buy.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "http://buy.mcconquest.com"));
        e.getPlayer().sendMessage(discord);
        e.getPlayer().sendMessage(wiki);
        e.getPlayer().sendMessage(buy);
        StringUtil.sendCenteredMessage(e.getPlayer(), "§f§m───────────────────────────────");

        if (MCCPlayer.playerConfirms.containsKey(e.getPlayer().getUniqueId())) {
            e.getPlayer().sendMessage(DefaultConfig.prefix + "§aYou have items to claim,");
            TextComponent claim = new TextComponent("§a§lClick here to claim them.");
            claim.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/claim"));
            e.getPlayer().sendMessage(claim);
        }

        if (!e.getPlayer().hasPlayedBefore()) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + e.getPlayer().getName() + " permission set mcc.tags.BETA");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "kit starter " + e.getPlayer().getName());
        }
    }

    @EventHandler
    public void onDrinkMilk(PlayerItemConsumeEvent e) {
        if (e.getItem().getType() == Material.MILK_BUCKET) {
            if (e.getItem().getAmount() <= 1) {
                e.setReplacement(new ItemStack(Material.AIR));
            }
        }

        if (e.getItem().getType() == Material.POTION) {
            if (e.getItem().getAmount() <= 1) {
                e.setReplacement(new ItemStack(Material.AIR));
            }
        }
    }

    @EventHandler
    public void onPlaceOffhand(InventoryClickEvent e) {
        if (e.getInventory().getType() == InventoryType.CRAFTING && e.getRawSlot() == 45) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onSwitchOffhand(PlayerSwapHandItemsEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onCraft(CraftItemEvent e) {
        e.setCancelled(true);
    }

}
