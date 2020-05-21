package me.robertle.mcconquest;

import org.bukkit.entity.Player;
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

    public static boolean addNewGenerator(Player player, GeneratorType type) {
        if (MCCPlayer.playerGenerators.containsKey(player.getUniqueId())) {
            if (MCCPlayer.playerGenerators.get(player.getUniqueId()).size() < 4) {
                MCCPlayer.playerGenerators.get(player.getUniqueId()).add(new Generator(type));
                return true;
            } else return false;
        } else {
            List<Generator> generators = new ArrayList<>();
            generators.add(new Generator(type));
            MCCPlayer.playerGenerators.put(player.getUniqueId(), generators);
            return true;
        }
    }

    public static void runGenerators() {
        new BukkitRunnable() {
            public void run() {
                for (UUID uuid : MCCPlayer.playerGenerators.keySet()) {
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
        }.runTaskTimer(Core.instance, 0L, 100L);
    }

}
