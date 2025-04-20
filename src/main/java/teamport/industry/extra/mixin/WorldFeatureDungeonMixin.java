package teamport.industry.extra.mixin;

import net.minecraft.core.WeightedRandomBag;
import net.minecraft.core.WeightedRandomLootObject;
import net.minecraft.core.world.generate.feature.WorldFeature;
import net.minecraft.core.world.generate.feature.WorldFeatureDungeon;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import teamport.industry.core.block.IndBlocks;

@Mixin(value = WorldFeatureDungeon.class, remap = false)
public abstract class WorldFeatureDungeonMixin extends WorldFeature {

    @Shadow public WeightedRandomBag<WeightedRandomLootObject> chestLoot;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void industry_addLoot(int blockIdWalls, int blockIdFloor, String mobOverride, CallbackInfo ci) {
        chestLoot.addEntry(new WeightedRandomLootObject(IndBlocks.SAPLING_ORANGE.getDefaultStack()), 100.0);
    }
}
