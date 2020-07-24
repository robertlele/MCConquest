package me.robertle.mcconquest;

import me.robertle.mcconquest.Managers.*;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.List;
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
        getCommand("merchant").setExecutor(new MCCCommands());
        getCommand("analyze").setExecutor(new MCCCommands());
        getCommand("generator").setExecutor(new MCCCommands());
        getCommand("event").setExecutor(new ClanEvents());
        getCommand("coinflip").setExecutor(new Coinflip());
        getCommand("war").setExecutor(new War());
        getCommand("tag").setExecutor(new Tags());
        getCommand("outpost").setExecutor(new OutpostManager());
        getCommand("reboot").setExecutor(new MCCCommands());
        getCommand("mcchelp").setExecutor(new MCCCommands());
        getCommand("wiki").setExecutor(new MCCCommands());
        getCommand("discord").setExecutor(new MCCCommands());
        getCommand("donate").setExecutor(new MCCCommands());
        getCommand("claim").setExecutor(new MCCCommands());
        getCommand("shout").setExecutor(new MCCCommands());
        getCommand("debuff").setExecutor(new MCCCommands());
        getCommand("punish").setExecutor(new MCCCommands());
        getCommand("boss").setExecutor(new BossManager());
        getCommand("leave").setExecutor(new MCCCommands());


        //Events
        getServer().getPluginManager().registerEvents(new InventoryManager(), this);
        getServer().getPluginManager().registerEvents(new MobManager(), this);
        getServer().getPluginManager().registerEvents(new MiningManager(), this);
        getServer().getPluginManager().registerEvents(new VoucherEvent(), this);
        getServer().getPluginManager().registerEvents(new FishingManager(), this);
        getServer().getPluginManager().registerEvents(new MCCEvents(), this);
        getServer().getPluginManager().registerEvents(new ClanEvents(), this);
        getServer().getPluginManager().registerEvents(new Coinflip(), this);
        getServer().getPluginManager().registerEvents(new War(), this);
        getServer().getPluginManager().registerEvents(new Tags(), this);
        getServer().getPluginManager().registerEvents(new JoinFull(), this);
        getServer().getPluginManager().registerEvents(new CustomEnchantManager(), this);
        getServer().getPluginManager().registerEvents(new BossManager(), this);
        getServer().getPluginManager().registerEvents(new TutorialManager(), this);

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
        ClanEvents.runEventTimers();
        Challenge.runChallengeTimer();
        MobManager.spawnMobTimer();
        CustomEnchantManager.runEnchantTimer();
        OutpostManager.runOutpostTimer();
        BossManager.runBossTimer();
    }

    @Override
    public void onDisable() {
        //Entities
        for (Entity entity : Bukkit.getWorld("world").getEntities()) {
            if (!(entity instanceof Player || entity instanceof NPC || entity instanceof ArmorStand)) {
                entity.remove();
            }
        }

        //Data
        War.daysTilWar--;
        Clan.saveClans();
        MCCPlayer.savePlayers();
        DefaultConfig.saveDefaultConfigurations();

        //Events
        ClanEvents.stopCurretEvent();

        for (BukkitTask task : Bukkit.getScheduler().getPendingTasks()) {
            ((Runnable) task).run();
        }
    }

    public static void logToConsole(String message) {
        instance.getLogger().log(Level.INFO, message);
    }

    public static void shout(String message) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.sendMessage(message);
        }
    }

    public static void shoutCenter(String message) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            StringUtil.sendCenteredMessage(player, message);
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

    public static boolean chance(double chance) {
        Random random = new Random();
        double r = random.nextDouble();
        if (r < (chance / 100)) return true;
        else return false;
    }

    public static int generateNumber(int min, int max) {
        Random random = new Random();
        int r = random.nextInt((max - min) + 1) + min;
        return r;
    }

    public static int rollNegative() {
        if (chance(50)) return -1;
        return 1;
    }

    public static UUID getPlayerUUID(String player) {
        if (Bukkit.getOfflinePlayer(player).hasPlayedBefore()) {
            return Bukkit.getOfflinePlayer(player).getUniqueId();
        }
        return UUID.randomUUID();
    }

    public static Location getRandomLocationAtHighestBlock(Location center, int xMin, int xMax, int zMin, int zMax) {
        int x = center.getBlockX() + (rollNegative() * Core.generateNumber(xMin, xMax));
        int z = center.getBlockZ() + (rollNegative() * Core.generateNumber(zMin, zMax));
        int y = Bukkit.getWorld("world").getHighestBlockYAt(x, z);
        return new Location(Bukkit.getWorld("world"), x, y + 2, z);
    }

    public static String getStringFromLocation(Location location) {
        return location.getBlockX() + ", " + location.getBlockY() + ", " + location.getBlockZ();
    }

    public static void sendActionBar(Player player, String message) {
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(message));
    }

    public static void spawnFirework(Player player) {
        Firework fw = player.getWorld().spawn(player.getLocation(), Firework.class);
        FireworkMeta meta = fw.getFireworkMeta();

        meta.addEffect(FireworkEffect.builder().flicker(true).withColor(Color.RED, Color.BLACK).withFade(Color.WHITE).with(FireworkEffect.Type.STAR).build());
        meta.addEffect(FireworkEffect.builder().flicker(true).withColor(Color.GRAY).withFade(Color.RED).with(FireworkEffect.Type.BURST).build());
        meta.setPower(1);
        fw.setFireworkMeta(meta);
    }

    public static void giftPlayer(Player player, String gift, int amount) {
        List<ItemStack> items = new ArrayList<>();
        if (MCCPlayer.playerConfirms.containsKey(player.getUniqueId()))
            items = MCCPlayer.playerConfirms.get(player.getUniqueId());
        for (int i = 0; i < amount; i++) {
            switch (gift) {
                case "season":
                    items.add(CustomItemManager.getCrate(Crate.SEASON_CRATE));
                    break;
                case "zeus":
                    items.add(CustomItemManager.getBossEgg(MCCBoss.ZEUS));
                    break;
                case "hades":
                    items.add(CustomItemManager.getBossEgg(MCCBoss.HADES));
                    break;
                case "godly":
                    items.add(CustomItemManager.getCrate(Crate.GODLY_CRATE));
                    break;
                case "super":
                    items.add(CustomItemManager.getCrate(Crate.SUPER_CRATE));
                    break;
                case "ultra":
                    items.add(CustomItemManager.getCrate(Crate.ULTRA_CRATE));
                    break;
            }
        }
        MCCPlayer.playerConfirms.put(player.getUniqueId(), items);
        if (Bukkit.getOfflinePlayer(player.getUniqueId()).isOnline()) {
            Bukkit.getPlayer(player.getUniqueId()).sendMessage(DefaultConfig.prefix + "§aYou have items to claim,");
            TextComponent claim = new TextComponent("§a§lClick here to claim them.");
            claim.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/claim"));
            Bukkit.getPlayer(player.getUniqueId()).sendMessage(claim);
        }
    }

    public static void spawnFirework(Location location) {
        Firework fw = location.getWorld().spawn(location, Firework.class);
        FireworkMeta meta = fw.getFireworkMeta();

        meta.addEffect(FireworkEffect.builder().flicker(true).withColor(Color.RED, Color.BLACK).withFade(Color.WHITE).with(FireworkEffect.Type.STAR).build());
        meta.addEffect(FireworkEffect.builder().flicker(true).withColor(Color.GRAY).withFade(Color.RED).with(FireworkEffect.Type.BURST).build());
        meta.setPower(1);
        fw.setFireworkMeta(meta);
    }
}
