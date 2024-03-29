package me.robertle.mcconquest;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class Challenge {

    public static HashMap<Clan, Challenge> pendingChallenges = new HashMap<>();

    public static Queue<Challenge> challengeQueue = new LinkedList<>();

    public static List<String> challengeCooldown = new ArrayList<>();

    //Challenge Manager
    public static Challenge activeChallenge;
    public static boolean active = false;
    public static int timer = 0;

    public static void runChallengeTimer() {
        new BukkitRunnable() {
            public void run() {
                if (!active) {
                    if (!challengeQueue.isEmpty()) {
                        active = true;
                        Challenge challenge = challengeQueue.poll();
                        activeChallenge = challenge;
                        challenge.sendAll(DefaultConfig.prefix + "Your clan battle is starting in a minute.\n §4§lGet ready!");
                        new BukkitRunnable() {
                            public void run() {
                                if (challenge.team1Participants.isEmpty() || challenge.team2Participants.isEmpty()) {
                                    active = false;
                                    challenge.sendAll(DefaultConfig.prefix + "§cThe clan battle failed to start.");
                                    this.cancel();
                                    return;
                                }
                                challenge.removeOfflines();
                                challenge.teleportTeam1(DefaultConfig.locations.get("challenge1"));
                                challenge.teleportTeam2(DefaultConfig.locations.get("challenge2"));
                                challenge.sendAllCenter("§m────────────────────────────────");
                                challenge.sendAllCenter("§e§lClan Battle");
                                challenge.sendAllCenter("§6" + challenge.clan1.clanName + " §f§lVS §6" + challenge.clan2.clanName);
                                challenge.sendAllCenter("§fThe first clan to eliminate the other wins.");
                                challenge.sendAllCenter("§fBattles over 10 minutes will end in a draw.");
                                challenge.sendAllCenter("§m────────────────────────────────");

                                challengeCooldown.add(challenge.clan1.clanName);
                                challengeCooldown.add(challenge.clan2.clanName);
                            }
                        }.runTaskLater(Core.instance, 1200L);
                    }
                } else {
                    if (timer == 660) {
                        activeChallenge.sendAllCenter("§m────────────────────────────────");
                        activeChallenge.sendAllCenter("§e§lClan Battle");
                        activeChallenge.sendAllCenter("§6" + activeChallenge.clan1.clanName + " §f§lVS §6" + activeChallenge.clan2.clanName);
                        activeChallenge.sendAllCenter("§fThe clan battle ended in a §cdraw.");
                        activeChallenge.sendAllCenter("§m────────────────────────────────");
                        endChallenge();
                        return;
                    }
                    if (timer < 660) {
                        timer++;
                        activeChallenge.removeOfflines();
                        if (activeChallenge.team1Participants.isEmpty()) {
                            activeChallenge.sendAllCenter("§m────────────────────────────────");
                            activeChallenge.sendAllCenter("§e§lClan Battle");
                            activeChallenge.sendAllCenter("§6" + activeChallenge.clan1.clanName + " §f§lVS §6" + activeChallenge.clan2.clanName);
                            activeChallenge.sendAllCenter("§f§lWinner: §6" + activeChallenge.clan2.clanName);
                            activeChallenge.sendAllCenter("§fThe rewards have been randomly given out.");
                            activeChallenge.sendAllCenter("§m────────────────────────────────");
                            Core.shout(DefaultConfig.prefix + "§6" + activeChallenge.clan2.clanName + " §fhas defeated §c" + activeChallenge.clan1.clanName + " §fin a clan battle!");
                            int perkAverage = (activeChallenge.clan1.clanPerk + activeChallenge.clan2.clanPerk) / 2;
                            giveChallengeRewards(perkAverage, activeChallenge.team2Participants);
                            activeChallenge.clan2.battleWins += 1;
                            endChallenge();
                        } else if (activeChallenge.team2Participants.isEmpty()) {
                            activeChallenge.sendAllCenter("§m────────────────────────────────");
                            activeChallenge.sendAllCenter("§e§lClan Battle");
                            activeChallenge.sendAllCenter("§6" + activeChallenge.clan1.clanName + " §f§lVS §6" + activeChallenge.clan2.clanName);
                            activeChallenge.sendAllCenter("§f§lWinner: §6" + activeChallenge.clan1.clanName);
                            activeChallenge.sendAllCenter("§fThe rewards have been randomly given out.");
                            activeChallenge.sendAllCenter("§m────────────────────────────────");
                            Core.shout(DefaultConfig.prefix + "§6" + activeChallenge.clan1.clanName + " §fhas defeated §c" + activeChallenge.clan2.clanName + " §fin a clan battle!");
                            int perkAverage = (activeChallenge.clan1.clanPerk + activeChallenge.clan2.clanPerk) / 2;
                            giveChallengeRewards(perkAverage, activeChallenge.team1Participants);
                            activeChallenge.clan1.battleWins += 1;
                            endChallenge();
                        }
                    }
                    return;
                }
            }
        }.runTaskTimer(Core.instance, 0L, 20L);
    }

    public static void giveChallengeRewards(int perkAverage, List<Player> winners) {
        if (perkAverage == 4 || perkAverage == 5) {
            Core.giftPlayer(winners.get(Core.generateNumber(0, winners.size() - 1)), "super", 1);
            Core.giftPlayer(winners.get(Core.generateNumber(0, winners.size() - 1)), "super", 1);
            Core.giftPlayer(winners.get(Core.generateNumber(0, winners.size() - 1)), "super", 1);
        } else if (perkAverage == 6 || perkAverage == 7) {
            Core.giftPlayer(winners.get(Core.generateNumber(0, winners.size() - 1)), "super", 1);
            Core.giftPlayer(winners.get(Core.generateNumber(0, winners.size() - 1)), "super", 1);
            Core.giftPlayer(winners.get(Core.generateNumber(0, winners.size() - 1)), "super", 1);
            Core.giftPlayer(winners.get(Core.generateNumber(0, winners.size() - 1)), "super", 1);
        } else if (perkAverage == 8 || perkAverage == 9) {
            Core.giftPlayer(winners.get(Core.generateNumber(0, winners.size() - 1)), "super", 1);
            Core.giftPlayer(winners.get(Core.generateNumber(0, winners.size() - 1)), "super", 1);
            Core.giftPlayer(winners.get(Core.generateNumber(0, winners.size() - 1)), "super", 1);
            Core.giftPlayer(winners.get(Core.generateNumber(0, winners.size() - 1)), "super", 1);
            Core.giftPlayer(winners.get(Core.generateNumber(0, winners.size() - 1)), "super", 1);
        } else if (perkAverage == 10) {
            Core.giftPlayer(winners.get(Core.generateNumber(0, winners.size() - 1)), "super", 1);
            Core.giftPlayer(winners.get(Core.generateNumber(0, winners.size() - 1)), "super", 1);
            Core.giftPlayer(winners.get(Core.generateNumber(0, winners.size() - 1)), "super", 1);
            Core.giftPlayer(winners.get(Core.generateNumber(0, winners.size() - 1)), "super", 1);
            Core.giftPlayer(winners.get(Core.generateNumber(0, winners.size() - 1)), "super", 1);
            Core.giftPlayer(winners.get(Core.generateNumber(0, winners.size() - 1)), "ultra", 1);
        }
    }

    public static void endChallenge() {
        if (active) {
            timer = 999;
            active = false;
            activeChallenge.teleportTeam1(DefaultConfig.locations.get("spawn"));
            activeChallenge.teleportTeam2(DefaultConfig.locations.get("spawn"));
            pendingChallenges.remove(activeChallenge.clan1);
            pendingChallenges.remove(activeChallenge.clan2);
            activeChallenge = null;
        }
    }

    //Challenge Object
    public List<Player> team1Participants = new ArrayList<>();
    public List<Player> team2Participants = new ArrayList<>();
    public Clan clan1;
    public Clan clan2;
    public int participants;

    public Player challenger;
    public Player challenged;

    public boolean accepted = false;

    public boolean confirm1 = false;
    public boolean confirm2 = false;

    public Challenge(Clan team1, Clan team2, Player challenger, Player challenged, int participants) {
        this.clan1 = team1;
        this.clan2 = team2;
        this.challenged = challenged;
        this.challenger = challenger;
        this.participants = participants;
    }

    public void sendAll(String message) {
        for (Player player : team1Participants) {
            if (player.isOnline()) player.sendMessage(message);
        }
        for (Player player : team2Participants) {
            if (player.isOnline()) player.sendMessage(message);
        }
    }

    public void sendAllCenter(String message) {
        for (Player player : team1Participants) {
            if (player.isOnline()) StringUtil.sendCenteredMessage(player, message);
        }
        for (Player player : team2Participants) {
            if (player.isOnline()) StringUtil.sendCenteredMessage(player, message);
        }
    }

    public void removeOfflines() {
        for (int i = team1Participants.size() - 1; i >= 0; i--) {
            if (!team1Participants.get(i).isOnline()) team1Participants.remove(i);
        }
        for (int i = team2Participants.size() - 1; i >= 0; i--) {
            if (!team2Participants.get(i).isOnline()) team2Participants.remove(i);
        }
    }


    public void teleportTeam1(Location location) {
        for (Player player : team1Participants) {
            if (player.isOnline()) player.teleport(location);
        }
    }

    public void teleportTeam2(Location location) {
        for (Player player : team2Participants) {
            if (player.isOnline()) player.teleport(location);
        }
    }


}
