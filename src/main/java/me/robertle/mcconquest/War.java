package me.robertle.mcconquest;

import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class War implements Listener, CommandExecutor {

    public static List<String> warClans = new ArrayList<>();
    public static boolean warOpen = false;

    public static int daysTilWar = 0;

    public static Clan clan1;
    public static Clan clan2;

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        String prefix = DefaultConfig.prefix;
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            if (command.getLabel().equalsIgnoreCase("war")) {
                if (args.length >= 1) {
                    if (args[0].equalsIgnoreCase("apply")) {
                        if (warOpen) {
                            if (!Clan.getPlayerClan(player).equalsIgnoreCase("None")) {
                                Clan clan = Clan.getClan(Clan.getPlayerClan(player));
                                if (clan.clanMembers.get(player.getUniqueId()) == ClanRole.LEADER) {
                                    if (!warClans.contains(clan.clanName)) {
                                        if (clan.clanPerk == 10 && clan.clanMembers.size() >= 6) {
                                            warClans.add(clan.clanName);
                                            player.sendMessage(prefix + "§aYour clan has successfully joined the clan war.");
                                        } else {
                                            player.sendMessage(prefix + "§cYour clan doesn't meet requirements to join.");
                                        }
                                    } else {
                                        player.sendMessage(prefix + "§cYour clan is already in the war.");
                                    }
                                } else {
                                    player.sendMessage(prefix + "§cOnly leaders can join clan wars.");
                                }
                            } else {
                                player.sendMessage(prefix + "§cYou aren't in a clan.");
                            }
                        } else {
                            player.sendMessage(prefix + "§cClan wars are not open to join.");
                        }
                        return true;
                    } else if (args[0].equalsIgnoreCase("list")) {
                        StringUtil.sendCenteredMessage(player, "§f§m────────§r §aParticipating Clans §f§m────────");
                        for (int i = 0; i < warClans.size(); i++) {
                            player.sendMessage("§f" + (i + 1) + ". §6" + warClans.get(i));
                        }
                        StringUtil.sendCenteredMessage(player, "§f§m────────────────────────");
                        return true;
                    } else if (args[0].equalsIgnoreCase("help")) {
                        if (!player.hasPermission("mcc.admin")) {
                            return false;
                        }
                        StringUtil.sendCenteredMessage(player, "§f§m───────────§r  §aClan Wars §f§m───────────");
                        player.sendMessage("§f/war toggle");
                        player.sendMessage("§f/war remove <clan>");
                        player.sendMessage("§f/war time <days>");
                        player.sendMessage("§f/war clan1 <clan>");
                        player.sendMessage("§f/war clan2 <clan>");
                        player.sendMessage("§f/war tpall");
                        player.sendMessage("§f/war start");
                        player.sendMessage("§f/war stop");
                        StringUtil.sendCenteredMessage(player, "§f§m───────────────────────────────");
                    } else if (args[0].equalsIgnoreCase("status")) {
                        if (daysTilWar > 0) {
                            StringUtil.sendCenteredMessage(player, "§f§m───────────§r  §aClan Wars §f§m───────────");
                            StringUtil.sendCenteredMessage(player, "§fThe Clan Wars will be open to join in §6" + daysTilWar + " §fdays.");
                            StringUtil.sendCenteredMessage(player, "§f§m───────────────────────────────");
                        } else {
                            if (warOpen) {
                                StringUtil.sendCenteredMessage(player, "§f§m───────────§r  §aClan Wars §f§m───────────");
                                StringUtil.sendCenteredMessage(player, "§fThe Clan Wars is §aopen §fto join.");
                                StringUtil.sendCenteredMessage(player, "§fClan leaders can do §6/war apply §fto join.");
                                StringUtil.sendCenteredMessage(player, "§fClans must have perk level §610 §fand atleast §66 §fmembers to join.");
                                StringUtil.sendCenteredMessage(player, "§f§m───────────────────────────────");
                            } else {
                                StringUtil.sendCenteredMessage(player, "§f§m───────────§r  §aClan Wars §f§m───────────");
                                StringUtil.sendCenteredMessage(player, "§fThe Clan Wars is §cclosed§f.");
                                StringUtil.sendCenteredMessage(player, "§fThe Clan War will be in progress for the next few days.");
                                StringUtil.sendCenteredMessage(player, "§f§m───────────────────────────────");
                            }
                        }
                    } else if (args[0].equalsIgnoreCase("toggle")) {
                        if (!player.hasPermission("mcc.admin")) {
                            return false;
                        }
                        if (warOpen) {
                            warOpen = false;
                            player.sendMessage(prefix + "§cClan wars is now closed.");
                        } else {
                            warOpen = true;
                            player.sendMessage(prefix + "§aClan wars is now open.");
                        }
                        return true;
                    } else if (args[0].equalsIgnoreCase("remove")) {
                        if (!player.hasPermission("mcc.admin")) {
                            return false;
                        }
                        if (warClans.contains(args[1])) {
                            warClans.remove(args[1]);
                            player.sendMessage(prefix + "§aClan has successfully been removed.");
                        }
                        return true;
                    } else if (args[0].equalsIgnoreCase("time")) {
                        if (!player.hasPermission("mcc.admin")) {
                            return false;
                        }
                        daysTilWar = Integer.parseInt(args[1]);
                        player.sendMessage(prefix + "Days til war has been set to " + args[1]);
                        return true;
                    } else if (args[0].equalsIgnoreCase("clan1")) {
                        if (!player.hasPermission("mcc.admin")) {
                            return false;
                        }
                        if (Clan.clanExist(args[1]) && warClans.contains(Clan.getClan(args[1]).clanName)) {
                            clan1 = Clan.getClan(args[1]);
                            player.sendMessage(prefix + "§aClan 1 set to §6" + clan1.clanName);
                        }
                        return true;
                    } else if (args[0].equalsIgnoreCase("clan2")) {
                        if (!player.hasPermission("mcc.admin")) {
                            return false;
                        }
                        if (Clan.clanExist(args[1]) && warClans.contains(Clan.getClan(args[1]).clanName)) {
                            clan2 = Clan.getClan(args[1]);
                            player.sendMessage(prefix + "§aClan 2 set to §6" + clan2.clanName);
                        }
                        return true;
                    } else if (args[0].equalsIgnoreCase("tpall")) {
                        if (!player.hasPermission("mcc.admin")) {
                            return false;
                        }
                        if (clan1 != null && clan2 != null) {
                            player.sendMessage(prefix + "§aTeleport successful.");
                            for (Player p : clan1.getOnlinePlayers()) {
                                p.teleport(player.getLocation());
                            }
                            for (Player p : clan2.getOnlinePlayers()) {
                                p.teleport(player.getLocation());
                            }
                        } else {
                            player.sendMessage(prefix + "§cTeleport failed.");
                        }
                        return true;
                    } else if (args[0].equalsIgnoreCase("start")) {
                        if (!player.hasPermission("mcc.admin")) {
                            return false;
                        }
                        if (!warActive) {
                            if (clan1 == null || clan2 == null) {
                                player.sendMessage(prefix + "§cYou need to set a clan1 and clan2.");
                                return false;
                            }
                            startWar();
                            player.sendMessage(prefix + "§aClan war started.");
                        } else {
                            player.sendMessage(prefix + "§cThere's a clan war already in progress.");
                        }
                        return true;
                    } else if (args[0].equalsIgnoreCase("stop")) {
                        if (!player.hasPermission("mcc.admin")) {
                            return false;
                        }
                        if (warActive) {
                            stopWar();
                            player.sendMessage(prefix + "§aClan war stopped.");
                        } else {
                            player.sendMessage(prefix + "§cThere's no clan war in progress.");
                        }
                        return true;
                    }
                } else {
                    StringUtil.sendCenteredMessage(player, "§f§m───────────§r  §aClan Wars §f§m───────────");
                    player.sendMessage("§f/war apply §eApply to join the clan war.");
                    player.sendMessage("§f/war status §eView the current status of the clan war.");
                    player.sendMessage("§f/war list §eView clans currently in the clan war.");
                    StringUtil.sendCenteredMessage(player, "§f§m───────────────────────────────");
                    return false;
                }
            }
        }
        return false;
    }

    //War KOTH
    public static boolean warActive = false;
    public static BossBar bossBar;

    public static List<Player> clan1Players = new ArrayList<>();
    public static List<Player> clan2Players = new ArrayList<>();

    public static void startWar() {
        warActive = true;
        Core.shoutCenter("§f§m────────§r §6Clan War §f────────");
        Core.shoutCenter("§eA clan war match is starting between:");
        Core.shoutCenter("§6" + clan1.clanName + " §f§lVS §6" + clan2.clanName);
        Core.shoutCenter("§m───────────────────────────────");

        for (Player player : clan1.getOnlinePlayers()) {
            player.teleport(DefaultConfig.locations.get("warteam1"));
            clan1Players.add(player);
        }

        for (Player player : clan2.getOnlinePlayers()) {
            player.teleport(DefaultConfig.locations.get("warteam2"));
            clan2Players.add(player);
        }

        new BukkitRunnable() {
            HashMap<String, Integer> kothTimes = new HashMap<>();
            Player holder = null;

            public void run() {
                if (warActive) {
                    if (holder != null && WGRegionManager.inRegion(holder, "warkoth")) {
                        Clan clan = Clan.getClan(Clan.getPlayerClan(holder));
                        if (kothTimes.containsKey(clan.clanName)) {
                            kothTimes.put(clan.clanName, kothTimes.get(clan.clanName) + 1);
                        } else {
                            kothTimes.put(clan.clanName, 1);
                        }
                        if (bossBar != null) bossBar.removeAll();
                        bossBar = Bukkit.createBossBar("§c§lClan War §f> §6" + clan.clanName + " §f> " + DateUtil.getTimerFormat(kothTimes.get(clan.clanName)), BarColor.RED, BarStyle.SOLID);
                        for (Player p : Bukkit.getOnlinePlayers()) bossBar.addPlayer(p);
                    } else {
                        for (Player player : Bukkit.getOnlinePlayers()) {
                            if (WGRegionManager.inRegion(player, "warkoth")) {
                                holder = player;
                                Core.shout("§c§lClan War §f> §6" + holder.getName() + " §fis now capturing the flag.");
                                break;
                            }
                        }
                    }

                    for (String clans : kothTimes.keySet()) {
                        if (kothTimes.get(clans) == 300) {
                            Core.shoutCenter("§f§m────────§r §6Clan War §f────────");
                            Core.shoutCenter("§eThe clan war match has ended.");
                            Core.shoutCenter("§eThe winner of the match is: §6" + clans);
                            Core.shoutCenter("§m───────────────────────────────");
                        }
                        stopWar();
                        this.cancel();
                        return;
                    }
                } else {
                    this.cancel();
                    return;
                }
            }
        }.runTaskTimer(Core.instance, 0L, 20L);
    }

    public static void stopWar() {
        if (warActive) {
            warActive = false;
            if (bossBar != null) bossBar.removeAll();
            if (!clan1Players.isEmpty())
                for (Player p : clan1Players) p.teleport(DefaultConfig.locations.get("spawn"));
            if (!clan2Players.isEmpty())
                for (Player p : clan2Players) p.teleport(DefaultConfig.locations.get("spawn"));
            clan1 = null;
            clan2 = null;
            clan1Players.clear();
            clan2Players.clear();
        }
    }

    @EventHandler
    public void onWarDeath(PlayerDeathEvent e) {
        if (!warActive) return;
        if (clan1Players.contains(e.getEntity())) {
            clan1Players.remove(e.getEntity());
        }
        if (clan2Players.contains(e.getEntity())) {
            clan2Players.remove(e.getEntity());
        }
        if (clan1Players.isEmpty()) {
            Core.shoutCenter("§f§m───────────§r §6Clan War §f§m───────────");
            Core.shoutCenter("§eThe clan war has ended.");
            Core.shoutCenter("§eThe winner of the match is: §6" + clan2.clanName);
            Core.shoutCenter("§m───────────────────────────────");
            stopWar();
        } else if (clan2Players.isEmpty()) {
            Core.shoutCenter("§f§m───────────§r §6Clan War §f§m───────────");
            Core.shoutCenter("§eThe clan war has ended.");
            Core.shoutCenter("§eThe winner of the match is: §6" + clan1.clanName);
            Core.shoutCenter("§m───────────────────────────────");
            stopWar();
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        if (!warActive) return;
        if (clan1Players.contains(e.getPlayer())) {
            clan1Players.remove(e.getPlayer());
        }
        if (clan2Players.contains(e.getPlayer())) {
            clan2Players.remove(e.getPlayer());
        }
        if (clan1Players.isEmpty()) {
            Core.shoutCenter("§f§m───────────§r §6Clan War §f§m───────────");
            Core.shoutCenter("§eThe clan war has ended.");
            Core.shoutCenter("§eThe winner of the match is: §6" + clan2.clanName);
            Core.shoutCenter("§m───────────────────────────────");
            stopWar();
        } else if (clan2Players.isEmpty()) {
            Core.shoutCenter("§f§m───────────§r §6Clan War §f§m───────────");
            Core.shoutCenter("§eThe clan war has ended.");
            Core.shoutCenter("§eThe winner of the match is: §6" + clan1.clanName);
            Core.shoutCenter("§m───────────────────────────────");
            stopWar();
        }
    }
}
