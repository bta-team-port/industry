package teamport.industry;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.texture.stitcher.TextureRegistry;
import net.minecraft.client.sound.SoundRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import turniplabs.halplibe.util.ClientStartEntrypoint;

import java.io.IOException;
import java.net.URISyntaxException;

@Environment(EnvType.CLIENT)
public class IndustryClient implements ClientStartEntrypoint, ClientModInitializer {
    public static final String MOD_ID = "industry|client";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void beforeClientStart() {

    }

    @Override
    public void afterClientStart() {

    }

    @Override
    public void onInitializeClient() {
        try {
            TextureRegistry.initializeAllFiles(Industry.MOD_ID, TextureRegistry.blockAtlas, true);
            TextureRegistry.initializeAllFiles(Industry.MOD_ID, TextureRegistry.itemAtlas, true);
            TextureRegistry.initializeAllFiles(Industry.MOD_ID, TextureRegistry.particleAtlas, true);
        } catch (URISyntaxException | IOException e) {
            LOGGER.error("Failed to initialize texture files, errors may occur!", e);
        }

        SoundRepository.registerNamespace(Industry.MOD_ID);

        LOGGER.info("Industry initialized for client.");
    }
}