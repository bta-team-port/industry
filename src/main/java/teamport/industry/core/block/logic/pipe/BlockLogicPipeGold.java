package teamport.industry.core.block.logic.pipe;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.material.Material;
import teamport.industry.core.block.entity.TileEntityPipeBase;

public class BlockLogicPipeGold extends BlockLogicPipeBase {
    public BlockLogicPipeGold(Block<?> block) {
        super(block, Material.metal);
        block.withEntity(() -> new TileEntityPipeBase(0, 20 / 2));
    }
}
