package me.robertle.mcconquest.Managers;

import me.robertle.mcconquest.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class OutpostManager implements CommandExecutor {

    public static List<UUID> cd10 = new ArrayList<>();
    public static List<UUID> cd20 = new ArrayList<>();
    public static List<UUID> cd30 = new ArrayList<>();

    public static List<Outpost> outposts = new ArrayList<>();

    public static class Outpost {
        public String name;
        public Location outpostLocation;
        public Player holder;
        public Clan clan;
        public int time;
        public BossBar bar;

        public Outpost(Location outpostLocation, String name) {
            this.outpostLocation = outpostLocation;
            this.name = name;
        }
    }

    public static void runOutpostTimer() {
        new BukkitRunnable() {
            public void run() {
                for (Outpost outpost : outposts) {
                    List<Player> players = new ArrayList<>();
                    for (Player p : outpost.outpostLocation.getNearbyPlayers(16, 16, 16)) players.add(p);
                    if (outpost.holder != null) {
                        if (players.contains(outpost.holder)) {
                            outpost.time += 1;
                            if (outpost.bar != null) outpost.bar.removeAll();
                            outpost.bar = Bukkit.createBossBar("§a§l" + outpost.name + " Outpost §r> §eHolder: §6" + outpost.clan.clanName + " §r> §e" + DateUtil.getTimerFormat(outpost.time), BarColor.YELLOW, BarStyle.SOLID);
                            for (Player player : outpost.outpostLocation.getNearbyPlayers(30, 256, 30)) {
                                outpost.bar.addPlayer(player);
                            }
                            if (outpost.time == 300) {
                                for (Player player : players) {
                                    if (!cd10.contains(player.getUniqueId())) {
                                        if (Clan.getPlayerClan(player).equalsIgnoreCase(outpost.clan.clanName)) {
                                            player.getInventory().addItem(CustomItemManager.getCrate(Crate.BASIC_CRATE));
                                            player.sendMessage("§6§lOutpost §r> §aYou've received a basic crate for holding the outpost for 5 minutes.");
                                        }
                                    }
                                    cd10.add(player.getUniqueId());
                                }
                            } else if (outpost.time == 600) {
                                for (Player player : players) {
                                    if (!cd20.contains(player.getUniqueId())) {
                                        if (Clan.getPlayerClan(player).equalsIgnoreCase(outpost.clan.clanName)) {
                                            player.getInventory().addItem(CustomItemManager.getCrate(Crate.SUPER_CRATE));
                                            player.sendMessage("§6§lOutpost §r> §aYou've received a super crate for holding the outpost for 10 minutes.");
                                        }
                                    }
                                    cd20.add(player.getUniqueId());
                                }
                            } else if (outpost.time == 900) {
                                for (Player player : players) {
                                    if (!cd30.contains(player.getUniqueId())) {
                                        if (Clan.getPlayerClan(player).equalsIgnoreCase(outpost.clan.clanName)) {
                                            player.getInventory().addItem(CustomItemManager.getCrate(Crate.SUPER_CRATE));
                                            player.sendMessage("§6§lOutpost §r> §aYou've received a super crate for holding the outpost for 15 minutes.");
                                        }
                                    }
                                    cd30.add(player.getUniqueId());
                                }
                            }
                        } else {
                            outpost.holder = null;
                        }
                    } else {
                        if (!players.isEmpty()) {
                            outpost.holder = players.get(0);
                            if (outpost.clan != null && Clan.getPlayerClan(outpost.holder).equalsIgnoreCase(outpost.clan.clanName)) {
                                outpost.time += 1;
                                if (outpost.bar != null) outpost.bar.removeAll();
                                outpost.bar = Bukkit.createBossBar("§a§l" + outpost.name + " Outpost §r> §eHolder: §6" + outpost.clan.clanName + " §r> §e" + DateUtil.getTimerFormat(outpost.time), BarColor.YELLOW, BarStyle.SOLID);
                                for (Player player : outpost.outpostLocation.getNearbyPlayers(30, 256, 30)) {
                                    outpost.bar.addPlayer(player);
                                }
                            } else {
                                outpost.time = 0;
                                if (!Clan.getPlayerClan(outpost.holder).equalsIgnoreCase("None")) {
                                    outpost.clan = Clan.getClan(Clan.getPlayerClan(outpost.holder));
                                    Core.shout("§6§lOutpost §r> §6" + outpost.clan.clanName + " §ris now holding the §a" + outpost.name + " §routpost.");
                                } else {
                                    outpost.holder = null;
                                    if (outpost.bar != null) outpost.bar.removeAll();
                                    outpost.bar = null;
                                }
                            }
                        } else {
                            outpost.holder = null;
                            if (outpost.bar != null) outpost.bar.removeAll();
                            outpost.bar = null;
                        }
                    }
                }
            }
        }.runTaskTimer(Core.instance, 0L, 20L);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (command.getLabel().equalsIgnoreCase("outpost")) {
                if (args.length == 2 && player.hasPermission("mcc.admin")) {
                    if (args[0].equalsIgnoreCase("set")) {
                        outposts.add(new Outpost(player.getLocation(), args[1]));
                    }
                } else {
                    player.openInventory(getOutpostMenu());
                }
            }
        }
        return false;
    }

    public static Inventory getOutpostMenu() {
        Inventory inv = Bukkit.createInventory(null, 9, "§6§lOutposts");
        inv.setItem(0, new ItemBuilder(Material.WHITE_STAINED_GLASS_PANE).displayName("§8§lMC§c§lConquest").asItemStack());
        inv.setItem(8, new ItemBuilder(Material.WHITE_STAINED_GLASS_PANE).displayName("§8§lMC§c§lConquest").asItemStack());

        int i = 2;
        for (Outpost outpost : outposts) {
            String holder;
            if (outpost.holder != null) {
                holder = outpost.clan.clanName;
            } else {
                holder = "None";
            }
            inv.setItem(i, new ItemBuilder(Material.BEACON).displayName("§6§l" + outpost.name + " Outpost").lore("§eLocation: §f" + Core.getStringFromLocation(outpost.outpostLocation), "", "§eHolder: §6" + holder, "§eTime: §f" + DateUtil.getTimerFormat(outpost.time)).setGlowing(true).asItemStack());
            i += 2;
        }

        return inv;
    }

}
