package com.lypaka.betterloots.Commands;

import com.lypaka.betterloots.BetterLoots;
import com.lypaka.betterloots.ConfigGetters;
import com.lypaka.betterloots.ScavengerHunts.HuntRegistry;
import com.lypaka.lypakautils.FancyText;
import com.lypaka.lypakautils.MiscHandlers.PermissionHandler;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.ServerPlayerEntity;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

import java.util.ArrayList;

public class ReloadCommand {

    public ReloadCommand (CommandDispatcher<CommandSource> dispatcher) {

        for (String a : BetterLootsCommand.ALIASES) {

            dispatcher.register(
                    Commands.literal(a)
                            .then(Commands.literal("reload")
                                    .executes(c -> {

                                        if (c.getSource().getEntity() instanceof ServerPlayerEntity) {

                                            ServerPlayerEntity player = (ServerPlayerEntity) c.getSource().getEntity();
                                            if (!PermissionHandler.hasPermission(player, "betterloots.command.admin")) {

                                                player.sendMessage(FancyText.getFormattedText("&cYou do not have permission to use this command!"), player.getUniqueID());
                                                return 0;

                                            }

                                        }

                                        try {

                                            BetterLoots.configManager.load();
                                            ConfigGetters.load();
                                            BetterLoots.lootConfigManager.setFileNames(ConfigGetters.scavengerHunts);
                                            BetterLoots.lootConfigManager.load();
                                            BetterLoots.scavengerHunts = new ArrayList<>();
                                            HuntRegistry.loadHunts();
                                            c.getSource().sendFeedback(FancyText.getFormattedText("&aSuccessfully reloaded BetterLoots configuration!"), true);

                                        } catch (ObjectMappingException e) {

                                            e.printStackTrace();

                                        }

                                        return 0;

                                    })
                            )
            );

        }

    }

}
