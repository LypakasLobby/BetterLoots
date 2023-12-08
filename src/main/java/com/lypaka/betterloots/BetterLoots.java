package com.lypaka.betterloots;

import com.lypaka.betterloots.Accounts.Account;
import com.lypaka.betterloots.ScavengerHunts.Hunt;
import com.lypaka.betterloots.ScavengerHunts.HuntRegistry;
import com.lypaka.lypakautils.ConfigurationLoaders.BasicConfigManager;
import com.lypaka.lypakautils.ConfigurationLoaders.ComplexConfigManager;
import com.lypaka.lypakautils.ConfigurationLoaders.ConfigUtils;
import com.lypaka.lypakautils.ConfigurationLoaders.PlayerConfigManager;
import net.minecraftforge.fml.common.Mod;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("betterloots")
public class BetterLoots {

    public static final String MOD_ID = "betterloots";
    public static final String MOD_NAME = "BetterLoots";
    public static final Logger logger = LogManager.getLogger();
    public static BasicConfigManager configManager;
    public static ComplexConfigManager lootConfigManager;
    public static PlayerConfigManager playerConfigManager;
    public static Path dir;
    public static List<Hunt> scavengerHunts = new ArrayList<>();
    public static Map<UUID, Account> accountMap = new HashMap<>();

    public BetterLoots() throws IOException, ObjectMappingException {

        dir = ConfigUtils.checkDir(Paths.get("./config/betterloots"));
        String[] files = new String[]{"betterloots.conf", "lootTemplate.conf", "guiSettings.conf"};
        configManager = new BasicConfigManager(files, dir, BetterLoots.class, MOD_NAME, MOD_ID, logger);
        configManager.init();
        playerConfigManager = new PlayerConfigManager("account.conf", "player-accounts", dir, BetterLoots.class, MOD_NAME, MOD_ID, logger);
        playerConfigManager.init();
        ConfigGetters.load();
        lootConfigManager = new ComplexConfigManager(ConfigGetters.scavengerHunts, "hunts", "blankFile.conf", dir, BetterLoots.class, MOD_NAME, MOD_ID, logger);
        lootConfigManager.init();
        HuntRegistry.loadHunts();

    }

}
