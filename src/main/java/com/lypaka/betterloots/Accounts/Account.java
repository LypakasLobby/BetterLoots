package com.lypaka.betterloots.Accounts;

import com.google.common.reflect.TypeToken;
import com.lypaka.betterloots.BetterLoots;
import com.lypaka.betterloots.ScavengerHunts.Hunt;
import com.lypaka.betterloots.ScavengerHunts.Loot;
import com.lypaka.lypakautils.MiscHandlers.PermissionHandler;
import net.minecraft.entity.player.ServerPlayerEntity;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Account {

    private final ServerPlayerEntity player;
    private Map<String, Map<String, Boolean>> huntMap;

    public Account (ServerPlayerEntity player) {

        this.player = player;
        this.huntMap = new HashMap<>();

    }

    public void create() throws ObjectMappingException {

        this.huntMap = BetterLoots.playerConfigManager.getPlayerConfigNode(this.player.getUniqueID(), "Hunts").getValue(new TypeToken<Map<String, Map<String, Boolean>>>() {});
        BetterLoots.accountMap.put(this.player.getUniqueID(), this);

    }

    public boolean hasClaimed (String hunt, int lootNum) {

        if (!this.huntMap.containsKey(hunt)) {

            return false;

        } else {

            Map<String, Boolean> map = this.huntMap.get(hunt);
            return map.getOrDefault("Loot-" + lootNum, false);

        }

    }

    public void setClaimed (String hunt, int lootNum) {

        Map<String, Boolean> map = new HashMap<>();
        if (this.huntMap.containsKey(hunt)) {

            map = this.huntMap.get(hunt);

        }
        map.put("Loot-" + lootNum, true);
        this.huntMap.put(hunt, map);

    }

    public boolean hasCompletedHunt (String huntName) {

        if (!this.huntMap.containsKey(huntName)) {

            return false;

        }
        Hunt hunt = null;
        for (Hunt h : BetterLoots.scavengerHunts) {

            if (h.getName().equalsIgnoreCase(huntName)) {

                hunt = h;
                break;

            }

        }

        if (hunt != null) {

            int amount = hunt.getLoots().size();
            Map<String, Boolean> map = this.huntMap.get(huntName);
            for (int i = 1; i <= amount; i++) {

                try {

                    if (!map.get("Loot-" + i)) {

                        return false;

                    }

                } catch (NullPointerException er) {

                    return false;

                }

            }

            return true;

        } else {

            BetterLoots.logger.error("Could not get Hunt from name to check for completion! Hunt name: " + huntName);
            return false;

        }

    }

    public boolean canClaim (Hunt hunt, Loot loot) {

        if (!hunt.isAccessible()) {

            if (!PermissionHandler.hasPermission(this.player, "betterloots.admin")) {

                return false;

            } else {

                return true;

            }

        }
        List<String> requirements = loot.getRequirements();
        for (String p : requirements) {

            if (!PermissionHandler.hasPermission(this.player, p)) {

                return false;

            }

        }

        return true;

    }

    public void resetHunt (String huntName) {

        this.huntMap.entrySet().removeIf(h -> h.getKey().equalsIgnoreCase(huntName));
        Hunt hunt = Hunt.getFromName(huntName);
        for (String c : hunt.getClearCommands()) {

            this.player.world.getServer().getCommandManager().handleCommand(this.player.world.getServer().getCommandSource(), c.replace("%player%", this.player.getName().getString()));

        }

    }

    public void save() {

        BetterLoots.playerConfigManager.getPlayerConfigNode(this.player.getUniqueID(), "Hunts").setValue(this.huntMap);

    }

}
