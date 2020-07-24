package me.robertle.mcconquest.Managers;

import me.robertle.mcconquest.Core;
import me.robertle.mcconquest.DefaultConfig;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TutorialManager implements Listener {

    public static List<UUID> tutorialPlayers = new ArrayList<>();

    public static void sendTutorial(Player player) {
        tutorialPlayers.add(player.getUniqueId());
        player.setInvulnerable(true);
        player.setGameMode(GameMode.SPECTATOR);

        new BukkitRunnable() {
            int time = 0;

            public void run() {
                if (!tutorialPlayers.contains(player.getUniqueId()) || !player.isOnline()) {
                    this.cancel();
                }

                switch (time) {
                    case 0:
                        player.teleport(DefaultConfig.locations.get("t1"));
                        player.sendMessage("");
                        player.sendMessage("Welcome to §c§lMC§8§lConquest§r! This tutorial will help you get started on this new gamemode.");
                        player.sendMessage("Do §a/leave §rto exit the tutorial at anytime!");
                        player.sendMessage("");
                        break;
                    case 6:
                        player.teleport(DefaultConfig.locations.get("t2"));
                        player.sendMessage("Your first goal on the server is to start making some economy and getting some good gear.");
                        player.sendMessage("Remember to claim your generator voucher in your inventory! You can view it in §a/gen §rto collect its production.");
                        break;
                    case 18:
                        player.teleport(DefaultConfig.locations.get("t3"));
                        player.sendMessage("");
                        player.sendMessage("You can buy most of your needs here at the §6§lMerchant Market§r.");
                        break;
                    case 24:
                        player.teleport(DefaultConfig.locations.get("t4"));
                        player.sendMessage("");
                        player.sendMessage("The main way you'll be getting your armor and weapons is to buy vouchers at the §6§lCombat Merchant§r.");
                        break;
                    case 30:
                        player.teleport(DefaultConfig.locations.get("t5"));
                        player.sendMessage("");
                        player.sendMessage("Armor and Weapons in this server are different. They're §8§lunbreakable§r, they have §d§llives §rand they're classified by §4§ltiers§r.");
                        break;
                    case 38:
                        player.teleport(DefaultConfig.locations.get("t6"));
                        player.sendMessage("");
                        player.sendMessage("§aB+ §rTiers of armors and §eA+ §rTiers of weapons have custom enchants.");
                        player.sendMessage("You can modify the custom enchant and lives here at the §8§lBlacksmith§r.");
                        break;
                    case 48:
                        player.teleport(DefaultConfig.locations.get("t7"));
                        player.sendMessage("");
                        player.sendMessage("The §6§lCombat Merchant §rtrades vouchers for ingots. You can start mining here at the §7§lIron Mines §rin the §aSpawn Zone§r.");
                        player.sendMessage("You can later get gold ingots at the §6§lGold Mines §rin the §cPvP Zone§r.");
                        break;
                    case 62:
                        player.teleport(DefaultConfig.locations.get("t8"));
                        player.sendMessage("");
                        player.sendMessage("A good way to make money is §4§lsalvaging §rarmor and weapons you don't want here.");
                        break;
                    case 68:
                        player.teleport(DefaultConfig.locations.get("t9"));
                        player.sendMessage("");
                        player.sendMessage("Another economy you'll need later is §bessence§r. You can get these from killing mobs at the §4§lMob Totem§r.");
                        player.sendMessage("You can later get rare essence at §4§lMob Totems §rin the §cPvP Zone§r.");
                        break;
                    case 80:
                        player.teleport(DefaultConfig.locations.get("t10"));
                        player.sendMessage("");
                        player.sendMessage("Fishing is also another way you can make economy, but it's less efficient.");
                        player.sendMessage("Though you have a chance of finding §6rare loot §rand §6artifacts§r!");
                        break;
                    case 90:
                        player.teleport(DefaultConfig.locations.get("t11"));
                        player.sendMessage("");
                        player.sendMessage("Also the §6§lSpecial Merchant §ris here, you can get some special items and pets here.");
                        break;
                    case 95:
                        player.teleport(DefaultConfig.locations.get("t12"));
                        player.sendMessage("");
                        player.sendMessage("Joining or Creating a clan is highly advised. This will help you win the daily events.");
                        break;
                    case 101:
                        player.teleport(DefaultConfig.locations.get("t13"));
                        player.sendMessage("");
                        player.sendMessage("This will also allow you to participate in daily §6§lClan Battles §rand the §6§lClan War §rat the end of the season.");
                        break;
                    case 107:
                        player.teleport(DefaultConfig.locations.get("t14"));
                        player.sendMessage("");
                        player.sendMessage("Have fun playing! Check the §a/wiki §rfor more in-depth details or ask a §ahelper §ror §2mod §rquestions.");
                        break;
                    case 110:
                        removeTutorial(player);
                }

                time++;
            }
        }.runTaskTimer(Core.instance, 0L, 20L);
    }

    public static void removeTutorial(Player player) {
        if (tutorialPlayers.contains(player.getUniqueId())) {
            tutorialPlayers.remove(player.getUniqueId());
            player.teleport(DefaultConfig.locations.get("spawn"));
        }
        player.setInvulnerable(false);
        player.setGameMode(GameMode.SURVIVAL);
    }

    @EventHandler
    public void onFreezeMove(PlayerMoveEvent e) {
        if (tutorialPlayers.contains(e.getPlayer().getUniqueId())) {
            Double xTo = e.getTo().getX();
            Double xFrom = e.getFrom().getX();
            Double yTo = e.getTo().getY();
            Double yFrom = e.getFrom().getY();
            Double zTo = e.getTo().getZ();
            Double zFrom = e.getFrom().getZ();
            if (e.getTo().locToBlock(xTo) != e.getFrom().locToBlock(xFrom) || e.getTo().locToBlock(zTo) != e.getFrom().locToBlock(zFrom) || e.getTo().locToBlock(yTo) != e.getFrom().locToBlock(yFrom)) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        for (UUID uuid : tutorialPlayers) {
            if (e.getRecipients().contains(Bukkit.getPlayer(uuid)))
                e.getRecipients().remove(Bukkit.getPlayer(uuid));
        }
    }

    @EventHandler
    public void onChat(PlayerCommandPreprocessEvent e) {
        if (tutorialPlayers.contains(e.getPlayer().getUniqueId())) {
            if (!e.getMessage().equalsIgnoreCase("/leave")) {
                e.setCancelled(true);
                e.getPlayer().sendMessage("§cYou cannot use commands while in the tutorial. Please /leave to exit it.");
            }
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        removeTutorial(e.getPlayer());
    }

}
