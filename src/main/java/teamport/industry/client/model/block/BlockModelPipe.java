package teamport.industry.client.model.block;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.block.model.BlockModelStandard;
import net.minecraft.client.render.tessellator.Tessellator;
import net.minecraft.core.block.Block;
import net.minecraft.core.player.inventory.IInventory;

/**
 * Client model renderer for the pipe blocks (8x8)
 * @author Cookie
 * @date 2024-12-24
 */
@Environment(EnvType.CLIENT)
public class BlockModelPipe extends BlockModelStandard<Block> {
    public BlockModelPipe(Block block) {
        super(block);
    }

    @Override
    public boolean render(Tessellator tessellator, int x, int y, int z) {
        float boundMin = 0.25f;
        float boundMax = 0.75f;

        boolean aPosX = renderBlocks.blockAccess.getBlockId(x + 1, y, z) == block.id ||
                renderBlocks.blockAccess.getBlockTileEntity(x + 1, y, z) instanceof IInventory;

        boolean aNegX = renderBlocks.blockAccess.getBlockId(x - 1, y, z) == block.id ||
                renderBlocks.blockAccess.getBlockTileEntity(x - 1, y, z) instanceof IInventory;

        boolean aPosY = renderBlocks.blockAccess.getBlockId(x, y + 1, z) == block.id ||
                renderBlocks.blockAccess.getBlockTileEntity(x, y + 1, z) instanceof IInventory;

        boolean aNegY = renderBlocks.blockAccess.getBlockId(x, y - 1, z) == block.id ||
                renderBlocks.blockAccess.getBlockTileEntity(x, y - 1, z) instanceof IInventory;

        boolean aPosZ = renderBlocks.blockAccess.getBlockId(x, y, z + 1) == block.id ||
                renderBlocks.blockAccess.getBlockTileEntity(x, y, z + 1) instanceof IInventory;

        boolean aNegZ = renderBlocks.blockAccess.getBlockId(x, y, z - 1) == block.id ||
                renderBlocks.blockAccess.getBlockTileEntity(x, y, z - 1) instanceof IInventory;

        // If this is set to normal bounds it will visibly z-fight! -Cookie
        block.setBlockBounds(boundMin - 0.0001f,
                boundMin - 0.0001f,
                boundMin - 0.0001f,
                boundMax + 0.0001f,
                boundMax + 0.0001f,
                boundMax + 0.0001f);

        renderStandardBlock(tessellator, block, x, y, z);

        if (aPosX || aNegX) {
            block.setBlockBounds(0.5f + (aNegX ? -0.5f : 0), boundMin, boundMin,
                    0.5f + (aPosX ? 0.5f : 0), boundMax, boundMax);
            renderStandardBlock(tessellator, block, x, y, z);
        }

        if (aPosY || aNegY) {
            block.setBlockBounds(boundMin, 0.5f + (aNegY ? -0.5f : 0), boundMin,
                    boundMax, 0.5f + (aPosY ? 0.5f : 0), boundMax);
            renderStandardBlock(tessellator, block, x, y, z);
        }

        if (aPosZ || aNegZ) {
            block.setBlockBounds(boundMin, boundMin, 0.5f + (aNegZ ? -0.5f : 0),
                    boundMax, boundMax, 0.5f + (aPosZ ? 0.5f : 0));
            renderStandardBlock(tessellator, block, x, y, z);
        }

        block.setBlockBounds(0.15f, 0.15f, 0.15f, 0.85f, 0.85f, 0.85f);

        return true;
    }
}
