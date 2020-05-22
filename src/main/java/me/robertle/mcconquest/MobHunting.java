package me.robertle.mcconquest;

import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class MobHunting implements Listener {

    @EventHandler
    public void onZombieKill(EntityDeathEvent e) {
        if (e.getEntity() instanceof Zombie) {
            Zombie zombie = (Zombie) e.getEntity();
            if (zombie.getCustomName() == null) return;
            if (zombie.getCustomName().equalsIgnoreCase("§aB-Tier Zombie")) {
                e.setDroppedExp(0);
                e.getDrops().clear();
                if (zombie.getKiller() != null) {
                    Player player = zombie.getKiller();
                    if (ItemHelper.hasName(player.getInventory().getItemInMainHand())) {
                        String itemName = ItemHelper.getName(player.getInventory().getItemInMainHand());
                        if (itemName.equalsIgnoreCase("§2Giant Grinder")) {
                            player.getInventory().addItem(CustomItemManager.getEssence(false, Core.generateNumber(1, 2) + 2));
                            if (Core.chance(1))
                                player.getInventory().addItem(CustomItemManager.getCrate(Crate.BASIC_CRATE));
                            if (Core.chance(6))
                                player.getInventory().addItem(CustomItemManager.getRandomMoneyNote(500, 1000));
                        } else if (itemName.equalsIgnoreCase("§2Zombie Grinder")) {
                            player.getInventory().addItem(CustomItemManager.getEssence(false, Core.generateNumber(1, 2) + 1));
                            if (Core.chance(1))
                                player.getInventory().addItem(CustomItemManager.getCrate(Crate.BASIC_CRATE));
                            if (Core.chance(6))
                                player.getInventory().addItem(CustomItemManager.getRandomMoneyNote(500, 1000));
                        } else {
                            player.getInventory().addItem(CustomItemManager.getEssence(false, Core.generateNumber(1, 2)));
                            if (Core.chance(1))
                                player.getInventory().addItem(CustomItemManager.getCrate(Crate.BASIC_CRATE));
                            if (Core.chance(6))
                                player.getInventory().addItem(CustomItemManager.getRandomMoneyNote(500, 1000));
                        }
                    }
                }
            } else if (zombie.getCustomName().equalsIgnoreCase("§eA-Tier Zombie")) {
                e.setDroppedExp(0);
                e.getDrops().clear();
                if (zombie.getKiller() != null) {
                    Player player = zombie.getKiller();
                    if (ItemHelper.hasName(player.getInventory().getItemInMainHand())) {
                        String itemName = ItemHelper.getName(player.getInventory().getItemInMainHand());
                        if (itemName.equalsIgnoreCase("§2Giant Grinder")) {
                            player.getInventory().addItem(CustomItemManager.getEssence(false, Core.generateNumber(2, 3) + 2));
                            player.getInventory().addItem(CustomItemManager.getEssence(true, Core.generateNumber(1, 2) + 2));
                            if (Core.chance(3))
                                player.getInventory().addItem(CustomItemManager.getCrate(Crate.BASIC_CRATE));
                            if (Core.chance(1))
                                player.getInventory().addItem(CustomItemManager.getCrate(Crate.SUPER_CRATE));
                            if (Core.chance(6))
                                player.getInventory().addItem(CustomItemManager.getRandomMoneyNote(500, 2000));
                        } else if (itemName.equalsIgnoreCase("§2Zombie Grinder")) {
                            player.getInventory().addItem(CustomItemManager.getEssence(false, Core.generateNumber(2, 3) + 1));
                            player.getInventory().addItem(CustomItemManager.getEssence(true, Core.generateNumber(1, 2) + 1));
                            if (Core.chance(3))
                                player.getInventory().addItem(CustomItemManager.getCrate(Crate.BASIC_CRATE));
                            if (Core.chance(1))
                                player.getInventory().addItem(CustomItemManager.getCrate(Crate.SUPER_CRATE));
                            if (Core.chance(6))
                                player.getInventory().addItem(CustomItemManager.getRandomMoneyNote(500, 2000));
                        } else {
                            player.getInventory().addItem(CustomItemManager.getEssence(false, Core.generateNumber(2, 3)));
                            player.getInventory().addItem(CustomItemManager.getEssence(true, Core.generateNumber(1, 2)));
                            if (Core.chance(3))
                                player.getInventory().addItem(CustomItemManager.getCrate(Crate.BASIC_CRATE));
                            if (Core.chance(1))
                                player.getInventory().addItem(CustomItemManager.getCrate(Crate.SUPER_CRATE));
                            if (Core.chance(6))
                                player.getInventory().addItem(CustomItemManager.getRandomMoneyNote(500, 2000));
                        }
                    }
                }
            }
        }
    }

}
