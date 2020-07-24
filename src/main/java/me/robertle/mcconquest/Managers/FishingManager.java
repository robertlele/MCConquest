package me.robertle.mcconquest.Managers;

import me.robertle.mcconquest.*;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
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

import java.util.*;

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
                fishingPlayers.put(e.getPlayer().getName(), new FishingTimer(e.getPlayer()));
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
                            if (Core.chance(.10))
                                e.getPlayer().getInventory().addItem(CustomItemManager.getTag(Tag.FISHERMAN));
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
        for (int i = 0; i < 60; i++) {
            if (i < 60) {
                pot.add(FishingReward.IRON_INGOT);
                pot.add(FishingReward.GOLD_INGOT);
                pot.add(FishingReward.ESSENCE);
                pot.add(FishingReward.RARE_ESSENCE);
                pot.add(FishingReward.MONEY_NOTE);
            }
            if (i < 6) {
                pot.add(FishingReward.BASIC_CRATE);
            }
            if (i < 2) {
                pot.add(FishingReward.SUPER_CRATE);
            }
        }
        if (Pet.isSummoned(player, Pet.GUARDIAN)) {
            pot.add(FishingReward.BASIC_CRATE);
            pot.add(FishingReward.SUPER_CRATE);
        }
        Collections.shuffle(pot);
        FishingReward reward = pot.get(Core.generateNumber(0, pot.size() - 1));
        int amount;
        switch (reward) {
            case IRON_INGOT:
                amount = Core.generateNumber(1, 6);
                player.getInventory().addItem(CustomItemManager.getIngot(false, amount));
                player.sendMessage(DefaultConfig.prefix + "§fYou caught: §6" + amount + " Iron Ingot");
                break;
            case GOLD_INGOT:
                amount = Core.generateNumber(1, 3);
                player.getInventory().addItem(CustomItemManager.getIngot(true, amount));
                player.sendMessage(DefaultConfig.prefix + "§fYou caught: §6" + amount + " Gold Ingot");
                break;
            case ESSENCE:
                amount = Core.generateNumber(1, 3);
                player.getInventory().addItem(CustomItemManager.getEssence(false, amount));
                player.sendMessage(DefaultConfig.prefix + "§fYou caught: §6" + amount + " Essence");
                break;
            case RARE_ESSENCE:
                amount = Core.generateNumber(1, 2);
                player.getInventory().addItem(CustomItemManager.getEssence(true, amount));
                player.sendMessage(DefaultConfig.prefix + "§fYou caught: §6" + amount + " Rare Essence");
                break;
            case MONEY_NOTE:
                player.getInventory().addItem(CustomItemManager.getRandomMoneyNote(2500, 5000));
                player.sendMessage(DefaultConfig.prefix + "§aYou caught: Money Note");
                break;
            case BASIC_CRATE:
                player.getInventory().addItem(CustomItemManager.getCrate(Crate.BASIC_CRATE));
                player.sendMessage(DefaultConfig.prefix + "§aYou caught: Basic Crate");
                break;
            case SUPER_CRATE:
                player.getInventory().addItem(CustomItemManager.getCrate(Crate.SUPER_CRATE));
                player.sendMessage(DefaultConfig.prefix + "§aYou caught: Super Crate");
                break;
        }
    }

    public static void giveRandomGuardianFishingReward(Player player) {
        List<FishingReward> pot = new ArrayList<>();
        for (int i = 0; i < 75; i++) {
            if (i < 75) {
                pot.add(FishingReward.IRON_INGOT);
                pot.add(FishingReward.GOLD_INGOT);
                pot.add(FishingReward.ESSENCE);
                pot.add(FishingReward.RARE_ESSENCE);
                pot.add(FishingReward.MONEY_NOTE);
            }
            if (i < 8) {
                pot.add(FishingReward.BASIC_CRATE);
            }
            if (i < 6) {
                pot.add(FishingReward.SUPER_CRATE);
            }
            if (i < 5) {
                pot.add(FishingReward.BLACKSMITHS_MAGIC_DUST);
            }
            if (i < 2) {
                pot.add(FishingReward.ULTRA_CRATE);
                pot.add(FishingReward.PET_CRATE);
                pot.add(FishingReward.ARMOR_DUST);
            }
            if (i < 1) {
                pot.add(FishingReward.GOD_SLAYER);
                pot.add(FishingReward.EXTREME_KNOCKBACK_STICK);
            }
        }
        if (Pet.isSummoned(player, Pet.GUARDIAN)) {
            pot.add(FishingReward.BASIC_CRATE);
            pot.add(FishingReward.SUPER_CRATE);
            pot.add(FishingReward.ULTRA_CRATE);
            pot.add(FishingReward.PET_CRATE);
        }
        Collections.shuffle(pot);
        FishingReward reward = pot.get(Core.generateNumber(0, pot.size() - 1));
        int amount;
        switch (reward) {
            case IRON_INGOT:
                amount = Core.generateNumber(4, 8);
                player.getInventory().addItem(CustomItemManager.getIngot(false, amount));
                player.sendMessage(DefaultConfig.prefix + "§fYou caught: §6" + amount + " Iron Ingot");
                break;
            case GOLD_INGOT:
                amount = Core.generateNumber(2, 6);
                player.getInventory().addItem(CustomItemManager.getIngot(true, amount));
                player.sendMessage(DefaultConfig.prefix + "§fYou caught: §6" + amount + " Gold Ingot");
                break;
            case ESSENCE:
                amount = Core.generateNumber(3, 6);
                player.getInventory().addItem(CustomItemManager.getEssence(false, amount));
                player.sendMessage(DefaultConfig.prefix + "§fYou caught: §6" + amount + " Essence");
                break;
            case RARE_ESSENCE:
                amount = Core.generateNumber(2, 4);
                player.getInventory().addItem(CustomItemManager.getEssence(true, amount));
                player.sendMessage(DefaultConfig.prefix + "§fYou caught: §6" + amount + " Rare Essence");
                break;
            case MONEY_NOTE:
                player.getInventory().addItem(CustomItemManager.getRandomMoneyNote(2500, 5000));
                player.sendMessage(DefaultConfig.prefix + "§aYou caught: Money Note");
                break;
            case BASIC_CRATE:
                player.getInventory().addItem(CustomItemManager.getCrate(Crate.BASIC_CRATE));
                player.sendMessage(DefaultConfig.prefix + "§aYou caught: Basic Crate");
                break;
            case SUPER_CRATE:
                player.getInventory().addItem(CustomItemManager.getCrate(Crate.SUPER_CRATE));
                player.sendMessage(DefaultConfig.prefix + "§aYou caught: Super Crate");
                break;
            case ULTRA_CRATE:
                player.getInventory().addItem(CustomItemManager.getCrate(Crate.ULTRA_CRATE));
                player.sendMessage(DefaultConfig.prefix + "§aYou caught: Ultra Crate");
                break;
            case EXTREME_KNOCKBACK_STICK:
                player.getInventory().addItem(CustomItemManager.getArtifact(MCCArtifact.EXTREME_KNOCKBACK_STICK));
                player.sendMessage(DefaultConfig.prefix + "§aYou caught: Extreme Knockback Stick");
                break;
            case GOD_SLAYER:
                player.getInventory().addItem(CustomItemManager.getArtifact(MCCArtifact.GOD_SLAYER));
                player.sendMessage(DefaultConfig.prefix + "§aYou caught: God Slayer");
                break;
            case ARMOR_DUST:
                player.getInventory().addItem(CustomItemManager.getMergerDust(false));
                player.sendMessage(DefaultConfig.prefix + "§aYou caught: Armor Dust");
                break;
            case BLACKSMITHS_MAGIC_DUST:
                player.getInventory().addItem(CustomItemManager.getBlacksmithsMagicDust());
                player.sendMessage(DefaultConfig.prefix + "§aYou caught: Blacksmith's Magic Dust");
                break;
            case BLACKSMITHS_LIFE_ORB:
                player.getInventory().addItem(CustomItemManager.getBlacksmithsLifeOrb());
                player.sendMessage(DefaultConfig.prefix + "§aYou caught: Blacksmith's Life Orb");
                break;
            case PET_CRATE:
                player.getInventory().addItem(CustomItemManager.getCrate(Crate.PET_CRATE));
                player.sendMessage(DefaultConfig.prefix + "§aYou caught: Pet Crate");
                break;
        }
    }

    public static void giveRandomSquidFishingReward(Player player) {
        List<FishingReward> pot = new ArrayList<>();
        for (int i = 0; i < 75; i++) {
            if (i < 75) {
                pot.add(FishingReward.IRON_INGOT);
                pot.add(FishingReward.GOLD_INGOT);
                pot.add(FishingReward.ESSENCE);
                pot.add(FishingReward.RARE_ESSENCE);
                pot.add(FishingReward.MONEY_NOTE);
            }
            if (i < 8) {
                pot.add(FishingReward.BASIC_CRATE);
            }
            if (i < 6) {
                pot.add(FishingReward.SUPER_CRATE);
            }
            if (i < 4) {
                pot.add(FishingReward.BLACKSMITHS_MAGIC_DUST);
            }
            if (i < 2) {
                pot.add(FishingReward.PET_CRATE);
            }
            if (i < 1) {
                pot.add(FishingReward.KNOCKBACK_STICK);
                pot.add(FishingReward.CRYSTAL_BEATER);
            }
        }
        if (Pet.isSummoned(player, Pet.GUARDIAN)) {
            pot.add(FishingReward.BASIC_CRATE);
            pot.add(FishingReward.SUPER_CRATE);
            pot.add(FishingReward.PET_CRATE);
        }
        Collections.shuffle(pot);
        FishingReward reward = pot.get(Core.generateNumber(0, pot.size() - 1));
        int amount;
        switch (reward) {
            case IRON_INGOT:
                amount = Core.generateNumber(3, 6);
                player.getInventory().addItem(CustomItemManager.getIngot(false, amount));
                player.sendMessage(DefaultConfig.prefix + "§fYou caught: §6" + amount + " Iron Ingot");
                break;
            case GOLD_INGOT:
                amount = Core.generateNumber(2, 6);
                player.getInventory().addItem(CustomItemManager.getIngot(true, amount));
                player.sendMessage(DefaultConfig.prefix + "§fYou caught: §6" + amount + " Gold Ingot");
                break;
            case ESSENCE:
                amount = Core.generateNumber(3, 6);
                player.getInventory().addItem(CustomItemManager.getEssence(false, amount));
                player.sendMessage(DefaultConfig.prefix + "§fYou caught: §6" + amount + " Essence");
                break;
            case RARE_ESSENCE:
                amount = Core.generateNumber(2, 4);
                player.getInventory().addItem(CustomItemManager.getEssence(true, amount));
                player.sendMessage(DefaultConfig.prefix + "§fYou caught: §6" + amount + " Rare Essence");
                break;
            case MONEY_NOTE:
                player.getInventory().addItem(CustomItemManager.getRandomMoneyNote(2500, 5000));
                player.sendMessage(DefaultConfig.prefix + "§aYou caught: Money Note");
                break;
            case BASIC_CRATE:
                player.getInventory().addItem(CustomItemManager.getCrate(Crate.BASIC_CRATE));
                player.sendMessage(DefaultConfig.prefix + "§aYou caught: Basic Crate");
                break;
            case SUPER_CRATE:
                player.getInventory().addItem(CustomItemManager.getCrate(Crate.SUPER_CRATE));
                player.sendMessage(DefaultConfig.prefix + "§aYou caught: Super Crate");
                break;
            case ULTRA_CRATE:
                player.getInventory().addItem(CustomItemManager.getCrate(Crate.ULTRA_CRATE));
                player.sendMessage(DefaultConfig.prefix + "§aYou caught: Ultra Crate");
                break;
            case KNOCKBACK_STICK:
                player.getInventory().addItem(CustomItemManager.getArtifact(MCCArtifact.KNOCKBACK_STICK));
                player.sendMessage(DefaultConfig.prefix + "§aYou caught: Knockback Stick");
                break;
            case CRYSTAL_BEATER:
                player.getInventory().addItem(CustomItemManager.getArtifact(MCCArtifact.CRYSTAL_BEATER));
                player.sendMessage(DefaultConfig.prefix + "§aYou caught: Crystal Beater");
                break;
            case BLACKSMITHS_MAGIC_DUST:
                player.getInventory().addItem(CustomItemManager.getBlacksmithsMagicDust());
                player.sendMessage(DefaultConfig.prefix + "§aYou caught: Blacksmith's Magic Dust");
                break;
            case BLACKSMITHS_LIFE_ORB:
                player.getInventory().addItem(CustomItemManager.getBlacksmithsLifeOrb());
                player.sendMessage(DefaultConfig.prefix + "§aYou caught: Blacksmith's Life Orb");
                break;
            case PET_CRATE:
                player.getInventory().addItem(CustomItemManager.getCrate(Crate.PET_CRATE));
                player.sendMessage(DefaultConfig.prefix + "§aYou caught: Pet Crate");
                break;
        }
    }

    public static void runFishTimers() {
        new BukkitRunnable() {
            public void run() {
                if (!fishingPlayers.isEmpty()) {
                    Iterator<String> it = fishingPlayers.keySet().iterator();
                    while (it.hasNext()) {
                        String playerName = it.next();
                        Player player = Bukkit.getPlayer(playerName);
                        if (fishingPlayers.get(playerName).state == FishingState.FISHING)
                            Core.sendActionBar(player, "§f§l>>> §a§lFISHING §f§l<<<");
                        if (fishingPlayers.get(playerName).catchTime == 0) {
                            fishingPlayers.get(playerName).state = FishingState.BITE;
                            Core.sendActionBar(player, "§f§l>>> §e§lBITE §f§l<<<");
                            player.playSound(player.getLocation(), Sound.ENTITY_FISHING_BOBBER_SPLASH, 1.0f, 1.0f);
                        } else if (fishingPlayers.get(playerName).catchTime == -2) {
                            fishingPlayers.get(playerName).state = FishingState.BITE_LOSS;
                            Core.sendActionBar(player, "§f§l>>> §c§lBITE LOSS §f§l<<<");
                            it.remove();
                        }
                        if (fishingPlayers.containsKey(playerName)) fishingPlayers.get(playerName).catchTime -= 1;
                    }
                }
            }
        }.runTaskTimer(Core.instance, 0L, 20L);
    }

    public static class FishingTimer {
        public FishingState state;
        public int catchTime;

        public FishingTimer(Player player) {
            state = FishingState.FISHING;
            if (Pet.isSummoned(player, Pet.OCELOT)) catchTime = Core.generateNumber(2, 14);
            else catchTime = Core.generateNumber(4, 18);
        }

    }

    private enum FishingState {
        FISHING, BITE, BITE_LOSS
    }

    private enum FishingReward {
        IRON_INGOT, GOLD_INGOT, ESSENCE, RARE_ESSENCE,
        MONEY_NOTE, BASIC_CRATE, KNOCKBACK_STICK, CRYSTAL_BEATER,
        SUPER_CRATE, ULTRA_CRATE, BLACKSMITHS_MAGIC_DUST, ARMOR_DUST,
        BLACKSMITHS_LIFE_ORB, PET_CRATE, EXTREME_KNOCKBACK_STICK, GOD_SLAYER
    }

}
