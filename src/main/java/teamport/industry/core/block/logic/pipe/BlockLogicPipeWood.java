package teamport.industry.core.block.logic.pipe;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.material.Material;
import teamport.industry.core.block.entity.TileEntityPipeBase;

public class BlockLogicPipeWood extends BlockLogicPipeBase {
    public BlockLogicPipeWood(Block<?> block) {
        super(block, Material.wood);
        block.withEntity(() -> new TileEntityPipeBase(1, 20 * 3));
    }
}
