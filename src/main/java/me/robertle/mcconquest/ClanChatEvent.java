package me.robertle.mcconquest;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ClanChatEvent implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void changeClanChatFormat(AsyncPlayerChatEvent e) {
        if (MCCPlayer.playerClans.containsKey(e.getPlayer().getUniqueId())) {
            e.getFormat().replaceAll("%CLAN%", MCCPlayer.playerClans.get(e.getPlayer().getUniqueId()));
        } else
            e.getFormat().replaceAll("%CLAN%", "");
    }

}
