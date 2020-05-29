package me.robertle.mcconquest;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;
import java.util.UUID;

public class Tags implements Listener, CommandExecutor {

    public static HashMap<UUID, Tag> playerTags = new HashMap<>();

    public static void giveTag(Player player, Tag tag) {
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + player.getName() + " permission set mcc.tags." + tag.toString());
    }

    public static Inventory getTagInventory(Player player) {
        Inventory inv = Bukkit.createInventory(null, 36, "§e§lTags");

        ItemBuilder none = new ItemBuilder(Material.RED_STAINED_GLASS_PANE);
        none.displayName("§cRemove Current Tag");
        inv.setItem(35, none.asItemStack());

        int i = 0;
        for (Tag tag : Tag.values()) {
            if (player.hasPermission("mcc.tags."+tag.toString())) {
                ItemBuilder item = new ItemBuilder(Material.NAME_TAG);
                item.displayName(tag.getTagString());
                item.lore("§eLeft click to select this tag");
                if (playerTags.containsKey(player.getUniqueId())) {
                    if (playerTags.get(player.getUniqueId()) == tag) item.setGlowing(true);
                }
                inv.setItem(i, item.asItemStack());
            }
            i++;
        }

        return inv;
    }

    @EventHandler
    public void tagClick(InventoryClickEvent e) {
        if (e.getView().getTitle().equalsIgnoreCase("§e§lTags")) {
            e.setCancelled(true);
            Player player = (Player) e.getWhoClicked();
            if (e.getCurrentItem().getType() == Material.NAME_TAG) {
                Tag tag = Tag.getTagFromTagString(ItemHelper.getName(e.getCurrentItem()));
                playerTags.put(player.getUniqueId(), tag);
                player.openInventory(getTagInventory(player));
            }
            else if (e.getCurrentItem().getType() == Material.RED_STAINED_GLASS_PANE) {
                if (playerTags.containsKey(player.getUniqueId())) {
                    playerTags.remove(player.getUniqueId());
                    player.openInventory(getTagInventory(player));
                }
            }
        }
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            if (command.getLabel().equalsIgnoreCase("tag")) {
                player.openInventory(getTagInventory(player));
            }
        }
        return false;
    }
}
