package com.lypaka.betterloots.ScavengerHunts;

import java.util.List;

public class Loot {

    private final Hunt hunt;
    private final int number;
    private final String location;
    private final List<String> prizes;
    private final List<String> requirements;

    public Loot (Hunt hunt, int number, String location, List<String> prizes, List<String> requirements) {

        this.hunt = hunt;
        this.number = number;
        this.location = location;
        this.prizes = prizes;
        this.requirements = requirements;

    }

    public void create() {

        this.hunt.addLoot(this);

    }

    public Hunt getHunt() {

        return this.hunt;

    }

    public int getLootNumber() {

        return this.number;

    }

    public String getLocation() {

        return this.location;

    }

    public List<String> getPrizes() {

        return this.prizes;

    }

    public List<String> getRequirements() {

        return this.requirements;

    }

    public static Loot getFromLocation (Hunt hunt, String location) {

        Loot loot = null;
        for (Loot l : hunt.getLoots()) {

            if (l.getLocation().equalsIgnoreCase(location)) {

                loot = l;
                break;

            }

        }

        return loot;

    }

    public static int getLootNumberFromLocation (Hunt hunt, String location) {

        int num = -1;
        for (Loot loot : hunt.getLoots()) {

            if (loot.getLocation().equalsIgnoreCase(location)) {

                num = loot.getLootNumber();
                break;

            }

        }

        return num;

    }

}
