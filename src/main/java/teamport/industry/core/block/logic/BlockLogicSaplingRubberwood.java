package teamport.industry.core.block.logic;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogicSaplingBase;
import net.minecraft.core.world.World;
import net.minecraft.core.world.generate.feature.WorldFeature;
import teamport.industry.core.block.IndBlocks;
import teamport.industry.core.world.generate.feature.tree.WorldFeatureTreeRubberwood;

import java.util.Random;

public class BlockLogicSaplingRubberwood extends BlockLogicSaplingBase {
    public BlockLogicSaplingRubberwood(Block<?> block) {
        super(block);
    }

    @Override
    public void growTree(World world, int x, int y, int z, Random rand) {
        WorldFeature tree = new WorldFeatureTreeRubberwood(IndBlocks.LEAVES_RUBBERWOOD.id(), IndBlocks.LOG_RUBBERWOOD.id(), 6);
        world.setBlock(x, y, z, 0);
        if (!tree.place(world, rand, x, y, z)) {
            world.setBlock(x, y, z, block.id());
        }
    }
}
