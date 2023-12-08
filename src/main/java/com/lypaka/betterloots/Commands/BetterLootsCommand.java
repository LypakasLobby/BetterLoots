package com.lypaka.betterloots.Commands;

import com.lypaka.betterloots.BetterLoots;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.server.command.ConfigCommand;

import java.util.Arrays;
import java.util.List;

/**
 * FUCK Brigadier
 */
@Mod.EventBusSubscriber(modid = BetterLoots.MOD_ID)
public class BetterLootsCommand {

    public static final List<String> ALIASES = Arrays.asList("betterloots", "bloots", "loots");

    @SubscribeEvent
    public static void onCommandRegistration (RegisterCommandsEvent event) {

        new ClearCommand(event.getDispatcher());
        new MenuCommand(event.getDispatcher());
        new ReloadCommand(event.getDispatcher());
        new SetAccessibleCommand(event.getDispatcher());

        ConfigCommand.register(event.getDispatcher());

    }

}
