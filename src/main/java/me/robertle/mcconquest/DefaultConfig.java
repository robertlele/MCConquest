package me.robertle.mcconquest;

import org.bukkit.configuration.file.FileConfiguration;

public class DefaultConfig {

    public static FileConfiguration config = Core.instance.getConfig();

    public static String prefix;

    public static void loadDefaultConfigurations() {

        config.addDefault("Prefix", "§8§lMC§c§lConquest §r§f> ");

        prefix = config.getString("Prefix");

        if (config.get("Clan Events") != null) {
            for (String time : config.getConfigurationSection("Clan Events").getKeys(false)) {
                ClanEvents.eventTimes.put(Integer.parseInt(time), Event.valueOf(config.getString("Clan Events." + time)));
            }
        } else {
            config.set("Clan Events.7", "LUCKY_PIT");
            config.set("Clan Events.17", "LUCKY_PIT");
            config.set("Clan Events.9", "KOTH");
            config.set("Clan Events.19", "KOTH");
            config.set("Clan Events.11", "BEAT_DOWN");
            config.set("Clan Events.22", "BEAT_DOWN");
            config.set("Clan Events.13", "TREASURE_HUNT");
            config.set("Clan Events.24", "TREASURE_HUNT");
        }

        Core.instance.getConfig().options().copyDefaults(true);
        Core.instance.saveConfig();
        Core.logToConsole("Default config has been loaded.");
    }

    public static void saveDefaultConfigurations() {
        for (int time : ClanEvents.eventTimes.keySet()) {
            config.set("Clan Events." + time, ClanEvents.eventTimes.get(time).toString());
        }

        Core.instance.saveConfig();
        Core.logToConsole("Default config has been saved.");
    }

}
