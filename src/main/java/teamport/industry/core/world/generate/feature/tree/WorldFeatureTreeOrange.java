package teamport.industry.core.world.generate.feature.tree;

import net.minecraft.core.world.World;
import net.minecraft.core.world.generate.feature.MethodParametersAnnotation;
import net.minecraft.core.world.generate.feature.tree.WorldFeatureTree;
import teamport.industry.core.block.IndBlocks;

import java.util.Random;

public class WorldFeatureTreeOrange extends WorldFeatureTree {
    @MethodParametersAnnotation(names = {"leavesID", "logID", "heightMod"})
    public WorldFeatureTreeOrange(int leavesID, int logID, int heightMod) {
        super(leavesID, logID, heightMod);
    }

    @Override
    public void placeLeaves(World world, int x, int y, int z, Random rand) {
        if (rand.nextInt(5) == 0) {
            world.setBlockAndMetadataWithNotify(x, y, z, IndBlocks.LEAVES_ORANGE_FLOWERING.id(), 0);
        } else {
            world.setBlockWithNotify(x, y, z, IndBlocks.LEAVES_ORANGE.id());
        }
    }

    @Override
    public boolean isLeaf(int id) {
        return id == IndBlocks.LEAVES_ORANGE.id() || id == IndBlocks.LEAVES_ORANGE_FLOWERING.id();
    }
}
