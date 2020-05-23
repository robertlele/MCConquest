package me.robertle.mcconquest;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ClanCommands implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String prefix = DefaultConfig.prefix;
        if (!(sender instanceof Player)) return false;
        if (command.getLabel().equalsIgnoreCase("cc")) {
            Player player = (Player) sender;
            if (args.length == 0) {
                player.sendMessage(prefix + "§cUsage: /cc <message>");
                return false;
            }
            if (!Clan.getPlayerClan(player).equalsIgnoreCase("None")) {
                Clan clan = Clan.clans.get(Clan.getPlayerClan(player));
                String message = "";
                for (int i = 0; i < args.length; i++) {
                    message += args[i] + " ";
                }
                for (Player p : clan.getOnlinePlayers()) {
                    p.sendMessage("§e[Clan Chat] " + p.getName() + "§f: " + message);
                }
            } else {
                player.sendMessage("You aren't in a clan.");
            }

        }
        if (command.getLabel().equalsIgnoreCase("clan")) {

            Player player = (Player) sender;

            if (args.length == 0) {
                StringUtil.sendCenteredMessage(player, "§f———————————————— §aClans Help §f————————————————");
                player.sendMessage("§f/clan create <clan> §eCreate a clan for $50k.");
                player.sendMessage("§f/clan show <clan/player> §eView a clan.");
                player.sendMessage("§f/clan join §eJoin a clan that you've been invited to.");
                player.sendMessage("§f/clan leave §eLeave a clan.");
                player.sendMessage("§f/clan list <page> §eView all clans.");
                player.sendMessage("§f/cc <message> §eSend a message to your clan members.");
                player.sendMessage("§f/clan invite <player> §eInvite a player to the clan. (Co-Leaders+ Only)");
                player.sendMessage("§f/clan kick <player> §eKick a player from the clan. (Co-Leaders+ Only)");
                player.sendMessage("§f/clan logs <page> §eView the clan logs. (Co-Leaders+ Only)");
                player.sendMessage("§f/clan promote <player> §ePromote a player. (Leaders Only)");
                player.sendMessage("§f/clan demote <player> §eDemote a player. (Leaders Only)");
                player.sendMessage("§f/clan disband §eDisband your clan. (Leaders Only)");
                player.sendMessage("§f/clan deposit <amount> §eDeposit money to your clan to purchase clan perks.");
                player.sendMessage("§f/clan storage §eOpen up the clan's storage. (Officers+ Only)");
                player.sendMessage("§f/clan perks §eView your clan's perks.");
                StringUtil.sendCenteredMessage(player, "§f———————————————— §aClans Help §f————————————————");
                return false;
            }

            if (args[0].equalsIgnoreCase("remove")) {
                if (player.hasPermission("mcc.admin")) {

                } else {
                    player.sendMessage("§cYou don't have permission for this command.");
                }
            } else if (args[0].equalsIgnoreCase("create")) {
                if (args.length != 2) {
                    player.sendMessage(prefix + "§cUsage: /clan create <clan>");
                    return false;
                }
                if (Clan.getPlayerClan(player).equalsIgnoreCase("None")) {
                    if (Core.econ.getBalance(player) >= 50000) {
                        if (!Clan.clanExist(args[1])) {
                            if (Core.isAlpha(args[1]) && args[1].length() <= 16) {
                                Clan.createClan(args[1], player);
                                Core.econ.withdrawPlayer(player, 50000);
                                player.sendMessage(prefix + "§aYour clan has been created.");
                                Core.shout(prefix + player.getName() + " has created a clan: §6§l" + args[1]);
                            } else {
                                player.sendMessage(prefix + "§cClan names can only contain up to 16 letters.");
                            }
                        } else {
                            player.sendMessage(prefix + "§cThis clan name already exists.");
                        }
                    } else {
                        player.sendMessage(prefix + "§cYou need $50k to create a clan.");
                    }
                } else {
                    player.sendMessage(prefix + "§cYou are already in a clan.");
                }
            } else if (args[0].equalsIgnoreCase("invite")) {
                if (args.length != 2) {
                    player.sendMessage(prefix + "§cUsage: /clan invite <player>");
                    return false;
                }
                if (!Clan.getPlayerClan(player).equalsIgnoreCase("None")) {
                    Clan clan = Clan.clans.get(Clan.getPlayerClan(player));
                    if (clan.clanMembers.get(player.getUniqueId()) == ClanRole.COLEADER || clan.clanMembers.get(player.getUniqueId()) == ClanRole.LEADER) {
                        if (Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(args[1]))) {
                            Player invitee = Bukkit.getPlayer(args[1]);
                            if (Clan.getPlayerClan(invitee).equalsIgnoreCase("None")) {
                                clan.invitePlayer(player, invitee);
                                player.sendMessage(prefix + "§aYour invite has been sent.");
                                invitee.sendMessage(prefix + "§eYou've been invited to join " + clan.clanName + ". Do /clan join to join.");
                            } else {
                                player.sendMessage(prefix + "§cThe player is already in a clan.");
                            }
                        } else {
                            player.sendMessage(prefix + "§cThe player isn't online.");
                        }
                    } else {
                        player.sendMessage(prefix + "§cYou cannot invite players to the clan.");
                    }
                } else {
                    player.sendMessage(prefix + "§cYou aren't in a clan.");
                }
            } else if (args[0].equalsIgnoreCase("join") || args[0].equalsIgnoreCase("j")) {
                if (Clan.getPlayerClan(player).equalsIgnoreCase("None")) {
                    if (Clan.clanInvites.containsKey(player.getUniqueId())) {
                        Clan.clans.get(Clan.clanInvites.get(player.getUniqueId())).addPlayer(player);
                        player.sendMessage(prefix + "§aYou've joined the clan.");
                    } else {
                        player.sendMessage(prefix + "§cYou don't have any pending invites.");
                    }
                } else {
                    player.sendMessage(prefix + "§cYou're already in a clan.");
                }
            } else if (args[0].equalsIgnoreCase("kick")) {
                if (args.length != 2) {
                    player.sendMessage(prefix + "§cUsage: /clan kick <player>");
                } else if (!Clan.getPlayerClan(player).equalsIgnoreCase("None")) {
                    Clan clan = Clan.clans.get(Clan.getPlayerClan(player));
                    if (clan.clanMembers.get(player.getUniqueId()) == ClanRole.COLEADER || clan.clanMembers.get(player.getUniqueId()) == ClanRole.LEADER) {
                        if (Clan.getPlayerClan(args[1]).equalsIgnoreCase(clan.clanName)) {
                            if (clan.clanMembers.get(args[1]) != ClanRole.LEADER) {
                                clan.kickPlayer(Bukkit.getPlayerUniqueId(args[1]), player);
                                player.sendMessage(prefix + "§aYou've kicked " + args[1] + " from the clan.");
                            } else {
                                player.sendMessage(prefix + "§cYou cannot kick the leader.");
                            }
                        } else {
                            player.sendMessage(prefix + "§cThe player isn't in the clan.");
                        }
                    } else {
                        player.sendMessage(prefix + "§cYou cannot kick players from the clan.");
                    }
                } else {
                    player.sendMessage(prefix + "§cYou aren't in a clan.");
                }
            } else if (args[0].equalsIgnoreCase("leave")) {
                if (!Clan.getPlayerClan(player).equalsIgnoreCase("None")) {
                    Clan clan = Clan.clans.get(Clan.getPlayerClan(player));
                    if (clan.clanMembers.get(player.getUniqueId()) != ClanRole.LEADER) {
                        clan.removePlayer(player.getUniqueId());
                        player.sendMessage(prefix + "§aYou've left the clan.");
                    } else {
                        player.sendMessage(prefix + "§cYou have to disband the clan to leave.");
                    }
                } else {
                    player.sendMessage(prefix + "§cYou aren't in a clan.");
                }
            } else if (args[0].equalsIgnoreCase("disband")) {
                if (!Clan.getPlayerClan(player).equalsIgnoreCase("None")) {
                    Clan clan = Clan.clans.get(Clan.getPlayerClan(player));
                    if (clan.clanMembers.get(player.getUniqueId()) == ClanRole.LEADER) {
                        Clan.deleteClan(clan.clanName);
                        player.sendMessage(prefix + "§aYou've successfully disbanded your clan.");
                    } else {
                        player.sendMessage(prefix + "§cOnly leaders can disband the clan.");
                    }
                } else {
                    player.sendMessage(prefix + "§cYou aren't in a clan.");
                }
            } else if (args[0].equalsIgnoreCase("list")) {
                if (args.length == 1) {
                    int page = 1;
                    List<Clan> clans = Clan.sortClans();
                    StringUtil.sendCenteredMessage(player, "§f———————————————— §aClans List [" + page + "/" + (clans.size() / 10 + 1) + "] §f————————————————");
                    String list = "";
                    for (int i = 10 * (page - 1); i < 10 * (page); i++) {
                        if (i < clans.size()) {
                            list += "§f" + clans.get(i).clanName + " §7> §e" + clans.get(i).getOnlinePlayers().size() + "/8 Online\n";
                        }
                    }
                    player.sendMessage(list);
                    StringUtil.sendCenteredMessage(player, "§f———————————————— §aClans List [" + page + "/" + (clans.size() / 10 + 1) + "] §f————————————————");
                    return false;
                }
                int page;
                try {
                    page = Integer.parseInt(args[1]);
                    if (page == 0) page++;
                } catch (NumberFormatException e) {
                    player.sendMessage(prefix + "§cUsage: /clan list <page>");
                    return false;
                }
                if (Clan.clans.size() / 10 < page - 1) {
                    player.sendMessage(prefix + "§cInvalid page.");
                    return false;
                }
                List<Clan> clans = Clan.sortClans();
                StringUtil.sendCenteredMessage(player, "§f———————————————— §aClans List [" + page + "/" + (clans.size() / 10 + 1) + "] §f————————————————");
                String list = "";
                for (int i = 10 * (page - 1); i < 10 * (page); i++) {
                    if (i < clans.size()) {
                        list += "§f" + clans.get(i).clanName + " §7> §e" + clans.get(i).getOnlinePlayers().size() + "/8 Online\n";
                    }
                }
                player.sendMessage(list);
                StringUtil.sendCenteredMessage(player, "§f———————————————— §aClans List [" + page + "/" + (clans.size() / 10 + 1) + "] §f————————————————");
            } else if (args[0].equalsIgnoreCase("help")) {
                StringUtil.sendCenteredMessage(player, "§f———————————————— §aClans Help §f————————————————");
                player.sendMessage("§f/clan create <clan> §eCreate a clan for $50k.");
                player.sendMessage("§f/clan show <clan/player> §eView a clan.");
                player.sendMessage("§f/clan join §eJoin a clan that you've been invited to.");
                player.sendMessage("§f/clan leave §eLeave a clan.");
                player.sendMessage("§f/clan list <page> §eView all clans.");
                player.sendMessage("§f/cc <message> §eSend a message to your clan members.");
                player.sendMessage("§f/clan invite <player> §eInvite a player to the clan. (Co-Leaders+ Only)");
                player.sendMessage("§f/clan kick <player> §eKick a player from the clan. (Co-Leaders+ Only)");
                player.sendMessage("§f/clan logs <page> §eView the clan logs. (Co-Leaders+ Only)");
                player.sendMessage("§f/clan promote <player> §ePromote a player. (Leaders Only)");
                player.sendMessage("§f/clan demote <player> §eDemote a player. (Leaders Only)");
                player.sendMessage("§f/clan disband §eDisband your clan. (Leaders Only)");
                player.sendMessage("§f/clan deposit <amount> §eDeposit money to your clan to purchase clan perks.");
                player.sendMessage("§f/clan storage §eOpen up the clan's storage. (Officers+ Only)");
                player.sendMessage("§f/clan perks §eView your clan's perks.");
                StringUtil.sendCenteredMessage(player, "§f———————————————— §aClans Help §f————————————————");
            } else if (args[0].equalsIgnoreCase("deposit") || args[0].equalsIgnoreCase("d")) {
                if (args.length != 2) {
                    player.sendMessage(prefix + "§cUsage: /clan deposit <amount>");
                    return false;
                }
                if (!Clan.getPlayerClan(player).equalsIgnoreCase("None")) {
                    Clan clan = Clan.clans.get(Clan.getPlayerClan(player));
                    int amount = 0;
                    try {
                        amount = Integer.parseInt(args[1]);
                    } catch (Exception e) {
                        player.sendMessage(prefix + "§cUsage: /clan deposit <amount>");
                        return false;
                    }
                    if (Core.econ.has(player, amount)) {
                        clan.deposit(player, amount);
                        player.sendMessage(prefix + "§aYou've deposited $" + amount + " to the clan.");
                    } else {
                        player.sendMessage(prefix + "§cInsufficient funds.");
                    }
                } else {
                    player.sendMessage(prefix + "§cYou aren't in a clan.");
                }
            } else if (args[0].equalsIgnoreCase("logs") || args[0].equalsIgnoreCase("log")) {
                if (args.length != 2) {
                    player.sendMessage(prefix + "§cUsage: /clan logs <page>");
                    return false;
                }
                if (!Clan.getPlayerClan(player).equalsIgnoreCase("None")) {
                    Clan clan = Clan.clans.get(Clan.getPlayerClan(player));
                    if (clan.clanMembers.get(player.getUniqueId()) == ClanRole.COLEADER || clan.clanMembers.get(player.getUniqueId()) == ClanRole.LEADER) {
                        int page;
                        try {
                            page = Integer.parseInt(args[1]);
                            if (page == 0) page++;
                        } catch (NumberFormatException e) {
                            player.sendMessage(prefix + "§cUsage: /clan logs <page>");
                            return false;
                        }
                        if (page > clan.getLogPages()) {
                            player.sendMessage(prefix + "§cInvalid page.");
                            return false;
                        }
                        StringUtil.sendCenteredMessage(player, "§f———————————————— §aClan Logs [" + page + "/" + clan.getLogPages() + "] §f————————————————");
                        player.sendMessage(clan.getLogString(page - 1));
                        StringUtil.sendCenteredMessage(player, "§f———————————————— §aClan Logs [" + page + "/" + clan.getLogPages() + "] §f————————————————");
                    } else {
                        player.sendMessage(prefix + "§cYou cannot view the clan logs.");
                    }
                } else {
                    player.sendMessage(prefix + "§cYou aren't in a clan.");
                }
            } else if (args[0].equalsIgnoreCase("show")) {
                if (args.length == 2) {
                    if (Clan.clanExist(args[1])) {
                        Clan clan = Clan.getClan(args[1]);
                        player.sendMessage("§f———————————————— §6" + clan.clanName + " §f————————————————");
                        player.sendMessage("§eClan Perk Progress: §f" + clan.clanPerk + "/10");
                        player.sendMessage("§eClan Balance: §f$" + clan.clanBalance);
                        List<String> players = new ArrayList<>();
                        for (UUID uuid : clan.clanMembers.keySet()) {
                            if (clan.clanMembers.get(uuid) == ClanRole.LEADER) {
                                if (Bukkit.getOfflinePlayer(uuid).isOnline())
                                    players.add("§a***" + Bukkit.getOfflinePlayer(uuid).getName());
                                else players.add("§7***" + Bukkit.getOfflinePlayer(uuid).getName());
                            }
                        }
                        for (UUID uuid : clan.clanMembers.keySet()) {
                            if (clan.clanMembers.get(uuid) == ClanRole.COLEADER) {
                                if (Bukkit.getOfflinePlayer(uuid).isOnline())
                                    players.add("§a**" + Bukkit.getOfflinePlayer(uuid).getName());
                                else players.add("§7**" + Bukkit.getOfflinePlayer(uuid).getName());
                            }
                        }
                        for (UUID uuid : clan.clanMembers.keySet()) {
                            if (clan.clanMembers.get(uuid) == ClanRole.OFFICER) {
                                if (Bukkit.getOfflinePlayer(uuid).isOnline())
                                    players.add("§a*" + Bukkit.getOfflinePlayer(uuid).getName());
                                else players.add("§7*" + Bukkit.getOfflinePlayer(uuid).getName());
                            }
                        }
                        for (UUID uuid : clan.clanMembers.keySet()) {
                            if (clan.clanMembers.get(uuid) == ClanRole.MEMBER) {
                                if (Bukkit.getOfflinePlayer(uuid).isOnline())
                                    players.add("§a-" + Bukkit.getOfflinePlayer(uuid).getName());
                                else players.add("§7-" + Bukkit.getOfflinePlayer(uuid).getName());
                            }
                        }
                        String memberList = "§eMembers: §f";
                        for (String string : players) {
                            memberList += string + ", ";
                        }
                        player.sendMessage(memberList);
                    } else if (MCCPlayer.playerClans.containsKey(Core.getPlayerUUID(args[1]))) {
                        Clan clan = Clan.clans.get(MCCPlayer.playerClans.get(Core.getPlayerUUID(args[1])));
                        StringUtil.sendCenteredMessage(player, "§f———————————————— §6" + clan.clanName + " §f————————————————");
                        player.sendMessage("§eClan Perk Progress: §f" + clan.clanPerk + "/10");
                        player.sendMessage("§eClan Balance: §f$" + clan.clanBalance);
                        List<String> players = new ArrayList<>();
                        for (UUID uuid : clan.clanMembers.keySet()) {
                            if (clan.clanMembers.get(uuid) == ClanRole.LEADER) {
                                if (Bukkit.getOfflinePlayer(uuid).isOnline())
                                    players.add("§a***" + Bukkit.getOfflinePlayer(uuid).getName());
                                else players.add("§7***" + Bukkit.getOfflinePlayer(uuid).getName());
                            }
                        }
                        for (UUID uuid : clan.clanMembers.keySet()) {
                            if (clan.clanMembers.get(uuid) == ClanRole.COLEADER) {
                                if (Bukkit.getOfflinePlayer(uuid).isOnline())
                                    players.add("§a**" + Bukkit.getOfflinePlayer(uuid).getName());
                                else players.add("§7**" + Bukkit.getOfflinePlayer(uuid).getName());
                            }
                        }
                        for (UUID uuid : clan.clanMembers.keySet()) {
                            if (clan.clanMembers.get(uuid) == ClanRole.OFFICER) {
                                if (Bukkit.getOfflinePlayer(uuid).isOnline())
                                    players.add("§a*" + Bukkit.getOfflinePlayer(uuid).getName());
                                else players.add("§7*" + Bukkit.getOfflinePlayer(uuid).getName());
                            }
                        }
                        for (UUID uuid : clan.clanMembers.keySet()) {
                            if (clan.clanMembers.get(uuid) == ClanRole.MEMBER) {
                                if (Bukkit.getOfflinePlayer(uuid).isOnline())
                                    players.add("§a-" + Bukkit.getOfflinePlayer(uuid).getName());
                                else players.add("§7-" + Bukkit.getOfflinePlayer(uuid).getName());
                            }
                        }
                        String memberList = "§eMembers: §f";
                        for (String string : players) {
                            memberList += string + ", ";
                        }
                        player.sendMessage(memberList);
                        StringUtil.sendCenteredMessage(player, "§f———————————————— §6" + clan.clanName + " §f————————————————");
                    } else {
                        player.sendMessage(prefix + "§cThat clan doesn't exist.");
                    }
                } else {
                    if (!Clan.getPlayerClan(player).equalsIgnoreCase("None")) {
                        Clan clan = Clan.clans.get(Clan.getPlayerClan(player));
                        StringUtil.sendCenteredMessage(player, "§f———————————————— §6" + clan.clanName + " §f————————————————");
                        player.sendMessage("§eClan Perk Progress: §f" + clan.clanPerk + "/10");
                        player.sendMessage("§eClan Balance: §f$" + clan.clanBalance);
                        List<String> players = new ArrayList<>();
                        for (UUID uuid : clan.clanMembers.keySet()) {
                            if (clan.clanMembers.get(uuid) == ClanRole.LEADER) {
                                if (Bukkit.getOfflinePlayer(uuid).isOnline())
                                    players.add("§a***" + Bukkit.getOfflinePlayer(uuid).getName());
                                else players.add("§7***" + Bukkit.getOfflinePlayer(uuid).getName());
                            }
                        }
                        for (UUID uuid : clan.clanMembers.keySet()) {
                            if (clan.clanMembers.get(uuid) == ClanRole.COLEADER) {
                                if (Bukkit.getOfflinePlayer(uuid).isOnline())
                                    players.add("§a**" + Bukkit.getOfflinePlayer(uuid).getName());
                                else players.add("§7**" + Bukkit.getOfflinePlayer(uuid).getName());
                            }
                        }
                        for (UUID uuid : clan.clanMembers.keySet()) {
                            if (clan.clanMembers.get(uuid) == ClanRole.OFFICER) {
                                if (Bukkit.getOfflinePlayer(uuid).isOnline())
                                    players.add("§a*" + Bukkit.getOfflinePlayer(uuid).getName());
                                else players.add("§7*" + Bukkit.getOfflinePlayer(uuid).getName());
                            }
                        }
                        for (UUID uuid : clan.clanMembers.keySet()) {
                            if (clan.clanMembers.get(uuid) == ClanRole.MEMBER) {
                                if (Bukkit.getOfflinePlayer(uuid).isOnline())
                                    players.add("§a-" + Bukkit.getOfflinePlayer(uuid).getName());
                                else players.add("§7-" + Bukkit.getOfflinePlayer(uuid).getName());
                            }
                        }
                        String memberList = "§eMembers: §f";
                        for (String string : players) {
                            memberList += string + ", ";
                        }
                        player.sendMessage(memberList);
                        StringUtil.sendCenteredMessage(player, "§f———————————————— §6" + clan.clanName + " §f————————————————");
                    } else {
                        player.sendMessage(prefix + "§cYou aren't in a clan.");
                    }
                }
            } else if (args[0].equalsIgnoreCase("storage")) {
                if (!Clan.getPlayerClan(player).equalsIgnoreCase("None")) {
                    Clan clan = Clan.clans.get(Clan.getPlayerClan(player));
                    if (clan.clanMembers.get(player.getUniqueId()) == ClanRole.OFFICER || clan.clanMembers.get(player.getUniqueId()) == ClanRole.COLEADER || clan.clanMembers.get(player.getUniqueId()) == ClanRole.LEADER) {
                        if (clan.clanPerk >= 1) {
                            clan.openStorage(player);
                        } else {
                            player.sendMessage(prefix + "§cYour clan doesn't have a storage.");
                        }
                    } else {
                        player.sendMessage(prefix + "§cYou cannot access the storage");
                    }
                } else {
                    player.sendMessage(prefix + "§cYou aren't in a clan.");
                }
            } else if (args[0].equalsIgnoreCase("promote")) {
                if (args.length != 2) {
                    player.sendMessage(prefix + "§cUsage: /clan promote <player>");
                } else if (!Clan.getPlayerClan(player).equalsIgnoreCase("None")) {
                    Clan clan = Clan.clans.get(Clan.getPlayerClan(player));
                    if (clan.clanMembers.get(player.getUniqueId()) == ClanRole.LEADER) {
                        if (Clan.getPlayerClan(args[1]).equalsIgnoreCase(clan.clanName)) {
                            if (clan.promotePlayer(Core.getPlayerUUID(args[1]))) {
                                player.sendMessage(prefix + "§aThe player has been promoted.");
                            } else {
                                player.sendMessage(prefix + "§cThe player couldn't be promoted.");
                            }
                        } else {
                            player.sendMessage(prefix + "§cThe player isn't in the clan.");
                        }
                    } else {
                        player.sendMessage(prefix + "§cYou cannot promote players.");
                    }
                } else {
                    player.sendMessage(prefix + "§cYou aren't in a clan.");
                }
            } else if (args[0].equalsIgnoreCase("demote")) {
                if (args.length != 2) {
                    player.sendMessage(prefix + "§cUsage: /clan demote <player>");
                } else if (!Clan.getPlayerClan(player).equalsIgnoreCase("None")) {
                    Clan clan = Clan.clans.get(Clan.getPlayerClan(player));
                    if (clan.clanMembers.get(player.getUniqueId()) == ClanRole.LEADER) {
                        if (Clan.getPlayerClan(args[1]).equalsIgnoreCase(clan.clanName)) {
                            if (clan.demotePlayer(Core.getPlayerUUID(args[1]))) {
                                player.sendMessage(prefix + "§aThe player has been demoted.");
                            } else {
                                player.sendMessage(prefix + "§cThe player couldn't be demoted.");
                            }
                        } else {
                            player.sendMessage(prefix + "§cThe player isn't in the clan.");
                        }
                    } else {
                        player.sendMessage(prefix + "§cYou cannot demote players.");
                    }
                } else {
                    player.sendMessage(prefix + "§cYou aren't in a clan.");
                }
            } else if (args[0].equalsIgnoreCase("perks") || args[0].equalsIgnoreCase("perk")) {
                if (!Clan.getPlayerClan(player).equalsIgnoreCase("None")) {
                    Clan clan = Clan.clans.get(Clan.getPlayerClan(player));
                    player.openInventory(InventoryManager.getPerkGui(clan));
                } else {
                    player.sendMessage(prefix + "§cYou aren't in a clan.");
                }
            } else {
                StringUtil.sendCenteredMessage(player, "§f———————————————— §aClans Help §f————————————————");
                player.sendMessage("§f/clan create <clan> §eCreate a clan for $50k.");
                player.sendMessage("§f/clan show <clan/player> §eView a clan.");
                player.sendMessage("§f/clan join §eJoin a clan that you've been invited to.");
                player.sendMessage("§f/clan leave §eLeave a clan.");
                player.sendMessage("§f/clan list <page> §eView all clans.");
                player.sendMessage("§f/cc <message> §eSend a message to your clan members.");
                player.sendMessage("§f/clan invite <player> §eInvite a player to the clan. (Co-Leaders+ Only)");
                player.sendMessage("§f/clan kick <player> §eKick a player from the clan. (Co-Leaders+ Only)");
                player.sendMessage("§f/clan logs <page> §eView the clan logs. (Co-Leaders+ Only)");
                player.sendMessage("§f/clan promote <player> §ePromote a player. (Leaders Only)");
                player.sendMessage("§f/clan demote <player> §eDemote a player. (Leaders Only)");
                player.sendMessage("§f/clan disband §eDisband your clan. (Leaders Only)");
                player.sendMessage("§f/clan deposit <amount> §eDeposit money to your clan to purchase clan perks.");
                player.sendMessage("§f/clan storage §eOpen up the clan's storage. (Officers+ Only)");
                player.sendMessage("§f/clan perks §eView your clan's perks.");
                StringUtil.sendCenteredMessage(player, "§f———————————————— §aClans Help §f————————————————");
            }
        }
        return false;
    }

}
