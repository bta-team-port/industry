package teamport.industry.core;

import teamport.industry.Industry;
import turniplabs.halplibe.util.TomlConfigHandler;
import turniplabs.halplibe.util.toml.Toml;

public class IndConfig {
    private static final Toml TOML = new Toml("Industry's TOML Config");
    public static TomlConfigHandler cfg;
    static {
        TOML.addCategory("IDs")
                .addEntry("startingBlockID", "Default: 1100", 1100)
                .addEntry("startingItemID", "Default: 17000", 17000);

        TOML.addCategory("Spawning")
                .addEntry("copperOre", "Default: true", true)
                .addEntry("tinOre", "Default: true", true)
                .addEntry("uraniumOre", "Default: true", true);


        cfg = new TomlConfigHandler(Industry.MOD_ID, TOML);
    }
}
