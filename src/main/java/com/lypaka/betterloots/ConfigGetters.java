package com.lypaka.betterloots;

import com.google.common.reflect.TypeToken;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

import java.util.List;
import java.util.Map;

public class ConfigGetters {

    public static List<String> scavengerHunts;
    public static String mainBorderID;
    public static String mainBorderSlots;
    public static Map<String, Map<String, String>> huntGUIStuff;
    public static int mainGUIRows;
    public static String mainGUITitle;

    public static void load() throws ObjectMappingException {

        scavengerHunts = BetterLoots.configManager.getConfigNode(0, "Hunts").getList(TypeToken.of(String.class));
        mainBorderID = BetterLoots.configManager.getConfigNode(2, "Border", "ID").getString();
        mainBorderSlots = BetterLoots.configManager.getConfigNode(2, "Border", "Slots").getString();
        huntGUIStuff = BetterLoots.configManager.getConfigNode(2, "Hunts").getValue(new TypeToken<Map<String, Map<String, String>>>() {});
        mainGUIRows = BetterLoots.configManager.getConfigNode(2, "Rows").getInt();
        mainGUITitle = BetterLoots.configManager.getConfigNode(2, "Title").getString();

    }

}
