package me.robertle.mcconquest;

import me.robertle.mcconquest.Managers.BossManager;
import me.robertle.mcconquest.Managers.MobManager;
import me.robertle.mcconquest.Managers.OutpostManager;
import me.robertle.mcconquest.Managers.OutpostManager.Outpost;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;
import java.util.List;

public class DefaultConfig {

    public static FileConfiguration config = Core.instance.getConfig();

    public static String prefix;

    public static HashMap<String, Location> locations = new HashMap<>();

    public static void loadDefaultConfigurations() {

        config.addDefault("Prefix", "§8§lMC§c§lConquest §r§f> ");

        prefix = config.getString("Prefix");

        if (config.get("Clan Events") != null && !config.getString("Clan Events").equalsIgnoreCase("")) {
            for (String time : config.getConfigurationSection("Clan Events").getKeys(false)) {
                ClanEvents.eventTimes.put(Integer.parseInt(time), Event.valueOf(config.getString("Clan Events." + time)));
            }
        } else {
            ClanEvents.eventTimes.put(7, Event.LUCKY_PIT);
            ClanEvents.eventTimes.put(17, Event.LUCKY_PIT);
            ClanEvents.eventTimes.put(9, Event.KOTH);
            ClanEvents.eventTimes.put(19, Event.KOTH);
            ClanEvents.eventTimes.put(11, Event.BEAT_DOWN);
            ClanEvents.eventTimes.put(22, Event.BEAT_DOWN);
            ClanEvents.eventTimes.put(13, Event.TREASURE_HUNT);
            ClanEvents.eventTimes.put(24, Event.TREASURE_HUNT);
            Core.logToConsole("Default event times loaded.");

        }

        if (config.get("Locations") != null) {
            for (String locationName : config.getConfigurationSection("Locations").getKeys(false)) {
                locations.put(locationName, config.getLocation("Locations." + locationName));
            }
        }

        if (config.get("Spawners") != null) {
            MobManager.spawners = (List<Location>) config.getList("Spawners");
        }

        if (config.getStringList("War Clans") != null) {
            War.warClans = config.getStringList("War Clans");
        }

        if (config.get("Outpost") != null) {
            for (String outpost : config.getConfigurationSection("Outpost").getKeys(false)) {
                OutpostManager.outposts.add(new Outpost(config.getLocation("Outpost." + outpost), outpost));
            }
        }

        War.daysTilWar = config.getInt("War Days");

        War.warOpen = config.getBoolean("War Open");

        BossManager.nextWorldBoss = config.getLong("Next World Boss");
        BossManager.ZeusOrHades = config.getBoolean("Zeus or Hades");

        config.set("Clan Events", "");
        Core.instance.getConfig().options().copyDefaults(false);
        Core.instance.saveConfig();
        Core.logToConsole("Default config has been loaded.");
    }

    public static void saveDefaultConfigurations() {
        for (int time : ClanEvents.eventTimes.keySet()) {
            config.set("Clan Events." + time, ClanEvents.eventTimes.get(time).toString());
        }

        if (!locations.isEmpty())
            for (String locationName : locations.keySet()) {
                config.set("Locations." + locationName, locations.get(locationName));
            }

        if (!War.warClans.isEmpty()) {
            config.set("War Clans", War.warClans);
        }

        if (!MobManager.spawners.isEmpty()) {
            config.set("Spawners", MobManager.spawners);
        }

        if (!OutpostManager.outposts.isEmpty()) {
            for (Outpost outpost : OutpostManager.outposts) {
                config.set("Outpost." + outpost.name, outpost.outpostLocation);
            }
        }

        config.set("War Days", War.daysTilWar);

        config.set("War Open", War.warOpen);

        config.set("Next World Boss", BossManager.nextWorldBoss);
        config.set("Zeus or Hades", BossManager.ZeusOrHades);

        Core.instance.saveConfig();
        Core.logToConsole("Default config has been saved.");
    }

}
