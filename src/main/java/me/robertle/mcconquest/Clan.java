package me.robertle.mcconquest;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class Clan {

    //Clan Data

    public static FileConfiguration clanConfig;
    public static File clanConfigFile;

    public static void loadClans() {
        clanConfigFile = new File(Core.instance.getDataFolder(), "clans.yml");
        clanConfig = YamlConfiguration.loadConfiguration(clanConfigFile);

        if (clanConfig.get("Clans.") == null) return;
        Set<String> paths = clanConfig.getConfigurationSection("Clans").getKeys(false);
        for (String name : paths) {
            String path = "Clans." + name + ".";
            HashMap<UUID, ClanRole> members = new HashMap<>();
            Set<String> memberPaths = clanConfig.getConfigurationSection("Clans." + name + ".Members").getKeys(false);
            for (String memberUUID : memberPaths) {
                String memberPath = path + "Members." + memberUUID;
                members.put(UUID.fromString(memberUUID), ClanRole.valueOf(clanConfig.getString(memberPath)));
            }
            int balance = clanConfig.getInt(path + "Balance");
            List<String> logs = clanConfig.getStringList(path + "Logs");
            int storageSize = clanConfig.getInt(path + "Storage Size");
            int perk = clanConfig.getInt(path + "Perk");
            Inventory storage = Bukkit.createInventory(null, storageSize, "§e" + name + "'s Clan Storage");
            for (ItemStack i : InventoryUtil.loadItemStackList(path + "Storage", storageSize)) {
                if (i != null) storage.addItem(i);
            }
            clans.put(name, new Clan(members, name, balance, logs, storage, perk, storageSize));
        }

        clanConfig.set("Clans", "");
        Core.logToConsole("Clans data has been loaded.");
    }

    public static void saveClans() {
        if (clans.isEmpty()) return;
        for (Clan clan : clans.values()) {
            String path = "Clans." + clan.clanName + ".";
            clanConfig.set(path + "Name", clan.clanName);
            clanConfig.set(path + "Balance", clan.clanBalance);
            clanConfig.set(path + "Perk", clan.clanPerk);
            clanConfig.set(path + "Storage Size", clan.clanStorageSize);
            clanConfig.set(path + "Logs", clan.clanLogs);
            for (UUID uuid : clan.clanMembers.keySet()) {
                clanConfig.set(path + "Members." + uuid.toString(), clan.clanMembers.get(uuid).toString());
            }
            List<ItemStack> storage = new ArrayList<>();
            if (clan.clanStorage.getContents().length > 0) {
                for (ItemStack i : clan.clanStorage.getContents()) {
                    storage.add(i);
                }
            }
            clanConfig.set(path + "Storage", storage);
        }
        try {
            clanConfig.save(clanConfigFile);
            Core.logToConsole("Clans data has been saved.");
        } catch (IOException io) {
            io.printStackTrace();
        }
    }

    //Clan Manager
    public static HashMap<String, Clan> clans = new HashMap<>();
    public static HashMap<UUID, String> clanInvites = new HashMap<>();

    public static List<Clan> sortClans() {
        List<Clan> newClans = new ArrayList<>();
        for (int i = 8; i >= 0; i--) {
            for (String clanName : clans.keySet()) {
                if (clans.get(clanName).getOnlinePlayers().size() == i) {
                    newClans.add(clans.get(clanName));
                }
            }
        }
        return newClans;
    }

    public static void createClan(String clanName, Player player) {
        clans.put(clanName, new Clan(clanName, player));
    }

    public static void deleteClan(String clanName) {
        Clan clan = clans.get(clanName);
        for (UUID uuid : clan.clanMembers.keySet()) {
            MCCPlayer.playerClans.remove(uuid);
        }
        clans.remove(clanName);
    }

    public static String getPlayerClan(Player player) {
        if (MCCPlayer.playerClans.containsKey(player.getUniqueId())) {
            return MCCPlayer.playerClans.get(player.getUniqueId());
        } else return "None";
    }

    public static String getPlayerClan(String player) {
        if (MCCPlayer.playerClans.containsKey(Core.getPlayerUUID(player))) {
            return MCCPlayer.playerClans.get(Core.getPlayerUUID(player));
        } else return "None";
    }

    public static boolean clanExist(String clanName) {
        for (String clan : clans.keySet()) {
            if (clan.equalsIgnoreCase(clanName)) return true;
        }
        return false;
    }

    public static Clan getClan(String clanName) {
        for (String clan : clans.keySet()) {
            if (clan.equalsIgnoreCase(clanName)) return clans.get(clan);
        }
        return null;
    }

    //Clan Object

    public HashMap<UUID, ClanRole> clanMembers = new HashMap<>();
    public String clanName;
    public int clanBalance = 0;
    public List<String> clanLogs = new ArrayList<>();
    public Inventory clanStorage;
    public int clanPerk = 0;
    public int clanStorageSize = 18;

    public Clan(HashMap<UUID, ClanRole> clanMembers, String clanName, int clanBalance, List<String> clanLogs, Inventory clanStorage, int clanPerk, int clanStorageSize) {
        this.clanMembers = clanMembers;
        this.clanName = clanName;
        this.clanBalance = clanBalance;
        this.clanLogs = clanLogs;
        this.clanStorage = clanStorage;
        this.clanPerk = clanPerk;
        this.clanStorageSize = clanStorageSize;
        clans.put(clanName, this);
    }

    public Clan(String clanName, Player player) {
        this.clanName = clanName;
        this.clanMembers.put(player.getUniqueId(), ClanRole.LEADER);
        MCCPlayer.playerClans.put(player.getUniqueId(), clanName);
        clanStorage = Bukkit.createInventory(null, 18, "§e" + this.clanName + "'s Clan Storage");
        this.log(player.getName() + " created the clan.");
    }

    public void log(String message) {
        clanLogs.add("[" + DateUtil.getDate() + "] " + message);
    }

    public void invitePlayer(Player inviter, Player invitee) {
        clanInvites.put(invitee.getUniqueId(), this.clanName);
        this.log(inviter.getName() + " invited " + invitee.getName() + " to the clan.");
    }

    public void addPlayer(Player player) {
        this.clanMembers.put(player.getUniqueId(), ClanRole.MEMBER);
        MCCPlayer.playerClans.put(player.getUniqueId(), this.clanName);
        this.log(player.getName() + " joined the clan.");
    }

    public void removePlayer(UUID uuid) {
        this.clanMembers.remove(uuid);
        MCCPlayer.playerClans.remove(uuid);
        this.log(Bukkit.getOfflinePlayer(uuid).getName() + " left the clan.");
    }

    public boolean promotePlayer(UUID uuid) {
        if (clanMembers.get(uuid) == ClanRole.COLEADER) {
            return false;
        } else if (clanMembers.get(uuid) == ClanRole.OFFICER) {
            clanMembers.put(uuid, ClanRole.COLEADER);
            return true;
        } else if (clanMembers.get(uuid) == ClanRole.MEMBER) {
            clanMembers.put(uuid, ClanRole.OFFICER);
            return true;
        }
        return false;
    }

    public boolean demotePlayer(UUID uuid) {
        if (clanMembers.get(uuid) == ClanRole.COLEADER) {
            clanMembers.put(uuid, ClanRole.OFFICER);
            return true;
        } else if (clanMembers.get(uuid) == ClanRole.OFFICER) {
            clanMembers.put(uuid, ClanRole.MEMBER);
            return true;
        } else if (clanMembers.get(uuid) == ClanRole.MEMBER) {
            return false;
        }
        return false;
    }

    public void kickPlayer(UUID uuid, Player kicker) {
        this.clanMembers.remove(uuid);
        MCCPlayer.playerClans.remove(uuid);
        this.log(Bukkit.getOfflinePlayer(uuid).getName() + " was kicked from the clan by " + kicker.getName() + ".");
    }

    public void deposit(Player player, int amount) {
        Core.econ.withdrawPlayer(player, amount);
        this.clanBalance += amount;
        this.log(player.getName() + " deposited $" + amount + " to the clan.");
    }

    public String getLogString(int page) {
        String logs = "";
        if (this.clanLogs.size() > 10 * page) {
            for (int i = 10 * page; i < 10 * (page + 1); i++) {
                if (i < clanLogs.size()) {
                    logs += this.clanLogs.get(i) + "\n";
                }
            }
        }
        return logs;
    }

    public int getLogPages() {
        return (this.clanLogs.size() / 10) + 1;
    }

    public List<Player> getOnlinePlayers() {
        List<Player> onlinePlayers = new ArrayList<>();
        for (UUID clanMember : clanMembers.keySet()) {
            if (Bukkit.getOfflinePlayer(clanMember).isOnline()) {
                onlinePlayers.add(Bukkit.getPlayer(clanMember));
            }
        }
        return onlinePlayers;
    }

    public void openStorage(Player player) {
        player.openInventory(this.clanStorage);
        this.log(player.getName() + " opened the clan storage.");
    }

    public boolean buyNextPerk() {
        switch (this.clanPerk) {
            case 0:
                if (this.clanBalance >= 400000) {
                    this.clanBalance -= 400000;
                    this.clanPerk++;
                    this.clanStorageSize = 18;
                    clanStorage = Bukkit.createInventory(null, 18, "§e" + this.clanName + "'s Clan Storage");
                    return true;
                }
                return false;
            case 1:
                if (this.clanBalance >= 200000) {
                    this.clanBalance -= 200000;
                    this.clanPerk++;
                    for (UUID uuid : this.clanMembers.keySet()) {
                        this.clanStorage.addItem(CustomItemManager.getArmorVoucher(false));
                    }
                    return true;
                }
                return false;
            case 2:
                if (this.clanBalance >= 200000) {
                    this.clanBalance -= 200000;
                    this.clanPerk++;
                    this.clanStorageSize = 27;
                    clanStorage = Bukkit.createInventory(null, 27, "§e" + this.clanName + "'s Clan Storage");
                    return true;
                }
                return false;
            case 3:
                if (this.clanBalance >= 400000) {
                    this.clanBalance -= 400000;
                    this.clanPerk++;
                    for (UUID uuid : this.clanMembers.keySet()) {
                        this.clanStorage.addItem(CustomItemManager.getWeaponVoucher(false));
                    }
                    return true;
                }
                return false;
            case 4:
                if (this.clanBalance >= 1200000) {
                    this.clanBalance -= 1200000;
                    this.clanPerk++;
                    this.clanStorageSize = 54;
                    clanStorage = Bukkit.createInventory(null, 54, "§e" + this.clanName + "'s Clan Storage");
                    return true;
                }
                return false;
            case 5:
                if (this.clanBalance >= 800000) {
                    this.clanBalance -= 800000;
                    this.clanPerk++;
                    for (UUID uuid : this.clanMembers.keySet()) {
                        this.clanStorage.addItem(CustomItemManager.getArtifactVoucher());
                    }
                    return true;
                }
                return false;
            case 6:
                if (this.clanBalance >= 800000) {
                    this.clanBalance -= 800000;
                    this.clanPerk++;
                    for (UUID uuid : this.clanMembers.keySet()) {
                        this.clanStorage.addItem(CustomItemManager.getArmorVoucher(true));
                    }
                    return true;
                }
                return false;
            case 7:
                if (this.clanBalance >= 800000) {
                    this.clanBalance -= 800000;
                    this.clanPerk++;
                    for (UUID uuid : this.clanMembers.keySet()) {
                        this.clanStorage.addItem(CustomItemManager.getWeaponVoucher(true));
                    }
                    return true;
                }
                return false;
            case 8:
                if (this.clanBalance >= 1200000) {
                    this.clanBalance -= 1200000;
                    this.clanPerk++;
                    for (UUID uuid : this.clanMembers.keySet()) {
                        this.clanStorage.addItem(CustomItemManager.getCrate(Crate.SUPER_CRATE));
                    }
                    return true;
                }
                return false;
            case 9:
                if (this.clanBalance >= 1600000) {
                    this.clanBalance -= 1600000;
                    this.clanPerk++;
                    for (UUID uuid : this.clanMembers.keySet()) {
                        this.clanStorage.addItem(CustomItemManager.getCrate(Crate.ULTRA_CRATE));
                    }
                    return true;
                }
                return false;
            default:
                return false;
        }
    }


}
