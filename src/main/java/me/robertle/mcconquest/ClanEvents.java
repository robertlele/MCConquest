package me.robertle.mcconquest;

import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EnderCrystal;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Shulker;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class ClanEvents implements Listener, CommandExecutor {

    public static HashMap<Integer, Event> eventTimes = new HashMap<>();
    public static Event currentEvent;
    public static boolean eventActive;
    public static boolean eventToggle = true;
    public static String winner = "";
    public static int skip = 0;

    //Beat Down
    public static HashMap<String, Integer> damageDone = new HashMap<>();
    public static int health = 50000;
    public static BossBar bar = Bukkit.createBossBar("§d§lCrystal", BarColor.RED, BarStyle.SOLID);

    //Treasure Hunt
    public static List<Shulker> shulkers = new ArrayList<>();
    public static HashMap<String, Integer> treasureFound = new HashMap<>();

    public static void runEventTimers() {
        new BukkitRunnable() {
            public void run() {
                if (!eventToggle) return;
                for (int eventTime : eventTimes.keySet()) {
                    if (DateUtil.getHour() == eventTime && currentEvent != eventTimes.get(eventTime)) {
                        stopCurretEvent();
                        currentEvent = eventTimes.get(DateUtil.getHour());
                        startEvent(eventTimes.get(DateUtil.getHour()));
                        int time = eventTime;
                        Event event = eventTimes.get(time);
                        eventTimes.remove(time);
                        if (time - 1 < 0) time = (time - 1) + 24;
                        else time = time - 1;
                        eventTimes.put(time, event);
                    }
                }
            }
        }.runTaskTimer(Core.instance, 0L, 1200L);
    }

    public static void startEvent(Event event) {
        if (event == Event.LUCKY_PIT) {
            eventActive = true;
            currentEvent = Event.LUCKY_PIT;
            Core.shoutCenter("§f§m────────§r §6Clan Event §f§m────────");
            Core.shoutCenter("§eThe Lucky Pit event is starting soon.");
            Core.shoutCenter("§eLocation: §f" + Core.getStringFromLocation(DefaultConfig.locations.get("luckypit"))); //Put Coords here
            Core.shoutCenter("§m───────────────────────");
            winner = "Starting soon...";

            new BukkitRunnable() {
                public void run() {
                    if (eventActive && currentEvent == Event.LUCKY_PIT) {
                        Core.shoutCenter("§f§m────────§r §6Clan Event §f§m────────");
                        Core.shoutCenter("§eThe Lucky Pit event has started.");
                        Core.shoutCenter("§eA random winner will be selected in §f5 minutes§e.");
                        Core.shoutCenter("§m───────────────────────");
                        winner = "Winner will be selected soon...";
                    } else {
                        this.cancel();
                        return;
                    }
                    new BukkitRunnable() {
                        public void run() {
                            if (eventActive && currentEvent == Event.LUCKY_PIT) {
                                List<Player> bag = new ArrayList<>();
                                for (Player player : Bukkit.getOnlinePlayers()) {
                                    if (WGRegionManager.inRegion(player, "luckypit")) bag.add(player);
                                }
                                Collections.shuffle(bag);
                                Player winner = null;
                                if (!bag.isEmpty())
                                    winner = bag.get(Core.generateNumber(0, bag.size() - 1));

                                if (winner != null) {
                                    Core.shoutCenter("§f§m────────§r §6Clan Event §f§m────────");
                                    Core.shoutCenter("§eThe Lucky Pit event has ended.");
                                    Core.shoutCenter("§eThe winner of the pit is: §6" + winner.getName());
                                    Core.shoutCenter("§m───────────────────────");

                                    winner.getInventory().addItem(CustomItemManager.getCrate(Crate.ULTRA_CRATE));
                                    if (Core.chance(5))
                                        winner.getInventory().addItem(CustomItemManager.getTag(Tag.EZ));
                                } else {
                                    Core.shoutCenter("§f§m────────§r §6Clan Event §f§m────────");
                                    Core.shoutCenter("§eThe Lucky Pit event has ended.");
                                    Core.shoutCenter("§eNobody was in the pit to win.");
                                    Core.shoutCenter("§m───────────────────────");
                                }
                                stopCurretEvent();
                            } else {
                                this.cancel();
                                return;
                            }
                        }
                    }.runTaskLater(Core.instance, 6000L - skip);
                }
            }.runTaskLater(Core.instance, 6000L - skip);

        } else if (event == Event.KOTH) {
            eventActive = true;
            currentEvent = Event.KOTH;
            Core.shoutCenter("§f§m────────§r §6Clan Event §f§m────────");
            Core.shoutCenter("§eThe KOTH event is starting soon.");
            Core.shoutCenter("§eLocation: §f?, ?, ?");
            Core.shoutCenter("§m───────────────────────");
            winner = "Starting soon...";

            int koth = Core.generateNumber(1, 2);

            new BukkitRunnable() {
                public void run() {
                    if (eventActive && currentEvent == Event.KOTH) {
                        Core.shoutCenter("§f§m────────§r §6Clan Event §f§m────────");
                        Core.shoutCenter("§eThe KOTH event has started.");
                        Core.shoutCenter("§eThe first clan to hold the KOTH for 10 minutes wins.");
                        if (koth == 1)
                            Core.shoutCenter("§eLocation: §f" + Core.getStringFromLocation(DefaultConfig.locations.get("koth1")));
                        if (koth == 2)
                            Core.shoutCenter("§eLocation: §f" + Core.getStringFromLocation(DefaultConfig.locations.get("koth2")));
                        Core.shoutCenter("§m───────────────────────");
                        winner = "Started";
                    } else {
                        this.cancel();
                        return;
                    }
                }
            }.runTaskLater(Core.instance, 6000L - skip);

            new BukkitRunnable() {
                Player holder = null;
                HashMap<String, Integer> kothTimes = new HashMap<>();

                public void run() {
                    if (eventActive && currentEvent == Event.KOTH) {
                        if (holder != null && WGRegionManager.inRegion(holder, "koth" + koth)) {
                            if (Clan.getPlayerClan(holder).equalsIgnoreCase("None")) {
                                if (kothTimes.containsKey("Player: " + holder.getName())) {
                                    kothTimes.put("Player: " + holder.getName(), kothTimes.get(holder.getName()) + 1);
                                } else {
                                    kothTimes.put("Player: " + holder.getName(), 1);
                                }
                                winner = "§6" + "Player: " + holder.getName() + " §f> §f§lTime: §f" + DateUtil.getTimerFormat(kothTimes.get("Player: " + holder.getName()));
                            } else {
                                Clan clan = Clan.getClan(Clan.getPlayerClan(holder));
                                if (kothTimes.containsKey(clan.clanName)) {
                                    kothTimes.put(clan.clanName, kothTimes.get(clan.clanName) + 1);
                                } else {
                                    kothTimes.put(clan.clanName, 1);
                                }
                                winner = "§6" + clan.clanName + " §f> §f§lTime: §f" + DateUtil.getTimerFormat(kothTimes.get(clan.clanName));
                            }
                        } else {
                            for (Player player : Bukkit.getOnlinePlayers()) {
                                if (WGRegionManager.inRegion(player, "koth" + koth)) {
                                    holder = player;
                                    Core.shout("§6§lKOTH §r§f> §6" + holder.getName() + " §fis now capturing the KOTH.");
                                    break;
                                }
                            }
                        }

                        for (String clans : kothTimes.keySet()) {
                            if (kothTimes.get(clans) == 600) {
                                Core.shoutCenter("§f§m────────§r §6Clan Event §f§m────────");
                                Core.shoutCenter("§eThe KOTH event has ended.");
                                Core.shoutCenter("§eThe winner of the KOTH is: §6" + clans);
                                Core.shoutCenter("§m───────────────────────");
                                if (clans.contains("Player: ")) {
                                    Player player = Bukkit.getPlayer(clans.replace("Player: ", ""));
                                    if (player.isOnline()) {
                                        player.getInventory().addItem(CustomItemManager.getCrate(Crate.ULTRA_CRATE));
                                        if (Core.chance(5))
                                            player.getInventory().addItem(CustomItemManager.getTag(Tag.EZ));
                                    }
                                } else {
                                    if (holder.isOnline()) {
                                        holder.getInventory().addItem(CustomItemManager.getCrate(Crate.ULTRA_CRATE));
                                        if (Core.chance(5))
                                            holder.getInventory().addItem(CustomItemManager.getTag(Tag.EZ));
                                    }
                                    Clan clan = Clan.getClan(clans);
                                    for (Player player : clan.getOnlinePlayers()) {
                                        if (player != holder && player.isOnline())
                                            player.getInventory().addItem(CustomItemManager.getCrate(Crate.SUPER_CRATE));
                                    }
                                    clan.eventWins += 1;
                                }
                                stopCurretEvent();
                                this.cancel();
                                return;
                            }
                        }
                    } else {
                        this.cancel();
                        return;
                    }
                }
            }.runTaskTimer(Core.instance, 6000L - skip, 20L);

        } else if (event == Event.BEAT_DOWN) {
            eventActive = true;
            currentEvent = Event.BEAT_DOWN;
            Core.shoutCenter("§f§m────────§r §6Clan Event §f§m────────");
            Core.shoutCenter("§eThe Beat Down event is starting soon.");
            Core.shoutCenter("§eLocation: §f" + Core.getStringFromLocation(DefaultConfig.locations.get("beatdown")));
            Core.shoutCenter("§m───────────────────────");
            winner = "Starting soon...";

            new BukkitRunnable() {
                public void run() {
                    if (eventActive && currentEvent == Event.BEAT_DOWN) {
                        Core.shoutCenter("§f§m────────§r §6Clan Event §f§m────────");
                        Core.shoutCenter("§eThe Beat Down event has started.");
                        Core.shoutCenter("§eThe clan with the highest damage wins.");
                        Core.shoutCenter("§eThe killer will get a special reward.");
                        Core.shoutCenter("§m───────────────────────");
                        winner = "Started";
                        EnderCrystal crystal = (EnderCrystal) Bukkit.getWorld("world").spawnEntity(DefaultConfig.locations.get("beatdown"), EntityType.ENDER_CRYSTAL);
                        crystal.setShowingBottom(false);
                        crystal.setFireTicks(0);
                        crystal.setCustomName("§d§lCrystal");
                        crystal.setCustomNameVisible(true);
                    } else {
                        this.cancel();
                        return;
                    }
                }
            }.runTaskLater(Core.instance, 6000L - skip);
        } else if (event == Event.TREASURE_HUNT) {
            eventActive = true;
            currentEvent = Event.TREASURE_HUNT;
            Core.shoutCenter("§f§m────────§r §6Clan Event §f§m────────");
            Core.shoutCenter("§eThe Treasure Hunt event is starting soon.");
            Core.shoutCenter("§eHunt down shulkers for treasure.");
            Core.shoutCenter("§eThe clan with the most found after 15 minutes wins.");
            Core.shoutCenter("§m───────────────────────");
            winner = "Starting soon...";

            new BukkitRunnable() {
                public void run() {
                    if (eventActive && currentEvent == Event.TREASURE_HUNT) {
                        Core.shoutCenter("§f§m────────§r §6Clan Event §f§m────────");
                        Core.shoutCenter("§eThe Treasure Hunt event has started.");
                        Core.shoutCenter("§eThe shulkers have spawned around the pvp area.");
                        Core.shoutCenter("§m───────────────────────");
                        winner = "Started";

                        for (int i = 0; i < 25; i++) {
                            //Set range
                            Shulker shulker = (Shulker) Bukkit.getWorld("world").spawnEntity(Core.getRandomLocationAtHighestBlock(DefaultConfig.locations.get("spawn"), 100, 500, 100, 500), EntityType.SHULKER);
                            shulker.setColor(DyeColor.ORANGE);
                            shulker.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING, 1200, 0, false, false));
                            shulkers.add(shulker);
                        }
                    } else {
                        this.cancel();
                        return;
                    }
                }
            }.runTaskLater(Core.instance, 6000L - skip);

            new BukkitRunnable() {
                public void run() {
                    if (eventActive) {
                        String winning = "None";
                        int highest = 0;
                        if (!treasureFound.isEmpty()) {
                            for (String clans : treasureFound.keySet()) {
                                if (treasureFound.get(clans) > highest) {
                                    winning = clans;
                                    highest = treasureFound.get(clans);
                                }
                            }
                        }

                        if (winning.contains("Player: ")) {
                            Player p = Bukkit.getPlayer(winning.replace("Player: ", ""));
                            if (p.isOnline()) p.getInventory().addItem(CustomItemManager.getCrate(Crate.SUPER_CRATE));
                        } else if (winning.equalsIgnoreCase("None")) {
                            winning = "No one";
                        } else {
                            Clan clan = Clan.getClan(winning);
                            for (Player p : clan.getOnlinePlayers()) {
                                if (p.isOnline())
                                    p.getInventory().addItem(CustomItemManager.getCrate(Crate.SUPER_CRATE));
                            }
                            clan.eventWins += 1;
                        }

                        Core.shoutCenter("§f§m────────§r §6Clan Event §f§m────────");
                        Core.shoutCenter("§eThe Treasure Hunt event has ended.");
                        Core.shoutCenter("§eThe winner of the hunt is: §6" + winning);
                        Core.shoutCenter("§m───────────────────────");
                    } else {
                        this.cancel();
                        return;
                    }
                }
            }.runTaskLater(Core.instance, 18000L);
        }
    }

    public static void stopCurretEvent() {
        eventActive = false;
        winner = "";
        damageDone.clear();
        health = 50000;
        bar.removeAll();
        if (!shulkers.isEmpty()) {
            for (Shulker shulker : shulkers) {
                shulker.remove();
            }
        }
        shulkers.clear();
        treasureFound.clear();
    }

    @EventHandler
    public void onShulkerKill(EntityDeathEvent e) {
        if (currentEvent == Event.TREASURE_HUNT) {
            if (e.getEntity() instanceof Shulker) {
                e.setDroppedExp(0);
                e.getDrops().clear();
                if (e.getEntity().getKiller() instanceof Player) {
                    Player player = e.getEntity().getKiller();
                    ItemStack loot = getTreasureLoot();
                    player.getInventory().addItem(loot);
                    player.sendMessage(DefaultConfig.prefix + "§aYou found: &f" + loot.getAmount() + " " + ItemHelper.getName(loot));

                    if (Clan.getPlayerClan(player).equalsIgnoreCase("None")) {
                        if (treasureFound.containsKey("Player: " + player.getName())) {
                            treasureFound.put("Player: " + player.getName(), treasureFound.get(player.getName()) + 1);
                        } else {
                            treasureFound.put("Player: " + player.getName(), 1);
                        }
                    } else {
                        Clan clan = Clan.getClan(Clan.getPlayerClan(player));
                        if (treasureFound.containsKey(clan.clanName)) {
                            treasureFound.put(clan.clanName, treasureFound.get(clan.clanName) + 1);
                        } else {
                            treasureFound.put(clan.clanName, 1);
                        }
                    }

                    String winning = "None";
                    int highest = 0;
                    if (!treasureFound.isEmpty()) {
                        for (String clans : treasureFound.keySet()) {
                            if (treasureFound.get(clans) > highest) {
                                winning = clans;
                                highest = treasureFound.get(clans);
                            }
                        }
                    }
                    winner = "§6" + winning + " §f> §f" + highest + " §f§lFound";

                }
            }
        }
    }

    public ItemStack getTreasureLoot() {
        List<ItemStack> bag = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            if (i < 30) {
                bag.add(CustomItemManager.getIngot(false, 32));
                bag.add(CustomItemManager.getIngot(true, 32));
                bag.add(CustomItemManager.getEssence(false, 32));
                bag.add(CustomItemManager.getEssence(true, 32));
            }
            if (i < 10) {
                bag.add(CustomItemManager.getRandomMoneyNote(10000, 25000));
            }
            if (i < 5) {
                bag.add(CustomItemManager.getCrate(Crate.SUPER_CRATE));
            }
            if (i < 2) {
                bag.add(CustomItemManager.getCrate(Crate.ULTRA_CRATE));
            }
        }
        return bag.get(Core.generateNumber(0, bag.size() - 1));
    }

    @EventHandler
    public void onCrystalHit(EntityDamageByEntityEvent e) {
        if (currentEvent == Event.BEAT_DOWN) {
            if (e.getDamager() instanceof Player && e.getEntity() instanceof EnderCrystal) {
                Player player = (Player) e.getDamager();
                if (!eventActive) {
                    stopCurretEvent();
                    return;
                }
                int damage = 5;
                if (ItemHelper.hasName(player.getInventory().getItemInMainHand()))
                    if (ItemHelper.getName(player.getInventory().getItemInMainHand()).equalsIgnoreCase("§d§lCrystal Beater"))
                        damage = 10;
                if (Clan.getPlayerClan(player).equalsIgnoreCase("None")) {
                    if (damageDone.containsKey("Player: " + player.getName())) {
                        damageDone.put("Player: " + player.getName(), damageDone.get(player.getName()) + damage);
                    } else {
                        damageDone.put("Player: " + player.getName(), damage);
                    }
                    health -= damage;
                } else {
                    Clan clan = Clan.getClan(Clan.getPlayerClan(player));
                    if (damageDone.containsKey(clan.clanName)) {
                        damageDone.put(clan.clanName, damageDone.get(clan.clanName) + damage);
                    } else {
                        damageDone.put(clan.clanName, damage);
                    }
                    health -= damage;
                }

                if (!bar.getPlayers().contains(player))
                    bar.addPlayer(player);
                bar.setProgress(((double) health / 50000.00));

                String winning = "None";
                int highest = 0;
                if (!damageDone.isEmpty()) {
                    for (String clans : damageDone.keySet()) {
                        if (damageDone.get(clans) > highest) {
                            winning = clans;
                            highest = damageDone.get(clans);
                        }
                    }
                }
                winner = "§c" + health + "❤" + " §f> §6" + winning + " §f> §c" + highest + " §f§lDamage";

                if (health < 0) {
                    if (winning.contains("Player: ")) {
                        Player p = Bukkit.getPlayer(winning.replace("Player: ", ""));
                        if (p.isOnline()) p.getInventory().addItem(CustomItemManager.getCrate(Crate.SUPER_CRATE));
                    } else {
                        Clan clan = Clan.getClan(winning);
                        for (Player p : clan.getOnlinePlayers()) {
                            if (p.isOnline()) p.getInventory().addItem(CustomItemManager.getCrate(Crate.SUPER_CRATE));
                        }
                        clan.eventWins += 1;
                    }
                    player.getInventory().addItem(CustomItemManager.getCrate(Crate.ULTRA_CRATE));
                    if (Core.chance(5)) player.getInventory().addItem(CustomItemManager.getTag(Tag.EZ));

                    Core.shoutCenter("§f§m────────§r §6Clan Event §f§m────────");
                    Core.shoutCenter("§eThe Beat Down event has ended.");
                    Core.shoutCenter("§eThe highest damage dealt was by: §6" + winning);
                    Core.shoutCenter("§eThe crystal killer was: §6" + player.getName());
                    Core.shoutCenter("§m───────────────────────");
                    stopCurretEvent();
                } else {
                    e.setDamage(0);
                    e.setCancelled(true);
                }
            }
        }
    }

    public static Event getNextEvent() {
        int timeCheck = DateUtil.getHour();
        for (int i = 1; i < 24; i++) {
            if (timeCheck + i > 23) {
                if (eventTimes.containsKey(timeCheck + i - 24)) return eventTimes.get(timeCheck + i - 24);
            } else {
                if (eventTimes.containsKey(timeCheck + i)) return eventTimes.get(timeCheck + i);
            }
        }
        return null;
    }

    public static int getNextEventInHours(Event event) {
        int timeCheck = DateUtil.getHour();
        for (int i = 1; i < 24; i++) {
            if (timeCheck + i > 23) {
                if (eventTimes.containsKey(timeCheck + i - 24)) {
                    if (eventTimes.get(timeCheck + i - 24) == event)
                        return (timeCheck + i) - DateUtil.getHour();
                }
            } else {
                if (eventTimes.containsKey(timeCheck + i)) {
                    if (eventTimes.get(timeCheck + i) == event)
                        return (timeCheck + i) - DateUtil.getHour();
                }
            }
        }
        return -1;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            if (command.getLabel().equalsIgnoreCase("event")) {
                if (args.length == 0) {
                    StringUtil.sendCenteredMessage(player, "§f§m────────§r §6Clan Event §f§m────────");
                    StringUtil.sendCenteredMessage(player, "§eThe next upcoming event is: §6" + Event.stringFromEvent(getNextEvent()));
                    if (getNextEventInHours(getNextEvent()) == 1) {
                        StringUtil.sendCenteredMessage(player, "§eThe event will begin in under a hour");
                    } else {
                        StringUtil.sendCenteredMessage(player, "§eThe event will begin in about " + getNextEventInHours(getNextEvent()) + " hours.");
                    }
                    StringUtil.sendCenteredMessage(player, "§f§m───────────────────────");
                } else if (args.length >= 1) {
                    if (args[0].equalsIgnoreCase("toggle")) {
                        if (player.hasPermission("mcc.admin")) {
                            if (eventToggle) {
                                eventToggle = false;
                                player.sendMessage(DefaultConfig.prefix + "§aThe events timer has been turned off");
                            } else {
                                eventToggle = true;
                                player.sendMessage(DefaultConfig.prefix + "§aThe events timer has been turned on");
                            }
                            return true;
                        }
                    } else if (args[0].equalsIgnoreCase("stop")) {
                        if (player.hasPermission("mcc.admin")) {
                            stopCurretEvent();
                            player.sendMessage(DefaultConfig.prefix + "§aSuccessfully stopped event.");
                            Core.shout(DefaultConfig.prefix + "§c§lTHE CURRENT EVEN HAS BEEN STOPPED.");
                            return true;
                        }
                    } else if (args[0].equalsIgnoreCase("skip")) {
                        if (player.hasPermission("mcc.admin")) {
                            if (skip == 6000) {
                                skip = 0;
                                player.sendMessage(DefaultConfig.prefix + "§aSuccessfully untoggled skip.");
                            } else {
                                skip = 6000;
                                player.sendMessage(DefaultConfig.prefix + "§aSuccessfully toggled skip.");
                            }
                            return true;
                        }
                    } else if (args[0].equalsIgnoreCase("force")) {
                        if (args.length == 2) {
                            if (player.hasPermission("mcc.admin")) {
                                switch (args[1]) {
                                    case "beatdown":
                                        startEvent(Event.BEAT_DOWN);
                                        player.sendMessage(DefaultConfig.prefix + "Beat Down event successfully started.");
                                        return true;
                                    case "koth":
                                        startEvent(Event.KOTH);
                                        player.sendMessage(DefaultConfig.prefix + "KOTH event successfully started.");
                                        return true;
                                    case "treasurehunt":
                                        startEvent(Event.TREASURE_HUNT);
                                        player.sendMessage(DefaultConfig.prefix + "Treasure Hunt event successfully started.");
                                        return true;
                                    case "luckypit":
                                        startEvent(Event.LUCKY_PIT);
                                        player.sendMessage(DefaultConfig.prefix + "Lucky Pit event successfully started.");
                                        return true;
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
}
