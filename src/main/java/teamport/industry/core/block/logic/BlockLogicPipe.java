package teamport.industry.core.block.logic;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogic;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.player.inventory.container.Container;
import net.minecraft.core.world.WorldSource;
import teamport.industry.core.block.entity.TileEntityPipe;
import teamport.industry.extra.interfaces.IBasket;

public class BlockLogicPipe extends BlockLogic {
    public BlockLogicPipe(Block<?> block, Material material) {
        super(block, material);
        block.withEntity(TileEntityPipe::new);
    }

    public boolean canConnectTo(WorldSource worldSource, int x, int y, int z) {
        Block<?> block = worldSource.getBlock(x, y, z);
        TileEntity tile = worldSource.getTileEntity(x, y, z);
        if (block != null) {
            return block.getLogic() instanceof BlockLogicPipe || tile instanceof Container || tile instanceof IBasket;
        }

        return false;
    }

    @Override
    public void setBlockBounds(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
        bounds.set(0.25F, 0.25F, 0.25F, 0.75F, 0.75F, 0.75F);
    }

    @Override
    public boolean isSolidRender() {
        return false;
    }

    @Override
    public boolean isCubeShaped() {
        return false;
    }
}
