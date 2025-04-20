package teamport.industry;

import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import teamport.industry.core.util.SoundTypesFix;

@Environment(EnvType.SERVER)
public class IndustryServer implements DedicatedServerModInitializer {
    public static final String MOD_ID = "industry|server";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitializeServer() {
        SoundTypesFix.loadSoundsJson(Industry.MOD_ID);
        LOGGER.info("Industry initialized for server.");
    }
}
