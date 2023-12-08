package com.lypaka.betterloots.Listeners;

import com.lypaka.betterloots.Accounts.Account;
import com.lypaka.betterloots.BetterLoots;
import com.lypaka.betterloots.ScavengerHunts.Hunt;
import com.lypaka.betterloots.ScavengerHunts.Loot;
import com.lypaka.lypakautils.WorldStuff.WorldMap;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.Hand;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;

import java.util.List;

public class BlockInteractListener {

    @SubscribeEvent
    public void onBlockClick (PlayerInteractEvent.RightClickBlock event) {

        if (event.getSide() == LogicalSide.CLIENT) return;
        if (event.getHand() == Hand.OFF_HAND) return;

        ServerPlayerEntity player = (ServerPlayerEntity) event.getPlayer();
        String worldName = WorldMap.getWorldName(player);
        int x = event.getPos().getX();
        int y = event.getPos().getY();
        int z = event.getPos().getZ();
        String location = worldName + "," + x + "," + y + "," + z;
        Account account = BetterLoots.accountMap.get(player.getUniqueID());
        if (account == null) return;

        if (Hunt.getFromLootLocation(location) != null) {

            Hunt hunt = Hunt.getFromLootLocation(location);
            int lootNum = Loot.getLootNumberFromLocation(hunt, location);
            if (lootNum > -1) {

                Loot loot = Loot.getFromLocation(hunt, location);
                if (!account.hasClaimed(hunt.getName(), lootNum)) {

                    if (account.canClaim(hunt, loot)) {

                        account.setClaimed(hunt.getName(), lootNum);
                        List<String> prizes = loot.getPrizes();
                        for (String c : prizes) {

                            player.world.getServer().getCommandManager().handleCommand(player.world.getServer().getCommandSource(), c.replace("%player%", player.getName().getString()));

                        }
                        if (account.hasCompletedHunt(hunt.getName())) {

                            List<String> completionCommands = hunt.getCompletionRewards();
                            for (String c : completionCommands) {

                                player.world.getServer().getCommandManager().handleCommand(player.world.getServer().getCommandSource(), c.replace("%player%", player.getName().getString()));

                            }

                        }

                    }

                }

            }

        }

    }

}
