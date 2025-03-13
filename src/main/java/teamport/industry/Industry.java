package teamport.industry;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import teamport.industry.core.IndConfig;
import teamport.industry.core.IndSounds;
import teamport.industry.core.IndWireMaterials;
import teamport.industry.core.IndWireProperties;
import teamport.industry.core.block.IndBlockTags;
import teamport.industry.core.block.IndBlocks;
import teamport.industry.core.item.IndItems;
import teamport.industry.core.recipe.IndRecipes;
import turniplabs.halplibe.util.GameStartEntrypoint;

public class Industry implements ModInitializer, GameStartEntrypoint {
    public static String MOD_ID = "industry";
    public static Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("Industry Initialized. Have fun automating!");
    }

    @Override
    public void beforeGameStart() {
        new IndConfig();

        new IndBlockTags();
        IndSounds.registerSounds();
        new IndWireMaterials();
        new IndWireProperties();

        new IndBlocks();
        new IndItems();
    }

    @Override
    public void afterGameStart() {
    }
}
