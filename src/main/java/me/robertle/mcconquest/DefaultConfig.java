package me.robertle.mcconquest;

import org.bukkit.configuration.file.FileConfiguration;

public class DefaultConfig {

    public static FileConfiguration config = Core.instance.getConfig();

    public static String prefix;

    public static void loadDefaultConfigurations() {

        config.addDefault("Prefix", "§8§lMC§c§lConquest §r§f> ");

        prefix = config.getString("Prefix");

        Core.instance.getConfig().options().copyDefaults(true);
        Core.instance.saveConfig();
        Core.logToConsole("Default config has been loaded.");
    }

}
