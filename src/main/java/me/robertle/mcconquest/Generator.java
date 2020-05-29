package me.robertle.mcconquest;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Generator {

    public GeneratorType type;
    public double produced;
    public int level;

    public Generator(GeneratorType type) {
        this.type = type;
        level = 1;
    }

    public Generator(GeneratorType type, int level, double produced) {
        this.type = type;
        this.level = level;
        this.produced = produced;
    }

    public static boolean addNewGenerator(Player player, GeneratorType type) {
        if (MCCPlayer.playerGenerators.containsKey(player.getUniqueId())) {
            if (MCCPlayer.playerGenerators.get(player.getUniqueId()).size() < 4) {
                List<Generator> generators = MCCPlayer.playerGenerators.get(player.getUniqueId());
                generators.add(new Generator(type));
                MCCPlayer.playerGenerators.put(player.getUniqueId(), generators);
                return true;
            } else return false;
        } else {
            List<Generator> generators = new ArrayList<>();
            generators.add(new Generator(type));
            MCCPlayer.playerGenerators.put(player.getUniqueId(), generators);
            return true;
        }
    }

    public static boolean upgradeGenerator(Player player, int index) {
        if (MCCPlayer.playerGenerators.containsKey(player.getUniqueId())) {
            Generator generator = MCCPlayer.playerGenerators.get(player.getUniqueId()).get(index);
            if (generator.type == GeneratorType.MONEY) {
                switch (generator.level) {
                    case 1:
                        if (player.getInventory().containsAtLeast(CustomItemManager.getIngot(false, 1), 150)) {
                            InventoryUtil.removeItems(player, CustomItemManager.getIngot(false, 150), 150);
                            generator.level++;
                            generator.produced = 0;
                            return true;
                        } else
                            return false;
                    case 2:
                        if (player.getInventory().containsAtLeast(CustomItemManager.getIngot(false, 1), 250)) {
                            InventoryUtil.removeItems(player, CustomItemManager.getIngot(false, 250), 250);
                            generator.level++;
                            generator.produced = 0;
                            return true;
                        } else
                            return false;
                    case 3:
                        if (player.getInventory().containsAtLeast(CustomItemManager.getIngot(true, 1), 150)) {
                            InventoryUtil.removeItems(player, CustomItemManager.getIngot(true, 150), 150);
                            generator.level++;
                            generator.produced = 0;
                            return true;
                        } else
                            return false;
                    case 4:
                        if (player.getInventory().containsAtLeast(CustomItemManager.getIngot(true, 1), 250)) {
                            InventoryUtil.removeItems(player, CustomItemManager.getIngot(true, 250), 250);
                            generator.level++;
                            generator.produced = 0;
                            return true;
                        } else
                            return false;
                    case 5:
                        return false;
                }
            } else if (generator.type == GeneratorType.INGOT) {
                switch (generator.level) {
                    case 1:
                        if (player.getInventory().containsAtLeast(CustomItemManager.getEssence(false, 1), 100)) {
                            InventoryUtil.removeItems(player, CustomItemManager.getEssence(false, 100), 100);
                            generator.level++;
                            generator.produced = 0;
                            return true;
                        } else
                            return false;
                    case 2:
                        if (player.getInventory().containsAtLeast(CustomItemManager.getEssence(false, 1), 200)) {
                            InventoryUtil.removeItems(player, CustomItemManager.getEssence(false, 200), 200);
                            generator.level++;
                            generator.produced = 0;
                            return true;
                        } else
                            return false;
                    case 3:
                        if (player.getInventory().containsAtLeast(CustomItemManager.getEssence(true, 1), 100)) {
                            InventoryUtil.removeItems(player, CustomItemManager.getEssence(true, 100), 100);
                            generator.level++;
                            generator.produced = 0;
                            return true;
                        } else
                            return false;
                    case 4:
                        if (player.getInventory().containsAtLeast(CustomItemManager.getEssence(true, 1), 200)) {
                            InventoryUtil.removeItems(player, CustomItemManager.getEssence(true, 200), 200);
                            generator.level++;
                            generator.produced = 0;
                            return true;
                        } else
                            return false;
                    case 5:
                        return false;
                }
            } else if (generator.type == GeneratorType.ESSENCE) {
                switch (generator.level) {
                    case 1:
                        if (Core.econ.has(player, 50000)) {
                            Core.econ.withdrawPlayer(player, 50000);
                            generator.level++;
                            generator.produced = 0;
                            return true;
                        } else
                            return false;
                    case 2:
                        if (Core.econ.has(player, 100000)) {
                            Core.econ.withdrawPlayer(player, 100000);
                            generator.level++;
                            generator.produced = 0;
                            return true;
                        } else
                            return false;
                    case 3:
                        if (Core.econ.has(player, 150000)) {
                            Core.econ.withdrawPlayer(player, 150000);
                            generator.level++;
                            generator.produced = 0;
                            return true;
                        } else
                            return false;
                    case 4:
                        if (Core.econ.has(player, 250000)) {
                            Core.econ.withdrawPlayer(player, 250000);
                            generator.level++;
                            generator.produced = 0;
                            return true;
                        } else
                            return false;
                    case 5:
                        return false;
                }
            }
        }
        return false;
    }

    public static void collectGenerators(Player player) {
        if (MCCPlayer.playerGenerators.containsKey(player.getUniqueId())) {
            for (Generator generator : MCCPlayer.playerGenerators.get(player.getUniqueId())) {
                if (generator.produced > 1) {
                    int amount = (int) generator.produced;
                    switch (generator.type) {
                        case MONEY:
                            Core.econ.depositPlayer(player, amount);
                            generator.produced -= amount;
                            player.sendMessage(DefaultConfig.prefix + "§eYou collected §6$" + amount + ".");
                            break;
                        case INGOT:
                            if (generator.level > 3) {
                                player.getInventory().addItem(CustomItemManager.getIngot(true, amount));
                                player.sendMessage(DefaultConfig.prefix + "§eYou collected §6" + amount + " Gold Ingot.");
                            } else {
                                player.getInventory().addItem(CustomItemManager.getIngot(false, amount));
                                player.sendMessage(DefaultConfig.prefix + "§eYou collected §6" + amount + " Iron Ingot.");
                            }
                            generator.produced -= amount;
                            break;
                        case ESSENCE:
                            if (generator.level > 3) {
                                player.getInventory().addItem(CustomItemManager.getEssence(true, amount));
                                player.sendMessage(DefaultConfig.prefix + "§eYou collected §6" + amount + " Rare Essence.");
                            } else {
                                player.getInventory().addItem(CustomItemManager.getEssence(false, amount));
                                player.sendMessage(DefaultConfig.prefix + "§eYou collected §6" + amount + " Essence.");
                            }
                            generator.produced -= amount;
                            break;
                    }
                }
            }
        }
    }

    public static ItemStack getGeneratorItem(Player player, int index) {
        if (MCCPlayer.playerGenerators.containsKey(player.getUniqueId())) {
            Generator generator = MCCPlayer.playerGenerators.get(player.getUniqueId()).get(index);
            ItemBuilder item;
            if (generator.type == GeneratorType.MONEY) {
                item = new ItemBuilder(CustomHeadManager.heads.get("moneyprinter")).displayName("§a§lMoney Generator");
                switch (generator.level) {
                    case 1:
                        item.lore("§6§n§lGenerator Info", "", "§eLevel: §f" + generator.level, "§eProduced: §f$" + (int) generator.produced, "§eUpgrade Cost: §f150 Iron Ingot -> $15k/hour", "", "§eLeft click to upgrade");
                        return item.asItemStack();
                    case 2:
                        item.lore("§6§n§lGenerator Info", "", "§eLevel: §f" + generator.level, "§eProduced: §f$" + (int) generator.produced, "§eUpgrade Cost: §f250 Iron Ingot -> $20k/hour", "", "§eLeft click to upgrade");
                        return item.asItemStack();
                    case 3:
                        item.lore("§6§n§lGenerator Info", "", "§eLevel: §f" + generator.level, "§eProduced: §f$" + (int) generator.produced, "§eUpgrade Cost: §f150 Gold Ingot -> $30k/hour", "", "§eLeft click to upgrade");
                        return item.asItemStack();
                    case 4:
                        item.lore("§6§n§lGenerator Info", "", "§eLevel: §f" + generator.level, "§eProduced: §f$" + (int) generator.produced, "§eUpgrade Cost: §f250 Gold Ingot -> $40k/hour", "", "§eLeft click to upgrade");
                        return item.asItemStack();
                    case 5:
                        item.lore("§6§n§lGenerator Info", "", "§eLevel: §f" + generator.level, "§eProduced: §f$" + (int) generator.produced, "§eUpgrade Cost: §f§lMAX", "", "");
                        return item.asItemStack();
                }
            } else if (generator.type == GeneratorType.INGOT) {
                item = new ItemBuilder(CustomHeadManager.heads.get("minecartchest")).displayName("§a§lIngot Generator");
                switch (generator.level) {
                    case 1:
                        item.lore("§6§n§lGenerator Info", "", "§eLevel: §f" + generator.level, "§eProduced: §f" + (int) generator.produced + " Iron Ingot", "§eUpgrade Cost: §f100 Essence -> 40 Iron Ingot/hour", "", "§eLeft click to upgrade");
                        return item.asItemStack();
                    case 2:
                        item.lore("§6§n§lGenerator Info", "", "§eLevel: §f" + generator.level, "§eProduced: §f" + (int) generator.produced + " Iron Ingot", "§eUpgrade Cost: §f200 Essence -> 60 Iron Ingot/hour", "", "§eLeft click to upgrade");
                        return item.asItemStack();
                    case 3:
                        item.lore("§6§n§lGenerator Info", "", "§eLevel: §f" + generator.level, "§eProduced: §f" + (int) generator.produced + " Iron Ingot", "§eUpgrade Cost: §f100 Rare Essence -> 30 Gold Ingot/hour", "", "§eLeft click to upgrade");
                        return item.asItemStack();
                    case 4:
                        item.lore("§6§n§lGenerator Info", "", "§eLevel: §f" + generator.level, "§eProduced: §f" + (int) generator.produced + " Gold Ingot", "§eUpgrade Cost: §f200 Rare Essence -> 40 Gold Ingot/hour", "", "§eLeft click to upgrade");
                        return item.asItemStack();
                    case 5:
                        item.lore("§6§n§lGenerator Info", "", "§eLevel: §f" + generator.level, "§eProduced: §f" + (int) generator.produced + " Gold Ingot", "§eUpgrade Cost: §f§lMAX", "", "");
                        return item.asItemStack();
                }
            } else if (generator.type == GeneratorType.ESSENCE) {
                item = new ItemBuilder(CustomHeadManager.heads.get("mobspawner")).displayName("§a§lEssence Generator");
                switch (generator.level) {
                    case 1:
                        item.lore("§6§n§lGenerator Info", "", "§eLevel: §f" + generator.level, "§eProduced: §f" + (int) generator.produced + " Essence", "§eUpgrade Cost: §f$50k -> 30 Essence/hour", "", "§eLeft click to upgrade");
                        return item.asItemStack();
                    case 2:
                        item.lore("§6§n§lGenerator Info", "", "§eLevel: §f" + generator.level, "§eProduced: §f" + (int) generator.produced + " Essence", "§eUpgrade Cost: §f$100k -> 50 Essence/hour", "", "§eLeft click to upgrade");
                        return item.asItemStack();
                    case 3:
                        item.lore("§6§n§lGenerator Info", "", "§eLevel: §f" + generator.level, "§eProduced: §f" + (int) generator.produced + " Essence", "§eUpgrade Cost: §f$150k -> 20 Rare Essence/hour", "", "§eLeft click to upgrade");
                        return item.asItemStack();
                    case 4:
                        item.lore("§6§n§lGenerator Info", "", "§eLevel: §f" + generator.level, "§eProduced: §f" + (int) generator.produced + " Rare Essence", "§eUpgrade Cost: §f$250k -> 30 Rare Essence/hour", "", "§eLeft click to upgrade");
                        return item.asItemStack();
                    case 5:
                        item.lore("§6§n§lGenerator Info", "", "§eLevel: §f" + generator.level, "§eProduced: §f" + (int) generator.produced + " Rare Essence", "§eUpgrade Cost: §f§lMAX", "", "");
                        return item.asItemStack();
                }
            }
        }
        return null;
    }

    public static void runGenerators() {
        new BukkitRunnable() {
            public void run() {
                for (UUID uuid : MCCPlayer.playerGenerators.keySet()) {
                    if (Bukkit.getOfflinePlayer(uuid).isOnline()) {
                        for (Generator gen : MCCPlayer.playerGenerators.get(uuid)) {
                            if (gen.type == GeneratorType.MONEY) {
                                switch (gen.level) {
                                    case 1:
                                        gen.produced += 13.89;
                                        break;
                                    case 2:
                                        gen.produced += 20.83;
                                        break;
                                    case 3:
                                        gen.produced += 27.78;
                                        break;
                                    case 4:
                                        gen.produced += 41.67;
                                        break;
                                    case 5:
                                        gen.produced += 55.56;
                                        break;
                                }
                            } else if (gen.type == GeneratorType.INGOT) {
                                switch (gen.level) {
                                    case 1:
                                        gen.produced += .0417;
                                        break;
                                    case 2:
                                        gen.produced += .0556;
                                        break;
                                    case 3:
                                        gen.produced += .0833;
                                        break;
                                    case 4:
                                        gen.produced += .0417;
                                        break;
                                    case 5:
                                        gen.produced += .0556;
                                        break;
                                }
                            } else if (gen.type == GeneratorType.ESSENCE) {
                                switch (gen.level) {
                                    case 1:
                                        gen.produced += .0278;
                                        break;
                                    case 2:
                                        gen.produced += .0417;
                                        break;
                                    case 3:
                                        gen.produced += .0694;
                                        break;
                                    case 4:
                                        gen.produced += .0278;
                                        break;
                                    case 5:
                                        gen.produced += .0417;
                                        break;
                                }
                            }
                        }
                    }
                }
            }
        }.runTaskTimer(Core.instance, 0L, 100L);
    }

}
