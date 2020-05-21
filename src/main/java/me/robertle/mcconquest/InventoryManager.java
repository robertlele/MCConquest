package me.robertle.mcconquest;

import com.hazebyte.crate.api.util.ItemBuilder;
import com.hazebyte.crate.api.util.ItemHelper;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public class InventoryManager implements Listener {

    public static Inventory getPerkGui(Clan clan) {
        Inventory inv = Bukkit.createInventory(null, 9, "§6§l" + clan.clanName + "'s Perks");

        ItemBuilder balance = new ItemBuilder(CustomHeadManager.heads.get("bank"));
        balance.displayName("§6Clan Balance: §f$" + clan.clanBalance);
        inv.setItem(3, balance.asItemStack());

        ItemBuilder perk = new ItemBuilder(Material.BOOK);
        perk.displayName("§aPerks Unlocked: §f" + clan.clanPerk);
        perk.setGlowing(true);
        switch (clan.clanPerk) {
            case 0:
                perk.lore("§f§nNext Perk", "", "§eUnlock size 18 clan storage");
                break;
            case 1:
                perk.lore("§f§nNext Perk", "", "§eEach member receives an armor voucher", "§f§lWARNING §r§fItems will be put in the clan storage", "§fso make sure it's not full");
                break;
            case 2:
                perk.lore("§f§nNext Perk", "", "§eUnlock size 27 clan storage", "§f§lWARNING §r§fItems will be cleared from clan storage", "§fso clear before unlocking");
                break;
            case 3:
                perk.lore("§f§nNext Perk", "", "§eEach member receives a weapon voucher", "§f§lWARNING §r§fItems will be put in the clan storage", "§fso make sure it's not full");
                break;
            case 4:
                perk.lore("§f§nNext Perk", "", "§eUnlock size 45 clan storage", "§f§lWARNING §r§fItems will be cleared from clan storage", "§fso clear before unlocking");
                break;
            case 5:
                perk.lore("§f§nNext Perk", "", "§eEach member receives a special artifact voucher", "§f§lWARNING §r§fItems will be put in the clan storage", "§fso make sure it's not full");
                break;
            case 6:
                perk.lore("§f§nNext Perk", "", "§eEach member receives a special armor voucher", "§f§lWARNING §r§fItems will be put in the clan storage", "§fso make sure it's not full");
                break;
            case 7:
                perk.lore("§f§nNext Perk", "", "§eEach member receives a special weapon voucher", "§f§lWARNING §r§fItems will be put in the clan storage", "§fso make sure it's not full");
                break;
            case 8:
                perk.lore("§f§nNext Perk", "", "§eEach member receives an ultra crate", "§f§lWARNING §r§fItems will be put in the clan storage", "§fso make sure it's not full");
                break;
            case 9:
                perk.lore("§f§nNext Perk", "", "§eEach member receives an ultra crate", "§f§lWARNING §r§fItems will be put in the clan storage", "§fso make sure it's not full");
                break;
        }
        inv.setItem(4, perk.asItemStack());

        ItemBuilder buy = new ItemBuilder(CustomHeadManager.heads.get("goldplay"));
        buy.displayName("§6Unlock New Perk");
        switch (clan.clanPerk) {
            case 0:
                buy.lore("§aCost: $" + 400000, "", "§eLeft click to unlock");
                break;
            case 1:
                buy.lore("§aCost: $" + 200000, "", "§eLeft click to unlock");
                break;
            case 2:
                buy.lore("§aCost: $" + 800000, "", "§eLeft click to unlock");
                break;
            case 3:
                buy.lore("§aCost: $" + 400000, "", "§eLeft click to unlock");
                break;
            case 4:
                buy.lore("§aCost: $" + 1600000, "", "§eLeft click to unlock");
                break;
            case 5:
                buy.lore("§aCost: $" + 800000, "", "§eLeft click to unlock");
                break;
            case 6:
                buy.lore("§aCost: $" + 800000, "", "§eLeft click to unlock");
                break;
            case 7:
                buy.lore("§aCost: $" + 800000, "", "§eLeft click to unlock");
                break;
            case 8:
                buy.lore("§aCost: $" + 1600000, "", "§eLeft click to unlock");
                break;
            case 9:
                buy.lore("§aCost: $" + 1600000, "", "§eLeft click to unlock");
                break;
        }
        inv.setItem(5, buy.asItemStack());

        return inv;
    }

    @EventHandler
    public void perkGui(InventoryClickEvent e) {
        if (e.getView().getTitle().contains("Perks")) {
            e.setCancelled(true);
            Clan clan = Clan.clans.get(Clan.getPlayerClan(e.getWhoClicked().getName()));
            if (e.getClick() == ClickType.LEFT) {
                if (ItemHelper.hasName(e.getCurrentItem())) {
                    String itemName = ItemHelper.getName(e.getCurrentItem());
                    if (itemName.equalsIgnoreCase("§6Unlock New Perk")) {
                        if (clan.clanMembers.get(e.getWhoClicked().getUniqueId()) == ClanRole.COLEADER || clan.clanMembers.get(e.getWhoClicked().getUniqueId()) == ClanRole.LEADER) {
                            if (clan.buyNextPerk()) {
                                e.getWhoClicked().sendMessage(DefaultConfig.prefix + "§aPerk unlocked.");
                                clan.log(e.getWhoClicked().getName() + " unlocked a new perk.");
                                e.getWhoClicked().openInventory(getPerkGui(clan));
                            } else {
                                e.getWhoClicked().sendMessage(DefaultConfig.prefix + "§cInsufficient balance, use §f/clan deposit <amount> §cto deposit more.");
                                e.getWhoClicked().closeInventory();
                            }
                        }
                    }
                }
            }
        }
    }

}
