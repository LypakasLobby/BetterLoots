package com.lypaka.betterloots.ScavengerHunts;

import com.lypaka.betterloots.BetterLoots;

import java.util.ArrayList;
import java.util.List;

public class Hunt {

    private final String name;
    private boolean accessible;
    private final List<String> clearCommands;
    private final List<String> completionRewards;
    private HuntGUI gui;
    private List<Loot> loots;

    public Hunt (String name, boolean accessible, List<String> clearCommands, List<String> completionRewards) {

        this.name = name;
        this.accessible = accessible;
        this.clearCommands = clearCommands;
        this.completionRewards = completionRewards;
        this.loots = new ArrayList<>();

    }

    public void create() {

        BetterLoots.scavengerHunts.add(this);

    }

    public String getName() {

        return this.name;

    }

    public boolean isAccessible() {

        return this.accessible;

    }

    public void setAccessible (boolean accessible) {

        this.accessible = accessible;

    }

    public List<String> getClearCommands() {

        return this.clearCommands;

    }

    public List<String> getCompletionRewards() {

        return this.completionRewards;

    }

    public List<Loot> getLoots() {

        return this.loots;

    }

    public void setGUI (HuntGUI gui) {

        this.gui = gui;

    }

    public HuntGUI getGUI() {

        return this.gui;

    }

    public void addLoot (Loot loot) {

        this.loots.add(loot);

    }

    public static Hunt getFromName (String name) {

        Hunt hunt = null;
        if (!name.contains(".conf")) {

            name = name + ".conf";

        }

        for (Hunt h : BetterLoots.scavengerHunts) {

            if (h.getName().equalsIgnoreCase(name)) {

                hunt = h;
                break;

            }

        }

        return hunt;

    }

    public static Hunt getFromLootLocation (String location) {

        Hunt hunt = null;
        for (Hunt h : BetterLoots.scavengerHunts) {

            for (Loot loot : h.getLoots()) {

                if (loot.getLocation().equalsIgnoreCase(location)) {

                    hunt = h;
                    break;

                }

            }

            if (hunt != null) break;

        }

        return hunt;

    }

}
