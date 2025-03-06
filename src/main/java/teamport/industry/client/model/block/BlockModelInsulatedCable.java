package teamport.industry.client.model.block;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.block.model.BlockModelStandard;
import net.minecraft.client.render.tessellator.Tessellator;
import net.minecraft.core.block.Block;
import sunsetsatellite.catalyst.energy.electric.api.IElectric;
import sunsetsatellite.catalyst.energy.electric.api.IElectricWire;

/**
 * Client model renderer for the insulated cables (6x6)
 * @author Cookie
 * @date 2024-12-24
 */
@Environment(EnvType.CLIENT)
public class BlockModelInsulatedCable extends BlockModelStandard<Block> {
    public BlockModelInsulatedCable(Block block) {
        super(block);
    }

    @Override
    public boolean render(Tessellator tessellator, int x, int y, int z) {
        float boundMin = 0.315f;
        float boundMax = 0.685f;

        boolean aPosX = renderBlocks.blockAccess.getBlockTileEntity(x + 1, y, z) instanceof IElectric ||
                renderBlocks.blockAccess.getBlockTileEntity(x + 1, y, z) instanceof IElectricWire;

        boolean aNegX = renderBlocks.blockAccess.getBlockTileEntity(x - 1, y, z) instanceof IElectric ||
                renderBlocks.blockAccess.getBlockTileEntity(x - 1, y, z) instanceof IElectricWire;

        boolean aPosY = renderBlocks.blockAccess.getBlockTileEntity(x, y + 1, z) instanceof IElectric ||
                renderBlocks.blockAccess.getBlockTileEntity(x, y + 1, z) instanceof IElectricWire;

        boolean aNegY = renderBlocks.blockAccess.getBlockTileEntity(x, y - 1, z) instanceof IElectric ||
                renderBlocks.blockAccess.getBlockTileEntity(x, y - 1, z) instanceof IElectricWire;

        boolean aPosZ = renderBlocks.blockAccess.getBlockTileEntity(x, y, z + 1) instanceof IElectric ||
                renderBlocks.blockAccess.getBlockTileEntity(x, y, z + 1) instanceof IElectricWire;

        boolean aNegZ = renderBlocks.blockAccess.getBlockTileEntity(x, y, z - 1) instanceof IElectric ||
                renderBlocks.blockAccess.getBlockTileEntity(x, y, z - 1) instanceof IElectricWire;

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

    /*    public static boolean renderCable(RenderBlocks renderBlocks, WorldSource blockAccess, BlockCable blockCable, int x, int y, int z) {
        float boundMin = 0.375f;
        float boundMax = 0.625f;

        boolean aPosX = blockAccess.getBlockId(x + 1, y, z) == blockCable.id ||
                blockAccess.getBlockTileEntity(x + 1, y, z) instanceof IEnergy;

        boolean aNegX = blockAccess.getBlockId(x - 1, y, z) == blockCable.id ||
                blockAccess.getBlockTileEntity(x - 1, y, z) instanceof IEnergy;

        boolean aPosY = blockAccess.getBlockId(x, y + 1, z) == blockCable.id ||
                blockAccess.getBlockTileEntity(x, y + 1, z) instanceof IEnergy;

        boolean aNegY = blockAccess.getBlockId(x, y - 1, z) == blockCable.id ||
                blockAccess.getBlockTileEntity(x, y - 1, z) instanceof IEnergy;

        boolean aPosZ = blockAccess.getBlockId(x, y, z + 1) == blockCable.id ||
                blockAccess.getBlockTileEntity(x, y, z + 1) instanceof IEnergy;

        boolean aNegZ = blockAccess.getBlockId(x, y, z - 1) == blockCable.id ||
                blockAccess.getBlockTileEntity(x, y, z - 1) instanceof IEnergy;

        blockCable.setBlockBounds(boundMin - 0.0001f, boundMin - 0.0001f, boundMin - 0.0001f, boundMax + 0.0001f, boundMax + 0.0001f, boundMax + 0.0001f);

        renderBlocks.renderStandardBlock(blockCable, x, y, z);

        if (aPosX || aNegX) {
            blockCable.setBlockBounds(
                    (float) (0.5 + (aNegX ? -0.5f : 0.0f)), boundMin, boundMin,
                    (float) (0.5 + (aPosX ? 0.5f : 0.0f)), boundMax, boundMax
            );
            renderBlocks.renderStandardBlock(blockCable, x, y, z);
        }

        if (aPosY || aNegY) {
            blockCable.setBlockBounds(
                    boundMin, (float) (0.5 + (aNegY ? -0.5f : 0.0f)), boundMin,
                    boundMax, (float) (0.5 + (aPosY ? 0.5f : 0.0f)), boundMax
            );
            renderBlocks.renderStandardBlock(blockCable, x, y, z);
        }
        if (aPosZ || aNegZ) {
            blockCable.setBlockBounds(
                    boundMin, boundMin, (float) (0.5 + (aNegZ ? -0.5f : 0.0f)),
                    boundMax, boundMax, (float) (0.5 + (aPosZ ? 0.5f : 0.0f))
            );
            renderBlocks.renderStandardBlock(blockCable, x, y, z);
        }

        blockCable.setBlockBounds(0.15f, 0.15f, 0.15f, 0.85f, 0.85f, 0.85f);

        return true;
    }*/
}
