package me.robertle.mcconquest;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class Coinflip implements Listener, CommandExecutor {

    public static HashMap<String, Coinflip> activeCoinflips = new HashMap<>();
    public static HashMap<UUID, String> pendingCoinflips = new HashMap<>();
    public static HashMap<UUID, CoinflipLoot> playerLoot = new HashMap<>();

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            if (command.getLabel().equalsIgnoreCase("coinflip")) {
                if (args.length == 1) {
                    if (args[0].equalsIgnoreCase("accept")) {
                        if (pendingCoinflips.containsKey(player.getUniqueId())) {
                            activeCoinflips.get(pendingCoinflips.get(player.getUniqueId())).accepted = true;
                            activeCoinflips.get(pendingCoinflips.get(player.getUniqueId())).sender.openInventory(activeCoinflips.get(pendingCoinflips.get(player.getUniqueId())).cfInventory);
                            activeCoinflips.get(pendingCoinflips.get(player.getUniqueId())).receiver.openInventory(activeCoinflips.get(pendingCoinflips.get(player.getUniqueId())).cfInventory);
                            pendingCoinflips.remove(player.getUniqueId());
                        } else {
                            player.sendMessage(DefaultConfig.prefix + "§cYou have no pending coinflip.");
                            return false;
                        }
                    } else if (args[0].equalsIgnoreCase("decline")) {
                        if (pendingCoinflips.containsKey(player.getUniqueId())) {
                            player.sendMessage(DefaultConfig.prefix + "§aYou've successfully declined the coinflip.");
                            activeCoinflips.get(pendingCoinflips.get(player.getUniqueId())).sender.sendMessage(DefaultConfig.prefix + "§cYour coinflip request has been declined.");
                            activeCoinflips.remove(pendingCoinflips.get(player.getUniqueId()));
                            pendingCoinflips.remove(player.getUniqueId());
                        } else {
                            player.sendMessage(DefaultConfig.prefix + "§cYou have no pending coinflip.");
                            return false;
                        }
                    } else if (args[0].equalsIgnoreCase("collect")) {
                        if (playerLoot.containsKey(player.getUniqueId())) {
                            playerLoot.get(player.getUniqueId()).claimLoot(player);
                            playerLoot.remove(player.getUniqueId());
                            player.sendMessage(DefaultConfig.prefix + "§aYou've successfully collected your winnings.");
                            return true;
                        } else {
                            player.sendMessage(DefaultConfig.prefix + "§cYou have nothing to collect.");
                            return false;
                        }
                    } else {
                        if (activeCoinflips.containsKey(player.getName() + "'s Coinflip")) {
                            player.sendMessage(DefaultConfig.prefix + "§cYou still have an outgoing coinflip.");
                            return false;
                        }
                        if (args[0].equalsIgnoreCase(player.getName())) {
                            player.sendMessage(DefaultConfig.prefix + "§cYou cannot coinflip yourself.");
                            return false;
                        }
                        if (Bukkit.getOfflinePlayer(Core.getPlayerUUID(args[0])).isOnline()) {
                            UUID receiver = Core.getPlayerUUID(args[0]);
                            pendingCoinflips.put(receiver, player.getName() + "'s Coinflip");
                            player.sendMessage(DefaultConfig.prefix + "§aCoinflip has been sent to §6" + Bukkit.getOfflinePlayer(receiver).getName());
                            player.sendMessage(DefaultConfig.prefix + "§aThey have §f30 seconds §ato accept the request.");
                            Bukkit.getOfflinePlayer(receiver).getPlayer().sendMessage(DefaultConfig.prefix + "§aYou've received a coinflip from §6" + player.getName());
                            TextComponent acceptMessage = new TextComponent(DefaultConfig.prefix + "§aClick here or type §f/cf accept §ato accept.");
                            acceptMessage.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/cf accept"));
                            Bukkit.getOfflinePlayer(receiver).getPlayer().sendMessage(acceptMessage);
                            activeCoinflips.put(player.getName() + "'s Coinflip", new Coinflip(player, Bukkit.getOfflinePlayer(receiver).getPlayer()));
                            new BukkitRunnable() {
                                public void run() {
                                    if (pendingCoinflips.containsKey(receiver)) {
                                        if (pendingCoinflips.get(receiver).equalsIgnoreCase(player.getName() + "'s Coinflip"))
                                            pendingCoinflips.remove(receiver);
                                    }
                                    if (activeCoinflips.containsKey(player.getName() + "'s Coinflip") && !activeCoinflips.get(player.getName() + "'s Coinflip").accepted && activeCoinflips.get(player.getName() + "'s Coinflip").receiver.getUniqueId() == receiver) {
                                        activeCoinflips.remove(player.getName() + "'s Coinflip");
                                    }
                                }
                            }.runTaskLater(Core.instance, 600L);
                            return true;
                        } else {
                            player.sendMessage(DefaultConfig.prefix + "§cThe player is not online.");
                            return false;
                        }
                    }
                } else {
                    StringUtil.sendCenteredMessage(player, "§f──────── §aCoinflip Help §f────────");
                    player.sendMessage("§e/cf <player> §fSend a coinflip to a player.");
                    player.sendMessage("§e/cf accept §fAccept a coinflip.");
                    player.sendMessage("§e/cf decline §fDecline a coinflip");
                    player.sendMessage("§e/cf collect §fCollect winnings from coinflips.");
                    StringUtil.sendCenteredMessage(player, "§f────────────────────────");
                }
            }
        }
        return false;
    }

    //Coinflip Gui Event
    @EventHandler
    public void coinflipGui(InventoryClickEvent e) {
        if (e.getView().getTitle().contains("'s Coinflip")) {
            Coinflip cf = activeCoinflips.get(e.getView().getTitle().substring(4));
            Player player = (Player) e.getWhoClicked();

            //Add & cancel button
            if (e.getClick() == ClickType.LEFT) {
                switch (e.getRawSlot()) {
                    case 49:
                        e.setCancelled(true);
                        cf.sender.closeInventory();
                        cf.receiver.closeInventory();
                        cf.returnItems();
                        cf.sender.sendMessage(DefaultConfig.prefix + "§6" + player.getName() + " §chas cancelled the coinflip.");
                        cf.receiver.sendMessage(DefaultConfig.prefix + "§6" + player.getName() + " §chas cancelled the coinflip.");
                        activeCoinflips.remove(cf.sender.getName() + "'s Coinflip");
                        return;
                    case 13:
                        e.setCancelled(true);
                        if (cf.sender == player) {
                            if (cf.senderConfirm) return;
                            ItemBuilder senderWager = new ItemBuilder(CustomHeadManager.heads.get("bank")).displayName("§a" + cf.sender.getName() + "'s Wager");
                            int amount = Integer.parseInt(e.getInventory().getItem(1).getLore().get(0).substring(13));
                            amount += 1000;
                            if (!Core.econ.has(cf.sender, amount)) return;
                            senderWager.lore("§eAmount: §f$" + amount);
                            e.getInventory().setItem(1, senderWager.asItemStack());
                            if (cf.receiverConfirm) {
                                cf.receiverConfirm = false;
                                cf.cfInventory.setItem(5, new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).displayName("§aConfirm").asItemStack());
                                cf.updateInventories();
                                return;
                            }
                        } else if (cf.receiver == player) {
                            if (cf.receiverConfirm) return;
                            ItemBuilder receiverWager = new ItemBuilder(CustomHeadManager.heads.get("bank")).displayName("§a" + cf.receiver.getName() + "'s Wager");
                            int amount = Integer.parseInt(e.getInventory().getItem(7).getLore().get(0).substring(13));
                            amount += 1000;
                            if (!Core.econ.has(cf.receiver, amount)) return;
                            receiverWager.lore("§eAmount: §f$" + amount);
                            e.getInventory().setItem(7, receiverWager.asItemStack());
                            if (cf.senderConfirm) {
                                cf.senderConfirm = false;
                                cf.cfInventory.setItem(3, new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).displayName("§aConfirm").asItemStack());
                                cf.updateInventories();
                                return;
                            }
                        }
                        cf.updateInventories();
                        return;
                    case 22:
                        e.setCancelled(true);
                        if (cf.sender == player) {
                            if (cf.senderConfirm) return;
                            ItemBuilder senderWager = new ItemBuilder(CustomHeadManager.heads.get("bank")).displayName("§a" + cf.sender.getName() + "'s Wager");
                            int amount = Integer.parseInt(e.getInventory().getItem(1).getLore().get(0).substring(13));
                            amount += 10000;
                            if (!Core.econ.has(cf.sender, amount)) return;
                            senderWager.lore("§eAmount: §f$" + amount);
                            e.getInventory().setItem(1, senderWager.asItemStack());
                            if (cf.receiverConfirm) {
                                cf.receiverConfirm = false;
                                cf.cfInventory.setItem(5, new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).displayName("§aConfirm").asItemStack());
                                cf.updateInventories();
                                return;
                            }
                        } else if (cf.receiver == player) {
                            if (cf.receiverConfirm) return;
                            ItemBuilder receiverWager = new ItemBuilder(CustomHeadManager.heads.get("bank")).displayName("§a" + cf.receiver.getName() + "'s Wager");
                            int amount = Integer.parseInt(e.getInventory().getItem(7).getLore().get(0).substring(13));
                            amount += 10000;
                            if (!Core.econ.has(cf.receiver, amount)) return;
                            receiverWager.lore("§eAmount: §f$" + amount);
                            e.getInventory().setItem(7, receiverWager.asItemStack());
                            if (cf.senderConfirm) {
                                cf.senderConfirm = false;
                                cf.cfInventory.setItem(3, new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).displayName("§aConfirm").asItemStack());
                                cf.updateInventories();
                                return;
                            }
                        }
                        cf.updateInventories();
                        return;
                    case 31:
                        e.setCancelled(true);
                        if (cf.sender == player) {
                            if (cf.senderConfirm) return;
                            ItemBuilder senderWager = new ItemBuilder(CustomHeadManager.heads.get("bank")).displayName("§a" + cf.sender.getName() + "'s Wager");
                            int amount = Integer.parseInt(e.getInventory().getItem(1).getLore().get(0).substring(13));
                            amount += 100000;
                            if (!Core.econ.has(cf.sender, amount)) return;
                            senderWager.lore("§eAmount: §f$" + amount);
                            e.getInventory().setItem(1, senderWager.asItemStack());
                            if (cf.receiverConfirm) {
                                cf.receiverConfirm = false;
                                cf.cfInventory.setItem(5, new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).displayName("§aConfirm").asItemStack());
                                cf.updateInventories();
                                return;
                            }
                        } else if (cf.receiver == player) {
                            if (cf.receiverConfirm) return;
                            ItemBuilder receiverWager = new ItemBuilder(CustomHeadManager.heads.get("bank")).displayName("§a" + cf.receiver.getName() + "'s Wager");
                            int amount = Integer.parseInt(e.getInventory().getItem(7).getLore().get(0).substring(13));
                            amount += 100000;
                            if (!Core.econ.has(cf.receiver, amount)) return;
                            receiverWager.lore("§eAmount: §f$" + amount);
                            e.getInventory().setItem(7, receiverWager.asItemStack());
                            if (cf.senderConfirm) {
                                cf.senderConfirm = false;
                                cf.cfInventory.setItem(3, new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).displayName("§aConfirm").asItemStack());
                                cf.updateInventories();
                                return;
                            }
                        }
                        cf.updateInventories();
                        return;
                    case 40:
                        e.setCancelled(true);
                        if (cf.sender == player) {
                            if (cf.senderConfirm) return;
                            ItemBuilder senderWager = new ItemBuilder(CustomHeadManager.heads.get("bank")).displayName("§a" + cf.sender.getName() + "'s Wager");
                            int amount = Integer.parseInt(e.getInventory().getItem(1).getLore().get(0).substring(13));
                            amount += 1000000;
                            if (!Core.econ.has(cf.sender, amount)) return;
                            senderWager.lore("§eAmount: §f$" + amount);
                            e.getInventory().setItem(1, senderWager.asItemStack());
                            if (cf.receiverConfirm) {
                                cf.receiverConfirm = false;
                                cf.cfInventory.setItem(5, new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).displayName("§aConfirm").asItemStack());
                                cf.updateInventories();
                                return;
                            }
                        } else if (cf.receiver == player) {
                            if (cf.receiverConfirm) return;
                            ItemBuilder receiverWager = new ItemBuilder(CustomHeadManager.heads.get("bank")).displayName("§a" + cf.receiver.getName() + "'s Wager");
                            int amount = Integer.parseInt(e.getInventory().getItem(7).getLore().get(0).substring(13));
                            amount += 1000000;
                            if (!Core.econ.has(cf.receiver, amount)) return;
                            receiverWager.lore("§eAmount: §f$" + amount);
                            e.getInventory().setItem(7, receiverWager.asItemStack());
                            if (cf.senderConfirm) {
                                cf.senderConfirm = false;
                                cf.cfInventory.setItem(3, new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).displayName("§aConfirm").asItemStack());
                                cf.updateInventories();
                                return;
                            }
                        }
                        cf.updateInventories();
                        return;
                }
            }

            //Subtract button
            if (e.getClick() == ClickType.RIGHT) {
                switch (e.getRawSlot()) {
                    case 13:
                        e.setCancelled(true);
                        if (cf.sender == player) {
                            if (cf.senderConfirm) return;
                            ItemBuilder senderWager = new ItemBuilder(CustomHeadManager.heads.get("bank")).displayName("§a" + cf.sender.getName() + "'s Wager");
                            int amount = Integer.parseInt(e.getInventory().getItem(1).getLore().get(0).substring(13));
                            amount -= 1000;
                            if (amount < 0) amount = 0;
                            senderWager.lore("§eAmount: §f$" + amount);
                            e.getInventory().setItem(1, senderWager.asItemStack());
                            if (cf.receiverConfirm) {
                                cf.receiverConfirm = false;
                                cf.cfInventory.setItem(5, new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).displayName("§aConfirm").asItemStack());
                                cf.updateInventories();
                                return;
                            }
                        } else if (cf.receiver == player) {
                            if (cf.receiverConfirm) return;
                            ItemBuilder receiverWager = new ItemBuilder(CustomHeadManager.heads.get("bank")).displayName("§a" + cf.receiver.getName() + "'s Wager");
                            int amount = Integer.parseInt(e.getInventory().getItem(7).getLore().get(0).substring(13));
                            amount -= 1000;
                            if (amount < 0) amount = 0;
                            receiverWager.lore("§eAmount: §f$" + amount);
                            e.getInventory().setItem(7, receiverWager.asItemStack());
                            if (cf.senderConfirm) {
                                cf.senderConfirm = false;
                                cf.cfInventory.setItem(3, new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).displayName("§aConfirm").asItemStack());
                                cf.updateInventories();
                                return;
                            }
                        }
                        cf.updateInventories();
                        return;
                    case 22:
                        e.setCancelled(true);
                        if (cf.sender == player) {
                            if (cf.senderConfirm) return;
                            ItemBuilder senderWager = new ItemBuilder(CustomHeadManager.heads.get("bank")).displayName("§a" + cf.sender.getName() + "'s Wager");
                            int amount = Integer.parseInt(e.getInventory().getItem(1).getLore().get(0).substring(13));
                            amount -= 10000;
                            if (amount < 0) amount = 0;
                            senderWager.lore("§eAmount: §f$" + amount);
                            e.getInventory().setItem(1, senderWager.asItemStack());
                            if (cf.receiverConfirm) {
                                cf.receiverConfirm = false;
                                cf.cfInventory.setItem(5, new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).displayName("§aConfirm").asItemStack());
                                cf.updateInventories();
                                return;
                            }
                        } else if (cf.receiver == player) {
                            if (cf.receiverConfirm) return;
                            ItemBuilder receiverWager = new ItemBuilder(CustomHeadManager.heads.get("bank")).displayName("§a" + cf.receiver.getName() + "'s Wager");
                            int amount = Integer.parseInt(e.getInventory().getItem(7).getLore().get(0).substring(13));
                            amount -= 10000;
                            if (amount < 0) amount = 0;
                            receiverWager.lore("§eAmount: §f$" + amount);
                            e.getInventory().setItem(7, receiverWager.asItemStack());
                            if (cf.senderConfirm) {
                                cf.senderConfirm = false;
                                cf.cfInventory.setItem(3, new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).displayName("§aConfirm").asItemStack());
                                cf.updateInventories();
                                return;
                            }
                        }
                        cf.updateInventories();
                        return;
                    case 31:
                        e.setCancelled(true);
                        if (cf.sender == player) {
                            if (cf.senderConfirm) return;
                            ItemBuilder senderWager = new ItemBuilder(CustomHeadManager.heads.get("bank")).displayName("§a" + cf.sender.getName() + "'s Wager");
                            int amount = Integer.parseInt(e.getInventory().getItem(1).getLore().get(0).substring(13));
                            amount -= 100000;
                            if (amount < 0) amount = 0;
                            senderWager.lore("§eAmount: §f$" + amount);
                            e.getInventory().setItem(1, senderWager.asItemStack());
                            if (cf.receiverConfirm) {
                                cf.receiverConfirm = false;
                                cf.cfInventory.setItem(5, new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).displayName("§aConfirm").asItemStack());
                                cf.updateInventories();
                                return;
                            }
                        } else if (cf.receiver == player) {
                            if (cf.receiverConfirm) return;
                            ItemBuilder receiverWager = new ItemBuilder(CustomHeadManager.heads.get("bank")).displayName("§a" + cf.receiver.getName() + "'s Wager");
                            int amount = Integer.parseInt(e.getInventory().getItem(7).getLore().get(0).substring(13));
                            amount -= 100000;
                            if (amount < 0) amount = 0;
                            receiverWager.lore("§eAmount: §f$" + amount);
                            e.getInventory().setItem(7, receiverWager.asItemStack());
                            if (cf.senderConfirm) {
                                cf.senderConfirm = false;
                                cf.cfInventory.setItem(3, new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).displayName("§aConfirm").asItemStack());
                                cf.updateInventories();
                                return;
                            }
                        }
                        cf.updateInventories();
                        return;
                    case 40:
                        e.setCancelled(true);
                        if (cf.sender == player) {
                            if (cf.senderConfirm) return;
                            ItemBuilder senderWager = new ItemBuilder(CustomHeadManager.heads.get("bank")).displayName("§a" + cf.sender.getName() + "'s Wager");
                            int amount = Integer.parseInt(e.getInventory().getItem(1).getLore().get(0).substring(13));
                            amount -= 1000000;
                            if (amount < 0) amount = 0;
                            senderWager.lore("§eAmount: §f$" + amount);
                            e.getInventory().setItem(1, senderWager.asItemStack());
                            if (cf.receiverConfirm) {
                                cf.receiverConfirm = false;
                                cf.cfInventory.setItem(5, new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).displayName("§aConfirm").asItemStack());
                                cf.updateInventories();
                                return;
                            }
                        } else if (cf.receiver == player) {
                            if (cf.receiverConfirm) return;
                            ItemBuilder receiverWager = new ItemBuilder(CustomHeadManager.heads.get("bank")).displayName("§a" + cf.receiver.getName() + "'s Wager");
                            int amount = Integer.parseInt(e.getInventory().getItem(7).getLore().get(0).substring(13));
                            amount -= 1000000;
                            if (amount < 0) amount = 0;
                            receiverWager.lore("§eAmount: §f$" + amount);
                            e.getInventory().setItem(7, receiverWager.asItemStack());
                            if (cf.senderConfirm) {
                                cf.senderConfirm = false;
                                cf.cfInventory.setItem(3, new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).displayName("§aConfirm").asItemStack());
                                cf.updateInventories();
                                return;
                            }
                        }
                        cf.updateInventories();
                        return;
                }
            }

            //Confirm button
            if (cf.sender == player) {
                if (e.getRawSlot() == 3) {
                    e.setCancelled(true);
                    if (e.getCurrentItem().getType() == Material.GRAY_STAINED_GLASS_PANE) {
                        cf.senderConfirm = true;
                        e.setCurrentItem(new ItemBuilder(Material.GREEN_STAINED_GLASS_PANE).displayName("§aConfirm").asItemStack());
                        cf.updateInventories();
                        cf.confirmCoinflip();
                        return;
                    } else {
                        cf.senderConfirm = false;
                        e.setCurrentItem(new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).displayName("§aConfirm").asItemStack());
                        cf.updateInventories();
                        return;
                    }
                }
                if (cf.senderConfirm) {
                    e.setCancelled(true);
                    return;
                }
                if (cf.isSenderSlot(e.getRawSlot()) || e.getRawSlot() > 53) {
                    if (e.getClick() == ClickType.LEFT || e.getClick() == ClickType.RIGHT) {
                        if (cf.receiverConfirm) {
                            cf.receiverConfirm = false;
                            cf.cfInventory.setItem(5, new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).displayName("§aConfirm").asItemStack());
                            cf.updateInventories();
                            return;
                        }
                    } else {
                        e.setCancelled(true);
                        return;
                    }
                } else {
                    e.setCancelled(true);
                    return;
                }
            } else if (cf.receiver == player) {
                if (e.getRawSlot() == 5) {
                    e.setCancelled(true);
                    if (e.getCurrentItem().getType() == Material.GRAY_STAINED_GLASS_PANE) {
                        cf.receiverConfirm = true;
                        e.setCurrentItem(new ItemBuilder(Material.GREEN_STAINED_GLASS_PANE).displayName("§aConfirm").asItemStack());
                        cf.updateInventories();
                        cf.confirmCoinflip();
                        return;
                    } else {
                        cf.receiverConfirm = false;
                        e.setCurrentItem(new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).displayName("§aConfirm").asItemStack());
                        cf.updateInventories();
                        return;
                    }
                }
                if (cf.receiverConfirm) {
                    e.setCancelled(true);
                    return;
                }
                if (cf.isReceiverSlot(e.getRawSlot()) || e.getRawSlot() > 53) {
                    if (e.getClick() == ClickType.LEFT || e.getClick() == ClickType.RIGHT) {
                        if (cf.senderConfirm) {
                            cf.senderConfirm = false;
                            cf.cfInventory.setItem(3, new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).displayName("§aConfirm").asItemStack());
                            cf.updateInventories();
                            return;
                        }
                    } else {
                        e.setCancelled(true);
                        return;
                    }
                } else {
                    e.setCancelled(true);
                    return;
                }
            }
        }
    }

    @EventHandler
    public void closeCoinflipGui(InventoryCloseEvent e) {
        if (e.getView().getTitle().contains("'s Coinflip")) {
            if (e.getReason() != InventoryCloseEvent.Reason.PLUGIN) {
                new BukkitRunnable() {
                    public void run() {
                        e.getPlayer().openInventory(e.getInventory());
                    }
                }.runTaskLater(Core.instance, 1L);
            }
            if (e.getReason() == InventoryCloseEvent.Reason.DISCONNECT) {
                Coinflip cf = activeCoinflips.get(e.getView().getTitle().substring(4));
                if (cf.sender.isOnline()) cf.sender.closeInventory();
                if (cf.receiver.isOnline()) cf.receiver.closeInventory();
                cf.returnItems();
                if (cf.sender.isOnline()) cf.sender.sendMessage(DefaultConfig.prefix + "§6" + e.getPlayer().getName() + " §chas cancelled the coinflip.");
                if (cf.receiver.isOnline()) cf.receiver.sendMessage(DefaultConfig.prefix + "§6" + e.getPlayer().getName() + " §chas cancelled the coinflip.");
                activeCoinflips.remove(cf.sender.getName() + "'s Coinflip");
            }
        }
    }

    //Coinflip Animation
    public static void playerCoinflipAnimation(Player winner, Player loser) {
        Inventory inv = Bukkit.createInventory(null, 9, "§a§lCoinflip");

        for (int j = 0 ; j < 9 ; j++) {
            if (j != 4) {
                inv.setItem(j, new ItemBuilder(Material.WHITE_STAINED_GLASS_PANE).displayName("§8§lMC§c§lConquest").asItemStack());
            }
        }

        ItemBuilder winnerHead = new ItemBuilder(CustomHeadManager.getPlayerHead(winner));
        winnerHead.displayName("§6§l" + winner.getName());
        ItemBuilder loserHead = new ItemBuilder(CustomHeadManager.getPlayerHead(loser));
        loserHead.displayName("§6§l" + loser.getName());

        winner.openInventory(inv);
        loser.openInventory(inv);
        new BukkitRunnable() {
            int i = 1;
            public void run() {
                switch(i) {
                    case 1:
                        if (Core.chance(50)) inv.setItem(4, winnerHead.asItemStack());
                        else inv.setItem(4, loserHead.asItemStack());
                        break;
                    case 2:
                        inv.setItem(4, winnerHead.asItemStack());
                        winner.playSound(winner.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1.0f, 1.0f);
                        loser.playSound(winner.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1.0f, 1.0f);
                        break;
                    case 3:
                        inv.setItem(4, loserHead.asItemStack());
                        winner.playSound(winner.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1.0f, 1.0f);
                        loser.playSound(winner.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1.0f, 1.0f);
                        break;
                    case 4:
                        inv.setItem(4, winnerHead.asItemStack());
                        winner.playSound(winner.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1.0f, 1.0f);
                        loser.playSound(winner.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1.0f, 1.0f);
                        break;
                    case 5:
                        inv.setItem(4, loserHead.asItemStack());
                        winner.playSound(winner.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1.0f, 1.0f);
                        loser.playSound(winner.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1.0f, 1.0f);
                        break;
                    case 6:
                        inv.setItem(4, winnerHead.asItemStack());
                        winner.playSound(winner.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1.0f, 1.0f);
                        loser.playSound(winner.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1.0f, 1.0f);
                        break;
                    case 7:
                        inv.setItem(4, loserHead.asItemStack());
                        winner.playSound(winner.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1.0f, 1.0f);
                        loser.playSound(winner.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1.0f, 1.0f);
                        break;
                    case 8:
                        inv.setItem(4, winnerHead.asItemStack());
                        winner.playSound(winner.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1.0f, 1.0f);
                        loser.playSound(winner.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1.0f, 1.0f);
                        break;
                    case 9:
                        inv.setItem(4, loserHead.asItemStack());
                        winner.playSound(winner.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1.0f, 1.0f);
                        loser.playSound(winner.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1.0f, 1.0f);
                        break;
                    case 10:
                        inv.setItem(4, winnerHead.asItemStack());
                        winner.playSound(winner.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1.0f, 1.0f);
                        loser.playSound(winner.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1.0f, 1.0f);
                        break;
                    case 11:
                        inv.setItem(4, loserHead.asItemStack());
                        winner.playSound(winner.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1.0f, 1.0f);
                        loser.playSound(winner.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1.0f, 1.0f);
                        break;
                    case 12:
                        inv.setItem(4, winnerHead.asItemStack());
                        winner.playSound(winner.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1.0f, 1.0f);
                        loser.playSound(winner.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1.0f, 1.0f);
                        break;
                    case 13:
                        inv.setItem(4, loserHead.asItemStack());
                        winner.playSound(winner.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1.0f, 1.0f);
                        loser.playSound(winner.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1.0f, 1.0f);
                        break;
                    case 14:
                        inv.setItem(4, winnerHead.asItemStack());
                        winner.playSound(winner.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1.0f, 1.0f);
                        loser.playSound(winner.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1.0f, 1.0f);
                        break;
                    case 15:
                        inv.setItem(4, loserHead.asItemStack());
                        winner.playSound(winner.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1.0f, 1.0f);
                        loser.playSound(winner.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1.0f, 1.0f);
                        break;
                    case 17:
                        inv.setItem(4, winnerHead.asItemStack());
                        winner.playSound(winner.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1.0f, 1.0f);
                        loser.playSound(winner.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1.0f, 1.0f);
                        break;
                    case 19:
                        inv.setItem(4, loserHead.asItemStack());
                        winner.playSound(winner.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1.0f, 1.0f);
                        loser.playSound(winner.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1.0f, 1.0f);
                        break;
                    case 21:
                        inv.setItem(4, winnerHead.asItemStack());
                        winner.playSound(winner.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1.0f, 1.0f);
                        loser.playSound(winner.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1.0f, 1.0f);
                        break;
                    case 24:
                        inv.setItem(4, loserHead.asItemStack());
                        winner.playSound(winner.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1.0f, 1.0f);
                        loser.playSound(winner.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1.0f, 1.0f);
                        break;
                    case 27:
                        inv.setItem(4, winnerHead.asItemStack());
                        winner.playSound(winner.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1.0f, 1.0f);
                        loser.playSound(winner.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1.0f, 1.0f);
                        break;
                    case 30:
                        inv.setItem(4, loserHead.asItemStack());
                        winner.playSound(winner.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1.0f, 1.0f);
                        loser.playSound(winner.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1.0f, 1.0f);
                        break;
                    case 33:
                        inv.setItem(4, winnerHead.asItemStack());
                        winner.playSound(winner.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1.0f, 1.0f);
                        loser.playSound(winner.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1.0f, 1.0f);
                        break;
                    case 36:
                        inv.setItem(4, loserHead.asItemStack());
                        winner.playSound(winner.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1.0f, 1.0f);
                        loser.playSound(winner.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1.0f, 1.0f);
                        break;
                    case 39:
                        inv.setItem(4, winnerHead.asItemStack());
                        winner.playSound(winner.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1.0f, 1.0f);
                        loser.playSound(winner.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1.0f, 1.0f);
                        break;
                    case 42:
                        inv.setItem(4, loserHead.asItemStack());
                        winner.playSound(winner.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1.0f, 1.0f);
                        loser.playSound(winner.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1.0f, 1.0f);
                        break;
                    case 45:
                        inv.setItem(4, winnerHead.asItemStack());
                        winner.playSound(winner.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1.0f, 1.0f);
                        loser.playSound(winner.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1.0f, 1.0f);
                        break;
                    case 46:
                        inv.setItem(4, winnerHead.displayName("§e§lWinner: §6" + winner.getName()).asItemStack());
                        winner.sendMessage(DefaultConfig.prefix + "§aYou've won the coinflip against §6" + loser.getName());
                        winner.sendMessage(DefaultConfig.prefix + "§aDo §f/cf collect §ato collect your winnings.");
                        loser.sendMessage(DefaultConfig.prefix + "§cYou've lost the coinflip against §6" + winner.getName());
                        this.cancel();
                        break;
                }
                winner.updateInventory();
                loser.updateInventory();
                i++;
            }
        }.runTaskTimer(Core.instance, 0L, 3L);
    }

    @EventHandler
    public void coinflipAnimation(InventoryClickEvent e) {
        if (e.getView().getTitle().equalsIgnoreCase("§a§lCoinflip")) e.setCancelled(true);
    }

    //Coinflip Object
    public Player sender;
    public boolean senderConfirm;

    public Player receiver;
    public boolean receiverConfirm;

    public boolean accepted = false;

    public Inventory cfInventory;
    public List<ItemStack> pot = new ArrayList<>();
    public int moneyPot = 0;

    public Coinflip(){}

    public Coinflip(Player sender, Player receiver) {
        this.sender = sender;
        this.receiver = receiver;
        cfInventory = Bukkit.createInventory(null, 54, "§a§l" + sender.getName() + "'s Coinflip");

        ItemBuilder senderWager = new ItemBuilder(CustomHeadManager.heads.get("bank")).displayName("§a" + sender.getName() + "'s Wager");
        senderWager.lore("§eAmount: §f$0");

        ItemBuilder senderHead = new ItemBuilder(CustomHeadManager.getPlayerHead(sender)).displayName("§f"+sender.getName());

        ItemBuilder receiverWager = new ItemBuilder(CustomHeadManager.heads.get("bank")).displayName("§a" + receiver.getName() + "'s Wager");
        receiverWager.lore("§eAmount: §f$0");

        ItemBuilder receiverHead = new ItemBuilder(CustomHeadManager.getPlayerHead(receiver)).displayName("§f"+receiver.getName());

        cfInventory.setItem(0, senderHead.asItemStack());
        cfInventory.setItem(1, senderWager.asItemStack());
        cfInventory.setItem(2, new ItemBuilder(Material.WHITE_STAINED_GLASS_PANE).displayName("§8§lMC§c§lConquest").asItemStack());
        cfInventory.setItem(3, new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).displayName("§aConfirm").asItemStack());
        cfInventory.setItem(4, new ItemBuilder(Material.WHITE_STAINED_GLASS_PANE).displayName("§8§lMC§c§lConquest").asItemStack());
        cfInventory.setItem(5, new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).displayName("§aConfirm").asItemStack());
        cfInventory.setItem(6, new ItemBuilder(Material.WHITE_STAINED_GLASS_PANE).displayName("§8§lMC§c§lConquest").asItemStack());
        cfInventory.setItem(7, receiverWager.asItemStack());
        cfInventory.setItem(8, receiverHead.asItemStack());

        cfInventory.setItem(13, new ItemBuilder(CustomHeadManager.heads.get("money")).displayName("§a$1000").lore("§eLeft click to add money", "§eRight click to subtract money").asItemStack());
        cfInventory.setItem(22, new ItemBuilder(CustomHeadManager.heads.get("money")).displayName("§a$10,000").lore("§eLeft click to add money", "§eRight click to subtract money").asItemStack());
        cfInventory.setItem(31, new ItemBuilder(CustomHeadManager.heads.get("money")).displayName("§a$100,000").lore("§eLeft click to add money", "§eRight click to subtract money").asItemStack());
        cfInventory.setItem(40, new ItemBuilder(CustomHeadManager.heads.get("money")).displayName("§a$1,000,000").lore("§eLeft click to add money", "§eRight click to subtract money").asItemStack());

        cfInventory.setItem(49, new ItemBuilder(Material.RED_STAINED_GLASS_PANE).displayName("§cCancel").asItemStack());
    }

    public void addItemsToPot() {
        for (int i = 0; i < 54; i++) {
            if (isSenderSlot(i) || isReceiverSlot(i)) {
                pot.add(cfInventory.getItem(i));
            }
        }
    }

    public void addMoneyToPot() {
        int senderWager = Integer.parseInt(ItemHelper.getLore(cfInventory.getItem(1)).get(0).substring(13));
        int receiverWager = Integer.parseInt(ItemHelper.getLore(cfInventory.getItem(7)).get(0).substring(13));
        moneyPot = (int) ((senderWager + receiverWager) * .9);
    }

    public void confirmCoinflip() {
        if (senderConfirm && receiverConfirm) {
            sender.closeInventory();
            receiver.closeInventory();
            addItemsToPot();
            addMoneyToPot();
            CoinflipLoot cfLoot = new CoinflipLoot();
            cfLoot.money += moneyPot;
            for (ItemStack i : pot) {
                if (i != null) cfLoot.items.add(i);
            }
            if (Core.chance(50)) {
                playerCoinflipAnimation(sender, receiver);
                playerLoot.put(sender.getUniqueId(), cfLoot);
            } else {
                playerCoinflipAnimation(receiver, sender);
                playerLoot.put(receiver.getUniqueId(), cfLoot);
            }
            activeCoinflips.remove(sender.getName()+"'s Coinflip");
        }
    }

    public void returnItems() {
        for (int i = 0; i < 54; i++) {
            if (sender.isOnline())
            if (isSenderSlot(i)) {
                if (cfInventory.getItem(i) != null) sender.getInventory().addItem(cfInventory.getItem(i));
            }
            if (receiver.isOnline())
            if (isReceiverSlot(i)) {
                if (cfInventory.getItem(i) != null) receiver.getInventory().addItem(cfInventory.getItem(i));
            }
        }
    }

    public void updateInventories() {
        receiver.updateInventory();
        sender.updateInventory();
    }

    public boolean isSenderSlot(int slot) {
        if (slot >= 9 && slot <= 12) return true;
        if (slot >= 18 && slot <= 21) return true;
        if (slot >= 27 && slot <= 30) return true;
        if (slot >= 36 && slot <= 39) return true;
        if (slot >= 45 && slot <= 48) return true;
        return false;
    }

    public boolean isReceiverSlot(int slot) {
        if (slot >= 14 && slot <= 17) return true;
        if (slot >= 23 && slot <= 26) return true;
        if (slot >= 32 && slot <= 35) return true;
        if (slot >= 41 && slot <= 44) return true;
        if (slot >= 50 && slot <= 53) return true;
        return false;
    }

}
