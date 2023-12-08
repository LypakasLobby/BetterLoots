package com.lypaka.betterloots.Commands;

import com.lypaka.betterloots.Accounts.Account;
import com.lypaka.betterloots.BetterLoots;
import com.lypaka.betterloots.ScavengerHunts.Hunt;
import com.lypaka.lypakautils.FancyText;
import com.lypaka.lypakautils.MiscHandlers.PermissionHandler;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.player.ServerPlayerEntity;

public class ClearCommand {

    public ClearCommand (CommandDispatcher<CommandSource> dispatcher) {

        for (String a : BetterLootsCommand.ALIASES) {

            dispatcher.register(
                    Commands.literal(a)
                            .then(Commands.literal("clear")
                                    .then(Commands.argument("player", EntityArgument.players())
                                            .then(Commands.argument("hunt", StringArgumentType.string())
                                                    .executes(c -> {

                                                        if (c.getSource().getEntity() instanceof ServerPlayerEntity) {

                                                            ServerPlayerEntity player = (ServerPlayerEntity) c.getSource().getEntity();
                                                            if (!PermissionHandler.hasPermission(player, "betterloots.command.admin")) {

                                                                player.sendMessage(FancyText.getFormattedText("&cYou do not have permission to use this command!"), player.getUniqueID());
                                                                return 0;

                                                            }

                                                        }

                                                        ServerPlayerEntity target = EntityArgument.getPlayer(c, "player");
                                                        String huntName = StringArgumentType.getString(c, "hunt");
                                                        if (!huntName.contains(".conf")) {

                                                            huntName = huntName + ".conf";

                                                        }

                                                        if (Hunt.getFromName(huntName) == null) {

                                                            c.getSource().sendErrorMessage(FancyText.getFormattedText("&cInvalid hunt name!"));
                                                            return 0;

                                                        }

                                                        Hunt hunt = Hunt.getFromName(huntName);
                                                        Account account = BetterLoots.accountMap.get(target.getUniqueID());
                                                        if (account == null) {

                                                            c.getSource().sendErrorMessage(FancyText.getFormattedText("&cCould not locate account for " + target.getName().getString() + "!"));
                                                            return 0;

                                                        }

                                                        account.resetHunt(huntName);
                                                        c.getSource().sendFeedback(FancyText.getFormattedText("&aSuccessfully cleared all progress on the hunt: " + hunt.getName() + " for " + target.getName().getString() + "!"), true);
                                                        return 1;

                                                    })
                                            )
                                    )
                            )
            );

        }

    }

}
