package teamport.industry.extra.mixin;

import net.minecraft.core.block.BlockLogicOreGold;
import net.minecraft.core.world.World;
import net.minecraft.core.world.chunk.Chunk;
import net.minecraft.core.world.generate.chunk.ChunkDecorator;
import net.minecraft.core.world.generate.chunk.perlin.overworld.ChunkDecoratorOverworld;
import net.minecraft.core.world.generate.feature.WorldFeatureOre;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import teamport.industry.core.IndConfig;
import teamport.industry.core.block.IndBlocks;
import teamport.industry.core.block.logic.BlockLogicOreCopper;
import teamport.industry.core.block.logic.BlockLogicOreTin;
import teamport.industry.core.block.logic.BlockLogicOreUranium;

import java.util.Random;

@Mixin(value = ChunkDecoratorOverworld.class, remap = false)
public abstract class ChunkDecoratorOverworldMixin implements ChunkDecorator {

    @Shadow @Final private World world;

    @Inject(method = "decorate", at = @At("TAIL"))
    private void industry_decorateOres(@NotNull Chunk chunk, CallbackInfo ci) {
        int chunkX = chunk.xPosition;
        int chunkZ = chunk.zPosition;
        int minY = this.world.getWorldType().getMinY();
        int maxY = this.world.getWorldType().getMaxY();
        int rangeY = maxY + 1 - minY;
        float oreHeightModifier = (float)rangeY / 128.0F;
        int x = chunkX * 16;
        int z = chunkZ * 16;
        Random rand = new Random(world.getRandomSeed());

        // Copper Ore
        if (IndConfig.cfg.getBoolean("Spawning.copperOre")) {
            for (int heightModifier = 0; (float) heightModifier < 10.0F * oreHeightModifier; ++heightModifier) {
                int _x = x + rand.nextInt(16);
                int _y = minY + rand.nextInt(rangeY / 2);
                int _z = z + rand.nextInt(16);
                (new WorldFeatureOre(BlockLogicOreCopper.variantMap, 10)).place(this.world, rand, _x, _y, _z);
            }
        }

        // Tin Ore
        if (IndConfig.cfg.getBoolean("Spawning.tinOre")) {
            for (int heightModifier = 0; (float) heightModifier < 12.0F * oreHeightModifier; ++heightModifier) {
                int _x = x + rand.nextInt(16);
                int _y = minY + rand.nextInt(rangeY / 2);
                int _z = z + rand.nextInt(16);
                (new WorldFeatureOre(BlockLogicOreTin.variantMap, 12)).place(this.world, rand, _x, _y, _z);
            }
        }

        // Uranium Ore
        if (IndConfig.cfg.getBoolean("Spawning.uraniumOre")) {
            for (int heightModifier = 0; (float) heightModifier < 4.0F * oreHeightModifier; ++heightModifier) {
                int _x = x + rand.nextInt(16);
                int _y = minY + rand.nextInt(rangeY / 2);
                int _z = z + rand.nextInt(16);
                (new WorldFeatureOre(BlockLogicOreUranium.variantMap, 3)).place(this.world, rand, _x, _y, _z);
            }
        }
    }
}
