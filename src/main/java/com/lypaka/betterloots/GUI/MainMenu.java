package com.lypaka.betterloots.GUI;

import ca.landonjw.gooeylibs2.api.UIManager;
import ca.landonjw.gooeylibs2.api.button.Button;
import ca.landonjw.gooeylibs2.api.button.GooeyButton;
import ca.landonjw.gooeylibs2.api.page.GooeyPage;
import ca.landonjw.gooeylibs2.api.template.types.ChestTemplate;
import com.google.common.reflect.TypeToken;
import com.lypaka.betterloots.BetterLoots;
import com.lypaka.betterloots.ConfigGetters;
import com.lypaka.betterloots.ScavengerHunts.Hunt;
import com.lypaka.betterloots.ScavengerHunts.HuntGUI;
import com.lypaka.lypakautils.FancyText;
import com.lypaka.lypakautils.MiscHandlers.ItemStackBuilder;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.StringNBT;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

import java.util.List;
import java.util.Map;

public class MainMenu {

    public static void open (ServerPlayerEntity player) throws ObjectMappingException {

        ChestTemplate template = ChestTemplate.builder(ConfigGetters.mainGUIRows).build();
        GooeyPage page = GooeyPage.builder()
                .template(template)
                .title(FancyText.getFormattedString(ConfigGetters.mainGUITitle))
                .build();

        if (!ConfigGetters.mainBorderSlots.equalsIgnoreCase("")) {

            String[] split = ConfigGetters.mainBorderSlots.split(", ");
            int[] borderSlots = new int[split.length];
            int index = 0;
            for (String s : split) {

                borderSlots[index] = Integer.parseInt(split[index]);
                index++;

            }

            for (int i : borderSlots) {

                page.getTemplate().getSlot(i).setButton(getBorder());

            }

        }

        for (Map.Entry<String, Map<String, String>> entry : ConfigGetters.huntGUIStuff.entrySet()) {

            String name = entry.getKey();
            String id = BetterLoots.configManager.getConfigNode(2, "Hunts", name, "Display", "ID").getString();
            String displayName = BetterLoots.configManager.getConfigNode(2, "Hunts", name, "Display", "Name").getString();
            String hunt = BetterLoots.configManager.getConfigNode(2, "Hunts", name, "Hunt").getString();
            List<String> lore = BetterLoots.configManager.getConfigNode(2, "Hunts", name, "Display", "Lore").getList(TypeToken.of(String.class));
            int slot = Integer.parseInt(entry.getValue().get("Slot"));
            page.getTemplate().getSlot(slot).setButton(getHuntButton(player, id, displayName, hunt, lore));

        }

        UIManager.openUIForcefully(player, page);

    }

    private static Button getHuntButton (ServerPlayerEntity player, String id, String displayName, String huntName, List<String> loreString) {

        if (!huntName.contains(".conf")) {

            huntName = huntName + ".conf";

        }

        Hunt hunt = Hunt.getFromName(huntName);
        if (hunt == null) {

            ItemStack barrier = new ItemStack(Items.BARRIER);
            barrier.setDisplayName(FancyText.getFormattedText("&eInvalid hunt data!"));
            return GooeyButton.builder().display(barrier).build();

        } else {

            ItemStack item = ItemStackBuilder.buildFromStringID(id);
            item.setDisplayName(FancyText.getFormattedText(displayName));
            if (!loreString.isEmpty()) {

                ListNBT lore = new ListNBT();
                for (String s : loreString) {

                    lore.add(StringNBT.valueOf(FancyText.getFormattedString(s)));

                }

                item.getChildTag("display").put("Lore", lore);

            }

            HuntGUI gui = hunt.getGUI();
            return GooeyButton.builder()
                    .display(item)
                    .onClick(() -> {

                        HuntPage huntPage = new HuntPage(player, gui);
                        huntPage.open();

                    })
                    .build();

        }

    }

    private static Button getBorder() {

        String id = ConfigGetters.mainBorderID;
        ItemStack border = ItemStackBuilder.buildFromStringID(id);
        border.setDisplayName(FancyText.getFormattedText(""));
        return GooeyButton.builder().display(border).build();

    }

}
