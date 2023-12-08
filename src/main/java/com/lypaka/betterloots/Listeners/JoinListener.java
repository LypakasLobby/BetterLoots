package com.lypaka.betterloots.Listeners;

import com.lypaka.betterloots.Accounts.Account;
import com.lypaka.betterloots.BetterLoots;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

import java.util.UUID;

public class JoinListener {

    @SubscribeEvent
    public void onJoin (PlayerEvent.PlayerLoggedInEvent event) throws ObjectMappingException {

        ServerPlayerEntity player = (ServerPlayerEntity) event.getPlayer();
        BetterLoots.playerConfigManager.loadPlayer(player.getUniqueID());
        Account account = new Account(player);
        account.create();

    }

    @SubscribeEvent
    public void onLeave (PlayerEvent.PlayerLoggedOutEvent event) {

        UUID uuid = event.getPlayer().getUniqueID();
        Account account = BetterLoots.accountMap.get(uuid);
        if (account == null) return;
        account.save();
        BetterLoots.accountMap.entrySet().removeIf(entry -> entry.toString().equalsIgnoreCase(uuid.toString()));

    }

}
