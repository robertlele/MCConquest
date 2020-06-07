package me.robertle.mcconquest;

import me.astero.companions.api.CAPI;
import org.bukkit.entity.Player;

public enum Pet {
    GIANT, DRAGON, HORSE, SKELETON_HORSE, GOLEM, MOOSHROOM, SQUID,
    PIGMAN, BLAZE, POLAR_BEAR, SLIME, PIG, OCELOT, SILVERFISH, SKELETON,
    ENDERMAN, SPIDER, BAT, RABBIT, CHICKEN, WOLF, GHAST, MAGMA_CUBE, GUARDIAN,
    WITCH, SHEEP, ZOMBIE;

    public static boolean isSummoned(Player player, Pet pet) {
        if (CAPI.isSummoned(player)) {
            CAPI companion = new CAPI();
            if (companion.getActiveCompanionName(player).equalsIgnoreCase(pet.toString())) {
                return true;
            }
        }
        return false;
    }
}
