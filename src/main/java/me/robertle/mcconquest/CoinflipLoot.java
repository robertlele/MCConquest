package me.robertle.mcconquest;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class CoinflipLoot {

    public List<ItemStack> items = new ArrayList<>();
    public int money = 0;

    public CoinflipLoot() {

    }

    public void claimLoot(Player player) {
        for (ItemStack i : items) {
            if (i != null)
            player.getInventory().addItem(i);
            player.sendMessage("+ §l" + i.getAmount() + " " + ItemHelper.getName(i));
        }
        items.clear();
        Core.econ.depositPlayer(player, money);
        player.sendMessage("§f+ §a$" + money);
        money = 0;
    }

}
