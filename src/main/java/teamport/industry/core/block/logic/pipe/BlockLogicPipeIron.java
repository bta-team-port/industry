package teamport.industry.core.block.logic.pipe;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;
import org.jetbrains.annotations.NotNull;
import teamport.industry.core.block.entity.TileEntityPipeIron;

public class BlockLogicPipeIron extends BlockLogicPipeBase {
    public BlockLogicPipeIron(Block<?> block) {
        super(block, Material.metal);
        block.withEntity(TileEntityPipeIron::new);
    }

    @Override
    public void onBlockPlacedOnSide(World world, int x, int y, int z, @NotNull Side side, double xPlaced, double yPlaced) {
        super.onBlockPlacedOnSide(world, x, y, z, side, xPlaced, yPlaced);
        TileEntity tile = world.getTileEntity(x, y, z);
        if (!(tile instanceof TileEntityPipeIron)) return;

        TileEntityPipeIron pipeTile = (TileEntityPipeIron) tile;
        pipeTile.updateRestrictions();
    }

    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, int blockId) {
        super.onNeighborBlockChange(world, x, y, z, blockId);
        TileEntity tile = world.getTileEntity(x, y, z);
        if (!(tile instanceof TileEntityPipeIron)) return;

        TileEntityPipeIron pipeTile = (TileEntityPipeIron) tile;
        pipeTile.updateRestrictions();
    }
}
