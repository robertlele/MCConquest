package me.robertle.mcconquest;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Set;
import java.util.UUID;

public class MCCPlayer {

    //Player data
    public static HashMap<UUID, String> playerClans = new HashMap<>();

    public static FileConfiguration playerConfig;
    public static File playerConfigFile;

    public static void loadPlayers() {
        playerConfigFile = new File(Core.instance.getDataFolder(), "players.yml");
        playerConfig = YamlConfiguration.loadConfiguration(playerConfigFile);

        if (playerConfig.get("Players.") == null) return;
        Set<String> paths = playerConfig.getConfigurationSection("Players").getKeys(false);
        for (String uuid : paths) {
            String path = "Players." + uuid;
            Core.logToConsole(UUID.fromString(uuid) + " ---- " + playerConfig.getString(path));
            playerClans.put(UUID.fromString(uuid), playerConfig.getString(path));
        }

        playerConfig.set("Players", "");
        Core.logToConsole("Player data has been loaded.");
    }

    public static void savePlayers() {
        if (playerClans.isEmpty()) return;
        for (UUID uuid : playerClans.keySet()) {
            String path = "Players." + uuid.toString();
            playerConfig.set(path, playerClans.get(uuid));
        }
        try {
            playerConfig.save(playerConfigFile);
            Core.logToConsole("Player data has been loaded.");
        } catch (IOException io) {
            io.printStackTrace();
        }
    }
}
