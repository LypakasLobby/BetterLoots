package com.lypaka.betterloots.ScavengerHunts;

import com.google.common.reflect.TypeToken;
import com.lypaka.betterloots.BetterLoots;
import com.lypaka.betterloots.ConfigGetters;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

import java.util.List;
import java.util.Map;

public class HuntRegistry {

    public static void loadHunts() throws ObjectMappingException {

        List<String> files = ConfigGetters.scavengerHunts;
        for (int i = 0; i < files.size(); i++) {

            String huntName = files.get(i);
            if (!huntName.contains(".conf")) {
                
                huntName = huntName + ".conf";
                
            }
            if (BetterLoots.lootConfigManager.getConfigNode(i, "Accessible").isVirtual()) {
                
                BetterLoots.logger.info("Detected an empty file for " + huntName + "...skipping!");
                continue;
                
            }

            boolean accessible = BetterLoots.lootConfigManager.getConfigNode(i, "Accessible").getBoolean();
            List<String> clearCommands = BetterLoots.lootConfigManager.getConfigNode(i, "Clear-Commands").getList(TypeToken.of(String.class));
            List<String> completionRewards = BetterLoots.lootConfigManager.getConfigNode(i, "Completion").getList(TypeToken.of(String.class));
            Hunt hunt = new Hunt(huntName, accessible, clearCommands, completionRewards);
            hunt.create();
            String guiDisplayTitle = BetterLoots.lootConfigManager.getConfigNode(i, "GUI", "Display-Title").getString();
            int guiRows = BetterLoots.lootConfigManager.getConfigNode(i, "GUI", "Rows").getInt();
            String guiBorderID = BetterLoots.lootConfigManager.getConfigNode(i, "GUI", "Slots", "Border", "ID").getString();
            String guiBorderSlots = BetterLoots.lootConfigManager.getConfigNode(i, "GUI", "Slots", "Border", "Slots").getString();
            String claimedLootDisplayName = BetterLoots.lootConfigManager.getConfigNode(i, "GUI", "Slots", "Loots", "Claimed", "Display-Name").getString();
            String claimedLootID = BetterLoots.lootConfigManager.getConfigNode(i, "GUI", "Slots", "Loots", "Claimed", "ID").getString();
            String notClaimedLootDisplayName = BetterLoots.lootConfigManager.getConfigNode(i, "GUI", "Slots", "Loots", "Not-Claimed", "Display-Name").getString();
            String notClaimedLootID = BetterLoots.lootConfigManager.getConfigNode(i, "GUI", "Slots", "Loots", "Not-Claimed", "ID").getString();
            String lootSlots = BetterLoots.lootConfigManager.getConfigNode(i, "GUI", "Slots", "Loots", "Slots").getString();
            HuntGUI gui = new HuntGUI(hunt, guiDisplayTitle, guiRows, guiBorderID, guiBorderSlots, claimedLootDisplayName, claimedLootID, notClaimedLootDisplayName, notClaimedLootID, lootSlots);
            gui.create();
            Map<String, Map<String, String>> lootMap = BetterLoots.lootConfigManager.getConfigNode(i, "Loots").getValue(new TypeToken<Map<String, Map<String, String>>>() {});
            for (Map.Entry<String, Map<String, String>> e2 : lootMap.entrySet()) {

                int number = Integer.parseInt(e2.getKey().replace("Loot-", ""));
                String location = e2.getValue().get("Location");
                List<String> prizes = BetterLoots.lootConfigManager.getConfigNode(i, "Loots", e2, "Prizes").getList(TypeToken.of(String.class));
                List<String> requirements = BetterLoots.lootConfigManager.getConfigNode(i, "Loots", e2, "Requirements").getList(TypeToken.of(String.class));
                Loot loot = new Loot(hunt, number, location, prizes, requirements);
                loot.create();

            }

        }

        BetterLoots.logger.info("Finished loading all the scavenger hunts!");

    }

}
