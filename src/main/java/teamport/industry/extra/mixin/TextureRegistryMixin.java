package teamport.industry.extra.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.stitcher.AtlasStitcher;
import net.minecraft.client.render.stitcher.TextureRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import teamport.industry.Industry;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;

import static teamport.industry.Industry.MOD_ID;

@Mixin(value = TextureRegistry.class, remap = false)
@Environment(EnvType.CLIENT)
public abstract class TextureRegistryMixin  {

    @Shadow public static HashMap<String, AtlasStitcher> stitcherMap;

    @Shadow
    public static void initializeAllFiles(String namespace, AtlasStitcher atlas, boolean subDirectories) throws URISyntaxException, IOException, NullPointerException {
    }

    @Shadow public static AtlasStitcher artAtlas;

    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void industry_initAllTextures(CallbackInfo ci) {
        try {
            for (AtlasStitcher stitcher : stitcherMap.values()) {
                initializeAllFiles(MOD_ID, stitcher, stitcher != artAtlas);
            }
        } catch (Exception e) {
            Industry.LOGGER.error(e.getLocalizedMessage(), e);
        }
    }
}
