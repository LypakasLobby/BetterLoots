package com.lypaka.betterloots.Commands;

import com.lypaka.betterloots.ScavengerHunts.Hunt;
import com.lypaka.lypakautils.FancyText;
import com.lypaka.lypakautils.MiscHandlers.PermissionHandler;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.ServerPlayerEntity;

public class SetAccessibleCommand {

    public SetAccessibleCommand (CommandDispatcher<CommandSource> dispatcher) {

        for (String a : BetterLootsCommand.ALIASES) {

            dispatcher.register(
                    Commands.literal(a)
                            .then(Commands.literal("setaccess")
                                    .then(Commands.argument("hunt", StringArgumentType.string())
                                            .then(Commands.argument("value", StringArgumentType.string())
                                                    .executes(c -> {

                                                        if (c.getSource().getEntity() instanceof ServerPlayerEntity) {

                                                            ServerPlayerEntity player = (ServerPlayerEntity) c.getSource().getEntity();
                                                            if (!PermissionHandler.hasPermission(player, "betterloots.command.admin")) {

                                                                player.sendMessage(FancyText.getFormattedText("&cYou do not have permission to use this command!"), player.getUniqueID());
                                                                return 0;

                                                            }

                                                        }

                                                        String huntName = StringArgumentType.getString(c, "hunt");
                                                        String value = StringArgumentType.getString(c, "value");
                                                        if (!value.equalsIgnoreCase("true")) {

                                                            if (!value.equalsIgnoreCase("false")) {

                                                                c.getSource().sendErrorMessage(FancyText.getFormattedText("&cInvalid value! Expected: \"true\" or \"false\"!"));
                                                                return 0;

                                                            }

                                                        }

                                                        boolean setting = Boolean.parseBoolean(value);
                                                        if (!huntName.contains(".conf")) {

                                                            huntName = huntName + ".conf";

                                                        }
                                                        if (Hunt.getFromName(huntName) != null) {

                                                            Hunt hunt = Hunt.getFromName(huntName);
                                                            hunt.setAccessible(setting);
                                                            String mode;
                                                            if (setting) {

                                                                mode = "&aAccessible";

                                                            } else {

                                                                mode = "&cInaccessible";

                                                            }

                                                            c.getSource().sendFeedback(FancyText.getFormattedText("&aSuccessfully set the status of hunt: " + hunt.getName() + " to: " + mode), true);

                                                        } else {

                                                            c.getSource().sendErrorMessage(FancyText.getFormattedText("&cInvalid hunt name!"));

                                                        }

                                                        return 1;

                                                    })
                                            )
                                    )
                            )
            );

        }

    }

}
