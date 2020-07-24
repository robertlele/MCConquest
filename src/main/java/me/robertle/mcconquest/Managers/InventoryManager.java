package me.robertle.mcconquest.Managers;

import me.robertle.mcconquest.*;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class InventoryManager implements Listener {

    //Perk Gui
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
                perk.lore("§f§nNext Perk", "", "§e8 Armor Vouchers", "§c§lWARNING §r§fItems will be put in the clan storage", "§fso make sure it's not full");
                break;
            case 2:
                perk.lore("§f§nNext Perk", "", "§eUnlock size 36 clan storage");
                break;
            case 3:
                perk.lore("§f§nNext Perk", "", "§e8 Weapon Vouchers,", "§eAble to challenge other clans to clan battles", "§c§lWARNING §r§fItems will be put in the clan storage", "§fso make sure it's not full");
                break;
            case 4:
                perk.lore("§f§nNext Perk", "", "§eUnlock size 54 clan storage");
                break;
            case 5:
                perk.lore("§f§nNext Perk", "", "§e8 Super Crates", "§c§lWARNING §r§fItems will be put in the clan storage", "§fso make sure it's not full");
                break;
            case 6:
                perk.lore("§f§nNext Perk", "", "§e8 Special Armor Vouchers", "§c§lWARNING §r§fItems will be put in the clan storage", "§fso make sure it's not full");
                break;
            case 7:
                perk.lore("§f§nNext Perk", "", "§e8 Special Weapon Vouchers", "§c§lWARNING §r§fItems will be put in the clan storage", "§fso make sure it's not full");
                break;
            case 8:
                perk.lore("§f§nNext Perk", "", "§e8 Super Crates", "§c§lWARNING §r§fItems will be put in the clan storage", "§fso make sure it's not full");
                break;
            case 9:
                perk.lore("§f§nNext Perk", "", "§e8 Ultra Crates,", "§eAble to participate in the clan war", "§c§lWARNING §r§fItems will be put in the clan storage", "§fso make sure it's not full");
                break;
        }
        inv.setItem(4, perk.asItemStack());

        ItemBuilder buy = new ItemBuilder(CustomHeadManager.heads.get("goldplay"));
        buy.displayName("§6Unlock New Perk");
        switch (clan.clanPerk) {
            case 0:
                buy.lore("§aCost: $" + 200000, "", "§eLeft click to unlock");
                break;
            case 1:
                buy.lore("§aCost: $" + 200000, "", "§eLeft click to unlock");
                break;
            case 2:
                buy.lore("§aCost: $" + 200000, "", "§eLeft click to unlock");
                break;
            case 3:
                buy.lore("§aCost: $" + 400000, "", "§eLeft click to unlock");
                break;
            case 4:
                buy.lore("§aCost: $" + 400000, "", "§eLeft click to unlock");
                break;
            case 5:
                buy.lore("§aCost: $" + 800000, "", "§eLeft click to unlock");
                break;
            case 6:
                buy.lore("§aCost: $" + 400000, "", "§eLeft click to unlock");
                break;
            case 7:
                buy.lore("§aCost: $" + 400000, "", "§eLeft click to unlock");
                break;
            case 8:
                buy.lore("§aCost: $" + 800000, "", "§eLeft click to unlock");
                break;
            case 9:
                buy.lore("§aCost: $" + 1200000, "", "§eLeft click to unlock");
                break;
        }
        if (clan.clanPerk < 10)
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
                                Player player = (Player) e.getWhoClicked();
                                player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 1f);
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

    //Generator Gui
    public static Inventory getGeneratorGui(Player player) {
        Inventory inv = Bukkit.createInventory(player, 9, "§e§lGenerators");

        inv.setItem(0, new ItemBuilder(Material.WHITE_STAINED_GLASS_PANE).displayName("§8§lMC§c§lConquest").asItemStack());
        inv.setItem(1, new ItemBuilder(Material.WHITE_STAINED_GLASS_PANE).displayName("§8§lMC§c§lConquest").asItemStack());
        inv.setItem(6, new ItemBuilder(Material.WHITE_STAINED_GLASS_PANE).displayName("§8§lMC§c§lConquest").asItemStack());
        inv.setItem(8, new ItemBuilder(Material.WHITE_STAINED_GLASS_PANE).displayName("§8§lMC§c§lConquest").asItemStack());

        if (MCCPlayer.playerGenerators.containsKey(player.getUniqueId())) {
            if (MCCPlayer.playerGenerators.get(player.getUniqueId()).size() >= 1) {
                inv.setItem(2, Generator.getGeneratorItem(player, 0));
            }
            if (MCCPlayer.playerGenerators.get(player.getUniqueId()).size() >= 2) {
                inv.setItem(3, Generator.getGeneratorItem(player, 1));
            }
            if (MCCPlayer.playerGenerators.get(player.getUniqueId()).size() >= 3) {
                inv.setItem(4, Generator.getGeneratorItem(player, 2));
            }
            if (MCCPlayer.playerGenerators.get(player.getUniqueId()).size() >= 4) {
                inv.setItem(5, Generator.getGeneratorItem(player, 3));
            }
        }

        for (int i = 2; i < 6; i++) {
            if (inv.getItem(i) == null) {
                inv.setItem(i, new ItemBuilder(Material.RED_STAINED_GLASS_PANE).displayName("§fEmpty").lore("§eFind a generator to place it here.").asItemStack());
            }
        }

        inv.setItem(7, new ItemBuilder(Material.GREEN_STAINED_GLASS_PANE).displayName("§a§lCollect").asItemStack());

        return inv;
    }

    @EventHandler
    public void generatorGui(InventoryClickEvent e) {
        if (e.getView().getTitle().contains("Generators")) {
            e.setCancelled(true);
            if (e.getClick() == ClickType.LEFT) {
                if (e.getCurrentItem() == null) return;
                if (ItemHelper.hasName(e.getCurrentItem())) {
                    String itemName = ItemHelper.getName(e.getCurrentItem());
                    Player player = (Player) e.getWhoClicked();
                    if (itemName.equalsIgnoreCase("§a§lCollect")) {
                        Generator.collectGenerators(player);
                        player.closeInventory();
                    } else if (itemName.contains("Generator")) {
                        if (ItemHelper.hasLore(e.getCurrentItem())) {
                            if (Generator.upgradeGenerator(player, e.getRawSlot() - 2)) {
                                player.openInventory(getGeneratorGui(player));
                                player.sendMessage(DefaultConfig.prefix + "§aGenerator successfully upgraded.");
                            } else {
                                if (!ItemHelper.getLore(e.getCurrentItem()).get(2).substring(11).equalsIgnoreCase("5")) {
                                    player.sendMessage(DefaultConfig.prefix + "§cInsufficient funds to upgrade.");
                                    player.closeInventory();
                                }
                            }
                        }
                    }
                }
            } else if (e.getClick() == ClickType.SHIFT_RIGHT) {
                if (e.getCurrentItem() == null) return;
                if (ItemHelper.hasName(e.getCurrentItem())) {
                    String itemName = ItemHelper.getName(e.getCurrentItem());
                    Player player = (Player) e.getWhoClicked();
                    if (itemName.contains("Generator")) {
                        if (ItemHelper.hasLore(e.getCurrentItem())) {
                            Generator.deleteGenerator(player, e.getRawSlot() - 2);
                            player.openInventory(getGeneratorGui(player));
                            player.sendMessage(DefaultConfig.prefix + "§aGenerator successfully destroyed.");
                        }
                    }
                }
            }
        }
    }

    //Blacksmith Gui
    public static Inventory getBlacksmithGui() {
        Inventory inv = Bukkit.createInventory(null, 9, "§8§lBlacksmith");

        inv.setItem(0, new ItemBuilder(Material.WHITE_STAINED_GLASS_PANE).displayName("§8§lMC§c§lConquest").asItemStack());
        inv.setItem(3, new ItemBuilder(Material.WHITE_STAINED_GLASS_PANE).displayName("§8§lMC§c§lConquest").asItemStack());
        inv.setItem(5, new ItemBuilder(Material.WHITE_STAINED_GLASS_PANE).displayName("§8§lMC§c§lConquest").asItemStack());
        inv.setItem(8, new ItemBuilder(Material.WHITE_STAINED_GLASS_PANE).displayName("§8§lMC§c§lConquest").asItemStack());

        inv.setItem(1, new ItemBuilder(CustomHeadManager.heads.get("right")).displayName("§eItem ->").lore("§fPlace the item to forge here").asItemStack());

        inv.setItem(4, new ItemBuilder(CustomHeadManager.heads.get("anvil")).displayName("§a§lForge").lore("§eLeft click to forge your item").asItemStack());

        inv.setItem(7, new ItemBuilder(CustomHeadManager.heads.get("left")).displayName("§e<- Blacksmith Item").lore("§fPlace the blacksmith item here").asItemStack());

        return inv;

    }

    @EventHandler
    public void blacksmithGui(InventoryClickEvent e) {
        if (e.getView().getTitle().contains("Blacksmith")) {
            if (e.getClick() == ClickType.LEFT && ItemHelper.hasName(e.getCurrentItem())) {
                if (ItemHelper.getName(e.getCurrentItem()).equalsIgnoreCase("§a§lForge")) {
                    e.setCancelled(true);
                    Player player = (Player) e.getWhoClicked();
                    if (e.getClickedInventory().getItem(6) == null || e.getClickedInventory().getItem(2) == null) {
                        return;
                    }
                    if (InventoryUtil.verifyBlacksmithItem(e.getClickedInventory().getItem(6))) {
                        if (BlacksmithManager.forgeBlacksmithItem(e.getClickedInventory().getItem(6), e.getClickedInventory().getItem(2), player)) {
                            player.closeInventory();
                        } else {
                            player.closeInventory(InventoryCloseEvent.Reason.PLAYER);
                        }
                    } else {
                        player.sendMessage("§8§lBlacksmith §f> This isn't one of my forging items!");
                        player.closeInventory(InventoryCloseEvent.Reason.PLAYER);
                        return;
                    }
                }
            }

            if (ItemHelper.hasName(e.getCurrentItem())) {
                if (ItemHelper.getName(e.getCurrentItem()).equalsIgnoreCase("§8§lMC§c§lConquest") || ItemHelper.getName(e.getCurrentItem()).equalsIgnoreCase("§eItem ->") || ItemHelper.getName(e.getCurrentItem()).equalsIgnoreCase("§e<- Blacksmith Item") || ItemHelper.getName(e.getCurrentItem()).equalsIgnoreCase("§a§lForge"))
                    e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onCloseBlacksmithGui(InventoryCloseEvent e) {
        if (e.getView().getTitle().contains("Blacksmith")) {
            if (e.getReason() == InventoryCloseEvent.Reason.DISCONNECT || e.getReason() == InventoryCloseEvent.Reason.PLAYER || e.getReason() == InventoryCloseEvent.Reason.TELEPORT || e.getReason() == InventoryCloseEvent.Reason.UNKNOWN) {
                if (e.getInventory().getItem(2) != null)
                    e.getPlayer().getInventory().addItem(e.getInventory().getItem(2));
                if (e.getInventory().getItem(6) != null)
                    e.getPlayer().getInventory().addItem(e.getInventory().getItem(6));
            }
        }
    }

    //Merger Gui
    public static Inventory getMergerGui() {
        Inventory inv = Bukkit.createInventory(null, 9, "§c§lMerger");

        inv.setItem(0, new ItemBuilder(Material.WHITE_STAINED_GLASS_PANE).displayName("§8§lMC§c§lConquest").asItemStack());
        inv.setItem(3, new ItemBuilder(Material.WHITE_STAINED_GLASS_PANE).displayName("§8§lMC§c§lConquest").asItemStack());
        inv.setItem(5, new ItemBuilder(Material.WHITE_STAINED_GLASS_PANE).displayName("§8§lMC§c§lConquest").asItemStack());
        inv.setItem(8, new ItemBuilder(Material.WHITE_STAINED_GLASS_PANE).displayName("§8§lMC§c§lConquest").asItemStack());

        inv.setItem(1, new ItemBuilder(CustomHeadManager.heads.get("right")).displayName("§eItem to keep ->").lore("§fPlace the item to keep here").asItemStack());

        inv.setItem(4, new ItemBuilder(CustomHeadManager.heads.get("powerorb")).displayName("§a§lMerge").lore("", "", "§eLeft click to merge your weapons or armor").asItemStack());

        inv.setItem(7, new ItemBuilder(CustomHeadManager.heads.get("left")).displayName("§e<- Item to merge").lore("§fPlace the item with enchants to merge here").asItemStack());

        return inv;

    }

    @EventHandler
    public void mergerGui(InventoryClickEvent e) {
        if (e.getView().getTitle().contains("Merger")) {
            if (e.getClick() == ClickType.LEFT && ItemHelper.hasName(e.getCurrentItem())) {
                if (ItemHelper.getName(e.getCurrentItem()).equalsIgnoreCase("§a§lMerge")) {
                    e.setCancelled(true);
                    Player player = (Player) e.getWhoClicked();
                    if (e.getClickedInventory().getItem(6) == null || e.getClickedInventory().getItem(2) == null) {
                        return;
                    }
                    if (MergerManager.mergeWeapon(e.getClickedInventory().getItem(2), e.getClickedInventory().getItem(6), player)) {
                        player.closeInventory();
                    } else {
                        player.closeInventory(InventoryCloseEvent.Reason.PLAYER);
                        return;
                    }
                }
            }

            if (ItemHelper.hasName(e.getCurrentItem())) {
                if (ItemHelper.getName(e.getCurrentItem()).equalsIgnoreCase("§8§lMC§c§lConquest") || ItemHelper.getName(e.getCurrentItem()).equalsIgnoreCase("§eWeapon to keep ->") || ItemHelper.getName(e.getCurrentItem()).equalsIgnoreCase("§e<- Weapon to merge") || ItemHelper.getName(e.getCurrentItem()).equalsIgnoreCase("§a§lMerge"))
                    e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onCloseMergerGui(InventoryCloseEvent e) {
        if (e.getView().getTitle().contains("Merger")) {
            if (e.getReason() == InventoryCloseEvent.Reason.DISCONNECT || e.getReason() == InventoryCloseEvent.Reason.PLAYER || e.getReason() == InventoryCloseEvent.Reason.TELEPORT || e.getReason() == InventoryCloseEvent.Reason.UNKNOWN) {
                if (e.getInventory().getItem(2) != null)
                    e.getPlayer().getInventory().addItem(e.getInventory().getItem(2));
                if (e.getInventory().getItem(6) != null)
                    e.getPlayer().getInventory().addItem(e.getInventory().getItem(6));
            }
        }
    }

    //Combat Merchant Gui
    public static Inventory getCombatMerchantGui() {
        Inventory inv = Bukkit.createInventory(null, 9, "§e§lCombat Merchant");
        inv.setItem(0, new ItemBuilder(Material.WHITE_STAINED_GLASS_PANE).displayName("§8§lMC§c§lConquest").asItemStack());
        inv.setItem(8, new ItemBuilder(Material.WHITE_STAINED_GLASS_PANE).displayName("§8§lMC§c§lConquest").asItemStack());
        ItemBuilder weaponVoucher = new ItemBuilder(CustomItemManager.getWeaponVoucher());
        weaponVoucher.lore("§aCost: 320 Iron Ingot or 256 Gold Ingot", "", "§eLeft click to buy");
        ItemBuilder armorVoucher = new ItemBuilder(CustomItemManager.getArmorVoucher());
        armorVoucher.lore("§aCost: 256 Iron Ingot or 192 Gold Ingot", "", "§eLeft click to buy");
        ItemBuilder blacksmithsCoal = new ItemBuilder(CustomItemManager.getBlacksmithsCoal());
        blacksmithsCoal.lore("§aCost: 128 Rare Essence", "", "§eLeft click to buy");
        ItemBuilder blacksmithsMagmaRod = new ItemBuilder(CustomItemManager.getBlacksmithsMagmaRod());
        blacksmithsMagmaRod.lore("§aCost: 192 Rare Essence", "", "§eLeft click to buy");
        ItemBuilder arrow = new ItemBuilder(Material.ARROW);
        arrow.lore("§aCost: $160", "", "§eLeft click to buy 16");
        inv.setItem(1, armorVoucher.asItemStack());
        inv.setItem(2, weaponVoucher.asItemStack());
        inv.setItem(3, blacksmithsCoal.asItemStack());
        inv.setItem(4, blacksmithsMagmaRod.asItemStack());
        inv.setItem(5, arrow.asItemStack());
        return inv;
    }

    @EventHandler
    public void combatMerchantGui(InventoryClickEvent e) {
        if (e.getView().getTitle().contains("Combat Merchant")) {
            e.setCancelled(true);
            if (e.getClick() == ClickType.LEFT) {
                ItemBuilder weaponVoucher = new ItemBuilder(CustomItemManager.getWeaponVoucher());
                weaponVoucher.lore("§aCost: 320 Iron Ingot or 256 Gold Ingot", "", "§eLeft click to buy");
                ItemBuilder armorVoucher = new ItemBuilder(CustomItemManager.getArmorVoucher());
                armorVoucher.lore("§aCost: 256 Iron Ingot or 192 Gold Ingot", "", "§eLeft click to buy");
                ItemBuilder blacksmithsCoal = new ItemBuilder(CustomItemManager.getBlacksmithsCoal());
                blacksmithsCoal.lore("§aCost: 128 Rare Essence", "", "§eLeft click to buy");
                ItemBuilder blacksmithsMagmaRod = new ItemBuilder(CustomItemManager.getBlacksmithsMagmaRod());
                blacksmithsMagmaRod.lore("§aCost: 192 Rare Essence", "", "§eLeft click to buy");
                ItemBuilder arrow = new ItemBuilder(Material.ARROW);
                arrow.lore("§aCost: $160", "", "§eLeft click to buy 16");
                Player player = (Player) e.getWhoClicked();
                if (e.getCurrentItem() == null) return;
                if (e.getCurrentItem().isSimilar(weaponVoucher.asItemStack()) && player.getInventory().containsAtLeast(CustomItemManager.getIngot(false, 1), 320)) {
                    InventoryUtil.removeItems(player, CustomItemManager.getIngot(false, 1), 320);
                    player.getInventory().addItem(CustomItemManager.getWeaponVoucher());
                } else if (e.getCurrentItem().isSimilar(weaponVoucher.asItemStack()) && player.getInventory().containsAtLeast(CustomItemManager.getIngot(true, 1), 256)) {
                    InventoryUtil.removeItems(player, CustomItemManager.getIngot(true, 1), 256);
                    player.getInventory().addItem(CustomItemManager.getWeaponVoucher());
                } else if (e.getCurrentItem().isSimilar(armorVoucher.asItemStack()) && player.getInventory().containsAtLeast(CustomItemManager.getIngot(false, 1), 256)) {
                    InventoryUtil.removeItems(player, CustomItemManager.getIngot(false, 1), 256);
                    player.getInventory().addItem(CustomItemManager.getArmorVoucher());
                } else if (e.getCurrentItem().isSimilar(armorVoucher.asItemStack()) && player.getInventory().containsAtLeast(CustomItemManager.getIngot(true, 1), 192)) {
                    InventoryUtil.removeItems(player, CustomItemManager.getIngot(true, 1), 192);
                    player.getInventory().addItem(CustomItemManager.getArmorVoucher());
                } else if (e.getCurrentItem().isSimilar(blacksmithsCoal.asItemStack())) {
                    if (player.getInventory().containsAtLeast(CustomItemManager.getEssence(true, 1), 128)) {
                        InventoryUtil.removeItems(player, CustomItemManager.getEssence(true, 1), 128);
                        player.getInventory().addItem(CustomItemManager.getBlacksmithsCoal());
                    } else {
                        player.sendMessage("§4§lCombat Merchant §f> You don't have enough for this.");
                        player.closeInventory();
                    }
                } else if (e.getCurrentItem().isSimilar(blacksmithsMagmaRod.asItemStack())) {
                    if (player.getInventory().containsAtLeast(CustomItemManager.getEssence(true, 1), 192)) {
                        InventoryUtil.removeItems(player, CustomItemManager.getEssence(true, 1), 192);
                        player.getInventory().addItem(CustomItemManager.getBlacksmithsMagmaRod());
                    } else {
                        player.sendMessage("§4§lCombat Merchant §f> You don't have enough for this.");
                        player.closeInventory();
                    }
                } else if (e.getCurrentItem().isSimilar(arrow.asItemStack())) {
                    if (Core.econ.has(player, 160)) {
                        Core.econ.withdrawPlayer(player, 160);
                        arrow.lore(new ArrayList<>());
                        arrow.amount(16);
                        player.getInventory().addItem(arrow.asItemStack());
                    } else {
                        player.sendMessage("§4§lCombat Merchant §f> You don't have enough for this.");
                        player.closeInventory();
                    }
                } else {
                    player.sendMessage("§4§lCombat Merchant §f> You don't have enough for this.");
                    player.closeInventory();
                }
            }
        }
    }

    @EventHandler
    public void closeCombatMerchantGui(InventoryCloseEvent e) {
        if (e.getView().getTitle().contains("Combat Merchant")) {
            e.getPlayer().sendMessage("§4§lCombat Merchant §f> Best of luck!");
        }
    }

    //Potion Merchant Gui
    public static Inventory getPotionMerchant() {
        Inventory inv = Bukkit.createInventory(null, 9, "§d§lPotion Merchant");
        inv.setItem(0, new ItemBuilder(Material.WHITE_STAINED_GLASS_PANE).displayName("§8§lMC§c§lConquest").asItemStack());
        inv.setItem(8, new ItemBuilder(Material.WHITE_STAINED_GLASS_PANE).displayName("§8§lMC§c§lConquest").asItemStack());

        ItemBuilder healing = new ItemBuilder(Material.SPLASH_POTION);
        PotionMeta potionMeta = (PotionMeta) healing.asItemStack().getItemMeta();
        potionMeta.setBasePotionData(new PotionData(PotionType.INSTANT_HEAL, false, true));
        healing.potion(potionMeta);
        healing.displayName("§dSplash Potion of Healing");
        healing.lore("§aCost: $100", "", "§eLeft click to buy 1", "§eMiddle click to buy 9");

        ItemBuilder fire = new ItemBuilder(Material.POTION);
        PotionMeta firepotionMeta = (PotionMeta) fire.asItemStack().getItemMeta();
        firepotionMeta.setBasePotionData(new PotionData(PotionType.FIRE_RESISTANCE, false, false));
        fire.potion(firepotionMeta);
        fire.displayName("§6Fire Resistance Potion");
        fire.lore("§aCost: $500", "", "§eLeft click to buy 1", "§eMiddle click to buy 9");

        ItemBuilder invis = new ItemBuilder(Material.POTION);
        PotionMeta invispotionMeta = (PotionMeta) invis.asItemStack().getItemMeta();
        invispotionMeta.setBasePotionData(new PotionData(PotionType.INVISIBILITY, true, false));
        invis.potion(invispotionMeta);
        invis.displayName("§7Invisibility Potion");
        invis.lore("§aCost: $500", "", "§eLeft click to buy 1", "§eMiddle click to buy 9");

        ItemBuilder milk = new ItemBuilder(Material.MILK_BUCKET);
        milk.displayName("§fMilk Bucket");
        milk.lore("§aCost: $250", "", "§eLeft click to buy 1", "§eMiddle click to buy 16");

        inv.setItem(1, healing.asItemStack());
        inv.setItem(2, fire.asItemStack());
        inv.setItem(3, invis.asItemStack());
        inv.setItem(4, milk.asItemStack());

        return inv;
    }

    @EventHandler
    public void potionMerchantGui(InventoryClickEvent e) {
        if (e.getView().getTitle().contains("Potion Merchant")) {
            e.setCancelled(true);

            ItemBuilder healing = new ItemBuilder(Material.SPLASH_POTION);
            PotionMeta potionMeta = (PotionMeta) healing.asItemStack().getItemMeta();
            potionMeta.setBasePotionData(new PotionData(PotionType.INSTANT_HEAL, false, true));
            healing.potion(potionMeta);
            healing.displayName("§dSplash Potion of Healing");
            healing.lore("§aCost: $100", "", "§eLeft click to buy 1", "§eMiddle click to buy 9");

            ItemBuilder fire = new ItemBuilder(Material.POTION);
            PotionMeta firepotionMeta = (PotionMeta) fire.asItemStack().getItemMeta();
            firepotionMeta.setBasePotionData(new PotionData(PotionType.FIRE_RESISTANCE, false, false));
            fire.potion(firepotionMeta);
            fire.displayName("§6Fire Resistance Potion");
            fire.lore("§aCost: $500", "", "§eLeft click to buy 1", "§eMiddle click to buy 9");

            ItemBuilder invis = new ItemBuilder(Material.POTION);
            PotionMeta invispotionMeta = (PotionMeta) invis.asItemStack().getItemMeta();
            invispotionMeta.setBasePotionData(new PotionData(PotionType.INVISIBILITY, true, false));
            invis.potion(invispotionMeta);
            invis.displayName("§7Invisibility Potion");
            invis.lore("§aCost: $500", "", "§eLeft click to buy 1", "§eMiddle click to buy 9");

            ItemBuilder milk = new ItemBuilder(Material.MILK_BUCKET);
            milk.displayName("§fMilk Bucket");
            milk.lore("§aCost: $250", "", "§eLeft click to buy 1", "§eMiddle click to buy 16");

            Player player = (Player) e.getWhoClicked();

            if (e.getCurrentItem() == null) return;

            if (e.getClick() == ClickType.LEFT) {
                if (e.getCurrentItem().isSimilar(healing.asItemStack())) {
                    if (Core.econ.has(player, 100)) {
                        Core.econ.withdrawPlayer(player, 100);
                        healing.lore(new ArrayList<>());
                        player.getInventory().addItem(healing.asItemStack());
                    }
                } else if (e.getCurrentItem().isSimilar(fire.asItemStack())) {
                    if (Core.econ.has(player, 500)) {
                        Core.econ.withdrawPlayer(player, 500);
                        fire.lore(new ArrayList<>());
                        player.getInventory().addItem(fire.asItemStack());
                    }
                } else if (e.getCurrentItem().isSimilar(invis.asItemStack())) {
                    if (Core.econ.has(player, 500)) {
                        Core.econ.withdrawPlayer(player, 500);
                        invis.lore(new ArrayList<>());
                        player.getInventory().addItem(invis.asItemStack());
                    }
                } else if (e.getCurrentItem().isSimilar(milk.asItemStack())) {
                    if (Core.econ.has(player, 250)) {
                        Core.econ.withdrawPlayer(player, 250);
                        milk.lore(new ArrayList<>());
                        player.getInventory().addItem(milk.asItemStack());
                    }
                }
            } else if (e.getClick() == ClickType.MIDDLE) {
                if (e.getCurrentItem().isSimilar(healing.asItemStack())) {
                    if (Core.econ.has(player, 900)) {
                        Core.econ.withdrawPlayer(player, 900);
                        healing.lore(new ArrayList<>());
                        for (int i = 0; i < 9; i++)
                            player.getInventory().addItem(healing.asItemStack());
                    }
                } else if (e.getCurrentItem().isSimilar(fire.asItemStack())) {
                    if (Core.econ.has(player, 4500)) {
                        Core.econ.withdrawPlayer(player, 4500);
                        fire.lore(new ArrayList<>());
                        for (int i = 0; i < 9; i++)
                            player.getInventory().addItem(fire.asItemStack());
                    }
                } else if (e.getCurrentItem().isSimilar(invis.asItemStack())) {
                    if (Core.econ.has(player, 4500)) {
                        Core.econ.withdrawPlayer(player, 4500);
                        invis.lore(new ArrayList<>());
                        for (int i = 0; i < 9; i++)
                            player.getInventory().addItem(invis.asItemStack());
                    }
                } else if (e.getCurrentItem().isSimilar(milk.asItemStack())) {
                    if (Core.econ.has(player, 4000)) {
                        Core.econ.withdrawPlayer(player, 4000);
                        milk.lore(new ArrayList<>());
                        milk.amount(16);
                        player.getInventory().addItem(milk.asItemStack());
                    }
                }
            }
        }
    }

    @EventHandler
    public void closePotionMerchantGui(InventoryCloseEvent e) {
        if (e.getView().getTitle().contains("Potion Merchant")) {
            e.getPlayer().sendMessage("§d§lPotion Merchant §f> See you soon!");
        }
    }

    //Tools Merchant Gui
    public static Inventory getToolsMerchant() {
        Inventory inv = Bukkit.createInventory(null, 9, "§8§lTools Merchant");
        inv.setItem(0, new ItemBuilder(Material.WHITE_STAINED_GLASS_PANE).displayName("§8§lMC§c§lConquest").asItemStack());
        inv.setItem(8, new ItemBuilder(Material.WHITE_STAINED_GLASS_PANE).displayName("§8§lMC§c§lConquest").asItemStack());

        ItemBuilder pigmanpickaxe = new ItemBuilder(CustomItemManager.getArtifact(MCCArtifact.PIGMAN_PICKAXE));
        pigmanpickaxe.displayName("§cPigman Pickaxe");
        pigmanpickaxe.lore("§aCost: 256 Essence & $100k", "", "§eLeft click to buy 1");

        ItemBuilder zombiegrinder = new ItemBuilder(CustomItemManager.getArtifact(MCCArtifact.ZOMBIE_GRINDER));
        zombiegrinder.displayName("§2Zombie Grinder");
        zombiegrinder.lore("§aCost: $100k", "", "§eLeft click to buy 1");

        ItemBuilder squidrod = new ItemBuilder(CustomItemManager.getArtifact(MCCArtifact.SQUID_ROD));
        squidrod.displayName("§9Squid Rod");
        squidrod.lore("§aCost: 256 Essence & $100k", "", "§eLeft click to buy 1");

        ItemBuilder witherpickaxe = new ItemBuilder(CustomItemManager.getArtifact(MCCArtifact.WITHER_PICKAXE));
        witherpickaxe.displayName("§8Wither Pickaxe");
        witherpickaxe.removeEnchants();
        witherpickaxe.unsafeEnchant(Enchantment.DIG_SPEED, 1);
        witherpickaxe.lore("§aCost: 512 Essence & $300k", "§cUnbreaking is random!", "§eLeft click to buy 1");

        ItemBuilder giantgrinder = new ItemBuilder(CustomItemManager.getArtifact(MCCArtifact.GIANT_GRINDER));
        giantgrinder.displayName("§2Giant Grinder");
        giantgrinder.lore("§aCost: $250k", "", "§eLeft click to buy 1");

        ItemBuilder guardianrod = new ItemBuilder(CustomItemManager.getArtifact(MCCArtifact.GUARDIAN_ROD));
        guardianrod.displayName("§9Guardian Rod");
        guardianrod.removeEnchants();
        guardianrod.lore("§aCost: 512 Essence & $300k", "§cUnbreaking is random!", "§eLeft click to buy 1");

        inv.setItem(1, pigmanpickaxe.asItemStack());
        inv.setItem(2, zombiegrinder.asItemStack());
        inv.setItem(3, squidrod.asItemStack());
        inv.setItem(4, witherpickaxe.asItemStack());
        inv.setItem(5, giantgrinder.asItemStack());
        inv.setItem(6, guardianrod.asItemStack());

        return inv;
    }

    @EventHandler
    public void toolsMerchantGui(InventoryClickEvent e) {
        if (e.getView().getTitle().contains("Tools Merchant")) {
            e.setCancelled(true);
            ItemBuilder pigmanpickaxe = new ItemBuilder(CustomItemManager.getArtifact(MCCArtifact.PIGMAN_PICKAXE));
            pigmanpickaxe.displayName("§cPigman Pickaxe");
            pigmanpickaxe.lore("§aCost: 256 Essence & $100k", "", "§eLeft click to buy 1");

            ItemBuilder zombiegrinder = new ItemBuilder(CustomItemManager.getArtifact(MCCArtifact.ZOMBIE_GRINDER));
            zombiegrinder.displayName("§2Zombie Grinder");
            zombiegrinder.lore("§aCost: $100k", "", "§eLeft click to buy 1");

            ItemBuilder squidrod = new ItemBuilder(CustomItemManager.getArtifact(MCCArtifact.SQUID_ROD));
            squidrod.displayName("§9Squid Rod");
            squidrod.lore("§aCost: 256 Essence & $100k", "", "§eLeft click to buy 1");

            ItemBuilder witherpickaxe = new ItemBuilder(CustomItemManager.getArtifact(MCCArtifact.WITHER_PICKAXE));
            witherpickaxe.displayName("§8Wither Pickaxe");
            witherpickaxe.removeEnchants();
            witherpickaxe.unsafeEnchant(Enchantment.DIG_SPEED, 1);
            witherpickaxe.lore("§aCost: 512 Essence & $300k", "§cUnbreaking is random!", "§eLeft click to buy 1");

            ItemBuilder giantgrinder = new ItemBuilder(CustomItemManager.getArtifact(MCCArtifact.GIANT_GRINDER));
            giantgrinder.displayName("§2Giant Grinder");
            giantgrinder.lore("§aCost: $250k", "", "§eLeft click to buy 1");

            ItemBuilder guardianrod = new ItemBuilder(CustomItemManager.getArtifact(MCCArtifact.GUARDIAN_ROD));
            guardianrod.displayName("§9Guardian Rod");
            guardianrod.removeEnchants();
            guardianrod.lore("§aCost: 512 Essence & $300k", "§cUnbreaking is random!", "§eLeft click to buy 1");

            Player player = (Player) e.getWhoClicked();

            if (e.getCurrentItem() == null) return;

            if (e.getClick() == ClickType.LEFT) {
                if (e.getCurrentItem().isSimilar(pigmanpickaxe.asItemStack())) {
                    if (Core.econ.has(player, 100000) && player.getInventory().containsAtLeast(CustomItemManager.getEssence(false, 1), 256)) {
                        Core.econ.withdrawPlayer(player, 100000);
                        InventoryUtil.removeItems(player, CustomItemManager.getEssence(false, 1), 256);
                        player.getInventory().addItem(CustomItemManager.getArtifact(MCCArtifact.PIGMAN_PICKAXE));
                    }
                } else if (e.getCurrentItem().isSimilar(zombiegrinder.asItemStack())) {
                    if (Core.econ.has(player, 100000)) {
                        Core.econ.withdrawPlayer(player, 100000);
                        player.getInventory().addItem(CustomItemManager.getArtifact(MCCArtifact.ZOMBIE_GRINDER));
                    }
                } else if (e.getCurrentItem().isSimilar(squidrod.asItemStack())) {
                    if (Core.econ.has(player, 100000) && player.getInventory().containsAtLeast(CustomItemManager.getEssence(false, 1), 256)) {
                        Core.econ.withdrawPlayer(player, 100000);
                        InventoryUtil.removeItems(player, CustomItemManager.getEssence(false, 1), 256);
                        player.getInventory().addItem(CustomItemManager.getArtifact(MCCArtifact.SQUID_ROD));
                    }
                } else if (e.getCurrentItem().isSimilar(witherpickaxe.asItemStack())) {
                    if (Core.econ.has(player, 300000) && player.getInventory().containsAtLeast(CustomItemManager.getEssence(false, 1), 512)) {
                        Core.econ.withdrawPlayer(player, 300000);
                        InventoryUtil.removeItems(player, CustomItemManager.getEssence(false, 1), 512);
                        player.getInventory().addItem(CustomItemManager.getArtifact(MCCArtifact.WITHER_PICKAXE));
                    }
                } else if (e.getCurrentItem().isSimilar(giantgrinder.asItemStack())) {
                    if (Core.econ.has(player, 250000)) {
                        Core.econ.withdrawPlayer(player, 250000);
                        player.getInventory().addItem(CustomItemManager.getArtifact(MCCArtifact.GIANT_GRINDER));
                    }
                } else if (e.getCurrentItem().isSimilar(guardianrod.asItemStack())) {
                    if (Core.econ.has(player, 300000) && player.getInventory().containsAtLeast(CustomItemManager.getEssence(false, 1), 512)) {
                        Core.econ.withdrawPlayer(player, 300000);
                        InventoryUtil.removeItems(player, CustomItemManager.getEssence(false, 1), 512);
                        player.getInventory().addItem(CustomItemManager.getArtifact(MCCArtifact.GUARDIAN_ROD));
                    }
                }
            }
        }
    }

    @EventHandler
    public void closeToolsMerchantGui(InventoryCloseEvent e) {
        if (e.getView().getTitle().contains("Tools Merchant")) {
            e.getPlayer().sendMessage("§8§lTools Merchant §f> Thank you for your business.");
        }
    }

    //Special Merchant Gui
    public static Inventory getSpecialMerchant() {
        Inventory inv = Bukkit.createInventory(null, 9, "§6§lSpecial Merchant");
        inv.setItem(0, new ItemBuilder(Material.WHITE_STAINED_GLASS_PANE).displayName("§8§lMC§c§lConquest").asItemStack());
        inv.setItem(8, new ItemBuilder(Material.WHITE_STAINED_GLASS_PANE).displayName("§8§lMC§c§lConquest").asItemStack());

        ItemBuilder enderpearl = new ItemBuilder(Material.ENDER_PEARL);
        enderpearl.displayName("§5Ender Pearl");
        enderpearl.lore("§aCost: $300", "", "§eLeft click to buy 1", "§eMiddle click to buy 16");

        ItemBuilder gapple = new ItemBuilder(Material.GOLDEN_APPLE);
        gapple.displayName("§6Golden Apple");
        gapple.lore("§aCost: $1000", "", "§eLeft click to buy 1", "§eMiddle click to buy 16");

        ItemBuilder pickaxe = new ItemBuilder(CustomItemManager.getArtifact(MCCArtifact.IRON_PICKAXE));
        pickaxe.lore("§aCost: $5000", "", "§eLeft click to buy 1");

        ItemBuilder rod = new ItemBuilder(CustomItemManager.getArtifact(MCCArtifact.FISHING_ROD));
        rod.lore("§aCost: $5000", "", "§eLeft click to buy 1");

        ItemBuilder petCrate = new ItemBuilder(CustomItemManager.getCrate(Crate.PET_CRATE));
        petCrate.lore("§aCost: 384 Rare Essence", "", "§eLeft click to buy 1");

        inv.setItem(1, enderpearl.asItemStack());
        inv.setItem(2, gapple.asItemStack());
        inv.setItem(3, pickaxe.asItemStack());
        inv.setItem(4, rod.asItemStack());
        inv.setItem(5, petCrate.asItemStack());

        return inv;
    }

    @EventHandler
    public void specialMerchantGui(InventoryClickEvent e) {
        if (e.getView().getTitle().contains("Special Merchant")) {
            e.setCancelled(true);
            ItemBuilder enderpearl = new ItemBuilder(Material.ENDER_PEARL);
            enderpearl.displayName("§5Ender Pearl");
            enderpearl.lore("§aCost: $300", "", "§eLeft click to buy 1", "§eMiddle click to buy 16");

            ItemBuilder gapple = new ItemBuilder(Material.GOLDEN_APPLE);
            gapple.displayName("§6Golden Apple");
            gapple.lore("§aCost: $1000", "", "§eLeft click to buy 1", "§eMiddle click to buy 16");

            ItemBuilder pickaxe = new ItemBuilder(CustomItemManager.getArtifact(MCCArtifact.IRON_PICKAXE));
            pickaxe.lore("§aCost: $5000", "", "§eLeft click to buy 1");

            ItemBuilder rod = new ItemBuilder(CustomItemManager.getArtifact(MCCArtifact.FISHING_ROD));
            rod.lore("§aCost: $5000", "", "§eLeft click to buy 1");

            ItemBuilder petCrate = new ItemBuilder(CustomItemManager.getCrate(Crate.PET_CRATE));
            petCrate.lore("§aCost: 384 Rare Essence", "", "§eLeft click to buy 1");

            Player player = (Player) e.getWhoClicked();

            if (e.getCurrentItem() == null) return;

            if (e.getClick() == ClickType.LEFT) {
                if (e.getCurrentItem().isSimilar(enderpearl.asItemStack())) {
                    if (Core.econ.has(player, 400)) {
                        enderpearl.lore(new ArrayList<>());
                        Core.econ.withdrawPlayer(player, 400);
                        player.getInventory().addItem(enderpearl.asItemStack());
                    }
                } else if (e.getCurrentItem().isSimilar(gapple.asItemStack())) {
                    if (Core.econ.has(player, 1000)) {
                        gapple.lore(new ArrayList<>());
                        Core.econ.withdrawPlayer(player, 1000);
                        player.getInventory().addItem(gapple.asItemStack());
                    }
                } else if (e.getCurrentItem().isSimilar(pickaxe.asItemStack())) {
                    if (Core.econ.has(player, 5000)) {
                        pickaxe.lore("§a§l✸B-TIER", "", "");
                        Core.econ.withdrawPlayer(player, 5000);
                        player.getInventory().addItem(pickaxe.asItemStack());
                    }
                } else if (e.getCurrentItem().isSimilar(rod.asItemStack())) {
                    if (Core.econ.has(player, 5000)) {
                        rod.lore("§a§l✸B-TIER", "", "");
                        Core.econ.withdrawPlayer(player, 5000);
                        player.getInventory().addItem(rod.asItemStack());
                    }
                } else if (e.getCurrentItem().isSimilar(petCrate.asItemStack())) {
                    if (player.getInventory().containsAtLeast(CustomItemManager.getEssence(true, 1), 384)) {
                        InventoryUtil.removeItems(player, CustomItemManager.getEssence(true, 1), 384);
                        player.getInventory().addItem(CustomItemManager.getCrate(Crate.PET_CRATE));
                        player.sendMessage("§6§lSpecial Merchant §f> Best of luck!");
                        player.closeInventory();
                    } else {
                        player.sendMessage("§6§lSpecial Merchant §f> You don't have enough for this.");
                        player.closeInventory();
                    }
                }
            } else if (e.getClick() == ClickType.MIDDLE) {
                if (e.getCurrentItem().isSimilar(enderpearl.asItemStack())) {
                    if (Core.econ.has(player, 6400)) {
                        enderpearl.amount(16);
                        enderpearl.lore(new ArrayList<>());
                        Core.econ.withdrawPlayer(player, 6400);
                        player.getInventory().addItem(enderpearl.asItemStack());
                    }
                } else if (e.getCurrentItem().isSimilar(gapple.asItemStack())) {
                    if (Core.econ.has(player, 16000)) {
                        gapple.amount(16);
                        gapple.lore(new ArrayList<>());
                        Core.econ.withdrawPlayer(player, 16000);
                        player.getInventory().addItem(gapple.asItemStack());
                    }
                }
            }
        }
    }

    @EventHandler
    public void closeSpecialMerchantGui(InventoryCloseEvent e) {
        if (e.getView().getTitle().contains("Special Merchant")) {
            e.getPlayer().sendMessage("§6§lSpecial Merchant §f> Thank you for your business.");
        }
    }

    //Merchant Gui
    public static Inventory getMerchant() {
        Inventory inv = Bukkit.createInventory(null, 9, "§e§lMerchant Picker");
        inv.setItem(0, new ItemBuilder(Material.WHITE_STAINED_GLASS_PANE).displayName("§8§lMC§c§lConquest").asItemStack());
        inv.setItem(8, new ItemBuilder(Material.WHITE_STAINED_GLASS_PANE).displayName("§8§lMC§c§lConquest").asItemStack());

        ItemBuilder combat = new ItemBuilder(Material.DIAMOND_SWORD);
        combat.hideAll(true);
        combat.displayName("§6§lCombat Merchant");
        combat.lore("§eLeft click to open combat merchant gui", "", "§cRequirement: Delta rank");
        ItemBuilder food = new ItemBuilder(Material.GOLDEN_CARROT);
        food.displayName("§6§lFood Merchant");
        food.lore("§eLeft click to open food merchant gui", "", "§cRequirement: Gamma rank");
        ItemBuilder special = new ItemBuilder(Material.ENDER_PEARL);
        special.displayName("§6§lSpecial Merchant");
        special.lore("§eLeft click to open special merchant gui", "", "§cRequirement: Alpha rank");
        ItemBuilder potion = new ItemBuilder(Material.POTION);
        potion.hideAll(true);
        potion.displayName("§6§lPotion Merchant");
        potion.lore("§eLeft click to open potion merchant gui", "", "§cRequirement: Omega rank");

        inv.setItem(1, combat.asItemStack());
        inv.setItem(3, food.asItemStack());
        inv.setItem(5, special.asItemStack());
        inv.setItem(7, potion.asItemStack());

        return inv;
    }

    @EventHandler
    public void merchantGui(InventoryClickEvent e) {
        if (e.getView().getTitle().contains("Merchant Picker")) {
            e.setCancelled(true);

            ItemBuilder combat = new ItemBuilder(Material.DIAMOND_SWORD);
            combat.hideAll(true);
            combat.displayName("§6§lCombat Merchant");
            combat.lore("§eLeft click to open combat merchant gui", "", "§cRequirement: Delta rank");
            ItemBuilder food = new ItemBuilder(Material.GOLDEN_CARROT);
            food.displayName("§6§lFood Merchant");
            food.lore("§eLeft click to open food merchant gui", "", "§cRequirement: Gamma rank");
            ItemBuilder special = new ItemBuilder(Material.ENDER_PEARL);
            special.displayName("§6§lSpecial Merchant");
            special.lore("§eLeft click to open special merchant gui", "", "§cRequirement: Alpha rank");
            ItemBuilder potion = new ItemBuilder(Material.POTION);
            potion.hideAll(true);
            potion.displayName("§6§lPotion Merchant");
            potion.lore("§eLeft click to open potion merchant gui", "", "§cRequirement: Omega rank");

            Player player = (Player) e.getWhoClicked();

            if (e.getClick() == ClickType.LEFT) {
                if (e.getCurrentItem().isSimilar(combat.asItemStack())) {
                    if (player.hasPermission("mcc.combat")) {
                        player.openInventory(getCombatMerchantGui());
                    }
                } else if (e.getCurrentItem().isSimilar(food.asItemStack())) {
                    if (player.hasPermission("mcc.food")) {
                        player.openInventory(getFoodMerchant());
                    }
                } else if (e.getCurrentItem().isSimilar(special.asItemStack())) {
                    if (player.hasPermission("mcc.special")) {
                        player.openInventory(getSpecialMerchant());
                    }
                } else if (e.getCurrentItem().isSimilar(potion.asItemStack())) {
                    if (player.hasPermission("mcc.potion")) {
                        player.openInventory(getPotionMerchant());
                    }
                }
            }
        }
    }

    //Food Merchant Gui
    public static Inventory getFoodMerchant() {
        Inventory inv = Bukkit.createInventory(null, 9, "§e§lFood Merchant");
        inv.setItem(0, new ItemBuilder(Material.WHITE_STAINED_GLASS_PANE).displayName("§8§lMC§c§lConquest").asItemStack());
        inv.setItem(8, new ItemBuilder(Material.WHITE_STAINED_GLASS_PANE).displayName("§8§lMC§c§lConquest").asItemStack());

        ItemBuilder potato = new ItemBuilder(Material.BAKED_POTATO);
        potato.displayName("§eBaked Potato");
        potato.lore("§aCost: $25", "", "§eLeft click to buy 1", "§eMiddle click to buy 16");

        ItemBuilder chicken = new ItemBuilder(Material.COOKED_CHICKEN);
        chicken.displayName("§eCooked Chicken");
        chicken.lore("§aCost: $50", "", "§eLeft click to buy 1", "§eMiddle click to buy 16");

        ItemBuilder steak = new ItemBuilder(Material.COOKED_BEEF);
        steak.displayName("§eSteak");
        steak.lore("§aCost: $75", "", "§eLeft click to buy 1", "§eMiddle click to buy 16");

        ItemBuilder carrot = new ItemBuilder(Material.GOLDEN_CARROT);
        carrot.displayName("§6Golden Carrot");
        carrot.lore("§aCost: $150", "", "§eLeft click to buy 1", "§eMiddle click to buy 16");

        inv.setItem(1, potato.asItemStack());
        inv.setItem(2, chicken.asItemStack());
        inv.setItem(3, steak.asItemStack());
        inv.setItem(4, carrot.asItemStack());

        return inv;
    }

    @EventHandler
    public void foodMerchantGui(InventoryClickEvent e) {
        if (e.getView().getTitle().contains("Food Merchant")) {
            e.setCancelled(true);

            ItemBuilder potato = new ItemBuilder(Material.BAKED_POTATO);
            potato.displayName("§eBaked Potato");
            potato.lore("§aCost: $25", "", "§eLeft click to buy 1", "§eMiddle click to buy 16");

            ItemBuilder chicken = new ItemBuilder(Material.COOKED_CHICKEN);
            chicken.displayName("§eCooked Chicken");
            chicken.lore("§aCost: $50", "", "§eLeft click to buy 1", "§eMiddle click to buy 16");

            ItemBuilder steak = new ItemBuilder(Material.COOKED_BEEF);
            steak.displayName("§eSteak");
            steak.lore("§aCost: $75", "", "§eLeft click to buy 1", "§eMiddle click to buy 16");

            ItemBuilder carrot = new ItemBuilder(Material.GOLDEN_CARROT);
            carrot.displayName("§6Golden Carrot");
            carrot.lore("§aCost: $150", "", "§eLeft click to buy 1", "§eMiddle click to buy 16");

            Player player = (Player) e.getWhoClicked();

            if (e.getCurrentItem() == null) return;

            if (e.getClick() == ClickType.LEFT) {
                if (e.getCurrentItem().isSimilar(potato.asItemStack())) {
                    if (Core.econ.has(player, 25)) {
                        potato.lore(new ArrayList<>());
                        Core.econ.withdrawPlayer(player, 25);
                        player.getInventory().addItem(potato.asItemStack());
                    }
                } else if (e.getCurrentItem().isSimilar(chicken.asItemStack())) {
                    if (Core.econ.has(player, 50)) {
                        chicken.lore(new ArrayList<>());
                        Core.econ.withdrawPlayer(player, 50);
                        player.getInventory().addItem(chicken.asItemStack());
                    }
                } else if (e.getCurrentItem().isSimilar(steak.asItemStack())) {
                    if (Core.econ.has(player, 75)) {
                        steak.lore(new ArrayList<>());
                        Core.econ.withdrawPlayer(player, 75);
                        player.getInventory().addItem(steak.asItemStack());
                    }
                } else if (e.getCurrentItem().isSimilar(carrot.asItemStack())) {
                    if (Core.econ.has(player, 150)) {
                        carrot.lore(new ArrayList<>());
                        Core.econ.withdrawPlayer(player, 150);
                        player.getInventory().addItem(carrot.asItemStack());
                    }
                }
            } else if (e.getClick() == ClickType.MIDDLE) {
                if (e.getCurrentItem().isSimilar(potato.asItemStack())) {
                    if (Core.econ.has(player, 400)) {
                        potato.lore(new ArrayList<>());
                        Core.econ.withdrawPlayer(player, 400);
                        potato.amount(16);
                        player.getInventory().addItem(potato.asItemStack());
                    }
                } else if (e.getCurrentItem().isSimilar(chicken.asItemStack())) {
                    if (Core.econ.has(player, 800)) {
                        chicken.lore(new ArrayList<>());
                        Core.econ.withdrawPlayer(player, 800);
                        chicken.amount(16);
                        player.getInventory().addItem(chicken.asItemStack());
                    }
                } else if (e.getCurrentItem().isSimilar(steak.asItemStack())) {
                    if (Core.econ.has(player, 1200)) {
                        steak.lore(new ArrayList<>());
                        Core.econ.withdrawPlayer(player, 1200);
                        steak.amount(16);
                        player.getInventory().addItem(steak.asItemStack());
                    }
                } else if (e.getCurrentItem().isSimilar(carrot.asItemStack())) {
                    if (Core.econ.has(player, 2400)) {
                        carrot.lore(new ArrayList<>());
                        Core.econ.withdrawPlayer(player, 2400);
                        carrot.amount(16);
                        player.getInventory().addItem(carrot.asItemStack());
                    }
                }
            }
        }
    }

    @EventHandler
    public void closeFoodMerchantGui(InventoryCloseEvent e) {
        if (e.getView().getTitle().contains("Food Merchant")) {
            e.getPlayer().sendMessage("§e§lFood Merchant §f> Come back for more food!");
        }
    }

    //Member Picker
    public static Inventory getMemberPicker(Clan clan) {
        Inventory inv = Bukkit.createInventory(null, 9, "§6§lMember Picker");

        int i = 0;
        for (Player player : clan.getOnlinePlayers()) {
            ItemBuilder head = new ItemBuilder(CustomHeadManager.getPlayerHead(player));
            head.displayName("§f" + player.getName());
            head.lore("§eLeft click to add player", "§eRight click to remove player");
            inv.setItem(i, head.asItemStack());
            i++;
        }

        ItemBuilder confirm = new ItemBuilder(Material.GREEN_STAINED_GLASS_PANE);
        confirm.displayName("§aConfirm");
        inv.setItem(8, confirm.asItemStack());

        return inv;
    }

    @EventHandler
    public void memberPickerGui(InventoryClickEvent e) {
        if (e.getView().getTitle().contains("Member Picker")) {
            e.setCancelled(true);
            Player player = (Player) e.getWhoClicked();
            if (e.getCurrentItem() == null) return;
            if (e.getCurrentItem().getType() == Material.PLAYER_HEAD) {
                Clan clan = Clan.getClan(Clan.getPlayerClan(player));
                if (e.getClick() == ClickType.LEFT) {
                    if (!ItemHelper.getName(e.getCurrentItem()).startsWith("§a")) {
                        SkullMeta skull = (SkullMeta) e.getCurrentItem().getItemMeta();
                        if (skull.getOwningPlayer().isOnline()) {
                            Player skullPlayer = skull.getOwningPlayer().getPlayer();
                            ItemBuilder item = new ItemBuilder(e.getCurrentItem());
                            item.displayName("§a" + skullPlayer.getName());
                            e.setCurrentItem(item.asItemStack());
                            Challenge challenge = Challenge.pendingChallenges.get(clan);
                            if (challenge.clan1 == clan) {
                                if (!challenge.team1Participants.contains(skullPlayer))
                                    challenge.team1Participants.add(skullPlayer);
                            } else if (challenge.clan2 == clan) {
                                if (!challenge.team2Participants.contains(skullPlayer))
                                    challenge.team2Participants.add(skullPlayer);
                            }
                        }
                    }
                } else if (e.getClick() == ClickType.RIGHT) {
                    if (ItemHelper.getName(e.getCurrentItem()).startsWith("§a")) {
                        SkullMeta skull = (SkullMeta) e.getCurrentItem().getItemMeta();
                        if (skull.getOwningPlayer().isOnline()) {
                            Player skullPlayer = skull.getOwningPlayer().getPlayer();
                            ItemBuilder item = new ItemBuilder(e.getCurrentItem());
                            item.displayName("§f" + skullPlayer.getName());
                            e.setCurrentItem(item.asItemStack());
                            Challenge challenge = Challenge.pendingChallenges.get(clan);
                            if (challenge.clan1 == clan) {
                                if (challenge.team1Participants.contains(skullPlayer))
                                    challenge.team1Participants.remove(skullPlayer);
                            } else if (challenge.clan2 == clan) {
                                if (challenge.team2Participants.contains(skullPlayer))
                                    challenge.team2Participants.remove(skullPlayer);
                            }
                        }
                    }
                }
            }
            if (e.getClick() == ClickType.LEFT && ItemHelper.getName(e.getCurrentItem()).equalsIgnoreCase("§aConfirm")) {
                Clan clan = Clan.getClan(Clan.getPlayerClan(player));
                Challenge challenge = Challenge.pendingChallenges.get(clan);
                if (challenge.clan1 == clan) {
                    if (!challenge.team1Participants.isEmpty() && challenge.team1Participants.size() <= challenge.participants) {
                        player.closeInventory();
                        challenge.confirm1 = true;
                        if (challenge.confirm2) {
                            player.sendMessage(DefaultConfig.prefix + "§aBoth clans have confirmed, now placing in queue.");
                            Challenge.challengeQueue.add(challenge);
                        }
                    } else {
                        player.sendMessage(DefaultConfig.prefix + "§cYou can only pick up to §f" + challenge.participants + " §cparticipants.");
                    }
                }
                if (challenge.clan2 == clan) {
                    if (!challenge.team2Participants.isEmpty() && challenge.team2Participants.size() <= challenge.participants) {
                        player.closeInventory();
                        challenge.confirm2 = true;
                        if (challenge.confirm1) {
                            player.sendMessage(DefaultConfig.prefix + "§aBoth clans have confirmed, now placing in queue.");
                            Challenge.challengeQueue.add(challenge);
                        }
                    } else {
                        player.sendMessage(DefaultConfig.prefix + "§cYou can only pick up to §f" + challenge.participants + " §cparticipants.");
                    }
                }
            }
        }
    }

    @EventHandler
    public void closeMemberPickerGui(InventoryCloseEvent e) {
        if (e.getView().getTitle().contains("Member Picker")) {
            if (e.getReason() != InventoryCloseEvent.Reason.PLUGIN) {
                new BukkitRunnable() {
                    public void run() {
                        e.getPlayer().openInventory(e.getInventory());
                    }
                }.runTaskLater(Core.instance, 1L);
            }
        }
    }

    //Weapon Enchanter
    public static Inventory getWeaponEnchanter(ItemStack weapon) {
        Inventory inv = Bukkit.createInventory(null, 9, "§5§lWeapon Enchanter");
        inv.setItem(0, weapon);
        inv.setItem(1, new ItemBuilder(Material.WHITE_STAINED_GLASS_PANE).displayName("§8§lMC§c§lConquest").asItemStack());

        List<String> lores = ItemHelper.getLore(weapon);
        if (lores.size() >= 4) {
            ItemBuilder item = new ItemBuilder(Material.ENCHANTED_BOOK);
            item.displayName("§6" + lores.get(3).substring(0, lores.get(3).length() - 2));
            item.lore("§eClick to increase the enchant's level");
            inv.setItem(2, item.asItemStack());
        }
        if (lores.size() >= 6) {
            ItemBuilder item = new ItemBuilder(Material.ENCHANTED_BOOK);
            item.displayName("§6" + lores.get(5).substring(0, lores.get(5).length() - 2));
            item.lore("§eClick to increase the enchant's level");
            inv.setItem(3, item.asItemStack());
        }
        if (lores.size() >= 8) {
            ItemBuilder item = new ItemBuilder(Material.ENCHANTED_BOOK);
            item.displayName("§6" + lores.get(7).substring(0, lores.get(7).length() - 2));
            item.lore("§eClick to increase the enchant's level");
            inv.setItem(4, item.asItemStack());
        }
        return inv;
    }

    @EventHandler
    public void weaponEnchanterGui(InventoryClickEvent e) {
        if (e.getView().getTitle().contains("Weapon Enchanter")) {
            e.setCancelled(true);
            Player player = (Player) e.getWhoClicked();
            if (e.getClick() == ClickType.LEFT) {
                if (e.getCurrentItem().getType() == Material.ENCHANTED_BOOK) {
                    ItemStack weapon = e.getClickedInventory().getItem(0);
                    String enchant = ItemHelper.getName(e.getCurrentItem()).substring(2);
                    if (!InventoryUtil.checkMaxSwordLevel(weapon, enchant)) {
                        player.getInventory().addItem(InventoryUtil.increaseSwordEnchantLevel(weapon, enchant));
                        player.sendMessage("§8§lBlacksmith §f> Your enchant's level has been increased.");
                        player.closeInventory();
                    } else {
                        player.sendMessage("§8§lBlacksmith §f> Your enchant is already maxed level.");
                        player.closeInventory(InventoryCloseEvent.Reason.PLAYER);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onCloseWeaponEnchanterGui(InventoryCloseEvent e) {
        if (e.getView().getTitle().contains("Weapon Enchanter")) {
            if (e.getReason() == InventoryCloseEvent.Reason.DISCONNECT || e.getReason() == InventoryCloseEvent.Reason.PLAYER || e.getReason() == InventoryCloseEvent.Reason.TELEPORT || e.getReason() == InventoryCloseEvent.Reason.UNKNOWN) {
                e.getPlayer().getInventory().addItem(e.getInventory().getItem(0));
                e.getPlayer().getInventory().addItem(CustomItemManager.getBlacksmithsMagicDust());
            }
        }
    }

    //Armor Enchanter
    public static Inventory getArmorEnchanter(ItemStack armor) {
        Inventory inv = Bukkit.createInventory(null, 9, "§5§lArmor Enchanter");
        inv.setItem(0, armor);
        inv.setItem(1, new ItemBuilder(Material.WHITE_STAINED_GLASS_PANE).displayName("§8§lMC§c§lConquest").asItemStack());

        List<String> lores = ItemHelper.getLore(armor);
        if (lores.size() >= 4) {
            ItemBuilder item = new ItemBuilder(Material.ENCHANTED_BOOK);
            item.displayName("§6" + lores.get(3).substring(0, lores.get(3).length() - 2));
            item.lore("§eClick to increase the enchant's level");
            inv.setItem(2, item.asItemStack());
        }
        if (lores.size() >= 6) {
            ItemBuilder item = new ItemBuilder(Material.ENCHANTED_BOOK);
            item.displayName("§6" + lores.get(5).substring(0, lores.get(5).length() - 2));
            item.lore("§eClick to increase the enchant's level");
            inv.setItem(3, item.asItemStack());
        }
        return inv;
    }

    @EventHandler
    public void armorEnchanterGui(InventoryClickEvent e) {
        if (e.getView().getTitle().contains("Armor Enchanter")) {
            e.setCancelled(true);
            Player player = (Player) e.getWhoClicked();
            if (e.getClick() == ClickType.LEFT) {
                if (e.getCurrentItem().getType() == Material.ENCHANTED_BOOK) {
                    ItemStack armor = e.getClickedInventory().getItem(0);
                    String enchant = ItemHelper.getName(e.getCurrentItem()).substring(2);
                    if (!InventoryUtil.checkMaxArmorLevel(armor, enchant)) {
                        player.getInventory().addItem(InventoryUtil.increaseArmorEnchantLevel(armor, enchant));
                        player.sendMessage("§8§lBlacksmith §f> Your enchant's level has been increased.");
                        player.closeInventory();
                    } else {
                        player.sendMessage("§8§lBlacksmith §f> Your enchant is already maxed level.");
                        player.closeInventory(InventoryCloseEvent.Reason.PLAYER);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onCloseArmorEnchanterGui(InventoryCloseEvent e) {
        if (e.getView().getTitle().contains("Armor Enchanter")) {
            if (e.getReason() == InventoryCloseEvent.Reason.DISCONNECT || e.getReason() == InventoryCloseEvent.Reason.PLAYER || e.getReason() == InventoryCloseEvent.Reason.TELEPORT || e.getReason() == InventoryCloseEvent.Reason.UNKNOWN) {
                e.getPlayer().getInventory().addItem(e.getInventory().getItem(0));
                e.getPlayer().getInventory().addItem(CustomItemManager.getBlacksmithsMagicDust());
            }
        }
    }

    //Analyzer
    public static Inventory getAnalyzeInventory(Player player) {
        Inventory inv = Bukkit.createInventory(null, 18, "§6Analyzing: " + player.getName());
        for (int i = 0; i < 9; i++) {
            inv.setItem(i, player.getInventory().getItem(i));
        }
        inv.setItem(9, new ItemBuilder(CustomHeadManager.heads.get("right")).displayName("§8§lArmor ->").asItemStack());
        inv.setItem(17, new ItemBuilder(Material.RED_STAINED_GLASS_PANE).displayName("§cClose").asItemStack());
        if (player.getInventory().getItem(EquipmentSlot.HEAD) != null)
            inv.setItem(10, player.getInventory().getItem(EquipmentSlot.HEAD));
        if (player.getInventory().getItem(EquipmentSlot.CHEST) != null)
            inv.setItem(11, player.getInventory().getItem(EquipmentSlot.CHEST));
        if (player.getInventory().getItem(EquipmentSlot.LEGS) != null)
            inv.setItem(12, player.getInventory().getItem(EquipmentSlot.LEGS));
        if (player.getInventory().getItem(EquipmentSlot.FEET) != null)
            inv.setItem(13, player.getInventory().getItem(EquipmentSlot.FEET));
        return inv;
    }

    @EventHandler
    public void analyzeGui(InventoryClickEvent e) {
        if (e.getView().getTitle().contains("Analyzing")) {
            e.setCancelled(true);
            if (e.getCurrentItem() != null && e.getCurrentItem().getType() == Material.RED_STAINED_GLASS_PANE)
                e.getWhoClicked().closeInventory();
        }
    }

    //Warp
    public static Inventory getWarpInventory() {
        Inventory inv = Bukkit.createInventory(null, 18, "§6Warp Menu");

        inv.setItem(0, new ItemBuilder(Material.ANVIL).displayName("§8Blacksmith").lore("", "§eClick to warp here!").asItemStack());
        inv.setItem(2, new ItemBuilder(Material.IRON_INGOT).displayName("§7Iron Mines").lore("", "§eClick to warp here!").asItemStack());
        inv.setItem(4, new ItemBuilder(Material.DIAMOND).displayName("§bMarket Center").lore("", "§eClick to warp here!").asItemStack());
        inv.setItem(6, new ItemBuilder(Material.ENDER_EYE).displayName("§5Merger").lore("", "§eClick to warp here!").asItemStack());
        inv.setItem(8, new ItemBuilder(Material.IRON_SWORD).displayName("§4Mob Totem").lore("", "§eClick to warp here!").asItemStack());
        inv.setItem(10, new ItemBuilder(Material.DIAMOND_SWORD).displayName("§4PvP Mob Totem 1").lore("§aCost: $3000", "§eClick to warp here!").asItemStack());
        inv.setItem(12, new ItemBuilder(Material.WOODEN_SWORD).displayName("§4PvP Mob Totem 2").lore("§aCost: $3000", "§eClick to warp here!").asItemStack());
        inv.setItem(14, new ItemBuilder(Material.GOLD_BLOCK).displayName("§6PvP Gold Mines 1").lore("§aCost: $3000", "§eClick to warp here!").asItemStack());
        inv.setItem(16, new ItemBuilder(Material.GOLD_INGOT).displayName("§6PvP Gold Mines 2").lore("§aCost: $3000", "§eClick to warp here!").asItemStack());
        return inv;
    }

    @EventHandler
    public void warpGui(InventoryClickEvent e) {
        if (e.getView().getTitle().contains("Warp Menu")) {
            e.setCancelled(true);
            Player player = (Player) e.getWhoClicked();
            if (e.getCurrentItem() == null) return;
            if (e.getCurrentItem().getType() == Material.ANVIL) {
                player.performCommand("warp blacksmith");
                e.getWhoClicked().closeInventory();
            } else if (e.getCurrentItem().getType() == Material.IRON_INGOT) {
                player.performCommand("warp ironmine");
                e.getWhoClicked().closeInventory();
            } else if (e.getCurrentItem().getType() == Material.DIAMOND) {
                player.performCommand("warp merchantmarket");
                e.getWhoClicked().closeInventory();
            } else if (e.getCurrentItem().getType() == Material.ENDER_EYE) {
                player.performCommand("warp merger");
                e.getWhoClicked().closeInventory();
            } else if (e.getCurrentItem().getType() == Material.IRON_SWORD) {
                player.performCommand("warp totem2");
                e.getWhoClicked().closeInventory();
            } else if (e.getCurrentItem().getType() == Material.DIAMOND_SWORD) {
                if (Core.econ.has(player, 3000)) {
                    Core.econ.withdrawPlayer(player, 3000);
                    player.teleport(DefaultConfig.locations.get("totem1"));
                    e.getWhoClicked().closeInventory();
                }
            } else if (e.getCurrentItem().getType() == Material.WOODEN_SWORD) {
                if (Core.econ.has(player, 3000)) {
                    Core.econ.withdrawPlayer(player, 3000);
                    player.teleport(DefaultConfig.locations.get("totem2"));
                    e.getWhoClicked().closeInventory();
                }
            } else if (e.getCurrentItem().getType() == Material.GOLD_BLOCK) {
                if (Core.econ.has(player, 3000)) {
                    Core.econ.withdrawPlayer(player, 3000);
                    player.teleport(DefaultConfig.locations.get("gold1"));
                    e.getWhoClicked().closeInventory();
                }
            } else if (e.getCurrentItem().getType() == Material.GOLD_INGOT) {
                if (Core.econ.has(player, 3000)) {
                    Core.econ.withdrawPlayer(player, 3000);
                    player.teleport(DefaultConfig.locations.get("gold2"));
                    e.getWhoClicked().closeInventory();
                }
            }
        }
    }

    //Outposts
    @EventHandler
    public void outpostsGui(InventoryClickEvent e) {
        if (e.getView().getTitle().contains("Outposts")) {
            e.setCancelled(true);
        }
    }

    //Punish
    public static Inventory getPunishInventory(String player) {
        Inventory inv = Bukkit.createInventory(null, 54, "§4Punishing: " + player);

        for (int i = 0; i < 8; i++)
            inv.setItem(i, new ItemBuilder(Material.WHITE_STAINED_GLASS_PANE).displayName("§8§lMC§c§lConquest").asItemStack());
        inv.setItem(8, new ItemBuilder(Material.RED_STAINED_GLASS_PANE).displayName("§cClose").asItemStack());

        inv.setItem(9, new ItemBuilder(Material.PAPER).displayName("§c§lHacking & Modifications").asItemStack());
        inv.setItem(18, new ItemBuilder(Material.RED_STAINED_GLASS_PANE).displayName("§a1st Offense").lore("§fPermanent Ban").asItemStack());

        inv.setItem(10, new ItemBuilder(Material.PAPER).displayName("§c§lAdvertising").asItemStack());
        inv.setItem(19, new ItemBuilder(Material.ORANGE_STAINED_GLASS_PANE).displayName("§a1st Offense").lore("§fWarning").asItemStack());
        inv.setItem(28, new ItemBuilder(Material.ORANGE_STAINED_GLASS_PANE).displayName("§a2nd Offense").lore("§f7 Day Mute").asItemStack());
        inv.setItem(37, new ItemBuilder(Material.ORANGE_STAINED_GLASS_PANE).displayName("§a3rd Offense").lore("§fPermanent Mute").asItemStack());

        inv.setItem(11, new ItemBuilder(Material.PAPER).displayName("§c§lAbusing Glitches & Exploits").asItemStack());
        inv.setItem(20, new ItemBuilder(Material.YELLOW_STAINED_GLASS_PANE).displayName("§a1st Offense").lore("§f7 Day Ban").asItemStack());
        inv.setItem(29, new ItemBuilder(Material.YELLOW_STAINED_GLASS_PANE).displayName("§a2st Offense").lore("§fPermanent Ban").asItemStack());

        inv.setItem(12, new ItemBuilder(Material.PAPER).displayName("§c§lRacism").asItemStack());
        inv.setItem(21, new ItemBuilder(Material.GREEN_STAINED_GLASS_PANE).displayName("§a1st Offense").lore("§fWarning").asItemStack());
        inv.setItem(30, new ItemBuilder(Material.GREEN_STAINED_GLASS_PANE).displayName("§a2nd Offense").lore("§f1 Day Mute").asItemStack());
        inv.setItem(39, new ItemBuilder(Material.GREEN_STAINED_GLASS_PANE).displayName("§a3rd Offense").lore("§f7 Day Mute").asItemStack());
        inv.setItem(48, new ItemBuilder(Material.GREEN_STAINED_GLASS_PANE).displayName("§a4th Offense").lore("§f30 Day Mute").asItemStack());

        inv.setItem(13, new ItemBuilder(Material.PAPER).displayName("§c§lDisrespect").asItemStack());
        inv.setItem(22, new ItemBuilder(Material.BLUE_STAINED_GLASS_PANE).displayName("§a1st Offense").lore("§fWarning").asItemStack());
        inv.setItem(31, new ItemBuilder(Material.BLUE_STAINED_GLASS_PANE).displayName("§a2nd Offense").lore("§f1 Day Mute").asItemStack());
        inv.setItem(40, new ItemBuilder(Material.BLUE_STAINED_GLASS_PANE).displayName("§a3rd Offense").lore("§f7 Day Mute").asItemStack());

        inv.setItem(14, new ItemBuilder(Material.PAPER).displayName("§c§lDDOS & Other Threats").asItemStack());
        inv.setItem(23, new ItemBuilder(Material.PURPLE_STAINED_GLASS_PANE).displayName("§a1st Offense").lore("§f7 Day Ban").asItemStack());
        inv.setItem(32, new ItemBuilder(Material.PURPLE_STAINED_GLASS_PANE).displayName("§a2nd Offense").lore("§fPermanent Ban").asItemStack());

        inv.setItem(15, new ItemBuilder(Material.PAPER).displayName("§c§lSpamming or Bypass Swear Filter").asItemStack());
        inv.setItem(24, new ItemBuilder(Material.RED_STAINED_GLASS_PANE).displayName("§a1st Offense").lore("§fWarning").asItemStack());
        inv.setItem(33, new ItemBuilder(Material.RED_STAINED_GLASS_PANE).displayName("§a2nd Offense").lore("§fWarning").asItemStack());
        inv.setItem(42, new ItemBuilder(Material.RED_STAINED_GLASS_PANE).displayName("§a3rd Offense").lore("§f1 Day Mute").asItemStack());
        inv.setItem(51, new ItemBuilder(Material.RED_STAINED_GLASS_PANE).displayName("§a4th Offense").lore("§f7 Day Mute").asItemStack());

        inv.setItem(16, new ItemBuilder(Material.PAPER).displayName("§c§lScamming").asItemStack());
        inv.setItem(25, new ItemBuilder(Material.ORANGE_STAINED_GLASS_PANE).displayName("§a1st Offense").lore("§f7 Day Ban").asItemStack());
        inv.setItem(34, new ItemBuilder(Material.ORANGE_STAINED_GLASS_PANE).displayName("§a2nd Offense").lore("§f30 Day Ban").asItemStack());
        inv.setItem(43, new ItemBuilder(Material.ORANGE_STAINED_GLASS_PANE).displayName("§a3rd Offense").lore("§fPermanent Ban").asItemStack());

        inv.setItem(17, new ItemBuilder(Material.PAPER).displayName("§c§lAlt Accounts").asItemStack());
        inv.setItem(26, new ItemBuilder(Material.YELLOW_STAINED_GLASS_PANE).displayName("§a1st Offense").lore("§fWarning").asItemStack());
        inv.setItem(35, new ItemBuilder(Material.YELLOW_STAINED_GLASS_PANE).displayName("§a2nd Offense").lore("§fPermanent Ban").asItemStack());

        return inv;
    }

    @EventHandler
    public void punishGui(InventoryClickEvent e) {
        if (e.getView().getTitle().contains("§4Punishing: ")) {
            Player player = (Player) e.getWhoClicked();
            String playerName = e.getView().getTitle().replaceAll("§4Punishing: ", "");
            e.setCancelled(true);

            if (e.getClick() == ClickType.LEFT) {

                switch (e.getRawSlot()) {
                    case 8:
                        player.closeInventory();
                        break;
                    case 18:
                        player.performCommand("ban -s " + playerName + " Hacking/Modifications");
                        player.closeInventory();
                        break;
                    case 19:
                        player.performCommand("warn -s " + playerName + " No Advertising");
                        player.closeInventory();
                        break;
                    case 28:
                        player.performCommand("tempmute -s " + playerName + " 7d Advertising");
                        player.closeInventory();
                        break;
                    case 37:
                        player.performCommand("mute -s " + playerName + " Advertising");
                        player.closeInventory();
                        break;
                    case 20:
                        player.performCommand("tempban -s " + playerName + " 7d Abusing Glitches/Exploits");
                        player.closeInventory();
                        break;
                    case 29:
                        player.performCommand("ban -s " + playerName + " Abbusing Glitches/Exploits");
                        player.closeInventory();
                        break;
                    case 21:
                        player.performCommand("warn -s " + playerName + " No Racism/Discrimination");
                        player.closeInventory();
                        break;
                    case 30:
                        player.performCommand("tempmute -s " + playerName + " 1d Racism/Discrimination");
                        player.closeInventory();
                        break;
                    case 39:
                        player.performCommand("tempmute -s " + playerName + " 7d Racism/Discrimination");
                        player.closeInventory();
                        break;
                    case 48:
                        player.performCommand("tempmute -s " + playerName + " 30d Racism/Discrimination");
                        player.closeInventory();
                        break;
                    case 22:
                        player.performCommand("warn -s " + playerName + " No Disrespecting Staff/Players");
                        player.closeInventory();
                        break;
                    case 31:
                        player.performCommand("tempmute -s " + playerName + " 1d Disrespecting Staff/Players");
                        player.closeInventory();
                        break;
                    case 40:
                        player.performCommand("tempmute -s " + playerName + " 7d Disrespecting Staff/Players");
                        player.closeInventory();
                        break;
                    case 23:
                        player.performCommand("tempban -s " + playerName + " 7d DDOS/Other Threats");
                        player.closeInventory();
                        break;
                    case 32:
                        player.performCommand("ban -s " + playerName + " DDOS/Other Threats");
                        player.closeInventory();
                        break;
                    case 24:
                        player.performCommand("warn -s " + playerName + " No Spamming/Bypassing Swear Filter");
                        player.closeInventory();
                        break;
                    case 33:
                        player.performCommand("warn -s " + playerName + " No Spamming/Bypassing Swear Filter");
                        player.closeInventory();
                        break;
                    case 42:
                        player.performCommand("tempmute -s " + playerName + " 1d Spamming/Bypassing Swear Filter");
                        player.closeInventory();
                        break;
                    case 51:
                        player.performCommand("tempmute -s " + playerName + " 7d Spamming/Bypassing Swear Filter");
                        player.closeInventory();
                        break;
                    case 25:
                        player.performCommand("tempban -s " + playerName + " 7d Scamming");
                        player.closeInventory();
                        break;
                    case 34:
                        player.performCommand("tempban -s " + playerName + " 30d Scamming");
                        player.closeInventory();
                        break;
                    case 43:
                        player.performCommand("ban -s " + playerName + " Scamming");
                        player.closeInventory();
                        break;
                    case 26:
                        player.performCommand("warn -s " + playerName + " No Alt Accounts");
                        player.closeInventory();
                        break;
                    case 35:
                        player.performCommand("ban -s " + playerName + " Alt Account");
                        player.closeInventory();
                        break;
                }
            }
        }
    }
}
