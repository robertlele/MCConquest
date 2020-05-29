package me.robertle.mcconquest;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinFull implements Listener {

    @EventHandler
    public void joinFull(PlayerJoinEvent e) {
        if (Bukkit.getOnlinePlayers().size() > 120) {
            if (!e.getPlayer().hasPermission("mcc.joinfull")) {
                e.getPlayer().kickPlayer("§cThe server is full.\n§fPurchase a rank at §ashop.mcconquest.com §ffor a reserved slot.");
            }
        }
    }

}
