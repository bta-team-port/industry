package teamport.industry;

import net.fabricmc.api.ModInitializer;
import net.minecraft.core.util.collection.NamespaceID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import teamport.industry.core.IndConfig;
import teamport.industry.core.IndWireMaterials;
import teamport.industry.core.IndWireProperties;
import teamport.industry.core.block.IndBlockTags;
import teamport.industry.core.block.entity.TileEntityPipeBase;
import teamport.industry.core.block.entity.TileEntityPipeIron;
import turniplabs.halplibe.helper.EntityHelper;
import turniplabs.halplibe.util.GameStartEntrypoint;

public class Industry implements ModInitializer, GameStartEntrypoint {
    public static String MOD_ID = "industry";
    public static Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        EntityHelper.createTileEntity(TileEntityPipeBase.class, NamespaceID.getPermanent(MOD_ID, "pipe"));
        EntityHelper.createTileEntity(TileEntityPipeIron.class, NamespaceID.getPermanent(MOD_ID, "pipe_iron"));
        LOGGER.info("Industry Initialized. Have fun automating!");
    }

    @Override
    public void beforeGameStart() {
        new IndConfig();
        new IndBlockTags();
        new IndWireMaterials();
        new IndWireProperties();
    }

    @Override
    public void afterGameStart() {
    }
}
