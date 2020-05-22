package me.robertle.mcconquest;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Random;
import java.util.UUID;
import java.util.logging.Level;

public final class Core extends JavaPlugin {

    public static JavaPlugin instance;

    public static Economy econ;

    @Override
    public void onEnable() {
        instance = this;

        //Setup Vault
        econ = getServer().getServicesManager().getRegistration(Economy.class).getProvider();

        //Commands
        getCommand("clan").setExecutor(new ClanCommands());
        getCommand("cc").setExecutor(new ClanCommands());
        getCommand("mcc").setExecutor(new MCCCommands());
        getCommand("generator").setExecutor(new MCCCommands());

        //Events
        getServer().getPluginManager().registerEvents(new InventoryManager(), this);
        getServer().getPluginManager().registerEvents(new MobHunting(), this);
        getServer().getPluginManager().registerEvents(new MiningManager(), this);
        getServer().getPluginManager().registerEvents(new VoucherEvent(), this);
        getServer().getPluginManager().registerEvents(new FishingManager(), this);
        getServer().getPluginManager().registerEvents(new MCCEvents(), this);

        //Placeholder
        new MCCPlaceholder(this).register();

        //Config & Data
        DefaultConfig.loadDefaultConfigurations();
        Clan.loadClans();
        MCCPlayer.loadPlayers();
        CustomHeadManager.loadHeads();
        InventoryUtil.addLevelOneEnchants();

        //Timers
        FishingManager.runFishTimers();
        Generator.runGenerators();

    }

    @Override
    public void onDisable() {
        //Data
        Clan.saveClans();
        MCCPlayer.savePlayers();
    }

    public static void logToConsole(String message) {
        instance.getLogger().log(Level.INFO, message);
    }

    public static void shout(String message) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.sendMessage(message);
        }
    }

    public static boolean isAlpha(String s) {
        if (s == null) {
            return false;
        }

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (!(c >= 'A' && c <= 'Z') && !(c >= 'a' && c <= 'z')) {
                return false;
            }
        }
        return true;
    }

    public static boolean chance(int chance) {
        int r = generateNumber(1, 100);
        if (r <= chance) return true;
        else return false;
    }

    public static int generateNumber(int min, int max) {
        Random random = new Random();
        int r = random.nextInt((max - min) + 1) + min;
        return r;
    }

    public static UUID getPlayerUUID(String player) {
        if (Bukkit.getOfflinePlayer(player).hasPlayedBefore()) {
            return Bukkit.getOfflinePlayer(player).getUniqueId();
        }
        return null;
    }

    public static void sendActionBar(Player player, String message) {
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(message));
    }
}
