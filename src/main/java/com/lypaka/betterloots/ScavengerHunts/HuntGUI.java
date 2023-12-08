package com.lypaka.betterloots.ScavengerHunts;

public class HuntGUI {

    private final Hunt hunt;
    private final String guiDisplayTitle;
    private final int guiRows;
    private final String guiBorderID;
    private final String guiBorderSlots;
    private final String claimedLootDisplayName;
    private final String claimedLootID;
    private final String notClaimedLootDisplayName;
    private final String notClaimedLootID;
    private final String lootSlots;

    public HuntGUI (Hunt hunt, String guiDisplayTitle, int guiRows, String guiBorderID, String guiBorderSlots, String claimedLootDisplayName, String claimedLootID, String notClaimedLootDisplayName, String notClaimedLootID, String lootSlots) {

        this.hunt = hunt;
        this.guiDisplayTitle = guiDisplayTitle;
        this.guiRows = guiRows;
        this.guiBorderID = guiBorderID;
        this.guiBorderSlots = guiBorderSlots;
        this.claimedLootDisplayName = claimedLootDisplayName;
        this.claimedLootID = claimedLootID;
        this.notClaimedLootDisplayName = notClaimedLootDisplayName;
        this.notClaimedLootID = notClaimedLootID;
        this.lootSlots = lootSlots;

    }

    public void create() {

        this.hunt.setGUI(this);

    }

    public Hunt getHunt() {

        return this.hunt;

    }

    public String getGUIDisplayTitle() {

        return this.guiDisplayTitle;

    }

    public int getGUIRows() {

        return this.guiRows;

    }

    public String getGUIBorderID() {

        return this.guiBorderID;

    }

    public String getGUIBorderSlots() {

        return this.guiBorderSlots;

    }

    public String getClaimedLootDisplayName() {

        return this.claimedLootDisplayName;

    }

    public String getClaimedLootID() {

        return this.claimedLootID;

    }

    public String getNotClaimedLootDisplayName() {

        return this.notClaimedLootDisplayName;

    }

    public String getNotClaimedLootID() {

        return this.notClaimedLootID;

    }

    public String getLootSlots() {

        return this.lootSlots;

    }

}
