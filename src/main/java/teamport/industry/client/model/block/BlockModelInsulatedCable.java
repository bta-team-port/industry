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
 * @author Cookie, sunsetsatellite
 * @date 2024-12-24
 */
@Environment(EnvType.CLIENT)
public class BlockModelInsulatedCable extends BlockModelStandard<Block> {
    public BlockModelInsulatedCable(Block block) {
        super(block);
    }

    @Override
    public boolean render(Tessellator tessellator, int x, int y, int z) {
        float width = 0.375f;

        float halfWidth = (1.0F - width) / 2.0F;

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

        block.setBlockBounds(halfWidth, halfWidth, halfWidth, halfWidth + width, halfWidth + width, halfWidth + width);

        renderStandardBlock(tessellator, block, x, y, z);

        if(aPosX){
            block.setBlockBounds(halfWidth + width, halfWidth, halfWidth, 1.0F, halfWidth + width, halfWidth + width);
            renderStandardBlock(tessellator, block, x, y, z);
        }

        if(aNegX){
            block.setBlockBounds(0.0F, halfWidth, halfWidth, halfWidth, halfWidth + width, halfWidth + width);
            renderStandardBlock(tessellator, block, x, y, z);
        }

        if(aPosY){
            block.setBlockBounds(halfWidth, halfWidth + width, halfWidth, halfWidth + width, 1.0F, halfWidth + width);
            renderStandardBlock(tessellator, block, x, y, z);
        }

        if(aNegY){
            block.setBlockBounds(halfWidth, 0.0F, halfWidth, halfWidth + width, halfWidth, halfWidth + width);
            renderStandardBlock(tessellator, block, x, y, z);
        }

        if(aPosZ){
            block.setBlockBounds(halfWidth, halfWidth, halfWidth + width, halfWidth + width, halfWidth + width, 1.0F);
            renderStandardBlock(tessellator, block, x, y, z);
        }

        if(aNegZ){
            block.setBlockBounds(halfWidth, halfWidth, 0.0F, halfWidth + width, halfWidth + width, halfWidth);
            renderStandardBlock(tessellator, block, x, y, z);
        }

        block.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);

        return true;
    }
}
