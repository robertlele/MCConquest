package me.robertle.mcconquest;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

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
                perk.lore("§f§nNext Perk", "", "§eUnlock size 9 clan storage");
                break;
            case 1:
                perk.lore("§f§nNext Perk", "", "§eEach member receives an armor voucher", "§c§lWARNING §r§fItems will be put in the clan storage", "§fso make sure it's not full");
                break;
            case 2:
                perk.lore("§f§nNext Perk", "", "§eUnlock size 27 clan storage");
                break;
            case 3:
                perk.lore("§f§nNext Perk", "", "§eEach member receives a weapon voucher,", "§eAble to challenge other clans for rewards", "§c§lWARNING §r§fItems will be put in the clan storage", "§fso make sure it's not full");
                break;
            case 4:
                perk.lore("§f§nNext Perk", "", "§eUnlock size 36 clan storage");
                break;
            case 5:
                perk.lore("§f§nNext Perk", "", "§eEach member receives a special artifact voucher", "§c§lWARNING §r§fItems will be put in the clan storage", "§fso make sure it's not full");
                break;
            case 6:
                perk.lore("§f§nNext Perk", "", "§eEach member receives a special armor voucher", "§c§lWARNING §r§fItems will be put in the clan storage", "§fso make sure it's not full");
                break;
            case 7:
                perk.lore("§f§nNext Perk", "", "§eEach member receives a special weapon voucher", "§c§lWARNING §r§fItems will be put in the clan storage", "§fso make sure it's not full");
                break;
            case 8:
                perk.lore("§f§nNext Perk", "", "§eEach member receives an super crate", "§c§lWARNING §r§fItems will be put in the clan storage", "§fso make sure it's not full");
                break;
            case 9:
                perk.lore("§f§nNext Perk", "", "§eEach member receives an ultra crate,", "§eAble to participate in the clan war", "§c§lWARNING §r§fItems will be put in the clan storage", "§fso make sure it's not full");
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
                buy.lore("§aCost: $" + 1200000, "", "§eLeft click to unlock");
                break;
            case 4:
                buy.lore("§aCost: $" + 400000, "", "§eLeft click to unlock");
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
                buy.lore("§aCost: $" + 1200000, "", "§eLeft click to unlock");
                break;
            case 9:
                buy.lore("§aCost: $" + 1600000, "", "§eLeft click to unlock");
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
            if (e.isLeftClick() && ItemHelper.hasName(e.getCurrentItem())) {
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
                if (e.getInventory().getItem(6) != null)
                    e.getPlayer().getInventory().addItem(e.getInventory().getItem(6));
                if (e.getInventory().getItem(2) != null)
                    e.getPlayer().getInventory().addItem(e.getInventory().getItem(2));
            }
        }
    }

    //Combat Merchant Gui
    public static Inventory getCombatMerchantGui() {
        Inventory inv = Bukkit.createInventory(null, 9, "§e§lCombat Merchant");
        inv.setItem(0, new ItemBuilder(Material.WHITE_STAINED_GLASS_PANE).displayName("§8§lMC§c§lConquest").asItemStack());
        inv.setItem(8, new ItemBuilder(Material.WHITE_STAINED_GLASS_PANE).displayName("§8§lMC§c§lConquest").asItemStack());
        ItemBuilder weaponVoucher = new ItemBuilder(CustomItemManager.getWeaponVoucher(false));
        weaponVoucher.lore("§aCost: 448 Iron Ingot", "", "§eLeft click to buy");
        ItemBuilder specialWeaponVoucher = new ItemBuilder(CustomItemManager.getWeaponVoucher(true));
        specialWeaponVoucher.lore("§aCost: 320 Gold Ingot", "", "§eLeft click to buy");
        ItemBuilder armorVoucher = new ItemBuilder(CustomItemManager.getArmorVoucher(false));
        armorVoucher.lore("§aCost: 256 Iron Ingot", "", "§eLeft click to buy");
        ItemBuilder specialArmorVoucher = new ItemBuilder(CustomItemManager.getArmorVoucher(true));
        specialArmorVoucher.lore("§aCost: 192 Gold Ingot", "", "§eLeft click to buy");
        ItemBuilder blacksmithsCoal = new ItemBuilder(CustomItemManager.getBlacksmithsCoal());
        blacksmithsCoal.lore("§aCost: 128 Rare Essence", "", "§eLeft click to buy");
        ItemBuilder blacksmithsMagmaRod = new ItemBuilder(CustomItemManager.getBlacksmithsMagmaRod());
        blacksmithsMagmaRod.lore("§aCost: 192 Rare Essence", "", "§eLeft click to buy");
        ItemBuilder arrow = new ItemBuilder(Material.ARROW);
        arrow.lore("§aCost: $10", "", "§eLeft click to buy", "§eMiddle click to buy 16");
        inv.setItem(1, armorVoucher.asItemStack());
        inv.setItem(2, specialArmorVoucher.asItemStack());
        inv.setItem(3, weaponVoucher.asItemStack());
        inv.setItem(4, specialWeaponVoucher.asItemStack());
        inv.setItem(5, blacksmithsCoal.asItemStack());
        inv.setItem(6, blacksmithsMagmaRod.asItemStack());
        inv.setItem(7, arrow.asItemStack());
        return inv;
    }

    @EventHandler
    public void combatMerchantGui(InventoryClickEvent e) {
        if (e.getView().getTitle().contains("Combat Merchant")) {
            e.setCancelled(true);
            if (e.isLeftClick()) {
                ItemBuilder weaponVoucher = new ItemBuilder(CustomItemManager.getWeaponVoucher(false));
                weaponVoucher.lore("§aCost: 448 Iron Ingot", "", "§eLeft click to buy");
                ItemBuilder specialWeaponVoucher = new ItemBuilder(CustomItemManager.getWeaponVoucher(true));
                specialWeaponVoucher.lore("§aCost: 320 Gold Ingot", "", "§eLeft click to buy");
                ItemBuilder armorVoucher = new ItemBuilder(CustomItemManager.getArmorVoucher(false));
                armorVoucher.lore("§aCost: 256 Iron Ingot", "", "§eLeft click to buy");
                ItemBuilder specialArmorVoucher = new ItemBuilder(CustomItemManager.getArmorVoucher(true));
                specialArmorVoucher.lore("§aCost: 192 Gold Ingot", "", "§eLeft click to buy");
                ItemBuilder blacksmithsCoal = new ItemBuilder(CustomItemManager.getBlacksmithsCoal());
                blacksmithsCoal.lore("§aCost: 128 Rare Essence", "", "§eLeft click to buy");
                ItemBuilder blacksmithsMagmaRod = new ItemBuilder(CustomItemManager.getBlacksmithsMagmaRod());
                blacksmithsMagmaRod.lore("§aCost: 192 Rare Essence", "", "§eLeft click to buy");
                ItemBuilder arrow = new ItemBuilder(Material.ARROW);
                arrow.lore("§aCost: $150", "", "§eLeft click to buy 16");
                Player player = (Player) e.getWhoClicked();
                if (e.getCurrentItem().isSimilar(weaponVoucher.asItemStack())) {
                    if (player.getInventory().containsAtLeast(CustomItemManager.getIngot(false, 1), 448)) {
                        InventoryUtil.removeItems(player, CustomItemManager.getIngot(false, 1), 448);
                        player.getInventory().addItem(CustomItemManager.getWeaponVoucher(false));
                        player.sendMessage("§4§lCombat Merchant §f> Best of luck!");
                        player.closeInventory();
                    } else {
                        player.sendMessage("§4§lCombat Merchant §f> You don't have enough for this.");
                        player.closeInventory();
                    }
                } else if (e.getCurrentItem().isSimilar(specialWeaponVoucher.asItemStack())) {
                    if (player.getInventory().containsAtLeast(CustomItemManager.getIngot(true, 1), 320)) {
                        InventoryUtil.removeItems(player, CustomItemManager.getIngot(true, 1), 320);
                        player.getInventory().addItem(CustomItemManager.getWeaponVoucher(true));
                        player.sendMessage("§4§lCombat Merchant §f> Best of luck!");
                        player.closeInventory();
                    } else {
                        player.sendMessage("§4§lCombat Merchant §f> You don't have enough for this.");
                        player.closeInventory();
                    }
                } else if (e.getCurrentItem().isSimilar(armorVoucher.asItemStack())) {
                    if (player.getInventory().containsAtLeast(CustomItemManager.getIngot(false, 1), 256)) {
                        InventoryUtil.removeItems(player, CustomItemManager.getIngot(false, 1), 256);
                        player.getInventory().addItem(CustomItemManager.getArmorVoucher(false));
                        player.sendMessage("§4§lCombat Merchant §f> Best of luck!");
                        player.closeInventory();
                    } else {
                        player.sendMessage("§4§lCombat Merchant §f> You don't have enough for this.");
                        player.closeInventory();
                    }
                } else if (e.getCurrentItem().isSimilar(specialArmorVoucher.asItemStack())) {
                    if (player.getInventory().containsAtLeast(CustomItemManager.getIngot(true, 1), 192)) {
                        InventoryUtil.removeItems(player, CustomItemManager.getIngot(true, 1), 192);
                        player.getInventory().addItem(CustomItemManager.getArmorVoucher(true));
                        player.sendMessage("§4§lCombat Merchant §f> Best of luck!");
                        player.closeInventory();
                    } else {
                        player.sendMessage("§4§lCombat Merchant §f> You don't have enough for this.");
                        player.closeInventory();
                    }
                } else if (e.getCurrentItem().isSimilar(blacksmithsCoal.asItemStack())) {
                    if (player.getInventory().containsAtLeast(CustomItemManager.getEssence(true, 1), 128)) {
                        InventoryUtil.removeItems(player, CustomItemManager.getEssence(true, 1), 128);
                        player.getInventory().addItem(CustomItemManager.getBlacksmithsCoal());
                        player.sendMessage("§4§lCombat Merchant §f> Best of luck!");
                        player.closeInventory();
                    } else {
                        player.sendMessage("§4§lCombat Merchant §f> You don't have enough for this.");
                        player.closeInventory();
                    }
                } else if (e.getCurrentItem().isSimilar(blacksmithsMagmaRod.asItemStack())) {
                    if (player.getInventory().containsAtLeast(CustomItemManager.getEssence(true, 1), 192)) {
                        InventoryUtil.removeItems(player, CustomItemManager.getEssence(true, 1), 192);
                        player.getInventory().addItem(CustomItemManager.getBlacksmithsMagmaRod());
                        player.sendMessage("§4§lCombat Merchant §f> Best of luck!");
                        player.closeInventory();
                    } else {
                        player.sendMessage("§4§lCombat Merchant §f> You don't have enough for this.");
                        player.closeInventory();
                    }
                } else if (e.getCurrentItem().isSimilar(arrow.asItemStack())) {
                    if (Core.econ.has(player, 150)) {
                        Core.econ.withdrawPlayer(player, 150);
                        arrow.lore(new ArrayList<>());
                        arrow.amount(16);
                        player.getInventory().addItem(arrow.asItemStack());
                    }
                }
            }
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

            if (e.isLeftClick()) {
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

        ItemBuilder artifactVoucher = new ItemBuilder(CustomItemManager.getArtifactVoucher());
        artifactVoucher.lore("§aCost: 256 Essence", "", "§eLeft click to buy 1");

        ItemBuilder petCrate = new ItemBuilder(CustomItemManager.getCrate(Crate.PET_CRATE));
        petCrate.lore("§aCost: 384 Essence", "", "§eLeft click to buy 1");

        inv.setItem(1, enderpearl.asItemStack());
        inv.setItem(2, gapple.asItemStack());
        inv.setItem(3, pickaxe.asItemStack());
        inv.setItem(4, rod.asItemStack());
        inv.setItem(5, artifactVoucher.asItemStack());
        inv.setItem(6, petCrate.asItemStack());

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

            ItemBuilder artifactVoucher = new ItemBuilder(CustomItemManager.getArtifactVoucher());
            artifactVoucher.lore("§aCost: 256 Essence", "", "§eLeft click to buy 1");

            ItemBuilder petCrate = new ItemBuilder(CustomItemManager.getCrate(Crate.PET_CRATE));
            petCrate.lore("§aCost: 384 Essence", "", "§eLeft click to buy 1");

            Player player = (Player) e.getWhoClicked();

            if (e.isLeftClick()) {
                if (e.getCurrentItem().isSimilar(enderpearl.asItemStack())) {
                    if (Core.econ.has(player, 400)) {
                        enderpearl.lore(new ArrayList<>());
                        player.getInventory().addItem(enderpearl.asItemStack());
                    }
                } else if (e.getCurrentItem().isSimilar(gapple.asItemStack())) {
                    if (Core.econ.has(player, 1000)) {
                        gapple.lore(new ArrayList<>());
                        player.getInventory().addItem(gapple.asItemStack());
                    }
                } else if (e.getCurrentItem().isSimilar(pickaxe.asItemStack())) {
                    if (Core.econ.has(player, 5000)) {
                        pickaxe.lore("§a§l✸B-TIER", "", "");
                        player.getInventory().addItem(pickaxe.asItemStack());
                    }
                } else if (e.getCurrentItem().isSimilar(rod.asItemStack())) {
                    if (Core.econ.has(player, 5000)) {
                        rod.lore("§a§l✸B-TIER", "", "");
                        player.getInventory().addItem(rod.asItemStack());
                    }
                } else if (e.getCurrentItem().isSimilar(artifactVoucher.asItemStack())) {
                    if (player.getInventory().containsAtLeast(CustomItemManager.getEssence(false, 1), 256)) {
                        InventoryUtil.removeItems(player, CustomItemManager.getEssence(false, 1), 256);
                        player.getInventory().addItem(CustomItemManager.getArtifactVoucher());
                        player.sendMessage("§6§lSpecial Merchant §f> Best of luck!");
                        player.closeInventory();
                    } else {
                        player.sendMessage("§6§lSpecial Merchant §f> You don't have enough for this.");
                        player.closeInventory();
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
                        player.getInventory().addItem(enderpearl.asItemStack());
                    }
                } else if (e.getCurrentItem().isSimilar(gapple.asItemStack())) {
                    if (Core.econ.has(player, 16000)) {
                        gapple.amount(16);
                        gapple.lore(new ArrayList<>());
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

            if (e.isLeftClick()) {
                if (e.getCurrentItem().isSimilar(potato.asItemStack())) {
                    if (Core.econ.has(player, 25)) {
                        potato.lore(new ArrayList<>());
                        player.getInventory().addItem(potato.asItemStack());
                    }
                } else if (e.getCurrentItem().isSimilar(chicken.asItemStack())) {
                    if (Core.econ.has(player, 50)) {
                        chicken.lore(new ArrayList<>());
                        player.getInventory().addItem(chicken.asItemStack());
                    }
                } else if (e.getCurrentItem().isSimilar(steak.asItemStack())) {
                    if (Core.econ.has(player, 75)) {
                        steak.lore(new ArrayList<>());
                        player.getInventory().addItem(steak.asItemStack());
                    }
                } else if (e.getCurrentItem().isSimilar(carrot.asItemStack())) {
                    if (Core.econ.has(player, 150)) {
                        carrot.lore(new ArrayList<>());
                        player.getInventory().addItem(carrot.asItemStack());
                    }
                }
            } else if (e.getClick() == ClickType.MIDDLE) {
                if (e.getCurrentItem().isSimilar(potato.asItemStack())) {
                    if (Core.econ.has(player, 400)) {
                        potato.lore(new ArrayList<>());
                        potato.amount(16);
                        player.getInventory().addItem(potato.asItemStack());
                    }
                } else if (e.getCurrentItem().isSimilar(chicken.asItemStack())) {
                    if (Core.econ.has(player, 800)) {
                        chicken.lore(new ArrayList<>());
                        chicken.amount(16);
                        player.getInventory().addItem(chicken.asItemStack());
                    }
                } else if (e.getCurrentItem().isSimilar(steak.asItemStack())) {
                    if (Core.econ.has(player, 1200)) {
                        steak.lore(new ArrayList<>());
                        steak.amount(16);
                        player.getInventory().addItem(steak.asItemStack());
                    }
                } else if (e.getCurrentItem().isSimilar(carrot.asItemStack())) {
                    if (Core.econ.has(player, 2400)) {
                        carrot.lore(new ArrayList<>());
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
                    if (!e.getCurrentItem().getItemMeta().hasEnchants()) {
                        SkullMeta skull = (SkullMeta) e.getCurrentItem().getItemMeta();
                        if (skull.getOwningPlayer().isOnline()) {
                            Player skullPlayer = skull.getOwningPlayer().getPlayer();
                            ItemBuilder item = new ItemBuilder(e.getCurrentItem());
                            item.setGlowing(true);
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
                    if (e.getCurrentItem().getItemMeta().hasEnchants()) {
                        SkullMeta skull = (SkullMeta) e.getCurrentItem().getItemMeta();
                        if (skull.getOwningPlayer().isOnline()) {
                            Player skullPlayer = skull.getOwningPlayer().getPlayer();
                            ItemBuilder item = new ItemBuilder(e.getCurrentItem());
                            item.setGlowing(false);
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
            if (e.isLeftClick() && ItemHelper.getName(e.getCurrentItem()).equalsIgnoreCase("§aConfirm")) {
                Clan clan = Clan.getClan(Clan.getPlayerClan(player));
                Challenge challenge = Challenge.pendingChallenges.get(clan);
                if (challenge.clan1 == clan) {
                    if (!challenge.team1Participants.isEmpty()) {
                        player.closeInventory();
                        challenge.confirm1 = true;
                        if (challenge.confirm2) {
                            player.sendMessage(DefaultConfig.prefix + "§aBoth clans have confirmed, now placing in queue.");
                            Challenge.challengeQueue.add(challenge);
                        }
                    }
                }
                if (challenge.clan2 == clan) {
                    if (!challenge.team1Participants.isEmpty()) {
                        player.closeInventory();
                        challenge.confirm2 = true;
                        if (challenge.confirm1) {
                            player.sendMessage(DefaultConfig.prefix + "§aBoth clans have confirmed, now placing in queue.");
                            Challenge.challengeQueue.add(challenge);
                        }
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

}
