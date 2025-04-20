package teamport.industry.extra.mixin;

import net.minecraft.core.world.biome.BiomeForest;
import net.minecraft.core.world.generate.feature.WorldFeature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import teamport.industry.core.block.IndBlocks;
import teamport.industry.core.world.generate.feature.tree.WorldFeatureTreeRubberwood;

import java.util.Random;

@Mixin(value = BiomeForest.class, remap = false)
public abstract class BiomeForestMixin {

    @Inject(method = "getRandomWorldGenForTrees", at = @At("HEAD"), cancellable = true)
    private void industry_genRubberwood(Random random, CallbackInfoReturnable<WorldFeature> cir) {
        if (random.nextInt(20) == 0) {
            cir.setReturnValue(new WorldFeatureTreeRubberwood(IndBlocks.LEAVES_RUBBERWOOD.id(), IndBlocks.LOG_RUBBERWOOD.id(), 6));
        }
    }
}
