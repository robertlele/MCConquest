package me.robertle.mcconquest;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.List;

public class Replacer {
    public Replacer() {
    }

    public static String replace(String var0) {
        var0 = var0.replace("{aqua}", "&b").replace("{black}", "&0").replace("{blue}", "&9").replace("{dark_aqua}", "&3").replace("{dark_blue}", "&1").replace("{dark_grey}", "&8").replace("{dark_green}", "&2").replace("{dark_purple}", "&5").replace("{dark_red}", "&4").replace("{gold}", "&6").replace("{gray}", "&7").replace("{green}", "&a").replace("{light_purple}", "&d").replace("{red}", "&c").replace("{white}", "&f").replace("{yellow}", "&e").replace("{bold}", "&l").replace("{italic}", "&o").replace("{magic}", "&k").replace("{reset}", "&r").replace("{strike}", "&m").replace("{strikethrough}", "&m").replace("{underline}", "&n");
        var0 = replace(var0, '&');
        return var0;
    }

    public static String replace(String var0, Player var1) {
        return var0.replace("{player}", var1.getName()).replace("{playerUUID}", var1.getUniqueId().toString());
    }

    public static String replace(String var0, char var1) {
        return ChatColor.translateAlternateColorCodes(var1, var0);
    }

    public static List<String> replace(List<String> var0) {
        for (int var1 = 0; var1 < var0.size(); ++var1) {
            String var2 = (String) var0.get(var1);
            var0.set(var1, replace(var2));
        }

        return var0;
    }
}
