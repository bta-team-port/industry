package teamport.industry.core.block.logic;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogicLeavesBase;
import net.minecraft.core.block.material.Material;
import teamport.industry.core.block.IndBlocks;

public class BlockLogicLeavesOrange extends BlockLogicLeavesBase {
    public BlockLogicLeavesOrange(Block<?> block) {
        super(block, Material.leaves, IndBlocks.SAPLING_ORANGE);
    }
}
