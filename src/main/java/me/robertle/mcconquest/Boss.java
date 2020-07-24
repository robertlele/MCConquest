package me.robertle.mcconquest;

import org.bukkit.boss.BossBar;

import java.util.*;

public class Boss {
    public HashMap<UUID, Integer> damageDone = new HashMap<>();
    public MCCBoss boss;
    public BossBar bar;
    public int health;

    public Boss(MCCBoss b, int h) {
        boss = b;
        health = h;
    }

    public List<UUID> getSortedList() {
        List<UUID> sortedList = new ArrayList<>();
        if (damageDone.isEmpty()) return sortedList;
        List<Integer> sortedIntegerList = new ArrayList<>();
        for (int i : damageDone.values()) {
            if (!sortedIntegerList.contains(i)) sortedIntegerList.add(i);
        }
        Collections.sort(sortedIntegerList);
        Collections.reverse(sortedIntegerList);
        for (int i : sortedIntegerList) {
            for (UUID uuid : damageDone.keySet()) {
                if (damageDone.get(uuid) == i) sortedList.add(uuid);
            }
        }
        return sortedList;
    }
}
