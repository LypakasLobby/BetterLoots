package com.lypaka.betterloots.GUI;

import ca.landonjw.gooeylibs2.api.UIManager;
import ca.landonjw.gooeylibs2.api.button.Button;
import ca.landonjw.gooeylibs2.api.button.GooeyButton;
import ca.landonjw.gooeylibs2.api.page.GooeyPage;
import ca.landonjw.gooeylibs2.api.template.types.ChestTemplate;
import com.lypaka.betterloots.Accounts.Account;
import com.lypaka.betterloots.BetterLoots;
import com.lypaka.betterloots.ScavengerHunts.Hunt;
import com.lypaka.betterloots.ScavengerHunts.HuntGUI;
import com.lypaka.lypakautils.FancyText;
import com.lypaka.lypakautils.MiscHandlers.ItemStackBuilder;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;

public class HuntPage {

    private final ServerPlayerEntity player;
    private final HuntGUI gui;

    public HuntPage (ServerPlayerEntity player, HuntGUI gui) {

        this.player = player;
        this.gui = gui;

    }

    public void open() {

        ChestTemplate template = ChestTemplate.builder(this.gui.getGUIRows()).build();
        GooeyPage page = GooeyPage.builder()
                .template(template)
                .title(FancyText.getFormattedString(this.gui.getGUIDisplayTitle()))
                .build();

        if (!this.gui.getGUIBorderSlots().equalsIgnoreCase("")) {

            String[] split = this.gui.getGUIBorderSlots().split(", ");
            int[] slots = new int[split.length];
            int index = 0;
            for (String s : split) {

                slots[index] = Integer.parseInt(s);
                index++;

            }

            for (int i : slots) {

                page.getTemplate().getSlot(i).setButton(getBorder());

            }

        }

        String[] split = this.gui.getLootSlots().split(", ");
        int[] slots = new int[split.length];
        for (int i = 0; i < split.length; i++) {

            slots[i] = Integer.parseInt(split[i]);

        }

        int lootIndex = 1;
        Account account = BetterLoots.accountMap.get(this.player.getUniqueID());
        Hunt hunt = this.gui.getHunt();
        for (int i : slots) {

            if (account.hasClaimed(hunt.getName(), lootIndex)) {

                page.getTemplate().getSlot(i).setButton(getClaimed());

            } else {

                page.getTemplate().getSlot(i).setButton(getNotClaimed());

            }
            lootIndex++;

        }

        UIManager.openUIForcefully(this.player, page);

    }

    private Button getClaimed() {

        String id = this.gui.getClaimedLootID();
        ItemStack claimed = ItemStackBuilder.buildFromStringID(id);
        claimed.setDisplayName(FancyText.getFormattedText(this.gui.getClaimedLootDisplayName()));
        return GooeyButton.builder().display(claimed).build();

    }

    private Button getNotClaimed() {

        String id = this.gui.getNotClaimedLootID();
        ItemStack notClaimed = ItemStackBuilder.buildFromStringID(id);
        notClaimed.setDisplayName(FancyText.getFormattedText(this.gui.getNotClaimedLootDisplayName()));
        return GooeyButton.builder().display(notClaimed).build();

    }

    private Button getBorder() {

        String id = this.gui.getGUIBorderID();
        ItemStack border = ItemStackBuilder.buildFromStringID(id);
        border.setDisplayName(FancyText.getFormattedText(""));
        return GooeyButton.builder().display(border).build();

    }

}
