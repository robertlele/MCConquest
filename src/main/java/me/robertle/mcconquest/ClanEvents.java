package me.robertle.mcconquest;

import me.robertle.mcconquest.Managers.CustomItemManager;
import org.bukkit.*;
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
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class ClanEvents implements Listener, CommandExecutor {

    public static HashMap<Integer, Event> eventTimes = new HashMap<>();
    public static Event currentEvent;
    public static boolean eventActive;
    public static boolean eventToggle = true;
    public static String winner = "";
    public static int skip = 0;

    //KOTH
    public static int koth;

    //Beat Down
    public static EnderCrystal crystal = null;
    public static HashMap<String, Integer> damageDone = new HashMap<>();
    public static int health = 30000;
    public static BossBar bar = Bukkit.createBossBar("§d§lCrystal §f> §c30000❤", BarColor.RED, BarStyle.SOLID);

    //Treasure Hunt
    public static List<Shulker> shulkers = new ArrayList<>();
    public static HashMap<String, Integer> treasureFound = new HashMap<>();

    //Pot of Gold
    public static Location pot;

    public static void runEventTimers() {
        new BukkitRunnable() {
            public void run() {
                if (!eventToggle) return;
                Iterator<Integer> it = eventTimes.keySet().iterator();
                HashMap<Integer, Event> tempTime = new HashMap<>();
                while (it.hasNext()) {
                    int eventTime = it.next();
                    if (DateUtil.getHour() == eventTime && currentEvent != eventTimes.get(eventTime)) {
                        stopCurretEvent();
                        currentEvent = eventTimes.get(DateUtil.getHour());
                        startEvent(eventTimes.get(DateUtil.getHour()));
                        int time = eventTime;
                        Event event = eventTimes.get(time);
                        it.remove();
                        if (time - 1 < 0) time = (time - 1) + 24;
                        else time = time - 1;
                        tempTime.put(time, event);
                    }
                }
                eventTimes.putAll(tempTime);
            }
        }.runTaskTimer(Core.instance, 100L, 1200L);
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
                        winner = "Choosing Winner...";
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
                                    Core.spawnFirework(winner);
                                    if (!Clan.getPlayerClan(winner).equalsIgnoreCase("None")) {
                                        Clan.getClan(Clan.getPlayerClan(winner)).eventWins++;
                                    }
                                    if (Core.chance(1))
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

            koth = Core.generateNumber(1, 2);

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
                                    kothTimes.put("Player: " + holder.getName(), kothTimes.get("Player: " + holder.getName()) + 1);
                                } else {
                                    kothTimes.put("Player: " + holder.getName(), 1);
                                }
                                winner = "§6" + (holder.getName().length() > 10 ? holder.getName().substring(0, 10) : holder.getName()) + " §f> " + DateUtil.getTimerFormat(kothTimes.get("Player: " + holder.getName()));
                            } else {
                                Clan clan = Clan.getClan(Clan.getPlayerClan(holder));
                                if (kothTimes.containsKey(clan.clanName)) {
                                    kothTimes.put(clan.clanName, kothTimes.get(clan.clanName) + 1);
                                } else {
                                    kothTimes.put(clan.clanName, 1);
                                }
                                winner = "§6" + (clan.clanName.length() > 10 ? clan.clanName.substring(0, 10) : clan.clanName) + " §f> " + DateUtil.getTimerFormat(kothTimes.get(clan.clanName));
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
                                        Core.spawnFirework(player);
                                        if (Core.chance(1))
                                            player.getInventory().addItem(CustomItemManager.getTag(Tag.EZ));
                                    }
                                } else {
                                    if (holder.isOnline()) {
                                        holder.getInventory().addItem(CustomItemManager.getCrate(Crate.ULTRA_CRATE));
                                        Core.spawnFirework(holder);
                                        if (Core.chance(1))
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
                        crystal = (EnderCrystal) Bukkit.getWorld("world").spawnEntity(DefaultConfig.locations.get("beatdown"), EntityType.ENDER_CRYSTAL);
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
            Core.shoutCenter("§eThe clan with the most found after 30 minutes wins.");
            Core.shoutCenter("§m───────────────────────");
            winner = "Starting soon...";

            new BukkitRunnable() {
                public void run() {
                    if (eventActive && currentEvent == Event.TREASURE_HUNT) {
                        Core.shoutCenter("§f§m────────§r §6Clan Event §f§m────────");
                        Core.shoutCenter("§eThe Treasure Hunt event has started.");
                        Core.shoutCenter("§e" + (Bukkit.getOnlinePlayers().size() * 2) + " Shulkers have spawned around the map.");
                        Core.shoutCenter("§m───────────────────────");
                        winner = "Started";

                        for (int i = 0; i < Bukkit.getOnlinePlayers().size() * 2; i++) {
                            //Set range
                            Location loc = Core.getRandomLocationAtHighestBlock(DefaultConfig.locations.get("spawn"), 50, 750, 50, 750);
                            loc.getChunk().setForceLoaded(true);
                            loc.getChunk().load();
                            Shulker shulker = (Shulker) Bukkit.getWorld("world").spawnEntity(loc, EntityType.SHULKER);
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
                    if (eventActive && currentEvent == Event.TREASURE_HUNT) {
                        for (Shulker shulker : shulkers) {
                            Core.spawnFirework(shulker.getLocation());
                        }
                    } else {
                        this.cancel();
                        return;
                    }
                }
            }.runTaskTimer(Core.instance, 6000L - skip, 200L);

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
                            if (p.isOnline()) {
                                Core.spawnFirework(p);
                                p.getInventory().addItem(CustomItemManager.getCrate(Crate.SUPER_CRATE));
                            }
                        } else if (winning.equalsIgnoreCase("None")) {
                            winning = "No one";
                        } else {
                            Clan clan = Clan.getClan(winning);
                            for (Player p : clan.getOnlinePlayers()) {
                                if (p.isOnline()) {
                                    Core.spawnFirework(p);
                                    p.getInventory().addItem(CustomItemManager.getCrate(Crate.SUPER_CRATE));
                                }
                            }
                            clan.eventWins += 1;
                        }

                        Core.shoutCenter("§f§m────────§r §6Clan Event §f§m────────");
                        Core.shoutCenter("§eThe Treasure Hunt event has ended.");
                        Core.shoutCenter("§eThe winner of the hunt is: §6" + winning);
                        Core.shoutCenter("§m───────────────────────");
                        stopCurretEvent();
                    } else {
                        stopCurretEvent();
                        this.cancel();
                        return;
                    }
                }
            }.runTaskLater(Core.instance, 36000L);
        } else if (event == Event.POT_OF_GOLD) {
            eventActive = true;
            currentEvent = Event.POT_OF_GOLD;
            Core.shoutCenter("§f§m────────§r §6Clan Event §f§m────────");
            Core.shoutCenter("§eThe Pot of Gold event is starting soon.");
            Core.shoutCenter("§eAn emerald block will randomly spawn in the world.");
            Core.shoutCenter("§eThe first person to mine it wins.");
            Core.shoutCenter("§m───────────────────────");
            winner = "Starting soon...";

            new BukkitRunnable() {
                public void run() {
                    if (eventActive && currentEvent == Event.POT_OF_GOLD) {
                        pot = Core.getRandomLocationAtHighestBlock(DefaultConfig.locations.get("spawn"), 50, 750, 50, 750);
                        Core.shoutCenter("§f§m────────§r §6Clan Event §f§m────────");
                        Core.shoutCenter("§eThe Pot of Gold event has started.");
                        Core.shoutCenter("§eLocation: " + Core.getStringFromLocation(pot));
                        Core.shoutCenter("§m───────────────────────");
                        winner = "§6" + Core.getStringFromLocation(pot);
                        pot.add(0, 1, 0).getBlock().setType(Material.EMERALD_BLOCK);
                    } else {
                        this.cancel();
                        return;
                    }
                }
            }.runTaskLater(Core.instance, 6000L - skip);
        }
    }

    public static void stopCurretEvent() {
        eventActive = false;
        winner = "";
        damageDone.clear();
        health = 30000;
        bar.removeAll();
        if (!shulkers.isEmpty()) {
            for (Shulker shulker : shulkers) {
                shulker.getLocation().getChunk().load();
                new BukkitRunnable() {
                    public void run() {
                        shulker.remove();
                    }
                }.runTaskLater(Core.instance, 1);
            }
        }
        if (crystal != null) {
            crystal.getLocation().getChunk().load();
            new BukkitRunnable() {
                public void run() {
                    crystal.remove();
                }
            }.runTaskLater(Core.instance, 1);
        }
        if (pot != null) pot.getBlock().setType(Material.AIR);
        shulkers.clear();
        treasureFound.clear();
    }

    @EventHandler
    public void onEmeraldBreak(BlockBreakEvent e) {
        if (currentEvent == Event.POT_OF_GOLD) {
            if (e.getBlock().getType() == Material.EMERALD_BLOCK) {
                if (Clan.getPlayerClan(e.getPlayer()).equalsIgnoreCase("None")) {
                    Core.shoutCenter("§f§m────────§r §6Clan Event §f§m────────");
                    Core.shoutCenter("§eThe Pot of Gold event has ended.");
                    Core.shoutCenter("§eThe winner of the Pot is: §6" + e.getPlayer().getName());
                    Core.shoutCenter("§m───────────────────────");
                    e.getPlayer().getInventory().addItem(CustomItemManager.getCrate(Crate.ULTRA_CRATE));
                    e.getPlayer().getInventory().addItem(CustomItemManager.getRandomMoneyNote(100000, 300000));
                    Core.spawnFirework(e.getPlayer());
                    if (Core.chance(1))
                        e.getPlayer().getInventory().addItem(CustomItemManager.getTag(Tag.EZ));
                } else {
                    Clan clan = Clan.getClan(Clan.getPlayerClan(e.getPlayer()));
                    Core.shoutCenter("§f§m────────§r §6Clan Event §f§m────────");
                    Core.shoutCenter("§eThe Pot of Gold event has ended.");
                    Core.shoutCenter("§eThe winner of the Pot is: §6" + clan.clanName);
                    Core.shoutCenter("§m───────────────────────");
                    e.getPlayer().getInventory().addItem(CustomItemManager.getCrate(Crate.ULTRA_CRATE));
                    e.getPlayer().getInventory().addItem(CustomItemManager.getRandomMoneyNote(250000, 500000));
                    Core.spawnFirework(e.getPlayer());
                    if (Core.chance(1))
                        e.getPlayer().getInventory().addItem(CustomItemManager.getTag(Tag.EZ));
                    for (Player player : clan.getOnlinePlayers()) {
                        if (player != e.getPlayer() && player.isOnline())
                            player.getInventory().addItem(CustomItemManager.getCrate(Crate.SUPER_CRATE));
                    }
                    clan.eventWins += 1;
                }
                stopCurretEvent();
            }
        }
    }

    @EventHandler
    public void onShulkerKill(EntityDeathEvent e) {
        if (e.getEntity() instanceof Shulker) {
            e.setDroppedExp(0);
            e.getDrops().clear();
        }

        if (currentEvent == Event.TREASURE_HUNT) {
            if (e.getEntity() instanceof Shulker) {
                if (!shulkers.contains(e.getEntity())) return;
                if (e.getEntity().getKiller() instanceof Player) {
                    Player player = e.getEntity().getKiller();
                    ItemStack loot = getTreasureLoot();
                    player.getInventory().addItem(loot);
                    player.sendMessage(DefaultConfig.prefix + "§aYou found: §f" + loot.getAmount() + " " + ItemHelper.getName(loot));
                    shulkers.remove(e.getEntity());

                    if (Clan.getPlayerClan(player).equalsIgnoreCase("None")) {
                        if (treasureFound.containsKey("Player: " + player.getName())) {
                            treasureFound.put("Player: " + player.getName(), treasureFound.get("Player: " + player.getName()) + 1);
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
                    winning = winning.replaceAll("Player: ", "");
                    winner = "§6" + (winning.length() > 10 ? winning.substring(0, 10) : winning) + " §f> " + highest + " §f§lFound";

                }
            }
        }
    }

    public ItemStack getTreasureLoot() {
        List<ItemStack> bag = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            if (i < 10) {
                bag.add(CustomItemManager.getCrate(Crate.BASIC_CRATE));
            }
            if (i < 5) {
                bag.add(CustomItemManager.getRandomMoneyNote(25000, 50000));
            }
            if (i < 2) {
                bag.add(CustomItemManager.getCrate(Crate.SUPER_CRATE));
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
                        damageDone.put("Player: " + player.getName(), damageDone.get("Player: " + player.getName()) + damage);
                    } else {
                        damageDone.put("Player: " + player.getName(), damage);
                    }
                    health -= (health >= damage) ? damage : ((health >= 5) ? 5 : 0);
                } else {
                    Clan clan = Clan.getClan(Clan.getPlayerClan(player));
                    if (damageDone.containsKey(clan.clanName)) {
                        damageDone.put(clan.clanName, damageDone.get(clan.clanName) + damage);
                    } else {
                        damageDone.put(clan.clanName, damage);
                    }
                    health -= (health >= damage) ? damage : ((health >= 5) ? 5 : 0);
                }

                if (!bar.getPlayers().contains(player))
                    bar.addPlayer(player);
                bar.setTitle("§d§lCrystal §f> §c" + health + "❤");
                bar.setProgress(((double) health / 30000.00));

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
                crystal.setCustomName("§d§lCrystal");
                winning = winning.replaceAll("Player: ", "");
                winner = "§6" + (winning.length() > 10 ? winning.substring(0, 10) : winning) + " §f> §c" + highest + "❤";

                if (health <= 0) {
                    if (winning.contains("Player: ")) {
                        Player p = Bukkit.getPlayer(winning.replace("Player: ", ""));
                        if (p.isOnline()) p.getInventory().addItem(CustomItemManager.getCrate(Crate.SUPER_CRATE));
                    } else {
                        Clan clan = Clan.getClan(winning);
                        for (Player p : clan.getOnlinePlayers()) {
                            if (p.isOnline()) {
                                p.getInventory().addItem(CustomItemManager.getCrate(Crate.SUPER_CRATE));
                            }
                        }
                        clan.eventWins += 1;
                    }
                    e.getEntity().getWorld().spawnParticle(Particle.EXPLOSION_LARGE, e.getEntity().getLocation(), 3);
                    player.getInventory().addItem(CustomItemManager.getCrate(Crate.ULTRA_CRATE));
                    Core.spawnFirework(player);
                    if (Core.chance(1)) player.getInventory().addItem(CustomItemManager.getTag(Tag.EZ));

                    Core.shoutCenter("§f§m────────§r §6Clan Event §f§m────────");
                    Core.shoutCenter("§eThe Beat Down event has ended.");
                    Core.shoutCenter("§eThe highest damage dealt was by: §6" + winning);
                    Core.shoutCenter("§eThe crystal killer was: §6" + player.getName());
                    Core.shoutCenter("§m───────────────────────");

                    e.getEntity().remove();
                    stopCurretEvent();
                } else {
                    e.setDamage(0);
                }
            }
        } else {
            if (e.getDamager() instanceof Player && e.getEntity() instanceof EnderCrystal) {
                e.getEntity().remove();
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
                        return timeCheck + i - 24;
                }
            } else {
                if (eventTimes.containsKey(timeCheck + i)) {
                    if (eventTimes.get(timeCheck + i) == event)
                        return timeCheck + i;
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
                    if (eventActive) {
                        StringUtil.sendCenteredMessage(player, "§f§m────────§r §6Clan Event §f§m────────");
                        StringUtil.sendCenteredMessage(player, "§eCurrent Event: §6" + Event.stringFromEvent(currentEvent));
                        StringUtil.sendCenteredMessage(player, "§a§lStatus");
                        StringUtil.sendCenteredMessage(player, winner);
                        StringUtil.sendCenteredMessage(player, "§e§lLocation");
                        if (currentEvent == Event.KOTH) {
                            if (koth == 1) {
                                StringUtil.sendCenteredMessage(player, Core.getStringFromLocation(DefaultConfig.locations.get("koth1")));
                            } else {
                                StringUtil.sendCenteredMessage(player, Core.getStringFromLocation(DefaultConfig.locations.get("koth2")));
                            }
                        } else if (currentEvent == Event.LUCKY_PIT) {
                            StringUtil.sendCenteredMessage(player, Core.getStringFromLocation(DefaultConfig.locations.get("luckypit")));
                        } else if (currentEvent == Event.BEAT_DOWN) {
                            StringUtil.sendCenteredMessage(player, Core.getStringFromLocation(DefaultConfig.locations.get("beatdown")));
                        } else if (currentEvent == Event.POT_OF_GOLD) {
                            StringUtil.sendCenteredMessage(player, Core.getStringFromLocation(pot));
                        } else if (currentEvent == Event.TREASURE_HUNT) {
                            StringUtil.sendCenteredMessage(player, "Random locations 50-750 blocks away from spawn.");
                        }
                        StringUtil.sendCenteredMessage(player, "§f§m───────────────────────");
                    } else {
                        StringUtil.sendCenteredMessage(player, "§f§m────────§r §6Clan Event §f§m────────");
                        StringUtil.sendCenteredMessage(player, "§eThe next upcoming event is: §6" + Event.stringFromEvent(getNextEvent()));
                        StringUtil.sendCenteredMessage(player, "§eThe event will begin in " + DateUtil.getTimeTil(getNextEventInHours(getNextEvent()), 0));
                        StringUtil.sendCenteredMessage(player, "§f§m───────────────────────");
                    }
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
                            Core.shout(DefaultConfig.prefix + "§c§lTHE CURRENT EVENT HAS BEEN STOPPED.");
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
                                    case "potofgold":
                                        startEvent(Event.POT_OF_GOLD);
                                        player.sendMessage(DefaultConfig.prefix + "Pot of Gold event successfully started.");
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
