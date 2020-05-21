package me.robertle.mcconquest;

import com.hazebyte.crate.api.util.ItemHelper;
import org.bukkit.Bukkit;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class FishingManager implements Listener {

    public static HashMap<String, FishingTimer> fishingPlayers = new HashMap<>();

    @EventHandler
    public void onFish(PlayerFishEvent e) {
        if (ItemHelper.hasName(e.getPlayer().getInventory().getItemInMainHand())) {
            String itemName = ItemHelper.getName(e.getPlayer().getInventory().getItemInMainHand());
            if (e.getState() == PlayerFishEvent.State.BITE) {
                e.setCancelled(true);
                return;
            }
            if (e.getState() == PlayerFishEvent.State.IN_GROUND) {
                if (fishingPlayers.containsKey(e.getPlayer().getName())) {
                    fishingPlayers.remove(e.getPlayer().getName());
                }
                e.getPlayer().sendMessage(DefaultConfig.prefix + "§cYou can only fish in the water.");
                return;
            }
            e.setExpToDrop(0);
            if (e.getState() == PlayerFishEvent.State.FISHING) {
                fishingPlayers.put(e.getPlayer().getName(), new FishingTimer());
                Core.sendActionBar(e.getPlayer(), "§f§l>>> §a§lFISHING §f§l<<<");
            }
            if (e.getState() == PlayerFishEvent.State.REEL_IN) {
                if (fishingPlayers.containsKey(e.getPlayer().getName())) {
                    if (fishingPlayers.get(e.getPlayer().getName()).state == FishingState.BITE) {
                        if (e.getPlayer().getInventory().getItemInMainHand().getItemMeta() instanceof Damageable) {
                            ItemMeta itemMeta = e.getPlayer().getInventory().getItemInMainHand().getItemMeta();
                            if (InventoryUtil.hasEnchant(e.getPlayer().getInventory().getItemInMainHand(), Enchantment.DURABILITY)) {
                                if (Core.chance(50))
                                    ((Damageable) itemMeta).setDamage(((Damageable) itemMeta).getDamage() + 1);
                            } else {
                                ((Damageable) itemMeta).setDamage(((Damageable) itemMeta).getDamage() + 1);
                            }
                            e.getPlayer().getInventory().getItemInMainHand().setItemMeta(itemMeta);
                        }
                        if (itemName.equalsIgnoreCase("§9Guardian Rod")) {
                            giveRandomGuardianFishingReward(e.getPlayer());
                        } else if (itemName.equalsIgnoreCase("§3Squid Rod")) {
                            giveRandomSquidFishingReward(e.getPlayer());
                        } else {
                            giveRandomFishingReward(e.getPlayer());
                        }
                        fishingPlayers.remove(e.getPlayer().getName());
                    } else {
                        Core.sendActionBar(e.getPlayer(), "§f§l>>> §c§lTOO EARLY §f§l<<<");
                        fishingPlayers.remove(e.getPlayer().getName());
                        return;
                    }
                }
            }
        }
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent e) {
        if (fishingPlayers.containsKey(e.getPlayer().getName())) {
            fishingPlayers.remove(e.getPlayer().getName());
        }
    }

    @EventHandler
    public void onLeave(PlayerItemHeldEvent e) {
        if (fishingPlayers.containsKey(e.getPlayer().getName())) {
            fishingPlayers.remove(e.getPlayer().getName());
        }
    }

    public static void giveRandomFishingReward(Player player) {
        List<FishingReward> pot = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            if (i < 30) {
                pot.add(FishingReward.IRON_INGOT);
                pot.add(FishingReward.GOLD_INGOT);
                pot.add(FishingReward.ESSENCE);
                pot.add(FishingReward.RARE_ESSENCE);
                pot.add(FishingReward.MONEY_NOTE);
            }
            if (i < 4) {
                pot.add(FishingReward.BASIC_CRATE);
            }
            if (i < 2) {
                pot.add(FishingReward.ARTIFACT_VOUCHER);
            }
            if (i < 1) {
                pot.add(FishingReward.SUPER_CRATE);
            }
        }
        Collections.shuffle(pot);
        FishingReward reward = pot.get(Core.generateNumber(0, pot.size() - 1));
        int amount;
        switch (reward) {
            case IRON_INGOT:
                amount = Core.generateNumber(1, 6);
                player.getInventory().addItem(CustomItemManager.getIngot(false, amount));
                player.sendMessage(DefaultConfig.prefix + "§aYou caught: " + amount + " Iron Ingot");
                break;
            case GOLD_INGOT:
                amount = Core.generateNumber(1, 3);
                player.getInventory().addItem(CustomItemManager.getIngot(true, amount));
                player.sendMessage(DefaultConfig.prefix + "§aYou caught: " + amount + " Gold Ingot");
                break;
            case ESSENCE:
                amount = Core.generateNumber(1, 3);
                player.getInventory().addItem(CustomItemManager.getEssence(false, amount));
                player.sendMessage(DefaultConfig.prefix + "§aYou caught: " + amount + " Essence");
                break;
            case RARE_ESSENCE:
                amount = Core.generateNumber(1, 2);
                player.getInventory().addItem(CustomItemManager.getEssence(true, amount));
                player.sendMessage(DefaultConfig.prefix + "§aYou caught: " + amount + " Rare Essence");
                break;
            case MONEY_NOTE:
                player.getInventory().addItem(CustomItemManager.getRandomMoneyNote(1000, 2500));
                player.sendMessage(DefaultConfig.prefix + "§aYou caught: Money Note");
                break;
            case ARTIFACT_VOUCHER:
                player.getInventory().addItem(CustomItemManager.getArtifactVoucher(false));
                player.sendMessage(DefaultConfig.prefix + "§aYou caught: Artifact Voucher");
                break;
            case BASIC_CRATE:
                //player.getInventory().addItem(CustomItemManager.getCrate(Crate.BASIC_CRATE));
                player.sendMessage(DefaultConfig.prefix + "§aYou caught: Basic Crate");
                break;
            case SUPER_CRATE:
                //player.getInventory().addItem(CustomItemManager.getCrate(Crate.SUPER_CRATE));
                player.sendMessage(DefaultConfig.prefix + "§aYou caught: Super Crate");
                break;
        }
    }

    public static void giveRandomGuardianFishingReward(Player player) {
        List<FishingReward> pot = new ArrayList<>();
        for (int i = 0; i < 60; i++) {
            if (i < 40) {
                pot.add(FishingReward.IRON_INGOT);
                pot.add(FishingReward.GOLD_INGOT);
                pot.add(FishingReward.ESSENCE);
                pot.add(FishingReward.RARE_ESSENCE);
                pot.add(FishingReward.MONEY_NOTE);
            }
            if (i < 4) {
                pot.add(FishingReward.BASIC_CRATE);
            }
            if (i < 3) {
                pot.add(FishingReward.ARTIFACT_VOUCHER);
                pot.add(FishingReward.SPECIAL_ARTIFACT_VOUCHER);
                pot.add(FishingReward.SUPER_CRATE);
            }
            if (i < 2) {
                pot.add(FishingReward.BLACKSMITHS_MAGIC_DUST);
            }
            if (i < 1) {
                pot.add(FishingReward.ULTRA_CRATE);
            }
        }
        Collections.shuffle(pot);
        FishingReward reward = pot.get(Core.generateNumber(0, pot.size() - 1));
        int amount;
        switch (reward) {
            case IRON_INGOT:
                amount = Core.generateNumber(4, 8);
                player.getInventory().addItem(CustomItemManager.getIngot(false, amount));
                player.sendMessage(DefaultConfig.prefix + "§aYou caught: " + amount + " Iron Ingot");
                break;
            case GOLD_INGOT:
                amount = Core.generateNumber(2, 6);
                player.getInventory().addItem(CustomItemManager.getIngot(true, amount));
                player.sendMessage(DefaultConfig.prefix + "§aYou caught: " + amount + " Gold Ingot");
                break;
            case ESSENCE:
                amount = Core.generateNumber(3, 6);
                player.getInventory().addItem(CustomItemManager.getEssence(false, amount));
                player.sendMessage(DefaultConfig.prefix + "§aYou caught: " + amount + " Essence");
                break;
            case RARE_ESSENCE:
                amount = Core.generateNumber(2, 4);
                player.getInventory().addItem(CustomItemManager.getEssence(true, amount));
                player.sendMessage(DefaultConfig.prefix + "§aYou caught: " + amount + " Rare Essence");
                break;
            case MONEY_NOTE:
                player.getInventory().addItem(CustomItemManager.getRandomMoneyNote(1000, 3000));
                player.sendMessage(DefaultConfig.prefix + "§aYou caught: Money Note");
                break;
            case ARTIFACT_VOUCHER:
                player.getInventory().addItem(CustomItemManager.getArtifactVoucher(false));
                player.sendMessage(DefaultConfig.prefix + "§aYou caught: Artifact Voucher");
                break;
            case SPECIAL_ARTIFACT_VOUCHER:
                player.getInventory().addItem(CustomItemManager.getArtifactVoucher(true));
                player.sendMessage(DefaultConfig.prefix + "§aYou caught: Special Artifact Voucher");
                break;
            case BASIC_CRATE:
                //player.getInventory().addItem(CustomItemManager.getCrate(Crate.BASIC_CRATE));
                player.sendMessage(DefaultConfig.prefix + "§aYou caught: Basic Crate");
                break;
            case SUPER_CRATE:
                //player.getInventory().addItem(CustomItemManager.getCrate(Crate.SUPER_CRATE));
                player.sendMessage(DefaultConfig.prefix + "§aYou caught: Super Crate");
                break;
            case ULTRA_CRATE:
                //player.getInventory().addItem(CustomItemManager.getCrate(Crate.ULTRA_CRATE));
                player.sendMessage(DefaultConfig.prefix + "§aYou caught: Ultra Crate");
                break;
            case BLACKSMITHS_MAGIC_DUST:
                //give player
                player.sendMessage(DefaultConfig.prefix + "§aYou caught: Blacksmith's Magic Dust");
                break;
        }
    }

    public static void giveRandomSquidFishingReward(Player player) {
        List<FishingReward> pot = new ArrayList<>();
        for (int i = 0; i < 60; i++) {
            if (i < 35) {
                pot.add(FishingReward.IRON_INGOT);
                pot.add(FishingReward.GOLD_INGOT);
                pot.add(FishingReward.ESSENCE);
                pot.add(FishingReward.RARE_ESSENCE);
                pot.add(FishingReward.MONEY_NOTE);
            }
            if (i < 5) {
                pot.add(FishingReward.BASIC_CRATE);
            }
            if (i < 3) {
                pot.add(FishingReward.ARTIFACT_VOUCHER);
            }
            if (i < 2) {
                pot.add(FishingReward.BLACKSMITHS_MAGIC_DUST);
                pot.add(FishingReward.SPECIAL_ARTIFACT_VOUCHER);
                pot.add(FishingReward.SUPER_CRATE);
            }
            if (i < 1) {
                pot.add(FishingReward.BLACKSMITHS_MAGIC_DUST);
            }
        }
        Collections.shuffle(pot);
        FishingReward reward = pot.get(Core.generateNumber(0, pot.size() - 1));
        int amount;
        switch (reward) {
            case IRON_INGOT:
                amount = Core.generateNumber(3, 6);
                player.getInventory().addItem(CustomItemManager.getIngot(false, amount));
                player.sendMessage(DefaultConfig.prefix + "§aYou caught: " + amount + " Iron Ingot");
                break;
            case GOLD_INGOT:
                amount = Core.generateNumber(2, 6);
                player.getInventory().addItem(CustomItemManager.getIngot(true, amount));
                player.sendMessage(DefaultConfig.prefix + "§aYou caught: " + amount + " Gold Ingot");
                break;
            case ESSENCE:
                amount = Core.generateNumber(3, 6);
                player.getInventory().addItem(CustomItemManager.getEssence(false, amount));
                player.sendMessage(DefaultConfig.prefix + "§aYou caught: " + amount + " Essence");
                break;
            case RARE_ESSENCE:
                amount = Core.generateNumber(2, 4);
                player.getInventory().addItem(CustomItemManager.getEssence(true, amount));
                player.sendMessage(DefaultConfig.prefix + "§aYou caught: " + amount + " Rare Essence");
                break;
            case MONEY_NOTE:
                player.getInventory().addItem(CustomItemManager.getRandomMoneyNote(1500, 3500));
                player.sendMessage(DefaultConfig.prefix + "§aYou caught: Money Note");
                break;
            case ARTIFACT_VOUCHER:
                player.getInventory().addItem(CustomItemManager.getArtifactVoucher(false));
                player.sendMessage(DefaultConfig.prefix + "§aYou caught: Artifact Voucher");
                break;
            case SPECIAL_ARTIFACT_VOUCHER:
                player.getInventory().addItem(CustomItemManager.getArtifactVoucher(true));
                player.sendMessage(DefaultConfig.prefix + "§aYou caught: Special Artifact Voucher");
                break;
            case BASIC_CRATE:
                //player.getInventory().addItem(CustomItemManager.getCrate(Crate.BASIC_CRATE));
                player.sendMessage(DefaultConfig.prefix + "§aYou caught: Basic Crate");
                break;
            case SUPER_CRATE:
                //player.getInventory().addItem(CustomItemManager.getCrate(Crate.SUPER_CRATE));
                player.sendMessage(DefaultConfig.prefix + "§aYou caught: Super Crate");
                break;
            case ULTRA_CRATE:
                //player.getInventory().addItem(CustomItemManager.getCrate(Crate.ULTRA_CRATE));
                player.sendMessage(DefaultConfig.prefix + "§aYou caught: Ultra Crate");
                break;
            case BLACKSMITHS_MAGIC_DUST:
                //give player
                player.sendMessage(DefaultConfig.prefix + "§aYou caught: Blacksmith's Magic Dust");
                break;
        }
    }

    public static void runFishTimers() {
        new BukkitRunnable() {
            public void run() {
                if (!fishingPlayers.isEmpty()) {
                    for (String playerName : fishingPlayers.keySet()) {
                        if (fishingPlayers.get(playerName).state == FishingState.FISHING)
                            Core.sendActionBar(Bukkit.getPlayer(playerName), "§f§l>>> §a§lFISHING §f§l<<<");
                        if (fishingPlayers.get(playerName).catchTime == 0) {
                            fishingPlayers.get(playerName).state = FishingState.BITE;
                            Core.sendActionBar(Bukkit.getPlayer(playerName), "§f§l>>> §e§lBITE §f§l<<<");
                        } else if (fishingPlayers.get(playerName).catchTime == -2) {
                            fishingPlayers.get(playerName).state = FishingState.BITE_LOSS;
                            Core.sendActionBar(Bukkit.getPlayer(playerName), "§f§l>>> §c§lBITE LOSS §f§l<<<");
                            fishingPlayers.remove(playerName);
                        }
                        if (fishingPlayers.containsKey(playerName)) fishingPlayers.get(playerName).catchTime -= 1;
                    }
                }
            }
        }.runTaskTimer(Core.instance, 0L, 20L);
    }

    public class FishingTimer {
        public FishingState state;
        public int catchTime;

        public FishingTimer() {
            state = FishingState.FISHING;
            catchTime = Core.generateNumber(4, 18);
        }

    }

    private enum FishingState {
        FISHING, BITE, BITE_LOSS
    }

    private enum FishingReward {
        IRON_INGOT, GOLD_INGOT, ESSENCE, RARE_ESSENCE,
        MONEY_NOTE, BASIC_CRATE, ARTIFACT_VOUCHER, SPECIAL_ARTIFACT_VOUCHER,
        SUPER_CRATE, ULTRA_CRATE, BLACKSMITHS_MAGIC_DUST
    }

}
