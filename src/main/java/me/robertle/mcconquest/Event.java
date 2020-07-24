package me.robertle.mcconquest;

public enum Event {
    BEAT_DOWN, KOTH, LUCKY_PIT, TREASURE_HUNT, POT_OF_GOLD;

    public static String stringFromEvent(Event event) {
        switch (event) {
            case BEAT_DOWN:
                return "Beat Down";
            case KOTH:
                return "KOTH";
            case LUCKY_PIT:
                return "Lucky Pit";
            case TREASURE_HUNT:
                return "Treasure Hunt";
            case POT_OF_GOLD:
                return "Pot of Gold";
        }
        return null;
    }
}
