package com.lypaka.betterloots.Listeners;

import com.lypaka.betterloots.BetterLoots;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;

@Mod.EventBusSubscriber(modid = BetterLoots.MOD_ID)
public class ServerStartingListener {

    @SubscribeEvent
    public static void onServerStarting (FMLServerStartingEvent event) {

        MinecraftForge.EVENT_BUS.register(new BlockInteractListener());
        MinecraftForge.EVENT_BUS.register(new JoinListener());

    }

}
