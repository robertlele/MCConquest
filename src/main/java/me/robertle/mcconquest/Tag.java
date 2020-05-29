package me.robertle.mcconquest;

public enum Tag {
    BETA("§a§lBeta"),
    STIER("§4§lS-TIER"),
    ATIER("§e§lA-TIER"),
    BTIER("§a§lB-TIER"),
    OMEGA("§6Omega"),
    ALPHA("§4Alpha"),
    GAMMA("§eGamma"),
    DELTA("§2Delta"),
    ETA("§fEta"),
    CASH("§a§lCASH"),
    FISHERMAN("§3F§bi§3s§bh§3e§br§3m§ba§3n"),
    GRINDER("§6G§er§6i§en§6d§ee§6r"),
    MINER("§8M§7i§8n§7e§8r"),
    SPECIAL("§d§lSPECIAL"),
    WINNER("§6§lWINNER");

    private String tagString;

    Tag(String tagString){
        this.tagString = tagString;
    }

    public String getTagString() {
        return tagString + " ";
    }

    public static Tag getTagFromTagString(String string) {
        for (Tag tag : Tag.values()) {
            if (tag.getTagString().equalsIgnoreCase(string)) {
                return tag;
            }
        }
        return null;
    }
}
