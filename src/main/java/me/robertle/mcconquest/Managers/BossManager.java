package me.robertle.mcconquest.Managers;

import me.robertle.mcconquest.Boss;
import me.robertle.mcconquest.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class BossManager implements Listener, CommandExecutor {

    public static HashMap<UUID, Boss> activeBosses = new HashMap<>();

    public static void spawnBoss(MCCBoss mccboss, Location location) {
        if (mccboss == MCCBoss.ZEUS) {
            location.getChunk().setForceLoaded(true);
            location.getChunk().load();
            Zombie zombie = (Zombie) location.getWorld().spawnEntity(location, EntityType.ZOMBIE);
            zombie.setBaby(false);
            zombie.setCustomName("§6§lZeus");
            zombie.setCustomNameVisible(true);
            zombie.setCanPickupItems(false);
            zombie.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 1));
            zombie.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, Integer.MAX_VALUE, 127));
            zombie.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, Integer.MAX_VALUE, 127));
            zombie.setShouldBurnInDay(false);
            zombie.setRemoveWhenFarAway(false);
            zombie.getEquipment().setHelmet(new ItemStack(Material.DIAMOND_HELMET));
            zombie.getEquipment().setChestplate(null);
            zombie.getEquipment().setLeggings(null);
            zombie.getEquipment().setBoots(null);
            zombie.getEquipment().setItemInMainHand(new ItemBuilder(Material.DIAMOND_SWORD).unsafeEnchant(Enchantment.DAMAGE_ALL, 6).asItemStack());
            activeBosses.put(zombie.getUniqueId(), new Boss(MCCBoss.ZEUS, 10000));

            location.getWorld().strikeLightningEffect(location);
            location.getWorld().spawnParticle(Particle.DRAGON_BREATH, location.add(0, 1, 0), 100, 2, 2, 2);

            Boss boss = activeBosses.get(zombie.getUniqueId());

            new BukkitRunnable() {
                int minion = 7;
                boolean enraged = false;
                boolean safety1 = false;
                boolean safety2 = false;

                public void run() {
                    if (!zombie.isDead()) {
                        List<Player> players = new ArrayList<>();
                        for (Player p : zombie.getLocation().getNearbyPlayers(32, 32, 32)) players.add(p);
                        if (boss.bar != null) boss.bar.removeAll();
                        List<UUID> sortedList = boss.getSortedList();
                        if (sortedList.isEmpty())
                            boss.bar = Bukkit.createBossBar("§6§lZeus §f> §c" + boss.health + "❤ §f> §eHighest Damager: §6None", BarColor.RED, BarStyle.SOLID);
                        else
                            boss.bar = Bukkit.createBossBar("§6§lZeus §f> §c" + boss.health + "❤ §f> §eHighest Damager: §6" + Bukkit.getOfflinePlayer(sortedList.get(0)).getName(), BarColor.RED, BarStyle.SOLID);
                        for (Player p : players) boss.bar.addPlayer(p);

                        if (minion == 7 && boss.health < 9500) {
                            for (Player p : players) {
                                p.sendMessage("§6§lZeus §chas summoned minions.");
                                spawnBossZombie(p.getLocation());
                                spawnBossZombie(p.getLocation());
                                spawnBossHusk(p.getLocation());
                            }
                            minion--;
                        } else if (minion == 6 && boss.health < 8000) {
                            for (Player p : players) {
                                p.sendMessage("§6§lZeus §chas summoned minions.");
                                spawnBossZombie(p.getLocation());
                                spawnBossZombie(p.getLocation());
                                spawnBossHusk(p.getLocation());
                            }
                            minion--;
                        } else if (minion == 5 && boss.health < 6500) {
                            for (Player p : players) {
                                p.sendMessage("§6§lZeus §chas summoned minions.");
                                spawnBossZombie(p.getLocation());
                                spawnBossZombie(p.getLocation());
                                spawnBossHusk(p.getLocation());
                                spawnBossHusk(p.getLocation());
                            }
                            minion--;
                        } else if (minion == 4 && boss.health < 5000) {
                            for (Player p : players) {
                                p.sendMessage("§6§lZeus §chas summoned minions.");
                                spawnBossZombie(p.getLocation());
                                spawnBossZombie(p.getLocation());
                                spawnBossHusk(p.getLocation());
                                spawnBossHusk(p.getLocation());
                            }
                            minion--;
                        } else if (minion == 3 && boss.health < 4500) {
                            for (Player p : players) {
                                p.sendMessage("§6§lZeus §chas summoned minions.");
                                spawnBossZombie(p.getLocation());
                                spawnBossZombie(p.getLocation());
                                spawnBossHusk(p.getLocation());
                                spawnBossHusk(p.getLocation());
                            }
                            minion--;
                        } else if (minion == 2 && boss.health < 3000) {
                            for (Player p : players) {
                                p.sendMessage("§6§lZeus §chas summoned minions.");
                                spawnBossZombie(p.getLocation());
                                spawnBossZombie(p.getLocation());
                                spawnBossHusk(p.getLocation());
                                spawnBossHusk(p.getLocation());
                                spawnBossRavager(p.getLocation());
                            }
                            minion--;
                        } else if (minion == 1 && boss.health < 1500) {
                            for (Player p : players) {
                                p.sendMessage("§6§lZeus §chas summoned minions.");
                                spawnBossZombie(p.getLocation());
                                spawnBossZombie(p.getLocation());
                                spawnBossHusk(p.getLocation());
                                spawnBossHusk(p.getLocation());
                                spawnBossRavager(p.getLocation());
                            }
                            minion--;
                        } else if (minion == 0 && boss.health < 500) {
                            for (Player p : players) {
                                p.sendMessage("§6§lZeus §chas summoned minions.");
                                spawnBossZombie(p.getLocation());
                                spawnBossZombie(p.getLocation());
                                spawnBossHusk(p.getLocation());
                                spawnBossHusk(p.getLocation());
                                spawnBossRavager(p.getLocation());
                            }
                            minion--;
                        }

                        if (!enraged) {
                            if (boss.health < 2500) {
                                for (Player p : players) {
                                    p.setVelocity(p.getLocation().getDirection().normalize().multiply(-2.5));
                                    p.sendMessage("§6§lZeus §cis now enraged!");
                                }
                                zombie.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 2));
                                zombie.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, Integer.MAX_VALUE, 1));
                                zombie.getWorld().spawnParticle(Particle.EXPLOSION_LARGE, zombie.getLocation().add(0, 1, 0), 10, 1, 1, 1);
                                enraged = true;
                            }
                        } else {
                            zombie.getWorld().spawnParticle(Particle.VILLAGER_ANGRY, zombie.getLocation().add(0, 2, 0), 5, 1, 1, 1);
                        }

                        if (!safety1) {
                            if (boss.health < 1500) {
                                for (Player p : players) {
                                    p.setVelocity(p.getLocation().getDirection().normalize().multiply(-2.5));
                                }
                                zombie.getWorld().spawnParticle(Particle.EXPLOSION_LARGE, zombie.getLocation().add(0, 1, 0), 10, 1, 1, 1);
                                safety1 = true;
                            }
                        }

                        if (!safety2) {
                            if (boss.health < 500) {
                                for (Player p : players) {
                                    p.setVelocity(p.getLocation().getDirection().normalize().multiply(-2.5));
                                }
                                zombie.getWorld().spawnParticle(Particle.EXPLOSION_LARGE, zombie.getLocation().add(0, 1, 0), 10, 1, 1, 1);
                                safety2 = true;
                            }
                        }
                    } else {
                        this.cancel();
                    }
                }
            }.runTaskTimer(Core.instance, 0L, 20L);

            new BukkitRunnable() {
                public void run() {
                    if (!zombie.isDead()) {
                        List<Player> players = new ArrayList<>();
                        for (Player p : zombie.getLocation().getNearbyPlayers(16, 16, 16)) players.add(p);
                        if (boss.health < 2500 ? Core.chance(30) : Core.chance(20)) {
                            for (Player p : players) {
                                p.sendMessage("§6§lZeus §ccalled down thunder.");
                                p.damage(20, zombie);
                                p.getWorld().strikeLightning(p.getLocation());
                                p.setFireTicks(200);
                            }
                        }
                        if (boss.health < 2500 ? Core.chance(15) : Core.chance(10)) {
                            zombie.getWorld().spawnParticle(Particle.END_ROD, zombie.getLocation().add(0, 2, 0), 100, 2, 2, 2);
                            for (Player p : players) {
                                p.sendMessage("§6§lZeus §cblinded the sky.");
                                p.getWorld().spawnParticle(Particle.END_ROD, p.getLocation().add(0, 2, 0), 100, 1, 0, 1);
                                CustomEnchantManager.addPotionEffect(p, new PotionEffect(PotionEffectType.BLINDNESS, 200, 0));
                            }
                        }
                    } else {
                        this.cancel();
                    }
                }
            }.runTaskTimer(Core.instance, 100L, 200L);
        } else if (mccboss == MCCBoss.HADES) {
            Zombie zombie = (Zombie) location.getWorld().spawnEntity(location, EntityType.ZOMBIE);
            zombie.setBaby(false);
            zombie.setCustomName("§4§lHades");
            zombie.setCustomNameVisible(true);
            zombie.setCanPickupItems(false);
            zombie.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 1));
            zombie.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, Integer.MAX_VALUE, 127));
            zombie.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, Integer.MAX_VALUE, 127));
            zombie.setShouldBurnInDay(false);
            zombie.setRemoveWhenFarAway(false);
            zombie.getEquipment().setHelmet(new ItemStack(Material.GOLDEN_HELMET));
            zombie.getEquipment().setChestplate(null);
            zombie.getEquipment().setLeggings(null);
            zombie.getEquipment().setBoots(null);
            zombie.getEquipment().setItemInMainHand(new ItemBuilder(Material.DIAMOND_SWORD).unsafeEnchant(Enchantment.DAMAGE_ALL, 6).asItemStack());
            activeBosses.put(zombie.getUniqueId(), new Boss(MCCBoss.HADES, 10000));
            location.getWorld().spawnParticle(Particle.PORTAL, location.add(0, 1, 0), 100, 2, 2, 2);

            Boss boss = activeBosses.get(zombie.getUniqueId());

            new BukkitRunnable() {
                int minion = 7;
                boolean enraged = false;
                boolean safety1 = false;
                boolean safety2 = false;

                public void run() {
                    if (!zombie.isDead()) {
                        List<Player> players = new ArrayList<>();
                        for (Player p : zombie.getLocation().getNearbyPlayers(32, 32, 32)) players.add(p);
                        if (boss.bar != null) boss.bar.removeAll();
                        List<UUID> sortedList = boss.getSortedList();
                        if (sortedList.isEmpty())
                            boss.bar = Bukkit.createBossBar("§4§lHades §f> §c" + boss.health + "❤ §f> §eHighest Damager: §6None", BarColor.RED, BarStyle.SOLID);
                        else
                            boss.bar = Bukkit.createBossBar("§4§lHades §f> §c" + boss.health + "❤ §f> §eHighest Damager: §6" + Bukkit.getOfflinePlayer(sortedList.get(0)).getName(), BarColor.RED, BarStyle.SOLID);
                        for (Player p : players) boss.bar.addPlayer(p);

                        if (minion == 7 && boss.health < 9500) {
                            for (Player p : players) {
                                p.sendMessage("§4§lHades §chas summoned minions.");
                                spawnBossVex(p.getLocation(), zombie);
                                spawnBossVex(p.getLocation(), zombie);
                                spawnBossSkeleton(p.getLocation());
                                spawnBossStray(p.getLocation());
                            }
                            minion--;
                        } else if (minion == 6 && boss.health < 8000) {
                            for (Player p : players) {
                                p.sendMessage("§4§lHades §chas summoned minions.");
                                spawnBossVex(p.getLocation(), zombie);
                                spawnBossVex(p.getLocation(), zombie);
                                spawnBossSkeleton(p.getLocation());
                                spawnBossStray(p.getLocation());
                            }
                            minion--;
                        } else if (minion == 5 && boss.health < 6500) {
                            for (Player p : players) {
                                p.sendMessage("§4§lHades §chas summoned minions.");
                                spawnBossVex(p.getLocation(), zombie);
                                spawnBossVex(p.getLocation(), zombie);
                                spawnBossSkeleton(p.getLocation());
                                spawnBossStray(p.getLocation());
                                spawnBossStray(p.getLocation());
                            }
                            minion--;
                        } else if (minion == 4 && boss.health < 5000) {
                            for (Player p : players) {
                                p.sendMessage("§4§lHades §chas summoned minions.");
                                spawnBossVex(p.getLocation(), zombie);
                                spawnBossVex(p.getLocation(), zombie);
                                spawnBossSkeleton(p.getLocation());
                                spawnBossStray(p.getLocation());
                                spawnBossStray(p.getLocation());
                            }
                            minion--;
                        } else if (minion == 3 && boss.health < 4500) {
                            for (Player p : players) {
                                p.sendMessage("§4§lHades §chas summoned minions.");
                                spawnBossVex(p.getLocation(), zombie);
                                spawnBossVex(p.getLocation(), zombie);
                                spawnBossSkeleton(p.getLocation());
                                spawnBossStray(p.getLocation());
                                spawnBossStray(p.getLocation());
                            }
                            minion--;
                        } else if (minion == 2 && boss.health < 3000) {
                            for (Player p : players) {
                                p.sendMessage("§4§lHades §chas summoned minions.");
                                spawnBossVex(p.getLocation(), zombie);
                                spawnBossVex(p.getLocation(), zombie);
                                spawnBossSkeleton(p.getLocation());
                                spawnBossStray(p.getLocation());
                                spawnBossStray(p.getLocation());
                                spawnBossRavager(p.getLocation());
                            }
                            minion--;
                        } else if (minion == 1 && boss.health < 1500) {
                            for (Player p : players) {
                                p.sendMessage("§4§lHades §chas summoned minions.");
                                spawnBossVex(p.getLocation(), zombie);
                                spawnBossVex(p.getLocation(), zombie);
                                spawnBossSkeleton(p.getLocation());
                                spawnBossStray(p.getLocation());
                                spawnBossStray(p.getLocation());
                                spawnBossRavager(p.getLocation());
                            }
                            minion--;
                        } else if (minion == 0 && boss.health < 500) {
                            for (Player p : players) {
                                p.sendMessage("§4§lHades §chas summoned minions.");
                                spawnBossVex(p.getLocation(), zombie);
                                spawnBossVex(p.getLocation(), zombie);
                                spawnBossSkeleton(p.getLocation());
                                spawnBossStray(p.getLocation());
                                spawnBossStray(p.getLocation());
                                spawnBossRavager(p.getLocation());
                            }
                            minion--;
                        }

                        if (!enraged) {
                            if (boss.health < 2500) {
                                for (Player p : players) {
                                    CustomEnchantManager.addPotionEffect(p, new PotionEffect(PotionEffectType.BLINDNESS, 200, 0));
                                    p.setVelocity(p.getLocation().getDirection().normalize().multiply(-2.5));
                                    p.sendMessage("§4§lHades §cis now enraged!");
                                }
                                zombie.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 2));
                                zombie.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, Integer.MAX_VALUE, 1));
                                zombie.getWorld().spawnParticle(Particle.EXPLOSION_LARGE, zombie.getLocation().add(0, 1, 0), 10, 1, 1, 1);
                                enraged = true;
                            }
                        } else {
                            zombie.getWorld().spawnParticle(Particle.VILLAGER_ANGRY, zombie.getLocation().add(0, 2, 0), 5, 1, 1, 1);
                        }

                        if (!safety1) {
                            if (boss.health < 1500) {
                                for (Player p : players) {
                                    CustomEnchantManager.addPotionEffect(p, new PotionEffect(PotionEffectType.BLINDNESS, 200, 0));
                                    p.setVelocity(p.getLocation().getDirection().normalize().multiply(-2.5));
                                }
                                zombie.getWorld().spawnParticle(Particle.EXPLOSION_LARGE, zombie.getLocation().add(0, 1, 0), 10, 1, 1, 1);
                                safety1 = true;
                            }
                        }

                        if (!safety2) {
                            if (boss.health < 500) {
                                for (Player p : players) {
                                    CustomEnchantManager.addPotionEffect(p, new PotionEffect(PotionEffectType.BLINDNESS, 200, 0));
                                    p.setVelocity(p.getLocation().getDirection().normalize().multiply(-2.5));
                                }
                                zombie.getWorld().spawnParticle(Particle.EXPLOSION_LARGE, zombie.getLocation().add(0, 1, 0), 10, 1, 1, 1);
                                safety2 = true;
                            }
                        }
                    } else {
                        this.cancel();
                    }
                }
            }.runTaskTimer(Core.instance, 0L, 20L);

            new BukkitRunnable() {
                public void run() {
                    if (!zombie.isDead()) {
                        List<Player> players = new ArrayList<>();
                        for (Player p : zombie.getLocation().getNearbyPlayers(16, 16, 16)) players.add(p);
                        if (boss.health < 2500 ? Core.chance(30) : Core.chance(20)) {
                            for (Player p : players) {
                                p.sendMessage("§4§lHades §cwithered the area.");
                                p.damage(20, zombie);
                                CustomEnchantManager.addPotionEffect(p, new PotionEffect(PotionEffectType.WITHER, 200, 0));
                            }
                            zombie.getWorld().spawnParticle(Particle.SQUID_INK, zombie.getLocation().add(0, 1, 0), 500, 16, 2, 16);
                        }
                        if (boss.health < 2500 ? Core.chance(15) : Core.chance(10)) {
                            zombie.getWorld().spawnParticle(Particle.SMOKE_LARGE, zombie.getLocation().add(0, 2, 0), 500, 16, 2, 16);
                            for (Player p : players) {
                                p.sendMessage("§4§lHades §cslowed the ground.");
                                CustomEnchantManager.addPotionEffect(p, new PotionEffect(PotionEffectType.SLOW, 200, 1));
                            }
                        }
                    } else {
                        this.cancel();
                    }
                }
            }.runTaskTimer(Core.instance, 100L, 200L);
        }
    }

    @EventHandler
    public void bossDamageEvent(EntityDamageByEntityEvent e) {
        if (activeBosses.containsKey(e.getEntity().getUniqueId())) {
            Boss boss = activeBosses.get(e.getEntity().getUniqueId());
            if (e.getDamager() instanceof Player) {
                Player player = (Player) e.getDamager();

                if (boss.damageDone.containsKey(player.getUniqueId())) {
                    int oldDamage = boss.damageDone.get(player.getUniqueId());
                    boss.damageDone.put(player.getUniqueId(), (int) (oldDamage + e.getDamage()));
                    boss.health -= e.getDamage();
                } else {
                    boss.damageDone.put(player.getUniqueId(), (int) e.getDamage());
                    boss.health -= e.getDamage();
                }

                e.setDamage(0);

                if (e.getEntity() instanceof Zombie && e.getDamager() instanceof Player) {
                    Zombie zombie = (Zombie) e.getEntity();
                    if (zombie.getCustomName().equalsIgnoreCase("§6§lZeus") || zombie.getCustomName().equalsIgnoreCase("§4§lHades")) {
                        zombie.setTarget((Player) e.getDamager());
                    }
                }

                if (boss.health < 0) {
                    boss.bar.removeAll();

                    if (boss.boss == MCCBoss.ZEUS) {
                        ItemStack kit1 = CustomItemManager.getRandomKit();
                        Core.spawnFirework(player);
                        e.getEntity().getLocation().getWorld().strikeLightningEffect(e.getEntity().getLocation());
                        e.getEntity().getLocation().getWorld().spawnParticle(Particle.CAMPFIRE_SIGNAL_SMOKE, e.getEntity().getLocation().add(0, 1, 0), 25, 0, 2, 0);
                        e.getEntity().getWorld().dropItemNaturally(e.getEntity().getLocation(), kit1);
                        if (Core.chance(5)) player.getInventory().addItem(CustomItemManager.getTag(Tag.SLAYER));
                        activeBosses.remove(e.getEntity());
                        e.getEntity().remove();
                        List<UUID> sortedList = boss.getSortedList();
                        for (int i = 0; i < sortedList.size(); i++) {
                            if (i < 3) {
                                if (Bukkit.getOfflinePlayer(sortedList.get(i)).isOnline()) {
                                    Bukkit.getPlayer(sortedList.get(i)).getInventory().addItem(CustomItemManager.getCrate(Crate.GODLY_CRATE));
                                    Bukkit.getPlayer(sortedList.get(i)).getInventory().addItem(Core.chance(50) ? CustomItemManager.getRandomZeusArmor() : CustomItemManager.getRandomZeusWeapon());
                                    Bukkit.getPlayer(sortedList.get(i)).sendMessage("§4§lBoss §r> §fYou received a §6GODLY CRATE §ffor being top 3!");
                                }
                            } else if (i < 6) {
                                if (Bukkit.getOfflinePlayer(sortedList.get(i)).isOnline()) {
                                    Bukkit.getPlayer(sortedList.get(i)).getInventory().addItem(CustomItemManager.getCrate(Crate.ULTRA_CRATE));
                                    Bukkit.getPlayer(sortedList.get(i)).sendMessage("§4§lBoss §r> §fYou received a §6ULTRA CRATE §ffor being top 6!");
                                }
                            } else if (i < 20) {
                                if (Bukkit.getOfflinePlayer(sortedList.get(i)).isOnline()) {
                                    Bukkit.getPlayer(sortedList.get(i)).getInventory().addItem(CustomItemManager.getCrate(Crate.SUPER_CRATE));
                                    Bukkit.getPlayer(sortedList.get(i)).sendMessage("§4§lBoss §r> §fYou received a §6SUPER CRATE §ffor being top 20!");
                                }
                            } else {
                                if (Bukkit.getOfflinePlayer(sortedList.get(i)).isOnline()) {
                                    Bukkit.getPlayer(sortedList.get(i)).getInventory().addItem(CustomItemManager.getCrate(Crate.BASIC_CRATE));
                                    Bukkit.getPlayer(sortedList.get(i)).sendMessage("§4§lBoss §r> §fYou received a §6BASIC CRATE §ffor participating!");
                                }
                            }
                        }
                        int rng = sortedList.size() < 5 ? Core.generateNumber(0, sortedList.size() - 1) : Core.generateNumber(0, 4);
                        if (Bukkit.getOfflinePlayer(sortedList.get(rng)).isOnline()) {
                            Player winner = Bukkit.getPlayer(sortedList.get(rng));
                            ItemStack kit2 = CustomItemManager.getRandomKit();
                            winner.getInventory().addItem(kit2);
                            Core.shout("§4§lBoss §r> §6" + winner.getName() + " §fhas received a " + ItemHelper.getName(kit2) + " §ffrom §6§lZeus");
                        }
                        player.getInventory().addItem(CustomItemManager.getTag(Tag.ZEUS));
                        Core.shoutCenter("§f§m────────§r §6Boss §f§m────────");
                        Core.shoutCenter("§6§lZeus §ehas been defeated!");
                        Core.shoutCenter("§eThe rewards have been distributed.");
                        Core.shoutCenter("§eTop 5 Damagers:");
                        for (int i = 0; i < 5; i++) {
                            if (sortedList.size() > i) {
                                Core.shoutCenter("§6§l" + (i + 1) + ". §f" + Bukkit.getOfflinePlayer(sortedList.get(i)).getName());
                            }
                        }
                        Core.shoutCenter("§m───────────────────────");
                    } else if (boss.boss == MCCBoss.HADES) {
                        ItemStack kit1 = CustomItemManager.getRandomKit();
                        Core.spawnFirework(player);
                        e.getEntity().getLocation().getWorld().spawnParticle(Particle.SMOKE_LARGE, e.getEntity().getLocation().add(0, 1, 0), 25, 0, 2, 0);
                        e.getEntity().getLocation().getWorld().spawnParticle(Particle.SQUID_INK, e.getEntity().getLocation().add(0, 1, 0), 25, 0, 2, 0);
                        e.getEntity().getWorld().dropItemNaturally(e.getEntity().getLocation(), kit1);
                        if (Core.chance(5)) player.getInventory().addItem(CustomItemManager.getTag(Tag.SLAYER));
                        e.getEntity().remove();
                        activeBosses.remove(e.getEntity());
                        List<UUID> sortedList = boss.getSortedList();
                        for (int i = 0; i < sortedList.size(); i++) {
                            if (i < 3) {
                                if (Bukkit.getOfflinePlayer(sortedList.get(i)).isOnline()) {
                                    Bukkit.getPlayer(sortedList.get(i)).getInventory().addItem(CustomItemManager.getCrate(Crate.GODLY_CRATE));
                                    Bukkit.getPlayer(sortedList.get(i)).getInventory().addItem(Core.chance(50) ? CustomItemManager.getRandomHadesArmor() : CustomItemManager.getRandomHadesWeapon());
                                    Bukkit.getPlayer(sortedList.get(i)).sendMessage("§4§lBoss §r> §fYou received a §6GODLY CRATE §ffor being top 3!");
                                }
                            } else if (i < 6) {
                                if (Bukkit.getOfflinePlayer(sortedList.get(i)).isOnline()) {
                                    Bukkit.getPlayer(sortedList.get(i)).getInventory().addItem(CustomItemManager.getCrate(Crate.ULTRA_CRATE));
                                    Bukkit.getPlayer(sortedList.get(i)).sendMessage("§4§lBoss §r> §fYou received a §6ULTRA CRATE §ffor being top 6!");
                                }
                            } else if (i < 20) {
                                if (Bukkit.getOfflinePlayer(sortedList.get(i)).isOnline()) {
                                    Bukkit.getPlayer(sortedList.get(i)).getInventory().addItem(CustomItemManager.getCrate(Crate.SUPER_CRATE));
                                    Bukkit.getPlayer(sortedList.get(i)).sendMessage("§4§lBoss §r> §fYou received a §6SUPER CRATE §ffor being top 20!");
                                }
                            } else {
                                if (Bukkit.getOfflinePlayer(sortedList.get(i)).isOnline()) {
                                    Bukkit.getPlayer(sortedList.get(i)).getInventory().addItem(CustomItemManager.getCrate(Crate.BASIC_CRATE));
                                    Bukkit.getPlayer(sortedList.get(i)).sendMessage("§4§lBoss §r> §fYou received a §6BASIC CRATE §ffor participating!");
                                }
                            }
                        }
                        int rng = sortedList.size() < 5 ? Core.generateNumber(0, sortedList.size() - 1) : Core.generateNumber(0, 4);
                        if (Bukkit.getOfflinePlayer(sortedList.get(rng)).isOnline()) {
                            Player winner = Bukkit.getPlayer(sortedList.get(rng));
                            ItemStack kit2 = CustomItemManager.getRandomKit();
                            winner.getInventory().addItem(kit2);
                            Core.shout("§4§lBoss §r> §6" + winner.getName() + " §fhas received a " + ItemHelper.getName(kit2) + " §ffrom §4§lHades");
                        }
                        player.getInventory().addItem(CustomItemManager.getTag(Tag.HADES));
                        Core.shoutCenter("§f§m────────§r §6Boss §f§m────────");
                        Core.shoutCenter("§4§lHades §ehas been defeated!");
                        Core.shoutCenter("§eThe rewards have been distributed.");
                        Core.shoutCenter("§eTop 5 Damagers:");
                        for (int i = 0; i < 5; i++) {
                            if (sortedList.size() > i) {
                                Core.shoutCenter("§6§l" + (i + 1) + ". §f" + Bukkit.getOfflinePlayer(sortedList.get(i)).getName());
                            }
                        }
                        Core.shoutCenter("§m───────────────────────");
                    }
                    return;
                }

                if (boss.boss == MCCBoss.ZEUS)
                    if (Core.chance(2)) {
                        player.sendMessage("§6§lZeus §chas levitated you!");
                        CustomEnchantManager.addPotionEffect(player, new PotionEffect(PotionEffectType.LEVITATION, 200, 0));
                    }
            }
        }
    }

    @EventHandler
    public void bossDieEvent(EntityDeathEvent e) {
        if (e.getEntity() instanceof Zombie || e.getEntity() instanceof Skeleton) {
            e.getDrops().clear();
            e.setDroppedExp(0);
        }
    }

    public static void spawnBossZombie(Location location) {
        ItemStack[] armor = {new ItemStack(Material.DIAMOND_BOOTS), null, new ItemStack(Material.DIAMOND_CHESTPLATE), new ItemStack(Material.DIAMOND_HELMET)};
        Zombie zombie = (Zombie) location.getWorld().spawnEntity(Core.getRandomLocationAtHighestBlock(location, 2, 4, 2, 4), EntityType.ZOMBIE);
        zombie.setShouldBurnInDay(false);
        zombie.setCanPickupItems(false);
        zombie.setRemoveWhenFarAway(true);
        zombie.setCustomNameVisible(false);
        zombie.setBaby(Core.chance(50) ? true : false);
        zombie.setCustomName("§2§lZombie");
        zombie.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 9999999, 0, false, false));
        zombie.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 9999999, 1, false, false));
        zombie.getEquipment().setArmorContents(armor);
        zombie.getEquipment().setItemInMainHand(new ItemBuilder(Material.DIAMOND_SWORD).enchant(Enchantment.DAMAGE_ALL, 3).asItemStack());
    }

    public static void spawnBossHusk(Location location) {
        ItemStack[] armor = {new ItemStack(Material.DIAMOND_BOOTS), null, new ItemStack(Material.DIAMOND_CHESTPLATE), new ItemStack(Material.DIAMOND_HELMET)};
        Husk zombie = (Husk) location.getWorld().spawnEntity(Core.getRandomLocationAtHighestBlock(location, 2, 4, 2, 4), EntityType.HUSK);
        zombie.setShouldBurnInDay(false);
        zombie.setCanPickupItems(false);
        zombie.setRemoveWhenFarAway(true);
        zombie.setCustomNameVisible(false);
        zombie.setBaby(Core.chance(50) ? true : false);
        zombie.setCustomName("§2§lZombie");
        zombie.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 9999999, 0, false, false));
        zombie.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 9999999, 1, false, false));
        zombie.getEquipment().setArmorContents(armor);
        zombie.getEquipment().setItemInMainHand(new ItemBuilder(Material.DIAMOND_SWORD).enchant(Enchantment.DAMAGE_ALL, 3).asItemStack());
    }

    public static void spawnBossSkeleton(Location location) {
        ItemStack[] armor = {new ItemStack(Material.DIAMOND_BOOTS), null, new ItemStack(Material.DIAMOND_CHESTPLATE), new ItemStack(Material.DIAMOND_HELMET)};
        Skeleton skeleton = (Skeleton) location.getWorld().spawnEntity(Core.getRandomLocationAtHighestBlock(location, 4, 8, 4, 8), EntityType.SKELETON);
        skeleton.setCustomNameVisible(true);
        skeleton.setCustomName("§lSkeleton");
        skeleton.setCanPickupItems(false);
        skeleton.setRemoveWhenFarAway(true);
        skeleton.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 9999999, 0, false, false));
        skeleton.getEquipment().setArmorContents(armor);
        skeleton.getEquipment().setItemInMainHand(new ItemBuilder(Material.BOW).enchant(Enchantment.ARROW_DAMAGE, 4).asItemStack());
    }

    public static void spawnBossStray(Location location) {
        ItemStack[] armor = {new ItemStack(Material.DIAMOND_BOOTS), null, new ItemStack(Material.DIAMOND_CHESTPLATE), new ItemStack(Material.DIAMOND_HELMET)};
        Stray skeleton = (Stray) location.getWorld().spawnEntity(Core.getRandomLocationAtHighestBlock(location, 4, 8, 4, 8), EntityType.STRAY);
        skeleton.setCustomNameVisible(true);
        skeleton.setCustomName("§lSkeleton");
        skeleton.setCanPickupItems(false);
        skeleton.setRemoveWhenFarAway(true);
        skeleton.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 9999999, 0, false, false));
        skeleton.getEquipment().setArmorContents(armor);
        skeleton.getEquipment().setItemInMainHand(new ItemBuilder(Material.BOW).enchant(Enchantment.ARROW_DAMAGE, 4).asItemStack());
    }

    public static void spawnBossRavager(Location location) {
        Ravager skeleton = (Ravager) location.getWorld().spawnEntity(Core.getRandomLocationAtHighestBlock(location, 4, 8, 4, 8), EntityType.RAVAGER);
        skeleton.setCustomNameVisible(true);
        skeleton.setCustomName("§8Ravager");
        skeleton.setCanPickupItems(false);
        skeleton.setRemoveWhenFarAway(true);
    }

    public static void spawnBossVex(Location location, Mob summoner) {
        Vex skeleton = (Vex) location.getWorld().spawnEntity(Core.getRandomLocationAtHighestBlock(location, 4, 8, 4, 8), EntityType.VEX);
        skeleton.setCustomNameVisible(true);
        skeleton.setCustomName("§5Vex");
        skeleton.setCanPickupItems(false);
        skeleton.setRemoveWhenFarAway(true);
        skeleton.getEquipment().setItemInMainHand(new ItemBuilder(Material.DIAMOND_SWORD).enchant(Enchantment.DAMAGE_ALL, 3).asItemStack());
        skeleton.setSummoner(summoner);
    }

    public static long nextWorldBoss = Long.MAX_VALUE;
    public static boolean ZeusOrHades;

    public static void runBossTimer() {
        new BukkitRunnable() {
            public void run() {
                if (DateUtil.getEpochTime() > nextWorldBoss) {
                    if (ZeusOrHades) {
                        Location rLocation = Core.getRandomLocationAtHighestBlock(DefaultConfig.locations.get("spawn"), 400, 650, 400, 650);
                        Core.shoutCenter("§f§m────────§r §6Boss §f§m────────");
                        Core.shoutCenter("§6§lZeus §ehas spawned in the world!");
                        Core.shoutCenter("§eDefeat him to gain his weapons and rewards.");
                        Core.shoutCenter("§eLocation: §f" + Core.getStringFromLocation(rLocation));
                        Core.shoutCenter("§m───────────────────────");
                        spawnBoss(MCCBoss.ZEUS, rLocation);
                    } else {
                        Location rLocation = Core.getRandomLocationAtHighestBlock(DefaultConfig.locations.get("spawn"), 400, 650, 400, 650);
                        Core.shoutCenter("§f§m────────§r §6Boss §f§m────────");
                        Core.shoutCenter("§4§lHades §ehas spawned in the world!");
                        Core.shoutCenter("§eDefeat him to gain his armor and rewards.");
                        Core.shoutCenter("§eLocation: §f" + Core.getStringFromLocation(rLocation));
                        Core.shoutCenter("§m───────────────────────");
                        spawnBoss(MCCBoss.HADES, rLocation);
                    }
                    nextWorldBoss += 259200;
                    ZeusOrHades = !ZeusOrHades;
                }
            }
        }.runTaskTimer(Core.instance, 100L, 20L);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (command.getLabel().equalsIgnoreCase("boss")) {
                if (args.length > 1 && ((args[0].equalsIgnoreCase("summon") || args[0].equalsIgnoreCase("time")) || args[0].equalsIgnoreCase("clear"))) {
                    if (player.hasPermission("mcc.admin")) {
                        if (args[0].equalsIgnoreCase("summon")) {
                            if (args[1].equalsIgnoreCase("zeus")) {
                                Core.shoutCenter("§f§m────────§r §6Boss §f§m────────");
                                Core.shoutCenter("§6§lZeus §ehas spawned in the world!");
                                Core.shoutCenter("§eDefeat him to gain his weapons and rewards.");
                                Core.shoutCenter("§eLocation: §f" + Core.getStringFromLocation(player.getLocation()));
                                Core.shoutCenter("§m───────────────────────");
                                spawnBoss(MCCBoss.ZEUS, player.getLocation());
                            } else if (args[1].equalsIgnoreCase("hades")) {
                                Core.shoutCenter("§f§m────────§r §6Boss §f§m────────");
                                Core.shoutCenter("§4§lHades §ehas spawned in the world!");
                                Core.shoutCenter("§eDefeat him to gain his armor and rewards.");
                                Core.shoutCenter("§eLocation: §f" + Core.getStringFromLocation(player.getLocation()));
                                Core.shoutCenter("§m───────────────────────");
                                spawnBoss(MCCBoss.HADES, player.getLocation());
                            }
                            return true;
                        } else if (args[0].equalsIgnoreCase("clear")) {
                            for (UUID entity : activeBosses.keySet()) {
                                Bukkit.getWorld("world").getEntity(entity).remove();
                                activeBosses.remove(entity);
                                player.sendMessage(DefaultConfig.prefix + "§aAll bosses have been removed.");
                            }
                        } else if (args[0].equalsIgnoreCase("time")) {
                            nextWorldBoss = Long.parseLong(args[1]);
                            player.sendMessage(DefaultConfig.prefix + "§aBoss time changed.");
                            return true;
                        }
                    }
                } else {
                    StringUtil.sendCenteredMessage(player, ("§f§m────────§r §6Boss §f§m────────"));
                    if (ZeusOrHades)
                        StringUtil.sendCenteredMessage(player, ("§6§lZeus §ewill be spawned at §f" + DateUtil.getDate(nextWorldBoss) + " PM CT"));
                    else
                        StringUtil.sendCenteredMessage(player, ("§4§lHades §ewill be spawned at §f" + DateUtil.getDate(nextWorldBoss) + " PM CT"));
                    StringUtil.sendCenteredMessage(player, ("§m───────────────────────"));
                    return true;
                }
            }
        }
        return false;
    }
}
