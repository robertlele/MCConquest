package me.robertle.mcconquest;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class MCCPlayer {

    //Player data
    public static HashMap<UUID, String> playerClans = new HashMap<>();
    public static HashMap<UUID, List<Generator>> playerGenerators = new HashMap<>();

    public static FileConfiguration playerConfig;
    public static File playerConfigFile;

    public static void loadPlayers() {
        playerConfigFile = new File(Core.instance.getDataFolder(), "players.yml");
        playerConfig = YamlConfiguration.loadConfiguration(playerConfigFile);

        if (playerConfig.get("Players.") == null) return;
        Set<String> paths = playerConfig.getConfigurationSection("Players").getKeys(false);
        for (String uuid : paths) {
            String path = "Players." + uuid;
            if (playerConfig.getString(path + ".Clan") != null)
                playerClans.put(UUID.fromString(uuid), playerConfig.getString(path + ".Clan"));
            if (playerConfig.get(path + ".Generator") != null) {
                Set<String> generatorPaths = playerConfig.getConfigurationSection(path + ".Generator").getKeys(false);
                List<Generator> generators = new ArrayList<>();
                for (String generatorNumber : generatorPaths) {
                    generators.add(new Generator(GeneratorType.valueOf(playerConfig.getString(path + ".Generator." + generatorNumber + ".type")), playerConfig.getInt(path + ".Generator." + generatorNumber + ".level"), playerConfig.getDouble(path + ".Generator." + generatorNumber + ".produced")));
                }
                playerGenerators.put(UUID.fromString(uuid), generators);
            }
            if (playerConfig.get(path + ".Tag") != null) {
                Tags.playerTags.put(UUID.fromString(uuid), Tag.valueOf(playerConfig.getString(path + ".Tag")));
            }
        }

        playerConfig.set("Players", "");
        Core.logToConsole("Player data has been loaded.");
    }

    public static void savePlayers() {
        if (!playerClans.isEmpty()) {
            for (UUID uuid : playerClans.keySet()) {
                String path = "Players." + uuid.toString();
                playerConfig.set(path + ".Clan", playerClans.get(uuid));
            }
            for (UUID uuid : playerGenerators.keySet()) {
                String path = "Players." + uuid.toString();
                for (int i = 0; i < playerGenerators.get(uuid).size(); i++) {
                    playerConfig.set(path + ".Generator." + i + ".type", playerGenerators.get(uuid).get(i).type.toString());
                    playerConfig.set(path + ".Generator." + i + ".level", playerGenerators.get(uuid).get(i).level);
                    playerConfig.set(path + ".Generator." + i + ".produced", playerGenerators.get(uuid).get(i).produced);
                }
            }
            for (UUID uuid : Tags.playerTags.keySet()) {
                String path = "Players." + uuid.toString() + ".Tag";
                playerConfig.set(path, Tags.playerTags.get(uuid).toString());
            }
        }
        try {
            playerConfig.save(playerConfigFile);
            Core.logToConsole("Player data has been saved.");
        } catch (IOException io) {
            io.printStackTrace();
        }
    }
}
